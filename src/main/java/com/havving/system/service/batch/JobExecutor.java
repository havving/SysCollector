package com.havving.system.service.batch;

import com.havving.system.global.ConnectionFactory;
import com.havving.system.global.ORMConnection;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

import java.lang.reflect.Constructor;

/**
 * Created by HAVVING on 2021-03-28.
 *
 * 스케줄링 대상 클래스
 */
@Slf4j
public class JobExecutor implements Job {

    private String targetClass;
    private ORMConnection connection = null;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail detail = context.getJobDetail();
        try {
            JobDataMap dataMap = detail.getJobDataMap();
            targetClass = dataMap.get("targetClass").toString();

            Class<?> jobClass = JobExecutor.class.getClassLoader().loadClass(targetClass);
            Constructor<?> constructor = jobClass.getConstructor(ORMConnection.class);

            connection = ConnectionFactory.getConnFactory().getOpenConnection();

            BatchExecutor executor = (BatchExecutor) constructor.newInstance(connection);
            if (connection != null) connection.beginTransaction();
            executor.execute();
            if (connection != null) connection.commit();

        } catch (Exception e) {
            log.error("Error while executing JobExecutor " + detail.getKey() + ", " + targetClass, e);
            if (connection != null) connection.rollback();

        } finally {
            if (connection != null) {
                connection.close();
                connection = null;
            }
        }

    }
}
