package com.linsir.core.mybatis.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.linsir.core.mybatis.entity.Organization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * description：OrganizationMapper
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/2/15 23:33
 */
@Mapper
public interface OrganizationMapper extends BaseCrudMapper<Organization> {

    /**
     * 查询租户的根部门id
     *
     * @param tenantId
     * @param deleted
     * @return
     */
    @InterceptorIgnore(tenantLine = "true")
    @Select({"SELECT id FROM organization WHERE is_deleted = #{deleted} AND tenant_id = #{tenantId} AND (parent_id = '0' or parent_id is null)"})
    String getTenantRootOrgId(String tenantId, Object deleted);
}
