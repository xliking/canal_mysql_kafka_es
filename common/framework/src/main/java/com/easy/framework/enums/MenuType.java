package com.easy.framework.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单类型枚举
 * </p>
 *
 * @author muchi
 */
@Getter
@AllArgsConstructor
public enum MenuType {

    /**
     * 菜单类型
     */
    CATALOGUE("CATALOGUE", "目录"),
    PAGE("PAGE", "页面"),
    BUTTON("BUTTON", "按钮");

    @EnumValue
    private final String value;

    private final String desc;
}