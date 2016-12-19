package com.pay.app.mail;

import com.pay.util.UUIDUtil;

/**
 * @author lei.jiangl 
 * @version 
 * @data 2010-7-26 下午12:35:39
 * 指定提供发送邮件时URL内的验证码
 */
public class CheckCodeUrl {
	
	/**
	 * 获取36位随即验证码
	 * @return
	 */
	public static String getCheckCodeUrl(){
		return UUIDUtil.uuid();
	}
}
