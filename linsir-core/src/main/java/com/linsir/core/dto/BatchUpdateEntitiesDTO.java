package com.linsir.core.dto;


import com.linsir.core.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * description：批量更新实体DTO
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/15 1:39
 */
@Getter
@Setter
@Accessors(chain = true)
public class BatchUpdateEntitiesDTO<T extends AbstractEntity<ID_TYPE>, ID_TYPE extends Serializable> implements Serializable {
    private static final long serialVersionUID = -3141680773920758263L;

    private List<ID_TYPE> idList;

    private T data;
}
