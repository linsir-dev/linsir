
package com.linsir.core.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Map相关工具类
 * @author JerryMa
 * @version v2.6.0
 * @date 2022/6/15
 * Copyright © diboot.com
 */
@Slf4j
public class MapUtils {

    /**
     * 忽略key大小写，兼容多库等场景
     * @param map
     * @param key
     * @return
     * @param <T>
     */
    public static <T> T getIgnoreCase(Map<String, T> map, String key) {
        if(map == null) {
            return null;
        }
        if(map.containsKey(key)) {
            return map.get(key);
        }
        if(S.isAllUpperCase(key)) {
            return map.get(key.toLowerCase());
        }
        return map.get(key.toUpperCase());
    }

    /**
     * 忽略key大小写，兼容多库等场景
     * @param map
     * @param key
     * @return
     * @param <T>
     */
    public static <T> T getIgnoreCase(Map<String, T> map, String key, T defaultVal) {
        if(map == null) {
            return defaultVal;
        }
        return getIgnoreCase(map, key);
    }

    /**
     * 构建ResultMap为实体
     * @param dataMap
     * @param entityClass
     * @return
     * @param <T>
     */
    public static <T> T buildEntity(Map<String, Object> dataMap, Class<T> entityClass) {
        // 字段映射
        if(V.isEmpty(dataMap)){
            return null;
        }
        return JSON.parseObject(JSON.stringify(dataMap), entityClass);
    }

    /**
     * 构建ResultMap为实体列表
     * @param resultListMap
     * @param entityClass
     * @return
     * @param <T>
     */
    public static <T> List<T> buildEntityList(List<Map<String, Object>> resultListMap, Class<T> entityClass) {
        if(V.isEmpty(resultListMap)){
            return Collections.emptyList();
        }
        return JSON.parseArray(JSON.stringify(resultListMap), entityClass);
    }

}
