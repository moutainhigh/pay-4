package com.pay.acc.service.cert;

import com.pay.acc.service.cidverify.impl.validation.Des2;
import com.pay.acc.service.cidverify.impl.validation.QueryValidatorServices;
import com.pay.acc.service.cidverify.impl.validation.QueryValidatorServicesProxy;

public class Test {

	public static void main(String[] args) throws Exception {
		//获得WebServices的代理对象
		QueryValidatorServicesProxy proxy = new QueryValidatorServicesProxy();
		proxy.setEndpoint("http://gboss.id5.cn/services/QueryValidatorServices");
		QueryValidatorServices service =  proxy.getQueryValidatorServices();
		//对调用的参数进行加密
		String key = "12345678";
		String userName = Des2.encode(key, "username");
		String password = Des2.encode(key, "password");
		//System.setProperty("javax.net.ssl.trustStore", "keystore");
		//查询返回结果
		String resultXML = "";
		
		/*------身份信息核查比对-------*/
		String datasource = Des2.encode(key,"1A020201");
		//单条
		String param = "宋雪,210219197101011246";
		resultXML = service.querySingle(userName, password, datasource, Des2.encode(key, param));
		//批量
//		String params = "王茜,150202198302101248;" + 
//						"吴晨晨,36252519821201061x;" + 
//						"王鹏,110108197412255477";
//		resultXML = service.queryBatch(userName, password, datasource, Des2.encode(key, params));
		
		
		//解密
		resultXML = Des2.decodeValue(key, resultXML);
		System.out.println(resultXML);
		
		
	}

}
