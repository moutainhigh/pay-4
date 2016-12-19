/**
 * 
 */
package com.pay.fo.order.service.fundoutrefund;

import com.pay.fo.order.dto.base.FundoutRefundOrderDTO;
import com.pay.inf.exception.AppException;

/**
 * @author NEW
 *
 */
public interface FundoutRefundOrderProcessService {
	
	
	/**
	 * 创建退票订单
	 * @param order 需要保存的订单信息
	 * @return
	 * @throws AppException 
	 */
	void createOrderRnTx(FundoutRefundOrderDTO order) throws AppException;
	
	/**
	 * 审核通过
	 * @param orderId 需退票的订单号
	 * @param auditor    审核员
	 * @param auditRemark 审核备注
	 * @throws AppException 
	 */
	void auditPassRequestRdTx(Long orderId,String auditor,String auditRemark) throws AppException;
	
	/**
	 * 审核拒绝
	 * @param orderId 需退票的订单号
	 *  @param auditor    审核员
	 * @param auditRemark 审核备注
	 * @throws AppException 
	 */
	void auditRejectRequestRdTx(Long orderId,String auditor,String auditRemark) throws AppException;

}
