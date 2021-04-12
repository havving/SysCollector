package com.havving.system.service.store;

import com.havving.system.domain.SysModel;
import com.havving.system.domain.impl.*;
import com.havving.system.domain.jpa.*;
import com.havving.system.domain.jpa.Exception;
import com.havving.system.domain.jpa.Process;
import com.havving.system.service.StoreService;
import com.havving.system.service.SysCollectorService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Created by HAVVING on 2021-03-20.
 */
@Slf4j
@SysCollectorService(name = "jpaStoreService")
public class JpaStoreService implements StoreService {
    private SessionFactory client;

    @Override
    public void store(List<SysModel> models) {
        for (SysModel m : models) {
            store(m);
        }
    }

    @SneakyThrows
    @Override
    public void store(SysModel model) {
        log.debug("Jpa Storing Object: {}", model);
        LocalDateTime now = LocalDateTime.now();
        try (Session sess = client.openSession()) {
            sess.beginTransaction();
            switch (model.getType()) {
                case "cpu":
                    CpuSysModel c = (CpuSysModel) model;
                    Cpu cpu = new Cpu();
                    SimpleId id = new SimpleId(now, model.getHost());
                    cpu.setId(id);
                    cpu.setIdle(c.getIdle());
                    if (c.getLoadAverage() != null) {
                        Map<Integer, Double> load = c.getLoadAverage();
                        cpu.setLoadAverageZero(load.get(0));
                        cpu.setLoadAverageOne(load.get(1));
                        cpu.setLoadAverageTwo(load.get(2));
                    }
                    cpu.setSys(c.getSys());
                    cpu.setUsr(c.getUser());
                    cpu.setWait(c.getWait());
                    sess.save(cpu);
                    break;

                case "memory":
                    MemorySysModel m = (MemorySysModel) model;
                    Memory mem = new Memory();
                    mem.setActualFree(m.getActualFree());
                    mem.setActualUsed(m.getActualUsed());
                    mem.setFreePercent(m.getFreePercent());
                    mem.setId(new SimpleId(now, m.getHost()));
                    mem.setRam(m.getRam());
                    mem.setSwapFree(m.getSwapFree());
                    mem.setSwapPageIn(m.getSwapPageIn());
                    mem.setSwapPageOut(m.getSwapPageOut());
                    mem.setSwapUsed(m.getSwapUsed());
                    mem.setTotal(m.getTotal());
                    mem.setUsedPercent(m.getUsedPercent());
                    sess.save(mem);
                    break;

                case "disk":
                    DiskSysModel d = (DiskSysModel) model;
                    Disk disk = new Disk();
                    disk.setId(new Disk.DiskId(now, d.getHost(), d.getName()));
                    disk.setQueue(d.getQueue());
                    disk.setServiceTime(d.getServiceTime());
                    disk.setReadLiveBytes(d.getReadLiveBytes());
                    disk.setReads(d.getReads());
                    disk.setWriteLiveBytes(d.getWriteLiveBytes());
                    disk.setWrites(d.getWrites());
                    disk.setTotalBytes(d.getTotalBytes());
                    disk.setFreeBytes(d.getFreeBytes());
                    disk.setUsedBytes(d.getUsedBytes());
                    disk.setUsedPercent(d.getUsedPercent());
                    sess.save(disk);
                    break;

                case "network":
                    NetworkSysModel n = (NetworkSysModel) model;
                    Network net = new Network();
                    net.setBroadcast(n.getBroadCast());
                    net.setId(new Network.NetworkId(now, n.getHost(), n.getName()));
                    net.setMacAddr(n.getMacAddr());
                    net.setMetric(n.getMetric());
                    net.setMtu(n.getMtu());
                    net.setNetmask(n.getNetMask());
                    net.setNetIpAddr(n.getNetIpAddr());
                    net.setRxBytes(n.getRxBytes());
                    net.setRxDropped(n.getRxDropped());
                    net.setRxErrors(n.getRxErrors());
                    net.setRxLiveBytes(n.getRxLiveBytes());
                    net.setSpeed(n.getSpeed());
                    net.setTxBytes(n.getTxBytes());
                    net.setTxDropped(n.getTxDropped());
                    net.setTxErrors(n.getTxErrors());
                    net.setTxLiveBytes(n.getTxLiveBytes());
                    net.setTxPackets(n.getTxPackets());
                    sess.save(net);
                    break;

                case "process":
                    ProcessSysModel p = (ProcessSysModel) model;
                    Process proc = new Process();
                    proc.setArgs(p.getArgs());
                    proc.setCpuUsage(p.getCpuUsage());
                    proc.setDname(p.getDname());
                    proc.setId(new Process.ProcessId(now, p.getHost(), p.getPid()));
                    proc.setMemoryUsage(p.getMemoryUsage());
                    proc.setMemoryUsageMb(p.getMemoryUsageMb());
                    proc.setPpid(p.getPpid());
                    proc.setPriority(p.getPriority());
                    proc.setProcessName(p.getName());
                    proc.setState(String.valueOf(p.getState()));
                    proc.setThreads(p.getThreads());
                    sess.save(proc);
                    break;

                case "exception":
                    ExceptionSysModel e = (ExceptionSysModel) model;
                    Exception ex = new Exception();
                    ex.setMessage(e.getMessage());
                    sess.save(ex);
                    break;

                default:
                    throw new IllegalAccessException("model type is not matched.");
            }
            sess.getTransaction().commit();
        }
    }

    @Override
    public void setClient(Object client) {
            this.client = (SessionFactory) client;
    }
}
