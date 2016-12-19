package com.pay.app.base.api.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.pay.util.RSAHelper;



public class SafeControllerWrapper extends HttpServletRequestWrapper {

	private String[] controllerNames;
	
	private static String SECURITY = "security";
	
	public SafeControllerWrapper(HttpServletRequest request,String[] controllerNames) {
		super(request);
		this.controllerNames = controllerNames;
	}

	@Override
	public String getParameter(String name) {
		
		String hasSecurity = super.getParameter("hasSecurity")==null?"":super.getParameter("hasSecurity");
		String param = super.getParameter(name);
		if(SECURITY.equals(hasSecurity)){
			for(String controllerName:controllerNames){
				if(controllerName.equals(name)){
					try {
						return RSAHelper.getRSAString(param);
					} catch (Exception e) {
						return "";
					}
				}
			}
		}
		
		return param;
	}
}
