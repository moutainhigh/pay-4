/**
 * 
 */
package com.pay.gateway.controller.cashier;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.fi.commons.TerminalType;
import com.pay.util.StringUtil;

/**
 * computop收单失败跳转
 * 
 * @author
 *
 */
public class ComputopFailureRedirectController extends MultiActionController {

	private final Log logger = LogFactory
			.getLog(ComputopFailureRedirectController.class);
	private String toView;
	private String cnMobileFailView;
	private String enMobileFailView;
	private String cnFailView;
	private String enFailView;
	private Map<String,String> valueMap;

	public void setValueMap(Map<String, String> valueMap) {
		this.valueMap = valueMap;
	}

	public void setToView(String toView) {
		this.toView = toView;
	}

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, String> resultMap = new HashMap<String, String>();
		String errorMsg = request.getParameter("errorMsg");
		String errorCode = request.getParameter("errorCode");
		String partnerId = request.getParameter("partnerId");
		String orderId = request.getParameter("orderId");
		String goodsName = request.getParameter("goodsName");
		String goodsDesc = request.getParameter("goodsDesc");
		String sellerName = request.getParameter("sellerName");
		String orderAmount = request.getParameter("orderAmount");
		String currencyCode = request.getParameter("currencyCode");
		String language = request.getParameter("language");
		String orderTerminal = request.getParameter("orderTerminal");
		String returnUrl = request.getParameter("returnUrl");
		
		resultMap.put("orderId", orderId);
		resultMap.put("partnerId", partnerId);
		resultMap.put("goodsName", goodsName);
		resultMap.put("goodsDesc", goodsDesc);
		resultMap.put("sellerName", sellerName);
		resultMap.put("returnUrl",returnUrl);
		
		if(!StringUtil.isEmpty(orderAmount)){
			BigDecimal am = new BigDecimal(orderAmount).multiply(new BigDecimal("0.01"));
			resultMap.put("orderAmount",am==null?"0.00":am.toString());
		}
		
		if(valueMap!=null&&"cn".equals(language)){
			String key = errorCode+"_cn";
			errorMsg = valueMap.get(key);
		}

		resultMap.put("errorMsg", errorMsg);
		resultMap.put("errorCode",errorCode);
		resultMap.put("resultCode",errorCode);
		resultMap.put("resultMsg",errorMsg);
		resultMap.put("currencyCode",currencyCode);
		resultMap.put("language",language);
		resultMap.put("orderTerminal",orderTerminal);
		
		if(TerminalType.MOBILE.getCode().equals(orderTerminal)){
			if("cn".equals(language))
				return new ModelAndView(cnMobileFailView, resultMap);
			else
				return new ModelAndView(enMobileFailView, resultMap);
		}else{
			if("cn".equals(language))
				return new ModelAndView(cnFailView, resultMap);
			else
				return new ModelAndView(enFailView, resultMap);
		}
	}

	public void setCnMobileFailView(String cnMobileFailView) {
		this.cnMobileFailView = cnMobileFailView;
	}

	public void setEnMobileFailView(String enMobileFailView) {
		this.enMobileFailView = enMobileFailView;
	}

	public void setCnFailView(String cnFailView) {
		this.cnFailView = cnFailView;
	}

	public void setEnFailView(String enFailView) {
		this.enFailView = enFailView;
	}
}
