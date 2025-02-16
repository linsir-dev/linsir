package com.linsir.core.mybatis.vo;


import com.linsir.core.mybatis.binding.annotation.BindDict;
import com.linsir.core.mybatis.binding.annotation.BindEntityList;
import com.linsir.core.mybatis.binding.annotation.BindField;
import com.linsir.core.mybatis.entity.Organization;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * description：OrganizationVO
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/2/16 13:30
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class OrganizationVO extends Organization {

    // 数据字典关联
    @BindDict(type="ORG_TYPE", field = "type")
    private LabelValue typeLabel;

    @BindField(entity = Organization.class,field = "name",condition = "this.parent_id=id")
    private String parentName;

    @BindEntityList(entity = Organization.class, condition = "this.id=parent_id")
    private List<OrganizationVO> children;


}
