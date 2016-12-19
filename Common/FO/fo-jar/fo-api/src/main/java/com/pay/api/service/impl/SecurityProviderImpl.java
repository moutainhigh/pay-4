/**
 *  File: SecurityProviderImpl.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-11-18   Sany        Create
 *
 */
package com.pay.api.service.impl;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;

import com.pay.api.service.ISecurityProvider;
import com.pay.util.Base64Util;
import com.pay.util.security.ISignature;
import com.pay.util.security.impl.RSASignatureImpl;

/**
 * 
 */
public class SecurityProviderImpl implements ISecurityProvider {
	
	private Log logger = LogFactory.getLog(SecurityProviderImpl.class);
	private Resource keyFile;
	private String providePwd;
	private String alias;
	private String pwd;
	
	@Override
	public String generateSignature(String srcData){
		PrivateKey prikey = getPrivateKey();
		ISignature sign = new RSASignatureImpl();
		
		try{
			return sign.genSignature(srcData.getBytes(), prikey);
		}catch(Exception e){
			logger.error("generate signdata error:", e);
		}
		return "";
	}
	
	@Override
	public boolean verifySignature(String srcData, String pubKey, String signMsg) {
		
		ISignature sign = new RSASignatureImpl();
		try {
			byte[] publicKey = Base64Util.decode(pubKey.getBytes());
			if (logger.isInfoEnabled()) {
				logger.info("verifySignature src data:" + srcData);
				logger.info("verifySignature data:" + signMsg);
			}
			return sign.verifySignature(srcData.getBytes(), signMsg, publicKey);
		} catch (Exception e) {

			logger.error("verifySignature error:", e);
		}
		return false;
	}

	public void setKeyFile(Resource keyFile) {
		this.keyFile = keyFile;
	}

	public void setProvidePwd(String providePwd) {
		this.providePwd = providePwd;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	private PrivateKey getPrivateKey() {
		PrivateKey prikey = null;
		try {
			
			InputStream in = keyFile.getInputStream();

			KeyStore ks = KeyStore.getInstance("JKS");
			ks.load(in, providePwd.toCharArray());
			prikey = (PrivateKey) ks.getKey(alias, pwd.toCharArray());
			in.close();
		} catch (Exception e) {
			logger.error("get ebpp key has happen some error, the error is : ",
					e);
		}
		return prikey;
	}
}
