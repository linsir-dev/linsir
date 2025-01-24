package com.linsir.core.mybatis.data.copy;

import java.lang.annotation.*;

/**
 * description：拷贝字段时的非同名字段处理
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/15 1:28
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface Accept {
    /**
     * 接收来源对象的属性名
     * @return
     */
    String name();
    /**
     * source该字段有值时是否覆盖
     * @return
     */
    boolean override() default false;
}
