package com.pay.app.common.helper;

import org.springframework.context.MessageSource;


/**
 * @author zengjin
 * @date 2010-8-3
 * @param 
 */
public class AppConf {

	private static MessageSource messageSource;
	
	/**
	 * 不需要进行session校验的url
	 */
	public static final String uncheckUrl="sso.uncheckurl";
	public static final String shopUrl="sso.shop";
	public static final String shopPayUrl="sso.shoppay";
	public static final String mail_interval="sendMail.interval";
	public static final String mail_paypwd_interval="sendMail.paypwd.interval";
	public static final String sms_interval="sendSms.interval";
	public static final  String  cookieJumpUrl="cookie_jump_uri";
	public static final  String  sessionJumpMap="jumpMap";
	public static final  String  sessionCorpJumpMap="jumpCorpMap";
	public static final  String  jumpChildParam="childParam";
	public static final  String  defaultCallBack="/app/myAccount.htm";
	public static final  String  defaultPlatformCallBack="/corp/myPlatAccout.do";
	public static final  String  defaultCorpCallBack="/corp/myAccount.htm";
	public static final  String  actionUrl="actionUrl";
	public static final  String  actionCorpUrl="actionCorpUrl";
	public static final  String  orginUrl="orgin";
	public static final  String  payChainKey="paychain.key";
	public static final  String  payChainUrl="paychain.url";
	/*支付链配置*/
	public static final  String payLinkUrl="paylink.url" ;
	public static final  String payLinkBasePath="paylink.base.path" ;
	//public static final  String payLinkShopTermPath="paylink.shopterm.path" ;
	/**MA文件上传根目录 */
	public static final  String  maUploadFilePath="ma.file.root.path";
	public static final  String  uploadFileRootPath="web.file.root.path";
	public static final  String  uploadFileMaxSize="web.file.max.size";
	public static final  String  uploadContextPath="web.file.context.path";
	
	//同一张卡最高使用数次
	public static final String  matrixCardUserLimit="matrix.card.user.limit";
	//同一ip一天100张
	public static final String  reqIpLimit="req.ip.limit";

	/** 登录失败次数限制 */
	public static final String limitNum = "user.loginpwd.error.limitNum";
	/** 登录失败锁定会员时间限制  */
	public static final String limitDate = "user.loginpwd.error.limitDay";
	
	public static final  String  payiframe="payiframe";
	
	public static final  String  successCode="0000";
	
	public static final String activeMobile="mo";
	
	public static final int ACTIVE_SUCCESS=1;
	
	public static final String ssoScoreRemark="支付会员关联社区账号";
	
	public static String get(String key) {

		if (null == messageSource) {
			return null;
		}
		return messageSource.getMessage(key, null, null);
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
