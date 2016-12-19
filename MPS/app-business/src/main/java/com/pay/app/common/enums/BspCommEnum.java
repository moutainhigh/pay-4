/**
* Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.app.common.enums;

/**
 * @author fjl
 * @date 2011-6-29
 */
public enum BspCommEnum {
	SUCCESS(1111,"成功"),
	SignatureVerifyFailure(2000,"签名认证不成功"),
	IllegalMerchant(2001,"找不到对应交易商帐号"),
	MerchantNoExists(2002,"交易商帐号与uid不匹配"),
	NoLoginOrigin(2003,"交易商未登录"),
	ClientIpNoIdentical(2004,"客户端IP不一致"),
	AccountException(2005,"帐户异常"),
	
	ServiceUrlNoExists(2006,"目标地址不存在"),
	AccessException(2007,"无法访问支付系统"),
	
	
	//注册信息错误提示
	CORP_NAME_LENGTH_ERROR(3000,"公司名称最少4个汉字，最多不能超过32个汉字"),
	LEGAL_NAME_LENGTH_ERROR(3002,"法人代表姓名最少2个字符，最多不能超过16个字符"),
	CORP_LINKER_NAME_ERROR(3004,"联系人名称最少2个字符，最多不能超过32个字符"),
	BIZ_LICENCE_CODE_ERROR(3006,"营业执照号码必须是13至15位数字"),
	TAX_CODE_ERROR(3008,"企业税务等级证件号码必须是13至15位数字"),
	BIZ_LICENCE_CODE_EXISTED(3010,"营业执照号码已存在"),
	GOV_CODE_EXISTED(3012,"商户机构代码证已存在"),
	TAX_CODE_EXISTED(3014,"企业税务等级证件号码已存在"),
	EMAIL_EXISTED(3016,"邮箱地址已经存在"),
	CORP_NAME_EXISTED(3018,"公司名称已经存在"),
	OPEN_ACCOUNT_DATE_ERROR(3020,"开户日期为空或格式不正确"),
	CONTRACT_VALIDITY_ERROR(3022,"合同有效期为空或格式不正确"),
	BSP_ID_ERROR(3024,"交易中心账号不能为空"),
	CORP_LINK_TEL_ERROR(3026,"公司联系电话为空"),
	BANK_NAME_ERROR(3028,"开户银行为空"),
	OA_BANK_NAME_ERROR(3030,"开户银行分行为空"),
	BANK_ACCT_ERROR(3032,"银行账户必须在8到32位之间"),
	ORIGIN_CODE_NOT_EXISTS(3034,"提交的交易中心商户号不存在或已失效"),
	BANK_NAME_NOT_SUPPORT(3038,"提交的开户银行暂时不支持");
	
	
	private int code;
	private String message;

	private BspCommEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	public static BspCommEnum getByCode(int code){
		for(BspCommEnum mse:values()){
			if(mse.getCode()==code){
				return mse;
			}
		}
		return null;
	}
	
}
