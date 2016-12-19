/**
* Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.app.service.external.impl;

import com.pay.app.service.external.MessageSignatureVerify;
import com.pay.inf.service.IMessageDigest;

/**
 * @author fjl
 * @date 2011-6-30
 */
public class MessageSignatureVerifyImpl implements MessageSignatureVerify {
	private final static String GROUP_CODE = "MA";
	
	private final static String MARK = "&";
	
	private IMessageDigest messageDigest = null;
	
	@Override
	public String Signature(String data, String key) throws Exception {
		try {
			//获得摘要
			String tmp = data + MARK +  key;
			String digestStr = messageDigest.genMessageDigest(tmp.getBytes("UTF-8"));
			return digestStr;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("摘要失败：",e);
		}
		
	}

	@Override
	public String SignatureByMmerchant(String data, String originCode)
			throws Exception {
		
//		String key = ConfigReader.get(getKey(originCode), GROUP_CODE);
		
		return "";//Signature(data,key);
	}

	@Override
	public boolean verify(String data, String key, String signdata) {
		
		try {
			String tmp = data + MARK +  key;
			return messageDigest.validateMessageDigest(tmp.getBytes("UTF-8"),signdata);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean verifyByMerchant(String data, String originCode,
			String signdata) {
		
//		String key = ConfigReader.get(getKey(originCode), GROUP_CODE);
		
		return false;//verify(data, key, signdata);
	}

	public void setMessageDigest(IMessageDigest messageDigest) {
		this.messageDigest = messageDigest;
	}
	
	/*
	 * 获得商户的密钥
	 */
	private String getKey(String originCode){
		return  originCode + ".bsp.key";
	}
}
