package com.havving.system.domain.impl;

import com.havving.system.domain.SysModel;

import java.util.Date;
import java.util.Map;

/**
 * Created by HAVVING on 2021-03-03.
 */
public class CpuSysModel implements SysModel {
    private final String type = "cpu";
    private String host;
    private String hostName;
    private long time;
    private double idle;
    private double sys;
    private double user;
    private double wait;
    private Map<Integer, Double> loadAverage;
    private double total;

    public CpuSysModel(String hostName) {
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

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getIdle() {
        return this.idle;
    }

    public void setIdle(double idle) {
        this.idle = idle;
    }

    public double getSys() {
        return this.sys;
    }

    public void setSys(double sys) {
        this.sys = sys;
    }

    public double getUser() {
        return this.user;
    }

    public void setUser(double user) {
        this.user = user;
    }

    public double getWait() {
        return this.wait;
    }

    public void setWait(double wait) {
        this.wait = wait;
    }

    public void setTotal(final double total) {
        this.total = total;
    }

    public Map<Integer, Double> getLoadAverage() {
        return loadAverage;
    }

    public void setLoadAverage(Map<Integer, Double> loadAverage) {
        this.loadAverage = loadAverage;
    }

    @Override
    public String toString() {
        return "CpuSysModel{" +
                "type='" + type + '\'' +
                ", host='" + host + '\'' +
                ", hostName='" + hostName + '\'' +
                ", time=" + time +
                ", idle=" + idle +
                ", sys=" + sys +
                ", user=" + user +
                ", wait=" + wait +
                ", total=" + total +
                ", loadAverage=" + loadAverage +
                '}';
    }
}
