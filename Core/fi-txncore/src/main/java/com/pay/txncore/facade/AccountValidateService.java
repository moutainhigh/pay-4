/**
 * 
 */
package com.pay.txncore.facade;

import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.txncore.facade.dto.AccountAttribDTO;

/**
 * @author huhb
 * 
 */
public interface AccountValidateService {

	/**
	 * 验证账户属性
	 * 
	 * @param acctAttr
	 * @return
	 */
	public ExceptionCodeEnum validatePartnerAccountAttr(
			AccountAttribDTO acctAttr);

	public ExceptionCodeEnum validateBuyerAccountAttr(AccountAttribDTO acctAttr);

	public boolean isPartnerAllowOutAndIn(AccountAttribDTO acctAttr);

	public boolean isPayerAllowIn(AccountAttribDTO accAttr);
}
