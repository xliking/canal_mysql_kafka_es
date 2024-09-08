package com.easy.datasource.dao;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.reflect.GenericTypeUtils;
import com.easy.datasource.utils.ProviderUtils;
import com.easy.framework.bean.base.BaseEntity;
import com.easy.utils.lang.ChartUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Provider模版
 * </p>
 *
 * @author muchi
 */
public class BaseProvider<T> {

    private static final String TABLE_PREFIX = "prefix_";
    private static final BaseProvider<?> PROVIDER = new BaseProvider<>();
    private final Class<?>[] typeArguments = GenericTypeUtils.resolveTypeArguments(getClass(), BaseProvider.class);

    public static BaseProvider<?> get() {
        return PROVIDER;
    }

    /**
     * 获取所有字段
     *
     * @param tClass 实体类
     * @return {@link List<Field>}
     */
    private static List<Field> getAllField(Class<?> tClass) {
        List<Field> fieldList = new LinkedList<>();
        if (tClass == Object.class) {
            return fieldList;
        }
        Field[] fields = tClass.getDeclaredFields();
        fieldList.addAll(Arrays.asList(fields));
        Class<?> superclass = tClass.getSuperclass();
        fieldList.addAll(getAllField(superclass));
        return fieldList;
    }

    /**
     * join 附加 select
     *
     * @param sql             需要附加的 sql
     * @param baseProvider    对应表
     * @param excludeFieldSet 排除字段
     */
    public static <M> void select(SQL sql, Set<String> excludeFieldSet, BaseProvider<M> baseProvider) {
        Map<String, String> fieldMap = baseProvider.getFieldMap();
        for (Map.Entry<String, String> entry : fieldMap.entrySet()) {
            if (excludeFieldSet.contains(entry.getValue())) {
                continue;
            }
            sql.SELECT(baseProvider.getTablePrefix() + ".`" + entry.getValue() + "` AS `" + entry.getKey() + "`");
        }
    }

    /**
     * 默认排序
     *
     * @return List<OrderItem>
     */
    public static List<OrderItem> defaultSort() {
        List<OrderItem> orderList = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setAsc(false);
        orderItem.setColumn("id");
        orderList.add(orderItem);
        return orderList;
    }

    @SuppressWarnings("unchecked")
    public Class<T> currentModelClass() {
        return (Class<T>) this.typeArguments[0];
    }

    /**
     * 根据mybatisplus的@TableName注解获取表名
     *
     * @return 数据表名字
     */
    public String getTableName() {
        TableName annotation = currentModelClass().getAnnotation(TableName.class);
        return annotation.value();
    }

    /**
     * 组装前缀表名字
     *
     * @return String {@link TABLE_PREFIX+TableName}
     */
    public String getTablePrefix() {
        return TABLE_PREFIX + getTableName();
    }


    public void select(SQL sql) {
        select(sql, Set.of());
    }

    /**
     * 获取表字段map
     *
     * @return Map<String, String>
     */
    public Map<String, String> getFieldMap() {
        Class<?> tClass = currentModelClass();
        List<Field> allField = getAllField(tClass);
        Map<String, String> fieldMap = new HashMap<>(allField.size());
        for (Field field : allField) {
            field.setAccessible(true);
            String name = field.getName();
            String columnName;
            TableField tableField = field.getAnnotation(TableField.class);
            if (Objects.nonNull(tableField)) {
                columnName = tableField.value();
            } else {
                columnName = ChartUtils.camelToUnderline(name, 1);
            }
            fieldMap.put(name, columnName.trim().replace("`", ""));
        }
        return fieldMap;
    }

    /**
     * 根据实体类mybatisplus的注解获取所有表字段
     *
     * @return List<String>
     */
    public List<String> getColumnList() {
        Class<?> tClass = currentModelClass();
        List<Field> allField = getAllField(tClass);
        List<String> fieldList = new LinkedList<>();
        for (Field field : allField) {
            field.setAccessible(true);
            String columnName;
            TableField tableField = field.getAnnotation(TableField.class);
            if (Objects.nonNull(tableField)) {
                columnName = tableField.value();
            } else {
                columnName = ChartUtils.camelToUnderline(field.getName(), 1);
            }
            fieldList.add(columnName);
        }
        return fieldList;
    }

    /**
     * 组装select语句
     *
     * @param sql             需要附加的 sql
     * @param excludeFieldSet 排除字段
     */
    public void select(SQL sql, Set<String> excludeFieldSet) {
        select(sql, excludeFieldSet, this);
    }

    /**
     * 组装查询from 表名
     *
     * @param sql sql
     */
    public void from(SQL sql) {
        sql.FROM(getTableName() + " " + getTablePrefix());
    }

    /**
     * @param sql                   需要附加的 sql
     * @param baseProvider          需要连接的目标表
     * @param sourceColumnUnderline 源表字段
     * @param targetColumnUnderline 目标表字段
     * @param excludeFieldSet       排除字段
     * @param <M>                   目标类
     */
    public <M> void join(SQL sql, BaseProvider<M> baseProvider, Set<String> excludeFieldSet, String sourceColumnUnderline, String targetColumnUnderline) {
        select(sql, excludeFieldSet, baseProvider);
        sql.LEFT_OUTER_JOIN(baseProvider.getTableName() + " " + baseProvider.getTablePrefix() + " ON " + baseProvider.getTablePrefix() + "." + targetColumnUnderline + " = " + getTablePrefix() + "." + sourceColumnUnderline);
    }

    /**
     * 条件查询组装
     *
     * @param sql       sql
     * @param condition 条件
     */
    public void where(SQL sql, String condition) {
        sql.WHERE(getTablePrefix() + "." + condition);
    }

    /**
     * 排序字段
     *
     * @param orderList        需要排序的字段
     * @param baseProviderList baseProviderList
     * @param sql              sql
     */
    public void sort(List<OrderItem> orderList, List<BaseProvider<? extends BaseEntity>> baseProviderList, SQL sql) {
        Map<String, BaseProvider<? extends BaseEntity>> map = new HashMap<>();
        // Bean字段名和数据库字段名对应map
        Map<String, String> keyValueMap = new HashMap<>();
        for (int i = baseProviderList.size() - 1; i >= 0; i--) {
            BaseProvider<? extends BaseEntity> baseProvider = baseProviderList.get(i);
            for (Map.Entry<String, String> entry : baseProvider.getFieldMap().entrySet()) {
                map.put(entry.getKey(), baseProvider);
                keyValueMap.put(entry.getKey(), entry.getValue());
            }
        }
        if (CollectionUtils.isEmpty(orderList)) {
            orderList = defaultSort();
        }
        for (OrderItem orderItem : orderList) {
            String column = orderItem.getColumn();
            if (!map.containsKey(column)) {
                continue;
            }
            sql.ORDER_BY(map.get(column).getTablePrefix() + "." + keyValueMap.get(column) + ProviderUtils.asc(orderItem.isAsc()));
        }
    }

    public void page(Long current, Long size, SQL sql) {
        sql.LIMIT((current - 1) * size + "," + size);
    }
}