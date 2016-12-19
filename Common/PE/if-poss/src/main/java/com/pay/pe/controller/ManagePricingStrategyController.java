package com.pay.pe.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.pe.dto.PaymentServiceDTO;
import com.pay.pe.service.paymentservice.PaymentServiceService;
import com.pay.pricingstrategy.dto.PricingStrategyDTO;
import com.pay.pricingstrategy.service.PricingStrategyService;
import com.pay.util.StringUtil;

public class ManagePricingStrategyController extends MultiActionController {

	private final Log log = LogFactory
			.getLog(ManagePricingStrategyController.class);

	private String indexView;

	private String listView;

	private String redirectView;

	private PaymentServiceService paymentServiceService;

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String paymentservicecode = request.getParameter("paymentservicecode");
		if (StringUtil.isNull(paymentservicecode)) {
			paymentservicecode = String.valueOf(request.getSession()
					.getAttribute("paymentservicecode"));
		}
		Assert.hasLength(paymentservicecode);
		PaymentServiceDTO paymentServiceDto = this.paymentServiceService
				.getPaymentService(Integer.valueOf(paymentservicecode));
		return new ModelAndView(indexView).addObject("paymentServiceDto",
				paymentServiceDto);
	}

	public ModelAndView query(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		int paymentServiceCode = Integer.valueOf(request
				.getParameter("paymentservicecode"));
		request.getSession().setAttribute("paymentservicecode",
				paymentServiceCode);

		Assert.notNull(paymentServiceCode);
		List<PricingStrategyDTO> resultList;
		try {
			resultList = this.pricingStrategyService
					.getAllPricingStrategyByPSC(paymentServiceCode);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return new ModelAndView(listView).addObject("resultList", resultList);
	}

	public ModelAndView delete(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String[] params = request.getParameterValues("code");
		String url = response.encodeRedirectURL(this.getRedirectView());

		try {
			this.pricingStrategyService.removeMulPricingStrategy(params);
		} catch (Exception e) {
			e.printStackTrace();
			String error = "删除数据异常!";
			return new ModelAndView(url).addObject("error", error);
		}

		return new ModelAndView(url);
	}

	public String getIndexView() {
		return indexView;
	}

	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}

	public String getListView() {
		return listView;
	}

	public void setListView(String listView) {
		this.listView = listView;
	}

	public PaymentServiceService getPaymentServiceService() {
		return paymentServiceService;
	}

	public void setPaymentServiceService(
			PaymentServiceService paymentServiceService) {
		this.paymentServiceService = paymentServiceService;
	}

	private PricingStrategyService pricingStrategyService;

	public String getRedirectView() {
		return redirectView;
	}

	public void setRedirectView(String redirectView) {
		this.redirectView = redirectView;
	}

	public PricingStrategyService getPricingStrategyService() {
		return pricingStrategyService;
	}

	public void setPricingStrategyService(
			PricingStrategyService pricingStrategyService) {
		this.pricingStrategyService = pricingStrategyService;
	}

}
