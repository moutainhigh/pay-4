package com.pay.acc.service.cidverify.impl.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorService {
	
	private String userName = "";
	private String password = "";

	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean validator(String name,String cardNo) throws Exception {
		String r = requestValidator(name, cardNo);
		Pattern p = Pattern.compile("<compStatus desc=\"比对状态\">3</compStatus>");
		Matcher m = p.matcher(r);
		return m.find();
	}
	
	private String requestValidator(String name,String cardNo) throws Exception{
		QueryValidatorServicesProxy proxy = new QueryValidatorServicesProxy();
		proxy.setEndpoint("http://gboss.id5.cn/services/QueryValidatorServices");
		QueryValidatorServices service =  proxy.getQueryValidatorServices();
		String key = "12345678";
		String resultXML = "";
		String datasource = Des2.encode(key,"1A020201");
		resultXML = service.querySingle(Des2.encode(key, userName), Des2.encode(key, password), datasource, Des2.encode(key, name+","+cardNo));
		resultXML = Des2.decodeValue(key, resultXML);
		return resultXML;
	}
}
