package com.linsir.core.binding.query.dynamic;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.linsir.core.binding.helper.ServiceAdaptor;
import com.linsir.core.binding.parser.ParserCache;
import com.linsir.core.exception.InvalidUsageException;
import com.linsir.core.util.ContextHolder;
import com.linsir.core.vo.Pagination;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * description：动态查询wrapper
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/15 0:20
 */
public class ExtQueryWrapper<T> extends QueryWrapper<T> {
    private static final long serialVersionUID = -3690583300701726821L;

    /**
     * 主实体class
     */
    @Getter
    @Setter
    private Class<T> mainEntityClass;

    /**
     * 获取entity表名
     * @return
     */
    public String getEntityTable(){
        if(this.mainEntityClass == null) {
            this.mainEntityClass = getEntityClass();
        }
        return ParserCache.getEntityTableName(this.mainEntityClass);
    }

    /**
     * 查询一条数据
     * @param entityClazz
     * @return
     */
    public T queryOne(Class<T> entityClazz){
        this.mainEntityClass = entityClazz;
        IService<T> iService = ContextHolder.getIServiceByEntity(this.mainEntityClass);
        if(iService != null){
            return ServiceAdaptor.getSingleEntity(iService, this);
        }
        else{
            throw new InvalidUsageException("查询对象无BaseService/IService实现: {}",this.mainEntityClass.getSimpleName());
        }
    }

    /**
     * 查询一条数据
     * @param entityClazz
     * @return
     */
    public List<T> queryList(Class<T> entityClazz){
        this.mainEntityClass = entityClazz;
        IService iService = ContextHolder.getIServiceByEntity(entityClazz);
        if(iService != null){
            return ServiceAdaptor.queryList(iService, this);
        }
        else{
            throw new InvalidUsageException("查询对象无BaseService/IService实现: {}",entityClazz.getSimpleName());
        }
    }

    /**
     * 查询一条数据
     * @param entityClazz
     * @return
     */
    public List queryList(Class<T> entityClazz, Pagination pagination){
        this.mainEntityClass = entityClazz;
        IService iService = ContextHolder.getIServiceByEntity(entityClazz);
        if(iService != null){
            return ServiceAdaptor.queryList(iService, this, pagination, entityClazz);
        }
        else{
            throw new InvalidUsageException("查询对象无BaseService/IService实现: {}", entityClazz.getSimpleName());
        }
    }

}

