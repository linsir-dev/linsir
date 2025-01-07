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
package com.linsir.core.cloud.hystrix;


import com.linsir.core.auth.LinsirUser;
import com.linsir.core.auth.utils.AuthUtil;
import com.linsir.core.tool.utils.Charsets;
import com.linsir.core.tool.utils.UrlUtil;
import jakarta.servlet.http.HttpServletRequest;

/**
 * 用户信息获取器
 *
 * @author Chill
 */
public class LinsirAccountGetter implements LinsirHystrixAccountGetter {

	@Override
	public String get(HttpServletRequest request) {
		LinsirUser account = AuthUtil.getUser();
		if (account == null) {
			return null;
		}
		// 增加用户头, 123[admin]
		String xAccount = String.format("%s[%s]", account.getUserId(), account.getUserName());
		return UrlUtil.encodeURL(xAccount, Charsets.UTF_8);
	}

}
