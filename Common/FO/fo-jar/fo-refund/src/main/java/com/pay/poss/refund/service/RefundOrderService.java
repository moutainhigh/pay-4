/**
 * 
 */
package com.pay.poss.refund.service;

import com.pay.poss.refund.model.RefundOrderD;
import com.pay.poss.refund.model.RefundOrderM;
import com.pay.poss.refund.model.RefundWorkorder;

/**
 * @author chaoyue
 *
 */
public interface RefundOrderService {

	/**
	 * 
	 * @param mDto
	 * @return
	 */
	Long saveRefundMRnTx(RefundOrderM mDto);

	/**
	 * 
	 * @param dDto
	 * @return
	 */
	Long saveRefundDRnTx(RefundOrderD dDto);

	/**
	 * 
	 * @param orderKy
	 * @param status
	 * @return
	 */
	boolean updateRefundMStatusRnTx(Long orderKy, Integer status);

	/**
	 * 
	 * @param orderKy
	 * @param status
	 * @return
	 */
	boolean updateRefundDStatusRnTx(Long orderKy, Integer status);

	/**
	 * 
	 * @param workOrder
	 * @return
	 */
	Long saveRefundWorkorderRnTx(RefundWorkorder workOrder);

	/**
	 * 
	 * @param workorderKy
	 * @param status
	 * @return
	 */
	boolean updateWorkorderStatusRnTx(Long workorderKy, Integer status);
}
