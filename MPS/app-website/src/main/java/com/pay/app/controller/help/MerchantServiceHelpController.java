package com.pay.app.controller.help;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class MerchantServiceHelpController extends MultiActionController{
	private String indusSolutionViewPrefix;
	private String indusSolutionIndex;
	private String paymentproductsViewPrefix;
	private String paymentproductsIndex;
	public ModelAndView indusSolution(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pageView = ServletRequestUtils.getStringParameter(request, "view",indusSolutionIndex);
		return new ModelAndView(indusSolutionViewPrefix+pageView).addObject("view",pageView);
	}
	
	public ModelAndView paymentproducts(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pageView = ServletRequestUtils.getStringParameter(request, "view",paymentproductsIndex);
		return new ModelAndView(paymentproductsViewPrefix+pageView).addObject("view",pageView);
	}

	public void setIndusSolutionViewPrefix(String indusSolutionViewPrefix) {
		this.indusSolutionViewPrefix = indusSolutionViewPrefix;
	}

	public void setIndusSolutionIndex(String indusSolutionIndex) {
		this.indusSolutionIndex = indusSolutionIndex;
	}

	public void setPaymentproductsViewPrefix(String paymentproductsViewPrefix) {
		this.paymentproductsViewPrefix = paymentproductsViewPrefix;
	}

	public void setpaymentproductsIndex(String paymentproductsIndex) {
		this.paymentproductsIndex = paymentproductsIndex;
	}
}
