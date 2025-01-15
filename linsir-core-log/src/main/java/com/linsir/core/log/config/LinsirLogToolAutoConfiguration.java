/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
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
 *  Author: Chill 庄骞 (smallchill@163.com)
 */

package com.linsir.core.log.config;


import com.linsir.core.launch.props.LinsirProperties;
import com.linsir.core.launch.props.LinsirPropertySource;
import com.linsir.core.launch.server.ServerInfo;
import com.linsir.core.log.aspect.ApiLogAspect;
import com.linsir.core.log.aspect.LogTraceAspect;
import com.linsir.core.log.event.ApiLogListener;
import com.linsir.core.log.event.ErrorLogListener;
import com.linsir.core.log.event.UsualLogListener;
import com.linsir.core.log.feign.ILogClient;
import com.linsir.core.log.filter.LogTraceFilter;
import com.linsir.core.log.logger.LinsirLogger;
import com.linsir.core.log.props.LinsirRequestLogProperties;
import jakarta.servlet.DispatcherType;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;


/**
 * 日志工具自动配置
 *
 * @author Chill
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(LinsirRequestLogProperties.class)
@LinsirPropertySource(value = "classpath:/linsir-log.yml")
public class LinsirLogToolAutoConfiguration {

	@Bean
	public ApiLogAspect apiLogAspect() {
		return new ApiLogAspect();
	}

	@Bean
	public LogTraceAspect logTraceAspect() {
		return new LogTraceAspect();
	}

	@Bean
	public LinsirLogger bladeLogger() {
		return new LinsirLogger();
	}

	@Bean
	public FilterRegistrationBean<LogTraceFilter> logTraceFilterRegistration() {
		FilterRegistrationBean<LogTraceFilter> registration = new FilterRegistrationBean<>();
		registration.setDispatcherTypes(DispatcherType.REQUEST);
		registration.setFilter(new LogTraceFilter());
		registration.addUrlPatterns("/*");
		registration.setName("LogTraceFilter");
		registration.setOrder(Ordered.LOWEST_PRECEDENCE);
		return registration;
	}

	@Bean
	@ConditionalOnMissingBean(name = "apiLogListener")
	public ApiLogListener apiLogListener(ILogClient logService, ServerInfo serverInfo, LinsirProperties linsirProperties) {
		return new ApiLogListener(logService, serverInfo, linsirProperties);
	}

	@Bean
	@ConditionalOnMissingBean(name = "errorEventListener")
	public ErrorLogListener errorEventListener(ILogClient logService, ServerInfo serverInfo, LinsirProperties linsirProperties) {
		return new ErrorLogListener(logService, serverInfo, linsirProperties);
	}

	@Bean
	@ConditionalOnMissingBean(name = "usualEventListener")
	public UsualLogListener usualEventListener(ILogClient logService, ServerInfo serverInfo, LinsirProperties linsirProperties) {
		return new UsualLogListener(logService, serverInfo, linsirProperties);
	}

}
