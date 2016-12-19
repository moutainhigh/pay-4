package com.pay.app.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.pay.acc.service.account.AccountInfoService;
import com.pay.app.common.ServiceLocator;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.util.RSAHelper;

public class SafeControllerWrapper extends HttpServletRequestWrapper {

	private static AccountInfoService accountInfoService = null;
	
	private String[] controllerNames;
	
	public SafeControllerWrapper(HttpServletRequest request,String[] controllerNames) {
		super(request);
		this.controllerNames = controllerNames;
		if(accountInfoService == null){
			accountInfoService = ServiceLocator.getService(AccountInfoService.class,"acc-accountInfoService");
		}
	}

	@Override
	public String getParameter(String name) {
		
		String hasSecurity = super.getParameter("hasSecurity")==null?"":super.getParameter("hasSecurity");
		String param = super.getParameter(name);
		if(MessageConvertFactory.getMessage("payedit.controller.hassecurity").equals(hasSecurity)){
			for(String controllerName:controllerNames){
				if(controllerName.equals(name)){
					try {
						return RSAHelper.getRSAString(param);
					} catch (Exception e) {
						return "";
					}
				}
			}
		}else if(MessageConvertFactory.getMessage("payedit.cert.controller.hassecurity").equals(hasSecurity)){
			for(String controllerName:controllerNames){
				if(controllerName.equals(name)){
					try {
						return accountInfoService.decryptCertPwd(param);
					} catch (Exception e) {
						return "";
					}
				}
			}
		}
		return param;
	}
}
