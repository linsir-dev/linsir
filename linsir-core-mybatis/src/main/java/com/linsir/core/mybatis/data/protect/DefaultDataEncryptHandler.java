package com.linsir.core.mybatis.data.protect;


import com.linsir.core.mybatis.util.Encryptor;

/**
 * description：加密，解密 处理器的默认实现类
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/15 1:33
 */
public class DefaultDataEncryptHandler implements DataEncryptHandler {

    protected String getSeed() {
        return Encryptor.getDefaultKey();
    }

    @Override
    public String encrypt(String fieldVal) {
        return Encryptor.encrypt(fieldVal, getSeed());
    }

    @Override
    public String decrypt(String encryptedStr) {
        return Encryptor.decrypt(encryptedStr, getSeed());
    }

}
