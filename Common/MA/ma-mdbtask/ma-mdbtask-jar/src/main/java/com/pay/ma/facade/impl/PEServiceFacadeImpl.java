package com.pay.ma.facade.impl;

import com.pay.ma.facade.PEServiceFacade;
import com.pay.pe.service.PEService;
import com.pay.pe.service.PaymentResponseDto;

public class PEServiceFacadeImpl implements PEServiceFacade {

	private PEService peService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.ma.facade.PEServiceFacade#accountCallFeeReponse(com.woyo
	 * .pay.pe.service.CalFeeReponse)
	 */
	@Override
	public boolean accountCallFeeReponse(PaymentResponseDto paymentResponse) throws Exception {
		boolean result = this.peService.accounting(paymentResponse);
		return result;
	}
	
	/**
	 * @param peService
	 *            the peService to set
	 */
	public void setPeService(PEService peService) {
		this.peService = peService;
	}

}
