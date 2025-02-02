package com.linsir.core.mybatis.config;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.linsir.core.mybatis.handler.LinsirTenantLineHandler;
import com.linsir.core.mybatis.injector.QueryInterceptor;
import com.linsir.core.mybatis.plugin.LinsirPaginationInterceptor;
import com.linsir.core.mybatis.plugin.SqlLogInterceptor;
import com.linsir.core.mybatis.props.MybatisPlusProperties;
import com.linsir.core.utils.ObjectUtil;
import lombok.AllArgsConstructor;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.nologging.NoLoggingImpl;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

/**
 * description：mybatis-plus 配置
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/16 12:58
 */
@AutoConfiguration
@AllArgsConstructor
@EnableConfigurationProperties(MybatisPlusProperties.class)
public class MybatisPlusConfiguration {

    /**
     * mybatis-plus 拦截器集合
     */
    @Bean
    @ConditionalOnMissingBean(MybatisPlusInterceptor.class)
    public MybatisPlusInterceptor mybatisPlusInterceptor(ObjectProvider<QueryInterceptor[]> queryInterceptors,
                                                         TenantLineInnerInterceptor tenantLineInnerInterceptor,
                                                         MybatisPlusProperties mybatisPlusProperties) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 配置租户拦截器
        interceptor.addInnerInterceptor(tenantLineInnerInterceptor);
        // 配置分页拦截器
        LinsirPaginationInterceptor paginationInterceptor = new LinsirPaginationInterceptor();
        // 配置自定义查询拦截器
        QueryInterceptor[] queryInterceptorArray = queryInterceptors.getIfAvailable();
        if (ObjectUtil.isNotEmpty(queryInterceptorArray)) {
            AnnotationAwareOrderComparator.sort(queryInterceptorArray);
            /*paginationInterceptor.setQueryInterceptors(queryInterceptorArray);*/
        }
        paginationInterceptor.setMaxLimit(mybatisPlusProperties.getPageLimit());
        paginationInterceptor.setOverflow(mybatisPlusProperties.getOverflow());
        interceptor.addInnerInterceptor(paginationInterceptor);
        return interceptor;
    }


    /**
     * 租户插件
     * @param mybatisPlusProperties
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(TenantLineInnerInterceptor.class)
    public TenantLineInnerInterceptor tenantLineInnerInterceptor(MybatisPlusProperties mybatisPlusProperties)
    {
        return new TenantLineInnerInterceptor(new LinsirTenantLineHandler(mybatisPlusProperties));
    }

    /**
     * sql 日志
     *
     * @return SqlLogInterceptor
     */
    @Bean
    @ConditionalOnProperty(value = "linsir.mybatis-plus.sql-log", matchIfMissing = true)
    public SqlLogInterceptor sqlLogInterceptor(MybatisPlusProperties properties) {
        return new SqlLogInterceptor(properties);
    }

    /**
     * 关闭 mybatis 默认日志
     */
    @Bean
    @ConditionalOnClass(MybatisPlusPropertiesCustomizer.class)
    public MybatisPlusPropertiesCustomizer mybatisPlusPropertiesCustomizer() {
        return properties -> {
            com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties.CoreConfiguration configuration = properties.getConfiguration();
            if (configuration != null) {
                Class<? extends Log> logImpl = configuration.getLogImpl();
                if (logImpl == null) {
                    configuration.setLogImpl(NoLoggingImpl.class);
                }
            }
        };
    }
}
