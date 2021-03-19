package com.havving.system.service;

import com.havving.system.domain.SysModel;

import java.util.List;

/**
 * Created by HAVVING on 2021-03-19.
 */
public interface StoreService {
    void store(List<SysModel> models);

    void store(SysModel model);

    void setClient(Object client);
}
