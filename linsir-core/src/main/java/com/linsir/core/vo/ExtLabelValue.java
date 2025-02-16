package com.linsir.core.vo;


import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * description：ExtLabelValue 用户扩展
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/2/16 20:57
 */
@Data
public class ExtLabelValue implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public ExtLabelValue() {

    }

    public ExtLabelValue(String label, Object value) {
        this.value = value;
        this.label = label;
    }
    private String label;

    private Object value;

    private Object ext;

    private Object parentId;

    private List<ExtLabelValue> children;
}
