package com.havving.system.domain.impl;

import com.havving.system.domain.SysModel;

import java.util.Date;

/**
 * Created by HAVVING on 2021-03-03.
 */
public class DiskSysModel implements SysModel {
    private final String type = "disk";
    private String host;
    private String hostName;
    private String name;
    private long time;
    private long reads;
    private long readLiveBytes;
    private long writes;
    private long writeLiveBytes;
    private double queue;
    private double serviceTime;
    private long totalBytes;
    private long freeBytes;
    private long usedBytes;
    private double usedPercent;

    public DiskSysModel(String hostName) {
        this.host = hostName;
        this.time = new Date().getTime();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getReads() {
        return this.reads;
    }

    public void setReads(long reads) {
        this.reads = reads;
    }

    public long getWrites() {
        return this.writes;
    }

    public void setWrites(long writes) {
        this.writes = writes;
    }

    public double getQueue() {
        return this.queue;
    }

    public void setQueue(double queue) {
        this.queue = queue;
    }

    public double getServiceTime() {
        return this.serviceTime;
    }

    public void setServiceTime(double serviceTime) {
        this.serviceTime = serviceTime;
    }

    public long getReadLiveBytes() {
        return readLiveBytes;
    }

    public void setReadLiveBytes(long readLiveBytes) {
        this.readLiveBytes = readLiveBytes;
    }

    public long getWriteLiveBytes() {
        return writeLiveBytes;
    }

    public void setWriteLiveBytes(long writeLiveBytes) {
        this.writeLiveBytes = writeLiveBytes;
    }

    public long getTotalBytes() {
        return totalBytes;
    }

    public void setTotalBytes(long totalBytes) {
        this.totalBytes = totalBytes;
    }

    public long getFreeBytes() {
        return freeBytes;
    }

    public void setFreeBytes(long freeBytes) {
        this.freeBytes = freeBytes;
    }

    public long getUsedBytes() {
        return usedBytes;
    }

    public void setUsedBytes(long usedBytes) {
        this.usedBytes = usedBytes;
    }

    public double getUsedPercent() {
        return usedPercent;
    }

    public void setUsedPercent(double usedPercent) {
        this.usedPercent = usedPercent;
    }

    @Override
    public String toString() {
        return "DiskSysModel{" +
                "type='" + type + '\'' +
                ", host='" + host + '\'' +
                ", hostName='" + hostName + '\'' +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", reads=" + reads +
                ", readLiveBytes=" + readLiveBytes +
                ", writes=" + writes +
                ", writeLiveBytes=" + writeLiveBytes +
                ", queue=" + queue +
                ", serviceTime=" + serviceTime +
                ", totalBytes=" + totalBytes +
                ", freeBytes=" + freeBytes +
                ", usedBytes=" + usedBytes +
                ", usedPercent=" + usedPercent +
                '}';
    }
}
