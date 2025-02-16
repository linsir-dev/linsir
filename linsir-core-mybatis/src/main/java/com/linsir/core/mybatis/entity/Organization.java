package com.linsir.core.mybatis.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * description：Organization
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/2/15 23:31
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("organization")
public class Organization extends BaseTreeModel {

    private String name;

    private String type;

    private String code;

    private Long rootOrgId;

    private String description;

    private String contact;

    private String managerId;

    private Long sortId;

    private String status;

    private String deputyManagerIds;

}
