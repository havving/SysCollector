package com.havving;

import org.junit.Ignore;
import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by HAVVING on 2021-03-30.
 */
public class QuartzTest {

    @Test
    @Ignore
    public void startScheduler() {
        try {
            // Job 구현 내용이 담긴 JobExecutorTest로 JobDetail 생성
            JobDetail jobDetail = newJob(JobExecutorTest.class)
                    .build();

            // 실행 시점을 결정하는 Trigger 생성
            Trigger trigger = newTrigger()
                    .build();

            // 스케줄러 실행 및 JobDetail과 Trigger 정보로 스케줄링
            Scheduler defaultScheduler = StdSchedulerFactory.getDefaultScheduler();
            defaultScheduler.start();
            defaultScheduler.scheduleJob(jobDetail, trigger);
            Thread.sleep(3 * 1000);  // Job이 실행될 수 있는 시간 여유를 준다

            // 스케줄러 종료
            defaultScheduler.shutdown(true);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
