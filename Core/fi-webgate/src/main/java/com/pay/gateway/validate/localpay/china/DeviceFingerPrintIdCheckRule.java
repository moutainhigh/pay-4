package com.pay.gateway.validate.localpay.china;

import com.pay.fi.commons.TradeTypeEnum;
import com.pay.gateway.dto.ChinaLocalPayRequest;
import com.pay.gateway.dto.ChinaLocalPayResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

public class DeviceFingerPrintIdCheckRule extends MessageRule {

	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		ChinaLocalPayRequest crosspayApiRequest = (ChinaLocalPayRequest) validateBean;
		ChinaLocalPayResponse crosspayApiResponse = crosspayApiRequest.getChinaLocalPayResponse();

		String deviceFingerPrintId = crosspayApiRequest.getDeviceFingerprintId();
		
		String tradeType = crosspayApiRequest.getTradeType();
		
		if(!TradeTypeEnum.REALTIME_CASH.getCode().equals(tradeType)){
			if(StringUtil.isEmpty(deviceFingerPrintId)|| 
					deviceFingerPrintId.trim().length()>60) {
				crosspayApiResponse.setResultMsg(getMessage());
				crosspayApiResponse.setResultCode(getMessageId());
				return false;
			}
		}
		return true;
	}

}
