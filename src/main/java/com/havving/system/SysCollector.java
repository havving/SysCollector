package com.havving.system;

/**
 * Created by HAVVING on 2021-03-02.
 */


import com.havving.Printer;
import org.apache.commons.io.FileUtils;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;


/**
 * SysCollector Main Class
 */
public class SysCollector {
    //private static final Logger log = LoggerFactory.getLogger(SysCollector.class);

    public static void main(String[] args) {
        // Initialize
        URL url = SysCollector.class.getClassLoader().getResource("syscollector.xml");
        File xml = new File(url.getFile());

        argsCheckup(args);

        // add shutdown hook
        shutdownHook();
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
                            Runtime.getRuntime().exec(new String[]{
                                    "kill",
                                    "-15",
                                    lines.get(0)
                            });
                        } catch (IOException e) {

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
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {

            }
        });
    }
}
