package com.pay.txncore.service.refund;

/**
 * author: nico.shao
 */

import com.pay.txncore.dto.refund.RefundOrderExtendDTO;
import com.pay.txncore.dto.refund.RefundOrderStatusChangeLogDTO;
import com.pay.txncore.model.RefundFeeOrder;


/**
 *
 */
public interface RefundOrderExtendService {

	/**
	 * 插入附属表RefundOrderExtend
	 */
	Long createRefundOrderExtend(RefundOrderExtendDTO refundOrderExtendDTO);

	/**
	 * 查询附属表RefundOrderExtend
	 */
	RefundOrderExtendDTO findByRefundOrderNo(Long refundOrderNo);
	
	boolean updateRefundOrderExtendCount(RefundOrderExtendDTO refundOrderExtendDTO);
	/*
	* 插入日志表
	*/
	Long createRefundOrderStatusChangeLog(RefundOrderStatusChangeLogDTO refundOrderStatusChangeLogDTO);
	
	
	void createRefundFeeOrder(RefundFeeOrder refundFeeOrder);	
}
