

package com.linsir.core.mybatis.intercept;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.core.Ordered;

/**
 * description：自定义查询拦截器
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/2/6 5:01
 */
@SuppressWarnings({"rawtypes"})
public interface QueryInterceptor extends Ordered {

	/**
	 * 拦截处理
	 *
	 * @param executor
	 * @param ms
	 * @param parameter
	 * @param rowBounds
	 * @param resultHandler
	 * @param boundSql
	 */
	void intercept(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql);

	/**
	 * 排序
	 *
	 * @return int
	 */
	@Override
	default int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}
}
