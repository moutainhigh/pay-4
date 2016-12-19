/**
 *  File: MemberAcctSpecificationRule.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-12      zliner      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.validate.rule;

import java.util.HashMap;
import java.util.Map;

import com.pay.fundout.channel.service.configbank.ConfigBankService;
import com.pay.fundout.withdraw.dto.requestproc.WithdrawRequestDTO;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

/**
 * 是否有出款渠道验证
 * @author zliner
 */
public class BankChannelRule extends MessageRule {
	private ConfigBankService configBankService;
	
	/**
	 * 是否有出款渠道验证
	 * @param validateBean        待验证规则对象
	 * @return boolean            true表示有出款渠道，false表示没有出款渠道
	 * @throws Exception          验证中抛出运行时异常
	 */
	protected boolean makeDecision(Object validateBean) throws Exception {
		WithdrawRequestDTO withDrawRequest = (WithdrawRequestDTO)validateBean;
		Map<String,Object> map = new HashMap<String,Object>();
		//目的银行编号
		map.put("targetBankId",withDrawRequest.getBankKy());
		//出款方式
		map.put("foMode", "1");//暂时写死为1手工,以后直连接入在进行修改
		//出款业务
		map.put("fobusiness", String.valueOf(withDrawRequest.getBusiType()));//提现
		String outBankCode = configBankService.queryFundOutBank2Withdraw(map);
		if(StringUtil.isNull(outBankCode) ){
			withDrawRequest.setMessageId(getMessageId());
			return false;
		}
		return true;
	}

	public void setConfigBankService(ConfigBankService configBankService) {
		this.configBankService = configBankService;
	}
	
	
	
}
