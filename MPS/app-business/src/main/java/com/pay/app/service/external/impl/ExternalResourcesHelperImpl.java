/**
* Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.app.service.external.impl;

import com.pay.app.service.external.ExternalResourcesHelper;
import com.pay.util.StringUtil;

public class ExternalResourcesHelperImpl implements ExternalResourcesHelper {
	private final static String GROUP_CODE = "MA";
	
	private final static String PRE_KEY = "ext.res";

	@Override
	public String getHost(String originCode) {
		String path = getIsLoginUrl(originCode);
		if(StringUtil.isEmpty(path)){
			return "";
		}
		int i = path.indexOf("://");
		if(i != -1){
			String path1 = path.substring(i + 3);
			int j = path1.indexOf("/");
			String path2 = path1.substring(0, j);
			return path2.split(":")[0];
		}else{
			int j =  path.indexOf("/");
			String path2 = path.substring(0, j);
			return path2.split(":")[0];
		}
	}

	@Override
	public int getPort(String originCode) {
		String path = getIsLoginUrl(originCode);
		if(StringUtil.isEmpty(path)){
			return 80;
		}
		
		int i = path.indexOf("://");
		String path2= "";
		if(i != -1){
			String path1 = path.substring(i + 3);
			int j = path1.indexOf("/");
			path2 = path1.substring(0, j);
			
		}else{
			int j =  path.indexOf("/");
			path2 = path.substring(0, j);
		}
		if (path2.indexOf(":") != -1) {
			return Integer.valueOf(path2.split(":")[1]);
		}
		return 80;
	}

	@Override
	public String getProtocol(String originCode) {
		String path = getIsLoginUrl(originCode);
		if(StringUtil.isEmpty(path)){
			return "http";
		}
		int i = path.indexOf("://");
		if(i != -1){
			String path1 = path.substring(0,i);
			return path1;
		}else{
			return "http";
		}
	}

	@Override
	public String getIsLoginUrl(String originCode) {
		
		//String value = ConfigReader.get(getKey(originCode,"isLoginUrl"), GROUP_CODE);
//		if(value == null){
//			return "";
//		}
		return "";
	}

	@Override
	public String getErrorPageUrl(String originCode) {
//		String value = ConfigReader.get(getKey(originCode,"errorPageUrl"), GROUP_CODE);
//		if(value == null){
//			return "";
//		}
		return "";
	}
	
	/**
	 * ext.res.originCode.key
	 * @param originCode
	 * @param key
	 * @return
	 */
	private String getKey(String originCode,String key){
		return PRE_KEY + "." +  originCode + "." + key;
	}

}
