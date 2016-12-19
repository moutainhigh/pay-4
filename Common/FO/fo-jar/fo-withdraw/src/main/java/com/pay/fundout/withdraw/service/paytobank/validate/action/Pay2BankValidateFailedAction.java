/**
 *  File: Pay2BankValidateFailedAction.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-4      bill_peng     Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.paytobank.validate.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fundout.withdraw.service.paytobank.validate.rule.Pay2BankDTO;
import com.pay.inf.rule.AbstractAction;
import com.pay.poss.service.ma.pay2bank.Pay2BankValidateException;
import com.pay.util.StringUtil;

/**
 * @author bill_peng
 * 
 */
public class Pay2BankValidateFailedAction extends AbstractAction {

	protected transient Log log = LogFactory.getLog(getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ruleengine.AbstractAction#doExecute(java.lang.Object)
	 */
	@Override
	protected void doExecute(Object validateBean) throws Exception {
		Pay2BankDTO dto = (Pay2BankDTO) validateBean;
		if (StringUtil.isNull(dto.getMessageId())) {
			dto.setMessageId(Pay2BankValidateException.UN_DEFINE_EXCEPTIONCODE
					.getCode());
		}
		log.info("payeeBankAccount:" + dto.getPayeeBankAccount()
				+ ",validate pay2bank info failed,the error code:"
				+ dto.getMessageId());
	}

}
