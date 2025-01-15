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
package com.linsir.core.log.error;

import com.linsir.core.log.publisher.ErrorLogPublisher;
import com.linsir.core.tool.utils.BeanUtil;
import com.linsir.core.vo.jsonResults.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * 全局异常处理
 *
 * @author Chill
 */
@Slf4j
public class LinsirErrorAttributes extends DefaultErrorAttributes {

	public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
		String requestUri = this.getAttr(webRequest, "javax.servlet.error.request_uri");
		Integer status = this.getAttr(webRequest, "javax.servlet.error.status_code");
		Throwable error = getError(webRequest);
		JsonResult result = null;
		if (error == null) {
			log.error("URL:{} error status:{}", requestUri, status);
			JsonResult.FAIL_EXCEPTION("系统未知异常[HttpStatus]");
			/*result = R.fail(ResultCode.FAILURE, "系统未知异常[HttpStatus]:" + status);*/
		} else {
			log.error(String.format("URL:%s error status:%d", requestUri, status), error);
			/*result = R.fail(status, error.getMessage());*/
			result = JsonResult.FAIL_EXCEPTION(error.getMessage());
		}
		// 发送服务异常事件
		ErrorLogPublisher.publishEvent(error, requestUri);
		return BeanUtil.toMap(result);
	}

	@Nullable
	private <T> T getAttr(WebRequest webRequest, String name) {
		return (T) webRequest.getAttribute(name, RequestAttributes.SCOPE_REQUEST);
	}

}
