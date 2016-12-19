/**
 *  File: ValidateServiceImpl.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-12   terry     Create
 *
 */
package com.pay.service.impl;

import com.pay.inf.rule.RuleEngine;
import com.pay.service.ValidateRequest;
import com.pay.service.ValidateService;

/**
 * 
 */
public class ValidateServiceImpl implements ValidateService {

	private RuleEngine ruleEngine;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.fo.order.validate.ValidateService#validate(java.lang.Object)
	 */
	@Override
	public String validate(ValidateRequest request) throws Exception {
		ruleEngine.processRequest(request);
		return request.getValidateReturnCode();
	}

	public void setRuleEngine(RuleEngine ruleEngine) {
		this.ruleEngine = ruleEngine;
	}

}
