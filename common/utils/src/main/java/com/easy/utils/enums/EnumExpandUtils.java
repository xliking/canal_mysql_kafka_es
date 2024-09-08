package com.easy.utils.enums;

import org.apache.commons.lang3.tuple.Pair;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

/**
 * <p>枚举常用工具类</p>
 * <p>使用该枚举工具类需要指定的枚举实现 {@link EnumInterface} 接口</p>
 *
 * @author muchi
 * @since 2022/4/8 14:24
 */
public final class EnumExpandUtils {

    /**
     * 禁止实例化
     */
    private EnumExpandUtils() {
        throw new IllegalAccessError(this.getClass().getName());
    }

    public static <E extends Enum<E> & EnumInterface<V>, V> List<Pair<String, String>> toList(Class<E> enumClass) {

        Objects.requireNonNull(enumClass);
        EnumSet<E> all = EnumSet.allOf(enumClass);
        return all.stream()
                .map(k -> Pair.of(String.valueOf(k.getCode()), String.valueOf(k.getIntroduction())))
                .toList();
    }
}