/**
 * TwoWaySSLHttpClientWrapperTest.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.conn.base;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

/**
 * latest modified time : 2011-8-23 上午11:53:23
 * @author bigknife
 */
public class TwoWaySSLHttpClientWrapperTest {
	@Test
	public void testConnect() throws Exception{
		TwoWaySSLHttpClientWrapper wrapper = new TwoWaySSLHttpClientWrapper();
		wrapper.setJksPassword("123456");
		wrapper.setJksUrl("d:/my1.jks");
		
		HttpClient hc = wrapper.wrap(null);
		HttpPost post = new HttpPost("https://corporbank.icbc.com.cn/servlet/ICBCINBSEBusinessServlet");
		
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("APIName", "EAPI"));
		parameters.add(new BasicNameValuePair("APIName", "001.001.005.001"));
		parameters.add(new BasicNameValuePair("MerReqData", "<?xml  version=\"1.0\" encoding=\"GBK\" standalone=\"no\" ?><ICBCAPI><in><orderNum>A0011</orderNum><tranDate>20061103</tranDate><ShopCode>2201021519999612836</ShopCode><ShopAccount>2201021519999612836</ShopAccount></in></ICBCAPI>"));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters,"GBK");
		post.setEntity(entity);
		
		HttpResponse resp = hc.execute(post);
		BasicResponseHandler respHandler = new BasicResponseHandler();
		
		String strResp = respHandler.handleResponse(resp);
		
		System.out.println(resp.getStatusLine());
		System.out.println(strResp);
	}
}
