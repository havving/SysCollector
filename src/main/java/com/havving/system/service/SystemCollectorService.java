package com.havving.system.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by HAVVING on 2021-03-08.
 */
@SysCollectorService(name = "systemCollectorService")
public class SystemCollectorService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public String getHostName() {
        String hostName = null;
        try {
            InetAddress localhost= InetAddress.getLocalHost();
            hostName = localhost.getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return hostName != null ? hostName : "UnKnown";
    }
}
