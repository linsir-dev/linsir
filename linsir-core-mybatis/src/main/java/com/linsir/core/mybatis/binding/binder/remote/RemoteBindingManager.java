package com.linsir.core.mybatis.binding.binder.remote;


import com.fasterxml.jackson.core.type.TypeReference;
import com.linsir.core.exception.InvalidUsageException;
import com.linsir.core.mybatis.util.V;
import com.linsir.core.util.ContextHolder;
import com.linsir.core.util.JSON;
import com.linsir.core.vo.jsonResults.JsonResult;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * description：远程绑定manager
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/14 23:01
 */
@Slf4j
public class RemoteBindingManager {

    /**
     * RemoteBindingProvider 实例缓存
     */
    private static Map<String, RemoteBindingProvider> MODULE_PROVIDER_MAP;

    /**
     * 从远程接口抓取 Entity List
     * @param module
     * @param remoteBindDTO
     * @param entityClass
     * @param <T>
     * @return
     */
    public static <T> List<T> fetchEntityList(String module, RemoteBindDTO remoteBindDTO, Class<T> entityClass) {
        remoteBindDTO.setResultType("Entity");
        RemoteBindingProvider bindingProvider = getRemoteBindingProvider(module);
        JsonResult<String> jsonResult = bindingProvider.loadBindingData(remoteBindDTO);
        if(V.equals(jsonResult.getCode(), 0)){
            log.debug("获取到绑定数据: {}", jsonResult.getData());
            List<T> entityList = JSON.parseArray(jsonResult.getData(), entityClass);
            return entityList;
        }
        else{
            log.warn("获取绑定数据失败: {}", jsonResult.getMsg());
            return Collections.EMPTY_LIST;
        }
    }

    /**
     * 从远程接口抓取 Map List
     * @param module
     * @param remoteBindDTO
     * @return
     */
    public static List<Map<String, Object>> fetchMapList(String module, RemoteBindDTO remoteBindDTO) {
        remoteBindDTO.setResultType("Entity");
        RemoteBindingProvider bindingProvider = getRemoteBindingProvider(module);
        JsonResult<String> jsonResult = bindingProvider.loadBindingData(remoteBindDTO);
        if(V.equals(jsonResult.getCode(), 0)){
            log.debug("获取到绑定数据: {}", jsonResult.getData());
            return JSON.parseArray(jsonResult.getData(), new TypeReference<List<Map<String, Object>>>(){});
        }
        else{
            log.warn("获取绑定数据失败: {}", jsonResult.getMsg());
            return Collections.EMPTY_LIST;
        }
    }

    /**
     * 获取实例
     * @return
     */
    private synchronized static RemoteBindingProvider getRemoteBindingProvider(String module){
        if(MODULE_PROVIDER_MAP == null){
            MODULE_PROVIDER_MAP = new ConcurrentHashMap<>();
        }
        return MODULE_PROVIDER_MAP.computeIfAbsent(module, key -> {
            RemoteBindingProviderFactory factory = ContextHolder.getBean(RemoteBindingProviderFactory.class);
            if(factory == null) {
                throw new InvalidUsageException("RemoteBindingProviderFactory 未实现，无法使用远程绑定功能！");
            }
            return factory.create(module);
        });
    }

}