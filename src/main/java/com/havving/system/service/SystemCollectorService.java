package com.havving.system.service;

import com.havving.system.domain.SysModel;
import com.havving.system.domain.global.Constants;
import com.havving.system.domain.impl.CpuSysModel;
import com.havving.system.domain.impl.DiskSysModel;
import com.havving.system.domain.impl.ExceptionSysModel;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import static com.havving.util.ExceptionUtils.printExceptionLog;

/**
 * Created by HAVVING on 2021-03-08.
 */
@SysCollectorService(name = "systemCollectorService")
public class SystemCollectorService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private Sigar sigar;

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

                for (FileSystem fileSystem : fileSystemList) {
                    String dirName = fileSystem.getDirName();

                }

            } catch (SigarException e) {
                printExceptionLog(getClass(), e);
                return new ExceptionSysModel[] {
                        new ExceptionSysModel(Constants.getConfig().host, e)
                };
            }
        }

        return null;
    }

    private boolean validate() {
        if (sigar == null) log.error("Sigar not initialized.");
        return sigar != null;
    }
}
