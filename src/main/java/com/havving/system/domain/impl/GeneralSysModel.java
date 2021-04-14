package com.havving.system.domain.impl;

import com.havving.system.domain.SysModel;
import org.joda.time.DateTime;

import java.util.Arrays;

/**
 * Created by HAVVING on 2021-03-03.
 */
public class GeneralSysModel implements SysModel {
    private final String type = "general";
    private String host;
    private String hostName;
    private CpuSysModel cpu;
    private MemorySysModel memory;
    private DiskSysModel[] disk;
    private NetworkSysModel[] network;

    public GeneralSysModel(String hostName) {
        this.host = hostName;
    }

    @Override
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String getHostName() {
        return hostName;
    }

    @Override
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public long getTime() {
        return new DateTime().getMillis();
    }

    public CpuSysModel getCpu() {
        return this.cpu;
    }

    public void setCpu(CpuSysModel cpu) {
        this.cpu = cpu;
    }

    public MemorySysModel getMemory() {
        return this.memory;
    }

    public void setMemory(MemorySysModel memory) {
        this.memory = memory;
    }

    public DiskSysModel[] getDisk() {
        return this.disk;
    }

    public void setDisk(DiskSysModel[] disk) {
        this.disk = disk;
    }

    public NetworkSysModel[] getNetwork() {
        return this.network;
    }

    public void setNetwork(NetworkSysModel[] network) {
        this.network = network;
    }

    @Override
    public String toString() {
        return "GeneralSysModel{" +
                "type='" + type + '\'' +
                ", host='" + host + '\'' +
                ", hostName='" + hostName + '\'' +
                ", cpu=" + cpu +
                ", memory=" + memory +
                ", disk=" + Arrays.toString(disk) +
                ", network=" + Arrays.toString(network) +
                '}';
    }
}
