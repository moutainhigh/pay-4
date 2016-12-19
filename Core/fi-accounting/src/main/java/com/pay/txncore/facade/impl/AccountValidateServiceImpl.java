/**
 * 
 */
package com.pay.txncore.facade.impl;

import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.txncore.facade.AccountValidateService;
import com.pay.txncore.facade.dto.AccountAttribDTO;

/**
 * @author huhb
 *
 */
public class AccountValidateServiceImpl implements AccountValidateService {

	/* (non-Javadoc)
	 * 验证商户账户属性
	 */
	@Override
	public ExceptionCodeEnum validatePartnerAccountAttr(AccountAttribDTO accountAttr) {
		if(accountAttr != null) {
			if (accountAttr.getAllowIn() == 0) {
				return ExceptionCodeEnum.PARTNER_ALLOW_IN;
			}
			if (accountAttr.getAllowTransferIn() == 0) {
				return ExceptionCodeEnum.PARTNER_TRANSFER_IN;
			}
			/** 收款时不需要验证止出状态 */
//			if(accountAttr.getAllowOut() == 0 ){
//				return ExceptionCodeEnum.PARTNER_ALLOW_IN;
//			}
			if(accountAttr.getAllowTransferOut() == 0){
				return ExceptionCodeEnum.PARTNER_TRANSFER_OUT;
			}
			if(accountAttr.getFrozen() == 0) {
				return ExceptionCodeEnum.PARTNER_FROZEN;
			}
		} else {
			return ExceptionCodeEnum.ACCT_NO_EXSITS;
		}

		return null;
	}

	@Override
	public ExceptionCodeEnum validateBuyerAccountAttr(AccountAttribDTO accountAttr) {
		if(accountAttr != null) {
			if (accountAttr.getAllowIn() == 0) {
				return ExceptionCodeEnum.ACCT_ALLOW_IN;
			}
			if (accountAttr.getAllowOut() == 0) {
				return ExceptionCodeEnum.ACCT_ALLOW_OUT;
			}
		} else {
			return ExceptionCodeEnum.ACCT_NO_EXSITS;
		}

		return null;
	}

	@Override
	public boolean isPartnerAllowOutAndIn(AccountAttribDTO acctAttr) {
		if(acctAttr == null)
			return false;
		else if(acctAttr.getAllowIn() == 1 && acctAttr.getAllowOut() == 1 && acctAttr.getFrozen() == 1)
			return true;
		return false;
	}

	@Override
	public boolean isPayerAllowIn(AccountAttribDTO accAttr) {
		if(accAttr == null)
			return false;
		if(accAttr.getAllowIn() == 1 && accAttr.getFrozen()  == 1)
			return true;
		return false;
	}

}
