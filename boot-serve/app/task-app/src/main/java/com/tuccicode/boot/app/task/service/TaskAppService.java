package com.tuccicode.boot.app.task.service;

import com.tuccicode.boot.app.task.assembler.TaskAssembler;
import com.tuccicode.boot.app.task.dto.body.TaskAddBody;
import com.tuccicode.boot.app.task.dto.body.TaskEditBody;
import com.tuccicode.boot.app.task.dto.body.TaskEditStatusBody;
import com.tuccicode.boot.app.task.dto.vo.TaskVO;
import com.tuccicode.boot.app.task.job.AbstractJob;
import com.tuccicode.boot.domain.exception.BootBizCode;
import com.tuccicode.boot.domain.task.entity.Task;
import com.tuccicode.boot.domain.task.entity.TaskListQuery;
import com.tuccicode.boot.domain.task.service.TaskService;
import com.tuccicode.raccoon.dto.PageResponse;
import com.tuccicode.raccoon.dto.Response;
import com.tuccicode.raccoon.exception.BizException;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tucci.lee
 */
@Service
public class TaskAppService implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Scheduler scheduler;
    private final TaskService taskService;

    public TaskAppService(Scheduler scheduler,
                          TaskService taskService) {
        this.scheduler = scheduler;
        this.taskService = taskService;
    }

    /**
     * 将所有的定时任务加载到内存中
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<Task> tasks = taskService.list();
        for (Task task : tasks) {
            this.addJob(task);
        }
    }

    public Response list(TaskListQuery query) {
        PageResponse<Task> page = taskService.list(query);
        List<TaskVO> taskVOList = page.getData().stream()
                .map(TaskAssembler::toVO)
                .collect(Collectors.toList());
        return PageResponse.success(taskVOList, page.getTotal());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public Response add(TaskAddBody body) {
        Task task = new Task();
        BeanUtils.copyProperties(body, task);
        Long id = taskService.add(task);

        task.setId(id);
        this.addJob(task);

        return Response.success();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public Response edit(TaskEditBody body) {
        Task oldTask = taskService.getById(body.getId());

        Task task = new Task();
        BeanUtils.copyProperties(body, task);
        taskService.edit(task);

        task.setStatus(oldTask.getStatus());
        this.deleteJob(body.getId().toString());
        this.addJob(task);

        return Response.success();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public Response delete(Long id) {
        taskService.delete(id);
        this.deleteJob(id.toString());
        return Response.success();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public Response editStatus(TaskEditStatusBody body) {
        Task task = new Task();
        BeanUtils.copyProperties(body, task);
        taskService.editStatus(task);

        if (task.getStatus()) {
            this.resumeJob(body.getId().toString());
        } else {
            this.pauseJob(body.getId().toString());
        }
        return Response.success();
    }

    public Response start(Long id) {
        Task task = taskService.getById(id);
        this.startJob(task.getId().toString());
        return Response.success();
    }

    /**
     * 添加定时任务到内存
     *
     * @param task 定时任务
     */
    protected void addJob(Task task) {
        if (ObjectUtils.isEmpty(task)) {
            return;
        }
        try {
            String jobName = task.getId().toString();
            Class<?> taskClass = Class.forName(task.getClassName());
            if (!AbstractJob.class.isAssignableFrom(taskClass)) {
                throw new ClassCastException("不是一个正确的定时任务");
            }
            Class<Job> jobClass = (Class<Job>) taskClass;
            JobKey jobKey = JobKey.jobKey(jobName);
            JobBuilder jobBuilder = JobBuilder.newJob(jobClass)
                    .withIdentity(jobKey);
            JobDetail detail = jobBuilder.build();
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(jobName)
                    .withSchedule(CronScheduleBuilder.cronSchedule(task.getCron()))
                    .build();
            scheduler.scheduleJob(detail, trigger);
            if (task.getStatus() == null || !task.getStatus()) {
                scheduler.pauseJob(jobKey);
            }
        } catch (Exception e) {
            logger.error("定时任务添加出错: " + task.getId(), e);
            throw new BizException(BootBizCode.TASK_ADD_ERROR);
        }
    }

    /**
     * 暂停定时任务
     *
     * @param jobName 定时任务key
     */
    private void pauseJob(String jobName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName);
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            logger.error("定时任务暂停出错: " + jobName, e);
            throw new BizException(BootBizCode.TASK_PAUSE_ERROR);
        }
    }

    /**
     * 恢复定时任务
     *
     * @param jobName 定时任务key
     */
    protected void resumeJob(String jobName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName);
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            logger.error("定时任务恢复出错: " + jobName, e);
            throw new BizException(BootBizCode.TASK_RESUME_ERROR);
        }
    }

    /**
     * 删除内存中的定时任务
     *
     * @param jobName 定时任务key
     */
    protected void deleteJob(String jobName) {
        try {
            JobKey jobKey = JobKey.jobKey(jobName);
            scheduler.pauseJob(jobKey);
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            logger.error("定时任务删除出错: " + jobName, e);
            throw new BizException(BootBizCode.TASK_DELETE_ERROR);
        }
    }

    /**
     * 执行一次定时任务
     *
     * @param jobName 定时任务key
     */
    protected void startJob(String jobName) {
        JobKey jobKey = JobKey.jobKey(jobName);
        try {
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            logger.error("定时任务执行出错: " + jobName, e);
            throw new BizException(BootBizCode.TASK_START_ERROR);
        }
    }


}
