/**
 * 
 */
package com.pay.wechat.service;


import javax.servlet.http.HttpServletRequest;



/**
 * 核心业务处理类
 * @author PengJiangbo
 *
 */
public interface CoreService {

	/**
	 * 处理微信发来的请求
	 * @param reqeust
	 */
	public String processRequest(HttpServletRequest request) ;

}
