/**
 * ABC.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.abc;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

import com.hnapay.fi.order.repair.conn.base.HttpClientWrapper;
import com.hnapay.fi.order.repair.conn.base.TwoWaySSLHttpClientWrapper;
import com.hnapay.fi.order.repair.engine.IBankOrderLoader;


/**
 * latest modified time : 2011-8-25 下午3:38:05
 * @author bigknife
 */
public class ABCConnTest {
	
	@Test
	public void testWrapper(){
		
		TwoWaySSLHttpClientWrapper wrapper = new TwoWaySSLHttpClientWrapper();
		wrapper.setJksPassword("changeit");
		wrapper.setJksUrl("/opt/hnapay/config/gateway/orderrepair/abc/abc.truststore");
		
		HttpClient hc = new DefaultHttpClient();
		hc = wrapper.wrap(hc);
		
		String url = "https://www.95599.cn/b2c/trustpay/ReceiveMerchantTrxReqServlet";
		HttpPost post = new HttpPost(url);
		
	}
	
	//@Test
	public void testLoad() throws Exception{
		try{
			//IBankOrderLoader loader = (IBankOrderLoader) ac.getBean("loader.abc.b2c");
			//loader.load();
		}catch (Exception e) {
			throw e;
		}
	}
	
}
