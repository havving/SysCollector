package com.havving.system.service;

import com.havving.system.domain.JsonResponse;
import com.havving.system.domain.StatusCode;
import com.havving.system.domain.SysModel;
import com.havving.system.global.Constants;
import com.havving.system.domain.impl.*;
import org.hyperic.sigar.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
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
     *
     * @return
     */
    public String getHostName() {
        String hostName = null;
        try {
            InetAddress localhost= InetAddress.getLocalHost();
            hostName = localhost.getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return hostName != null ? hostName : "UnKnown";
    }

    /**
     *
     * @return
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
     *
     * @return
     */
    public SysModel[] getDisk() {
        if (validate()) {
            try {
                FileSystem[] fileSystemList = sigar.getFileSystemList();
                List<DiskSysModel> diskSysModelList = new Vector<DiskSysModel>();

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

        return null;
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
            } else log.warn("SysModel before and after is not a valid object.");
        }

        return result;
    }

    /**
     *
     * @return
     */
    private boolean validate() {
        if (sigar == null) log.error("Sigar not initialized.");
        return sigar != null;
    }

    /**
     *
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
