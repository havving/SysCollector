package com.havving.system.domain;

/**
 * Created by HAVVING on 2021-03-09.
 */
public interface SysModel {
    String getHost();

    String getType();

    long getTime();

    String getHostName();

    void setHostName(String hostName);
}
