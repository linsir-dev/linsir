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
package com.linsir.core.cloud.http;

import com.linsir.core.cloud.hystrix.LinsirHttpHeadersContextHolder;
import com.linsir.core.cloud.hystrix.LinsirHystrixAccountGetter;
import com.linsir.core.cloud.props.LinsirHystrixHeadersProperties;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.Nullable;

import java.io.IOException;

/**
 * RestTemplateHeaderInterceptor 传递Request header
 *
 * @author L.cm
 */
@AllArgsConstructor
public class RestTemplateHeaderInterceptor implements ClientHttpRequestInterceptor {
	@Nullable
	private final LinsirHystrixAccountGetter accountGetter;
	private final LinsirHystrixHeadersProperties properties;

	@Override
	public ClientHttpResponse intercept(
		HttpRequest request, byte[] bytes,
		ClientHttpRequestExecution execution) throws IOException {
		HttpHeaders headers = LinsirHttpHeadersContextHolder.get();
		// 考虑2中情况 1. RestTemplate 不是用 hystrix 2. 使用 hystrix
		if (headers == null) {
			headers = LinsirHttpHeadersContextHolder.toHeaders(accountGetter, properties);
		}
		if (headers != null && !headers.isEmpty()) {
			HttpHeaders httpHeaders = request.getHeaders();
			headers.forEach((key, values) -> {
				values.forEach(value -> httpHeaders.add(key, value));
			});
		}
		return execution.execute(request, bytes);
	}
}
