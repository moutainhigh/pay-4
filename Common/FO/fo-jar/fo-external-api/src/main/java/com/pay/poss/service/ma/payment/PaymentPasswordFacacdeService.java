/**
 *  File: PaymentPasswordFacacdeService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-3      bill_peng     Changes
 *  
 *
 */
package com.pay.poss.service.ma.payment;

import com.pay.acc.service.account.dto.MaResultDto;

/**
 * @author bill_peng
 *
 */
public interface PaymentPasswordFacacdeService {

	
	/**
	 * 验证个人用户支付密码
	 * @param memberCode
	 * @param accountType
	 * @param pwd
	 * @return
	 */
	MaResultDto validatePaymentPwd(long memberCode,int accountType,String pwd);
	/**
	 * 验证企业操作员支付密码
	 * @param memberCode
	 * @param accountType
	 * @param pwd
	 * @param operatorID
	 * @return
	 */
	MaResultDto validatePaymentPwd(long memberCode,int accountType,String pwd,Long operatorID);

}
