package com.easy.framework.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是否枚举
 *
 * @author muchi
 */
@Getter
@AllArgsConstructor
public enum YesOrNo {
    /**
     * 否
     */
    NO(0, "否"),
    /**
     * 是
     */
    YES(1, "是");

    @EnumValue
    private final Integer value;
    private final String desc;


}