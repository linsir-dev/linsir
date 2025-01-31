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
package com.linsir.core.auth.utils;


import cn.hutool.jwt.Claims;
import com.linsir.core.auth.LinsirUser;
import com.linsir.core.constant.RoleConstant;
import com.linsir.core.jwt.JwtUtil;
import com.linsir.core.jwt.props.JwtProperties;
import com.linsir.core.launch.constant.TokenConstant;
import com.linsir.core.utils.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Objects;

/**
 * Auth工具类
 *
 * @author Chill
 */
public class AuthUtil {
	private static final String LINSIR_USER_REQUEST_ATTR = "_LINSIR_USER_REQUEST_ATTR_";

	private final static String HEADER = TokenConstant.HEADER;
	private final static String ACCOUNT = TokenConstant.ACCOUNT;
	private final static String USER_NAME = TokenConstant.USER_NAME;
	private final static String NICK_NAME = TokenConstant.NICK_NAME;
	private final static String USER_ID = TokenConstant.USER_ID;
	private final static String DEPT_ID = TokenConstant.DEPT_ID;
	private final static String POST_ID = TokenConstant.POST_ID;
	private final static String ROLE_ID = TokenConstant.ROLE_ID;
	private final static String ROLE_NAME = TokenConstant.ROLE_NAME;
	private final static String TENANT_ID = TokenConstant.TENANT_ID;
	private final static String OAUTH_ID = TokenConstant.OAUTH_ID;
	private final static String CLIENT_ID = TokenConstant.CLIENT_ID;
	private final static String USER_TYPE = TokenConstant.USER_TYPE;
	private final static String ADMIN_PRIVATE_TYPE = "7";

	private static JwtProperties jwtProperties;

	/**
	 * 获取配置类
	 *
	 * @return jwtProperties
	 */
	private static JwtProperties getJwtProperties() {
		if (jwtProperties == null) {
			jwtProperties = SpringUtil.getBean(JwtProperties.class);
		}
		return jwtProperties;
	}

	/**
	 * 获取用户信息
	 *
	 * @return BladeUser
	 */
	public static LinsirUser getUser() {
		HttpServletRequest request = WebUtil.getRequest();
		if (request == null) {
			return null;
		}
		// 优先从 request 中获取
		Object linsirUser = request.getAttribute(LINSIR_USER_REQUEST_ATTR);
		if (linsirUser == null) {
			linsirUser = getUser(request);
			if (linsirUser != null) {
				// 设置到 request 中
				request.setAttribute(LINSIR_USER_REQUEST_ATTR, linsirUser);
			}
		}
		return (LinsirUser) linsirUser;
	}

	/**
	 * 获取用户信息
	 *
	 * @param request request
	 * @return BladeUser
	 */
	public static LinsirUser getUser(HttpServletRequest request) {
		Claims claims = getClaims(request);
		if (claims == null) {
			return null;
		}
		String clientId = Func.toStr(claims.getClaim(AuthUtil.CLIENT_ID));
		Long userId = Func.toLong(claims.getClaim(AuthUtil.USER_ID));
		String tenantId = Func.toStr(claims.getClaim(AuthUtil.TENANT_ID));
		String oauthId = Func.toStr(claims.getClaim(AuthUtil.OAUTH_ID));
		String deptId = Func.toStrWithEmpty(claims.getClaim(AuthUtil.DEPT_ID), StringPool.MINUS_ONE);
		String postId = Func.toStrWithEmpty(claims.getClaim(AuthUtil.POST_ID), StringPool.MINUS_ONE);
		String roleId = Func.toStrWithEmpty(claims.getClaim(AuthUtil.ROLE_ID), StringPool.MINUS_ONE);
		String account = Func.toStr(claims.getClaim(AuthUtil.ACCOUNT));
		String roleName = Func.toStr(claims.getClaim(AuthUtil.ROLE_NAME));
		String userName = Func.toStr(claims.getClaim(AuthUtil.USER_NAME));
		String nickName = Func.toStr(claims.getClaim(AuthUtil.NICK_NAME));
		String type = Func.toStr(claims.getClaim(AuthUtil.USER_TYPE));
		LinsirUser linsirUser = new LinsirUser();
		linsirUser.setClientId(clientId);
		linsirUser.setUserId(userId);
		linsirUser.setTenantId(tenantId);
		linsirUser.setOauthId(oauthId);
		linsirUser.setAccount(account);
		linsirUser.setDeptId(deptId);
		linsirUser.setPostId(postId);
		linsirUser.setRoleId(roleId);
		linsirUser.setRoleName(roleName);
		linsirUser.setUserName(userName);
		linsirUser.setNickName(nickName);
		linsirUser.setType(type);
		return linsirUser;
	}

	/**
	 * 是否为超管
	 *
	 * @return boolean
	 */
	public static boolean isAdministrator() {
		return StringUtil.containsAny(getUserRole(), RoleConstant.ADMINISTRATOR);
	}

	/**
	 * 是否为超管 私有云使用
	 *
	 * @return boolean
	 */
	public static boolean isAdministratorPrivate() {
		return ADMIN_PRIVATE_TYPE.equals(getUser().getType());
	}

	/**
	 * 获取用户id
	 *
	 * @return userId
	 */
	public static Long getUserId() {
		LinsirUser user = getUser();
		return (null == user) ? -1 : user.getUserId();
	}

