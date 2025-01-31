package com.linsir.core.log.vo;


import com.linsir.core.code.ICode;
import com.linsir.core.code.ResultCode;
import com.linsir.core.results.R;
import jakarta.annotation.Resource;

import java.io.Serializable;

/**
 * description：
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/31 17:36
 */
public class LogResult<T> implements R<Integer,String,T> , Serializable {

    private Integer code;

    private String msg;

    private T data;


    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }

    @Override
    public T getData() {
        return this.data;
    }


    /**
     * 默认的
     * @param code
     * @param msg
     * @param data
     */
    public LogResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 默认
     * @param code
     */
    public LogResult(ICode code) {
        this.code = code.getCode();
        this.msg = code.getMsg();
        this.data = null;
    }

    /**
     * 自动逸信息
     * @param code
     * @param msg
     */
    public LogResult(ICode code,String msg) {
        this.code = code.getCode();
        this.msg = msg;
        this.data = null;
    }

    /**
     * 返回
     * @return
     */
    public static R FAIL_EXCEPTION()
    {
        return new LogResult(ResultCode.FAIL_EXCEPTION);
    }

    /**
     * 自定义信息
     * @param msg
     * @return
     */
    public static R FAIL_EXCEPTION(String msg)
    {
        return new LogResult(ResultCode.FAIL_EXCEPTION,msg);
    }

    /**
     * 参数异常
     * @param msg
     * @return
     */
    public static R FAIL_INVALID_PARAM(String msg)
    {
        return new LogResult(ResultCode.FAIL_INVALID_PARAM,msg);
    }

    /**
     * 发现
     * @param msg
     * @return
     */
    public static R FAIL_NOT_FOUND(String msg){
        return new LogResult(ResultCode.FAIL_NOT_FOUND,msg);
    }

    public static R FAIL_OPERATION(String msg)
    {
        return new LogResult(ResultCode.FAIL_OPERATION,msg);
    }
}
