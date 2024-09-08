package com.easy.framework.enums;


import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账号状态枚举
 * </p>
 *
 * @author muchi
 */
@Getter
@AllArgsConstructor
public enum AccountStatus {
    /**
     * 账号状态
     */
    INACTIVATED("INACTIVATED", "未激活"),
    NORMAL("NORMAL", "正常"),
    STOP("STOP", "停用"),
    ;
    @EnumValue
    private final String value;

    private final String desc;

}