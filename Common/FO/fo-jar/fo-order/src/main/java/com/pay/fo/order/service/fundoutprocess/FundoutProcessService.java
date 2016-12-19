/**
 * 
 */
package com.pay.fo.order.service.fundoutprocess;

import com.pay.fo.order.dto.base.FundoutOrderDTO;

/**
 * @author NEW
 *
 */
	
public interface FundoutProcessService {
	
	/**
	 * 出款处理成功
	 * @param order
	 */
	void foProcessSuccess(FundoutOrderDTO order);
	/**
	 * 出款处理失败
	 * @param order
	 */
	void foProcessFail(FundoutOrderDTO order);
	
	/**
	 * 退票处理
	 * @param order
	 */
	void refundOrder(FundoutOrderDTO order);

}
