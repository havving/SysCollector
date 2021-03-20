package com.havving.system.domain.jpa;

import com.havving.system.domain.JpaObject;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by HAVVING on 2021-03-20.
 */
@Entity
@Table(name = "SYS_CPU")
public class Cpu implements Serializable, JpaObject {

    @EmbeddedId
    private SimpleId id;
    @Column(name = "IDLE")
    private double idle;
    @Column(name = "SYS")
    private double seys;
    @Column(name = "USR")
    private double usr;
    @Column(name = "WAIT")
    private double wait;
    @Column(name = "LOAD_AVERAGE_ZERO")
    private double loadAverageZero;
    @Column(name = "LOAD_AVERAGE_ONE")
    private double loadAverageOne;
    @Column(name = "LOAD_AVERAGE_TWO")
    private double loadAverageTwo;

    public SimpleId getId() {
        return id;
    }

    public void setId(SimpleId id) {
        this.id = id;
    }

    public double getIdle() {
        return idle;
    }

    public void setIdle(double idle) {
        this.idle = idle;
    }

    public double getSeys() {
        return seys;
    }

    public void setSeys(double seys) {
        this.seys = seys;
    }

    public double getUsr() {
        return usr;
    }

    public void setUsr(double usr) {
        this.usr = usr;
    }

    public double getWait() {
        return wait;
    }

    public void setWait(double wait) {
        this.wait = wait;
    }

    public double getLoadAverageZero() {
        return loadAverageZero;
    }

    public void setLoadAverageZero(double loadAverageZero) {
        this.loadAverageZero = loadAverageZero;
    }

    public double getLoadAverageOne() {
        return loadAverageOne;
    }

    public void setLoadAverageOne(double loadAverageOne) {
        this.loadAverageOne = loadAverageOne;
    }

    public double getLoadAverageTwo() {
        return loadAverageTwo;
    }

    public void setLoadAverageTwo(double loadAverageTwo) {
        this.loadAverageTwo = loadAverageTwo;
    }
}
