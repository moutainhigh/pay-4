package com.pay.gateway.validate.crosspay.locale;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.CrosspayGatewayRequest;
import com.pay.gateway.dto.CrosspayGatewayResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.NumberUtil;
import com.pay.util.StringUtil;

public class TradeOraderCheckRule  extends MessageRule{
	
	private static Logger logger = LoggerFactory.getLogger(TradeOraderCheckRule.class);
	private String messageEn;
	
	public void setMessageEn(String messageEn) {
		this.messageEn = messageEn;
	}

	public String getMessageEn() {
		return messageEn;
	}

	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		CrosspayGatewayRequest onlineRequestDTO = (CrosspayGatewayRequest) validateBean;
		CrosspayGatewayResponse onlineResponseDTO = onlineRequestDTO
				.getGatewayResponseDTO();

		String orderAmount = onlineRequestDTO.getOrderAmount();
		String language = onlineRequestDTO.getLanguage();
		
		if (StringUtil.isEmpty(orderAmount)||!NumberUtil.isNumber(orderAmount)) {
			onlineResponseDTO.setResultCode(getMessageId());//如果订单金额没有或者订单金额不是数字
			
			if("cn".equals(language))
				onlineResponseDTO.setResultMsg(getMessage());
			else
				onlineResponseDTO.setResultMsg(getMessageEn());
			
			return false;
		} 
			return true;
	}

}
