/**
 *  File: SelectOutBankAction.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-12      zliner      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.validate.action;

import java.util.HashMap;
import java.util.Map;

import com.pay.fundout.channel.service.configbank.ConfigBankService;
import com.pay.fundout.withdraw.dto.requestproc.WithdrawRequestDTO;
import com.pay.inf.rule.AbstractAction;

/**
 * 选择出款银行
 * @author zliner
 */
public class SelectOutBankAction extends AbstractAction {
	
	private ConfigBankService configBankService;
	/**
	 * 规则验证中规则执行选择出款银行处理逻辑
	 * @param validateBean        待验证规则对象 
	 * @throws Exception          验证中抛出运行时异常
	 */
	protected void doExecute(Object validateBean) throws Exception {
		WithdrawRequestDTO withdrawRequest = (WithdrawRequestDTO)validateBean;
		Map<String,Object> map = new HashMap<String,Object>();
		
		//目的银行编号
		map.put("targetBankId", withdrawRequest.getBankKy());
		//出款方式
		map.put("foMode", "1");
		//出款业务
		map.put("fobusiness", "0");//个人提现0
		
		String outBankCode = configBankService.queryFundOutBank2Withdraw(map);
		if (outBankCode == null || "".equals(outBankCode)) {
			outBankCode = withdrawRequest.getBankKy();
		}
		withdrawRequest.setWithdrawBankCode(outBankCode);
	}
	
	public void setConfigBankService(ConfigBankService configBankService) {
		this.configBankService = configBankService;
	}

}
