package com.linsir.core.mybatis.binding.query.dynamic;


import com.baomidou.mybatisplus.annotation.TableField;
import com.linsir.core.code.ResultCode;
import com.linsir.core.mybatis.binding.parser.ParserCache;
import com.linsir.core.mybatis.binding.parser.PropInfo;
import com.linsir.core.mybatis.binding.query.BindQuery;
import com.linsir.core.mybatis.binding.query.Comparison;
import com.linsir.core.mybatis.exception.InvalidUsageException;
import com.linsir.core.mybatis.util.V;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.lang.model.type.NullType;
import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * description：BindQuery注解连接器
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/15 0:18
 */
@Slf4j
@Getter
@Setter
public class AnnoJoiner implements Serializable {
    private static final long serialVersionUID = 5998965277333389063L;

    public AnnoJoiner(PropInfo propInfo, Field field, BindQuery query){
        this.key = field.getName() + query;
        this.fieldName = field.getName();
        this.comparison = query.comparison();
        // 列名
        if (V.notEmpty(query.field())) {
            this.columnName = propInfo.getColumnByField(query.field());
            if(V.isEmpty(this.columnName)){
                throw new InvalidUsageException(ResultCode.INVALID_OPERATION,"@BindQuery 注解配置异常，filed={} 无法解析出对应的列名！", query.field());
            }
        }
        else if (V.notEmpty(query.column())) {
            this.columnName = query.column();
        }
        else if (field.isAnnotationPresent(TableField.class)) {
            this.columnName = field.getAnnotation(TableField.class).value();
        }
        if(V.isEmpty(this.columnName)){
            this.columnName = propInfo.getColumnByField(field.getName());
            if(V.isEmpty(this.columnName)){
                throw new InvalidUsageException(ResultCode.INVALID_OPERATION,"@BindQuery 注解配置异常，filed={} 无法解析出对应的列名！", query.field());
            }
        }
        // join 表名
        if(!NullType.class.equals(query.entity())){
            this.join = ParserCache.getEntityTableName(query.entity());
        }
        // 条件
        if(V.notEmpty(query.condition())){
            this.condition = query.condition();
        }
    }

    private String key;

    private Comparison comparison;

    private String fieldName;

    private String columnName;

    private String condition;

    private String join;
    /**
     * 别名
     */
    private String alias;
    /**
     * on条件
     */
    private String onSegment;

    /**
     * 中间表
     */
    private String middleTable;

    /**
     * 中间表别名
     */
    public String getMiddleTableAlias(){
        if(middleTable != null && alias != null){
            return alias+"m";
        }
        return null;
    }
    /**
     * 中间表on
     */
    private String middleTableOnSegment;

    /**
     * 解析
     */
    public void parse(){
        // 解析查询
        JoinConditionManager.parseJoinCondition(this);
    }
}

