package com.havving.system;

import com.havving.Printer;
import com.havving.system.domain.xml.BatchConfig;
import com.havving.system.domain.xml.Configs;
import com.havving.system.global.ConnectionFactory;
import com.havving.system.global.Constants;
import com.havving.system.service.batch.BatchManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by HAVVING on 2021-03-02.
 * SysCollector Main Class
 */
@Slf4j
public class SysCollector {

    /**
     * -- Main Method --
     * 1. read xml file
     * 2. check argument
     * 3. shutdown hook
     * 4. create pid file
     * 5. service run
     *
     * @param args
     */
    public static void main(String[] args) {
        if (System.getProperty("log.root.level") != null) {
            LogManager.getRootLogger().setLevel(Level.toLevel(System.getProperty("log.root.level")));
        }

        // Initialize
        URL url = SysCollector.class.getClassLoader().getResource("syscollector.xml");
        URL url_2 = SysCollector.class.getClassLoader().getResource("batchConfig.xml");
        File xmlFile = new File(url.getFile());
        File xmlFile_2 = new File(url_2.getFile());
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Configs.class);
            Unmarshaller u = jaxbContext.createUnmarshaller();
            Configs configs = (Configs) u.unmarshal(xmlFile);
            Constants.setConfig(configs);

            JAXBContext jaxbContext_2 = JAXBContext.newInstance(BatchConfig.class);
            Unmarshaller u_2 = jaxbContext_2.createUnmarshaller();
            BatchConfig batchConfig = (BatchConfig) u_2.unmarshal(xmlFile_2);
            Constants.setBatchConfig(batchConfig);

        } catch (JAXBException e) {
            log.error("SysCollector constants xml config fail\n{}", e);
            System.exit(-1);
        }

        argsCheckup(args);
        try {
            Constants.init();
        } catch (Exception e) {
            log.error("SysCollector initialized -Sigar,Gson,Services- fail\n{}", e);
            System.exit(-1);
        }
        shutdownHook();
        createPidFile();
        log.info("ServiceManager Run.");
        ConnectionFactory.getConnFactory().init();
        BatchManager.startBatch();

    }


    /**
     * cmd 명령어에 따른 출력 기능 수행
     *
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
     * JVM 셧다운 전, 종료 작업 처리
     */
    private static void shutdownHook() {
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
            // 기존 pid 파일이 존재하면 삭제
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
