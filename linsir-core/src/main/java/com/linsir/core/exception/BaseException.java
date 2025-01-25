package com.linsir.core.exception;


import com.linsir.core.code.ICode;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * description：基础业务类
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/25 5:23
 */
@Slf4j
public class BaseException extends RuntimeException implements IException{

    protected ICode code;
    /**
     * 基础的
     */
    public BaseException() {
            super();
    }

    /**
     * 基础的
     */
    public BaseException(String str) {
        super(str);
    }

    /**
     * 基础的
     * @param str
     * @param throwable
     */
    public BaseException(String str,Throwable throwable) {
        super(str,throwable);
    }

    protected  void logMsg(ICode code,String msg)
    {
        log.error("错误编码：{}, 错误状态：{},自定义的错误信息:{}",code.getCode(),code.status(),msg);
    }


    protected void logCode(ICode code)
    {
        log.error("错误编码：{}, 错误状态：{},错误信息:{}",code.getCode(),code.status(),code.getMsg());
    }


    /**
     * 转换为Map
     *
     * @return
     */
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>(8);
        map.put("code", getCode());
        map.put("msg", getMessage());
        return map;
    }

    @Override
    public ICode getCode() {
        return this.code;
    }

}
