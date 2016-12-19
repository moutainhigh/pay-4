package com.pay.pe.controller;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Assert;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.pe.dto.PaymentServiceDTO;
import com.pay.pe.service.paymentservice.PaymentServiceService;
import com.pay.pricingstrategy.dto.PricingStrategyDTO;
import com.pay.pricingstrategy.dto.PricingStrategyDetailDTO;
import com.pay.pricingstrategy.service.PricingStrategyService;
import com.pay.util.StringUtil;

public final class ManagePricingStrategyDetailController extends
		MultiActionController {
	public ManagePricingStrategyDetailController() {

	}

	private PricingStrategyService pricingStrategyService;

	private PaymentServiceService paymentServiceService;

	private String indexView;

	private String listView;

	private String successView;
	
	private String redirectView;
	

	protected Map referenceData(final HttpServletRequest request)
			throws ServletException {
		Map refData = new HashMap();
		String pricingStrategyCode = request.getParameter("pricingstrategycode");
		if (StringUtil.isEmpty(pricingStrategyCode)) {
			pricingStrategyCode = String.valueOf(request.getSession().getAttribute("pricingstrategycode"));
		}
		Assert.notNull(pricingStrategyCode);

		PricingStrategyDTO pricingStrategyDto = this.pricingStrategyService.getPricingStrategy(new Long(pricingStrategyCode));
		PaymentServiceDTO paymentServiceDto =	paymentServiceService.getPaymentService(pricingStrategyDto.getPaymentServiceCode());
//		 PaymentServiceDTO paymentServiceDto =		 pricingStrategyDto.getPaymentServiceDto();
		 refData.put("paymentServiceDto" , paymentServiceDto);
		refData.put("pricingStrategyDto", pricingStrategyDto);
		
		String error = request.getParameter("error");
		if (!StringUtil.isNull(error)) {
			error = URLDecoder.decode(error);
			refData.put("error", error);
		}
		return refData;
	}

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(indexView)
				.addAllObjects(referenceData(request));
	}

	public ModelAndView query(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {

		String pricingstrategycode = request.getParameter("pricingstrategycode");
		String status = request.getParameter("status");

		List<PricingStrategyDetailDTO> resultList = pricingStrategyService
				.getPricingStrategyDetailByCode(Long
						.parseLong(pricingstrategycode));
		for(PricingStrategyDetailDTO dto:resultList){
			String reservedCode = dto.getReservedCode();
	        if(reservedCode != null && !"".equals(reservedCode)){
	        	int reservedCodeLength = reservedCode.length();
	        	if(reservedCodeLength > 1){
	        		if("10".equals(reservedCode.substring(reservedCodeLength-2, reservedCodeLength))){
	        			reservedCode = reservedCode.substring(0, reservedCodeLength-2);
	        		}
	        		dto.setReservedCode(reservedCode);
	        	}
	        }
		}
		request.getSession().setAttribute("pricingstrategycode",pricingstrategycode);

		return new ModelAndView(this.getSuccessView()).addObject("resultList",
				resultList).addObject("status", status);

	}

	/**
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ModelAndView
	 * @throws Exception
	 */
	public ModelAndView deldetail(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String pricingStrategycode = String.valueOf(request.getSession()
				.getAttribute("pricingstrategycode"));
		
		PricingStrategyDTO dto = this.pricingStrategyService
				.getPricingStrategy(new Long(pricingStrategycode));

		// String url = response.encodeRedirectURL(this.getRedirectView());
		String url = response.encodeRedirectURL(this.getRedirectView());

		String[] code = request.getParameterValues("code");
		if (!StringUtil.isNull(code) && code.length > 0) {
			this.pricingStrategyService.removeMulPricingStrategyDetail(code);
		}
		return new ModelAndView(url);
	}

	public String getSuccessView() {
		return successView;
	}

	public void setSuccessView(String successView) {
		this.successView = successView;
	}

	public void setPricingStrategyService(
			PricingStrategyService pricingStrategyService) {
		this.pricingStrategyService = pricingStrategyService;
	}

	public PaymentServiceService getPaymentServiceService() {
		return paymentServiceService;
	}

	public void setPaymentServiceService(
			PaymentServiceService paymentServiceService) {
		this.paymentServiceService = paymentServiceService;
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

	public PricingStrategyService getPricingStrategyService() {
		return pricingStrategyService;
	}

	public String getRedirectView() {
		return redirectView;
	}

	public void setRedirectView(String redirectView) {
		this.redirectView = redirectView;
	}

}
