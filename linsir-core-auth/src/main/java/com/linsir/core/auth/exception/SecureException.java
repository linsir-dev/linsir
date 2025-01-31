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
package com.linsir.core.auth.exception;

import com.linsir.core.code.ICode;
import com.linsir.core.code.ResultCode;
import lombok.Getter;

/**
 * Secure异常
 *
 * @author Chill
 */
public class SecureException extends RuntimeException {
	private static final long serialVersionUID = 2359767895161832954L;

	@Getter
	private final ICode resultCode;

	public SecureException(String message) {
		super(message);
		this.resultCode = ResultCode.UNAUTHORIZED;
	}

	public SecureException(ICode resultCode) {
		super(resultCode.getMsg());
		this.resultCode = resultCode;
	}

	public SecureException(ICode resultCode, Throwable cause) {
		super(cause);
		this.resultCode = resultCode;
	}

	@Override
	public Throwable fillInStackTrace() {
		return this;
	}
}
