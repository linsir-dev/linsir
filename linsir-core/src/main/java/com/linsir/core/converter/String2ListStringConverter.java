
package com.linsir.core.converter;


import com.linsir.core.util.S;
import com.linsir.core.util.V;
import com.linsir.core.util.JSON;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * String - List<String> 转换器
 *
 * @author wind
 * @version v3.5.0
 * @date 2024/07/29
 * Copyright © diboot.com
 */
@Component
public class String2ListStringConverter implements Converter<String, List<String>> {

    @Override
    public List<String> convert(String source) {
        if (V.isEmpty(source)) return null;
        if (source.matches("^\\[.*]$")) return JSON.parseArray(source, String.class);
        else return S.splitToList(source);
    }
}
