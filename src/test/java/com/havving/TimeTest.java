package com.havving;

import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by HAVVING on 2021-04-11.
 */
public class TimeTest {

    @Test
    @Ignore
    public void getTime() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Before : " + now);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = now.format(formatter);
        System.out.println("After : " + formatDateTime);

    }

}
