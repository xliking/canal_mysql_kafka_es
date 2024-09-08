package com.easy.web.annotation;


import com.easy.framework.bean.base.R;

import java.lang.annotation.*;

/**
 * <p>用于将 controller 返回值用 {@link R R} 包裹</p>
 *
 * @author muchi
 * @since 2023/9/12 13:57
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Wrap {

    /**
     * 是否禁用
     */
    boolean disabled() default false;
}