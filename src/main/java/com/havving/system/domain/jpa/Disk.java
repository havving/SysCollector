package com.havving.system.domain.jpa;

import com.havving.system.domain.JpaObject;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by HAVVING on 2021-03-21.
 */
@Entity
@Table(name = "SYS_DISK")
public class Disk implements Serializable, JpaObject {
    private static final long serialVersionUID = 445025753442713398L;

    @EmbeddedId
    private DiskId id;
    @Column(name = "QUEUE")
    private double queue;
    @Column(name = "READ_LIVE_BYTES")
    private long readLiveBytes;
    @Column(name = "READ_S")
    private long reads;
    @Column(name = "SERVICE_TIME")
    private double serviceTime;
    @Column(name = "WRITE_LIVE_BYTES")
    private long writeLiveBytes;
    @Column(name = "WRITES")
    private long writes;
    @Column(name = "TOTAL_BYTES")
    private long totalBytes;
    @Column(name = "FREE_BYTES")
    private long freeBytes;
    @Column(name = "USED_BYTES")
    private long usedBytes;
    @Column(name = "USED_PERCENT")
    private double usedPercent;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public DiskId getId() {
        return id;
    }

    public void setId(DiskId id) {
        this.id = id;
    }

    public double getQueue() {
        return queue;
    }

    public void setQueue(double queue) {
        this.queue = queue;
    }

    public long getReadLiveBytes() {
        return readLiveBytes;
    }

    public void setReadLiveBytes(long readLiveBytes) {
        this.readLiveBytes = readLiveBytes;
    }

    public long getReads() {
        return reads;
    }

    public void setReads(long reads) {
        this.reads = reads;
    }

    public double getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(double serviceTime) {
        this.serviceTime = serviceTime;
    }

    public long getWriteLiveBytes() {
        return writeLiveBytes;
    }

    public void setWriteLiveBytes(long writeLiveBytes) {
        this.writeLiveBytes = writeLiveBytes;
    }

    public long getWrites() {
        return writes;
    }

    public void setWrites(long writes) {
        this.writes = writes;
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


    @Embeddable
    @AttributeOverrides(value = {
            @AttributeOverride(name = "dateTime", column = @Column(name = "DATE_TIME")),
            @AttributeOverride(name = "hostName", column = @Column(name = "HOST_NAME"))
    })
    public static class DiskId extends SimpleId {
        private static final long serialVersionUID = 9067424113519874134L;

        @Column(name = "DISK_NAME")
        private String diskName;

        public DiskId() {
        }

        public DiskId(long dateTime, String hostName, String diskName) {
            super.setDateTime(dateTime);
            super.setHostName(hostName);
            this.diskName = diskName;
        }
    }

}
