package com.havving.system.global;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.havving.system.domain.xml.Configs;
import com.havving.system.domain.xml.EsStore;
import com.havving.system.service.EsCollectorService;
import com.havving.system.service.SystemCollectorService;
import lombok.Getter;
import lombok.Setter;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarProxyCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

/**
 * Created by HAVVING on 2021-03-09.
 */
public class Constants {
    private static final Logger log = LoggerFactory.getLogger(Constants.class);
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
     * @return
     */
    public static Configs getConfig() {
        return getInstance().configs;
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

}
