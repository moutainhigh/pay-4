/**
 * IHttpConnectWrapper.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.conn;

import org.apache.http.client.HttpClient;

/**
 * HttpClient 包装器
 * latest modified time : 2011-8-23 上午9:23:02
 * @author bigknife
 */
public interface IHttpClientWrapper {
	/**
	 * 对HttpClient进行包装，主要用在ssl单向及双向连接、httpclient其他配置方面
	 * @param client
	 * @return
	 */
	HttpClient wrap(HttpClient client);
}
