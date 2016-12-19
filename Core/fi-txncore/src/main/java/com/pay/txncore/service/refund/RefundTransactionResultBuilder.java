package com.pay.txncore.service.refund;

import com.pay.txncore.dto.refund.RefundTransactionServiceParamDTO;
import com.pay.txncore.dto.refund.RefundTransactionServiceResultDTO;

/**
 * @Description 退款结果构建器
 * @project fi-Refund
 * @file RefundTransactionResultBuilder.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 hna Corporation. All rights reserved. Date
 *          Author Changes 2010-10-2 Sean.Chen Create
 */
public interface RefundTransactionResultBuilder {

	/**
	 * 按请求参数构建 结果对象
	 * 
	 * @param paramDTO
	 * @return ResultDTO
	 * @throws Exception
	 */
	public RefundTransactionServiceResultDTO buildFailResult(
			RefundTransactionServiceParamDTO paramDTO) throws Exception;

	/**
	 * 按处理参数构建 结果对象
	 * 
	 * @param paramDTO
	 * @return ResultDTO
	 * @throws Exception
	 */
	public RefundTransactionServiceResultDTO buildSuccessResult(
			RefundTransactionServiceParamDTO paramDTO) throws Exception;
}