	/**
	 * 获取用户id
	 *
	 * @param request request
	 * @return userId
	 */
	public static Long getUserId(HttpServletRequest request) {
		LinsirUser user = getUser(request);
		return (null == user) ? -1 : user.getUserId();
	}

	/**
	 * 获取用户账号
	 *
	 * @return userAccount
	 */
	public static String getUserAccount() {
		LinsirUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getAccount();
	}

	/**
	 * 获取用户账号
	 *
	 * @param request request
	 * @return userAccount
	 */
	public static String getUserAccount(HttpServletRequest request) {
		LinsirUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getAccount();
	}

	/**
	 * 获取用户名
	 *
	 * @return userName
	 */
	public static String getUserName() {
		LinsirUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getUserName();
	}

	/**
	 * 获取用户名
	 *
	 * @param request request
	 * @return userName
	 */
	public static String getUserName(HttpServletRequest request) {
		LinsirUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getUserName();
	}

	/**
	 * 获取昵称
	 *
	 * @return userName
	 */
	public static String getNickName() {
		LinsirUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getNickName();
	}

	/**
	 * 获取昵称
	 *
	 * @param request request
	 * @return userName
	 */
	public static String getNickName(HttpServletRequest request) {
		LinsirUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getNickName();
	}

	/**
	 * 获取用户部门
	 *
	 * @return userName
	 */
	public static String getDeptId() {
		LinsirUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getDeptId();
	}

	/**
	 * 获取用户部门
	 *
	 * @param request request
	 * @return userName
	 */
	public static String getDeptId(HttpServletRequest request) {
		LinsirUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getDeptId();
	}

	/**
	 * 获取用户岗位
	 *
	 * @return userName
	 */
	public static String getPostId() {
		LinsirUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getPostId();
	}

	/**
	 * 获取用户岗位
	 *
	 * @param request request
	 * @return userName
	 */
	public static String getPostId(HttpServletRequest request) {
		LinsirUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getPostId();
	}

	/**
	 * 获取用户角色
	 *
	 * @return userName
	 */
	public static String getUserRole() {
		LinsirUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getRoleName();
	}

	/**
	 * 获取用角色
	 *
	 * @param request request
	 * @return userName
	 */
	public static String getUserRole(HttpServletRequest request) {
		LinsirUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getRoleName();
	}

	/**
	 * 获取租户ID
	 *
	 * @return tenantId
	 */
	public static String getTenantId() {
		LinsirUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getTenantId();
	}

	/**
	 * 获取租户ID
	 *
	 * @param request request
	 * @return tenantId
	 */
	public static String getTenantId(HttpServletRequest request) {
		LinsirUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getTenantId();
	}

	/**
	 * 获取第三方认证ID
	 *
	 * @return tenantId
	 */
	public static String getOauthId() {
		LinsirUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getOauthId();
	}

	/**
	 * 获取第三方认证ID
	 *
	 * @param request request
	 * @return tenantId
	 */
	public static String getOauthId(HttpServletRequest request) {
		LinsirUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getOauthId();
	}

	/**
	 * 获取客户端id
	 *
	 * @return clientId
	 */
	public static String getClientId() {
		LinsirUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getClientId();
	}

	/**
	 * 获取客户端id
	 *
	 * @param request request
	 * @return clientId
	 */
	public static String getClientId(HttpServletRequest request) {
		LinsirUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getClientId();
	}

	/**
	 * 获取Claims
	 *
	 * @param request request
	 * @return Claims
	 */
	public static Claims getClaims(HttpServletRequest request) {
		String auth = request.getHeader(AuthUtil.HEADER);
		Claims claims = null;
		String token;
		// 获取 Token 参数
		if (StringUtil.isNotBlank(auth)) {
			token = JwtUtil.getToken(auth);
		} else {
			String parameter = request.getParameter(AuthUtil.HEADER);
			token = JwtUtil.getToken(parameter);
		}
		// 获取 Token 值
		if (StringUtil.isNotBlank(token)) {
			claims = AuthUtil.parseJWT(token);
		}
		// 判断 Token 状态
		if (ObjectUtil.isNotEmpty(claims) && getJwtProperties().getState()) {
			String tenantId = Func.toStr(claims.getClaim(AuthUtil.TENANT_ID));
			String userId = Func.toStr(claims.getClaim(AuthUtil.USER_ID));
			String accessToken = JwtUtil.getAccessToken(tenantId, userId, token);
			if (!token.equalsIgnoreCase(accessToken)) {
				return null;
			}
		}
		return claims;
	}

	/**
	 * 获取请求头
	 *
	 * @return header
	 */
	public static String getHeader() {
		return getHeader(Objects.requireNonNull(WebUtil.getRequest()));
	}

	/**
	 * 获取请求头
	 *
	 * @param request request
	 * @return header
	 */
	public static String getHeader(HttpServletRequest request) {
		return request.getHeader(HEADER);
	}

	/**
	 * 解析jsonWebToken
	 *
	 * @param jsonWebToken jsonWebToken
	 * @return Claims
	 */
	public static Claims parseJWT(String jsonWebToken) {
		return JwtUtil.parseJWT(jsonWebToken);
	}

}
