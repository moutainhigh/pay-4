/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.acc.comm;

import org.apache.commons.lang.StringUtils;

/**
 * CheckCode来源类型定义枚举
 * @author zhi.wang
 * @version $Id: CheckCodeOriginEnum.java, v 0.1 2010-12-28 下午01:08:45 zhi.wang Exp $
 */
public enum CheckCodeOriginEnum {
	/** 临时用户手机注册激活 */
	ACTIVE_REGISTER_TEMP_MOBILE("active_register_temp_mobile","注册激活"),
	/** 临时用户邮箱注册激活 */
	ACTIVE_REGISTER_TEMP_EMAIL("active_register_temp_email","注册激活"),
	/** 注册激活 */
	ACTIVE_REGISTER("active_register","注册激活"),
	
	ACTIVE_EMAIL("active_email","激活邮箱"),
	
	/** 手机短信找回登录密码 */
	SMS_FINDLOGINPWD("sms_findLoginPwd","手机短信找回登录密码"),
	/** 邮箱找回登录密码 */
	EMAIL_FINDLOGINPWD("email_findLoginPwd","邮箱找回登录密码"),
	/** 后台申请找回企业登录密码 */
	POSS_CORP_EMAIL_FINDLOGINPWD("poss_corp_login_pwdreset","后台申请找回企业登录密码"),
	/** 后台申请找回企业支付密码 */
	POSS_CORP_EMAIL_FINDPAYPWD("poss_corp_pay_pwdreset","后台申请找回企业支付密码"),
	/** 后台录入商户激活 */
	POSS_CORP_ACTIVE_REGISTER("poss_active_merchant","后台录入商户激活"),
	/** 邮箱找回支付密码  */
	EMAIL_FINDPAYPWD("email_findPayPwd","邮箱找回支付密码"),
	/** 手机短信找回支付密码  */
	SMS_FINDPAYPWD("sms_findPayPwd","手机短信找回支付密码"),
	/** 口令卡解绑 */
	MATRIX_UNBIND("matrix_unbind","口令卡解绑"),
	
	/** 口令卡重置 */
	MATRIX_RESET("matrix_reset","口令卡重置"),
	
	/** 口令卡申请 */
	MATRIX_REQUEST("matrix_request","口令卡申请"),
	
	BIND_MOBILE_SMS("bind_mobile_sms","绑定手机确认码"),
	
	UNBIND_MOBILE_SMS("unbind_mobile_sms","解除手机确认码"),
	
	MODIFY_MOBILE_SMS("modify_bind_mobile_sms","修改绑定手机确认码"),
	
	BACKUP_CERT_SMS("backup_cert_sms","备份证书手机确认码"),
	
	
	
	/** 数字证书申请 */
	CERT_APPLY("cert_apply","数字证书申请"),
	
	/** 数字证书注销校验码*/
	CERT_DISABLE("cert_disable","数字证书注销"),
	
	QUICK_PAY__MOBILE("quick_pay_mobile","快捷支付手机校验码"),
	
	/** 数字证书导入校验码*/
	CERT_IMPORT("cert_import","数字证书导入"),
	
	RESET_LOGINPWD("reset_login_pwd","重置登录密码"),
	
	;
	
	
	
	private String value;
	private String message;
	
	CheckCodeOriginEnum(String value,String message){
		this.value = value;
		this.message = message;
	}
	public String getValue() {
		return value;
	}
	public String getMessage() {
		return message;
	}
	public static CheckCodeOriginEnum getCheckCodeOriginEnum(String value){
        if(value != null){
            for (CheckCodeOriginEnum nameEnum : values()) {
                if(StringUtils.equals(nameEnum.getValue(), value)){
                    return nameEnum;
                }
            }
        }
        return null;
    }
	
}
