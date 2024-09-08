package com.easy.api.config;

import com.easy.api.bean.annotations.ElasticField;
import com.easy.api.bean.annotations.ElasticSync;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


/**
 * @author muchi
 */
@Component
@AllArgsConstructor
public class ElasticSyncProcessor {

    private static final Logger log = LoggerFactory.getLogger(ElasticSyncProcessor.class);

    private final ApplicationContext applicationContext;

    private final ElasticsearchOperations elasticsearchOperations;

    private final Map<String, Class<?>> indexClassMap = new HashMap<>();

    @EventListener(ContextRefreshedEvent.class)
    public void init() {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(ElasticSync.class);
        for (Object bean : beans.values()) {
            // 使用 AopProxyUtils 以处理可能的代理类问题
            Class<?> clazz = AopProxyUtils.ultimateTargetClass(bean);
            // 获取 ElasticSync 注解
            ElasticSync elasticSync = clazz.getAnnotation(ElasticSync.class);
            if (elasticSync != null) {
                log.info("Found entity class {} with ElasticSync annotation", clazz.getName());
                String indexName = elasticSync.indexName();
                indexClassMap.put(indexName, clazz);
                // 获取 Elasticsearch 的 IndexOperations 进行索引操作
                IndexOperations indexOperations = elasticsearchOperations.indexOps(IndexCoordinates.of(indexName));
                if (!indexOperations.exists()) {
                    indexOperations.create();
                    indexOperations.putMapping(indexOperations.createMapping(clazz));
                    log.info("Index {} created and mapping set for class {}", indexName, clazz.getName());
                } else {
                    log.info("Index {} already exists. Skipping creation.", indexName);
                }
            } else {
                log.warn("ElasticSync annotation not found on class: {}", clazz.getName());
            }
        }
    }

    public Map<String, String> getFieldMappings(Class<?> clazz) {
        Map<String, String> fieldMappings = new HashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(ElasticField.class)) {
                ElasticField elasticField = field.getAnnotation(ElasticField.class);
                String fieldName = elasticField.name().isEmpty() ? field.getName() : elasticField.name();
                fieldMappings.put(field.getName(), fieldName);
                log.info("Field mapping for {} -> {}", field.getName(), fieldName);
            }
        }
        return fieldMappings;
    }

    public Class<?> getClassByIndex(String indexName) {
        return indexClassMap.get(indexName);
    }
}
