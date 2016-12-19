/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.crosspay.token;

import com.pay.fi.commons.TradeMccEnum;
import com.pay.fi.commons.TradeTypeEnum;
import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.*;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

import java.util.Map;

/**
 * 账单地址长度不超过
 */
public class TokenValidationCheckRule extends MessageRule {
	private TxncoreClientService txncoreClientService;
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		CrosspayRequest crosspayApiRequest = (CrosspayRequest) validateBean;
		CrosspayResponse crosspayApiResponse = crosspayApiRequest.getCrosspayResponse();
		if(TradeTypeEnum.CARD_BIND.equals(crosspayApiRequest.getTradeType())){
			return true;
		}
		Map map = txncoreClientService.findByValidate(crosspayApiRequest);
		if(map != null && ResponseCodeEnum.SUCCESS.getCode().equals(map.get("responseCode"))){
			int validateNumber = (Integer)map.get("validateNumber");
			if(validateNumber / 100 >= 1)
				return true;
			if(validateNumber == 0 ){//000
				crosspayApiResponse.setResultCode("0086");
				crosspayApiResponse.setResultMsg("Invalid registration ID: 用户注册ID不正确");
				return false;
			}
			if(TradeTypeEnum.CARD_UNBIND.getCode().equals(crosspayApiRequest.getTradeType()) && validateNumber > 10){
				crosspayApiResponse.setResultCode("0451");
				crosspayApiResponse.setResultMsg("Card has been unbound: 卡号已解绑");
				return false;
			}
			crosspayApiResponse.setResultCode("0452");
			crosspayApiResponse.setResultMsg("Invalid token: 无效令牌");
			return false;
		}else{
			crosspayApiResponse.setResultCode(ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			crosspayApiResponse.setResultMsg(ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
			return false;
		}
	}

	public void setTxncoreClientService(TxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
	}
}
