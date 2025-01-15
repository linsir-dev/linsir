/*
 *      Copyright (c) 2018-2028, DreamLu All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: DreamLu 卢春梦 (596392912@qq.com)
 */
package com.linsir.core.tool.utils;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Protostuff 工具类
 *
 * @author L.cm
 */
public class ProtostuffUtil {

	/**
	 * 避免每次序列化都重新申请Buffer空间
	 */
	private static final LinkedBuffer BUFFER = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
	/**
	 * 缓存Schema
	 */
	private static final Map<Class<?>, Schema<?>> SCHEMA_CACHE = new ConcurrentHashMap<>();

	/**
	 * 序列化方法，把指定对象序列化成字节数组
	 *
	 * @param obj obj
	 * @param <T> T
	 * @return byte[]
	 */
	@SuppressWarnings("unchecked")
	public static <T> byte[] serialize(T obj) {
		Class<T> clazz = (Class<T>) obj.getClass();
		Schema<T> schema = getSchema(clazz);
		byte[] data;
		try {
			data = ProtostuffIOUtil.toByteArray(obj, schema, BUFFER);
		} finally {
			BUFFER.clear();
		}
		return data;
	}

	/**
	 * 反序列化方法，将字节数组反序列化成指定Class类型
	 *
	 * @param data  data
	 * @param clazz clazz
	 * @param <T>   T
	 * @return T
	 */
	public static <T> T deserialize(byte[] data, Class<T> clazz) {
		Schema<T> schema = getSchema(clazz);
		T obj = schema.newMessage();
		ProtostuffIOUtil.mergeFrom(data, obj, schema);
		return obj;
	}

	/**
	 * 获取Schema
	 *
	 * @param clazz clazz
	 * @param <T>   T
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	private static <T> Schema<T> getSchema(Class<T> clazz) {
		Schema<T> schema = (Schema<T>) SCHEMA_CACHE.get(clazz);
		if (Objects.isNull(schema)) {
			// 这个schema通过RuntimeSchema进行懒创建并缓存
			// 所以可以一直调用RuntimeSchema.getSchema(),这个方法是线程安全的
			schema = RuntimeSchema.getSchema(clazz);
			if (Objects.nonNull(schema)) {
				SCHEMA_CACHE.put(clazz, schema);
			}
		}
		return schema;
	}

}
