/**
 *  File: NotActivation.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-23   single_zhang     Create
 *
 */
package com.pay.app.common.activation;

/**
 * 未激活流程处理
 */
public interface NotActivation {
	
	/**
	 * 来源标记
	 * 
	 * @param flag
	 * @return
	 */
       public boolean backPage(String flag);
}

