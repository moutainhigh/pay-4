/**
 *  File: 
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.gateway.validate.query;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.pay.gateway.dto.OrderApiQueryRequest;
import com.pay.gateway.dto.OrderApiQueryResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.DateUtil;

/**
 * 验证提交时间
 */
public class QueryTimeCheckRule extends MessageRule {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		OrderApiQueryRequest orderApiQueryRequest = (OrderApiQueryRequest) validateBean;
		OrderApiQueryResponse orderApiQueryResponse = orderApiQueryRequest
				.getOrderApiQueryResponse();

		String mode = orderApiQueryRequest.getMode();
		String beginTime = orderApiQueryRequest.getBeginTime();
		String endTime = orderApiQueryRequest.getEndTime();

		if ("2".equals(mode)) {
			SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
			try {
				Date sDate = sf.parse(beginTime);
				Date eDate = sf.parse(endTime);
				long skipDate = DateUtil.subtraction(eDate, sDate);
				if (skipDate > 15) {
					orderApiQueryResponse.setResultCode(getMessageId());
					orderApiQueryResponse.setResultMsg("日期区间大于15天");
					return false;
				}
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				orderApiQueryResponse.setResultCode(getMessageId());
				orderApiQueryResponse.setResultMsg(getMessage());
				return false;
			}
		} else {
			return true;
		}

	}
}
