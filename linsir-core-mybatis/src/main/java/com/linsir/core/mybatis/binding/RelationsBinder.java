package com.linsir.core.mybatis.binding;


import com.linsir.core.mybatis.binding.helper.DeepRelationsBinder;
import com.linsir.core.mybatis.binding.helper.RelationsBindingManager;
import com.linsir.core.mybatis.binding.parser.BindAnnotationGroup;
import com.linsir.core.mybatis.binding.parser.FieldAnnotation;
import com.linsir.core.mybatis.binding.parser.ParserCache;

import com.linsir.core.mybatis.service.I18nConfigService;
import com.linsir.core.mybatis.util.BeanUtils;
import com.linsir.core.mybatis.util.ContextHolder;
import com.linsir.core.mybatis.util.V;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * description：关联关系绑定管理器
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/15 0:28
 */
@SuppressWarnings("JavaDoc")
public class RelationsBinder {
    private static final Logger log = LoggerFactory.getLogger(RelationsBinder.class);

    private static RelationsBindingManager relationsBindingManager;
    private static RelationsBindingManager getBindingManager() {
        if(relationsBindingManager == null){
            relationsBindingManager = ContextHolder.getBean(RelationsBindingManager.class);
        }
        return relationsBindingManager;
    }

    /**
     * 自动转换和绑定单个VO中的注解关联（禁止循环调用，多个对象请调用convertAndBind(voList, voClass)）
     * @param voClass 需要转换的VO class
     * @param <E>
     * @param <VO>
     * @return
     */
    public static <E, VO> VO convertAndBind(E entity, Class<VO> voClass){
        // 转换为VO列表
        VO vo = BeanUtils.convert(entity, voClass);
        // 自动绑定关联对象
        bind(vo);
        return vo;
    }

    /**
     * 自动转换和绑定多个VO中的注解关联
     * @param entityList 需要转换的VO list
     * @param voClass VO class
     * @param <E>
     * @param <VO>
     * @return
     */
    public static <E, VO> List<VO> convertAndBind(List<E> entityList, Class<VO> voClass){
        // 转换为VO列表
        List<VO> voList = BeanUtils.convertList(entityList, voClass);
        // 自动绑定关联对象
        bind(voList);
        return voList;
    }

    /**
     * 自动绑定单个VO的关联对象（禁止循环调用，多个对象请调用bind(voList)）
     * @param vo 需要注解绑定的对象
     * @return
     * @throws Exception
     */
    public static <VO> void bind(VO vo){
        bind(Collections.singletonList(vo));
    }

    /**
     * 自动绑定多个VO集合的关联对象
     * @param voList 需要注解绑定的对象集合
     * @return
     * @throws Exception
     */
    public static <VO> void bind(List<VO> voList){
        bind(voList, true);
    }

