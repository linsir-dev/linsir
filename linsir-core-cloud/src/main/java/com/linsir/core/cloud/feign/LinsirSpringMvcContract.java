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

import com.linsir.core.cloud.annotation.ApiVersion;
import com.linsir.core.cloud.annotation.UrlVersion;
import com.linsir.core.cloud.version.LinsirMediaType;
import com.linsir.core.utils.StringPool;
import com.linsir.core.utils.StringUtil;
import feign.MethodMetadata;

import org.springframework.cloud.openfeign.AnnotatedParameterProcessor;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 支持 blade-boot 的 版本 处理
 *
 * @author L.cm
 * @see
 * @see
 */
public class LinsirSpringMvcContract extends SpringMvcContract {

	public LinsirSpringMvcContract(List<AnnotatedParameterProcessor> annotatedParameterProcessors, ConversionService conversionService) {
		super(annotatedParameterProcessors, conversionService);
	}

	@Override
	protected void processAnnotationOnMethod(MethodMetadata data, Annotation methodAnnotation, Method method) {
		if (RequestMapping.class.isInstance(methodAnnotation) || methodAnnotation.annotationType().isAnnotationPresent(RequestMapping.class)) {
			Class<?> targetType = method.getDeclaringClass();
			// url 上的版本，优先获取方法上的版本
			UrlVersion urlVersion = AnnotatedElementUtils.findMergedAnnotation(method, UrlVersion.class);
			// 再次尝试类上的版本
			if (urlVersion == null || StringUtil.isBlank(urlVersion.value())) {
				urlVersion = AnnotatedElementUtils.findMergedAnnotation(targetType, UrlVersion.class);
			}
			if (urlVersion != null && StringUtil.isNotBlank(urlVersion.value())) {
				String versionUrl = "/" + urlVersion.value();
				data.template().uri(versionUrl);
			}

			// 注意：在父类之前 添加 url版本，在父类之后，处理 Media Types 版本
			super.processAnnotationOnMethod(data, methodAnnotation, method);

			// 处理 Media Types 版本信息
			ApiVersion apiVersion = AnnotatedElementUtils.findMergedAnnotation(method, ApiVersion.class);
			// 再次尝试类上的版本
			if (apiVersion == null || StringUtil.isBlank(apiVersion.value())) {
				apiVersion = AnnotatedElementUtils.findMergedAnnotation(targetType, ApiVersion.class);
			}
			if (apiVersion != null && StringUtil.isNotBlank(apiVersion.value())) {
				LinsirMediaType linsirMediaType = new LinsirMediaType(apiVersion.value());
				data.template().header(HttpHeaders.ACCEPT, linsirMediaType.toString());
			}
		}
	}

	/**
	 * 参考：https://gist.github.com/rmfish/0ed59a9af6c05157be2a60c9acea2a10
	 *
	 * @param annotations 注解
	 * @param paramIndex  参数索引
	 * @return 是否 http 注解
	 */
	@Override
	protected boolean processAnnotationsOnParameter(MethodMetadata data, Annotation[] annotations, int paramIndex) {
		boolean httpAnnotation = super.processAnnotationsOnParameter(data, annotations, paramIndex);
		// 在 springMvc 中如果是 Get 请求且参数中是对象 没有声明为@RequestBody 则默认为 Param
		if (!httpAnnotation && StringPool.GET.equals(data.template().method().toUpperCase())) {
			for (Annotation parameterAnnotation : annotations) {
				if (!(parameterAnnotation instanceof RequestBody)) {
					return false;
				}
			}
			data.queryMapIndex(paramIndex);
			return true;
		}
		return httpAnnotation;
	}
}
