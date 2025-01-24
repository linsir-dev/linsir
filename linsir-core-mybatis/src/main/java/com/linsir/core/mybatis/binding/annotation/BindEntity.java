package com.linsir.core.mybatis.binding.annotation;

import java.lang.annotation.*;

/**
 * description：绑定Entity 注解定义（1-1）
 * author     ：linsir
 * date       ：2025/1/14 22:04
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface BindEntity {
    /**
     * 对应的service类
     * @return
     */
    Class entity();

    /**
     * JOIN连接条件
     * @return
     */
    String condition();

    /**
     * 深度绑定
     * @return
     */
    boolean deepBind() default false;
}
