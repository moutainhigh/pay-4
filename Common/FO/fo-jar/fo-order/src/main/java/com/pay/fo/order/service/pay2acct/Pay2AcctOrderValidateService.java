/**
 * 
 */
package com.pay.fo.order.service.pay2acct;

import com.pay.rm.facade.dto.RCLimitResultDTO;

/**
 * @author NEW
 *
 */
public interface Pay2AcctOrderValidateService {

	/**
	 * 验证单笔付款到账户风控限额限次信息
	 * @param memberCode     付款方会员号
	 * @param paymentAmount  付款金额
	 * @param rcLimitInfo    风控限额限次信息
	 * @return
	 */
	String validateRCLimitInfo(long memberCode,long paymentAmount,RCLimitResultDTO rcLimitInfo);
}
