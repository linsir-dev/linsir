/*
 *      Copyright (c) 2018-2028, DreamLu All rights reserved.
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
 *  Author: DreamLu 卢春梦 (596392912@qq.com)
 */
package com.linsir.core.tool.utils;

import org.springframework.lang.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具
 *
 * @author L.cm
 */
public class RegexUtil {
	/**
	 * 用户名
	 */
	public static final String USER_NAME = "^[a-zA-Z\\u4E00-\\u9FA5][a-zA-Z0-9_\\u4E00-\\u9FA5]{1,11}$";

	/**
	 * 密码
	 */
	public static final String USER_PASSWORD = "^.{6,32}$";

	/**
	 * 邮箱
	 */
	public static final String EMAIL = "^\\w+([-+.]*\\w+)*@([\\da-z](-[\\da-z])?)+(\\.{1,2}[a-z]+)+$";

	/**
	 * 手机号
	 */
	public static final String PHONE = "^1[3456789]\\d{9}$";

	/**
	 * 手机号或者邮箱
	 */
	public static final String EMAIL_OR_PHONE = EMAIL + "|" + PHONE;

	/**
	 * URL路径
	 */
	public static final String URL = "^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})(:[\\d]+)?([\\/\\w\\.-]*)*\\/?$";

	/**
	 * 身份证校验，初级校验，具体规则有一套算法
	 */
	public static final String ID_CARD = "^\\d{15}$|^\\d{17}([0-9]|X)$";

	/**
	 * 域名校验
	 */
	public static final String DOMAIN = "^[0-9a-zA-Z]+[0-9a-zA-Z\\.-]*\\.[a-zA-Z]{2,4}$";

	/**
	 * 编译传入正则表达式和字符串去匹配,忽略大小写
	 *
	 * @param regex        正则
	 * @param beTestString 字符串
	 * @return {boolean}
	 */
	public static boolean match(String regex, String beTestString) {
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(beTestString);
		return matcher.matches();
	}

	/**
	 * 编译传入正则表达式在字符串中寻找，如果匹配到则为true
	 *
	 * @param regex        正则
	 * @param beTestString 字符串
	 * @return {boolean}
	 */
	public static boolean find(String regex, String beTestString) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(beTestString);
		return matcher.find();
	}

	/**
	 * 编译传入正则表达式在字符串中寻找，如果找到返回第一个结果
	 * 找不到返回null
	 *
	 * @param regex         正则
	 * @param beFoundString 字符串
	 * @return {boolean}
	 */
	@Nullable
	public static String findResult(String regex, String beFoundString) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(beFoundString);
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}

}
