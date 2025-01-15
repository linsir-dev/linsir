package com.linsir.core.data.copy;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * description：
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/15 1:29
 */
@Getter
@Setter
@Accessors(chain = true)
public class CopyInfo {

    private String form;

    private String to;

    private boolean override;

    public CopyInfo(String from, String to, boolean override) {
        this.form = from;
        this.to = to;
        this.override = override;
    }

}
