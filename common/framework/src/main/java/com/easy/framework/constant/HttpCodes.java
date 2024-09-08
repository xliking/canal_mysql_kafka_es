package com.easy.framework.constant;

/**
 * HTTP响应码
 * </p>
 *
 * @author muchi
 */
public final class HttpCodes {

    /**
     * 成功
     */
    public static final Integer SUCCESS = 200;

    /**
     * 错误请求
     */
    public static final Integer BAD_REQUEST = 400;

    /**
     * 未授权
     */
    public static final Integer UNAUTHORIZED = 401;

    /**
     * 禁止访问
     */
    public static final Integer FORBIDDEN = 403;

    /**
     * 方法不允许
     */
    public static final Integer METHOD_NOT_ALLOWED = 405;

    /**
     * 失败
     */
    public static final Integer ERROR = 500;

    private HttpCodes() {
        throw new IllegalAccessError("HttpCodes.class");
    }
}