package com.linsir.core.binding.parser;


import com.linsir.core.binding.annotation.*;
import com.linsir.core.util.V;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description：VO绑定注解的归类分组，用于缓存解析后的结果
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/14 23:23
 */
@SuppressWarnings("JavaDoc")
@Slf4j
public class BindAnnotationGroup {
    /**
     * Dictionary注解
     */
    private List<FieldAnnotation> bindDictAnnotations;
    /**
     * BindField分组Map
     */
    private Map<String, List<FieldAnnotation>> bindFieldGroupMap;
    /**
     * 实体关联注解
     */
    private List<FieldAnnotation> bindEntityAnnotations;
    /**
     * 实体集合关联注解
     */
    private List<FieldAnnotation> bindEntityListAnnotations;
    /**
     * BindFieldList分组Map
     */
    private Map<String, List<FieldAnnotation>> bindFieldListGroupMap;
    /**
     * count计数关联注解
     */
    private List<FieldAnnotation> bindCountAnnotations;
    /**
     * 国际化计数关联注解
     */
    private List<FieldAnnotation> bindI18nAnnotations;
    /**
     * 深度绑定实体
     */
    private List<FieldAnnotation> deepBindEntityAnnotations;
    /**
     * 深度绑定实体
     */
    private List<FieldAnnotation> deepBindEntityListAnnotations;
    /**
     * 需要序列
     */
    private boolean requireSequential = false;

    /**
     * 添加注解
     * @param fieldName
     * @param annotation
     */
    public void addBindAnnotation(String fieldName, Class<?> fieldClass, Annotation annotation){
        FieldAnnotation fieldAnnotation = new FieldAnnotation(fieldName, fieldClass, annotation);
        if(annotation instanceof BindDict){
            if(bindDictAnnotations == null){
                bindDictAnnotations = new ArrayList<>(4);
            }
            bindDictAnnotations.add(fieldAnnotation);
            requireSequential = true;
            return;
        }
        String key = null;
        try {
            if (annotation instanceof BindField) {
                BindField bindField = (BindField) annotation;
                key = bindField.entity().getName() + ":" + bindField.condition();
            } else if (annotation instanceof BindFieldList) {
                BindFieldList bindField = (BindFieldList) annotation;
                key = bindField.entity().getName() + ":" + bindField.condition() + ":" + bindField.orderBy();
            } else if (annotation instanceof BindCount) {
                BindCount bindCount = (BindCount) annotation;
                key = bindCount.entity().getName() + ":" + bindCount.condition();
            }
            else if (annotation instanceof BindEntity) {
                BindEntity bindEntity = (BindEntity) annotation;
                key = bindEntity.entity().getName();
            } else if (annotation instanceof BindEntityList) {
                BindEntityList bindEntity = (BindEntityList) annotation;
                key = bindEntity.entity().getName();
            }
        } catch (Exception e) {
            log.warn("获取绑定信息异常", e);
            return;
        }
        if(annotation instanceof BindField){
            if(bindFieldGroupMap == null){
                bindFieldGroupMap = new HashMap<>(4);
            }
            List<FieldAnnotation> list = bindFieldGroupMap.computeIfAbsent(key, k -> new ArrayList<>(4));
            list.add(fieldAnnotation);
        }
        else if(annotation instanceof BindEntity){
            if(bindEntityAnnotations == null){
                bindEntityAnnotations = new ArrayList<>(4);
            }
            bindEntityAnnotations.add(fieldAnnotation);
            if(((BindEntity)annotation).deepBind()){
                if(deepBindEntityAnnotations == null){
                    deepBindEntityAnnotations = new ArrayList<>(4);
                }
                deepBindEntityAnnotations.add(fieldAnnotation);
            }
        }
        else if(annotation instanceof BindEntityList){
            if(bindEntityListAnnotations == null){
                bindEntityListAnnotations = new ArrayList<>(4);
            }
            bindEntityListAnnotations.add(fieldAnnotation);
            if(((BindEntityList)annotation).deepBind()){
                if(deepBindEntityListAnnotations == null){
                    deepBindEntityListAnnotations = new ArrayList<>(4);
                }
                deepBindEntityListAnnotations.add(fieldAnnotation);
            }
        }
        else if(annotation instanceof BindFieldList){
            //多个字段，合并查询，以减少SQL数
            if(bindFieldListGroupMap == null){
                bindFieldListGroupMap = new HashMap<>(4);
            }
            List<FieldAnnotation> list = bindFieldListGroupMap.computeIfAbsent(key, k -> new ArrayList<>(4));
            list.add(fieldAnnotation);
        }
        else if(annotation instanceof BindCount){
            if(bindCountAnnotations == null){
                bindCountAnnotations = new ArrayList<>(4);
            }
            bindCountAnnotations.add(fieldAnnotation);
        }else if(annotation instanceof BindI18n){
            if(bindI18nAnnotations == null){
                bindI18nAnnotations = new ArrayList<>(4);
            }
            bindI18nAnnotations.add(fieldAnnotation);
        }
    }

    public List<FieldAnnotation> getBindDictAnnotations() {
        return bindDictAnnotations;
    }

    public List<FieldAnnotation> getBindEntityAnnotations() {
        return bindEntityAnnotations;
    }

    public List<FieldAnnotation> getBindEntityListAnnotations() {
        return bindEntityListAnnotations;
    }

    public Map<String, List<FieldAnnotation>> getBindFieldGroupMap(){
        return bindFieldGroupMap;
    }

    public Map<String, List<FieldAnnotation>> getBindFieldListGroupMap(){
        return bindFieldListGroupMap;
    }

    public List<FieldAnnotation> getBindCountAnnotations() {
        return bindCountAnnotations;
    }

    public List<FieldAnnotation> getBindI18nAnnotations() {
        return bindI18nAnnotations;
    }

    public List<FieldAnnotation> getDeepBindEntityAnnotations() {
        return deepBindEntityAnnotations;
    }

    public List<FieldAnnotation> getDeepBindEntityListAnnotations() {
        return deepBindEntityListAnnotations;
    }

    public boolean isEmpty() {
        return V.isAllEmpty(bindDictAnnotations, bindFieldGroupMap, bindEntityAnnotations, bindEntityListAnnotations, bindFieldListGroupMap, bindCountAnnotations, bindI18nAnnotations);
    }

    /**
     * 是否有序
     * @return
     */
    public boolean isRequireSequential(){
        return requireSequential;
    }

}

