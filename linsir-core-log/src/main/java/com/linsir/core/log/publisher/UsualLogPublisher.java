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

package com.linsir.core.log.publisher;


import com.linsir.core.log.constant.EventConstant;
import com.linsir.core.log.event.UsualLogEvent;
import com.linsir.core.log.model.LogUsual;
import com.linsir.core.log.utils.LogAbstractUtil;
import com.linsir.core.tool.utils.SpringUtil;
import com.linsir.core.tool.utils.WebUtil;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * BLADE日志信息事件发送
 *
 * @author Chill
 */
public class UsualLogPublisher {

	public static void publishEvent(String level, String id, String data) {
		HttpServletRequest request = WebUtil.getRequest();
		LogUsual logUsual = new LogUsual();
		logUsual.setLogLevel(level);
		logUsual.setLogId(id);
		logUsual.setLogData(data);
		Thread thread = Thread.currentThread();
		StackTraceElement[] trace = thread.getStackTrace();
		if (trace.length > 3) {
			logUsual.setMethodClass(trace[3].getClassName());
			logUsual.setMethodName(trace[3].getMethodName());
		}
		LogAbstractUtil.addRequestInfoToLog(request, logUsual);
		Map<String, Object> event = new HashMap<>(16);
		event.put(EventConstant.EVENT_LOG, logUsual);
		SpringUtil.publishEvent(new UsualLogEvent(event));
	}

}
