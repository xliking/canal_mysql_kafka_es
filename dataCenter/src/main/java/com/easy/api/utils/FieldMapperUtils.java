package com.easy.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author muchi
 */
public class FieldMapperUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final Logger log = LoggerFactory.getLogger(FieldMapperUtils.class);


    /**
     * 将数据从Map映射到指定对象的字段中，根据字段类型进行转换。
     *
     * @param clazz           对象的类
     * @param data            包含列名和值的Map
     * @param fieldMappings   对象字段与数据源列名之间的映射
     * @return 返回一个映射了数据的新对象实例
     * @throws Exception 反射异常
     */

    public static Object mapFieldsToDocument(Class<?> clazz, Map<String, Object> data, Map<String, String> fieldMappings) throws Exception {
        Map<Object, Object> resMap = new HashMap<>();
        // 通过反射创建对象实例
        Object document = clazz.getDeclaredConstructor().newInstance();
        // 遍历字段映射
        for (Map.Entry<String, String> entry : fieldMappings.entrySet()) {
            // 对象中的字段名称
            String fieldName = entry.getKey();
            // 数据源中的字段名称，初始为字段名，后续可能使用注解中的值
            String columnName = entry.getValue();
            // 获取类中的字段
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            Object value = data.get(fieldName);
            if (value != null) {
                Class<?> fieldType = field.getType();
                try {
                    Object convertedValue = null;
                    // 针对不同类型进行处理
                    if (fieldType == int.class || fieldType == Integer.class) {
                        convertedValue = Integer.parseInt(value.toString());
                    } else if (fieldType == long.class || fieldType == Long.class) {
                        convertedValue = Long.parseLong(value.toString());
                    } else if (fieldType == double.class || fieldType == Double.class) {
                        convertedValue = Double.parseDouble(value.toString());
                    } else if (fieldType == boolean.class || fieldType == Boolean.class) {
                        convertedValue = Boolean.parseBoolean(value.toString());
                    } else if (fieldType == String.class) {
                        convertedValue = value.toString();
                    } else if (fieldType == List.class) {
                        convertedValue = OBJECT_MAPPER.convertValue(value, List.class);
                    } else if (fieldType == Date.class) {
                        convertedValue = DATE_FORMAT.parse(value.toString());
                    } else {
                        convertedValue = OBJECT_MAPPER.convertValue(value, fieldType);
                    }
                    resMap.put(columnName, convertedValue);
                } catch (Exception e) {
                    log.error("Failed to map field: {}, with value: {}, to type: {}", fieldName, value, fieldType, e);
                }
            }
        }

        // 返回映射后的对象实例
        return OBJECT_MAPPER.convertValue(resMap, Object.class);
    }


}
