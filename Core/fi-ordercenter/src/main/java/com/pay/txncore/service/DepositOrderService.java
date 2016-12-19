/**
 * 
 */
package com.pay.txncore.service;

import com.pay.txncore.dto.DepositOrderDTO;

/**
 * 充值订单和协议流水记录相关操作
 * 
 * @author huhb
 * 
 */
public interface DepositOrderService {

	/**
	 * 保存充值订单
	 * 
	 * @param depositOrderDTO
	 * @return
	 */
	Long saveDepositOrderRnTx(DepositOrderDTO depositOrderDTO);

	/**
	 * 更新充值订单
	 * 
	 * @param depositOrderDTO
	 * @return
	 */
	boolean updateDepositOrderRnTx(DepositOrderDTO depositOrderDTO);

	/**
	 * 根据充值订单号查询
	 * 
	 * @param depositOrderNo
	 * @return
	 */
	DepositOrderDTO queryByDepositOrderNo(final Long depositOrderNo);

	/**
	 * 根据支付订单号查询
	 * 
	 * @param paymentOrderNo
	 * @return
	 */
	DepositOrderDTO queryByPaymentOrderNo(Long paymentOrderNo);
}
