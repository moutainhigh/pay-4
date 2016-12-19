package com.pay.app.common.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.pay.app.base.session.RequestLocal;

import freemarker.template.SimpleHash;
import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

/**
 * 
 * desc:
 * @author DaiDeRong
 * 2011-12-13 上午10:52:39
 */
public class CfcaTemplate implements TemplateMethodModel {
	
	@Override
	public Object exec(List arguments) throws TemplateModelException {
		HttpServletRequest request = (HttpServletRequest) RequestLocal.getRequest();
		String userAgentLow = request.getHeader("user-agent").toLowerCase();
		int isIE =  userAgentLow.toLowerCase().indexOf("msie")>=0?1:0;
		int isFirefox =  userAgentLow.toLowerCase().indexOf("firefox")>=0?1:0;
		int chrome =  userAgentLow.toLowerCase().indexOf("chrome")>=0?1:0;
		boolean isWindows= userAgentLow.toLowerCase().indexOf("windows")>=0?true:false;
		
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("ie", isIE);
		result.put("firefox", isFirefox);
		result.put("chrome", chrome);
		result.put("isWindows", isWindows);
		return new SimpleHash(result);
		
	}
}
