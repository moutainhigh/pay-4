package com.pay.pe.service.payment.verification;

import com.pay.pe.dto.DealDto;

/**
 * 验证交易中的异常
 * 简单过程，需要后续扩展
 * 
 */
public interface PaymentVerificationService {

	/**
	 * 在通知商户之前验证是否合法
	 * @param deal
	 * @return true：合法，false：非法
	 */
	boolean doVerifyBeforeNotifyMerchant(DealDto deal);
	
	/**
	 * 返回交易过程中是否最后posting成功，
	 * 即：order成功，deal成功，entry有
	 * @param deal
	 * @return true：OK
	 */
	boolean validatePaymentPostedSuccess(DealDto deal);
	
	
	/**在对DEAL进行记帐前判断其涉及到的帐户是否被冻结
	 * 如果没有冻结什么都不做，如果冻结了就抛错
	 * @param deal
	 * @return
	 */
	boolean validateDealBeforeProcessOrder(DealDto deal);
}
