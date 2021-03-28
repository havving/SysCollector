package com.havving.system.service.batch;

import com.havving.system.global.Constants;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Created by HAVVING on 2021-03-28.
 *
 * 스케줄링 대상 클래스
 */
@Slf4j
public class JobExecutor implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

    }
}
