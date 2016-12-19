/**
* Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.app.service.external.impl;

import java.security.PrivateKey;
import java.security.PublicKey;

import com.pay.app.service.external.SignatureVerify;
import com.pay.inf.service.IMessageDigest;
import com.pay.inf.service.impl.SHAMessageDigestImpl;
import com.pay.util.RSAHelper;
import com.pay.util.RSAUtil;
import com.pay.util.security.ToolsUtil;

public class SignatureVerifyImpl implements SignatureVerify {
	
	private final static String GROUP_CODE = "MA";
	
	private final static String pay_PRIVATE_KEY = "pay.bsp.privatekey";
	
	private final static String pay_PUBLIC_KEY = "pay.bsp.publickey";

	@Override
	public String Signature(String data, String privateKey) throws Exception{
		if(data == null || privateKey == null){
			return null;
		}
		try {
			String pkStr = RSAHelper.get16to2KeyString(privateKey);
			PrivateKey pk =  RSAHelper.getPrivateKey(pkStr);
			RSAUtil rsaUtil = RSAUtil.getInstance();
			
			IMessageDigest digest = new SHAMessageDigestImpl();
			//获得摘要
			String digestStr = digest.genMessageDigest(data.getBytes("UTF-8"));
			//加密摘要
			return RSAHelper.encodeHex(rsaUtil.encode(pk, ToolsUtil.toByteArray(digestStr)));
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("签名失败：",e);
		}
	}

	@Override
	public String Signature(String data) throws Exception{
//		String key = ConfigReader.get(pay_PRIVATE_KEY, GROUP_CODE);
		
		return "";//Signature(data,key);
	}

	@Override
	public boolean verify(String data, String publicKey, String signdata) {
		if(data == null || publicKey == null || signdata == null){
			return false;
		}
		
		try {
			String pkStr = RSAHelper.get16to2KeyString(publicKey);
			PublicKey publickey = RSAHelper.getPublicKey(pkStr);
			RSAUtil rsaUtil = RSAUtil.getInstance();
			//解密，摘要
			String digestStr = RSAHelper.encodeHex(rsaUtil.decode(publickey,  RSAHelper.decodeHex(signdata)));
			
			IMessageDigest digest = new SHAMessageDigestImpl();
			//比较，摘要
			return digest.validateMessageDigest(data.getBytes("UTF-8"),digestStr);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean verifyByMerchant(String data, String originCode,
			String signdata) {
//		String key = ConfigReader.get(getPublicKey(originCode), GROUP_CODE);
		
		return false;//verify(data,key,signdata);
	}
	
	@Override
	public boolean verify(String data, String signdata) {
//		String key = ConfigReader.get(pay_PUBLIC_KEY, GROUP_CODE);
		
		return false;//verify(data,key,signdata);
	}

	/*
	 * 获得商户的公钥
	 */
	private String getPublicKey(String originCode){
		return  originCode + ".bsp.publickey";
	}

}
