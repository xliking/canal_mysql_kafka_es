package com.easy.framework.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 机构级别
 * </p>
 *
 * @author muchi
 */
@Getter
@AllArgsConstructor
public enum OrgLevel {


    /**
     * 机构类型
     */
    FIRST("FIRST", "一级机构"),
    TWO("TWO", "二级机构"),
    THREE("THREE", "三级机构"),
    SCHOOL("SCHOOL", "学校");

    @EnumValue
    private final String value;

    private final String desc;
}