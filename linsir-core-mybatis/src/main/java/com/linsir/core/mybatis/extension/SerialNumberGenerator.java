/*
 * Copyright (c) 2015-2099, www.dibo.ltd (service@dibo.ltd).
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
package com.linsir.core.mybatis.extension;

import com.linsir.core.mybatis.entity.BaseEntity;

import java.util.Map;

/**
 * 序列号生成器的接口定义
 * @author mazc@dibo.ltd
 * @version v3.1.1
 * @date 2023/10/07
 * @see AutoFillHandler
 */
@Deprecated
public interface SerialNumberGenerator extends AutoFillHandler {

    /**
     * 生成序列号
     * @param entityData
     * @return
     */
    default String generate(BaseEntity entityData) {
        return generate(entityData.toMap());
    }

    /**
     * 生成序列号
     * @param entityDataMap
     * @return
     */
    default String generate(Map<String, Object> entityDataMap) {
        return (String)buildFillValue(entityDataMap);
    }

}
