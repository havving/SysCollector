package com.havving.system.service.store;

import com.havving.system.domain.SysModel;
import com.havving.system.service.StoreService;
import com.havving.system.service.SysCollectorService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.Client;

import java.util.List;

/**
 * Created by HAVVING on 2021-04-11.
 */
@Slf4j
@SysCollectorService(name = "esStoreService")
public class EsStoreService implements StoreService {
    private Client client;

    @Override
    public void store(List<SysModel> models) {

    }

    @Override
    public void store(SysModel model) {

    }

    @Override
    public void setClient(Object client) {

    }

    public SysModel[] getEngineStats() {

        return null;

    }
}
