package com.linsir.core.mybatis.data.protect;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.linsir.core.exception.InvalidUsageException;
import com.linsir.core.mybatis.data.annotation.DataMask;
import com.linsir.core.util.ContextHolder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * description：敏感信息序列化
 *  * <p>
 *  * 对保护字段进行脱敏处理
 * author     ：linsir
 * version    ： v1.2.0
 * date       ：2025/1/15 1:35
 */
@Slf4j
public class SensitiveInfoSerialize<E> extends JsonSerializer<E> implements ContextualSerializer {

    /**
     * 保护字段处理器
     */
    private DataMaskHandler dataMaskHandler;

    /**
     * Class类型
     */
    private Class<?> clazz;

    /**
     * 属性名
     */
    private String fieldName;

    public SensitiveInfoSerialize() {
    }

    public SensitiveInfoSerialize(Class<?> clazz, String fieldName) {
        this.clazz = clazz;
        this.fieldName = fieldName;
    }

    @Override
    public void serialize(E value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if(this.dataMaskHandler == null) {
            this.dataMaskHandler = ContextHolder.getBean(DataMaskHandler.class);
            if(this.dataMaskHandler == null) {
                throw new InvalidUsageException("无法获取 DataMaskHandler 数据脱敏的实现类，请检查！");
            }
        }
        if (value instanceof List) {
            gen.writeObject(((List<String>) value).stream().map(e -> dataMaskHandler.mask(e)).collect(Collectors.toList()));
        } else {
            gen.writeObject(dataMaskHandler.mask((String) value));
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        if (null == property) {
            return prov.findNullValueSerializer(null);
        }
        Class<?> rawClass = property.getType().getRawClass();
        if (rawClass == String.class || (rawClass == List.class && property.getType().getContentType().getRawClass() == String.class)) {
            DataMask protect = property.getAnnotation(DataMask.class);
            if (null == protect) {
                protect = property.getContextAnnotation(DataMask.class);
            }
            if (null != protect) {
                return new SensitiveInfoSerialize(property.getMember().getDeclaringClass(), property.getName());
            }
        } else {
            log.error("`@DataMask` 只支持 String 与 List<String> 类型脱敏！");
        }
        return prov.findValueSerializer(property.getType(), property);
    }
}

