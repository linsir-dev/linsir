package com.linsir.core.mybatis.controller;


import com.linsir.core.results.R;

/**
 * @author linsir
 * @title: ControllerCallable
 * @projectName lins
 * @description:
 * @date 2021/12/10 0:14
 */
@FunctionalInterface
public interface ControllerCallable<T> {

    /**
     * 执行业务方法
     *
     * @return
     */
    R execute() throws Exception;

}
