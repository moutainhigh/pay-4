/**
 * 
 */
package com.pay.fo.order.service.batchpay2acct;


/**
 * @author NEW
 *
 */
public interface BatchPay2AcctOrderValidateService {

	/**
	 * 验证批量付款到账户风控限额限次信息
	 * @param memberCode     付款方会员号
	 * @param paymentAmount  付款金额
	 * @param paymentCount   付款笔数
	 * @param rcLimitInfo    风控限额限次信息
	 * @return
	 */
	String validateRCLimitInfo(long memberCode,long paymentAmount,int paymentCount);
}
