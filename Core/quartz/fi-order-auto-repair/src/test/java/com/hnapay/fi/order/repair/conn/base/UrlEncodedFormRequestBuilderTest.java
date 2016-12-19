/**
 * UrlEncodedFormRequestBuilderTest.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.conn.base;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.junit.Test;

/**
 * latest modified time : 2011-8-23 下午12:07:36
 * @author bigknife
 */
public class UrlEncodedFormRequestBuilderTest {
	@Test
	public void testConnect() throws Exception{
		TwoWaySSLHttpClientWrapper wrapper = new TwoWaySSLHttpClientWrapper();
		wrapper.setJksPassword("123456");
		wrapper.setJksUrl("d:/my1.jks");
		
		HttpClient hc = wrapper.wrap(null);
		
		
		UrlEncodedFormRequestBuilder builder = new UrlEncodedFormRequestBuilder();
		builder.setUrl("https://corporbank.icbc.com.cn/servlet/ICBCINBSEBusinessServlet");
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("APIName", "EAPI");
		parameter.put("APIName", "001.001.002.001");
		parameter.put("MerReqData", "<?xml  version=\"1.0\" encoding=\"GBK\" standalone=\"no\" ?><ICBCAPI><in><orderNum>A0011</orderNum><tranDate>20061103</tranDate><ShopCode>2201021519999612836</ShopCode><ShopAccount>2201021519999612836</ShopAccount></in></ICBCAPI>");
		builder.setEncoding("GBK");
		
		HttpPost post = new HttpPost(builder.buildURL());
		post.setEntity(builder.buildHttpEntity(parameter));
		
		HttpResponse resp = hc.execute(post);
		BasicResponseHandler respHandler = new BasicResponseHandler();
		
		String strResp = respHandler.handleResponse(resp);
		
		System.out.println(resp.getStatusLine());
		System.out.println(strResp);
	}
}
