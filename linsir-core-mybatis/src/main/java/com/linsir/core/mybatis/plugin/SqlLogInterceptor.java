

package com.linsir.core.mybatis.plugin;

import com.alibaba.druid.DbType;
import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.JdbcParameter;
import com.alibaba.druid.proxy.jdbc.ResultSetProxy;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import com.alibaba.druid.sql.SQLUtils;
import com.linsir.core.mybatis.props.MybatisPlusProperties;
import com.linsir.core.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.List;

/**
 * description：SqlLogInterceptor  sql 拦截器
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/2/6 5:01
 */
@Slf4j
@RequiredArgsConstructor
public class SqlLogInterceptor extends FilterEventAdapter {
	private static final SQLUtils.FormatOption FORMAT_OPTION = new SQLUtils.FormatOption(false, false);
	private final MybatisPlusProperties properties;

	@Override
	protected void statementExecuteBefore(StatementProxy statement, String sql) {
		statement.setLastExecuteStartNano();
	}

	@Override
	protected void statementExecuteBatchBefore(StatementProxy statement) {
		statement.setLastExecuteStartNano();
	}

	@Override
	protected void statementExecuteUpdateBefore(StatementProxy statement, String sql) {
		statement.setLastExecuteStartNano();
	}

	@Override
	protected void statementExecuteQueryBefore(StatementProxy statement, String sql) {
		statement.setLastExecuteStartNano();
	}

	@Override
	protected void statementExecuteAfter(StatementProxy statement, String sql, boolean firstResult) {
		statement.setLastExecuteTimeNano();
	}

	@Override
	protected void statementExecuteBatchAfter(StatementProxy statement, int[] result) {
		statement.setLastExecuteTimeNano();
	}

	@Override
	protected void statementExecuteQueryAfter(StatementProxy statement, String sql, ResultSetProxy resultSet) {
		statement.setLastExecuteTimeNano();
	}

	@Override
	protected void statementExecuteUpdateAfter(StatementProxy statement, String sql, int updateCount) {
		statement.setLastExecuteTimeNano();
	}

	@Override
	public void statement_close(FilterChain chain, StatementProxy statement) throws SQLException {
		// 先调用父类关闭 statement
		super.statement_close(chain, statement);
		// 支持动态关闭
		if (!properties.isSqlLog()) {
			return;
		}
		// 是否开启调试
		if (!log.isInfoEnabled()) {
			return;
		}
		// 打印可执行的 sql
		String sql = statement.getBatchSql();
		// sql 为空直接返回
		if (StringUtils.isEmpty(sql)) {
			return;
		}
		int parametersSize = statement.getParametersSize();
		List<Object> parameters = new ArrayList<>(parametersSize);
		for (int i = 0; i < parametersSize; ++i) {
			// 转换参数，处理 java8 时间
			parameters.add(getJdbcParameter(statement.getParameter(i)));
		}
		String dbType = statement.getConnectionProxy().getDirectDataSource().getDbType();
		String formattedSql = SQLUtils.format(sql, DbType.of(dbType), parameters, FORMAT_OPTION);
		printSql(formattedSql, statement);
	}

	private static Object getJdbcParameter(JdbcParameter jdbcParam) {
		if (jdbcParam == null) {
			return null;
		}
		Object value = jdbcParam.getValue();
		// 处理 java8 时间
		if (value instanceof TemporalAccessor) {
			return value.toString();
		}
		return value;
	}

	private static void printSql(String sql, StatementProxy statement) {
		// 打印 sql
		String sqlLogger = "\n\n=================== Sql Logger ===================" +
			"\n{}" +
			"\n============ Sql Execute Time: {} ===========\n";
		log.info(sqlLogger, sql.trim(), StringUtil.format(statement.getLastExecuteTimeNano()));
	}

}
