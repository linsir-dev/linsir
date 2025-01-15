package com.linsir.core.controller;


import com.linsir.core.vo.IResult;

/**
 * @author linsir
 * @title: ControllerCallable
 * @projectName lins
 * @description:
 * @date 2021/12/10 0:14
 */
@FunctionalInterface
public interface ControllerCallable {

    /**
     * 执行业务方法
     *
     * @return
     */
    IResult execute() throws Exception;

}
