package com.easy.framework.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据权限级别
 * </p>
 *
 * @author muchi
 */
@Getter
@AllArgsConstructor
public enum AuthorityLevel {

    /**
     * 菜单类型
     */
    ONESELF("ONESELF", "本级", 0),
    LOWER("LOWER", "本级及下级", 1);

    @EnumValue
    private final String value;

    private final String desc;

    /**
     * 优先级，越小优先级越高
     */
    private final Integer priorityLevel;
}