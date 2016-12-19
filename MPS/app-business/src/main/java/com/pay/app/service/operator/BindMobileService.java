/*
 * Copyright (c) 2004-2013 pay.com . All rights reserved. 
 */
package com.pay.app.service.operator;

import com.pay.app.service.operator.statusEnum.BindMobleSmsType;


/**
 * 操作员绑定手机号
 * @author DaiDeRong
 *
 */
public interface BindMobileService {

	/*
	--手机绑定申请  sm 2022 email 2023
	--更改手机号码  sm 2024
	--手机取消绑定  sm 2025
	--数字证书申请  sm 2026 email 2027
	--数字证书安装  sm 2028 
	--数字证书取消  sm 2029
	对应模板id，开发环境已执行
	*/ 

    /**
     * 发送确认码
     * @param mobile
     * @param memberCode
     * @param bindSmsType
     */
	public void sendConfirmCode(String mobile,Long memberCode,BindMobleSmsType bindSmsType);
	
	 /**
	  * 发送成功邮件
	  * @param email
	  * @param memberName
	  * @param memberMobile
	  */
	public void sendBindSuccessEmail(String email,String memberName,String  memberMobile);
	
	/**
	 * 验证手机验证码
	 * @param memberCode
	 * @param checkCode
	 * @return
	 */
	public boolean validateConfirmCode(Long memberCode,String checkCode,BindMobleSmsType bindSmsType );
}
