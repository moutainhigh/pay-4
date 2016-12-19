/**
 *  File: SelectBankOutTypeAction.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-12      zliner      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.validate.action;

import com.pay.fundout.withdraw.dto.requestproc.WithdrawRequestDTO;
import com.pay.inf.rule.AbstractAction;

/**
 * 选择出款方式
 * @author zliner
 */
public class SelectBankOutTypeAction extends AbstractAction {
	
//	private WithdrawRuleService withdrawRuleService;
	
	/**
	 * 规则验证中规则执行选择出款银行方式处理逻辑
	 * @param validateBean        待验证规则对象 
	 * @throws Exception          验证中抛出运行时异常
	 */
	protected void doExecute(Object validateBean) throws Exception {
		WithdrawRequestDTO withdrawRequest = (WithdrawRequestDTO)validateBean;
//		Map<String,String> map = new HashMap<String,String>();
//		map.put("TO_BANK_CODE", withdrawRequest.getBankKy());//目标银行
//		map.put("SIN_MAX_VALUE", withdrawRequest.getApplyWithdrawAmount().toString());//提现金额
//		if (withdrawRequest.isBusiness()) {
//			map.put("WITHDRAW_TYPE", "1");//1:对公
//		}else {
//			map.put("WITHDRAW_TYPE", "2");//2:对私
//		}
//		map.put("BANK_ACCT_TYPE", withdrawRequest.getBankAcctType().toString());
//		int type = withdrawRuleService.getWithdrawType(map);
		
		//出款方式    手工=1 
		withdrawRequest.setWithdrawType(1);
	}
	
//	public void setWithdrawRuleService(WithdrawRuleService withdrawRuleService) {
//		this.withdrawRuleService = withdrawRuleService;
//	}
}
