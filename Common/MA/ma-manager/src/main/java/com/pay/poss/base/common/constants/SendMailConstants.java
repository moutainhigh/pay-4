package com.pay.poss.base.common.constants;


public class SendMailConstants {
	/**
	 * @Description 企业会员密码重置发送邮件短信常量
	 * @project 	ma-memberanager
	 * @file 		SendMailConstants.java 
	 * @note		<br>
	 * @develop		JDK1.6 + Eclipse 3.5
	 * @version     1.0
	 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
	 * Date				Author			Changes
	 * 2010-12-27		tianqing_wang			Create
	 */

	//发送邮件内容模板(订单邮件通知) add by davis.guo at 2016-09-03
	public static final Integer ORDER_EMAIL_NOTIFY_TEMPID = Integer.valueOf(20160903);
	 
	//发送邮件内容模板(企业会员密码重置) 登录密码模板
	public static final Integer PASSWORDRESET_ENTERPRISE_LOGIN_EMAIL_TEMPID = Integer.valueOf(2000);
	//发送邮件内容模板(企业会员密码重置) 支付密码模板
	public static final Integer PASSWORDRESET_ENTERPRISE_PAY_EMAIL_TEMPID = Integer.valueOf(2003);
	//邮件通知中的发件人(企业会员密码重置)
	public static final String PASSWORDRESET_ENTERPRISE_EMAIL_SENDER = "pay@pay.com";
	//邮件通知中的邮件标题(企业会员密码重置)
	public static final String PASSWORDRESET_ENTERPRISE_EMAIL_TITLE = "支付重置密码";
	//邮件通知中的路径(企业会员密码重置) 登录密码链接
	//public static final String PASSWORDRESET_EMAIL_TYPELOGIN = "https://qaapp.pay.com/website/passwordfind/corpEmailValidate.htm?source=resetP&code=";
	public static final String PASSWORDRESET_EMAIL_TYPELOGIN_URI = "passwordfind/corpEmailValidate.htm?source=resetP&code=";
	
	//邮件通知中的路径(企业会员密码重置) 支付密码链接
	//public static final String PASSWORDRESET_EMAIL_TYPEPAY = "https://qaapp.pay.com/website/corp/tofoundpaypasswordbyemailpage.htm?code=";
	public static final String PASSWORDRESET_EMAIL_TYPEPAY_URI = "corp/tofoundpaypasswordbyemailpage.htm?code=";
	
	//发送短信内容模板(企业会员密码重置)
	public static final Integer PASSWORDRESET_ENTERPRISE_MESSAGE_TEMPID = Integer.valueOf(2001);
	public static final String TYPENAME_TYPEPAY = "typePay";                      //支付密码
	public static final String TYPENAME_TYPELOGIN = "typeLogin";				  //登录密码
	public static final String ZN_TYPENAME_TYPEPAY = "支付";                   //支付密码 (用于在邮件中显示)
	public static final String ZN_TYPENAME_TYPELOGIN = "登录";				  //登录密码(用于在邮件中显示)
	public static final String DISPOSE_CONFIRM = "confirm";                   //审核通过
	public static final String DISPOSE_REFUSE = "refuse";				  	  //审核拒绝
	
	public static final String ORIGN_TYPEPAY = "poss_corp_pay_pwdreset";       //支付密码 checkCode中的来源
	public static final String ORIGN_TYPELOGIN = "poss_corp_login_pwdreset";	//登录密码checkCode中的来源 
	
}
