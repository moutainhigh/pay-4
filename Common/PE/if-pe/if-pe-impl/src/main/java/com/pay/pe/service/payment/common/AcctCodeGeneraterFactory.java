package com.pay.pe.service.payment.common;

import org.springframework.util.Assert;

import com.pay.pe.service.payment.AbstractAcctCodeGenerater;
import com.pay.pe.service.payment.impl.DefaultAcctCodeGenerater;
import com.pay.pe.service.payment.impl.FxAcctCodeGenerater;
import com.pay.util.CurrencyCodeEnum;

public class AcctCodeGeneraterFactory {

	private AcctCodeGeneraterFactory() {

	}

	public static AbstractAcctCodeGenerater createGenerater(
			final AccountingEvent event) {

		Assert.notNull(event, "AccountingEvent can not be null");
		Assert.notNull(event.getAccountingCurrencyCode(),
				"currencyCode can not be null");

		CurrencyCodeEnum fxCurrency = CurrencyCodeEnum.getCurrencyByCode(event
				.getAccountingCurrencyCode());
		Assert.notNull(fxCurrency, "unknow currency");

		if (CurrencyCodeEnum.CNY.equals(fxCurrency)) {
			return new DefaultAcctCodeGenerater();
		} else {
			return new FxAcctCodeGenerater();
		}
	}

}
