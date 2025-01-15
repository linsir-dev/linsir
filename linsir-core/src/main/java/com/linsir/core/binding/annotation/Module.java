package com.linsir.core.binding.annotation;

import java.lang.annotation.*;

/**
 * description：模块注解
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/15 14:44
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Module {

    /**
     * 指定模块名
     * @return
     */
    String value();
}
