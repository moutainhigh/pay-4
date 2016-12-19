/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.refund;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.OrderApiQueryDetailResponse;
import com.pay.gateway.dto.TradeOrderInfo;
import com.pay.gateway.dto.refund.RefundApiRequest;
import com.pay.gateway.dto.refund.RefundApiResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.MapUtil;
import com.pay.util.NumberUtil;
import com.pay.util.StringUtil;

/**
 * 验证订单号
 */
public class RefundAmountCheckRule extends MessageRule {
	
	private TxncoreClientService txncoreClientService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		RefundApiRequest refundApiRequest = (RefundApiRequest) validateBean;
		RefundApiResponse refundApiResponse = refundApiRequest
				.getRefundApiResponse();

		String refundAmount = refundApiRequest.getRefundAmount();

		if (!StringUtil.isEmpty(refundAmount)
				&& NumberUtil.isNumber(refundAmount)
				&& Long.valueOf(refundAmount) > 0) {
			
			Map<String,String> paraMap = new HashMap<String, String>();
			paraMap.put("orderId",refundApiRequest.getOrderId());
			paraMap.put("type","1");
			paraMap.put("mode","1");
			paraMap.put("partnerId",refundApiRequest.getPartnerId());
			
			Map map = txncoreClientService.crosspayOrderQuery_(paraMap);
			List<Map> details = (List<Map>) map.get("details");
			
			if(details!=null){
				List<TradeOrderInfo> detailRes = MapUtil.map2List(
						TradeOrderInfo.class, details);
				
				TradeOrderInfo info = detailRes.get(0);
				
				if(info!=null){
					String oa = info.getOrderAmount();
					Long orderAmount = Long.valueOf(oa);
					
					String ra = refundApiRequest.getRefundAmount();
					Long redundAmount = Long.valueOf(ra);
					
					if(redundAmount>orderAmount){
						refundApiResponse.setResultCode(getMessageId());
						refundApiResponse.setResultMsg(getMessage());
						return false;
					}
				}
			}

			return true;
		} else {
			refundApiResponse.setResultCode(getMessageId());
			refundApiResponse.setResultMsg(getMessage());
			return false;
		}
	}

	public void setTxncoreClientService(TxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
	}
}
