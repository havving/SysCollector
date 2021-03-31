package com.havving.system.service.batch;

import com.havving.system.global.ORMConnection;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by HAVVING on 2021-03-28.
 *
 * 스케줄링 대상 클래스
 */
@Slf4j
public class JobExecutor implements Job {

    private ORMConnection connection = null;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

    }
}
