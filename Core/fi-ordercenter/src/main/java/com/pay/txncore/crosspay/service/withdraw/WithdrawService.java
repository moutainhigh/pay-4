/**
 * 
 */
package com.pay.txncore.crosspay.service.withdraw;

import java.util.List;

import com.pay.txncore.crosspay.model.PartnerWithdrawOrder;

/**
 * @author chaoyue
 * 
 */
public interface WithdrawService {

	/**
	 * 统计商户可提现金额
	 * 
	 * @param partnerId
	 * @return
	 */
	Long withdrawAbleAmount(Long partnerId) throws Exception;

	/**
	 * 统计历史提现金额
	 * 
	 * @param partnerId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	Long sumWithdrawAmount(Long partnerId, String startDate, String endDate);

	/**
	 * 商户提现申请
	 * 
	 * @param partnerId
	 * @param amount
	 */
	void withdraw(Long partnerId, Long amount) throws Exception;

	/**
	 * 提现历史
	 * 
	 * @param partnerId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<PartnerWithdrawOrder> queryWithdrawOrder(Long partnerId,
			String startDate, String endDate);

}
