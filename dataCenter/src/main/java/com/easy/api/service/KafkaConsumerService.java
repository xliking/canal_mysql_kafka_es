package com.easy.api.service;

import com.easy.api.bean.constants.AppConstants;
import com.easy.api.config.ElasticSyncProcessor;
import com.easy.api.utils.FieldMapperUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.easy.api.config.LarkProcessor.sendLarkMessage;

/**
 * @author muchi
 */
@Service
@AllArgsConstructor
public class KafkaConsumerService {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumerService.class);


    private final ElasticsearchOperations elasticsearchOperations;

    private final ElasticSyncProcessor elasticSyncProcessor;

    private final ObjectMapper objectMapper;

    @KafkaListener(topics = AppConstants.KAFKA_TOPIC, groupId = AppConstants.KAFKA_GROUP_ID)
    public void consume(String message) throws Exception{
        Map data = objectMapper.readValue(message, Map.class);
        if (data.isEmpty()) {
            return;
        }
        String eventType = (String) data.remove("eventType");
        String tableName = (String) data.remove("tableName");
        Class<?> clazz = elasticSyncProcessor.getClassByIndex(tableName);
        log.info("Received event: {}, table: {}", eventType, tableName);
        if (clazz == null) {
            return;
        }
        // 获取字段映射
        Map<String, String> fieldMappings = elasticSyncProcessor.getFieldMappings(clazz);
        Object document = FieldMapperUtils.mapFieldsToDocument(clazz, data, fieldMappings);
        IndexCoordinates indexCoordinates = IndexCoordinates.of(tableName);
        if (AppConstants.INSERT_EVENT.equals(eventType) || AppConstants.UPDATE_EVENT.equals(eventType)) {
            elasticsearchOperations.save(document, indexCoordinates);
            sendLarkMessage(AppConstants.SUCCESS_CODE,tableName,eventType,document.toString());
            log.info("Saved OR updated document: {}", document);
        } else if (AppConstants.DELETE_EVENT.equals(eventType)) {
            String id = convertToString(data.get("id"));
            elasticsearchOperations.delete(id, indexCoordinates);
            sendLarkMessage(AppConstants.SUCCESS_CODE,tableName,eventType,document.toString());
            log.info("Deleted document: {}", document);
        }else {
            sendLarkMessage(AppConstants.ERROR_CODE,tableName,eventType,document.toString());
            log.error("Unsupported event type: {}", eventType);
        }
    }

    public String convertToString(Object value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null.");
        }
        return value.toString();
    }


}
