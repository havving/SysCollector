package com.havving.system.domain.impl;

import com.havving.system.domain.SysModel;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

/**
 * Created by HAVVING on 2021-04-12.
 */
public class EsStats implements SysModel {
    private static final String type = "es";

    private final String host;
    private final long time;

    @Getter @Setter
    private String nodeId;
    @Getter @Setter
    private String nodeIp;
    @Getter @Setter
    private String nodeInfo;
    @Getter @Setter
    private String name;
    private String hostName;

    public EsStats(String host) {
        this.host = host;
        this.time = new DateTime().getMillis();
    }

    @Override
    public String getHost() {
        return null;
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public long getTime() {
        return time;
    }

    @Override
    public String getHostName() {
        return this.hostName;
    }

    @Override
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
}
