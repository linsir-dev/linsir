package com.linsir.core.mybatis.handler;


import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.linsir.core.constant.RoleConstant;
import com.linsir.core.constant.TypeConstant;
import com.linsir.core.mybatis.props.MybatisPlusProperties;
import com.linsir.core.utils.Func;
import lombok.extern.log4j.Log4j2;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.schema.Column;

import java.util.List;

/**
 * description：LinsirTenantLineHandler
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/2/6 5:03
 */
@Log4j2
public class LinsirTenantLineHandler implements TenantLineHandler {

    private final MybatisPlusProperties properties;

    public LinsirTenantLineHandler(MybatisPlusProperties mybatisPlusProperties) {
        this.properties = mybatisPlusProperties;
    }

    @Override
    public Expression getTenantId() {
        /*
        * 租户编码这里对没有忽略的表，在查询的时候，会查询租户tenant_id，在插入的时候，会自动插入租户tenant_id
        * */
        log.info("获取租户ID，这里临时使用默认的用户{}", RoleConstant.ADMIN_TENANT_ID);
        return new StringValue(Func.toStr(null,RoleConstant.ADMIN_TENANT_ID));
    }

    @Override
    public boolean ignoreInsert(List<Column> columns, String tenantIdColumn) {
        return TenantLineHandler.super.ignoreInsert(columns, tenantIdColumn);
    }


    @Override
    public boolean ignoreTable(String tableName) {
        boolean isIgnoreTable = false;
        if (properties.getIgnoreTables()!=null)
        {
            if (properties.getIgnoreTables().contains(tableName))
            {
                isIgnoreTable = true;
            }
        }
        return isIgnoreTable;
    }

    @Override
    public String getTenantIdColumn() {
        return "tenant_id";
    }
}
