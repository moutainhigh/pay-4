/**
* Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.app.service.external.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;

import com.pay.app.common.enums.BspCommEnum;
import com.pay.app.service.external.ExternalLoginValidate;
import com.pay.app.service.external.ExternalResourcesHelper;
import com.pay.app.service.external.MessageSignatureVerify;

/**
 * 外部系统是否登录验证
 * @author fjl
 * @date 2011-6-23
 */
public class ExternalLoginValidateImpl implements ExternalLoginValidate {
	
	private ExternalResourcesHelper externalResourcesHelper;
	
	private final static String MARK = "&";
	
	/** 加签，验签 */
//	private SignatureVerify signatureVerify;
	
	private MessageSignatureVerify messageSignatureVerify;
	
	private final Log log = LogFactory.getLog(ExternalLoginValidateImpl.class);
	
	@Override
	public boolean isLogin(Map<String, String> loginInfoMap) {
		String originCode = loginInfoMap.get("originCode");
		HttpClient hc = new HttpClient();
		hc.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		hc.getHostConfiguration().setHost(
				externalResourcesHelper.getHost(originCode),
				externalResourcesHelper.getPort(originCode),
				externalResourcesHelper.getProtocol(originCode));
		String uid = loginInfoMap.get("uid");
		String userName = loginInfoMap.get("userName");
		String time = loginInfoMap.get("time");
		String signMsg = getSignature(uid,userName,time,originCode);
		
		Map<String,String> param = new HashMap<String,String>();
		param.put("uid", uid);
		param.put("userName", userName);
		param.put("time", time);
		param.put("signMsg", signMsg);
		
		NameValuePair[]data = new NameValuePair[param.size()];
		//将表单的值放入postMethod中
		int i = 0;
		for(String key:param.keySet()){
			data[i++] = new NameValuePair(key,param.get(key));
		}
		/*
		PostMethod pm = new PostMethod(externalResourcesHelper.getIsLoginUrl(originCode));
		pm.setRequestBody(data);
		*/
		GetMethod gm = new GetMethod(externalResourcesHelper.getIsLoginUrl(originCode));
		gm.setQueryString(data);
		
		try {
			int status = hc.executeMethod(gm);
			if(status == HttpStatus.SC_OK){
				String ret = gm.getResponseBodyAsString();
				if(log.isInfoEnabled()){
					log.info("验证登录返回信息：" + ret);
				}
				ObjectMapper mapper = new ObjectMapper();
				Map<String,Object> retMap = mapper.readValue(ret, Map.class);
				
				String _uid = (String)retMap.get("uid");
				String _time = (String)retMap.get("time");
				String _loginCode = (String)retMap.get("loginCode");
				String _errorMsg = (String)retMap.get("errorMsg");
				String _signMsg = (String)retMap.get("signMsg");
				if(doVerify(_uid,_time,_loginCode,_errorMsg,_signMsg,originCode)){
					if(String.valueOf(BspCommEnum.SUCCESS.getCode()).equals(_loginCode)){
						return true;
					}
				}
			}
			return false;
		} catch (Exception e) {
			log.error("判断商户外部系统是否登录出错", e);
			e.printStackTrace();
		}
		return false;
	}


	/**
	 * 登录信息加签
	 * @param uid
	 * @param userName
	 * @param time
	 * @return
	 */
	private String getSignature(String uid,String userName,String time,String originCode){
		String data = uid + MARK + userName + MARK + time;
		
		try {
			return messageSignatureVerify.SignatureByMmerchant(data,originCode);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("加签失败", e);
			return "";
		}
	}
	
	/**
	 * 验证登录返回信息
	 * @param uid
	 * @param time
	 * @param loginCode
	 * @param errorMsg
	 * @param signMsg
	 * @param originCode
	 * @return
	 */
	private boolean doVerify(String uid,String time,String loginCode,String errorMsg,String signMsg,String originCode){
		if(null == uid || null == time || null ==loginCode || null == signMsg){
			return false;
		}
		errorMsg = errorMsg == null ? "" : errorMsg;
		
		String sign = uid + MARK + time + MARK + loginCode + MARK + errorMsg;
		
		return messageSignatureVerify.verifyByMerchant(sign,originCode,signMsg);
	}
	
	/**
	 * @param externalResourcesHelper the externalResourcesHelper to set
	 */
	public void setExternalResourcesHelper(
			ExternalResourcesHelper externalResourcesHelper) {
		this.externalResourcesHelper = externalResourcesHelper;
	}
	
	
	/**
	 * @param messageSignatureVerify
	 */
	public void setMessageSignatureVerify(
			MessageSignatureVerify messageSignatureVerify) {
		this.messageSignatureVerify = messageSignatureVerify;
	}

}
