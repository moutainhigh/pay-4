package com.pay.txncore.service.refund;

import com.pay.txncore.dto.refund.RefundTransactionServiceParamDTO;
import com.pay.txncore.dto.refund.RefundTransactionServiceResultDTO;

public interface RefundResponseService {

	public RefundTransactionServiceResultDTO buildResult(
			RefundTransactionServiceParamDTO paramDTO);

	/**
	 * 用于创建已经创建订单成功发出的通知
	 * 
	 * @param refundOrderNo
	 * @return
	 */
	//public Long createNoticeByRefundOrderRnTx(GatewayResponseDTO response);

	/**
	 * 用于发送商户请求后台通知 间接调用公共
	 * 
	 * @param responseNo
	 * @return
	 */
	public void sendNoticeByResponseNo(Long responseNo);

	/**
	 * 记录网关退款申请历史
	 * 
	 * @param paramDTO
	 * @return
	 */
	public boolean recordHisttory(RefundTransactionServiceParamDTO paramDTO);

}
