package com.pay.app.controller.help;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class SafeHelpController extends MultiActionController{
	
	private String productIndex;
	private String classtIndex;
	private String productViewPrefix;
	private String classtViewPrefix;
	
	public ModelAndView product(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pageView = ServletRequestUtils.getStringParameter(request, "view",productIndex);
		return new ModelAndView(productViewPrefix+pageView).addObject("view",pageView);
	}
	
	public ModelAndView classroom(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pageView = ServletRequestUtils.getStringParameter(request, "view",classtIndex);
		return new ModelAndView(classtViewPrefix+pageView).addObject("view",pageView);
	}

	public void setProductIndex(String productIndex) {
		this.productIndex = productIndex;
	}

	public void setClasstIndex(String classtIndex) {
		this.classtIndex = classtIndex;
	}

	public void setProductViewPrefix(String productViewPrefix) {
		this.productViewPrefix = productViewPrefix;
	}

	public void setClasstViewPrefix(String classtViewPrefix) {
		this.classtViewPrefix = classtViewPrefix;
	}
	
	



	
}
