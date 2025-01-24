package com.linsir.core.mybatis.data.protect;

/**
 * description：加密，解密 处理器接口
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/15 1:32
 */
public interface DataEncryptHandler {

    /**
     * 加密
     * @param fieldVal
     * @return
     */
    String encrypt(String fieldVal);

    /**
     * 解密
     * @param encryptedStr
     * @return
     */
    String decrypt(String encryptedStr);

}
