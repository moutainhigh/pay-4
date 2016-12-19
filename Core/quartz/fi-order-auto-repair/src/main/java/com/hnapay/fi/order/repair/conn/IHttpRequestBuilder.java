/**
 * IHttpRequestBuilder.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.conn;

import java.util.Map;

import org.apache.http.HttpEntity;

/**
 * Http请求builder
 * latest modified time : 2011-8-23 上午9:18:35
 * @author bigknife
 */
public interface IHttpRequestBuilder {
	public static final String METHOD_GET = "GET";
	public static final String METHOD_POST = "POST";
	public static final String METHOD_DELETE = "DELETE";
	public static final String METHOD_PUT = "PUT";
	/**
	 * 构建url
	 * @return
	 */
	String buildURL();
	
	/**
	 * 构建请求方法
	 * @return
	 */
	String buildMethod();
	
	/**
	 * 根据数据，构建请求体
	 * @param map 
	 * @return
	 */
	HttpEntity buildHttpEntity(Map<String, String> data);
}
