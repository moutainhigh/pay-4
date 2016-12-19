/**
* Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.app.common.enums;

/**
 * @author fjl
 * @date 2011-6-24
 */
public enum ExternalRedirectExceptionEnum {
	SignatureFailure(100,"验签失败"),
	ClientIpNoIdentical(101,"客户端IP不一致"),
	ServiceUrlNoExists(102,"目标地址不存在"),
	MerchantNoExists(103,"商户不存在"),
	IllegalMerchant(104,"非法商户,商户不是交易中心的交易商"),
	AccountException(105,"帐户异常"),
	NoLoginOrigin(106,"认证登录失败"),
	AccessException(107,"无法访问支付系统");
	
	
	private int code;
	private String message;

	private ExternalRedirectExceptionEnum(int code, String message) {
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

	public static ExternalRedirectExceptionEnum getByCode(int code){
		for(ExternalRedirectExceptionEnum mse:values()){
			if(mse.getCode()==code){
				return mse;
			}
		}
		return null;
	}
	
}
