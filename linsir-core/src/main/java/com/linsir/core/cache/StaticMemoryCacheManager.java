package com.linsir.core.cache;


import com.linsir.core.exception.InvalidUsageException;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;

import java.util.ArrayList;
import java.util.List;

/**
 * description：静态不变化的数据内存缓存manager
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/15 0:32
 */
public class StaticMemoryCacheManager extends BaseMemoryCacheManager implements BaseCacheManager{

    public StaticMemoryCacheManager(String... cacheNames){
        List<Cache> caches = new ArrayList<>();
        for(String cacheName : cacheNames){
            caches.add(new ConcurrentMapCache(cacheName));
        }
        setCaches(caches);
        super.afterPropertiesSet();
    }

    @Override
    public void clearOutOfDateData(String cacheName) {
        throw new InvalidUsageException("StaticMemoryCacheManager 缓存不存在过期，不支持清理！");
    }
}
