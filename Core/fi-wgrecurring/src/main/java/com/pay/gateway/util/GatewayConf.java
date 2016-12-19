package com.pay.gateway.util;

import org.springframework.context.MessageSource;


/**
 * 
 * @author PengJiangbo
 *
 */
public class GatewayConf {
	
	//注入MessageSource
	private static MessageSource messageSource;
	
	public static final  String payLinkUrl="paylink.url" ;
	
	public static String get(String key) {

		if (null == messageSource) {
			return null;
		}
		return messageSource.getMessage(key, null, null);
	}

	public static void setMessageSource(MessageSource messageSource) {
		GatewayConf.messageSource = messageSource;
	}

}
