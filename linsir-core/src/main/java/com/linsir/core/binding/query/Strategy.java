package com.linsir.core.binding.query;


/**
 * description：查询策略（针对空值等的查询处理策略）
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/15 0:22
 */
public enum Strategy {
    /**
     * 忽略空字符串""
     */
    IGNORE_EMPTY,
    /**
     * 空字符串""参与查询
     */
    INCLUDE_EMPTY,
    /**
     * null参与构建isNull
     */
    INCLUDE_NULL,
}
