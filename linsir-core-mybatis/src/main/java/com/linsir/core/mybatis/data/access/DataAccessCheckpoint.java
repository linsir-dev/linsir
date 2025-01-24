package com.linsir.core.mybatis.data.access;

import java.lang.annotation.*;

/**
 * description：数据权限检查点 - 添加在entity/dto字段上的注解，可以支持自动检查数据权限
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/15 1:18
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface DataAccessCheckpoint {

}
