/*
 * Copyright (c) 2015-2029, www.dibo.ltd (service@dibo.ltd).
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
package com.linsir.core.mybatis.converter;

import com.linsir.core.mybatis.util.JSON;
import com.linsir.core.mybatis.util.S;
import com.linsir.core.mybatis.util.V;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * String - List<Double> 转换器
 *
 * @author wind
 * @version v3.5.0
 * @date 2024/07/29
 * Copyright © diboot.com
 */
@Component
public class String2ListDoubleConverter implements Converter<String, List<Double>> {

    @Override
    public List<Double> convert(String source) {
        if (V.isEmpty(source)) return null;
        if (source.matches("^\\[.*]$")) return JSON.parseArray(source, Double.class);
        else return S.splitToList(source).stream().map(Double::parseDouble).toList();
    }
}
