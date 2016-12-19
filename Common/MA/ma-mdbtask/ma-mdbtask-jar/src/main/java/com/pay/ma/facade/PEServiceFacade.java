/**
 * 
 */
package com.pay.ma.facade;

import com.pay.pe.service.PaymentResponseDto;

/**
 * pe记账接口封装类
 * 
 * @author Administrator
 * 
 */
public interface PEServiceFacade {

	/**
	 * 对记账进行封装
	 * 
	 * @param calFeeReponse
	 * @return
	 * @throws Exception 
	 */
	public boolean accountCallFeeReponse(PaymentResponseDto paymentResponse) throws Exception;

	

}
