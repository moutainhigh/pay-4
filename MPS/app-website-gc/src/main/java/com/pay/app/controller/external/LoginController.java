package com.pay.app.controller.external;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.pay.app.common.helper.MessageConvertFactory;

public class LoginController extends AbstractController{
	private String externalLogout;
	
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView(externalLogout);
		String service=StringUtils.isBlank(request.getParameter("service"))?MessageConvertFactory.getMessage("mall.defaultmall")
				:request.getParameter("service");

		return mv.addObject("service",service);
	}
	public String getExternalLogout() {
		return externalLogout;
	}
	public void setExternalLogout(String externalLogout) {
		this.externalLogout = externalLogout;
	}
}
