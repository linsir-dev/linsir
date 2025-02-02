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
package com.linsir.core.constant;

/**
 * 系统默认角色
 *
 * @author Chill
 */
public class RoleConstant {

	public static final String ADMINISTRATOR = "administrator";

	public static final String HAS_ROLE_ADMINISTRATOR = "hasRole('" + ADMINISTRATOR + "')";

	public static final String ADMIN = "admin";

	public static final String HAS_ROLE_ADMIN = "hasAnyRole('" + ADMINISTRATOR + "', '" + ADMIN + "')";

	public static final String USER = "user";

	public static final String HAS_ROLE_USER = "hasRole('" + USER + "')";

	public static final String TEST = "test";

	public static final String HAS_ROLE_TEST = "hasRole('" + TEST + "')";


	/**通告对象类型（USER:指定用户，ALL:全体用户）*/
	public static final String MSG_TYPE_UESR  = "USER";
	public static final String MSG_TYPE_ALL  = "ALL";

	/**
	 * 是否用户已被冻结 1正常(解冻) 2冻结
	 */
	public static final Integer USER_UNFREEZE = 1;
	public static final Integer USER_FREEZE = 2;


	/** sys_user 表 username 唯一键索引 */
	public static final String SQL_INDEX_UNIQ_SYS_USER_USERNAME = "uniq_sys_user_username";
	/** sys_user 表 work_no 唯一键索引 */
	public static final String SQL_INDEX_UNIQ_SYS_USER_WORK_NO = "uniq_sys_user_work_no";
	/** sys_user 表 phone 唯一键索引 */
	public static final String SQL_INDEX_UNIQ_SYS_USER_PHONE = "uniq_sys_user_phone";
	/** 达梦数据库升提示。违反表[SYS_USER]唯一性约束 */
	public static final String SQL_INDEX_UNIQ_SYS_USER = "唯一性约束";

	/** sys_user 表 email 唯一键索引 */
	public static final String SQL_INDEX_UNIQ_SYS_USER_EMAIL = "uniq_sys_user_email";
	/** sys_quartz_job 表 job_class_name 唯一键索引 */
	public static final String SQL_INDEX_UNIQ_JOB_CLASS_NAME = "uniq_job_class_name";
	/** sys_position 表 code 唯一键索引 */
	public static final String SQL_INDEX_UNIQ_CODE = "uniq_code";
	/** sys_role 表 code 唯一键索引 */
	public static final String SQL_INDEX_UNIQ_SYS_ROLE_CODE = "uniq_sys_role_role_code";
	/** sys_depart 表 code 唯一键索引 */
	public static final String SQL_INDEX_UNIQ_DEPART_ORG_CODE = "uniq_depart_org_code";
	/** sys_category 表 code 唯一键索引 */
	public static final String SQL_INDEX_UNIQ_CATEGORY_CODE = "idx_sc_code";



	/**
	 * 管理员对应的租户ID
	 */
	public static final String ADMIN_TENANT_ID = "000000";

	/**
	 * 租户字段名
	 */
	public static final String DB_TENANT_KEY = "tenantCode";

	/**
	 * 租户数据库的字段名称
	 * */
	public static final String DB_TENANT_COLUMN = "tenant_code";

	/**
	 * 租户字段get方法
	 */
	public static final String DB_TENANT_KEY_GET_METHOD = "getTenantId";

	/**
	 * 租户字段set方法
	 */
	public static final String DB_TENANT_KEY_SET_METHOD = "setTenantId";

}
