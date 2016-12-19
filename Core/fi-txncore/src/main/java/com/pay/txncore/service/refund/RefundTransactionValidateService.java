package com.pay.txncore.service.refund;

import com.pay.txncore.dto.refund.RefundTransactionServiceParamDTO;

/**
 * @Description 退款验证服务
 * @project gateway-pay
 * @file RefundService.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-10-02 Sean.Chen Update Author Changes 2010-10-26
 *          mole_zou Update Author Changes 2011-04-24 fred.feng Update
 */
public interface RefundTransactionValidateService {

	/**
	 * 退款基础服务处理
	 * 
	 * @param paramDTO
	 */
	void validate(RefundTransactionServiceParamDTO paramDTO);
}
