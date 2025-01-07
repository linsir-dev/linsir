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
package com.linsir.core.cloud.feign;

import com.fasterxml.jackson.databind.JsonNode;
import com.linsir.core.code.BaseCode;
import com.linsir.core.tool.jackson.JsonUtil;
import com.linsir.core.tool.utils.ObjectUtil;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * blade fallBack 代理处理
 *
 * @author L.cm
 */
@Slf4j
@AllArgsConstructor
public class LinsirFeignFallback<T> implements MethodInterceptor {
	private final Class<T> targetType;
	private final String targetName;
	private final Throwable cause;
	private final static String CODE = "code";

	@Nullable
	@Override
	public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
		String errorMessage = cause.getMessage();
		log.error("BladeFeignFallback:[{}.{}] serviceId:[{}] message:[{}]", targetType.getName(), method.getName(), targetName, errorMessage);
		Class<?> returnType = method.getReturnType();
		// 暂时不支持 flux，rx，异步等，返回值不是 R，直接返回 null。
		/*if (R.class != returnType) {
			return null;
		}*/
		// 非 FeignException
		if (!(cause instanceof FeignException)) {
			/*return R.fail(ResultCode.INTERNAL_SERVER_ERROR, errorMessage);*/
			return BaseCode.FAIL_SERVICE_UNAVAILABLE;
		}
		FeignException exception = (FeignException) cause;
		byte[] content = exception.content();
		// 如果返回的数据为空
		if (ObjectUtil.isEmpty(content)) {
			/*return R.fail(ResultCode.INTERNAL_SERVER_ERROR, errorMessage);*/
			return BaseCode.FAIL_SERVICE_UNAVAILABLE;
		}
		// 转换成 jsonNode 读取，因为直接转换，可能 对方放回的并 不是 R 的格式。
		JsonNode resultNode = JsonUtil.readTree(content);
		// 判断是否 R 格式 返回体
		if (resultNode.has(CODE)) {
			/*return JsonUtil.getInstance().convertValue(resultNode, R.class);*/
			return  "判断是否 R 格式 返回体";
		}
		return "判断是否 R 格式 返回体";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		LinsirFeignFallback<?> that = (LinsirFeignFallback<?>) o;
		return targetType.equals(that.targetType);
	}

	@Override
	public int hashCode() {
		return Objects.hash(targetType);
	}
}
