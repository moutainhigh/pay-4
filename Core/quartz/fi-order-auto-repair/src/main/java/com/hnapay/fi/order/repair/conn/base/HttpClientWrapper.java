/**
 * HttpClientWrapper.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.conn.base;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import com.hnapay.fi.order.repair.conn.IHttpClientWrapper;

/**
 * HttpClient 封装器
 * latest modified time : 2011-9-5 下午4:02:36
 * @author bigknife
 */
public class HttpClientWrapper implements HttpClient,IHttpClientWrapper {
	private HttpClient client;
	public HttpClientWrapper(){
		
	}
	public HttpClientWrapper(HttpClient hc){
		client = hc;
	}
	
	/**
	 * @param client the client to set
	 */
	public void setClient(HttpClient client) {
		this.client = client;
	}
	/* (non-Javadoc)
	 * @see org.apache.http.client.HttpClient#getParams()
	 */
	@Override
	public HttpParams getParams() {
		return client.getParams();
	}

	/* (non-Javadoc)
	 * @see org.apache.http.client.HttpClient#getConnectionManager()
	 */
	@Override
	public ClientConnectionManager getConnectionManager() {
		return client.getConnectionManager();
	}

	/* (non-Javadoc)
	 * @see org.apache.http.client.HttpClient#execute(org.apache.http.client.methods.HttpUriRequest)
	 */
	@Override
	public HttpResponse execute(HttpUriRequest request) throws IOException,
			ClientProtocolException {
		return client.execute(request);
	}

	/* (non-Javadoc)
	 * @see org.apache.http.client.HttpClient#execute(org.apache.http.client.methods.HttpUriRequest, org.apache.http.protocol.HttpContext)
	 */
	@Override
	public HttpResponse execute(HttpUriRequest request, HttpContext context)
			throws IOException, ClientProtocolException {
		return client.execute(request, context);
	}

	/* (non-Javadoc)
	 * @see org.apache.http.client.HttpClient#execute(org.apache.http.HttpHost, org.apache.http.HttpRequest)
	 */
	@Override
	public HttpResponse execute(HttpHost target, HttpRequest request)
			throws IOException, ClientProtocolException {
		return client.execute(target, request);
	}

	/* (non-Javadoc)
	 * @see org.apache.http.client.HttpClient#execute(org.apache.http.HttpHost, org.apache.http.HttpRequest, org.apache.http.protocol.HttpContext)
	 */
	@Override
	public HttpResponse execute(HttpHost target, HttpRequest request,
			HttpContext context) throws IOException, ClientProtocolException {
		return client.execute(target, request, context);
	}

	/* (non-Javadoc)
	 * @see org.apache.http.client.HttpClient#execute(org.apache.http.client.methods.HttpUriRequest, org.apache.http.client.ResponseHandler)
	 */
	@Override
	public <T> T execute(HttpUriRequest request,
			ResponseHandler<? extends T> responseHandler) throws IOException,
			ClientProtocolException {
		return client.execute(request, responseHandler);
	}

	/* (non-Javadoc)
	 * @see org.apache.http.client.HttpClient#execute(org.apache.http.client.methods.HttpUriRequest, org.apache.http.client.ResponseHandler, org.apache.http.protocol.HttpContext)
	 */
	@Override
	public <T> T execute(HttpUriRequest request,
			ResponseHandler<? extends T> responseHandler, HttpContext context)
			throws IOException, ClientProtocolException {
		return client.execute(request, responseHandler,context);
	}

	/* (non-Javadoc)
	 * @see org.apache.http.client.HttpClient#execute(org.apache.http.HttpHost, org.apache.http.HttpRequest, org.apache.http.client.ResponseHandler)
	 */
	@Override
	public <T> T execute(HttpHost target, HttpRequest request,
			ResponseHandler<? extends T> responseHandler) throws IOException,
			ClientProtocolException {
		return client.execute(target,request,responseHandler);
	}

	/* (non-Javadoc)
	 * @see org.apache.http.client.HttpClient#execute(org.apache.http.HttpHost, org.apache.http.HttpRequest, org.apache.http.client.ResponseHandler, org.apache.http.protocol.HttpContext)
	 */
	@Override
	public <T> T execute(HttpHost target, HttpRequest request,
			ResponseHandler<? extends T> responseHandler, HttpContext context)
			throws IOException, ClientProtocolException {
		return client.execute(target,request,responseHandler,context);
	}
	@Override
	public final HttpClient wrap(HttpClient client) {
		
		HttpClientWrapper wrapper = createInstance();
		wrapper.setClient(client);
		if(wrapper.client == null){
			wrapper.client = new DefaultHttpClient();
		}
		wrapper.client = innerWrap(wrapper.client);
		return wrapper;
	}
	
	/**
	 * 创建包装后的对象
	 * @return
	 */
	protected HttpClientWrapper createInstance(){
		HttpClientWrapper wrapper = new HttpClientWrapper();
		return wrapper;
	}
	
	/**
	 * 内部包装，供子类扩展
	 * @param client 一定不为空的HttpClient实例
	 * @return
	 */
	protected HttpClient innerWrap(HttpClient client){
		return client;
	}

}
