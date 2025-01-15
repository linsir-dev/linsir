package com.linsir.core.binding.helper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.linsir.core.service.BaseService;
import com.linsir.core.vo.Pagination;

import java.util.List;

/**
 * description：Service适配器
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/14 23:20
 */
public class ServiceAdaptor {

    /**
     * 查询一个实体
     * @param iService
     * @param queryWrapper
     * @param <E>
     * @return
     */
    public static <E> E getSingleEntity(IService<E> iService, QueryWrapper queryWrapper){
        if(iService instanceof BaseService){
            BaseService baseService = (BaseService)iService;
            return (E)baseService.getSingleEntity(queryWrapper);
        }
        else{
            return (E)iService.getOne(queryWrapper);
        }
    }

    /**
     * 查询实体列表
     * @param iService
     * @param queryWrapper
     * @param <E>
     * @return
     */
    public static <E> List<E> queryList(IService<E> iService, QueryWrapper<E> queryWrapper){
        if(iService instanceof BaseService){
            BaseService baseService = (BaseService)iService;
            return (List<E>)baseService.getEntityList(queryWrapper);
        }
        else{
            return iService.list(queryWrapper);
        }
    }

    /**
     * 查询一页的实体列表
     * @param iService
     * @param queryWrapper
     * @param pagination
     * @param <E>
     * @return
     */
    public static <E> List<E> queryList(IService iService, QueryWrapper<E> queryWrapper, Pagination pagination, Class entityClass) {
        if(iService instanceof BaseService){
            BaseService baseService = (BaseService)iService;
            return (List<E>)baseService.getEntityList(queryWrapper, pagination);
        }
        else{
            if(queryWrapper.getEntityClass() == null) {
                queryWrapper.setEntityClass(entityClass);
            }
            if(pagination != null){
                IPage<E> page = convertToIPage(pagination, entityClass);
                page = iService.page(page, queryWrapper);
                // 如果重新执行了count进行查询，则更新pagination中的总数
                if(page.searchCount()){
                    pagination.setTotalCount(page.getTotal());
                }
                return page.getRecords();
            }
            else{
                return iService.list(queryWrapper);
            }
        }
    }

    /**
     * 转换为IPage
     * @param pagination 分页
     * @return
     */
    public static <E> Page<E> convertToIPage(Pagination pagination, Class<?> entityClass) {
        if(pagination == null){
            return null;
        }
        return pagination.toPage(entityClass);
    }

}

