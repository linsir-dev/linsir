package com.linsir.core.results;

/**
 * @author ：linsir
 * @description：三联体返回结果
 * @date ：2025/1/6 21:52
 */
public interface R<Code,Msg,Data>{

    Code getCode();

    Msg getMsg();

    Data getData();
}
