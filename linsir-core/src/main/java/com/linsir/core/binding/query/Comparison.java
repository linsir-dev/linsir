package com.linsir.core.binding.query;

/**
 * description：比较条件枚举类
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/15 0:21
 */
public enum Comparison {
    EQ, // 相等，默认
    IN, // IN

    STARTSWITH, //以xx起始
    ENDSWITH, //以xx结尾
    LIKE, // LIKE
    CONTAINS, //包含（用于JsonArray是否包含某个值）

    GT, // 大于
    GE, // 大于等于
    LT, // 小于
    LE, // 小于等于

    BETWEEN, //介于-之间
    BETWEEN_BEGIN, //介于之后
    BETWEEN_END, //介于之前

    NOT_EQ,  //不等于
    NOT_IN, // 不在...内

    IS_NULL, // 空
    IS_NOT_NULL // 非空
}

