/**
 *  File: WithdrawMonthTimesConfigRule.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-6      Sunsea.Li      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.validate.rule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fundout.withdraw.dto.requestproc.WithdrawRequestDTO;
import com.pay.fundout.withdraw.service.order.WithdrawOrderService;
import com.pay.inf.rule.MessageRule;
import com.pay.rm.FoRcLimitFacade;
import com.pay.rm.facade.dto.RCLimitResultDTO;
import com.pay.rm.facade.util.RCLIMITCODE;

/**提现次数验证规则判断--单月提现次数限制
 * @author Sunsea.Li
 *
 */
public class WithdrawMonthTimesConfigRule extends MessageRule {
	
	private FoRcLimitFacade foRcLimitFacade;

	/**
	 * @param foRcLimitFacade the foRcLimitFacade to set
	 */
	public void setFoRcLimitFacade(FoRcLimitFacade foRcLimitFacade) {
		this.foRcLimitFacade = foRcLimitFacade;
	}
	private WithdrawOrderService withdrawOrderService;

	protected transient Log log = LogFactory.getLog(getClass());
	
	/* (non-Javadoc)
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		WithdrawRequestDTO withDrawRequest = (WithdrawRequestDTO)validateBean;
		RCLimitResultDTO rcLimitResultDTO = null;
		if (!withDrawRequest.isBusiness()) {
			rcLimitResultDTO = foRcLimitFacade.getUserRcLimit(RCLIMITCODE.FO_PERSONAL_WITHDRAW.getKey(), null, Long.valueOf(withDrawRequest.getMemberCode())); //个人用户//个人用户
		}else {
			rcLimitResultDTO = foRcLimitFacade.getBusinessRcLimit(RCLIMITCODE.FO_ENTERPRISE_WITHDRAW.getKey(), null, Long.valueOf(withDrawRequest.getMemberCode()));//企业用户
		}
		
		int monthTimes = withdrawOrderService.monthTimesTotal(Long
				.valueOf(withDrawRequest.getMemberCode()),0);
		
		if (monthTimes >= rcLimitResultDTO.getMonthTimes()) {
			withDrawRequest.setMessageId(getMessageId());
			return false;
		} else {
			return true;
		}
	}
	
	public WithdrawOrderService getWithdrawOrderService() {
		return withdrawOrderService;
	}

	public void setWithdrawOrderService(WithdrawOrderService withdrawOrderService) {
		this.withdrawOrderService = withdrawOrderService;
	}
	
}
