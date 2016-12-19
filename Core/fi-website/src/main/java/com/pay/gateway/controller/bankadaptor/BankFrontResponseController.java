package com.pay.gateway.controller.bankadaptor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.pay.gateway.client.TxncoreClientService;
import com.pay.inf.service.HessianInvokeService;

/**
 * @Title: BankFrontResponseController.java
 * @Package com.pay.gateway.controller.bankadaptor
 * @Description: 支付账户充值前台回调
 * @author Gavin_Song(foxdog888@gmail.com)
 * @date 2011-4-9 上午10:39:27
 * @version V1.0
 */
public class BankFrontResponseController implements Controller {

	private final Log logger = LogFactory
			.getLog(BankFrontResponseController.class);
	private String bankChannel;
	private String successView;
	private String paySuccessView;
	private String failView;
	private String payFailView;
	private HessianInvokeService invokeService;
	private String cashierView;
	private TxncoreClientService txncoreClientService;
	private long amount = 0;

	public void setBankChannel(String bankChannel) {
		this.bankChannel = bankChannel;
	}

	public void setTxncoreClientService(TxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
	}

	public void setSuccessView(String successView) {
		this.successView = successView;
	}

	public void setPaySuccessView(String paySuccessView) {
		this.paySuccessView = paySuccessView;
	}

	public void setCashierView(String cashierView) {
		this.cashierView = cashierView;
	}

	public void setFailView(String failView) {
		this.failView = failView;
	}

	public void setPayFailView(String payFailView) {
		this.payFailView = payFailView;
	}

	public void setInvokeService(HessianInvokeService invokeService) {
		this.invokeService = invokeService;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) {

		String payResult = request.getParameter("PAY_SUCCESS");
		//String orderAmount = request.getParameter("orderAmount");
		String orderId = request.getParameter("orderId");
		String payFlag = request.getParameter("payFlag");

		Map<String,String> paymentInfo = txncoreClientService.getPaymentedAmount(orderId);
		String orderAmount = paymentInfo.get("orderAmount");
		String paymentedAmount = paymentInfo.get("paymentedAmount");
		if (!orderAmount.equals(paymentedAmount)) {
			Map paraMap = new HashMap();
			paraMap.put("tradeOrderNo", orderId);	
			paraMap.put("orderAmount", orderAmount);
			paraMap.put("paymentedAmount", paymentedAmount);
			paraMap.put("unPaymentAmount", new BigDecimal(orderAmount).divide(new BigDecimal(paymentedAmount)));
			//return new ModelAndView(cashierView, paraMap);
		}

		ModelAndView view = new ModelAndView(successView);
		return view.addObject("payResult", payResult)
				.addObject("orderAmount", amount)
				.addObject("orderId", orderId);
	}

}
