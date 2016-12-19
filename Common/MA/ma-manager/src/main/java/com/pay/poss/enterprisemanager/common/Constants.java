package com.pay.poss.enterprisemanager.common;


/**
 * 
 * @Description 
 * @project 	poss-membermanager
 * @file 		Constants.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-9-19		gungun_zhang			Create
 */

public interface Constants {

	
	
	// 问候语
	public static final String GREETING = "您好！";
	//会员状态
	public static final Integer STATUS_CREATE = 0;
	public static final Integer STATUS_UNACTIVATION = 3;
	//会员类型
	public static final Integer MEMBERTYPE = 2;
	//邮件通知激活会员逾期天数
	public static final String UNACTIVATIONDATE = "5";
	//url是否有效
	public static final String URLUNACTIVATION = "1";
	public static final String URLACTIVATION = "2";
	//安全问题ID
	public static final Integer SECUTITYQUESTION = 1;
	//安全问题答案
	public static final String SECURITYANSWER = "WOYO";
	//与sso关联的用户id
	public static final String SSOID = "123456";
	//登陆方式 1手机号，2Email
	public static final Integer LOGINTYPE = 2;		
	//审核状态
	public static final Integer CHECK_NO = 0;
	public static final Integer CHECK_YES = 1;
	public static final Integer CHECK_READYCHECK = 2;
	//邮件激活通知中的发件人
	public static final String EMAIL_SENDER = "pay@pay.com";
	//邮件激活通知中的邮件标题
	public static final String EMAIL_TITLE = "商户激活通知";
	//邮件激活通知中的激活路径
	//请使用PossPropertiesUtil#getWebsiteContextPath得到上下文地址+EMAIL_ACTIVATIONURL_URI = EMAIL_ACTIVATIONURL
	public static final String EMAIL_ACTIVATIONURL_URI = "enterpriseActive.htm?code=";
	
	//邮件激活通知中的邮件内容模板
	public static final Long EMAIL_TEMPID = Long.valueOf(13);
	//部门
	public static final String DEPT_RISK= "2";
	public static final String DEPT_MARKET= "1";
	//人民币账户
	public static final Integer ACCOUNTTYPE_CHINESE = 10;
	//默认可用余额
	public static final Long BALANCE = Long.valueOf(0);
	//默认冻结金额
	public static final Long FROZEN = Long.valueOf(0);
	//默认借方发生额
	public static final Long CREDITBALANCE = Long.valueOf(0);
	//默认贷方发生额
	public static final Long DEBITBALANCE = Long.valueOf(0);
	//默认账户状态-0无效
	public static final Integer ACCOUNTSTATUS = 1;
	//类型为企业
	public static final String ENTERPRISETYPE= "2";
	//默认账户pw
	public static final String PW= "admin";
	//操作员登录标识
	public static final String OPLOGINNAME= "admin";
	//操作员登录名字
	public static final String OPNAME= "admin";
	//操作员证件类型
	public static final Integer OPCERTTYPE= 1;
	//操作员状态
	public static final Integer OPSTATUS= 1; 
	//未知属性
	public static final String UNKNOW= "未知";
	//未知属性
	public static final Long UNKNOW_LONG= Long.valueOf(0);
	//合同状态
	public static final Integer CONTRACTSTATUS= 1;
	//商户号前缀
	public static final String NEWMERCHANT_ACQ= "515";
	//产品开通状态
	public static final Integer PRODUCTSTATUS_OPEN = 1;
	//searchKey
	public static final String SEARCHKEY= "^[A-Za-z].+$";
	//ENCODING
	public static final String ENCODING_ISO = "ISO-8859-1";
	public static final String ENCODING_UTF = "UTF-8";
}
