package com.havving.system.domain.impl;

import com.havving.system.domain.SysModel;
import org.joda.time.DateTime;

/**
 * Created by HAVVING on 2021-03-03.
 */
public class MemorySysModel implements SysModel {
    private final String type = "memory";
    private String host;
    private String hostName;
    private long time;
    private long actualUsed;
    private long actualFree;
    private double usedPercent;
    private double freePercent;
    private long ram;
    private long total;
    private long swapFree;
    private long swapUsed;
    private long swapTotal;
    private long swapPageIn;
    private long swapPageOut;

    public MemorySysModel(String hostName) {
        this.host = hostName;
        this.time = new DateTime().getMillis();
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

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getActualUsed() {
        return this.actualUsed;
    }

    public void setActualUsed(long actualUsed) {
        this.actualUsed = actualUsed;
    }

    public long getActualFree() {
        return this.actualFree;
    }

    public void setActualFree(long actualFree) {
        this.actualFree = actualFree;
    }

    public double getUsedPercent() {
        return this.usedPercent;
    }

    public void setUsedPercent(double usedPercent) {
        this.usedPercent = usedPercent;
    }

    public double getFreePercent() {
        return this.freePercent;
    }

    public void setFreePercent(double freePercent) {
        this.freePercent = freePercent;
    }

    public long getRam() {
        return this.ram;
    }

    public void setRam(long ram) {
        this.ram = ram;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getSwapFree() {
        return this.swapFree;
    }

    public void setSwapFree(long swapFree) {
        this.swapFree = swapFree;
    }

    public long getSwapUsed() {
        return this.swapUsed;
    }

    public void setSwapUsed(long swapUsed) {
        this.swapUsed = swapUsed;
    }

    public long getSwapTotal() {
        return this.swapTotal;
    }

    public void setSwapTotal(long swapTotal) {
        this.swapTotal = swapTotal;
    }

    public long getSwapPageIn() {
        return this.swapPageIn;
    }

    public void setSwapPageIn(long swapPageIn) {
        this.swapPageIn = swapPageIn;
    }

    public long getSwapPageOut() {
        return this.swapPageOut;
    }

    public void setSwapPageOut(long swapPageOut) {
        this.swapPageOut = swapPageOut;
    }

    @Override
    public String toString() {
        return "MemorySysModel{" +
                "type='" + type + '\'' +
                ", host='" + host + '\'' +
                ", hostName='" + hostName + '\'' +
                ", time=" + time +
                ", actualUsed=" + actualUsed +
                ", actualFree=" + actualFree +
                ", usedPercent=" + usedPercent +
                ", freePercent=" + freePercent +
                ", ram=" + ram +
                ", total=" + total +
                ", swapFree=" + swapFree +
                ", swapUsed=" + swapUsed +
                ", swapTotal=" + swapTotal +
                ", swapPageIn=" + swapPageIn +
                ", swapPageOut=" + swapPageOut +
                '}';
    }
}
