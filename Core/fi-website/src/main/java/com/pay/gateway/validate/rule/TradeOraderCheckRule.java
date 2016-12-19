/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.rule;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.PaymentInfo;
import com.pay.gateway.dto.PaymentResult;
import com.pay.inf.rule.MessageRule;
import com.pay.util.NumberUtil;
import com.pay.util.StringUtil;

/**
 * 订单金额校验
 */
public class TradeOraderCheckRule extends MessageRule {
	
	private static Logger logger = LoggerFactory.getLogger(TradeOraderCheckRule.class);
	private String messageEn;
	
	private TxncoreClientService txncoreClientService;

	public void setTxncoreClientService(
			TxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
	}
	
	public void setMessageEn(String messageEn) {
		this.messageEn = messageEn;
	}

	public String getMessageEn() {
		return messageEn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		PaymentInfo paymentInfo = (PaymentInfo) validateBean;
		String orderAmount = paymentInfo.getOrderAmount();
		PaymentResult paymentResult = paymentInfo.getPaymentResult();
		
		
		if (null == paymentResult) {
			paymentResult = new PaymentResult();
			paymentInfo.setPaymentResult(paymentResult);
		}
		
		String tradeOrderNo = paymentInfo.getTradeOrderNo();
		String partnerId = paymentInfo.getPartnerId();
		String orderId = paymentInfo.getOrderId();
		String currencyCode = paymentInfo.getCurrencyCode();
		String language = paymentInfo.getLanguage();

		
		if (StringUtil.isEmpty(orderAmount)||!NumberUtil.isNumber(orderAmount)) {
			paymentResult.setErrorCode(getMessageId());//如果订单金额没有或者订单金额不是数字
			
			if("cn".equals(language))
				paymentResult.setErrorMsg(getMessage());
			else
				paymentResult.setErrorMsg(getMessageEn());
			
			return false;
		} else {
			Map<String,String> params = new HashMap<String, String>();
			params.put("orderId",orderId);
			params.put("partnerId",partnerId);
			params.put("status","0");
			params.put("tradeOrderNo",tradeOrderNo);
			
			Map map = txncoreClientService.completedOrderQuery(params);
			
			String hasTradeOrder = (String) map.get("hasTradeOrder");
			
			if ("0".equals(hasTradeOrder)) {
				paymentResult.setErrorCode(getMessageId());
				
				if("cn".equals(language))
					paymentResult.setErrorMsg(getMessage());
				else
					paymentResult.setErrorMsg(getMessageEn());
				
				return false;
			} else {
				
				Map<String,String> mapInfo = (Map<String, String>) map.get("mapInfo");
				
				String orderAmount_ = (String) mapInfo.get("orderAmount");
				String currencyCode_ = (String) mapInfo.get("currencyCode");
				String status = (String) mapInfo.get("status");
				
				BigDecimal oldAmount = new BigDecimal(orderAmount_);
				BigDecimal newAmount = new BigDecimal(orderAmount)
				                          .multiply(new BigDecimal("10"));
				
			    logger.info("orderAmount_: "+orderAmount_+",currencyCode_: "+currencyCode_
			    		        +",status: "+status+" ,newAmount: "+newAmount+" ,currencyCode: "+currencyCode);
				
				//满足了这些条件才可以提交成功
				if(oldAmount.compareTo(newAmount)==0&&currencyCode_.equals(currencyCode)
						&&"0".equals(status)){
					return true;
				}
			}

			paymentResult.setErrorCode(getMessageId());
			
			if("cn".equals(language))
				paymentResult.setErrorMsg(getMessage());
			else
				paymentResult.setErrorMsg(getMessageEn());
			
			return false;
		}
	}

}
