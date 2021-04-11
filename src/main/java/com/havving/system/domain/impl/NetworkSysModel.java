package com.havving.system.domain.impl;

import com.havving.system.domain.SysModel;
import org.joda.time.DateTime;

/**
 * Created by HAVVING on 2021-03-03.
 */
public class NetworkSysModel implements SysModel {
    private final String type = "network";
    private String host;
    private String hostName;
    private long time;
    //Network Configs
    private String broadCast;
    private String macAddr;
    private long metric;
    private String name;
    private String netMask;
    private String netIpAddr;
    private long mtu;
    //Network Stats
    private long speed;
    private long rxPackets;
    private long rxDropped;
    private long rxErrors;
    private long rxBytes;
    private long rxLiveBytes = 0;
    private long txPackets;
    private long txDropped;
    private long txErrors;
    private long txBytes;
    private long txLiveBytes = 0;

    public NetworkSysModel(String hostName) {
        this.host = hostName;
        this.time = new DateTime().getMillis();
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
        return type;
    }

    @Override
    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getBroadCast() {
        return this.broadCast;
    }

    public void setBroadCast(String broadCast) {
        this.broadCast = broadCast;
    }

    public String getMacAddr() {
        return this.macAddr;
    }

    public void setMacAddr(String macAddr) {
        this.macAddr = macAddr;
    }

    public long getMetric() {
        return this.metric;
    }

    public void setMetric(long metric) {
        this.metric = metric;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNetMask() {
        return this.netMask;
    }

    public void setNetMask(String netMask) {
        this.netMask = netMask;
    }

    public String getNetIpAddr() {
        return netIpAddr;
    }

    public void setNetIpAddr(String netIpAddr) {
        this.netIpAddr = netIpAddr;
    }

    public long getMtu() {
        return this.mtu;
    }

    public void setMtu(long mtu) {
        this.mtu = mtu;
    }

    public long getSpeed() {
        return this.speed;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public long getRxPackets() {
        return this.rxPackets;
    }

    public void setRxPackets(long rxPackats) {
        this.rxPackets = rxPackats;
    }

    public long getRxDropped() {
        return this.rxDropped;
    }

    public void setRxDropped(long rxDropped) {
        this.rxDropped = rxDropped;
    }

    public long getRxErrors() {
        return this.rxErrors;
    }

    public void setRxErrors(long rxErrors) {
        this.rxErrors = rxErrors;
    }

    public long getRxBytes() {
        return this.rxBytes;
    }

    public void setRxBytes(long rxBytes) {
        this.rxBytes = rxBytes;
    }

    public long getTxPackets() {
        return this.txPackets;
    }

    public void setTxPackets(long txPackats) {
        this.txPackets = txPackats;
    }

    public long getTxDropped() {
        return this.txDropped;
    }

    public void setTxDropped(long txDropped) {
        this.txDropped = txDropped;
    }

    public long getTxErrors() {
        return this.txErrors;
    }

    public void setTxErrors(long txErrors) {
        this.txErrors = txErrors;
    }

    public long getTxBytes() {
        return this.txBytes;
    }

    public void setTxBytes(long txBytes) {
        this.txBytes = txBytes;
    }

    public long getRxLiveBytes() {
        return rxLiveBytes;
    }

    public void setRxLiveBytes(long rxLiveBytes) {
        this.rxLiveBytes = rxLiveBytes;
    }

    public long getTxLiveBytes() {
        return txLiveBytes;
    }

    public void setTxLiveBytes(long txLiveBytes) {
        this.txLiveBytes = txLiveBytes;
    }

    @Override
    public String toString() {
        return "NetworkSysModel{" +
                "type='" + type + '\'' +
                ", host='" + host + '\'' +
                ", hostName='" + hostName + '\'' +
                ", time=" + time +
                ", broadCast='" + broadCast + '\'' +
                ", macAddr='" + macAddr + '\'' +
                ", metric=" + metric +
                ", name='" + name + '\'' +
                ", netMask='" + netMask + '\'' +
                ", netIpAddr='" + netIpAddr + '\'' +
                ", mtu=" + mtu +
                ", speed=" + speed +
                ", rxPackets=" + rxPackets +
                ", rxDropped=" + rxDropped +
                ", rxErrors=" + rxErrors +
                ", rxBytes=" + rxBytes +
                ", rxLiveBytes=" + rxLiveBytes +
                ", txPackets=" + txPackets +
                ", txDropped=" + txDropped +
                ", txErrors=" + txErrors +
                ", txBytes=" + txBytes +
                ", txLiveBytes=" + txLiveBytes +
                '}';
    }
}
