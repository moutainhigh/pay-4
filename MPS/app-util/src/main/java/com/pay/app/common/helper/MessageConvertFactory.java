/**
 *  File: MessageConvertFactory.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-26   Terry_ma    Create
 *
 */
package com.pay.app.common.helper;

import org.springframework.context.MessageSource;

/**
 * 
 */
public class MessageConvertFactory {
	
	private final static String WebsiteContextPath = "website.context.path";
	
	private final static String GroupCode = "MA";

	private static MessageSource messageSource;

	public static String getMessage(String messageId) {

		if (null == messageSource) {
			return null;
		}
		return messageSource.getMessage(messageId, null, null);
	}
	
	public static String getMessage(String messageId,Object[] args) {

		if (null == messageSource) {
			return null;
		}
		return messageSource.getMessage(messageId, args, null);
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	
	/**
	 * 默认从MA 组中读取配置信息
	 * @param key
	 * @return
	 */
	public  static String getProperties(String key) {
		
		//String value = ConfigReader.get(key, GroupCode);
//		if(value == null ) {
//			value = "";
//		}
		return "" ;
	}
	
	/**
	 * 获取 website 上下文完整路径
	 * @return
	 */
	public static String getWebsiteContextPath(){
		
		String value = getProperties(WebsiteContextPath);
		if(value == null ) {
			value = "";
		}
		return value ;
	}
}
