/*
 * Copyright (c) 2015-2020, www.dibo.ltd (service@dibo.ltd).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.linsir.core.mybatis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.linsir.core.code.ResultCode;
import com.linsir.core.mybatis.cache.DictionaryCacheManager;
import com.linsir.core.mybatis.config.Cons;
import com.linsir.core.mybatis.entity.Dictionary;
import com.linsir.core.mybatis.exception.BusinessException;
import com.linsir.core.mybatis.mapper.DictionaryMapper;
import com.linsir.core.mybatis.service.DictionaryService;
import com.linsir.core.mybatis.service.DictionaryServiceExtProvider;
import com.linsir.core.mybatis.service.I18nConfigService;
import com.linsir.core.mybatis.util.BeanUtils;
import com.linsir.core.mybatis.util.S;
import com.linsir.core.mybatis.util.V;
import com.linsir.core.mybatis.vo.DictionaryVO;
import com.linsir.core.mybatis.vo.LabelValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 数据字典相关service实现
 * @author mazc@dibo.ltd
 * @version 2.0
 * @date 2019/01/01
 */
@Primary
@Service("dictionaryService")
public class DictionaryServiceExtImpl extends BaseServiceImpl<DictionaryMapper, Dictionary> implements DictionaryService, DictionaryServiceExtProvider {
    private static final Logger log = LoggerFactory.getLogger(DictionaryServiceExtImpl.class);

    @Autowired
    private DictionaryCacheManager dictionaryCacheManager;

    @Autowired(required = false)
    private I18nConfigService i18nConfigService;

    /**
     * 数据变动前先清空缓存
     * @param entity
     */
    protected void beforeCreate(Dictionary entity) {
        dictionaryCacheManager.removeCachedItems(entity.getType());
        log.debug("字典 {}:{} 的缓存已被移除", entity.getItemName(), entity.getType());
    }

    /**
     * 数据变动前先清空缓存
     * @param entity
     */
    protected void beforeUpdate(Dictionary entity) {
        dictionaryCacheManager.removeCachedItems(entity.getType());
        log.debug("字典 {}:{} 的缓存已被移除", entity.getItemName(), entity.getType());
    }

    /**
     * 数据变动前先清空缓存
     * @param fieldKey
     * @param fieldVal
     */
    protected void beforeDelete(String fieldKey, Object fieldVal) {
        List<String> types = getValuesOfField(fieldKey, fieldVal, Dictionary::getType);
        if(V.isEmpty(types)){
            return;
        }
        types.forEach(type -> {
            dictionaryCacheManager.removeCachedItems(type);
            log.debug("字典 {} 的缓存已被移除", type);
        });
    }

    /**
     * 根据type查询字典选项（支持缓存）
     * @param type
     * @return
     */
    public List<Dictionary> getEntityListByType(String type) {
        List<Dictionary> dictList = dictionaryCacheManager.getCachedItems(type);
        if(dictList==null) {
            // 构建查询条件
            LambdaQueryWrapper<Dictionary> queryDictionary = new QueryWrapper<Dictionary>().lambda()
                    .select(Dictionary::getItemName, Dictionary::getItemValue, Dictionary::getItemNameI18n, Dictionary::getExtension)
                    .eq(Dictionary::getType, type)
                    .isNotNull(Dictionary::getParentId).ne(Dictionary::getParentId, Cons.ID_PREVENT_NULL)
                    .orderByAsc(Arrays.asList(Dictionary::getSortId, Dictionary::getId));
            dictList = super.getEntityList(queryDictionary);
            log.debug("查询到字典 {} 的选项数据", type);
            // 缓存字典选项数据
            dictionaryCacheManager.cacheItems(type, dictList);
        }
        else {
            log.debug("从缓存中获取 {} 的选项数据", type);
        }
        return dictList;
    }

