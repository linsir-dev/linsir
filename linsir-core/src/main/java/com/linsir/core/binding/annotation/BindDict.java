package com.linsir.core.binding.annotation;

import java.lang.annotation.*;

/**
 * description：绑定字典注解
 * author     ：linsir
 * date       ：2025/1/14 22:04
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface BindDict {

    /**
     * 绑定数据字典类型
     * @return
     */
    String type();

    /**
     * 数据字典项取值字段
     * @return
     */
    String field() default "";
}
