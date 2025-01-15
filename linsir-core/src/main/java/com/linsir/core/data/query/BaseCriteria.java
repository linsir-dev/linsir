package com.linsir.core.data.query;


import com.linsir.core.binding.query.Comparison;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Collection;

/**
 * description：基础查询条件
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/15 1:35
 */
@Getter
@Setter
@Accessors(chain = true)
public class BaseCriteria implements Serializable {
    private static final long serialVersionUID = 2012502786391342220L;

    protected String field;

    protected String comparison = Comparison.EQ.name();

    protected Object value;

    public BaseCriteria() {
    }

    public BaseCriteria(String field, Object value) {
        this.field = field;
        if(value instanceof Collection || (value != null && value.getClass().isArray())) {
            this.comparison = Comparison.IN.name();
        }
        this.value = value;
    }

    public BaseCriteria(String field, Comparison comparison, Object value) {
        this.field = field;
        this.comparison = comparison.name();
        this.value = value;
    }

    public BaseCriteria update(Comparison comparison, Object value) {
        this.comparison = comparison.name();
        this.value = value;
        return this;
    }

    public Comparison getComparison() {
        return Comparison.valueOf(comparison);
    }

    public String getComparisonName() {
        return comparison;
    }
}
