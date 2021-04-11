package com.havving.system.service.batch;

import com.havving.system.domain.SysModel;
import com.havving.system.global.Constants;
import com.havving.system.global.ORMConnection;
import com.havving.system.service.StoreService;
import com.havving.system.service.SystemCollectorService;
import com.havving.system.service.store.EsStoreService;
import com.havving.util.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by HAVVING on 2021-03-19.
 */
@Slf4j
public class SysCollectorBatch extends BatchExecutor {

    public SysCollectorBatch(ORMConnection ormConnection) {
        super(ormConnection);
    }

    @Override
    public void execute() throws SQLException {
        SystemCollectorService systemCollectorService = Constants.getInstance().getSystemCollector();
        systemCollectorService.setSigar(Constants.getInstance().getSigar());

        SysModel cpu = null, memory = null;
        SysModel[] disks = null, processes = null, networks = null, engineStats = null;

        if (Constants.getConfig().systemCollect.cpuEnable) {
            try {
                cpu = systemCollectorService.getCpu();
            } catch (Exception e) {
                log.info("CPU information collect failed.");
                ExceptionUtils.printExceptionLog(getClass(), e);
            }
        }

        if (Constants.getConfig().systemCollect.memoryEnable) {
            try {
                memory = systemCollectorService.getMemory();
            } catch (Exception e) {
                log.info("Memory information collect failed.");
                ExceptionUtils.printExceptionLog(getClass(), e);
            }
        }

        if (Constants.getConfig().systemCollect.diskEnable) {
            try {
                disks = systemCollectorService.getDisk();
            } catch (Exception e) {
                log.info("Disk information collect failed.");
                ExceptionUtils.printExceptionLog(getClass(), e);
            }
        }

        if (Constants.getConfig().process != null && Constants.getConfig().process.size() > 0) {
            try {
                processes = systemCollectorService.getProcesses();
            } catch (Exception e) {
                log.info("Process information collect failed.");
                ExceptionUtils.printExceptionLog(getClass(), e);
            }
        }

        if (Constants.getConfig().systemCollect.networkEnable) {
            try {
                networks = systemCollectorService.getNetworks();
            } catch (Exception e) {
                log.info("Network information collect failed.");
                ExceptionUtils.printExceptionLog(getClass(), e);
            }
        }

        // ES Store
        StoreService esCollectorService = Constants.getInstance().getStoreCollector();
        if (Constants.getConfig().systemCollect.engineEnable) {
            // TODO getEngineStats
            engineStats = ((EsStoreService) esCollectorService).getEngineStats();
        }

        // JPA Store
        StoreService jpaStoreService = Constants.getInstance().getStoreCollector();

        List<SysModel> models = new ArrayList<SysModel>();
        if (cpu != null) models.add(cpu);
        if (memory != null) models.add(memory);
        if (disks != null && disks.length > 0) Collections.addAll(models, disks);
        if (processes != null && processes.length > 0) Collections.addAll(models, processes);
        if (networks != null && networks.length > 0) Collections.addAll(models, networks);
        if (engineStats != null && engineStats.length > 0) {
            for (SysModel stats : engineStats) {
                if (stats != null) models.add(stats);
            }
        }

        if (models.size() > 0) {
            jpaStoreService.store(models);
            log.info("{} columns updated.", models.size());
        } else log.info("Collection data size '0'. Information does not be sent.");
    }

}
