package com.linsir.core.mybatis.data.protect;

/**
 * description：数据脱敏 处理器接口
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/15 1:33
 */
public interface DataMaskHandler {

    /**
     * 脱敏
     * @param fieldVal
     * @return
     */
    String mask(String fieldVal);

}

