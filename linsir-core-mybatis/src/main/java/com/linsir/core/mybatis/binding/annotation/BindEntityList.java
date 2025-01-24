package com.linsir.core.mybatis.binding.annotation;

import java.lang.annotation.*;

/**
 * description：绑定Entity集合注解（1-n）
 * author     ：linsir
 * date       ：2025/1/14 22:05
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface BindEntityList {
    /**
     * 对应的entity类
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

    /**
     * EntityList排序，示例 `id:DESC,age:ASC`
     * @return
     */
    String orderBy() default "";

    /**
     * 分隔符，用于拆解拼接存储的多个id值
     * @return
     */
    String splitBy() default "";
}
