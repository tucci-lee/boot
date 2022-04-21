package com.tuccicode.boot.app.task.job;

import com.tuccicode.boot.domain.task.service.TaskLogService;
import org.quartz.JobExecutionContext;

/**
 * @author tucci.lee
 */
public class TestJob extends LogJob {
    protected TestJob(TaskLogService taskLogService) {
        super(taskLogService);
    }

    @Override
    protected void doExecute(JobExecutionContext context) throws Exception {
        System.out.println(1);
    }
}
