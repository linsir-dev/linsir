package com.linsir.core.mybatis.cache;


import com.linsir.core.code.ResultCode;
import com.linsir.core.mybatis.exception.InvalidUsageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;

import java.util.concurrent.Callable;

/**
 * description：缓存manager父类
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/15 0:30
 */
@Slf4j
public abstract class BaseMemoryCacheManager extends SimpleCacheManager implements BaseCacheManager {

    /**
     * 获取缓存对象
     * @param objKey
     * @param <T>
     * @return
     */
    @Override
    public <T> T getCacheObj(String cacheName, Object objKey, Class<T> tClass){
        Cache cache = getCache(cacheName);
        T value = cache != null? cache.get(objKey, tClass) : null;
        if(log.isTraceEnabled()){
            log.trace("从缓存读取: {}.{} = {}", cacheName, objKey, value);
        }
        return value;
    }

    /**
     * 获取缓存对象, 如果找不到, 则生成初始值, 放入缓存, 并返回
     *
     * @param cacheName    cache名称
     * @param objKey       查找的key
     * @param initSupplier 初始值提供者
     * @param <T>          缓存对象类型
     * @return 缓存对象
     */
    @Override
    public <T> T getCacheObj(String cacheName, Object objKey, Callable<T> initSupplier) {
        Cache cache = getCache(cacheName);
        T value = cache != null ? cache.get(objKey, initSupplier) : null;
        if (log.isTraceEnabled()) {
            log.trace("从缓存读取: {}.{} = {}", cacheName, objKey, value);
        }
        return value;
    }

    /**
     * 获取缓存对象
     * @param objKey
     * @return
     */
    @Override
    public String getCacheString(String cacheName, Object objKey){
        return getCacheObj(cacheName, objKey, String.class);
    }

    /**
     * 缓存对象
     * @param cacheName
     * @param objKey
     * @param obj
     */
    @Override
    public void putCacheObj(String cacheName, Object objKey, Object obj){
        Cache cache = getCache(cacheName);
        if(cache != null) {
            cache.put(objKey, obj);
            if(log.isDebugEnabled()){
                ConcurrentMapCache mapCache = (ConcurrentMapCache)cache;
                log.debug("缓存: {} 新增-> {} , 当前size={}", cacheName, objKey, mapCache.getNativeCache().size());
            }
        }
        else {
            throw new InvalidUsageException(ResultCode.INVALID_OPERATION,"无法获取cache：{}，请检查是否初始化", cacheName);
        }
    }

    /**
     * 删除缓存对象
     * @param cacheName
     * @param objKey
     */
    @Override
    public void removeCacheObj(String cacheName, Object objKey){
        Cache cache = getCache(cacheName);
        if(cache != null) {
            cache.evict(objKey);
            if(log.isDebugEnabled()){
                ConcurrentMapCache mapCache = (ConcurrentMapCache)cache;
                log.debug("缓存删除: {}.{} , 当前size={}", cacheName, objKey, mapCache.getNativeCache().size());
            }
        }
        else {
            throw new InvalidUsageException(ResultCode.INVALID_OPERATION,"无法获取cache：{}，请检查是否初始化", cacheName);
        }
    }

    /**
     * 尚未初始化的
     * @param cacheName
     * @return
     */
    @Override
    public boolean isUninitializedCache(String cacheName){
        ConcurrentMapCache cache = (ConcurrentMapCache)getCache(cacheName);
        if(cache != null) {
            return cache.getNativeCache().isEmpty();
        }
        return true;
    }

}

