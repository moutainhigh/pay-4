/**
 * AddHeaderHttpClientWrapper.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.conn.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;

/**
 * latest modified time : 2011-9-5 下午3:38:23
 * 
 * @author bigknife
 */
public class AddHeaderHttpClientWrapper extends HttpClientWrapper {
	private Map<String, String> headers;

	/**
	 * @param headers
	 *            the headers to set
	 */
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public void removeHeader(String name) {
		if (headers != null) {
			headers.remove(name);
		}
	}

	public void addHeader(String name, String value) {
		if (headers == null) {
			headers = new HashMap<String, String>();
		}
		headers.put(name, value);
	}
	
	@Override
	protected HttpClientWrapper createInstance() {
		AddHeaderHttpClientWrapper instance = new AddHeaderHttpClientWrapper();
		if(this.headers != null){
			instance.headers = new HashMap<String, String>();
			instance.headers.putAll(this.headers);
		}
		return instance;
	}

	@Override
	public HttpResponse execute(HttpUriRequest request) throws IOException,
			ClientProtocolException {
		addRequestHeader(request);
		return super.execute(request);
	}
	
	@Override
	public <T> T execute(HttpUriRequest request,
			ResponseHandler<? extends T> responseHandler) throws IOException,
			ClientProtocolException {
		addRequestHeader(request);
		return super.execute(request, responseHandler);
	}

	private void addRequestHeader(HttpUriRequest request) {
		if (headers != null) {
			for (Entry<String, String> header : headers.entrySet()) {
				request.addHeader(header.getKey(), header.getValue());
			}
		}
	}

}
