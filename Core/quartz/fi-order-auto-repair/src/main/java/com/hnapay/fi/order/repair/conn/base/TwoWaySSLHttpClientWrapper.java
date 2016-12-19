/**
 * TwoWaySSLHttpClientWrapper.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.conn.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * 双向ssl http client 封装
 * latest modified time : 2011-8-23 上午10:32:12
 * @author bigknife
 */
public class TwoWaySSLHttpClientWrapper extends HttpClientWrapper {
	private Log log = LogFactory.getLog(getClass());
	private String jksUrl;
	private String jksPassword;
	private int sslPort = 443;
	
	
	
	/**
	 * @param sslPort the sslPort to set
	 */
	public void setSslPort(int sslPort) {
		this.sslPort = sslPort;
	}

	/**
	 * @param jksUrl the jksUrl to set
	 */
	public void setJksUrl(String jksUrl) {
		this.jksUrl = jksUrl;
	}

	/**
	 * @param jksPassword the jksPassword to set
	 */
	public void setJksPassword(String jksPassword) {
		this.jksPassword = jksPassword;
	}

	/* (non-Javadoc)
	 * @see com.hnapay.fi.order.repair.conn.IHttpClientWrapper#wrap(org.apache.http.client.HttpClient)
	 */
	@Override
	public HttpClient innerWrap(HttpClient client) {
		InputStream fin = null;
		try {
			fin = new FileInputStream(jksUrl);
			KeyStore jks = KeyStore.getInstance(KeyStore.getDefaultType());
			jks.load(fin, jksPassword.toCharArray());
			SSLSocketFactory socketFactory = new SSLSocketFactory(jks,
					jksPassword, jks);
			Scheme scheme = new Scheme("https", socketFactory, sslPort);
			HttpClient hc = client;
			if(hc == null){
				hc = new DefaultHttpClient();
			}
			hc.getConnectionManager().getSchemeRegistry().register(scheme);
			return hc;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Http Client 封装异常.",e);
			throw new RuntimeException("Http Client 封装异常.");
		}finally{
			if(fin != null){
				try {
					fin.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
