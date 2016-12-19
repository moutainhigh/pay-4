package com.pay.gateway.validate.crosspay.rule;
///**
// *  File: 
// *  Description:
// *  Copyright 2006-2011 pay Corporation. All rights reserved.
// *  Date      Author      Changes
// *  2011-9-12   terry     Create
// *
// */
//package com.pay.gateway.validate.crosspay.api;
//
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.lang.StringUtils;
//
//import com.pay.gateway.client.TxncoreClientService;
//import com.pay.gateway.dto.CrosspayApiRequest;
//import com.pay.gateway.dto.CrosspayApiResponse;
//import com.pay.inf.rule.MessageRule;
//import com.pay.util.StringUtil;
//
///**
// * 验证提交时间
// */
//public class VccSiteIdCheckRule extends MessageRule {
//
//	private TxncoreClientService txncoreClientService;
//
//	public void setTxncoreClientService(
//			TxncoreClientService txncoreClientService) {
//		this.txncoreClientService = txncoreClientService;
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
//	 */
//	@Override
//	protected boolean makeDecision(Object validateBean) throws Exception {
//
//		CrosspayApiRequest crosspayApiRequest = (CrosspayApiRequest) validateBean;
//		CrosspayApiResponse crosspayApiResponse = crosspayApiRequest
//				.getCrosspayApiResponse();
//
//		String partnerId = crosspayApiRequest.getPartnerId();
//		String siteId = crosspayApiRequest.getSiteId();
//		String isMpsVCC = crosspayApiRequest.getIsMpsVCC() ;
//
//		if (StringUtil.isEmpty(siteId)) {
//			crosspayApiResponse.setResultCode(getMessageId());
//			crosspayApiResponse.setResultMsg("Domain name can not be Empty");
//			return false;
//		}
//		
//		if(StringUtils.isNotEmpty(isMpsVCC)){
//			return true ;
//		}
//		
//		List<Map> map = txncoreClientService.crosspayMerchantWebsiteQuery(
//				partnerId, siteId, "1");
//
//		if (null != map && !map.isEmpty()) {
//			return true;
//		} else {
//			crosspayApiResponse.setResultCode(getMessageId());
//			crosspayApiResponse.setResultMsg(getMessage());
//			return false;
//	   }
//	}
//}
