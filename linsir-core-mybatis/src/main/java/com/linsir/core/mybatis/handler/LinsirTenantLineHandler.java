package com.linsir.core.mybatis.handler;


import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.linsir.core.constant.LinsirConstant;
import com.linsir.core.mybatis.props.MybatisPlusProperties;
import com.linsir.core.utils.Func;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import org.springframework.stereotype.Component;

/**
 * description：租户句柄
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/2/1 21:22
 */
@Slf4j
@Component
public class LinsirTenantLineHandler implements TenantLineHandler {

    private MybatisPlusProperties properties;

    public LinsirTenantLineHandler(MybatisPlusProperties mybatisPlusProperties) {
        this.properties = mybatisPlusProperties;
    }
    /**
     * 获取租户code
     * @return
     */
    @Override
    public Expression getTenantId() {
        /*默认的租户id*/
        /*return new StringValue(Func.toStr(SecureUtil.getTenantId(), BladeConstant.ADMIN_TENANT_ID));*/
        return new StringValue(Func.toStr(null, LinsirConstant.ADMIN_TENANT_ID));
    }

    /**
     * 租户数据库字段
     * @return
     */
    @Override
    public String getTenantIdColumn() {
        return LinsirConstant.DB_TENANT_COLUMN;
    }

    @Override
    public boolean ignoreTable(String tableName) {
        /*默认是需要插入 tenant_code*/
        boolean result = false;

        String ignoreTables = properties.getIgnoreTables();

        if (tableName.startsWith("sys_")) {
            log.info("表：{},以sys_开头，默认为系统表", tableName);
            result = true;
        }
        if (ignoreTables!=null)
        {
            if (ignoreTables.contains(tableName))
            {
                result = true;
            }
        }
        return result;
    }
}
