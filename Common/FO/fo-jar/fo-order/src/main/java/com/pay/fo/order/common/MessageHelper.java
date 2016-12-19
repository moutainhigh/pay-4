/**
 * 
 */
package com.pay.fo.order.common;

import org.apache.commons.lang.StringUtils;

/**
 * @author NEW
 *
 */
public class MessageHelper {
	
	public static String buildMessage(String msgCode,String ... paramValue){
		StringBuffer sbf = new StringBuffer(msgCode);
		
		for (int i = 0; i < paramValue.length; i++) {
			sbf.append("-");
			sbf.append(paramValue[i]);
		}
		
		return sbf.toString();
		
	}
	
	public static String getMessageCode(String srcMsgCode){
		if(StringUtils.isNotEmpty(srcMsgCode)){
			String[] tmp = srcMsgCode.split("-");
			return tmp[0];
		}
		return null;
		
	}
	
	public static String getMessageResult(String msgCode,String message){
		String[] tmp = msgCode.split("-");
		String result = message;
		for (int i = 1; i < tmp.length; i++) {
			result = result.replaceAll("\\{"+(i-1)+"\\}", tmp[i]);
		}
		
		return result;
	}

	
}
