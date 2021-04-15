package com.havving.system.service;

import com.havving.system.global.ConnectionFactory;
import com.havving.system.service.batch.BatchManager;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by HAVVING on 2021-04-13.
 */
@Slf4j
public class ServiceManager {

    public static void init(String[] args) {
        // TODO DB 설정 여부에 따른 init
//        if () {
            ConnectionFactory.getConnFactory().init();
//        }
        BatchManager.startBatch();
    }

}
