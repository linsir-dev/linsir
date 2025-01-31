package com.linsir.core.mybatis.binding.query.dynamic;


import com.linsir.core.mybatis.binding.JoinsBinder;
import com.linsir.core.mybatis.binding.parser.ParserCache;
import com.linsir.core.mybatis.vo.Pagination;
import lombok.Getter;

import java.util.Collection;
import java.util.List;

/**
 * description：动态查询wrapper
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/15 0:18
 */
public class DynamicJoinQueryWrapper<DTO,T> extends ExtQueryWrapper<T> {
    private static final long serialVersionUID = 5557355990471769264L;

    public DynamicJoinQueryWrapper(Class<DTO> dtoClass, Collection<String> fields){
        this.dtoClass = dtoClass;
        this.fields = fields;
    }

    /**
     * DTO类
     */
    @Getter
    private Class<DTO> dtoClass;
    /**
     * 字段
     */
    private Collection<String> fields;

    /**
     * dto字段和值
     */
    public List<AnnoJoiner> getAnnoJoiners(){
        return ParserCache.getAnnoJoiners(this.dtoClass, fields);
    }

    /**
     * 查询一条数据
     * @param entityClazz
     * @return
     */
    @Override
    public T queryOne(Class<T> entityClazz){
        return JoinsBinder.queryOne(this, entityClazz);
    }

    /**
     * 查询一条数据
     * @param entityClazz
     * @return
     */
    @Override
    public List<T> queryList(Class<T> entityClazz){
        return JoinsBinder.queryList(this, entityClazz);
    }

    /**
     * 查询一条数据
     * @param entityClazz
     * @return
     */
    @Override
    public List<T> queryList(Class<T> entityClazz, Pagination pagination){
        return JoinsBinder.queryList(this, entityClazz, pagination);
    }

}
