package com.pay.txncore.service.refund;

import com.pay.txncore.dto.refund.RefundApplyDTO;

public interface RefundApplyService {

	/**
	 * 商户提交退款申请
	 * 
	 * @param refundApplyDTO
	 * @return
	 * @throws Exception
	 */
	public Long submitRefundApply(RefundApplyDTO refundApplyDTO)
			throws Exception;

	/**
	 * 退款申请审核通过
	 * 
	 * @param refundApplyDTO
	 * @return
	 */
	public boolean auditRefundApply(RefundApplyDTO refundApplyDTO,
			String requestContext);

	/**
	 * 退款申请拒绝
	 * 
	 * @param refundApplyDTO
	 * @return
	 */
	public boolean refusalRefundApply(RefundApplyDTO refundApplyDTO);

	/**
	 * 获取明细
	 * 
	 * @param refundApplyNo
	 * @return
	 */
	public RefundApplyDTO queryRefundApply(Long refundApplyNo);

}
