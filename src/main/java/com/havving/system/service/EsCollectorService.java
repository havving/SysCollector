package com.havving.system.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.havving.system.domain.SysModel;
import com.havving.system.domain.impl.EsStats;
import com.havving.system.global.HttpProvider;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.*;

import static com.havving.system.global.EsUrl.NODE_STATS;

/**
 * Created by HAVVING on 2021-03-18.
 */
@Slf4j
public class EsCollectorService {
    public HttpProvider httpProvider;

    public EsCollectorService(String ip, int port) {
        this.httpProvider = new HttpProvider(ip, port);
    }

    public List<SysModel> collect() {
        if (httpProvider != null) {
            String url = NODE_STATS;
            List<SysModel> results = new ArrayList<>();

            HttpProvider.ResponseMessage response = httpProvider.get(url, null);
            log.info("{} status returned.", response.getStatusCode());
            log.debug("\t{}", response.getContents());
            ObjectMapper mapper = new ObjectMapper();

            try {
                JsonNode root = mapper.readTree(response.getContents());
                Iterator<Map.Entry<String, JsonNode>> nodes = root.at("/nodes").fields();

                while (nodes.hasNext()) {
                    Map.Entry<String, JsonNode> node = nodes.next();
                    String nodeId = node.getKey();

                    JsonNode nodeInfos = node.getValue();
                    String ip = nodeInfos.findValue("ip").asText();
                    String host = nodeInfos.findValue("host").asText();
                    String name = nodeInfos.findValue("name").asText();

                    EsStats stats = new EsStats(host);
                    stats.setNodeId(nodeId);
                    stats.setNodeIp(ip);
                    stats.setNodeInfo(nodeInfos.toString());
                    stats.setName(name);

                    results.add(stats);
                }

            } catch (IOException e) {
                log.error("Response Json translate failed.", e);
            }
            log.debug("ES stats collect complete. {}", results);

            return results;

        } else {
            log.error("ES stats get function has been failed. Connection is null");
            return Collections.EMPTY_LIST;
        }
    }
}
