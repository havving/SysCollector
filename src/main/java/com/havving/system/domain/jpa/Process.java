package com.havving.system.domain.jpa;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by HAVVING on 2021-03-21.
 */
@Entity
@Table(name = "SYS_PROCESS")
public class Process implements Serializable {
    private static final long serialVersionUID = 2741545361044408392L;

    @EmbeddedId
    private ProcessId id;
    @Column(name = "ARGS")
    private String args;
    @Column(name = "CPU_USAGE")
    private double cpuUsage;
    @Column(name = "DNAME")
    private String dname;
    @Column(name = "MEMORY_USAGE")
    private double memoryUsage;
    @Column(name = "MEMORY_USAGE_MB")
    private long memoryUsageMb;
    @Column(name = "PPID")
    private long ppid;
    @Column(name = "PRIORITY")
    private long priority;
    @Column(name = "PROCESS_NAME")
    private String processName;
    @Column(name = "STATE")
    private String state;
    @Column(name = "THREADS")
    private long threads;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public ProcessId getId() {
        return id;
    }

    public void setId(ProcessId id) {
        this.id = id;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public double getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public double getMemoryUsage() {
        return memoryUsage;
    }

    public void setMemoryUsage(double memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    public long getMemoryUsageMb() {
        return memoryUsageMb;
    }

    public void setMemoryUsageMb(long memoryUsageMb) {
        this.memoryUsageMb = memoryUsageMb;
    }

    public long getPpid() {
        return ppid;
    }

    public void setPpid(long ppid) {
        this.ppid = ppid;
    }

    public long getPriority() {
        return priority;
    }

    public void setPriority(long priority) {
        this.priority = priority;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getThreads() {
        return threads;
    }

    public void setThreads(long threads) {
        this.threads = threads;
    }


    @Embeddable
    @AttributeOverrides(value = {
            @AttributeOverride(name = "dateTime", column = @Column(name = "DATE_TIME")),
            @AttributeOverride(name = "hostName", column = @Column(name = "HOST_NAME"))
    })
    public static class ProcessId extends SimpleId {
        private static final long serialVersionUID = 4984472657889800963L;

        @Column(name = "PID")
        private long pid;

        public ProcessId() {}

        public ProcessId(LocalDateTime dateTime, String hostName, long pid) {
            super.setDateTime(dateTime);
            super.setHostName(hostName);
            this.pid = pid;
        }
    }
}
