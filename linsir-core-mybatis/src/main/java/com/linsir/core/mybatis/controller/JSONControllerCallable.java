package com.linsir.core.mybatis.controller;

import com.linsir.core.results.R;

/**
 * description:
 *
 * @author [linsir]
 * @version 0.0.1
 * @date 2022/08/08 16:31:37
 */
@FunctionalInterface
public interface JSONControllerCallable {

    R execute() throws Exception;
}
