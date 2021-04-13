package com.havving.system.service.batch;

import com.havving.system.global.Constants;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by HAVVING on 2021-03-28.
 *
 * JobExecutor 스케줄링
 */
public class BatchManager {

    public synchronized static void startBatch() {
        try {
            String groupName = Constants.getBatchConfig().job.group;
            String jobName = Constants.getBatchConfig().job.name;
            String triggerName = Constants.getBatchConfig().job.triggerName;
            String cronExpression = Constants.getBatchConfig().job.cronExpression;
            String targetClass = Constants.getBatchConfig().job.targetClass;

            JobBuilder jobBuilder = JobBuilder.newJob(JobExecutor.class).usingJobData("targetClass", targetClass)
                    .usingJobData("commandName", jobName).withIdentity(jobName, groupName);

            // Job 구현 내용이 담긴 JobExecutor로 JobDetail 생성
            JobDetail jobDetail = jobBuilder.build();

            // 실행 시점을 결정하는 Trigger 생성
            CronTrigger trigger = newTrigger().withIdentity(triggerName, groupName)
                    .withSchedule(cronSchedule(cronExpression)).build();

            // 스케줄러 실행 및 JobDetail과 Trigger 정보로 스케줄링
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            scheduler.scheduleJob(jobDetail, trigger);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
