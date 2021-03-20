package com.havving.system.service.store;

import com.havving.system.domain.SysModel;
import com.havving.system.domain.impl.CpuSysModel;
import com.havving.system.domain.jpa.Cpu;
import com.havving.system.service.StoreService;
import lombok.SneakyThrows;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by HAVVING on 2021-03-20.
 */
public class JpaStoreService implements StoreService {
    private final Logger log = LoggerFactory.getLogger(JpaStoreService.class);
    private SessionFactory client;

    @Override
    public void store(List<SysModel> models) {
        for (SysModel m : models) {
            store(m);
        }
    }

    @SneakyThrows
    @Override
    public void store(SysModel model) {
        // TODO save model
        log.debug("Jpa Storing Object: {}", model);
        try (Session sess = client.openSession()) {
            sess.beginTransaction();
            switch (model.getType()) {
                case "cpu":
                    CpuSysModel c = (CpuSysModel) model;
                    Cpu cpu = new Cpu();
                    break;

                case "memory":
                    break;

                case "network":
                    break;

                case "process":
                    break;

                case "disk":
                    break;

                case "exception":
                    break;

                default:
                    throw new IllegalAccessException("Sysmodel type is not matched.");
            }
            sess.getTransaction().commit();
        }
    }

    @Override
    public void setClient(Object client) {
            this.client = (SessionFactory) client;
    }
}
