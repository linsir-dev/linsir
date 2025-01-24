package com.linsir.core.mybatis.binding.parser;


import java.lang.annotation.Annotation;

/**
 * description：字段名与注解的包装对象关系
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/14 23:29
 */
public class FieldAnnotation{
    /**
     * 字段名
     */
    private String fieldName;
    /**
     * 字段类型
     */
    private Class<?> fieldClass;
    /**
     * 注解
     */
    private Annotation annotation;

    public FieldAnnotation(String fieldName, Class fieldClass, Annotation annotation){
        this.fieldName = fieldName;
        this.fieldClass = fieldClass;
        this.annotation = annotation;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public Class getFieldClass(){
        return fieldClass;
    }
}

