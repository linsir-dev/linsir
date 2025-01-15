package com.linsir.core.data.protect;


import com.linsir.core.exception.InvalidUsageException;
import com.linsir.core.util.ContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * description：加密，解密 Mybatis TypeHandler实现
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/15 1:34
 */
@Slf4j
public class DefaultEncryptTypeHandler extends BaseTypeHandler<String> {

    private DataEncryptHandler dataEncryptHandler;

    /**
     * 暴露这个方法，以便BindQuery查询时调用以加密后匹配密文
     * @return
     */
    protected DataEncryptHandler getDataEncryptHandler() {
        if(this.dataEncryptHandler == null) {
            this.dataEncryptHandler = ContextHolder.getBean(DataEncryptHandler.class);
            if(this.dataEncryptHandler == null) {
                throw new InvalidUsageException("无法获取 DataEncryptHandler 数据加解密的实现类，请检查！");
            }
        }
        return this.dataEncryptHandler;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        String encryptedValue = getDataEncryptHandler().encrypt(parameter);
        ps.setString(i, encryptedValue);
        log.debug("字段保护 - 存储前加密：{} -> {}", parameter, encryptedValue);
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return decryptValue(value);
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return decryptValue(value);
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return decryptValue(value);
    }

    protected String decryptValue(String value) {
        String decryptedValue = getDataEncryptHandler().decrypt(value);
        log.debug("字段保护 - 读取后解密：{} -> {}", value, decryptedValue);
        return decryptedValue;
    }
}

