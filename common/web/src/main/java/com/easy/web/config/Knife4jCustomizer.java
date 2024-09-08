package com.easy.web.config;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.oas.models.media.Schema;
import lombok.SneakyThrows;
import org.springdoc.core.customizers.PropertyCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * <p>枚举文档构建器</p>
 *
 * @author muchi
 * @since 2023/7/18 14:32
 */
@Configuration
public class Knife4jCustomizer implements PropertyCustomizer {

    @Value("${knife4j.enable:false}")
    private Boolean knife4j;

    @Override
    @SneakyThrows
    public Schema<?> customize(Schema schema, AnnotatedType annotatedType) {

        // knife4j 兼容
        if (knife4j) {
            String description = schema.getDescription();
            String title = schema.getTitle();
            if (null == description || description.isEmpty()) {
                schema.description(title);
            } else {
                if (null != title && !title.isEmpty()) {
                    schema.description(schema.getTitle() + "," + description);
                }
            }
        }
        return schema;
    }
}