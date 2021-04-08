package com.havving.system.domain.jpa;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by HAVVING on 2021-03-21.
 */
@Entity
@Table(name = "SYS_MEMORY")
public class Memory implements Serializable {
    private static final long serialVersionUID = 8549024083984251365L;

    @EmbeddedId
    private SimpleId id;
    @Column(name = "ACTUAL_FREE")
    private long actualFree;
    @Column(name = "ACTUAL_USED")
    private long actualUsed;
    @Column(name = "FREE_PERCENT")
    private double freePercent;
    @Column(name = "USED_PERCENT")
    private double usedPercent;
    @Column(name = "RAM")
    private long ram;
    @Column(name = "SWAP_FREE")
    private long swapFree;
    @Column(name = "SWAP_PAGE_IN")
    private long swapPageIn;
    @Column(name = "SWAP_PAGE_OUT")
    private long swapPageOut;
    @Column(name = "SWAP_TOTAL")
    private long swapTotal;
    @Column(name = "SWAP_USED")
    private long swapUsed;
    @Column(name = "TOTAL")
    private long total;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public SimpleId getId() {
        return id;
    }

    public void setId(SimpleId id) {
        this.id = id;
    }

    public long getActualFree() {
        return actualFree;
    }

    public void setActualFree(long actualFree) {
        this.actualFree = actualFree;
    }

    public long getActualUsed() {
        return actualUsed;
    }

    public void setActualUsed(long actualUsed) {
        this.actualUsed = actualUsed;
    }

    public double getFreePercent() {
        return freePercent;
    }

    public void setFreePercent(double freePercent) {
        this.freePercent = freePercent;
    }

    public double getUsedPercent() {
        return usedPercent;
    }

    public void setUsedPercent(double usedPercent) {
        this.usedPercent = usedPercent;
    }

    public long getRam() {
        return ram;
    }

    public void setRam(long ram) {
        this.ram = ram;
    }

    public long getSwapFree() {
        return swapFree;
    }

    public void setSwapFree(long swapFree) {
        this.swapFree = swapFree;
    }

    public long getSwapPageIn() {
        return swapPageIn;
    }

    public void setSwapPageIn(long swapPageIn) {
        this.swapPageIn = swapPageIn;
    }

    public long getSwapPageOut() {
        return swapPageOut;
    }

    public void setSwapPageOut(long swapPageOut) {
        this.swapPageOut = swapPageOut;
    }

    public long getSwapTotal() {
        return swapTotal;
    }

    public void setSwapTotal(long swapTotal) {
        this.swapTotal = swapTotal;
    }

    public long getSwapUsed() {
        return swapUsed;
    }

    public void setSwapUsed(long swapUsed) {
        this.swapUsed = swapUsed;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