    @Override
    public List<LabelValue> getLabelValueList(String type) {
        // 根据类型查询并返回
        List<Dictionary> dictionaryList = getEntityListByType(type);
        if(V.isEmpty(dictionaryList)){
            log.warn("字典 {} 无任何选项定义！", type);
            return Collections.emptyList();
        }
        return convertToLabelValueList(dictionaryList);
    }

    @Override
    public Map<String, LabelValue> getLabel2ItemMap(String type) {
        // 根据类型查询并返回
        List<Dictionary> dictionaryList = getEntityListByType(type);
        return dictionaryList.stream().collect(
                Collectors.toMap(Dictionary::getItemName, Dictionary::toLabelValue));
    }

    @Override
    public Map<String, LabelValue> getValue2ItemMap(String type) {
        // 根据类型查询并返回
        List<Dictionary> dictionaryList = getEntityListByType(type);
        return dictionaryList.stream().collect(
                Collectors.toMap(Dictionary::getItemValue, Dictionary::toLabelValue));
    }

    @Override
    public boolean existsDictType(String dictType) {
        LambdaQueryWrapper<Dictionary> queryWrapper = Wrappers.<Dictionary>lambdaQuery()
                .eq(Dictionary::getType, dictType);
        return exists(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createDictAndChildren(DictionaryVO dictVO) {
        if (dictVO.getIsEditable() == null){
            dictVO.setIsEditable(true);
        }
        if (dictVO.getIsDeletable() == null) {
            dictVO.setIsDeletable(true);
        }
        if(!super.createEntity(dictVO)){
            log.warn("新建数据字典定义失败，type={}", dictVO.getType());
            return false;
        }
        List<Dictionary> children = dictVO.getChildren();
        this.buildSortId(children);
        if(V.notEmpty(children)){
            // 检查选项重复
            checkDuplicate(children);
            for(Dictionary dict : children){
                dict.setParentId(dictVO.getId())
                    .setType(dictVO.getType())
                    .setIsDeletable(dictVO.getIsDeletable())
                    .setIsEditable(dictVO.getIsEditable());
            }
            // 批量保存
            boolean success = super.createEntities(children);
            if(!success){
                log.warn("新建数据字典子项失败， type= {}", dictVO.getType());
                throw new BusinessException(ResultCode.FAIL_OPERATION, "exception.business.dictionaryService.createDictAndChildren.message", dictVO.getType());
            }
        }
        return true;
    }

    @Override
    public List<Dictionary> getDictDefinitionList() {
        LambdaQueryWrapper<Dictionary> queryWrapper = Wrappers.<Dictionary>lambdaQuery()
                .and(wrapper -> {
                    wrapper.isNull(Dictionary::getParentId).or().eq(Dictionary::getParentId, Cons.ID_PREVENT_NULL);
                })
                .orderByDesc(Dictionary::getId);
        return getEntityList(queryWrapper);
    }

    @Override
    public List<DictionaryVO> getDictDefinitionVOList() {
        LambdaQueryWrapper<Dictionary> queryWrapper = Wrappers.<Dictionary>lambdaQuery()
                .and(wrapper -> {
                    wrapper.isNull(Dictionary::getParentId).or().eq(Dictionary::getParentId, Cons.ID_PREVENT_NULL);
                });
        return getViewObjectList(queryWrapper, null, DictionaryVO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDictAndChildren(DictionaryVO dictVO) {
        Dictionary oldDictionary = super.getEntity(dictVO.getId());
        //将DictionaryVO转化为Dictionary
        dictVO
                .setIsDeletable(oldDictionary.getIsDeletable())
                .setIsEditable(oldDictionary.getIsEditable());
        if(!updateEntity(oldDictionary.getItemName(), dictVO)){
            log.warn("更新数据字典定义失败，type={}", dictVO.getType());
            return false;
        }
        //获取原 子数据字典list
        QueryWrapper<Dictionary> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Dictionary::getParentId, dictVO.getId());
        List<Dictionary> oldDictList = super.getEntityList(queryWrapper);
        List<Dictionary> newDictList = dictVO.getChildren();
        Set<String> dictItemIds = new HashSet<>();
        this.buildSortId(newDictList);
        if(V.notEmpty(newDictList)){
            // 检查选项重复
            checkDuplicate(newDictList);
            for(Dictionary dict : newDictList){
                dict.setType(dictVO.getType())
                    .setParentId(dictVO.getId())
                    .setIsDeletable(dictVO.getIsDeletable())
                    .setIsEditable(dictVO.getIsEditable());
                if(V.notEmpty(dict.getId())){
                    dictItemIds.add(dict.getId());
                    Optional<Dictionary> oldDictOpt = oldDictList.stream().filter(d->d.getId().equals(dict.getId())).findFirst();
                    if(oldDictOpt.isPresent() && !updateEntity(oldDictOpt.get().getItemName(), dict)){
                        log.warn("更新字典子项失败，itemName={}", dict.getItemName());
                        throw new BusinessException(ResultCode.FAIL_EXCEPTION, "exception.business.dictionaryService.updateItem");
                    }
                }
                else{
                    if(!super.createEntity(dict)){
                        log.warn("新建字典子项失败，itemName={}", dict.getItemName());
                        throw new BusinessException(ResultCode.FAIL_EXCEPTION, "exception.business.dictionaryService.createItem");
                    }
                }
            }
        }
        if(V.notEmpty(oldDictList)){
            for(Dictionary dict : oldDictList){
                if(!dictItemIds.contains(dict.getId())){
                    if(!super.deleteEntity(dict.getId())){
                        log.warn("删除子数据字典失败，itemName={}", dict.getItemName());
                        throw new BusinessException(ResultCode.FAIL_EXCEPTION, "exception.business.dictionaryService.deleteItem");
                    }
                }
            }
        }
        return true;
    }

    /**
     * 更新i18n国际化
     * @param oldDictItemName
     * @param newDict
     * @return
     */
    private boolean updateEntity(String oldDictItemName, Dictionary newDict){
        if(i18nConfigService != null) {
            if(V.notEquals(oldDictItemName, newDict.getItemName())) {
                i18nConfigService.updateI18nContent("zh_CN", newDict.getItemNameI18n(), newDict.getItemName());
                log.debug("字典 {} 的国际化中文内容已更改为 {}", oldDictItemName, newDict.getItemName());
            }
        }
        return super.updateEntity(newDict);
    }

    /**
     * 检查duplicate
     * @param dictList
     */
    private void checkDuplicate(List<Dictionary> dictList) {
        Set<String> itemNames = new HashSet<>(), itemValues = new HashSet<>();
        dictList.forEach(dict -> {
            if (itemValues.contains(dict.getItemValue())) {
                throw new BusinessException(ResultCode.FAIL_OPERATION, "exception.business.dictionaryService.repeatItemValue", dict.getItemValue());
            } else if (itemNames.contains(dict.getItemName())) {
                throw new BusinessException(ResultCode.FAIL_OPERATION, "exception.business.dictionaryService.repeatItemName", dict.getItemName());
            }
            itemNames.add(dict.getItemName());
            itemValues.add(dict.getItemValue());
        });
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteDictAndChildren(Serializable id) {
        LambdaQueryWrapper<Dictionary> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Dictionary::getId, id).or().eq(Dictionary::getParentId, id);
        super.deleteEntities(queryWrapper);
        return true;
    }

    @Override
    public void bindItemLabel(List voList, String setFieldName, String getFieldName, String type){
        if(V.isEmpty(voList)){
            return;
        }
        List<LabelValue> entityList = getLabelValueList(type);
        Map<String, LabelValue> map = BeanUtils.convertToStringKeyObjectMap(entityList, LabelValue::getValue);
        Class<?> fieldType = BeanUtils.getFieldActualType(voList.get(0).getClass(), setFieldName);
        boolean isLabelValueClass = LabelValue.class.equals(fieldType);
        for (Object item : voList) {
            Object value = BeanUtils.getProperty(item, getFieldName);
            if (V.isEmpty(value)) {
                continue;
            }
            // 直接匹配无结果
            if(value instanceof String) {
                LabelValue matchedItem = map.get((String)value);
                if (matchedItem != null) {
                    if (isLabelValueClass) {
                        BeanUtils.setProperty(item, setFieldName, matchedItem);
                    }
                    else {
                        BeanUtils.setProperty(item, setFieldName, matchedItem.getLabel());
                    }
                    continue;
                }
                if(((String)value).contains(S.SEPARATOR)) {
                    List labelList = new ArrayList<>();
                    for (String key : ((String)value).split(S.SEPARATOR)) {
                        LabelValue labelValue = map.get(key);
                        if(labelValue == null) {
                            continue;
                        }
                        if(isLabelValueClass) {
                            labelList.add(labelValue);
                        }
                        else {
                            labelList.add(labelValue.getLabel());
                        }
                    }
                    if(V.notEmpty(labelList)) {
                        if(isLabelValueClass) {
                            BeanUtils.setProperty(item, setFieldName, labelList);
                        }
                        else {
                            BeanUtils.setProperty(item, setFieldName, S.join(labelList));
                        }
                    }
                }
                else {
                    log.warn("未匹配到字典选项: {}，存储值: {}", type, value);
                }
            }
            else if(value instanceof Collection) {
                List labelList = new ArrayList<>();
                for (Object key : (Collection)value) {
                    LabelValue labelValue = map.get((String)key);
                    if(labelValue == null) {
                        continue;
                    }
                    if(isLabelValueClass) {
                        labelList.add(labelValue);
                    }
                    else {
                        labelList.add(labelValue.getLabel());
                    }
                }
                BeanUtils.setProperty(item, setFieldName, labelList);
            }
            else if (value.getClass().isArray()) {
                List labelList = new ArrayList<>();
                for (Object key : (Object[])value) {
                    LabelValue labelValue = map.get((String)key);
                    if(labelValue == null) {
                        continue;
                    }
                    if(isLabelValueClass) {
                        labelList.add(labelValue);
                    }
                    else {
                        labelList.add(labelValue.getLabel());
                    }
                }
                BeanUtils.setProperty(item, setFieldName, labelList);
            }
            else {
                log.warn("不支持的属性类型: {}，存储值: {}", value.getClass().getSimpleName(), value);
            }
        }
    }

    /**
     * 构建排序编号
     * @param dictList
     */
    private void buildSortId(List<Dictionary> dictList) {
        if (V.isEmpty(dictList)) {
            return;
        }
        for (int i = 0; i < dictList.size(); i++) {
            Dictionary dict = dictList.get(i);
            dict.setSortId(i);
        }
    }

    /**
     * 转换为 List<LabelValue>（如启用i18n，则翻译）
     * @param dictList
     * @return
     */
    private List<LabelValue> convertToLabelValueList(List<Dictionary> dictList) {
        if(i18nConfigService == null) {
            return dictList.stream().map(Dictionary::toLabelValue).collect(Collectors.toList());
        }
        else {
            // i18n 翻译
            List<String> itemI18nMap = dictList.stream().map(Dictionary::getItemNameI18n).filter(V::notEmpty).collect(Collectors.toList());
            Map<String, String> i18nKeyValMap = i18nConfigService.translate(itemI18nMap);
            List<LabelValue> items = new ArrayList<>(dictList.size());
            for(Dictionary dictionary : dictList){
                LabelValue item = dictionary.toLabelValue();
                if(i18nKeyValMap.containsKey(dictionary.getItemNameI18n())){
                    item.setLabel(i18nKeyValMap.get(dictionary.getItemNameI18n()));
                }
                items.add(item);
            }
            return items;
        }
    }

}
