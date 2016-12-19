package com.pay.rm.blacklist.service.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Proxy;
import java.net.MalformedURLException;

import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.client.XFireProxy;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.codehaus.xfire.transport.http.CommonsHttpMessageSender;
import org.codehaus.xfire.transport.http.EasySSLProtocolSocketFactory;
import org.codehaus.xfire.util.dom.DOMOutHandler;

import com.pay.rm.blacklist.service.ServiceInf;

public class NciicClient {

	protected transient Log log = LogFactory.getLog(getClass());

	public static final String SERVICE_URL = "https://api.nciic.com.cn/nciic_ws/services/NciicServices";

	public NciicClient() {
	};

	public static void main(String[] args) throws MalformedURLException {

		// 授权文件
		String xml = "<?xml version='1.0' encoding='UTF-8'?>"
				+ "<ROWS><ROWHMDLX='1'><HMDID>100777777777</HMDID><CYDWBM>1007</CYDWBM><GMSFHM></GMSFHM>"
				+ "<XM>张三</XM><FSDQ>1200</FSDQ>"
				+ "<LHTJ>13</LHTJ><HMDSJ>199</HMDSJ><HMDSJBZ>个人套现</HMDSJBZ><SJHM>13438777773</SJHM><GDDH>44581111</GDDH>"
				+ "<YHKH>2512325123123125</YHKH>"
				+ "<KHH>农行茶店子支行</KHH>"
				+ "<IP>110.189.24.142#192.168.78.107</IP><MAC>dadsawd</MAC><EMAIL>aw@AW.A</EMAIL><DZ>dddddd</DZ><ICP>1232312312</ICP>"
				+ "<ICPBAR>adwdad</ICPBAR><URLDZ>www.sina.com</URLDZ><URLTZDZ>www.baidu.com</URLTZDZ><ZFR>aaaa</ZFR><SJZT>2</SJZT>"
				+ "<CZR>zhouzh1</CZR><ICPBAR>ccc</ICPBAR><GMSFHM>1303212412312</GMSFHM><YWLB>01</YWLB>"
				+ "</ROW></ROWS>";
		new NciicClient().executeClient(xml);
	}

	public String executeClient(String xml) throws MalformedURLException {
		ProtocolSocketFactory easy = new EasySSLProtocolSocketFactory();
		Protocol protocol = new Protocol("https", easy, 443);
		Protocol.registerProtocol("https", protocol);
		Service serviceModel = new ObjectServiceFactory().create(
				ServiceInf.class, "NciicServices", null, null);
		ServiceInf service = (ServiceInf) new XFireProxyFactory().create(
				serviceModel, SERVICE_URL);
		Client client = ((XFireProxy) Proxy.getInvocationHandler(service))
				.getClient();
		client.addOutHandler(new DOMOutHandler());
		// 设置压缩传输属性
		client.setProperty(CommonsHttpMessageSender.GZIP_ENABLED, Boolean.TRUE);
		// 忽略超时
		client.setProperty(CommonsHttpMessageSender.DISABLE_EXPECT_CONTINUE,
				"1");
		client.setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, "0");
		/**
		 * 读取授权文件内容，因为授权文件为加密格式请不要对内容做任何修改
		 */
		String licensecode = null;
		String result = null;
		BufferedReader in;
		try {
			InputStream input = this.getClass().getResourceAsStream(
					"/template/4317.txt");
			in = new BufferedReader(new InputStreamReader(input));
			licensecode = in.readLine();
			// 负面信息数据上传
			result = service.nciicFmxxEntry(licensecode, xml);
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		System.out.println(result);
		return result;
	}
}
