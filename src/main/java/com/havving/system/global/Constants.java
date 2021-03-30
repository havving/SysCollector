package com.havving.system.global;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.havving.system.domain.xml.*;
import com.havving.system.service.EsCollectorService;
import com.havving.system.service.StoreService;
import com.havving.system.service.SystemCollectorService;
import com.havving.system.service.store.EsRestStoreService;
import com.havving.system.service.store.JpaStoreService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarProxyCache;
import org.reflections.Reflections;

import javax.persistence.Entity;
import java.util.Set;
import java.util.UUID;

/**
 * Created by HAVVING on 2021-03-09.
 */
@Slf4j
public class Constants {
    private static final Constants constants = new Constants();
    private final Settings.Builder clientSet = Settings.builder()
            .put("action.auto_create_index", true)
            .put("client.transport.sniff", true)
            .put("client.transport.ping_timeout", "60s")
            .put("network.tcp.block", true)
            .put("node.name", UUID.randomUUID());

    private Gson gson;
    private Sigar sigar;
    private Configs configs;
    private BatchConfig batchConfig;
    private StoreService storeCollector;
    private SystemCollectorService systemCollectorService;
    private Client collectionClient;

    @Getter
    @Setter
    private EsCollectorService esCollectorService;

    private Constants() {
    }


    /**
     * @return
     */
    public static Constants getInstance() {
        if (constants == null) {
            synchronized (Constants.class) {
                return constants;
            }
        } else return constants;
    }


    /**
     * syscollector.xml config
     * @return
     */
    public static Configs getConfig() {
        return getInstance().configs;
    }



    /**
     * batchConfig.xml config
     * @return
     */
    public static BatchConfig getBatchConfig() {
        return getInstance().batchConfig;
    }

    /**
     * Sigar, Gson, ES 초기화
     */
    public static synchronized void init() {
        log.trace("Global constants initialize start.");
        getInstance().sigar = new Sigar();
        SigarProxyCache.newInstance(getInstance().sigar);
        getInstance().gson = new GsonBuilder().disableHtmlEscaping().create();
        getInstance().systemCollectorService = new SystemCollectorService();

        Store store = getConfig().store;
        if (store instanceof EsStore) {
            getInstance().storeCollector = new EsRestStoreService();
        } else if (store instanceof JpaStore) {
            Configuration config = new Configuration().configure(((JpaStore) store).hibernatePath);
            Reflections ref = new Reflections("com.havving.system.domain.jpa");
            Set<Class<?>> entityClasses = ref.getTypesAnnotatedWith(Entity.class);
            for (Class<?> entity : entityClasses) {
                config.addAnnotatedClass(entity);
            }
            System.out.println(config.getProperties());
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(config.getProperties());
            SessionFactory factory = config.buildSessionFactory(builder.build());
            getInstance().storeCollector = new JpaStoreService();
            getInstance().storeCollector.setClient(factory);
        }

        if (getConfig().systemCollect.engineEnable) {
            getInstance().collectionClient = initEngineTransport(getConfig().systemCollect.engine);
        }

        if (getConfig().esCollect != null) {
            EsStore esStore = getConfig().esCollect;
            getInstance().esCollectorService = new EsCollectorService(esStore.masterIp, esStore.destinationPort);
        }
    }


    /**
     *
     * @param esStore
     * @return
     */
    private static Client initEngineTransport(EsStore esStore) {
        log.debug("Client connection info: {}, {}, {}", esStore.masterIp, esStore.clusterName, esStore.destinationPort);

        // ES connection set

/*        Client client = new TransportClient(
                getInstance().clientSet.put("cluster.name", esStore.clusterName).build()
        ).addTransportAddress(
                new InetSocketTransportAddress(esStore.masterIp, esStore.destinationPort)
        );*/

//        log.debug("Store client init method called. Engine client null is {}", client == null);

        return null;
    }


    /**
     * config setting
     */
    public static Configs setConfig(Configs config) {
        log.trace("Config injection {}", config);
        return getInstance().configs = config;
    }


    /**
     * get SystemCollectorService
     * @return
     */
    public SystemCollectorService getSystemCollector() {
        return systemCollectorService;
    }


    /**
     * get Sigar library
     * @return
     */
    public Sigar getSigar() {
        return sigar;
    }


    /**
     * get StoreService
     * @return
     */
    public StoreService getStoreCollector() {
        return storeCollector;
    }
}
