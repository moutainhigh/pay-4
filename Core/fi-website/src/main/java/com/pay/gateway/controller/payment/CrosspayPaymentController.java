package com.pay.gateway.controller.payment;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.gateway.client.TxncoreClientService;

/**
 * 渠道支付
 * 
 * @author
 *
 */
public class CrosspayPaymentController extends MultiActionController {

	private final Log logger = LogFactory
			.getLog(CrosspayPaymentController.class);
	private TxncoreClientService txncoreClientService;
	private String failView;
	private String successView;
	private String redirectView;
	private String querysuccessView;
	private String fromView;

	@SuppressWarnings("unchecked")
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response) {

		Map resultMap = new HashMap();

		// 根据操作执行结果，显示等待页面
		return new ModelAndView(successView, resultMap);
	}

	public void setFailView(String failView) {
		this.failView = failView;
	}

	public void setSuccessView(String successView) {
		this.successView = successView;
	}

	public void setRedirectView(String redirectView) {
		this.redirectView = redirectView;
	}

	public void setQuerysuccessView(String querysuccessView) {
		this.querysuccessView = querysuccessView;
	}

	public void setFromView(String fromView) {
		this.fromView = fromView;
	}

	public void setTxncoreClientService(
			TxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
	}
}