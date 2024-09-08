package com.easy.framework.exception;


import com.easy.framework.bean.base.R;
import com.easy.framework.enums.REnum;

/**
 * 自定义异常
 * </p>
 *
 * @author muchi
 */
public class CustomizeException extends RuntimeException {

    private final R<String> result;

    public CustomizeException(REnum rEnum) {
        super(rEnum.getIntroduction());
        this.result = R.fail(rEnum);
    }

    public CustomizeException(int code, String message) {
        super(message);
        this.result = R.fail(code, message);
    }

    public CustomizeException(String message) {
        super(message);
        this.result = R.fail(message);
    }

    public R<String> getResult() {
        return result;
    }
}