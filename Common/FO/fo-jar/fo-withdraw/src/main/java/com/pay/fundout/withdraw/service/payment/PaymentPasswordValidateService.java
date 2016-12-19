/**
 *  File: PaymentPasswordValidateService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-3      bill_peng     Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.payment;


/**
 * @author bill_peng
 *
 */
public interface PaymentPasswordValidateService {

	/**
	 * 验证支付密码
	 * @param memberCode
	 * @param accountType
	 * @param pwd
	 * @return
	 */
	boolean validatePaymentPwd(long memberCode,int accountType,String pwd)throws ValidatePaymentPasswordException;
	
	/**
	 * 验证支付密码，返回详细信息
	 * @param memberCode
	 * @param accountType
	 * @param pwd
	 * @return
	 * @throws ValidatePaymentPasswordException
	 */
	String validatePaymentPassword(long memberCode,int accountType,String pwd);
	
	/**
	 * 验证支付密码，返回详细信息
	 * @param memberCode
	 * @param accountType
	 * @param op
	 * @param pwd
	 * @return
	 * @throws ValidatePaymentPasswordException
	 */
	String validatePaymentPassword(long memberCode,int accountType,Long op,String pwd);
	
}
