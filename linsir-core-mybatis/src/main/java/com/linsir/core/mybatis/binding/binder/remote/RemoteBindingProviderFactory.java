package com.linsir.core.mybatis.binding.binder.remote;


/**
 * description：远程绑定工厂类
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/14 23:04
 */
public interface RemoteBindingProviderFactory {

    /**
     * 创建provider实例
     * @param module
     * @return
     */
    RemoteBindingProvider create(String module);

}
