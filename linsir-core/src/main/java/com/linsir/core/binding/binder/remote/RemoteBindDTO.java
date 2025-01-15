package com.linsir.core.binding.binder.remote;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * description：远程绑定DTO定义
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/14 23:01
 */
@Getter
@Setter
@Accessors(chain = true)
public class RemoteBindDTO implements Serializable {
    private static final long serialVersionUID = -3339006060332345228L;

    private String entityClassName;
    private List<String> selectColumns;
    private String refJoinCol;
    private Collection<?> inConditionValues;
    private List<String> additionalConditions;
    private String orderBy;
    private String resultType;

    public RemoteBindDTO() {
    }

    public RemoteBindDTO(Class<?> entityClass) {
        this.entityClassName = entityClass.getName();
    }

}