    /**
     * 自动绑定多个VO集合的关联对象
     * @param voList 需要注解绑定的对象集合
     * @param enableDeepBind
     * @return
     * @throws Exception
     */
    public static <VO> void bind(List<VO> voList, boolean enableDeepBind){
        if(V.isEmpty(voList)){
            return;
        }
        // 获取VO类
        Class<?> voClass = voList.get(0).getClass();
        BindAnnotationGroup bindAnnotationGroup = ParserCache.getBindAnnotationGroup(voClass);
        if(bindAnnotationGroup.isEmpty()){
            return;
        }
        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(), true);
        LocaleContextHolder.setLocaleContext(LocaleContextHolder.getLocaleContext(),true);
        RelationsBindingManager bindingManager = getBindingManager();
        // 绑定Field字段名
        Map<String, List<FieldAnnotation>> bindFieldGroupMap = bindAnnotationGroup.getBindFieldGroupMap();
        if(bindFieldGroupMap != null){
            for(Map.Entry<String, List<FieldAnnotation>> entry : bindFieldGroupMap.entrySet()){
                bindingManager.doBindingField(voList, entry.getValue());
            }
        }
        // 绑定数据字典
        List<FieldAnnotation> dictAnnoList = bindAnnotationGroup.getBindDictAnnotations();
        if(dictAnnoList != null){
            for(FieldAnnotation annotation : dictAnnoList){
                bindingManager.doBindingDict(voList, annotation);
            }
        }
        // 绑定Entity实体
        List<FieldAnnotation> entityAnnoList = bindAnnotationGroup.getBindEntityAnnotations();
        if(entityAnnoList != null){
            for(FieldAnnotation anno : entityAnnoList){
                // 绑定关联对象entity
                bindingManager.doBindingEntity(voList, anno);
            }
        }
        // 绑定Entity实体List
        List<FieldAnnotation> entitiesAnnoList = bindAnnotationGroup.getBindEntityListAnnotations();
        if(entitiesAnnoList != null){
            for(FieldAnnotation anno : entitiesAnnoList){
                // 绑定关联对象entity
                bindingManager.doBindingEntityList(voList, anno);
            }
        }
        // 绑定Entity field List
        Map<String, List<FieldAnnotation>> bindFieldListGroupMap = bindAnnotationGroup.getBindFieldListGroupMap();
        if(bindFieldListGroupMap != null){
            // 解析条件并且执行绑定
            for(Map.Entry<String, List<FieldAnnotation>> entry : bindFieldListGroupMap.entrySet()){
                bindingManager.doBindingFieldList(voList, entry.getValue());
            }
        }
        // 绑定count子项计数
        List<FieldAnnotation> countAnnoList = bindAnnotationGroup.getBindCountAnnotations();
        if(countAnnoList != null){
            for(FieldAnnotation anno : countAnnoList){
                // 绑定关联对象count计数
                bindingManager.doBindingCount(voList, anno);
            }
        }
        // 开启国际化
        if(isEnableI18N()) {
            // 绑定国际化翻译
            List<FieldAnnotation> i18nAnnoList = bindAnnotationGroup.getBindI18nAnnotations();
            if(i18nAnnoList != null){
                for(FieldAnnotation anno : i18nAnnoList){
                    // 绑定关联对象count计数
                    bindingManager.doBindingI18n(voList, anno);
                }
            }
        }
        // 深度绑定
        if(enableDeepBind){
            List<FieldAnnotation> deepBindEntityAnnoList = bindAnnotationGroup.getDeepBindEntityAnnotations();
            List<FieldAnnotation> deepBindEntitiesAnnoList = bindAnnotationGroup.getDeepBindEntityListAnnotations();
            if(deepBindEntityAnnoList != null || deepBindEntitiesAnnoList != null){
                if(V.notEmpty(deepBindEntityAnnoList)){
                    FieldAnnotation firstAnnotation = deepBindEntityAnnoList.get(0);
                    log.debug("执行深度绑定: {}({}) for field {}", firstAnnotation.getAnnotation().annotationType().getSimpleName(),
                            firstAnnotation.getFieldClass().getSimpleName(), firstAnnotation.getFieldName());
                }
                if(deepBindEntitiesAnnoList != null) {
                    FieldAnnotation firstAnnotation = deepBindEntitiesAnnoList.get(0);
                    log.debug("执行深度绑定: {}({}) for field {}", firstAnnotation.getAnnotation().annotationType().getSimpleName(),
                            firstAnnotation.getFieldClass().getSimpleName(), firstAnnotation.getFieldName());
                }
                DeepRelationsBinder.deepBind(voList, deepBindEntityAnnoList, deepBindEntitiesAnnoList);
            }
        }
    }

    /**
     * 是否启用 i18n
     * @return
     */
    private static Boolean ENABLE_I18N = null;
    private static boolean isEnableI18N() {
        if(ENABLE_I18N == null){
            ENABLE_I18N = ContextHolder.getBean(I18nConfigService.class) != null;
            if(ENABLE_I18N){
                log.info("启用 i8n 国际化翻译转换");
            }
        }
        return ENABLE_I18N;
    }

}

