/**
* Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.poss.base.common.properties;


/**
 * 
 * 读取系统配置，使用动态参数配置组件
 * @Description 
 * @project 	ma-manager
 * @file 		PossPropertiesUtil.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 */
public class PossPropertiesUtil {
	
	private final static String WebsiteContextPath = "website.context.path";
	
	private final static String GroupCode = "MA";

	/**
	 * 默认从MA 组中读取配置信息
	 * @param key
	 * @return
	 */
	public  static String getProperties(String key) {
		
		String value = null;//ConfigReader.get(key, GroupCode);
		if(value == null ) {
			value = "";
		}
		return value ;
	}
	
	
	/**
	 * 获取 website 上下文完整路径
	 * @return
	 */
	public static String getWebsiteContextPath1(){
		return getProperties(WebsiteContextPath);
	}
}
