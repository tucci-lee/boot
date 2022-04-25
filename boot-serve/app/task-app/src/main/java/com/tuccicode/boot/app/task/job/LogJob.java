package com.tuccicode.boot.app.task.job;

import com.tuccicode.boot.domain.task.entity.TaskLog;
import com.tuccicode.boot.domain.task.service.TaskLogService;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志纪录任务，继承此类的任务每次执行都会纪录运行信息到数据库
 *
 * @author tucci.lee
 */
public abstract class LogJob extends AbstractJob {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final TaskLogService taskLogService;

    protected LogJob(TaskLogService taskLogService) {
        this.taskLogService = taskLogService;
    }


    @Override
    protected void before(JobExecutionContext context) {
        long taskId = Long.parseLong(context.getJobDetail().getKey().getName());
        long startTime = context.getFireTime().getTime();
        TaskLog log = new TaskLog()
                .setTaskId(taskId)
                .setStartTime(startTime);
        Long id = taskLogService.create(log);
        context.setResult(id);
    }

    @Override
    protected void after(JobExecutionContext context, Exception e) {
        Long logId = (Long) context.getResult();
        long startTime = context.getFireTime().getTime();
        long runTime = System.currentTimeMillis() - startTime;
        TaskLog log = new TaskLog()
                .setId(logId)
                .setRunTime(runTime);
        if (e == null) {
            log.setStatus(true);
        } else {
            logger.error("任务失败", e);
            log.setStatus(false)
                    .setMessage(e.getMessage());
        }
        taskLogService.update(log);
    }

}
