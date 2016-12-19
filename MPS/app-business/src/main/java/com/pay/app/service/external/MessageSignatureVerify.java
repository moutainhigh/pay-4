/**
* Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.app.service.external;

/**
 * BSP　消息摘要与验签
 * @author fjl
 * @date 2011-6-30
 */
public interface MessageSignatureVerify {
	
	/**
	 * 使用密钥对明文data进行摘要
	 * @param data　明文
	 * @param key　密钥十六进制的字符串
	 * @return
	 */
	public String Signature(String data,String key) throws Exception;
	
	/**
	 * 使用与约定的商户密钥对明文进行摘要
	 * @param data
	 * @return
	 */
	public String SignatureByMmerchant(String data,String originCode) throws Exception;
	
	/**
	 * 对摘要进行验签
	 * @param data　明文
	 * @param key　密钥十六进制的字符串
	 * @param signdata　摘要后的密文（十六进制的字符串）
	 * @return
	 */
	public boolean verify(String data,String key,String signdata);
	
	/**
	 * 根据商户，对摘要进行验签
	 * @param data　明文
	 * @param originCode
	 * @param signdata　摘要后的密文（十六进制的字符串）
	 * @return
	 */
	public boolean verifyByMerchant(String data,String originCode,String signdata);


}
