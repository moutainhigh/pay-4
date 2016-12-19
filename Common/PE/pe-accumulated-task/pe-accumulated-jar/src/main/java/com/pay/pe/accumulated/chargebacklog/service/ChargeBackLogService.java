package com.pay.pe.accumulated.chargebacklog.service;

import com.pay.pe.accumulated.chargebacklog.dto.ChargeBackLogDto;
import com.pay.pe.service.PaymentResponseDto;

/**
 * 
 * @ClassName: ChargeBackLogService
 * @Description:
 * @author cf
 * @date Mar 8, 2012 4:56:06 PM
 * 
 */
public interface ChargeBackLogService {

	public boolean saveChargeBackRnTx(ChargeBackLogDto chargeBackLogDto,
			PaymentResponseDto calFeeRespone);
}
