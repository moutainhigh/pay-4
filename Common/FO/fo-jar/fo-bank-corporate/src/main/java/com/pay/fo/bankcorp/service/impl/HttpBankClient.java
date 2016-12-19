/**
 *  File: HttpBankClient.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2014-2-17   liwei     Create
 *
 */
package com.pay.fo.bankcorp.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**		
 *  @author lIWEI
 *  @Date 2014-2-17
 *  @Description
 */
public class HttpBankClient {
private Log logger = LogFactory.getLog(getClass());
	
	/**
	 * 银行前置post请求
	 * @param reqParams
	 * @return
	 */
	public String httpPost(String url,Map<String,String> reqParams) {
		if (StringUtils.isBlank(url)) {
			logger.error("银企直连-银行前置http请求URL为空!");
			return null;
		}
		logger.info("银企直连银行前置http请求URL地址: " + url);
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httPost = new HttpPost(url);
		List<NameValuePair> params = getNameValuePair(reqParams);
		UrlEncodedFormEntity entity = null;
		try {
			entity = new UrlEncodedFormEntity(params,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("银企直连-银行前置http请求编码错误!",e);
		}
		httPost.setEntity(entity);
		try {
			HttpResponse response = httpclient.execute(httPost);
			if (response != null) {
				HttpEntity httpEntity = response.getEntity();
				if (entity != null) {
					return EntityUtils.toString(httpEntity);
				}
			}
		} catch (ClientProtocolException e) {
			logger.error("银企直连-银行前置http请求协议错误!",e);
		} catch (IOException e) {
			logger.error("银企直连-银行前置http请求IO/连接错误!",e);
		} finally {
			try {
				entity.consumeContent();
			} catch (UnsupportedOperationException e) {
				logger.error("银企直连-释放资源异常!",e);
			} catch (IOException e) {
				logger.error("银企直连-释放资源异常!",e);
			}
			httpclient.getConnectionManager().shutdown();
		}
		return null;
	}
	
	/**
	 * httpClient填充请求参数
	 * @param reqParams
	 */
	private List<NameValuePair> getNameValuePair(Map<String,String> reqParams) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if (reqParams == null) {
			return params;
		}
		for(Map.Entry<String, String> entry:reqParams.entrySet()){   
		     params.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
		}
		return params;
	}
}
