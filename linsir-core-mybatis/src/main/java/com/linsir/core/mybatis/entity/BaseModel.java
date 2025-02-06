package com.linsir.core.mybatis.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 动态实体模型
 * @author mazc@dibo.ltd
 * @version v3.0
 * @date 2023/5/25
 */
@Getter
@Setter
@Accessors(chain = true)
public class BaseModel extends BaseEntity<Long> {
    private static final long serialVersionUID = 10204L;

    /**
     * 租户ID
     */
    @JsonIgnore
    @TableField
    private String tenantId;
}
