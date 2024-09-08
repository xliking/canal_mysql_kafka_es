package com.easy.utils.enums;


import com.easy.utils.lang.CollectionUtil;

import java.util.*;

/**
 * <p>枚举常用工具类</p>
 * <p>使用该枚举工具类需要指定的枚举实现 {@link EnumInterface} 接口</p>
 *
 * @author muchi
 */
public final class EnumUtils {

    /**
     * 禁止实例化
     */
    private EnumUtils() {
        throw new IllegalAccessError("EnumUtils.class");
    }

    /**
     * <p>查找枚举</p>
     * <p>会根据枚举的 name、code 以及枚举的名称进行搜寻，满足上述任意条件即会认为判断成功</p>
     * <p>会返回找到的第一个枚举</p>
     * <p>不满足则抛出异常{@link NoSuchElementException NoSuchElementException}</p>
     *
     * @param value 待查找枚举值
     * @param enums 待查找枚举枚举数组
     * @param <T>   枚举泛型
     * @return 查找到的枚举
     */
    public static <T> EnumInterface<T> getByValue(Object value, EnumInterface<T>[] enums) {

        Objects.requireNonNull(value);
        Objects.requireNonNull(enums);

        String valueString = String.valueOf(value);
        return Arrays.stream(enums)
                .filter(e -> contains(e, valueString))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("未找到指定枚举"));
    }

    public static <T> List<EnumInterface<T>> getListByValue(Collection<?> valueList, EnumInterface<T>[] enums) {

        Objects.requireNonNull(enums);
        if (CollectionUtil.isEmpty(valueList)) {
            return new ArrayList<>();
        }

        List<String> list = valueList.stream().map(String::valueOf).toList();
        return Arrays.stream(enums)
                .filter(e -> {
                    for (String s : list) {
                        if (contains(e, s)) {
                            return true;
                        }
                    }
                    return false;
                })
                .toList();
    }

    /**
     * 判断枚举是否存在，拓展自方法 {@link EnumUtils#getByValue getByValue}
     *
     * @param value 待查找枚举值
     * @param enums 待查找枚举枚举数组
     * @param <T>   枚举泛型
     * @return 存在返回 true
     */
    public static <T> boolean containsByValue(Object value, EnumInterface<T>[] enums) {

        try {
            getByValue(value, enums);
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    /**
     * 判断枚举是否存在，拓展自方法 {@link EnumUtils#getByValue get}
     *
     * @param value 待查找枚举值
     * @param enums 待查找枚举枚举数组
     * @param <T>   枚举泛型
     * @return 存在返回 true
     */
    public static <T> boolean containsByValue(Object value, Collection<EnumInterface<?>> enums) {

        return Objects.nonNull(getByValue(value, enums.toArray(new EnumInterface[enums.size()])));
    }

    /**
     * <p>查找枚举</p>
     * <p>会根据枚举的 name、code 以及枚举的名称进行搜寻，满足上述任意条件即会认为判断成功</p>
     * <p>会返回找到的第一个枚举</p>
     * <p>不满足则抛出异常{@link NoSuchElementException NoSuchElementException}</p>
     *
     * @param value     待查找枚举值
     * @param enumClass 枚举 class
     * @param <E>       枚举类型
     * @param <V>       值类型
     * @return 查找到的枚举
     */
    public static <E extends Enum<E> & EnumInterface<V>, V> E getByClass(Object value, Class<E> enumClass) {

        Objects.requireNonNull(value);
        Objects.requireNonNull(enumClass);

        String valueString = String.valueOf(value);
        EnumSet<E> all = EnumSet.allOf(enumClass);
        return all.stream()
                .filter(e -> contains(e, valueString))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("未找到指定枚举"));
    }

    public static <E extends Enum<E> & EnumInterface<V>, V> List<E> getListByClass(Collection<?> valueList, Class<E> enumClass) {

        Objects.requireNonNull(enumClass);
        if (CollectionUtil.isEmpty(valueList)) {
            return new ArrayList<>();
        }

        List<String> list = valueList.stream().map(String::valueOf).toList();
        EnumSet<E> all = EnumSet.allOf(enumClass);
        return all.stream()
                .filter(e -> {
                    for (String s : list) {
                        if (contains(e, s)) {
                            return true;
                        }
                    }
                    return false;
                })
                .toList();
    }

    /**
     * 判断枚举是否存在，拓展自方法 {@link EnumUtils#getByClass getByClass}
     *
     * @param enumClass 枚举类
     * @param value     枚举值
     * @param <E>       枚举类型
     * @param <V>       值类型
     * @return true：存在
     */
    public static <E extends Enum<E> & EnumInterface<V>, V> boolean containsByClass(Object value, Class<E> enumClass) {

        try {
            getByClass(value, enumClass);
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    /**
     * @param e           枚举
     * @param valueString 枚举值
     * @param <E>         枚举类型
     * @param <V>         值类型
     * @return true：存在
     */
    public static <E extends Enum<E> & EnumInterface<V>, V> Boolean contains(E e, String valueString) {
        return Objects.equals(valueString, String.valueOf(e.getCode())) ||
                Objects.equals(valueString, e.toString());
    }

    /**
     * @param e           枚举
     * @param valueString 枚举值
     * @param <T>         枚举泛型
     * @return true：存在
     */
    public static <T> Boolean contains(EnumInterface<T> e, String valueString) {
        return Objects.equals(valueString, String.valueOf(e.getCode())) ||
                Objects.equals(valueString, e.toString());
    }
}