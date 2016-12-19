package com.pay.acc.service.cidverify.cid2gov;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.service.cidverify.INciicClient;
/*
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.client.XFireProxy;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.codehaus.xfire.transport.http.CommonsHttpMessageSender;
import org.codehaus.xfire.transport.http.EasySSLProtocolSocketFactory;
import org.codehaus.xfire.util.dom.DOMOutHandler;
*/

/**
 * 实名认证基础服务
 * 使用代理接口调用
 * @author lei.jiangl
 * @version 
 * @data 2010-9-12 下午03:05:54
 * @deprecated 此类依赖的xfire.jar 包与cxf 调用webservice 冲突。系统中去掉xfire 依赖，作废此类。 
 */
@Deprecated
public class NciicClient implements INciicClient{
	
	private final Log log = LogFactory.getLog(NciicClient.class);
	private String fileUrl;
	private String serviceUrl;
	private String serviceName;

	/**
	 * 根据身份文件以及条件进行实名认证
	 * 返回原始验证结果
	 * @param condition  查询条件条件
	 * @return
	 * @throws Exception
	 * @Deprecated
	 */

	public String executeCidVerify(String condition)throws Exception {
		/*
		log.info("#提交用户信息到公安网执行认证#");
		ProtocolSocketFactory easy = new EasySSLProtocolSocketFactory();
		Protocol protocol = new Protocol("https", easy, 443);
		Protocol.registerProtocol("https", protocol);
		Service serviceModel = new ObjectServiceFactory().create(ServiceInf.class, serviceName, null,null);
		ServiceInf service = (ServiceInf) new XFireProxyFactory().create(serviceModel, serviceUrl + serviceName);
		Long timeout = 10000L;
		HttpClientParams params = new HttpClientParams();
		params.setParameter(HttpClientParams.USE_EXPECT_CONTINUE, Boolean.FALSE); 
		params.setParameter(HttpClientParams.CONNECTION_MANAGER_TIMEOUT, timeout);// unit : millisecond
		
		Client client = ((XFireProxy)Proxy.getInvocationHandler(service)).getClient();
		client.addOutHandler(new DOMOutHandler());
		client.setProperty(CommonsHttpMessageSender.GZIP_ENABLED,Boolean.TRUE);
		client.setProperty(CommonsHttpMessageSender.DISABLE_EXPECT_CONTINUE, "1");
		client.setProperty(CommonsHttpMessageSender.HTTP_CLIENT_PARAMS, params); 
		String licensecode = null;
		String result = null;
		BufferedReader in;
		in = new BufferedReader(new FileReader(fileUrl));
		licensecode = in.readLine();
		result = service.nciicCheck(licensecode, condition);
		in.close();
		client.close();
		log.info("#执行认证完毕,返回认证结果数据#");
		return result;
		*/
		return "";
	}
	
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
}