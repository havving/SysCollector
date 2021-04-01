package com.havving;

import org.quartz.*;

import java.util.Date;

public class JobExecutorTest implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
           System.out.println("Job Executed [" + new Date(System.currentTimeMillis()) + "]");
    }
}