package com.easy.framework.constant;

/**
 * 通用常量信息
 *
 * @author muchi
 */
public interface Constants {
    /**
     * UTF-8 字符集
     */
    String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    String GBK = "GBK";

    /**
     * www主域
     */
    String WWW = "www.";

    /**
     * http请求
     */
    String HTTP = "http://";

    /**
     * https请求
     */
    String HTTPS = "https://";

    /**
     * 文件分隔符
     */
    String FILE_SEPARATOR = "/";

    /**
     * 下划线
     */
    String UNDERLINE = "_";

    /**
     * 响应头 CONTENT_TYPE
     */
    String CONTENT_TYPE = "application/json;charset=UTF-8";
    /**
     * 成功标记
     */
    Integer SUCCESS = 200;

    /**
     * 失败标记
     */
    Integer FAIL = 500;

    /**
     * 菜单根节点id
     */
    String ROOT = "0";

    /**
     * 超级管理员角色key
     */
    String ADMIN_ROLE = "admin";

    /**
     * 防重提交 redis key
     */
    String REPEAT_SUBMIT_KEY = "repeat_submit:";

    String ACCESS_TOKEN = "access_token";

}