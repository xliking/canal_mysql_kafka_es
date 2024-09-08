package com.easy.elasticsearch.config;


import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * </p>
 *
 * @author muchi
 */
@Configuration
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    private static final String LOCAL_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @Value("${spring.elasticsearch.uris}")
    private List<String> uris;

    @Value("${spring.elasticsearch.username}")
    private String username;

    @Value("${spring.elasticsearch.password}")
    private String password;

    @NotNull
    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(uris.toArray(new String[0]))
                .withConnectTimeout(Duration.ofSeconds(180000))
                .withSocketTimeout(Duration.ofSeconds(180000))
                .withBasicAuth(username, password)
                .build();
    }


    @NotNull
    @Override
    @Bean
    public ElasticsearchCustomConversions elasticsearchCustomConversions() {
        // 注入自定义转换器处理LocalDateTime数据
        List<Converter<?, ?>> converters = new ArrayList<>(16);
        converters.add(StringToLocalDateTimeConverter.INSTANCE);
        converters.add(LocalDateTimeToStringConverter.INSTANCE);
        return new ElasticsearchCustomConversions(converters);
    }

    /**
     * LocalDateTime转String
     */
    @WritingConverter
    private enum LocalDateTimeToStringConverter implements Converter<LocalDateTime, String> {
        /**
         * 实例化
         */
        INSTANCE;

        @Override
        public String convert(LocalDateTime source) {
            return source.format(DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_PATTERN));
        }
    }

    /**
     * String转LocalDateTime
     */
    @ReadingConverter
    private enum StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {
        /**
         * 实例化
         */
        INSTANCE;

        @Override
        public LocalDateTime convert(@NotNull String source) {
            return LocalDateTime.parse(source, DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_PATTERN));
        }
    }
}