/**
 *  File: PaymentPasswordFacacdeServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-3      bill_peng     Changes
 *  
 *
 */
package com.pay.poss.service.ma.payment.impl;

import com.pay.acc.service.account.dto.MaResultDto;
import com.pay.poss.service.adapter.AbstractExternalAdapter;
import com.pay.poss.service.ma.payment.PaymentPasswordFacacdeService;

/**
 * @author bill_peng
 * 
 */
public class PaymentPasswordFacacdeServiceImpl extends AbstractExternalAdapter
		implements PaymentPasswordFacacdeService {

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.pay.poss.service.ma.payment.PaymentPasswordFacacdeService#
	 * validatePaymentPwd(long, int, java.lang.String)
	 */
	@Override
	public MaResultDto validatePaymentPwd(long memberCode, int accountType,
			String pwd) {
		return this.validatePaymentPwd(memberCode, accountType, pwd, null);
	}

	@Override
	public MaResultDto validatePaymentPwd(long memberCode, int accountType,
			String pwd, Long operatorID) {
		MaResultDto result = null;
		try {
			result = accountInfoService.doVerifyPayPassword(memberCode,
					accountType, pwd, operatorID);
		} catch (Exception e) {
			log.error("call accountInfoService.doVerifyPayPassword failed", e);
		}

		return result;
	}
}
