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

import com.linsir.core.vo.ExtLabelValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户实体
 *
 * @author Chill
 */
@Data
public class LinsirUser implements Serializable {

	private static final long serialVersionUID = 1L;


	private Long id;
	/**
	 * 客户端id
	 */
	@Schema(name = "客户端Id",hidden = true)
	private String clientId;

	/**
	 * 用户id
	 */
	@Schema(name = "用户id",hidden = true)
	private Long userId;
	/**
	 * 账号
	 */
	@Schema(name = "账号",hidden = true)
	private String account;
	/**
	 * 用户名
	 */
	@Schema(name = "用户名",hidden = true)
	private String userName;
	/**
	 * 昵称
	 */
	@Schema(name = "昵称",hidden = true)
	private String nickName;
	/**
	 * 租户ID
	 */
	@Schema(name = "租户ID",hidden = true)
	private String tenantId;
	/**
	 * 第三方认证ID
	 */
	@Schema(name = "第三方认证ID",hidden = true)
	private String oauthId;
	/**
	 * 部门id
	 */
	@Schema(name = "部门id",hidden = true)
	private String deptId;
	/**
	 * 岗位id
	 */
	@Schema(name = "岗位id",hidden = true)
	private String postId;
	/**
	 * 角色id
	 */
	@Schema(name = "角色id",hidden = true)
	private String roleId;
	/**
	 * 角色名
	 */

	@Schema(name = "角色名",hidden = true)
	private String roleName;
	/**
	 * 用户类型
	 */
	@Schema(name = "用户类型",hidden = true)
	private String type;


	private ExtLabelValue extensionObj;
}
