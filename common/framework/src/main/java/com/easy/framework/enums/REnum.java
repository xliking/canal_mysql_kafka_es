package com.easy.framework.enums;

import com.easy.framework.constant.HttpCodes;
import com.easy.utils.enums.EnumInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * </p>
 *
 * @author muchi
 */
@Getter
@AllArgsConstructor
public enum REnum implements EnumInterface<Integer> {

    /**
     * ?
     */
    SUCCESS(HttpCodes.SUCCESS, "操作成功"),
    NO_DATA_SUCCESS(HttpCodes.SUCCESS, "暂无数据"),

    RUNTIME_EXCEPTION(HttpCodes.ERROR, "异常"),
    AUTHENTICATION_FAILURE(HttpCodes.ERROR, "认证失败"),
    UNAUTHORIZED(HttpCodes.UNAUTHORIZED, "无权访问"),
    NOT_LOGIN(HttpCodes.UNAUTHORIZED, "未登录"),
    BAD_CREDENTIAL(HttpCodes.BAD_REQUEST, "客户端凭证错误"),
    UNSUPPORTED_GRANT_TYPE(HttpCodes.BAD_REQUEST, "不支持的授权类型"),

    FORBIDDEN(HttpCodes.FORBIDDEN, "禁止访问"),
    UNKNOWN_EXCEPTION(HttpCodes.ERROR, "未知异常"),

    NOT_FUND_EXCEPTION(HttpCodes.ERROR, "未获取到签名"),
    TOKEN_EXCEPTION(HttpCodes.UNAUTHORIZED, "签名异常"),
    TOKEN_WRONGFUL(HttpCodes.BAD_REQUEST, "签名不合法"),
    TOKEN_EXPIRED(HttpCodes.FORBIDDEN, "签名过期,禁止访问"),

    ;

    private final Integer code;
    private final String introduction;
}