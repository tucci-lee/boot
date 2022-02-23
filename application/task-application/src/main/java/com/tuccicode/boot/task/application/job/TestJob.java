package com.tuccicode.boot.task.application.job;

import com.tuccicode.boot.task.domain.service.TaskLogService;
import org.quartz.JobExecutionContext;

import java.util.logging.Logger;

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
