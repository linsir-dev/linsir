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


import com.fasterxml.jackson.databind.ObjectMapper;
import com.linsir.core.cloud.hystrix.LinsirHystrixAccountGetter;
import com.linsir.core.cloud.props.LinsirHystrixHeadersProperties;
import com.linsir.core.tool.utils.Charsets;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.support.FeignHttpClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Http RestTemplateHeaderInterceptor 配置
 *
 * @author L.cm
 */
@Configuration
@ConditionalOnClass(okhttp3.OkHttpClient.class)
@AllArgsConstructor
public class RestTemplateConfiguration {
	private final ObjectMapper objectMapper;

	/**
	 * dev, test 环境打印出BODY
	 *
	 * @return HttpLoggingInterceptor
	 */
	@Bean("httpLoggingInterceptor")
	@Profile({"dev", "test"})
	public HttpLoggingInterceptor testLoggingInterceptor() {
		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new OkHttpSlf4jLogger());
		interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		return interceptor;
	}

	/**
	 * ontest 环境 打印 请求头
	 *
	 * @return HttpLoggingInterceptor
	 */
	@Bean("httpLoggingInterceptor")
	@Profile("ontest")
	public HttpLoggingInterceptor onTestLoggingInterceptor() {
		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new OkHttpSlf4jLogger());
		interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
		return interceptor;
	}

	/**
	 * prod 环境只打印请求url
	 *
	 * @return HttpLoggingInterceptor
	 */
	@Bean("httpLoggingInterceptor")
	@Profile("prod")
	public HttpLoggingInterceptor prodLoggingInterceptor() {
		HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new OkHttpSlf4jLogger());
		interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
		return interceptor;
	}

	/**
	 * okhttp3 链接池配置
	 *
	 * @param
	 * @param
	 * @return okhttp3.ConnectionPool
	 */
	/*@Bean
	@ConditionalOnMissingBean(okhttp3.ConnectionPool.class)
	public okhttp3.ConnectionPool httpClientConnectionPool(
		FeignHttpClientProperties httpClientProperties,
		OkHttpClientConnectionPoolFactory connectionPoolFactory) {
		Integer maxTotalConnections = httpClientProperties.getMaxConnections();
		Long timeToLive = httpClientProperties.getTimeToLive();
		TimeUnit ttlUnit = httpClientProperties.getTimeToLiveUnit();
		return connectionPoolFactory.create(maxTotalConnections, timeToLive, ttlUnit);
	}*/

	@Bean
	public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setReadTimeout(5000);//单位为ms
		factory.setConnectTimeout(5000);//单位为ms
		return factory;
	}

	/**
	 * 配置OkHttpClient
	 *
	 * @param
	 * @return OkHttpClient
	 */
	/*@Bean
	@ConditionalOnMissingBean(okhttp3.OkHttpClient.class)
	public okhttp3.OkHttpClient httpClient(
		OkHttpClientFactory httpClientFactory,
		okhttp3.ConnectionPool connectionPool,
		FeignHttpClientProperties httpClientProperties,
		HttpLoggingInterceptor interceptor)
	{
		Boolean followRedirects = httpClientProperties.isFollowRedirects();
		Integer connectTimeout = httpClientProperties.getConnectionTimeout();
		return httpClientFactory.createBuilder(httpClientProperties.isDisableSslValidation())
			.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
			.writeTimeout(30, TimeUnit.SECONDS)
			.readTimeout(30, TimeUnit.SECONDS)
			.followRedirects(followRedirects)
			.connectionPool(connectionPool)
			.addInterceptor(interceptor)
			.build();
	}*/

	@Bean
	public RestTemplateHeaderInterceptor requestHeaderInterceptor(
		@Autowired(required = false) @Nullable LinsirHystrixAccountGetter accountGetter,
		LinsirHystrixHeadersProperties properties) {
		return new RestTemplateHeaderInterceptor(accountGetter, properties);
	}

	/**
	 * 普通的 RestTemplate，不透传请求头，一般只做外部 http 调用
	 *
	 * @param
	 * @return RestTemplate
	 */
	/*@Bean
	@ConditionalOnMissingBean(RestTemplate.class)
	public RestTemplate restTemplate(okhttp3.OkHttpClient httpClient) {
		RestTemplate restTemplate = new RestTemplate(new OkHttp3ClientHttpRequestFactory(httpClient));
		configMessageConverters(restTemplate.getMessageConverters());
		return restTemplate;
	}*/

	@Bean
	public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
		RestTemplate restTemplate = new RestTemplate(factory);
		// 支持中文编码
		restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(Charset.forName("UTF-8")));
		return restTemplate;
	}


	/**
	 * 支持负载均衡的 LbRestTemplate
	 *
	 * @param
	 * @return LbRestTemplate
	 */
	/*@Bean
	@LoadBalanced
	@ConditionalOnMissingBean(LbRestTemplate.class)
	public LbRestTemplate lbRestTemplate(okhttp3.OkHttpClient httpClient, RestTemplateHeaderInterceptor interceptor) {
		LbRestTemplate lbRestTemplate = new LbRestTemplate(new OkHttp3ClientHttpRequestFactory(httpClient));
		lbRestTemplate.setInterceptors(Collections.singletonList(interceptor));
		configMessageConverters(lbRestTemplate.getMessageConverters());
		return lbRestTemplate;
	}*/

	private void configMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.removeIf(x -> x instanceof StringHttpMessageConverter || x instanceof MappingJackson2HttpMessageConverter);
		converters.add(new StringHttpMessageConverter(Charsets.UTF_8));
		converters.add(new MappingJackson2HttpMessageConverter(objectMapper));
	}
}
