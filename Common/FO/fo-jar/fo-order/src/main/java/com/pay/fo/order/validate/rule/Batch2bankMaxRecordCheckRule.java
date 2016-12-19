/**
 *  File: MaxRecordCheckRule.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.fo.order.validate.rule;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

import com.pay.fo.order.validate.BatchPaymentRequest;
import com.pay.fo.order.validate.BatchPaymentResponse;
import com.pay.fundout.util.FreeMarkParseUtil;
import com.pay.inf.rule.MessageRule;

/**
 * 验证批次文件最大记录数
 */
public class Batch2bankMaxRecordCheckRule extends MessageRule {

	private Integer maxRecords;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {

		BatchPaymentRequest request = (BatchPaymentRequest) validateBean;
		Assert.notNull(request, "batch2bank request must not null");
		BatchPaymentResponse response = request.getBatchPaymentResponse();

		int rows = response.getDetails().size();

		if (rows > maxRecords) {
			String errorMsg = getMessageId();
			Map paraMap = new HashMap();
			paraMap.put("maxRecords", maxRecords);
			request.getBatchPaymentResponse().setErrorMsg(
					FreeMarkParseUtil.parseTemplate(errorMsg, paraMap));
			return true;
		} else {
			return true;
		}
	}

	public void setMaxRecords(Integer maxRecords) {
		this.maxRecords = maxRecords;
	}
}
