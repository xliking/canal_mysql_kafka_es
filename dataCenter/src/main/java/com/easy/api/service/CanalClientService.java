package com.easy.api.service;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.easy.api.bean.constants.AppConstants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author muchi
 */
@Service
public class CanalClientService {

    private static final Logger log = LoggerFactory.getLogger(CanalClientService.class);
    @Value("${canal.client.host}")
    private String canalHost;

    @Value("${canal.client.port}")
    private int canalPort;

    @Value("${canal.client.destination}")
    private String destination;

    @Value("${canal.client.username}")
    private String username;

    @Value("${canal.client.password}")
    private String password;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    private CanalConnector connector;

    @PostConstruct
    public void init() {
        connector = CanalConnectors.newSingleConnector(
                new InetSocketAddress(canalHost, canalPort),
                destination,
                username,
                password
        );
        new Thread(this::process).start();
    }

    private void process() {
        int batchSize = 1000;
        try {
            connector.connect();
            connector.subscribe(".*\\..*");
            connector.rollback();
            while (true) {
                Message message = connector.getWithoutAck(batchSize);
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId != -1 && size != 0) {
                    handleEntries(message.getEntries());
                }
                connector.ack(batchId);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            connector.disconnect();
        }
    }

    private void handleEntries(List<CanalEntry.Entry> entries) {
        for (CanalEntry.Entry entry : entries) {
            if (entry.getEntryType() == CanalEntry.EntryType.ROWDATA) {
                try {
                    CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
                    for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
                        CanalEntry.EventType eventType = rowChange.getEventType();
                        String tableName = entry.getHeader().getTableName();
                        String databaseName = entry.getHeader().getSchemaName();
                        String fullTableName = databaseName + "." + tableName;
                        String rowDataJson = convertRowDataToJson(rowData, eventType, fullTableName);
                        log.info("rowDataJson: {}", rowDataJson);
                        kafkaTemplate.send(AppConstants.KAFKA_TOPIC, rowDataJson);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        }
    }

    private String convertRowDataToJson(CanalEntry.RowData rowData, CanalEntry.EventType eventType, String tableName) {
        Map<String, Object> data = new HashMap<>();
        List<CanalEntry.Column> columns = eventType == CanalEntry.EventType.DELETE ? rowData.getBeforeColumnsList() : rowData.getAfterColumnsList();
        for (CanalEntry.Column column : columns) {
            data.put(column.getName(), column.getValue());
        }
        data.put("eventType", eventType.name());
        data.put("tableName", tableName);
        try {
            return new ObjectMapper().writeValueAsString(data);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return "";
        }
    }
}
