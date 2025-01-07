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
package com.linsir.core.cloud.hystrix;

import com.linsir.core.cloud.props.LinsirHystrixHeadersProperties;
import com.linsir.core.tool.utils.ThreadLocalUtil;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * HttpHeaders hystrix Callable
 *
 * @param <V> 泛型标记
 * @author L.cm
 */
public class LinsirHttpHeadersCallable<V> implements Callable<V> {
	/**
	 * http委托
	 */
	private final Callable<V> delegate;
	/**
	 * 本地线程中的值
	 */
	private final Map<String, Object> tlMap;
	/**
	 * logback 下有可能为 null
	 */
	@Nullable
	private final Map<String, String> mdcMap;
	/**
	 * http请求头
	 */
	@Nullable
	private HttpHeaders httpHeaders;

	public LinsirHttpHeadersCallable(Callable<V> delegate,
									@Nullable LinsirHystrixAccountGetter accountGetter,
									LinsirHystrixHeadersProperties properties) {
		this.delegate = delegate;
		this.tlMap = ThreadLocalUtil.getAll();
		this.mdcMap = MDC.getCopyOfContextMap();
		this.httpHeaders = LinsirHttpHeadersContextHolder.toHeaders(accountGetter, properties);
	}

	@Override
	public V call() throws Exception {
		try {
			if (!tlMap.isEmpty()) {
				ThreadLocalUtil.put(tlMap);
			}
			if (mdcMap != null && !mdcMap.isEmpty()) {
				MDC.setContextMap(mdcMap);
			}
			LinsirHttpHeadersContextHolder.set(httpHeaders);
			return delegate.call();
		} finally {
			tlMap.clear();
			if (mdcMap != null) {
				mdcMap.clear();
			}
			ThreadLocalUtil.clear();
			MDC.clear();
			LinsirHttpHeadersContextHolder.remove();
			if (httpHeaders != null) {
				httpHeaders.clear();
			}
			httpHeaders = null;
		}
	}
}
