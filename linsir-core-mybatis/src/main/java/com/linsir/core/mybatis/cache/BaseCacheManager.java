package com.linsir.core.mybatis.cache;

import java.util.concurrent.Callable;

/**
 * description：缓存manager父类
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/15 0:29
 */
public interface BaseCacheManager {

    /**
     * 获取缓存对象
     * @param objKey
     * @param <T>
     * @return
     */
    <T> T getCacheObj(String cacheName, Object objKey, Class<T> tClass);

    /**
     * 获取缓存对象, 如果找不到, 则生成初始值, 放入缓存, 并返回
     *
     * @param cacheName    cache名称
     * @param objKey       查找的key
     * @param initSupplier 初始值提供者
     * @param <T>          缓存对象类型
     * @return 缓存对象
     */
    <T> T getCacheObj(String cacheName, Object objKey, Callable<T> initSupplier);

    /**
     * 获取缓存对象
     * @param objKey
     * @return
     */
    String getCacheString(String cacheName, Object objKey);

    /**
     * 缓存对象
     * @param cacheName
     * @param objKey
     * @param obj
     */
    void putCacheObj(String cacheName, Object objKey, Object obj);

    /**
     * 删除缓存对象
     * @param cacheName
     * @param objKey
     */
    void removeCacheObj(String cacheName, Object objKey);

    /**
     * 尚未初始化的
     * @param cacheName
     * @return
     */
    boolean isUninitializedCache(String cacheName);

    /**
     * 清理所有过期的数据：系统空闲时调用
     */
    void clearOutOfDateData(String cacheName);

}

