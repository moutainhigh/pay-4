/**
 * UrlEncodedFormRequestBuilder.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.conn.base;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import com.hnapay.fi.order.repair.conn.IHttpRequestBuilder;

/**
 * form表单提交的请求builder,仅支持post提交，线程安全
 * latest modified time : 2011-8-23 上午11:58:12
 * @author bigknife
 */
public class UrlEncodedFormRequestBuilder implements IHttpRequestBuilder {
	private String url;
	private String encoding;
	
	//private static ThreadLocal<Map<String, String> >  tlParameters = new ThreadLocal<Map<String,String>>();
	
	/*private Map<String, String> getCurrentParameters(){
		Map<String, String> parameters = tlParameters.get();
		if(parameters == null){
			parameters = new HashMap<String, String>();
			tlParameters.set(parameters);
		}
		return parameters;
	}*/
	
	
	/**
	 * @param encoding the encoding to set
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * @param parameters the parameters to set
	 */
	/*public void setParameters(Map<String, String> parameters) {
		tlParameters.set(parameters);
	}*/

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
	 * @see com.hnapay.fi.order.repair.conn.IHttpRequestBuilder#buildHttpEntity()
	 */
	@Override
	public HttpEntity buildHttpEntity(Map<String, String> data) {
		if(data != null){
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			for(Entry<String, String> entry : data.entrySet()){
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			try {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nvps,encoding);
				entity.setContentEncoding(encoding);
				return entity;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return null;
	}

	/**
	 * 设置form请求参数
	 * @param name
	 * @param value
	 */
	/*public void addParameter(String name, String value){
		getCurrentParameters().put(name, value);
	}*/
	/**
	 * 移除form请求参数
	 * @param name
	 */
	/*public void removeParameter(String name){
		getCurrentParameters().remove(name);
	}*/
}
