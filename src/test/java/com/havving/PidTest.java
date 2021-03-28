package com.havving;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by HAVVING on 2021-03-28.
 */
public class PidTest {

    @Test
    @Ignore
    public void getPid() {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String pid = name.split("@")[0];
        System.out.println("name: " + name + "/ pid: " + pid);
    }

    @Test
    @Ignore
    public void fileCreateTest() {
        try {
            File pidFile = new File("D:/test/syscollector.pid");
            // 기존 pid 파일이 존재하면 삭제
            if (pidFile.exists()) {
                FileUtils.forceDelete(pidFile);
            }
            // pid 구하기 (pid@pc명)
            String name = ManagementFactory.getRuntimeMXBean().getName();
            String pid = name.split("@")[0];
            FileUtils.write(pidFile, pid, Charset.forName("UTF-8"), false);

            List<String> lines = FileUtils.readLines(new File("D:/test/syscollector.pid"), Charset.forName("UTF-8"));
            System.out.println(lines.get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
