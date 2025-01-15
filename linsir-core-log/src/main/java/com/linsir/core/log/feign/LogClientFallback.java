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

import com.linsir.core.log.model.LogApi;
import com.linsir.core.log.model.LogError;
import com.linsir.core.log.model.LogUsual;
import com.linsir.core.vo.jsonResults.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 日志fallback
 *
 * @author jiang
 */
@Slf4j
@Component
public class LogClientFallback implements ILogClient {

	@Override
	public JsonResult<Boolean> saveUsualLog(LogUsual log) {
		/*return R.fail("usual log send fail");*/
		return JsonResult.FAIL_EXCEPTION("usual log send fail");
	}

	@Override
	public  JsonResult<Boolean> saveApiLog(LogApi log) {
		/*return R.fail("api log send fail");*/
		return JsonResult.FAIL_EXCEPTION("usual log send fail");
	}

	@Override
	public  JsonResult<Boolean> saveErrorLog(LogError log) {
		/*return R.fail("error log send fail");*/
		return JsonResult.FAIL_EXCEPTION("usual log send fail");
	}
}
