/**
 *  File: ValidateService.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.service;


/**
 * 对象校验证
 */
public interface ValidateService {

	/**
	 * 
	 * @param request
	 * @return
	 */
	public String validate(ValidateRequest request) throws Exception;
}
