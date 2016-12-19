/**
 * 
 */
package com.pay.fi.chain.util;

import org.springframework.context.MessageSource;

/**
 * 支付链配置信息
 * @author PengJiangbo
 *
 */
public class PayLinkConf {
	
	//注入messageSource
	private static MessageSource messageSource ;

	public static final  String payLinkShopTermPath="paylink.shopterm.path" ;
	
	public static String get(String key){
		if(null == messageSource){
			return null ;
		}
		return messageSource.getMessage(key, null, null) ;
	}
	
	public static void setMessageSource(MessageSource messageSource) {
		PayLinkConf.messageSource = messageSource;
	}
	
}
