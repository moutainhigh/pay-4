/**
 * GetQueryStringRequestBuilder.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.conn.base;

import java.util.Map;

import org.apache.http.HttpEntity;

import com.hnapay.fi.order.repair.conn.IHttpRequestBuilder;

/**
 * Get请求构建 latest modified time : 2011-8-31 下午3:59:36
 * 
 * @author bigknife
 */
public class GetQueryStringRequestBuilder implements IHttpRequestBuilder {
	private String url;
	private ThreadLocal<Map<String, String>> tlData = new ThreadLocal<Map<String, String>>();

	/**
	 * 将请求数据序列化为Get请求的queryString， 根据银行接口要求实现（仅针对Get 接口）
	 * latest modified time : 2011-9-5 上午10:12:32
	 * @author bigknife
	 */
	public interface Map2StringTransform {
		/**
		 * 将请求数据序列化为Get请求的queryString， 根据银行接口要求实现（仅针对Get 接口）
		 * @param data
		 * @return
		 */
		String map2String(Map<String, String> data);
	}

	private Map2StringTransform map2StringTransform;

	/**
	 * @param map2StringTransform
	 *            the map2StringTransform to set
	 */
	public void setMap2StringTransform(Map2StringTransform map2StringTransform) {
		this.map2StringTransform = map2StringTransform;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hnapay.fi.order.repair.conn.IHttpRequestBuilder#buildURL()
	 */
	@Override
	public String buildURL() {
		String queryString = map2StringTransform.map2String(tlData.get());
		tlData.remove();
		return url + "?" + queryString;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hnapay.fi.order.repair.conn.IHttpRequestBuilder#buildMethod()
	 */
	@Override
	public String buildMethod() {
		return IHttpRequestBuilder.METHOD_GET;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hnapay.fi.order.repair.conn.IHttpRequestBuilder#buildHttpEntity(java
	 * .util.Map)
	 */
	@Override
	public HttpEntity buildHttpEntity(Map<String, String> data) {
		tlData.set(data);
		return null;
	}

}
