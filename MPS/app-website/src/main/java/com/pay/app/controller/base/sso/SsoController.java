package com.pay.app.controller.base.sso;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class SsoController extends MultiActionController {
	private String toCheckView;
	
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(toCheckView);
	}
	
	public void setToCheckView(String toCheckView) {
		this.toCheckView = toCheckView;
	}
}
