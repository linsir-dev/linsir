/*
 * Copyright (c) 2015-2023, www.dibo.ltd (service@dibo.ltd).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.linsir.core.mybatis.handler;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.plugins.handler.MultiDataPermissionHandler;
import com.linsir.core.code.ResultCode;
import com.linsir.core.mybatis.data.access.DataAccessAnnoCache;
import com.linsir.core.mybatis.data.access.DataScopeManager;
import com.linsir.core.mybatis.exception.InvalidUsageException;
import com.linsir.core.mybatis.holder.ThreadLocalHolder;
import com.linsir.core.mybatis.util.ContextHolder;
import com.linsir.core.mybatis.util.S;
import com.linsir.core.mybatis.util.V;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 数据访问控制处理器
 *
 * @author wind
 * @version v2.9
 * @date 2023/03/29
 */
public class DataAccessControlHandler implements MultiDataPermissionHandler {
    private static final Logger log = LoggerFactory.getLogger(DataAccessControlHandler.class);
    /**
     * 获取全部自定义数据权限拦截器
     */
    private Map<Class<?>, DataScopeManager> entityClassToPermissionMap = null;

    /**
     * 获取数据权限拦截器的实现类
     * @param entityClass
     * @return
     */
    public synchronized DataScopeManager getDataScopeManager(Class<?> entityClass) {
        if(entityClassToPermissionMap == null) {
            entityClassToPermissionMap = new LinkedHashMap<>();
            List<DataScopeManager> dataProtectionHandlers = ContextHolder.getBeans(DataScopeManager.class);
            if(V.notEmpty(dataProtectionHandlers)) {
                for (DataScopeManager protectionHandler : dataProtectionHandlers) {
                    List<Class<?>> entityClasses = protectionHandler.getEntityClasses();
                    if(V.notEmpty(entityClasses)) {
                        for (Class<?> entityCls : entityClasses) {
                            if(entityClassToPermissionMap.containsKey(entityCls)) {
                                throw new InvalidUsageException(ResultCode.INVALID_OPERATION,"多个数据权限拦截实现类作用于Entity: {}，请检查！", entityCls.getName());
                            }
                            entityClassToPermissionMap.put(entityCls, protectionHandler);
                            log.info("缓存 Entity: {} 与数据权限拦截实现类：{} 对应关系", entityCls.getName(), protectionHandler.getClass().getName());
                        }
                    }
                }
            }
        }
        DataScopeManager dataScopeManager = entityClassToPermissionMap.get(entityClass);
        if(dataScopeManager != null) {
            log.debug("获取到 {} 类的数据范围控制实现: {}", entityClass.getSimpleName(), dataScopeManager.getClass().getSimpleName());
        }
        return dataScopeManager;
    }

    @Override
    public Expression getSqlSegment(Table table, Expression where, String mappedStatementId) {
        // 如果忽略此来源
        if (ThreadLocalHolder.ignoreInterceptor()) {
            return null;
        }
        TableInfo tableInfo = TableInfoHelper.getTableInfo(S.removeEsc(table.getName()));
        // 无权限检查点注解，不处理
        if (tableInfo == null || tableInfo.getEntityType() == null || !DataAccessAnnoCache.hasDataAccessCheckpoint(tableInfo.getEntityType())) {
            return null;
        }
        return buildDataAccessExpression(table, tableInfo.getEntityType());
    }

    /**
     * 构建数据权限检查条件
     *
     * @param mainTable
     * @param entityClass
     * @return
     */
    private Expression buildDataAccessExpression(Table mainTable, Class<?> entityClass) {
        return DataAccessAnnoCache.getDataPermissionMap(entityClass).entrySet().stream().map(entry -> {
            DataScopeManager checkImpl = getDataScopeManager(entityClass);
            if (checkImpl == null) {
                log.warn("未获取到 {} 类的数据范围控制实现，请检查DataScopeManager实现类是否正确实例化并指定作用于此实体！", entityClass.getSimpleName());
                throw new InvalidUsageException("无法从上下文中获取数据权限的接口实现：DataScopeManager");
            }
            List<? extends Serializable> idValues = checkImpl.getAccessibleIds(entityClass, entry.getKey());
            if (idValues == null) {
                return null;
            }
            String idCol = entry.getValue();
            if (mainTable.getAlias() != null) {
                idCol = mainTable.getAlias().getName() + "." + idCol;
            }
            if (idValues.isEmpty()) {
                return new IsNullExpression().withLeftExpression(new Column(idCol));
            } else if (idValues.size() == 1) {
                EqualsTo equalsTo = new EqualsTo();
                equalsTo.setLeftExpression(new Column(idCol));
                if (idValues.get(0) instanceof Long) {
                    equalsTo.setRightExpression(new LongValue((Long) idValues.get(0)));
                } else {
                    equalsTo.setRightExpression(new StringValue(S.defaultValueOf(idValues.get(0))));
                }
                return equalsTo;
            } else {
                String conditionExpr = idCol + " IN ";
                if (idValues.get(0) instanceof Long) {
                    conditionExpr += "(" + S.join(idValues, ", ") + ")";
                } else {
                    conditionExpr += "('" + S.join(idValues, "', '") + "')";
                }
                try {
                    return CCJSqlParserUtil.parseCondExpression(conditionExpr);
                } catch (JSQLParserException e) {
                    log.warn("解析condition异常: {}", conditionExpr, e);
                }
            }
            return null;
        }).filter(Objects::nonNull).reduce(AndExpression::new).orElse(null);
    }

}
