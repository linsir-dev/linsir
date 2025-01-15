package com.linsir.core.binding.helper;


import com.linsir.core.binding.RelationsBinder;
import com.linsir.core.binding.parser.FieldAnnotation;
import com.linsir.core.util.V;
import com.linsir.core.util.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * description：关联深度绑定
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/14 23:17
 */
public class DeepRelationsBinder {

    /**
     * 深度绑定
     * @param voList
     * @param deepBindEntityAnnoList
     * @param deepBindEntitiesAnnoList
     * @param <VO>
     */
    public static <VO> void deepBind(List<VO> voList, List<FieldAnnotation> deepBindEntityAnnoList, List<FieldAnnotation> deepBindEntitiesAnnoList) {
        if(V.isEmpty(voList)){
            return;
        }
        // 收集待深度绑定的对象集合, 绑定第二层
        if(V.notEmpty(deepBindEntityAnnoList)){
            for(FieldAnnotation anno : deepBindEntityAnnoList){
                String entityFieldName = anno.getFieldName();
                List entityList = BeanUtils.collectToList(voList, entityFieldName);
                RelationsBinder.bind(entityList, true);
            }
        }
        if(V.notEmpty(deepBindEntitiesAnnoList)){
            for(FieldAnnotation anno : deepBindEntitiesAnnoList){
                String entityFieldName = anno.getFieldName();
                List allEntityList = new ArrayList();
                for(VO vo : voList){
                    List entityList = (List) BeanUtils.getProperty(vo, entityFieldName);
                    if(V.notEmpty(entityList)){
                        allEntityList.addAll(entityList);
                    }
                }
                RelationsBinder.bind(allEntityList, true);
            }
        }
    }

}
