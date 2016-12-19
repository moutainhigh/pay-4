package com.pay.acc.comm;


/**
 * @author lei.jiangl
 * @version
 * @data 2010-7-29 下午06:05:31 APP邮件
 */
public class Resource {
	// 主题
	/**
	 * 激活主题
	 */
	public static final String MAIL_SUBJECT_ACTIVATION = "支付激活邮件";
	/**
	 * 修改密码主题
	 */
	public static final String MAIL_SUBJECT_PWD = "支付找回支付密码邮件";

	/**
     * 企业会员激活主题
     */
    public static final String MAIL_MERCHANT_SUBJECT_ACTIVATION = "支付激活邮件";
    
    /**
     * 口令卡解绑主题
     */
    public static final String MAIL_MATRIXCARD_UNBIND="支付口令卡解绑邮件";
    
    /**
     * 口令卡重置主题
     */
    public static final String MAIL_MATRIXCARD_RESET="支付口令卡重置邮件";
    
    /**
	 * 找回登录密码主题
	 */
	public static final String MAIL_LOGIN_PWD_FIND = "支付找回登录密码邮件";
	
	/**
	 * 支付链付款成功主题
	 */
	public static final String MAIL_PAYCHAIN_SUBJECT = "支付支付链付款成功邮件";
    
	/**
	 *邮件推送地址：找回密码
	 */
	//public static final String MAIL_URL_PWD = MessageConvertFactory.getWebsiteContextPath()+"app/tofoundpaypasswordbyemailpage.htm?code=";
	/**
	 *企业会员邮件推送地址：找回密码
	 */
	//public static final String MAIL_URL_PWD_CORP = MessageConvertFactory.getWebsiteContextPath()+"corp/tofoundpaypasswordbyemailpage.htm?code=";
	/**
	 * 邮件推送地址: 口令卡重置
	 */
	public static final String MAIL_URL_MATRIXCARDRESET=MessageConvertFactory.getWebsiteContextPath()+"app/matrixCardReset.htm?method=CheckEmail&checkCode=";
	/**
	 * 邮件推送地址: 口令卡解绑
	 */
	public static final String MAIL_URL_MATRIXCARDUNBIND=MessageConvertFactory.getWebsiteContextPath()+"app/matrixCardUnBind.htm?method=CheckEmail&checkCode=";
	
	/**
     * 邮件推送地址:找回登录密码
     */
    //public static final String MAIL_URL_FIND_LOGIN_PWD = MessageConvertFactory.getWebsiteContextPath()+"passwordfind/emailValidate.htm?source=resetP&code=";
	
	// 模板ID
	/**
	 * 激活模板ID
	 */
	public static final long TEMPLATEID_ACTIVATION = 4;
	/**
	 * 通过邮箱找回支付密码模板ID
	 */
	public static final long TEMPLATEID_PAYPWD = 7;
	/**
	 * 手机短信模板ID
	 */
	public static final long TEMPLATEID_SMS = 2;
	
	/**
	 * 临时会员手机短信模板ID
	 */
	public static final long TEMPLATEID_FOR_TEMPORARY = 2002;
	
	/**
	 * 临时会员邮箱模板ID
	 */
	public static final long TEMPLATEID_ACTIVATION_FOR_TEMPORARY = 2006;
	
	/**
	 * 找回支付密码手机短信模板ID
	 */
	public static final long TEMPLATEID_SMS_PAYPWD = 9;
	
	/**
	 * 通过邮箱重置口令卡的模版ID
	 */
	public static final long MATRIXCARDRESET_TEMPLATEID = 11;
	
	/**
	 * 口令卡重置的手机短信模版ID
	 */
	public static final long SMSRESET_TEMPLATEID = 16;
	
	/**
	 * 通过邮箱解绑口令卡的模版ID
	 */
	public static final long EMAILUNBIND_TEMPLATEID=14;
	/**
	 * 通过手机短信解绑口令卡的模版ID
	 */
    public static final long SMSUNBIND_TEMPLATEID=15;
    
    /**
	 *找回登录密码手机短信模板ID
	 */
    public static final long TEMPLATEID_SMS_LOGINPWD=38;
	
    /**
	 *找回登录密码邮件模板ID
	 */
    public static final long TEMPLATEID_EMAIL_LOGINPWD=39;
    
    /**
     * 支付链支付成功邮件模板ID
     */
    public static final long TEMPLATEID_EMAIL_PAYCHAIN= 2021;
    
   
    
    /**
     * 手机绑定申请验证码短信模板
     */
    public static final long APP_BIND_MOBILE_SMS = 2022;
    
    /**
     * 手机绑定申请成功邮件模板ID
     */
    public static final long BIND_SUCCESS_EMAIL_ID = 2023;
    
    /**
     * 更改手机绑定号码码短信模板
     */
    public static final long MODIFY_BIND_MOBILE_SMS = 2024;
    
    /**
     * 手机取消绑定短信模板
     */
    public static final long UNBIND_MOBILE_SMS = 2025;
    
	/**
	 * 申请证书手机短信模板ID
	 */
	public static final long APPLY_CERT_SMS = 2026;
	
	/**
	 * 申请证书成功邮件通知
	 */
	public static final long APPLY_CERT_SUCESS_EMAIL = 2027;
	
	/**
	 * 注销证书，手机校验码短信模板ID
	 */
	public static final long DISABLE_CERT_SMS = 2029;
	
	 /**
     * 数字证书备份短信模板
     */
    public static final long BACKUP_CERT_SMS = 2030;
   
    
	/**
	 * 导入证书，手机校验码短信模板ID
	 */
	public static final long IMPORT_CERT_SMS = 2031;
	
	
	
	// 发送人
	/**
	 * 默认邮件发送人
	 */
	public static final String EMAILFROMADDRESS = "pay@pay.com";
	
	/**
	 * 安全控件标志
	 */
	public static final String ORGIN_FROM_SECURITY = "security";
}
