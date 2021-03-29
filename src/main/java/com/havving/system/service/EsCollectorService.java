package com.havving.system.service;

import com.havving.system.global.HttpProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by HAVVING on 2021-03-18.
 */
public class EsCollectorService {
    public HttpProvider httpProvider;

    public EsCollectorService(String ip, int port) {
        this.httpProvider = new HttpProvider(ip, port);
    }

}
