package com.havving.system.service;

import com.havving.system.domain.JsonResponse;
import com.havving.system.global.StatusCode;
import com.havving.system.domain.SysModel;
import com.havving.system.domain.impl.*;
import com.havving.system.global.Constants;
import org.apache.commons.lang3.StringUtils;
import org.hyperic.sigar.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.havving.util.ExceptionUtils.printExceptionLog;

/**
 * Created by HAVVING on 2021-03-08.
 */
@SysCollectorService(name = "systemCollectorService")
public class SystemCollectorService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private Sigar sigar;
    private volatile Map<String, NetworkSysModel> prevNetwork = new ConcurrentHashMap<String, NetworkSysModel>();
    private volatile Map<String, DiskSysModel> prevDisk = new ConcurrentHashMap<String, DiskSysModel>();


    /**
     * get [HostName]
     * @return
     */
    public String getHostName() {
        String hostName = null;
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            hostName = localhost.getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return hostName != null ? hostName : "UnKnown";
    }


    /**
     * get [CPU] Information
     *
     * @return CpuSysModel
     */
    public SysModel getCpu() {
        if(validate()) {
            try {
                CpuSysModel cpuSysModel = new CpuSysModel(Constants.getConfig().host);
                CpuPerc cpuPerc = sigar.getCpuPerc();
                cpuSysModel.setIdle(Math.round(cpuPerc.getIdle() * 100 * 100d) / 100d);
                cpuSysModel.setSys(Math.round(cpuPerc.getSys() * 100 * 100d) / 100d);
                cpuSysModel.setUser(Math.round(cpuPerc.getUser() * 100 * 100d) / 100d);
                cpuSysModel.setWait(Math.round(cpuPerc.getWait() * 100 * 100d) / 100d);
                cpuSysModel.setTotal(cpuPerc.getCombined() * 100);
                cpuSysModel.setHostName(this.getHostName());

                try {
                    double[] loadAverage = sigar.getLoadAverage();
                    Map<Integer, Double> loadAverageMap = new HashMap<Integer, Double>(3);
                    loadAverageMap.put(0, loadAverage[0]);
                    loadAverageMap.put(1, loadAverage[1]);
                    loadAverageMap.put(2, loadAverage[2]);
                    cpuSysModel.setLoadAverage(loadAverageMap);
                } catch (Exception ignored) {
                    log.debug("This platform not support getLoadAverage() function.");
                }

                log.debug("{}", cpuSysModel);
                return cpuSysModel;

            } catch (SigarException e) {
                printExceptionLog(getClass(), e);
                return new ExceptionSysModel(Constants.getConfig().host, e);
            } catch (UnsatisfiedLinkError e) {  //
                printExceptionLog(getClass(), e);
                log.info("CPU Collector failed");
                return null;
            }

        } else
            return null;
    }


    /**
     * get [Disk] Information
     *
     * @return DiskSysModel[]
     */
    public SysModel[] getDisk() {
        if (validate()) {
            try {
                FileSystem[] fileSystemList = sigar.getFileSystemList();

                List<DiskSysModel> diskArray = new Vector<DiskSysModel>();
                for (FileSystem fileSystem : fileSystemList) {
                    String dirName = fileSystem.getDirName();
                    List<String> dirList = Constants.getConfig().systemCollect.dirList;
                    if (dirList.contains(dirName)) {
                        DiskSysModel diskSysModel = new DiskSysModel(Constants.getConfig().host);
                        log.debug("Dir name '{}' information get start.", dirName);
                        FileSystemUsage usage = sigar.getFileSystemUsage(dirName);

                        diskSysModel.setName(dirName);
                        diskSysModel.setReads(usage.getDiskReadBytes());  // KB / 1024
                        diskSysModel.setWrites(usage.getDiskWriteBytes());  // KB / 1024
                        diskSysModel.setQueue(Math.round(usage.getDiskQueue() * 100d) / 100d);  // I/O Operation amounts.
                        diskSysModel.setServiceTime(usage.getDiskServiceTime());
                        diskSysModel.setTotalBytes(usage.getTotal() * 1024);
                        diskSysModel.setFreeBytes(usage.getFree() * 1024);
                        diskSysModel.setUsedBytes(usage.getUsed() * 1024);
                        diskSysModel.setUsedPercent(usage.getUsePercent() * 100);
                        diskSysModel.setHostName(this.getHostName());

                        diskArray.add(diskSysModel);
                    } else {
                        log.debug("Dir name '{}' is not contains configured list '{}'.", dirName, dirList);
                    }
                }

                DiskSysModel[] diskSysModels = new DiskSysModel[diskArray.size()];
                for (int i = 0; i < diskArray.size(); i++) {
                    DiskSysModel curr = diskArray.get(i);
                    if (prevDisk != null && prevDisk.get(curr.getName()) != null) {
                        DiskSysModel prevDiskModel = prevDisk.get(curr.getName());
                        curr = (DiskSysModel) loadAvgByTimeMillis(prevDiskModel, curr);
                    }
                    diskSysModels[i] = curr;
                    if (prevDisk != null) {
                        prevDisk.put(curr.getName(), curr);
                    }
                    log.debug("{}", curr);
                }

                return diskSysModels;

            } catch (SigarException e) {
                printExceptionLog(getClass(), e);
                return new ExceptionSysModel[] {
                        new ExceptionSysModel(Constants.getConfig().host, e)
                };
            } catch (UnsatisfiedLinkError e) {
                printExceptionLog(getClass(), e);
                log.info("Disk collect failed.");
                return new ExceptionSysModel[]{};
            }
        } else
            return null;
    }


    /**
     *
     * @return
     */
    public SysModel[] getNetworks() {
        if (validate()) {
            Vector<String> ipAddressList = Constants.getConfig().systemCollect.ipAddressList;
            SysModel[] result = new NetworkSysModel[ipAddressList.size()];
            for (int i = 0; i < ipAddressList.size(); i++) {
                String netInterface = ipAddressList.get(i);
                SysModel network = getNetwork(netInterface);
                result[i] = network;
            }
            return result;
        } else
            return new SysModel[]{};
    }


    /**
     *
     * @param ipAddressName
     * @return
     */
    public SysModel getNetwork(String ipAddressName) {
        if (validate()) {
            try {
                NetworkSysModel networkSysModel = new NetworkSysModel(Constants.getConfig().host);
                String[] netInterfaces = sigar.getNetInterfaceList();
                for (String netInterface : netInterfaces) {
                    NetInterfaceConfig conf = sigar.getNetInterfaceConfig(netInterface);
                    if (conf.getAddress().equals(ipAddressName)) {
                        log.debug("Ethernet name '{}' information get start.", conf.getName());
                        // NetInterfaceConfig
                        networkSysModel.setBroadCast(conf.getBroadcast());
                        networkSysModel.setMacAddr(conf.getHwaddr());
                        networkSysModel.setMetric(conf.getMetric());
                        networkSysModel.setHost(this.getHostName());
                        networkSysModel.setName(conf.getName());
                        networkSysModel.setMtu(conf.getMtu());
                        networkSysModel.setNetMask(conf.getNetmask());
                        networkSysModel.setNetIpAddr(conf.getAddress());

                        // NetInterfaceStat
                        NetInterfaceStat stat = sigar.getNetInterfaceStat(netInterface);
                        networkSysModel.setSpeed(stat.getSpeed());

                        // Receive packet data
                        networkSysModel.setRxPackets(stat.getRxPackets());
                        networkSysModel.setRxDropped(stat.getRxDropped());
                        networkSysModel.setRxErrors(stat.getRxErrors());
                        networkSysModel.setRxBytes(stat.getRxBytes());

                        // Transmit packet data
                        networkSysModel.setTxPackets(stat.getTxPackets());
                        networkSysModel.setTxDropped(stat.getTxDropped());
                        networkSysModel.setTxErrors(stat.getTxErrors());
                        networkSysModel.setTxBytes(stat.getTxBytes());
                    }
                }

                log.debug("{}", networkSysModel);
                if (prevNetwork != null && prevNetwork.get(networkSysModel.getName()) != null) {
                    networkSysModel = (NetworkSysModel) loadAvgByTimeMillis(prevNetwork.get(networkSysModel.getName()), networkSysModel);
                }
                prevNetwork.put(networkSysModel.getName(), networkSysModel);

                return networkSysModel;

            } catch (SigarException e) {
                printExceptionLog(getClass(), e);
                return new ExceptionSysModel(Constants.getConfig().host, e);
            }
        } else
            return null;
    }


    /**
     * get [Memory] Information
     *
     * @return MemorySysModel
     */
    public SysModel getMemory() {
        if (validate()) {
            MemorySysModel memorySysModel = new MemorySysModel(Constants.getConfig().host);
            try {
                Mem mem = sigar.getMem();
                Swap swap = sigar.getSwap();
                memorySysModel.setSwapFree(swap.getFree() / 1024 / 1024);
                memorySysModel.setSwapTotal(swap.getTotal() / 1024 / 1024);
                memorySysModel.setSwapUsed(swap.getUsed() / 1024 / 1024);
                memorySysModel.setSwapPageIn(swap.getPageIn() / 1024 / 1024);
                memorySysModel.setSwapPageOut(swap.getPageOut() / 1024 / 1024);
                memorySysModel.setActualFree(mem.getActualFree() / 1024 / 1024);
                memorySysModel.setActualUsed(mem.getActualUsed() / 1024 / 1024);
                memorySysModel.setFreePercent(Math.round(mem.getFreePercent() * 100d) / 100d);
                memorySysModel.setUsedPercent(Math.round(mem.getUsedPercent() * 100d) / 100d);
                memorySysModel.setRam(mem.getRam() / 1024 / 1024);
                memorySysModel.setTotal(mem.getTotal() / 1024 / 1024);
                memorySysModel.setHostName(this.getHostName());

                String hostName = null;
                try {
                    InetAddress localhost = InetAddress.getLocalHost();
                    hostName = localhost.getHostName();
                } catch (UnknownHostException e) {
                    log.error("", e);
                }
                memorySysModel.setHostName(hostName != null ? hostName : "UnKnown");
                log.debug("{}", memorySysModel);

                return memorySysModel;

            } catch (SigarException e) {
                printExceptionLog(getClass(), e);
                return new ExceptionSysModel(Constants.getConfig().host, e);
            } catch (UnsatisfiedLinkError e) {
                printExceptionLog(getClass(), e);
                log.info("Memory collect failed.");
                return null;
            }
        } else
            return null;
    }


    /**
     * get [Process] Information
     *
     * @return
     */
    public SysModel[] getProcesses() {
        if (validate()) {
            List<SysModel> resultList = new ArrayList<SysModel>();
            try {
                for (long pid : sigar.getProcList()) {
                    if (validProcess(pid)) {
                        SysModel result = getProcess(pid);
                        resultList.add(result);
                    }
                }
                return resultList.toArray(new SysModel[resultList.size()]);

            } catch (SigarException e) {
                printExceptionLog(getClass(), e);
                return new ExceptionSysModel[] { new ExceptionSysModel(Constants.getConfig().host, e)};
            } catch (UnsatisfiedLinkError e) {
                printExceptionLog(getClass(), e);
                log.info("Process collect failed.");
                return new SysModel[]{};
            }
        } else
            return new SysModel[]{};
    }


    /**
     * get [Process] Information
     *
     * @param pid
     * @return
     */
    private SysModel getProcess(long pid) throws SigarException {
        ProcessSysModel processSysModel = new ProcessSysModel(Constants.getConfig().host);
        NetInfo info = sigar.getNetInfo();
        processSysModel.setHostName(info.getHostName());

        // Process common information
        ProcState pst = sigar.getProcState(pid);
        processSysModel.setPid(pid);
        processSysModel.setPpid(pst.getPpid());
        processSysModel.setName(pst.getName());
        processSysModel.setPriority(pst.getPriority());
        processSysModel.setState(pst.getState());
        processSysModel.setThreads(pst.getThreads());
        processSysModel.setHostName(this.getHostName());

        try {
            ProcCpu procCpu = sigar.getProcCpu(pid);
            processSysModel.setCpuUsage(((int) ((procCpu.getPercent() / Runtime.getRuntime().availableProcessors()) * 100 * 100)) / 100d);
        } catch (SigarException ignored) {}

        // Process memory information
        ProcMem procMem = sigar.getProcMem(pid);
        processSysModel.setMajorFaults(procMem.getMajorFaults());
        processSysModel.setMinorFaults(procMem.getMinorFaults());
        processSysModel.setPageFaults(procMem.getPageFaults());
        processSysModel.setShare(processSysModel.getShare());
        processSysModel.setSize(processSysModel.getSize());

        long residentMb = procMem.getResident() / 1024 / 1024;
        long totalMb = sigar.getMem().getTotal() / 1024 / 1024;
        processSysModel.setMemoryUsageMb(residentMb);
        processSysModel.setMemoryUsage(Math.round(((double) residentMb / totalMb) * 10000d) / 100d);

        // Process argument information
        try {
            String[] procArgs = sigar.getProcArgs(pid);
            String args = "";
            if (procArgs.length == 1) {
                procArgs = procArgs[0].split(" ");
            }

            for (String arg : procArgs) {
                args = args.concat(arg.replaceAll("\\\\", "/").concat(" "));
            }
            processSysModel.setArgs(args.trim());

            if (processSysModel.getArgs().contains("-Dname")) {
                processSysModel.setDname(StringUtils.substringBetween(processSysModel.getArgs(), "-Dname=", " "));
            }
        } catch (SigarException ignored) {}

        log.debug("{}", processSysModel);

        return processSysModel;
    }


    /**
     * 프로세스가 실제로 존재하는지 확인
     * @param pid
     * @return
     */
    private boolean validProcess(long pid) {
        try {
            String name = sigar.getProcState(pid).getName();
            String args = "";
            try {
                String[] procArgs = sigar.getProcArgs(pid);
                for (String arg : procArgs) {
                    args = arg.concat(" ");
                }
                args = args.trim();
            } catch (SigarException ignored) { }

            if (Constants.getConfig().containsLookupProcess(name) || Constants.getConfig().containsLookupProcess(args)) {
                return true;
            }

        } catch (SigarException e) {
            log.debug("{} process information get failed.", pid);
        }

        return false;
    }


    /**
     *
     * @param prev
     * @param curr
     * @return
     */
    private SysModel loadAvgByTimeMillis(final SysModel prev, SysModel curr) {
        SysModel result = null;
        long duration = Math.round((curr.getTime()) - prev.getTime()) / 1000;
        if (duration > 0) {
            if (prev instanceof DiskSysModel && curr instanceof DiskSysModel) {
                DiskSysModel beforeObj = (DiskSysModel) prev;
                DiskSysModel afterObj = (DiskSysModel) curr;
                long readsBytesPerSec = (afterObj.getReads() - beforeObj.getReads() < 0 ? 0 : afterObj.getReads() - beforeObj.getReads()) / duration;
                long writesBytesPerSec = (afterObj.getWrites() - beforeObj.getWrites() < 0 ? 0 : afterObj.getWrites() - beforeObj.getWrites()) / duration;
                afterObj.setReadLiveBytes(readsBytesPerSec);
                afterObj.setWriteLiveBytes(writesBytesPerSec);
                result = afterObj;
            } else if (prev instanceof NetworkSysModel && curr instanceof NetworkSysModel) {
                NetworkSysModel beforeObj = (NetworkSysModel) prev;
                NetworkSysModel afterObj = (NetworkSysModel) curr;
                long rxBytesPerSec = (afterObj.getRxBytes() - beforeObj.getRxBytes() < 0 ? 0 : afterObj.getRxBytes() - beforeObj.getRxBytes()) / duration;
                long txBytesPerSec = (afterObj.getTxBytes() - beforeObj.getTxBytes() < 0 ? 0 : afterObj.getTxBytes() - beforeObj.getTxBytes()) / duration;
                afterObj.setRxLiveBytes(rxBytesPerSec);
                afterObj.setTxLiveBytes(txBytesPerSec);
                result = afterObj;
            } else
                log.warn("SysModel before and after is not a valid object.");
        }

        return result;
    }


    /**
     * Verify [Sigar] is initialized
     *
     * @return boolean
     */
    private boolean validate() {
        if (sigar == null)
            log.error("Sigar not initialized.");

        return sigar != null;
    }


    /**
     * set Sigar
     * @param sigar
     */
    public void setSigar(Sigar sigar) {
        this.sigar = sigar;
    }


    /**
     *
     * @return
     */
    public final JsonResponse<GeneralSysModel> getTotalJson() {
        GeneralSysModel generalSysModel = new GeneralSysModel(Constants.getConfig().host);
        if (validate()) {
            generalSysModel.setCpu((CpuSysModel) getCpu());
            generalSysModel.setDisk((DiskSysModel[]) getDisk());

            JsonResponse<GeneralSysModel> result = new JsonResponse<GeneralSysModel>(StatusCode.OK, generalSysModel);
            log.debug("Response : {}", result);

            return result;
        } else {
            log.debug("Validation Error. Server Internal Error send.");
            return new JsonResponse<GeneralSysModel>(StatusCode.SERVER_ERROR, generalSysModel);
        }
    }
}
