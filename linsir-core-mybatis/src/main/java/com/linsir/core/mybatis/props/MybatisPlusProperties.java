package com.linsir.core.mybatis.props;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * description：MybatisPlusProperties
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/2/6 4:57
 */
@Data
@ConfigurationProperties("linsir.mybatis-plus")
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

    /*忽略的表，逗号分割开*/
    private String ignoreTables;

    /*数据权限控制标题*/
    private String scopeManagerTitle;
}
