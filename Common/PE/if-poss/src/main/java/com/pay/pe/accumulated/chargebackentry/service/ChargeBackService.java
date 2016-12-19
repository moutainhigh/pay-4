package com.pay.pe.accumulated.chargebackentry.service;

import com.pay.acc.exception.MaAccountQueryUntxException;
import com.pay.acc.service.account.dto.CalFeeReponseDto;

public interface ChargeBackService {

	public CalFeeReponseDto buildUpdateBalanceRequest(Long voucherCode)
			throws MaAccountQueryUntxException, NumberFormatException;

	public boolean doUpdateBalance(Long voucherCode, Integer dealType);

}
