package com.easy.framework.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账号客户端枚举
 * </p>
 *
 * @author muchi
 */
@Getter
@AllArgsConstructor
public enum AccountClient {
    /**
     * 账号客户端枚举
     */
    ADMIN("ADMIN", "管理端"),
    WEB("WEB", "WEB"),
    APP("APP", "APP"),
    ;
    @EnumValue
    private final String value;

    private final String desc;

    public static AccountClient getByValue(String value) {
        for (AccountClient accountClient : values()) {
            if (accountClient.getValue().equals(value)) {
                return accountClient;
            }
        }
        return null;
    }
}