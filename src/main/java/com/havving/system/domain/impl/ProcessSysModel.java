package com.havving.system.domain.impl;

import com.havving.system.domain.SysModel;
import org.joda.time.DateTime;

/**
 * Created by HAVVING on 2021-03-13.
 */
public class ProcessSysModel implements SysModel {
    private String type = "process";
    private String host;
    private String hostName;
    private long pid;
    private long ppid;
    private String name;
    private String dname;
    private String args = "";
    private double cpuUsage = 0d;
    private double memoryUsage = 0d;
    private long memoryUsageMb;
    private long majorFaults;
    private long minorFaults;
    private long pageFaults;
    private long share;
    private long size;
    private long threads;
    private char state;
    private int priority;
    private long time;

    public ProcessSysModel(String host) {
        this.host = host;
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
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getPid() {
        return this.pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public long getPpid() {
        return this.ppid;
    }

    public void setPpid(long ppid) {
        this.ppid = ppid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDname() {
        return this.dname;
    }

    public void setDname(String name) {
        this.dname = name;
    }

    public String getArgs() {
        return this.args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public double getCpuUsage() {
        return this.cpuUsage;
    }

    public void setCpuUsage(double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public double getMemoryUsage() {
        return this.memoryUsage;
    }

    public void setMemoryUsage(double memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    public long getMemoryUsageMb() {
        return this.memoryUsageMb;
    }

    public void setMemoryUsageMb(long memoryUsage) {
        this.memoryUsageMb = memoryUsage;
    }

    public long getThreads() {
        return this.threads;
    }

    public void setThreads(long threads) {
        this.threads = threads;
    }

    public char getState() {
        return this.state;
    }

    public void setState(char state) {
        this.state = state;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public long getMajorFaults() {
        return majorFaults;
    }

    public void setMajorFaults(long majorFaults) {
        this.majorFaults = majorFaults;
    }

    public long getMinorFaults() {
        return minorFaults;
    }

    public void setMinorFaults(long minorFaults) {
        this.minorFaults = minorFaults;
    }

    public long getPageFaults() {
        return pageFaults;
    }

    public void setPageFaults(long pageFaults) {
        this.pageFaults = pageFaults;
    }

    public long getShare() {
        return share;
    }

    public void setShare(long share) {
        this.share = share;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ProcessSysModel{" +
                "type='" + type + '\'' +
                ", host='" + host + '\'' +
                ", hostName='" + hostName + '\'' +
                ", pid=" + pid +
                ", ppid=" + ppid +
                ", name='" + name + '\'' +
                ", dname='" + dname + '\'' +
                ", args='" + args + '\'' +
                ", cpuUsage=" + cpuUsage +
                ", memoryUsage=" + memoryUsage +
                ", memoryUsageMb=" + memoryUsageMb +
                ", majorFaults=" + majorFaults +
                ", minorFaults=" + minorFaults +
                ", pageFaults=" + pageFaults +
                ", share=" + share +
                ", size=" + size +
                ", threads=" + threads +
                ", state=" + state +
                ", priority=" + priority +
                ", time=" + time +
                '}';
    }

}
