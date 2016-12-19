/**
* Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.app.service.external;

/**
 * BSP 加签解签接口
 * @author fjl
 * @date 2011-6-29
 */
public interface SignatureVerify {
	
	/**
	 * 使用私钥对明文data进行签名
	 * @param data　明文
	 * @param privateKey　私钥十六进制的字符串
	 * @return
	 */
	public String Signature(String data,String privateKey) throws Exception;
	
	/**
	 * 使用系统默认私钥对明文进行签名
	 * @param data
	 * @return
	 */
	public String Signature(String data) throws Exception;
	
	/**
	 * 使用公钥对密文进行验签
	 * @param data　明文
	 * @param publicKey　公钥十六进制的字符串
	 * @param signdata　签名后的密文（十六进制的字符串）
	 * @return
	 */
	public boolean verify(String data,String publicKey,String signdata);
	
	/**
	 * 根据商户，使用商户公钥对密文进行验签
	 * @param data　明文
	 * @param originCode
	 * @param signdata　签名后的密文（十六进制的字符串）
	 * @return
	 */
	public boolean verifyByMerchant(String data,String originCode,String signdata);
	

	/**
	 * 使用系统默认公钥对密文进行验签
	 * @param data　明文
	 * @param signdata　签名后的密文（十六进制的字符串）
	 * @return
	 */
	public boolean verify(String data,String signdata);
}
