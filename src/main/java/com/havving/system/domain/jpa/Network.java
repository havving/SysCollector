package com.havving.system.domain.jpa;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by HAVVING on 2021-03-21.
 */
@Entity
@Table(name = "SYS_NETWORK")
public class Network implements Serializable {
    private static final long serialVersionUID = 3417001903210736081L;

    @EmbeddedId
    private NetworkId id;
    @Column(name = "BROADCAST")
    private String broadcast;
    @Column(name = "MAC_ADDR")
    private String macAddr;
    @Column(name = "METRIC")
    private long metric;
    @Column(name = "MTU")
    private long mtu;
    @Column(name = "NETMASK")
    private String netmask;
    @Column(name = "NET_IP_ADDR")
    private String netIpAddr;
    @Column(name = "RX_BYTES")
    private long rxBytes;
    @Column(name = "RX_DROPPED")
    private long rxDropped;
    @Column(name = "RX_ERRORS")
    private long rxErrors;
    @Column(name = "RX_LIVE_BYTES")
    private long rxLiveBytes;
    @Column(name = "RX_PACKETS")
    private long rxPackets;
    @Column(name = "SPEED")
    private long speed;
    @Column(name = "TX_BYTES")
    private long txBytes;
    @Column(name = "TX_DROPPED")
    private long txDropped;
    @Column(name = "TX_ERRORS")
    private long txErrors;
    @Column(name = "TX_LIVE_BYTES")
    private long txLiveBytes;
    @Column(name = "TX_PACKETS")
    private long txPackets;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public NetworkId getId() {
        return id;
    }

    public void setId(NetworkId id) {
        this.id = id;
    }

    public String getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(String broadcast) {
        this.broadcast = broadcast;
    }

    public String getMacAddr() {
        return macAddr;
    }

    public void setMacAddr(String macAddr) {
        this.macAddr = macAddr;
    }

    public long getMetric() {
        return metric;
    }

    public void setMetric(long metric) {
        this.metric = metric;
    }

    public long getMtu() {
        return mtu;
    }

    public void setMtu(long mtu) {
        this.mtu = mtu;
    }

    public String getNetmask() {
        return netmask;
    }

    public void setNetmask(String netmask) {
        this.netmask = netmask;
    }

    public String getNetIpAddr() {
        return netIpAddr;
    }

    public void setNetIpAddr(String netIpAddr) {
        this.netIpAddr = netIpAddr;
    }

    public long getRxBytes() {
        return rxBytes;
    }

    public void setRxBytes(long rxBytes) {
        this.rxBytes = rxBytes;
    }

    public long getRxDropped() {
        return rxDropped;
    }

    public void setRxDropped(long rxDropped) {
        this.rxDropped = rxDropped;
    }

    public long getRxErrors() {
        return rxErrors;
    }

    public void setRxErrors(long rxErrors) {
        this.rxErrors = rxErrors;
    }

    public long getRxLiveBytes() {
        return rxLiveBytes;
    }

    public void setRxLiveBytes(long rxLiveBytes) {
        this.rxLiveBytes = rxLiveBytes;
    }

    public long getRxPackets() {
        return rxPackets;
    }

    public void setRxPackets(long rxPackets) {
        this.rxPackets = rxPackets;
    }

    public long getSpeed() {
        return speed;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public long getTxBytes() {
        return txBytes;
    }

    public void setTxBytes(long txBytes) {
        this.txBytes = txBytes;
    }

    public long getTxDropped() {
        return txDropped;
    }

    public void setTxDropped(long txDropped) {
        this.txDropped = txDropped;
    }

    public long getTxErrors() {
        return txErrors;
    }

    public void setTxErrors(long txErrors) {
        this.txErrors = txErrors;
    }

    public long getTxLiveBytes() {
        return txLiveBytes;
    }

    public void setTxLiveBytes(long txLiveBytes) {
        this.txLiveBytes = txLiveBytes;
    }

    public long getTxPackets() {
        return txPackets;
    }

    public void setTxPackets(long txPackets) {
        this.txPackets = txPackets;
    }


    @Embeddable
    @AttributeOverrides(value = {
            @AttributeOverride(name = "dateTime", column = @Column(name = "DATE_TIME")),
            @AttributeOverride(name = "hostName", column = @Column(name = "HOST_NAME"))
    })
    public static class NetworkId extends SimpleId {
        private static final long serialVersionUID = 5138520836615418925L;

        @Column(name = "NIC_NAME")
        private String nicName;

        public NetworkId() {}

        public NetworkId(long dateTime, String hostName, String nicName) {
            super.setDateTime(dateTime);
            super.setHostName(hostName);
            this.nicName = nicName;
        }
    }
}
