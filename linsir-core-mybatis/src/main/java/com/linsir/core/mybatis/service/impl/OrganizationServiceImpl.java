package com.linsir.core.mybatis.service.impl;


import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.linsir.core.constant.SymbolConstant;
import com.linsir.core.constant.TypeConstant;
import com.linsir.core.mybatis.config.BaseConfig;
import com.linsir.core.mybatis.entity.Organization;
import com.linsir.core.mybatis.exception.BusinessException;
import com.linsir.core.mybatis.mapper.OrganizationMapper;
import com.linsir.core.mybatis.service.OrganizationService;
import com.linsir.core.mybatis.util.BeanUtils;
import com.linsir.core.mybatis.util.S;
import com.linsir.core.mybatis.util.V;
import com.linsir.core.mybatis.vo.LabelValue;
import com.linsir.core.mybatis.vo.OrganizationVO;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * description：OrganizationServiceImpl
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/2/15 20:22
 */
@Service
public class OrganizationServiceImpl extends BaseServiceImpl<OrganizationMapper, Organization> implements OrganizationService {

    @Override
    public boolean createEntity(Organization organization) {
        enhanceOrganization(organization);
        return super.createEntity(organization);
    }


    /**
     * 增强
     * @param organization
     */
    private void enhanceOrganization(Organization organization) {

        //自动生成组织编码
        Snowflake snowflake = IdUtil.getSnowflake(1,1);
        organization.setCode("ZZBM-"+snowflake.nextId());

        if (!TypeConstant.TREE_ROOT_ID.equals(organization.getParentId().toString())) {
            //获取父对象
            Organization parentOrg = getEntity(organization.getParentId());

            if (parentOrg != null) {
                //设置公司ID
                if(TypeConstant.DICTCODE_ORG_TYPE.COMP.name().equals(parentOrg.getType())) {
                    organization.setRootOrgId(parentOrg.getId());
                }else {
                    if (TypeConstant.DICTCODE_ORG_TYPE.COMP.name().equals(organization.getType())){
                        throw new BusinessException("exception.business.orgService.deptHasComp");
                    }
                    organization.setRootOrgId(parentOrg.getRootOrgId());
                }
            }
        }

    }



    @Override
    public List<Long> getChildOrgIds(String rootOrgId) {
        if(rootOrgId == null){
            return Collections.emptyList();
        }
        //获取父对象
        Organization parentOrg = getEntity(rootOrgId);
        LambdaQueryWrapper<Organization> select = Wrappers.lambdaQuery();
        if (parentOrg != null) {
            String parentIds;
            if (parentOrg.getParentIdsPath()==null)
            {
                parentIds = rootOrgId + SymbolConstant.SEPARATOR_COMMA;
            }
            else {
                if (parentOrg.getParentIdsPath().endsWith(SymbolConstant.SEPARATOR_COMMA))
                {
                    parentIds = parentOrg.getParentIdsPath() + rootOrgId;
                }else {
                    parentIds = parentOrg.getParentIdsPath() + SymbolConstant.SEPARATOR_COMMA + rootOrgId;
                }
            }
            select.likeRight(Organization::getParentIdsPath, parentIds);
        }
        select.orderByAsc(Organization::getSortId);

        return getValuesOfField(select, Organization::getId);
    }



    @Override
    public List<OrganizationVO> getOrgTree(String rootOrgId) {
        LambdaQueryWrapper<Organization> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByAsc(Organization::getSortId);
        Organization parentOrg = getEntity(rootOrgId);
        if (parentOrg != null) {
            String parentIds = parentOrg.getParentIdsPath() == null ? rootOrgId : S.joinWith(SymbolConstant.SEPARATOR_COMMA, parentOrg.getParentIdsPath(), rootOrgId);
            queryWrapper.likeRight(Organization::getParentIdsPath, parentIds);
        }
        List<Organization> orgList = getEntityList(queryWrapper);
        if (V.isEmpty(orgList)) {
            return Collections.emptyList();
        }
        List<OrganizationVO> orgVOList = BeanUtils.convertList(orgList, OrganizationVO.class);
        return BeanUtils.buildTree(orgVOList, rootOrgId);
    }

    @Override
    public List<LabelValue> getSimpleOrgTree(String rootOrgId) {
        LambdaQueryWrapper<Organization> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper
                .select(Organization::getName, Organization::getId, Organization::getCode, Organization::getParentId)
                .orderByAsc(Organization::getSortId);
        Organization parentOrg = getEntity(rootOrgId);
        if (parentOrg != null) {
            String parentIds = parentOrg.getParentIdsPath() == null ? rootOrgId : S.joinWith(SymbolConstant.SEPARATOR_COMMA, parentOrg.getParentIdsPath(), rootOrgId);
            queryWrapper.likeRight(Organization::getParentIdsPath, parentIds);
        }
        return getLabelValueList(queryWrapper);
    }

    @Override
    public List<String> getParentOrgIds(String orgId) {
        Organization parentOrg = getEntity(orgId);
        if (parentOrg == null || parentOrg.getParentIdsPath() == null) {
            return Collections.emptyList();
        }
        return S.splitToList(parentOrg.getParentIdsPath());
    }

    @Override
    public List<Long> getOrgIdsByManagerId(String managerId) {
        LambdaQueryWrapper<Organization> queryWrapper = Wrappers.<Organization>lambdaQuery()
                .eq(Organization::getManagerId, managerId).orderByAsc(Organization::getSortId);
        return getValuesOfField(queryWrapper, Organization::getId);
    }

    @Override
    public Map<Long, LabelValue> getLabelValueMap(List<Long> orgIds) {
        LambdaQueryWrapper<Organization> queryWrapper = Wrappers.<Organization>lambdaQuery()
                .select(Organization::getName, Organization::getId, Organization::getCode)
                .in(Organization::getId, orgIds);
        // 返回构建条件
        return getEntityList(queryWrapper).stream().collect(
                Collectors.toMap(ent -> ent.getId(),
                        ent -> new LabelValue(ent.getName(), ent.getId()).setExt(ent.getCode())));
    }

    @Override
    public String getTenantRootOrgId(String tenantId) {
        return getMapper().getTenantRootOrgId(tenantId, BaseConfig.getActiveFlagValue());
    }
}
