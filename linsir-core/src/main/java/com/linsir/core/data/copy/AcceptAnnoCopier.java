package com.linsir.core.data.copy;


import com.linsir.core.util.V;
import com.linsir.core.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.BeanWrapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * description：Accept注解拷贝器
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/15 1:29
 */
@Slf4j
public class AcceptAnnoCopier {
    /**
     * 注解缓存
     */
    private static final Map<String, List<CopyInfo>> CLASS_ACCEPT_ANNO_CACHE_MAP = new ConcurrentHashMap<>();

    /**
     * 基于注解拷贝属性
     * @param source
     * @param target
     */
    public static void copyAcceptProperties(Object source, Object target){
        String key = target.getClass().getName();
        // 初始化
        if(!CLASS_ACCEPT_ANNO_CACHE_MAP.containsKey(key)){
            List<Field> annoFieldList = BeanUtils.extractFields(target.getClass(), Accept.class);
            if(V.isEmpty(annoFieldList)){
                CLASS_ACCEPT_ANNO_CACHE_MAP.put(key, Collections.EMPTY_LIST);
            }
            else{
                List<CopyInfo> annoDefList = new ArrayList<>(annoFieldList.size());
                for(Field fld : annoFieldList){
                    Accept accept = fld.getAnnotation(Accept.class);
                    CopyInfo copyInfo = new CopyInfo(accept.name(), fld.getName(), accept.override());
                    annoDefList.add(copyInfo);
                }
                CLASS_ACCEPT_ANNO_CACHE_MAP.put(key, annoDefList);
            }
        }
        // 解析copy
        List<CopyInfo> acceptAnnos = CLASS_ACCEPT_ANNO_CACHE_MAP.get(key);
        if(V.isEmpty(acceptAnnos)){
            return;
        }
        BeanWrapper beanWrapper = BeanUtils.getBeanWrapper(target);
        for(CopyInfo copyInfo : acceptAnnos){
            if(!copyInfo.isOverride()){
                Object targetValue = BeanUtils.getProperty(target, copyInfo.getTo());
                if(targetValue != null){
                    log.debug("目标对象{}已有值{}，copyAcceptProperties将忽略.", key, targetValue);
                    continue;
                }
            }
            Object sourceValue = SystemMetaObject.forObject(source).getValue(copyInfo.getForm());
            if(sourceValue != null) {
                beanWrapper.setPropertyValue(copyInfo.getTo(), sourceValue);
            }
        }
    }

}

