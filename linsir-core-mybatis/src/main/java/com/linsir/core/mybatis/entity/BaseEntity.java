package com.linsir.core.mybatis.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.linsir.core.tool.constant.CommonConstant;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author linsir
 * @title: BaseEntity
 * @projectName linsir
 * @description: Entity基础父类
 * @date 2022/3/19 23:18
 */
@Data
@Accessors(chain = true)
public abstract class BaseEntity<T extends Serializable> extends AbstractEntity<T> {
    private static final long serialVersionUID = -6776198764539598100L;
    /** 租户编码*/
    @TableField(fill = FieldFill.INSERT)
    private String tenantCode ;

    /**
     * 默认逻辑删除标记，is_deleted=0有效
     */
    @TableLogic
    @JsonIgnore
    @TableField(value = CommonConstant.COLUMN_IS_DELETED, select = false)
    private boolean deleted = false;

}
