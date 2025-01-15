package com.linsir.core.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * description：排序参数 DTO
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/15 1:39
 */
@Getter
@Setter
@Accessors(chain = true)
public class SortParamDTO<ID extends Serializable> implements Serializable {
    private static final long serialVersionUID = 30303L;

    /**
     * 操作对象ID
     */
    @NotNull(message = "{validation.id.NotNull.message}")
    private ID id;

    /**
     * 新序号
     */
    @NotNull(message = "{validation.newSortId.NotNull.message}")
    private Long newSortId;

    /**
     * 旧序号
     */
    private Long oldSortId;

    /**
     * 新的父节点ID（未改化传原父节点ID）
     * <p>
     * Tree 结构数据：应指定 parentIdField，及传递 newParentId；当跨层级时无需传递 oldSortId
     */
    private ID newParentId;

}

