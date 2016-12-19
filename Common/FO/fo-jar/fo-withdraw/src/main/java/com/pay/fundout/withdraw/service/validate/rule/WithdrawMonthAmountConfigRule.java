/**
 *  File: WithdrawMonthConfigRule.java
 *  Description:
 *  Copyright 2010-2010 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  2010-9-17   Sandy_Yang  create
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

/**
 * 提现金额验证规则判断--单月提现限制
 * @author Sandy_Yang
 */
public class WithdrawMonthAmountConfigRule extends MessageRule {
	
	private FoRcLimitFacade foRcLimitFacade;

	/**
	 * @param foRcLimitFacade the foRcLimitFacade to set
	 */
	public void setFoRcLimitFacade(FoRcLimitFacade foRcLimitFacade) {
		this.foRcLimitFacade = foRcLimitFacade;
	}
	private WithdrawOrderService withdrawOrderService;
	
	
	protected transient Log log = LogFactory.getLog(getClass());
	
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		WithdrawRequestDTO withDrawRequest = (WithdrawRequestDTO)validateBean;
		RCLimitResultDTO rcLimitResultDTO = null;
		if (!withDrawRequest.isBusiness()) {
			rcLimitResultDTO = foRcLimitFacade.getUserRcLimit(RCLIMITCODE.FO_PERSONAL_WITHDRAW.getKey(), null, Long.valueOf(withDrawRequest.getMemberCode())); //个人用户//个人用户
		}else {
			rcLimitResultDTO = foRcLimitFacade.getBusinessRcLimit(RCLIMITCODE.FO_ENTERPRISE_WITHDRAW.getKey(), null, Long.valueOf(withDrawRequest.getMemberCode()));//企业用户
		}
		
		Long monthAmount = withdrawOrderService.monthMoneyTotal(
				Long.valueOf(withDrawRequest.getMemberCode()));
		monthAmount += withDrawRequest.getApplyWithdrawAmount();//当月和本次提现金额相加
		
		
		if(monthAmount > rcLimitResultDTO.getMonthLimit()) {
			withDrawRequest.setMessageId(getMessageId());
			return false;
		}else {
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
