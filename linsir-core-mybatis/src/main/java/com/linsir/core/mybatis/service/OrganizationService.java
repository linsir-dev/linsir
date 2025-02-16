package com.linsir.core.mybatis.service;


import com.linsir.core.mybatis.entity.Organization;
import com.linsir.core.mybatis.vo.LabelValue;
import com.linsir.core.mybatis.vo.OrganizationVO;

import java.util.List;
import java.util.Map;

/**
 * description：Organization
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/2/15 20:21
 */
public interface OrganizationService extends BaseService<Organization> {


    /**
     * 获取全部子节点ID
     * @param rootOrgId
     * @return
     */
    List<Long> getChildOrgIds(String rootOrgId);

    /**
     * 获取指定根下的全部节点的组织树
     * @param rootOrgId
     * @return
     */
    List<OrganizationVO> getOrgTree(String rootOrgId);

    /**
     * 获取指定根下的全部节点的组织树
     * @param rootOrgId
     * @return
     */
    List<LabelValue> getSimpleOrgTree(String rootOrgId);

    /**
     * 获取当前部门节点所有上级部门id集合
     * @param orgId
     * @return
     */
    List<String> getParentOrgIds(String orgId);

    /**
     * 获取某负责人负责的相关部门ids
     * @param managerId 负责人id
     * @return
     */
    List<Long> getOrgIdsByManagerId(String managerId);

    /**
     * 获取id值-选项的映射Map
     * @param orgIds
     * @return
     */
    Map<Long, LabelValue> getLabelValueMap(List<Long> orgIds);

    /**
     * 获取租户的根节点id
     * @param tenantId
     * @return
     */
    String getTenantRootOrgId(String tenantId);
}
