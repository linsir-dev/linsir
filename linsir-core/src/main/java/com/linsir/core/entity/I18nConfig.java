package com.linsir.core.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * description：国际化配置
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/15 1:42
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("dbt_i18n_config")
public class I18nConfig extends BaseEntity<String> {
    private static final long serialVersionUID = 11501L;

    /**
     * type字段的关联字典
     */
    public static final String DICT_I18N_TYPE = "I18N_TYPE";

    /**
     * 租户ID
     */
    @JsonIgnore
    @TableField
    private String tenantId;

    /**
     * 类型
     */
    private String type;

    /**
     * 语言
     */
    @NotNull
    private String language;

    /**
     * 资源标识
     */
    @NotNull
    private String code;

    /**
     * 内容
     */
    @NotNull
    private String content;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}

