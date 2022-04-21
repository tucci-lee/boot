package com.tuccicode.boot.app.task.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 定时任务需要继承此类，任务类不需要@Component也会被spring注入属性
 * 如果自定义quartz需要设置spring注入
 * SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
 * SpringBeanJobFactory jobFactory = new SpringBeanJobFactory();
 * jobFactory.setApplicationContext(applicationContext);
 * @author tucci.lee
 */
public abstract class AbstractJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        before(context);
        Exception e = null;
        try {
            doExecute(context);
        } catch (Exception t) {
            e = t;
        }
        after(context, e);
    }

    protected void before(JobExecutionContext context){}

    protected abstract void doExecute(JobExecutionContext context) throws Exception;

    protected void after(JobExecutionContext context, Exception e){};

}
