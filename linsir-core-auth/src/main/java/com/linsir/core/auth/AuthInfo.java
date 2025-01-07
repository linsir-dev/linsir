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
package com.linsir.core.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * AuthInfo
 *
 * @author Chill
 */
@Data
@Schema(name = "认证信息",defaultValue = "认证信息-返回")
public class AuthInfo {

	@Schema(name = "令牌")
	private String accessToken;

	@Schema(name = "令牌类型")
	private String tokenType;

	@Schema(name = "头像")
	private String avatar = "https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png";

	@Schema(name = "角色名")
	private String authority;

	@Schema(name = "用户名")
	private String userName;

	@Schema(name = "账号名")
	private String account;

	@Schema(name = "过期时间")
	private long expiresIn;

	@Schema(name = "许可证")
	private String license = "made by blade";
}
