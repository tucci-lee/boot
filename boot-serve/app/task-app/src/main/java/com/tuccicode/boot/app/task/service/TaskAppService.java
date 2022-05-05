package com.tuccicode.boot.app.task.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.app.task.assembler.TaskAssembler;
import com.tuccicode.boot.app.task.dto.body.TaskCreateBody;
import com.tuccicode.boot.app.task.dto.body.TaskUpdateBody;
import com.tuccicode.boot.app.task.dto.body.TaskUpdateStatusBody;
import com.tuccicode.boot.app.task.dto.vo.TaskVO;
import com.tuccicode.boot.app.task.job.AbstractJob;
import com.tuccicode.boot.domain.exception.BootBizCode;
import com.tuccicode.boot.domain.task.dataobject.TaskDO;
import com.tuccicode.boot.domain.task.entity.Task;
import com.tuccicode.boot.domain.task.entity.TaskQuery;
import com.tuccicode.boot.domain.task.mapper.TaskMapper;
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
    private final TaskMapper taskMapper;

    public TaskAppService(Scheduler scheduler,
                          TaskService taskService,
                          TaskMapper taskMapper) {
        this.scheduler = scheduler;
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    /**
     * 将所有的定时任务加载到内存中
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<Task> tasks = taskService.list();
        for (Task task : tasks) {
            this.createJob(task);
        }
    }

    public Response list(TaskQuery query) {
        Page<TaskDO> page = new Page<>(query.getPageNo(), query.getPageSize());
        taskMapper.selectList(page, query);
        List<TaskVO> taskVOList = page.getRecords().stream()
                .map(TaskAssembler::toVO)
                .collect(Collectors.toList());
        return PageResponse.success(taskVOList, (int) page.getTotal());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public Response create(TaskCreateBody body) {
        Task task = new Task()
                .setStatus(false);
        BeanUtils.copyProperties(body, task);
        Long id = taskService.create(task);

        task.setId(id);
        this.createJob(task);

        return Response.success();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public Response update(TaskUpdateBody body) {
        Task oldTask = taskService.getById(body.getId());

        Task task = new Task();
        BeanUtils.copyProperties(body, task);
        taskService.update(task);

        task.setStatus(oldTask.getStatus());
        this.deleteJob(body.getId().toString());
        this.createJob(task);

        return Response.success();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public Response delete(Long id) {
        taskService.delete(id);
        this.deleteJob(id.toString());
        return Response.success();
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public Response updateStatus(TaskUpdateStatusBody body) {
        Task task = new Task();
        BeanUtils.copyProperties(body, task);
        taskService.updateStatus(task);

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
    protected void createJob(Task task) {
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
