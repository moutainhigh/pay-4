/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.crosspay.cardbind;

import java.util.List;
import java.util.Map;

import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.CardBindRequest;
import com.pay.gateway.dto.CardBindResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

/**
 * 验证提交时间
 */
public class SiteIdCheckRule extends MessageRule {

	private TxncoreClientService txncoreClientService;

	public void setTxncoreClientService(
			TxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		CardBindRequest cardBindRequest = (CardBindRequest) validateBean;
		CardBindResponse cardBindResponse = cardBindRequest.getCardBindResponse();

		String partnerId = cardBindRequest.getPartnerId();
		String siteId = cardBindRequest.getSiteId();

		if (StringUtil.isEmpty(siteId)) {
			cardBindResponse.setResultCode(getMessageId());
			cardBindResponse.setResultMsg("Domain name can not be Empty : 商户网站域名为空");
			return false;
		}
			//add logs 查询 1（审核通过） ，6（系统审核通过） 的域名 delin 2016年6月16日15:36:39 
		List<Map> map = txncoreClientService.crosspayMerchantWebsiteQuery(
				partnerId, siteId, "1,6");
		if (null != map && !map.isEmpty()) {
			return true;
		} else {
			cardBindResponse.setResultCode(getMessageId());
			cardBindResponse.setResultMsg(getMessage());
			return false;
	   }
	}
}
