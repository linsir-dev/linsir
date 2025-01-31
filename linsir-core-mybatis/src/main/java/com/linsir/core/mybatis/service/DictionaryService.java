package com.linsir.core.mybatis.service;


import com.linsir.core.mybatis.entity.Dictionary;
import com.linsir.core.mybatis.vo.DictionaryVO;
import com.linsir.core.mybatis.vo.LabelValue;

import java.util.List;

/**
 * @author ：linsir
 * @date ：Created in 2022/3/23 14:46
 * @description：数据字典Service
 * @modified By：
 * @version: 0.0.1
 */
public interface DictionaryService extends BaseService<Dictionary> {

    /***
     * 获取对应类型的键值对
     * @param type
     * @return
     */
    List<LabelValue> getLabelValueList(String type);

    /**
     * 添加字典定义及其子项
     * @param dictVO
     * @return
     */
    boolean createDictAndChildren(DictionaryVO dictVO);

    /**
     * 更新字典定义及其子项
     * @param dictVO
     * @return
     */
    boolean updateDictAndChildren(DictionaryVO dictVO);

    /**
     * 删除字典定义及其子项
     * @param id
     * @return
     */
    boolean deleteDictAndChildren(Long id);
}
