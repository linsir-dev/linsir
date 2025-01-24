package com.linsir.core.mybatis.binding.annotation;

import java.lang.annotation.*;

/**
 * description：绑定子项的条目计数
 * author     ：linsir
 * date       ：2025/1/14 22:02
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface BindCount {
    /**
     * 绑定的Entity类
     * @return
     */
    Class entity();

    /**
     * JOIN连接条件
     * @return
     */
    String condition();
}
