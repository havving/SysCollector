package com.havving;

import com.havving.system.domain.impl.*;
import com.havving.system.domain.xml.Configs;

import javax.xml.bind.JAXBContext;
import java.io.File;
import java.net.URL;
import java.util.Date;

/**
 * Created by HAVVING on 2021-03-03.
 */
public class Printer {
    GeneralSysModel[] models;

    public Printer() {

    }

    public static void printHelp() {
        System.out.println("----SysCollector Usage");
        System.out.println("     -a, --all       : All Information.");
        System.out.println("     -c, --cpu       : CPU Information.");
        System.out.println("     -d, --disk      : Disk Information.");
        System.out.println("     -m, --memory    : Memory Information.");
        System.out.println("     -n, --network   : Network Information.");
        System.out.println("     -k, --kill      : SysCollector process kill.");
        System.out.println("     -h, --help      : Help page.");
        System.out.println("     -cfg, --config  : Syscollector configurations.");
    }

    public void print(PrintMode mode) {
        System.out.println("Configured host: " + models[0].getHost());
        System.out.println("Time: " + new Date(models[0].getTime()));

        switch (mode) {
            case ALL:
                printAll();
                break;
            case CPU:
                printCpu();
                break;
            case MEMORY:
                printMemory();
                break;
            case DISK:
                printDisk();
                break;
            case NETWORK:
                printNetwork();
                break;
            case CONFIG:
                printConfig();
                break;
        }
    }

    private void printAll() {
        printCpu();
        printMemory();
        printDisk();
        printNetwork();
    }

    private void printCpu() {
        System.out.println("\nCPU: ");
        CpuSysModel cpu = models[0].getCpu();
        System.out.println("Sys:" + cpu.getSys() + ", Usr:" + cpu.getUser() + ", Wait: " + cpu.getWait() + ", Idle:" + cpu.getIdle());
        if (cpu.getLoadAverage() != null) {
            System.out.println("Load Average 0:" + cpu.getLoadAverage().get(0) + ", 1:" + cpu.getLoadAverage().get(1) + ", 2:" + cpu.getLoadAverage().get(2));
        }
    }

    private void printMemory() {
        System.out.println("\nMemory :");
        MemorySysModel mem = models[0].getMemory();
        System.out.println("RamUsed:" + mem.getActualUsed() + ", RamFree:" + mem.getActualFree() + ", RamFree(%):" + mem.getFreePercent());
        System.out.println("SwapUsed:" + mem.getSwapUsed() + ", SwapFree:" + mem.getSwapFree() + ", SwapTotal:" + mem.getSwapTotal()
                + ", PageIn:" + mem.getSwapPageIn() + ", PageOut:" + mem.getSwapPageOut());
    }

    private void printDisk() {
        System.out.println("\nDisk :");
        DiskSysModel[] disks = models[0].getDisk();
        for (DiskSysModel d : disks) {
            System.out.println("    " + d.getName() + " Reads:" + d.getReads() + ", Writes:" + d.getWrites() + ", Queue:" + d.getQueue() + ", TotalBytes:" + d.getTotalBytes()
                    + ", FreeBytes:" + d.getFreeBytes() + ", UsedBytes:" + d.getUsedBytes() + ", Used(%):" + d.getUsedPercent());
        }
    }

    private void printNetwork() {
        System.out.println("\nNetwork :");
        NetworkSysModel[] networks = models[0].getNetwork();
        for (NetworkSysModel n : networks) {
            System.out.println("    " + n.getName() + " MacAddr:" + n.getMacAddr() + ", NetMask:" + n.getNetMask() + ", NetIpAddr:" + n.getNetIpAddr() + ", BroadCast:" + n.getBroadCast());
            System.out.println("    Received Bytes:" + n.getRxBytes() + ", Dropped:" + n.getRxDropped() + ", Errors:" + n.getRxErrors() + ", Packets:" + n.getRxPackets());
            System.out.println("    Transmitted Bytes:" + n.getTxBytes() + ", Dropped:" + n.getTxDropped() + ", Errors:" + n.getTxErrors() + ", Packets:" + n.getTxPackets());
        }
    }

    private void printConfig() {
        URL url = Printer.class.getClassLoader().getResource("syscollector.xml");
        File xmlFile = new File(url.getFile());
        try {
//            System.out.println(JAXBContext.newInstance(Configs.class, xmlFile).toString());
//            System.out.println(serializer.read(Configs.class, xml).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public enum PrintMode {
        CPU, MEMORY, DISK, NETWORK, ALL, CONFIG;
    }
}
