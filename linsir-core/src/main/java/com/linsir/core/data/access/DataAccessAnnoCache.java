package com.linsir.core.data.access;


import com.linsir.core.util.V;
import com.linsir.core.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * description：数据访问权限的注解缓存
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/15 1:15
 */
@Slf4j
public class DataAccessAnnoCache {
    /**
     * 注解缓存
     */
    private static final Map<String, Map<String, String>> DATA_PERMISSION_ANNO_CACHE = new ConcurrentHashMap<>();

    /**
     * 是否有检查点注解
     *
     * @param entityClass
     * @return
     */
    public static boolean hasDataAccessCheckpoint(Class<?> entityClass) {
        return !DATA_PERMISSION_ANNO_CACHE.computeIfAbsent(entityClass.getName(), k -> initClassCheckpoint(entityClass)).isEmpty();
    }

    /**
     * 获取数据权限的用户类型列名
     *
     * @param entityClass
     * @return
     */
    public static Map<String, String> getDataPermissionMap(Class<?> entityClass) {
        return DATA_PERMISSION_ANNO_CACHE.computeIfAbsent(entityClass.getName(), k -> initClassCheckpoint(entityClass));
    }

    /**
     * 初始化entityDto的检查点缓存
     *
     * @param entityClass
     * @return
     */
    private static Map<String, String> initClassCheckpoint(Class<?> entityClass) {
        List<Field> fieldList = BeanUtils.extractFields(entityClass, DataAccessCheckpoint.class);
        if (V.notEmpty(fieldList)) {
            Map<String, String> results = new HashMap<>();
            for (Field fld : fieldList) {
                results.put(fld.getName(), BeanUtils.getColumnName(fld));
            }
            return results;
        }
        else {
            return Collections.emptyMap();
        }
    }

}

