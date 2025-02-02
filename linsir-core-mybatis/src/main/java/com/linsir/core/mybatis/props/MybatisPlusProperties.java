package com.linsir.core.mybatis.props;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * description：Mybatis 配置参数
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/2/1 19:43
 */
@Data
@ConfigurationProperties(prefix = "linsir.mybatis-plus")
public class MybatisPlusProperties {

    /**
     * 分页最大数
     */
    private Long pageLimit = 500L;

    /**
     * 溢出总页数后是否进行处理
     */
    protected Boolean overflow = false;

    /**
     * 是否打印 sql
     */
    private boolean sqlLog = true;


    /**
     * 忽略的表
     */
    private String ignoreTables;

}
