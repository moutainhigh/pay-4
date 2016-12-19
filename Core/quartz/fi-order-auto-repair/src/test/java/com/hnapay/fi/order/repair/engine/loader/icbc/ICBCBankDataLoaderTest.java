/**
 * ICBCBankDataLoaderTest.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.engine.loader.icbc;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 * 
 * latest modified time : 2011-8-22 下午12:58:16
 * 
 * @author bigknife
 */
public class ICBCBankDataLoaderTest {
	public static void main(String[] args) throws Exception {
		KeyStore jks = KeyStore.getInstance(KeyStore.getDefaultType());
		String jksUrl = "d:/my1.jks";
		InputStream fin = new FileInputStream(jksUrl);
		try {
			jks.load(fin, "123456".toCharArray());
			SSLSocketFactory socketFactory = new SSLSocketFactory(jks,
					"123456", jks);
			Scheme scheme = new Scheme("https", socketFactory, 443);
			HttpClient hc = new DefaultHttpClient();
			hc.getConnectionManager().getSchemeRegistry().register(scheme);
			
			String url = "https://corporbank.icbc.com.cn/servlet/ICBCINBSEBusinessServlet";
			HttpPost post = new HttpPost(url);
			
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
			
		} finally {
			fin.close();
		}
	}
}
