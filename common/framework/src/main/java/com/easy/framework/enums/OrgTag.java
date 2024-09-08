package com.easy.framework.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 机构标签
 * </p>
 *
 * @author muchi
 */

@Getter
@AllArgsConstructor
public enum OrgTag {

    /**
     * 机构标签
     */
    PROVINCE("PROVINCE", "省级单位"),
    CITY("CITY", "市级单位"),
    DISTRICT("DISTRICT", "区县级单位"),
    ;

    @EnumValue
    private final String value;

    private final String desc;

}