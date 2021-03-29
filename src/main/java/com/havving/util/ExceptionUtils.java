package com.havving.util;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by HAVVING on 2021-03-09.
 */
@Slf4j
public class ExceptionUtils {

    public static void printExceptionLog(Class<?> clz, Throwable e) {
        log.error("Class {}-{} got exception: {}", clz.getSimpleName(), e.getCause(), e.getMessage());
        for (StackTraceElement elem : e.getStackTrace()) {
            log.debug("{}", elem);
        }
    }
}
