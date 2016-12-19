package com.pay.txncore.service.refund;

import com.pay.fi.exception.refund.RefundException;
import com.pay.txncore.dto.refund.RefundTransactionServiceParamDTO;

/**
 * @Description 退款处理服务（内部接口）
 * @project gateway-pay
 * @file RefundService.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Author
 *          Changes 2010-10-2 Sean.Chen Create
 */
public interface RefundExecuteService {

	public void runRefund(RefundTransactionServiceParamDTO paramDTO);

	public void runRefundSuccessRnTx(Long refundOrderNo) throws Exception;

	public void runRefundFailRnTx(Long tradeOrderNo, Long refundOrderNo,
			Long refundAmount, String errorCode) throws RefundException;

	public void runCallDepositBack(RefundTransactionServiceParamDTO paramDTO);

}
