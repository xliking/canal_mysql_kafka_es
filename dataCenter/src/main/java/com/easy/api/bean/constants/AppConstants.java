package com.easy.api.bean.constants;

/**
 * @author muchi
 */
public final class AppConstants {
    private AppConstants() {}

    // Kafka
    public static final String KAFKA_TOPIC = "my-topic";
    public static final String KAFKA_GROUP_ID = "group_id";

    // Canal Event Types
    public static final String INSERT_EVENT = "INSERT";
    public static final String UPDATE_EVENT = "UPDATE";
    public static final String DELETE_EVENT = "DELETE";
}
