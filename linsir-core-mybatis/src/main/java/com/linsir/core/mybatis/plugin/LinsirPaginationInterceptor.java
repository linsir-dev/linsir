package com.linsir.core.mybatis.plugin;


import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.linsir.core.mybatis.injector.QueryInterceptor;
import lombok.SneakyThrows;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

/**
 * description：分页插件
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/2/1 22:59
 */
public class LinsirPaginationInterceptor extends PaginationInnerInterceptor {
    /**
     * 查询拦截器
     */
    private QueryInterceptor[] queryInterceptors;

    @SneakyThrows
    @Override
    public boolean willDoQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        QueryInterceptorExecutor.exec(queryInterceptors, executor, ms, parameter, rowBounds, resultHandler, boundSql);
        return super.willDoQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
    }
}
