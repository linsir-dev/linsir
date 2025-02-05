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
package com.linsir.core.mybatis.service;

import com.linsir.core.mybatis.entity.Dictionary;
import com.linsir.core.mybatis.vo.DictionaryVO;
import com.linsir.core.mybatis.vo.LabelValue;

import java.io.Serializable;
import java.util.List;

/**
 * 数据字典Service
 * @author mazc@dibo.ltd
 * @version 2.0
 * @date 2019/01/01
 */
public interface DictionaryService extends BaseService<Dictionary>{

    /**
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
    boolean deleteDictAndChildren(Serializable id);

}
