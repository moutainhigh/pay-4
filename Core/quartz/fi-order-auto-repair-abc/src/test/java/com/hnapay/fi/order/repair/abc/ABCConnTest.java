/**
 * ABC.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.abc;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hnapay.fi.order.repair.conn.IHttpClientWrapper;
import com.hnapay.fi.order.repair.engine.IBankOrderLoader;


/**
 * latest modified time : 2011-8-25 下午3:38:05
 * @author bigknife
 */
public class ABCConnTest {
	private static ApplicationContext ac = new ClassPathXmlApplicationContext("classpath*:context/**/*-test-bean.xml");
	//@Test
	public void testWrapper() throws Exception{
		IHttpClientWrapper wrapper = (IHttpClientWrapper) ac.getBean("conn.wrapper.abc");
		HttpClient hc = new DefaultHttpClient();
		hc = wrapper.wrap(hc);
		
		String url = "https://www.95599.cn/b2c/trustpay/ReceiveMerchantTrxReqServlet";
		HttpPost post = new HttpPost(url);
		try{
		hc.execute(post);
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Test
	public void testLoad() throws Exception{
		try{
			IBankOrderLoader loader = (IBankOrderLoader) ac.getBean("loader.abc.b2c");
			loader.load();
		}catch (Exception e) {
			throw e;
		}
	}
	
}
