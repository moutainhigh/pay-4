/**
 *  File: BankCardCheckRule.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-20      bill_peng     Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.paytobank.validate.rule;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.pay.inf.rule.MessageRule;
import com.pay.poss.service.ma.pay2bank.Pay2BankValidateService;
import com.pay.util.StringUtil;

/**
 * @author bill_peng
 *
 */
public class BankCardCheckRule extends MessageRule {
	
	private Pay2BankValidateService pay2BankValidateService;
	
	private final static String BANKACCT_PATTERN  ="^[0-9]+$";

	public void setPay2BankValidateService(
			Pay2BankValidateService pay2BankValidateService) {
		this.pay2BankValidateService = pay2BankValidateService;
	}



	/* (non-Javadoc)
	 * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
	 */
	@Override
	protected boolean makeDecision(Object validateBean) throws Exception {
		Pay2BankDTO dto = (Pay2BankDTO)validateBean;
		String msgCode = "";
		if(StringUtil.isNull(dto.getPayeeBankAccount())){
			msgCode = "请输入收款方银行账号";//
		}else if(StringUtil.isNull(dto.getPayeeBankCode())){
			msgCode = "请选则开户行";
		}else if(StringUtil.isNull(dto.getTradeType())){
			msgCode = "请选择账户类型";
		}else if(!checkBankAcctId(dto.getPayeeBankAccount())){
			msgCode = "银行账号不正确，银行账号必须由数字组成";
		}else if(dto.getPayeeBankAccount().length()<8 || dto.getPayeeBankAccount().length()>32){
			msgCode = "银行账号必须由8到32位的数字组成";
		}else if(dto.getTradeType().intValue()==0){
			msgCode = pay2BankValidateService.validateCardBin(dto.getPayeeBankAccount(),dto.getPayeeBankCode());
		}
		if(StringUtil.isNull(msgCode)){
			return true;
		}
		dto.setMessageId(msgCode);
		return false;
	}
	
	private boolean checkBankAcctId(String bankAcctId) {
		boolean ret = false;
		try {
			Pattern p = Pattern.compile(BANKACCT_PATTERN);
			Matcher m = p.matcher(bankAcctId);
			ret = m.matches();
		} catch (Exception e) {
			ret = false;
		}
		return ret;
	}

}
