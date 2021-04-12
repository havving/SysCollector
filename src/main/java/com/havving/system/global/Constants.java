package com.havving.system.global;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.havving.system.domain.impl.ProcessSysModel;
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
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.SigarProxyCache;
import org.reflections.Reflections;

import javax.persistence.Entity;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import static com.havving.util.ExceptionUtils.printExceptionLog;

/**
 * Created by HAVVING on 2021-03-09.
 */
@Slf4j
public class Constants {
    private static final Constants constants = new Constants();
    private static final ForkJoinPool taskRunner = new ForkJoinPool(Runtime.getRuntime().availableProcessors() / 2);
    private static Map<Long, ProcessSysModel> lookupProcess = new ConcurrentHashMap<>();
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

         Store store = getConfig().store.get(0);
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
     * config setting
     */
    public static BatchConfig setBatchConfig(BatchConfig batchConfig) {
        log.trace("Config injection {}", batchConfig);
        return getInstance().batchConfig = batchConfig;
    }

    public static void addLookups(ProcessSysModel model) {
        log.debug("Process catch. {}", model.getPid());
        log.trace("Process will be looked up. Process Information : {}", model);
        lookupProcess.put(model.getPid(), model);
    }

    public static void initLookup() {
        final Collection<ProcessSysModel> lookupValues = lookupProcess.values();
        Integer taskKilled = taskRunner.invoke(new RecursiveTask<Integer>() {
            @Override
            protected Integer compute() {
                log.trace("Process Lookup Start... {}", lookupValues.size());
                int kills = 0;
                for (ProcessSysModel p : lookupValues) {
                    if (p.getCpuUsage() >= Constants.getConfig().getThresholdByPid(p.getPid())) {
                        try {
                            log.info("Process Kill. pid : {}, load : {}, data : {}", p.getCpuUsage(), p.getPid(), p);
                            getInstance().sigar.kill(p.getPid(), 9);
                            kills++;
                        } catch (SigarException e) {
                            printExceptionLog(getClass(), e);
                        }
                    }
                }
                return kills;
            }
        });
        log.info("Process Kill complete. {} processes has been killed.", taskKilled);
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
