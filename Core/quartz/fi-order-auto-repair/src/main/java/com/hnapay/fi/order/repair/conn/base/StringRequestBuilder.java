/**
 * StringRequestBuilder.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.conn.base;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import com.hnapay.fi.order.repair.conn.IHttpRequestBuilder;

/**
 * latest modified time : 2011-8-26 上午10:21:08
 * @author bigknife
 */
public class StringRequestBuilder implements IHttpRequestBuilder {
	private String url;
	private String encoding;
	
	public interface Map2StringTransform{
		String map2String(Map<String, String> data);
	}
	
	private Map2StringTransform map2StringTransform;
	
	/**
	 * @param encoding the encoding to set
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * @param map2StringTransform the map2StringTransform to set
	 */
	public void setMap2StringTransform(Map2StringTransform map2StringTransform) {
		this.map2StringTransform = map2StringTransform;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/* (non-Javadoc)
	 * @see com.hnapay.fi.order.repair.conn.IHttpRequestBuilder#buildURL()
	 */
	@Override
	public String buildURL() {
		return url;
	}

	/* (non-Javadoc)
	 * @see com.hnapay.fi.order.repair.conn.IHttpRequestBuilder#buildMethod()
	 */
	@Override
	public String buildMethod() {
		return IHttpRequestBuilder.METHOD_POST;
	}
	
	

	/* (non-Javadoc)
	 * @see com.hnapay.fi.order.repair.conn.IHttpRequestBuilder#buildHttpEntity(java.util.Map)
	 */
	@Override
	public HttpEntity buildHttpEntity(Map<String, String> data) {
		if(data != null && !data.isEmpty()){
			try {
				StringEntity entity = new StringEntity(map2StringTransform.map2String(data),encoding);
				entity.setContentEncoding(encoding);
				return entity;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return null;
	}
}
