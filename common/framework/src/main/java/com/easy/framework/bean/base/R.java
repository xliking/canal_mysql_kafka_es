package com.easy.framework.bean.base;

import com.easy.framework.constant.Constants;
import com.easy.framework.enums.REnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.ObjectError;

/**
 * 响应类
 *
 * @author muchi
 */
@Data
@AllArgsConstructor
public class R<T> {
    /**
     * 成功
     */
    public static final int SUCCESS = Constants.SUCCESS;

    /**
     * 失败
     */
    public static final int FAIL = Constants.FAIL;

    @Schema(name = "返回编码", example = "0")
    public int code;

    @Schema(name = "返回消息", example = "操作成功")
    private String msg;

    @Schema(name = "响应参数 data")
    private T data;

    public R(REnum rEnum) {
        this.msg = rEnum.getIntroduction();
        this.code = rEnum.getCode();
    }

    private R() {
    }

    public static <T> R<T> success(T t) {

        R<T> r = new R<>(REnum.SUCCESS);
        r.setData(t);
        return r;
    }

    public static <T> R<T> success() {

        return success(null);
    }

    public static <T> R<T> fail(int code, String msg) {

        R<T> r = new R<>();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static <T> R<T> fail(String msg) {

        R<T> r = new R<>(REnum.RUNTIME_EXCEPTION);
        r.setMsg(msg);
        return r;
    }

    public static <T> R<T> fail(REnum rEnum, Exception e) {

        R<T> r = new R<>(rEnum);
        if (null != e) {
            r.setMsg(e.getMessage());
        }
        return r;
    }

    public static <T> R<T> fail(REnum rEnum) {

        return fail(rEnum, null);
    }

    public static <T> R<T> fail(ObjectError objectError) {

        R<T> r = new R<>(REnum.RUNTIME_EXCEPTION);
        r.setMsg(objectError.getDefaultMessage());
        return r;
    }
}