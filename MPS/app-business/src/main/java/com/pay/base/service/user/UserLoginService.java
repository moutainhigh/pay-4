/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.base.service.user;

import com.pay.base.dto.ResultDto;

/**
 * 用户登录服务
 * @author zhi.wang
 * @version $Id: UserLoginService.java, v 0.1 2010-11-10 下午04:30:47 zhi.wang Exp $
 */
public interface UserLoginService {

	/**
	 * 企业会员登录
	 * @param loginName
	 * @param identity
	 * @param loginPwd
	 * @return
	 */
	public ResultDto enterpriceMemberLogin(String loginName,String identity,String loginPwd);
	
	/**
	 * 个人会员登录
	 * @param loginName
	 * @param loginPwd
	 * @return
	 */
	public ResultDto individualMemberLogin(String loginName,String loginPwd);
}
