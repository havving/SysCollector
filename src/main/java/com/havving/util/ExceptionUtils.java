package com.havving.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by HAVVING on 2021-03-09.
 */
public class ExceptionUtils {

    public static void printExceptionLog(Class<?> clz, Throwable e) {
        final Logger logger = LoggerFactory.getLogger(clz);
        logger.error("Class {}-{} got exception: {}", clz.getSimpleName(), e.getCause(), e.getMessage());
        for (StackTraceElement elem : e.getStackTrace()) {
            logger.debug("{}", elem);
        }
    }
}
