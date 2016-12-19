/**
 * 
 */
package com.pay.txncore.service.refund;

import com.pay.txncore.dto.refund.RefundTransactionServiceParamDTO;
import com.pay.txncore.dto.refund.RefundTransactionServiceResultDTO;

/**
 * @Description 退款服务
 * @project gateway-pay
 * @file RefundService.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved. Date
 *          Author Changes 2010-10-2 Sean.Chen Update
 */
public interface RefundTransactionService {

	/**
	 * 交易退款主流程
	 * 
	 * @param paramDTO
	 * @return
	 * @throws RefundException
	 */
	public RefundTransactionServiceResultDTO refund(
			RefundTransactionServiceParamDTO paramDTO);

}
