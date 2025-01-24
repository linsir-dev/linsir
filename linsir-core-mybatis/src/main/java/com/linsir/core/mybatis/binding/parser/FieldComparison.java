package com.linsir.core.mybatis.binding.parser;


import com.linsir.core.mybatis.binding.query.Comparison;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * description：字段
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/14 23:41
 */
@Getter
@Setter
@Accessors(chain = true)
public class FieldComparison implements Serializable {
    private static final long serialVersionUID = -1080962768714815036L;

    private String fieldName;

    private Comparison comparison;

    private Object value;

    public FieldComparison(){}

    public FieldComparison(String fieldName, Comparison comparison, Object value) {
        this.fieldName = fieldName;
        this.comparison = comparison;
        this.value = value;
    }

}
