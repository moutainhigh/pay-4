/**
*Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.app.cidverify;

/**
 * 
 * 公安实名认证基础服务接口
 * @author fjl
 * @date 2011-4-29
 */
public interface INciicClient {
	
	
	/**
	 * 根据身份文件以及条件进行实名认证
	 * 返回原始验证结果
	 * @param condition  查询条件条件
	 * @return
	 * @throws Exception
	 */
	public String executeCidVerify(String condition)throws Exception;

}
