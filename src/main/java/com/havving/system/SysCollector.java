package com.havving.system;

/**
 * Created by HAVVING on 2021-03-02.
 */


import com.havving.Printer;
import com.havving.system.domain.xml.Configs;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;


/**
 * SysCollector Main Class
 */
public class SysCollector {
    private static final Logger log = LoggerFactory.getLogger(SysCollector.class);

    public static void main(String[] args) {
        // Initialize
        URL url = SysCollector.class.getClassLoader().getResource("syscollector.xml");
        File xmlFile = new File(url.getFile());
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Configs.class);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        argsCheckup(args);

        // add shutdown hook
        shutdownHook();
        createPidFile();
        log.info("ServiceManager Run.");
    }


    /**
     * 명령어 체크
     * @param args
     */
    private static void argsCheckup(String[] args) {
        if (args != null && args.length > 0) {
            Printer printer = new Printer();
            for (String arg : args) {
                if ("-h".equals(arg) || "--help".equals(arg)) {
                    Printer.printHelp();
                }
                if ("-a".equals(arg) || "--all".equals(arg)) {
                    printer.print(Printer.PrintMode.ALL);
                }
                if ("-c".equals(arg) || "--cpu".equals(arg)) {
                    printer.print(Printer.PrintMode.CPU);
                }
                if ("-d".equals(arg) || "--disk".equals(arg)) {
                    printer.print(Printer.PrintMode.DISK);
                }
                if ("-m".equals(arg) || "--memory".equals(arg)) {
                    printer.print(Printer.PrintMode.MEMORY);
                }
                if ("-n".equals(arg) || "--network".equals(arg)) {
                    printer.print(Printer.PrintMode.NETWORK);
                }
                if ("-cfg".equals(arg) || "--config".equals(arg)) {
                    printer.print(Printer.PrintMode.CONFIG);
                }
                // process kill on linux
                if ("-k".equals(arg) || "--kill".equals(arg)) {
                    if (System.getProperty("os.name").toLowerCase().contains("linux")) {
                        try {
                            File pidFile = new File("syscollector.pid");
                            if (!pidFile.exists()) {
                                System.out.println("pid File not exist.");
                                return;
                            }
                            List<String> lines = FileUtils.readLines(pidFile, Charset.forName("UTF-8"));
                            System.out.println("SysCollector kill pid " + lines.get(0));
                            // 시스템 command 실행
                            // kill -15 pid : 정상 종료
                            Runtime.getRuntime().exec(new String[]{
                                    "kill",
                                    "-15",
                                    lines.get(0)
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("kill function can't run this platform.");
                    }
                }
            }
                System.exit(0);
            }
        }


    /**
     * shutdown
     */
    private static void shutdownHook() {
        //JVM 셧다운 전, 종료 작업 처리
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                log.info("SysCollector terminate signal gas been called. Process goes down.\n ");
                System.out.println("SysCollector terminate signal gas been called. Process goes down.\n");
            }
        });
    }


    /**
     * create pid file
     */
    private static void createPidFile() {
        try {
            File pidFile = new File("syscollector.pid");
            if (pidFile.exists()) {
                FileUtils.forceDelete(pidFile);
            }
            // pid 구하기 (pid@pc명)
            String name = ManagementFactory.getRuntimeMXBean().getName();
            String pid = name.split("@")[0];
            FileUtils.write(pidFile, pid, Charset.forName("UTF-8"), false);
            System.out.println("SysCollector has been initialized pid: " + pid + " location: " + pidFile.getAbsoluteFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
