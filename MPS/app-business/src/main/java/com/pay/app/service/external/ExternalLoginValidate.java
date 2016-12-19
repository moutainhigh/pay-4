/**
* Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.app.service.external;

import java.util.Map;

/**
 * 外部系统登录验证
 * @author fjl
 * @date 2011-6-23
 */
public interface ExternalLoginValidate {
	
	/**
	 * @param loginInfoMap &lt; uid,originCode &gt;
	 * @return
	 */
	public boolean isLogin(Map<String,String> loginInfoMap);
	

}
