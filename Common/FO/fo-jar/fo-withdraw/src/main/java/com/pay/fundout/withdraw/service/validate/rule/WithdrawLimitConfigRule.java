/**
 *  File: WithdrawLimitConfigRule
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-12      zliner      Changes
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
 * 提现限制配置验证规则 --单日提现总限额
 * @author zliner
 *
 */
public class WithdrawLimitConfigRule extends MessageRule {
	
	
	private WithdrawOrderService withdrawOrderService;
	
	protected transient Log log = LogFactory.getLog(getClass());
	private FoRcLimitFacade foRcLimitFacade;

	/**
	 * @param foRcLimitFacade the foRcLimitFacade to set
	 */
	public void setFoRcLimitFacade(FoRcLimitFacade foRcLimitFacade) {
		this.foRcLimitFacade = foRcLimitFacade;
	}
	/**
	 * 提现金额验证规则判断
	 * @param validateBean        待验证规则对象
	 * @return boolean            true表示验证规则通过，false表示该验证规则未通过
	 * @throws Exception          验证中抛出运行时异常
	 */
	protected boolean makeDecision(Object validateBean) throws Exception {
		WithdrawRequestDTO withDrawRequest = (WithdrawRequestDTO)validateBean;
		RCLimitResultDTO rcLimitResultDTO = null;
		if (!withDrawRequest.isBusiness()) {
			rcLimitResultDTO = foRcLimitFacade.getUserRcLimit(RCLIMITCODE.FO_PERSONAL_WITHDRAW.getKey(), null, Long.valueOf(withDrawRequest.getMemberCode())); //个人用户//个人用户
		}else {
			rcLimitResultDTO = foRcLimitFacade.getBusinessRcLimit(RCLIMITCODE.FO_ENTERPRISE_WITHDRAW.getKey(), null, Long.valueOf(withDrawRequest.getMemberCode()));//企业用户//企业用户
		}
		
		Long dayAmount = withdrawOrderService.dayMoneyTotal(Long.valueOf(withDrawRequest.getMemberCode()));
		dayAmount += withDrawRequest.getApplyWithdrawAmount();//单日提现总额和本次提现金额相加
		
		if(dayAmount > rcLimitResultDTO.getDayLimit()) {
			withDrawRequest.setMessageId(getMessageId());
			return false;
		}else {
			return true;
		}
	}
	
	public void setWithdrawOrderService(final 
			WithdrawOrderService param) {
		this.withdrawOrderService = param;
	}
	
}
