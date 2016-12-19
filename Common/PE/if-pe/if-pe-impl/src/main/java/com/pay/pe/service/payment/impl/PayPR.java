
package com.pay.pe.service.payment.impl;

import com.pay.util.Money;
import com.pay.pe.service.payment.AbstractAcctCodeGenerater;
import com.pay.pe.service.payment.AbstractPostingRule;
import com.pay.pe.service.payment.common.AccountingEvent;

public final class PayPR extends AbstractPostingRule {

	/**
	 * 得到帐号代码.
	 */
	@Override
	public String generateAccountCode(final AccountingEvent accountingEvent) {
		
		AbstractAcctCodeGenerater generater = this
				.getAcctCodeGenerater(accountingEvent);
		String acctcode = generater.generateSpecAcct(getAccountCode(),
				accountingEvent.getAccountingCurrencyNum());

		return acctcode;
	}

	/**
	 * 
	 */
	@Override
	public Boolean isValid(final AccountingEvent accountingEvent) {
		return true;
	}

	/**
	 * 计算金额.
	 */
	@Override
	public Money calculateAmount(final AccountingEvent accountingEvent) {
		return accountingEvent.getAmount();
	}

}
