/**
 *  File: PaymentPasswordValidateServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-3      bill_peng     Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.payment.impl;

import com.pay.acc.service.account.dto.MaResultDto;
import com.pay.acc.service.account.dto.VerifyResultDto;
import com.pay.fundout.withdraw.service.payment.PaymentPasswordValidateService;
import com.pay.fundout.withdraw.service.payment.ValidatePaymentPasswordException;
import com.pay.poss.service.ma.payment.PaymentPasswordFacacdeService;
import com.pay.util.StringUtil;

/**
 * @author bill_peng
 *
 */
public class PaymentPasswordValidateServiceImpl implements
		PaymentPasswordValidateService {
	
	
	private PaymentPasswordFacacdeService paymentPasswordFacacdeService;
	
	
	
	
	public void setPaymentPasswordFacacdeService(
			PaymentPasswordFacacdeService paymentPasswordFacacdeService) {
		this.paymentPasswordFacacdeService = paymentPasswordFacacdeService;
	}



	/* (non-Javadoc)
	 * @see com.pay.fundout.withdraw.service.payment.PaymentPasswordValidateService#validatePaymentPwd(long, int, java.lang.String)
	 */
	@Override
	public boolean validatePaymentPwd(long memberCode, int accountType,
			String pwd) throws ValidatePaymentPasswordException{
		
		MaResultDto maResult = paymentPasswordFacacdeService.validatePaymentPwd(memberCode, accountType, pwd);
		if(null==maResult){
			return false;
		}
		int status = maResult.getResultStatus();
		if(status==1){
			return true;
		}
		if(status==3){
			throw new ValidatePaymentPasswordException();
		}
		
		return false;
	}



	@Override
	public String validatePaymentPassword(long memberCode,
			int accountType, String pwd) {
		return validatePaymentPassword(memberCode, accountType, null, pwd);
	}



	@Override
	public String validatePaymentPassword(long memberCode, int accountType,
			Long op, String pwd) {
		
		MaResultDto maResult = null;
		if(StringUtil.isNull(op)){
			maResult = paymentPasswordFacacdeService.validatePaymentPwd(memberCode, accountType, pwd);
		}else{
			maResult = paymentPasswordFacacdeService.validatePaymentPwd(memberCode, accountType, pwd, op);
		}
		String errorMsg1 =  "支付密码不正确，您还有num次机会";
		String errorMsg2 =  "支付密码连续输错3次，账户已被锁定，请mm分钟后再继续操作";
		String errorMsg3 =  "账户异常，请联系客服";
		String errorMsg8 = "支付密码不正确";
		if(null==maResult){
			return errorMsg1.replaceAll("num", "3");
		}
		int status = maResult.getResultStatus();
		VerifyResultDto verifyResult = (VerifyResultDto)maResult.getObject();
		if(status==1){
			return null;
		}else if(status==3){
			return errorMsg2.replaceAll("mm", String.valueOf(verifyResult.getLeavingMinute()));
		}else if(status==2){
			return errorMsg1.replaceAll("num", String.valueOf(verifyResult.getLeavingTime()));
		}else if(status==8){
			return errorMsg8;
		}else{
			return errorMsg3;
		}
	}

}
