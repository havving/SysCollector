package com.havving.system.service.store;

import com.havving.system.domain.SysModel;
import com.havving.system.domain.xml.EsStore;
import com.havving.system.global.Constants;
import com.havving.system.global.HttpProvider;
import com.havving.system.service.StoreService;
import com.havving.system.service.SysCollectorService;

import java.util.List;

/**
 * Created by HAVVING on 2021-03-20.
 */
@SysCollectorService(name = "esRestStoreService")
public class EsRestStoreService implements StoreService {
    private HttpProvider client;
    private EsStore storeConfig;


    public EsRestStoreService() {
//        storeConfig = (EsStore) Constants.getConfig().store;
        client = new HttpProvider(storeConfig.masterIp, storeConfig.destinationPort);
    }

    @Override
    public void store(List<SysModel> models) {

    }

    @Override
    public void store(SysModel model) {

    }

    @Override
    public void setClient(Object client) {

    }
}
