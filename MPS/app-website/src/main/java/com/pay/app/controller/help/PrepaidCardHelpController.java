package com.pay.app.controller.help;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class PrepaidCardHelpController extends MultiActionController{
	private String mgmtprepaidcardViewPrefix;
	private String mgmtprepaidcardIndex;
	private String introprepaidcardViewPrefix;
	private String introprepaidcardIndex;
	public ModelAndView mgmtprepaidcard(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pageView = ServletRequestUtils.getStringParameter(request, "view",mgmtprepaidcardIndex);
		return new ModelAndView(mgmtprepaidcardViewPrefix+pageView).addObject("view",pageView);
	}
	
	public ModelAndView introprepaidcard(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pageView = ServletRequestUtils.getStringParameter(request, "view",introprepaidcardIndex);
		return new ModelAndView(introprepaidcardViewPrefix+pageView).addObject("view",pageView);
	}

	public void setMgmtprepaidcardViewPrefix(String mgmtprepaidcardViewPrefix) {
		this.mgmtprepaidcardViewPrefix = mgmtprepaidcardViewPrefix;
	}

	public void setMgmtprepaidcardIndex(String mgmtprepaidcardIndex) {
		this.mgmtprepaidcardIndex = mgmtprepaidcardIndex;
	}

	public void setIntroprepaidcardViewPrefix(String introprepaidcardViewPrefix) {
		this.introprepaidcardViewPrefix = introprepaidcardViewPrefix;
	}

	public void setIntroprepaidcardIndex(String introprepaidcardIndex) {
		this.introprepaidcardIndex = introprepaidcardIndex;
	}
}
