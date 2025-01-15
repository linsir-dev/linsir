package com.linsir.core.binding.annotation;


import java.lang.annotation.*;

/**
 * description：绑定字段 （1-1）
 * author     ：linsir
 * date       ：2025/1/14 22:06
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface BindField {
    /**
     * 绑定的Entity类
     * @return
     */
    Class entity();

    /**
     * 绑定字段
     * @return
     */
    String field();

    /**
     * JOIN连接条件
     * @return
     */
    String condition();
}
