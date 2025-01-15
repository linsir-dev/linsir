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

import com.linsir.core.auth.exception.SecureException;
import com.linsir.core.code.BaseCode;
import com.linsir.core.log.exception.ServiceException;
import com.linsir.core.log.publisher.ErrorLogPublisher;
import com.linsir.core.tool.utils.Func;
import com.linsir.core.tool.utils.UrlUtil;
import com.linsir.core.tool.utils.WebUtil;
import com.linsir.core.vo.jsonResults.JsonResult;
import jakarta.servlet.Servlet;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.DispatcherServlet;


/**
 * 未知异常转译和发送，方便监听，对未知异常统一处理。Order 排序优先级低
 *
 * @author Chill
 */
@Slf4j
@Order
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass({Servlet.class, DispatcherServlet.class})
@RestControllerAdvice
public class LinsirRestExceptionTranslator {

	@ExceptionHandler(ServiceException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public JsonResult handleError(ServiceException e) {
		log.error("业务异常", e);
		/*return R.fail(e.getResultCode(), e.getMessage());*/
		return  JsonResult.FAIL_EXCEPTION(e.getMessage());
	}

	@ExceptionHandler(SecureException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public JsonResult handleError(SecureException e) {
		log.error("认证异常", e);
		/*return R.fail(e.getResultCode(), e.getMessage());*/
		return JsonResult.FAIL_EXCEPTION(e.getMessage());
	}

	@ExceptionHandler(Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public JsonResult handleError(Throwable e) {
		log.error("服务器异常", e);
		// 发送服务异常事件
		ErrorLogPublisher.publishEvent(e, UrlUtil.getPath(WebUtil.getRequest().getRequestURI()));
		return JsonResult.FAIL_EXCEPTION(Func.isEmpty(e.getMessage()) ? BaseCode.FAIL_EXCEPTION.getMsg() : e.getMessage());
/*
		return R.fail(ResultCode.INTERNAL_SERVER_ERROR, (Func.isEmpty(e.getMessage()) ? ResultCode.INTERNAL_SERVER_ERROR.getMessage() : e.getMessage()));
*/
	}

}
