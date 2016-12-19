/**
* Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.app.service.external;

/**
 * 外部资源接口
 * @author fjl
 * @date 2011-6-23
 */
public interface ExternalResourcesHelper {
	
	/**
	 * 获得外部主机
	 * @param originCode
	 * @return
	 */
	public String getHost(String originCode);
	
	/**
	 * 获得外部主机服务端口
	 * @param originCode
	 * @return
	 */
	public int getPort(String originCode);
	
	/**
	 * 获得外部主机服务协议
	 * @param originCode
	 * @return
	 */
	public String getProtocol(String originCode);
	
	
	/**
	 * 外部提供的判断是否登录url
	 * @param originCode
	 * @return
	 */
	public String getIsLoginUrl(String originCode);
	
	/**
	 * 外部提供出错页面url
	 * @param originCode
	 * @return
	 */
	public String getErrorPageUrl(String originCode);
	

}
