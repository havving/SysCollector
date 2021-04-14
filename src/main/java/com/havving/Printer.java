package com.havving;

import com.havving.system.domain.JsonResponse;
import com.havving.system.domain.impl.*;
import com.havving.system.domain.xml.Configs;
import com.havving.system.service.SystemCollectorService;
import org.hyperic.sigar.Sigar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.Date;

/**
 * Created by HAVVING on 2021-03-03.
 */
public class Printer {
    GeneralSysModel[] models;

    /**
     *
     */
    public Printer() {
        SystemCollectorService service = new SystemCollectorService();
        Sigar sigar = new Sigar();
        service.setSigar(sigar);
        JsonResponse<GeneralSysModel> result = service.getTotalJson();
        models = result.getModel();
        sigar.close();

    }

    /**
     * Print [Help] Information
     */
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

    /**
     * 입력된 argument에 따른 출력
     *
     * @param mode
     */
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

    /**
     * Print [CPU, Memory, Disk, Network] All Information
     */
    private void printAll() {
        printCpu();
        printMemory();
        printDisk();
        printNetwork();
    }

    /**
     * Print [CPU] Information
     */
    private void printCpu() {
        System.out.println("\nCPU: ");
        CpuSysModel cpu = models[0].getCpu();
        System.out.println("Sys:" + cpu.getSys() + ", Usr:" + cpu.getUser() + ", Wait:" + cpu.getWait() + ", Idle:" + cpu.getIdle());
        if (cpu.getLoadAverage() != null) {
            System.out.println("Load Average 0:" + cpu.getLoadAverage().get(0) + ", 1:" + cpu.getLoadAverage().get(1) + ", 2:" + cpu.getLoadAverage().get(2));
        }
    }

    /**
     * Print [Memory] Information
     */
    private void printMemory() {
        System.out.println("\nMemory:");
        MemorySysModel mem = models[0].getMemory();
        System.out.println("RamUsed:" + mem.getActualUsed() + ", RamFree:" + mem.getActualFree() + ", RamFree(%):" + mem.getFreePercent());
        System.out.println("SwapUsed:" + mem.getSwapUsed() + ", SwapFree:" + mem.getSwapFree() + ", SwapTotal:" + mem.getSwapTotal()
                + ", PageIn:" + mem.getSwapPageIn() + ", PageOut:" + mem.getSwapPageOut());
    }

    /**
     * Print [Disk] Information
     */
    private void printDisk() {
        System.out.println("\nDisk:");
        DiskSysModel[] disks = models[0].getDisk();
        for (DiskSysModel d : disks) {
            System.out.println("    " + d.getName() + " Reads:" + d.getReads() + ", Writes:" + d.getWrites() + ", Queue:" + d.getQueue() + ", TotalBytes:" + d.getTotalBytes()
                    + ", FreeBytes:" + d.getFreeBytes() + ", UsedBytes:" + d.getUsedBytes() + ", Used(%):" + d.getUsedPercent());
        }
    }

    /**
     * Print [Network] Information
     */
    private void printNetwork() {
        System.out.println("\nNetwork:");
        NetworkSysModel[] networks = models[0].getNetwork();
        for (NetworkSysModel n : networks) {
            System.out.println("    " + n.getName() + " MacAddr:" + n.getMacAddr() + ", NetMask:" + n.getNetMask() + ", NetIpAddr:" + n.getNetIpAddr() + ", BroadCast:" + n.getBroadCast());
            System.out.println("    Received Bytes:" + n.getRxBytes() + ", Dropped:" + n.getRxDropped() + ", Errors:" + n.getRxErrors() + ", Packets:" + n.getRxPackets());
            System.out.println("    Transmitted Bytes:" + n.getTxBytes() + ", Dropped:" + n.getTxDropped() + ", Errors:" + n.getTxErrors() + ", Packets:" + n.getTxPackets());
        }
    }

    /**
     * Print [Config] Information
     */
    private void printConfig() {
        InputStream sys_url = Printer.class.getClassLoader().getResourceAsStream("syscollector.xml");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Configs.class);
            Unmarshaller u = jaxbContext.createUnmarshaller();
            Configs configs = (Configs) u.unmarshal(sys_url);
            System.out.println(configs.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public enum PrintMode {
        CPU, MEMORY, DISK, NETWORK, ALL, CONFIG;
    }
}
