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
package com.linsir.core.log.feign;

import com.linsir.core.launch.constant.AppConstant;
import com.linsir.core.log.model.LogApi;
import com.linsir.core.log.model.LogError;
import com.linsir.core.log.model.LogUsual;
import com.linsir.core.results.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Feign接口类
 *
 * @author Chill
 */
@FeignClient(
	value = AppConstant.APPLICATION_LOG_NAME,
	fallback = LogClientFallback.class
)
public interface ILogClient {

	String API_PREFIX = "/log";

	/**
	 * 保存错误日志
	 *
	 * @param log
	 * @return
	 */
	@PostMapping(API_PREFIX + "/saveUsualLog")
	R saveUsualLog(@RequestBody LogUsual log);

	/**
	 * 保存操作日志
	 *
	 * @param log
	 * @return
	 */
	@PostMapping(API_PREFIX + "/saveApiLog")
	R saveApiLog(@RequestBody LogApi log);

	/**
	 * 保存错误日志
	 *
	 * @param log
	 * @return
	 */
	@PostMapping(API_PREFIX + "/saveErrorLog")
	R saveErrorLog(@RequestBody LogError log);

}
