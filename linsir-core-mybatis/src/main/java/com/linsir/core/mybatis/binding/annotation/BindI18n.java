package com.linsir.core.mybatis.binding.annotation;


import java.lang.annotation.*;

/**
 * description：国际化翻译
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/14 22:26
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface BindI18n {

    /**
     * 资源标识 {@link com.linsir.core.entity.I18nConfig#getCode()}
     */
    String value();

}

