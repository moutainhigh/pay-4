package com.pay.acc.service.account.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acct.exception.AcctServiceException;
import com.pay.acc.acct.service.AcctService;
import com.pay.acc.commons.AmountByEnum;
import com.pay.acc.service.account.NegativeBalanceService;
import com.pay.acc.service.account.exception.MaAcctBalanceException;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.pe.helper.CRDRType;

public class NegativeBalanceServiceImpl implements NegativeBalanceService {
	private AcctService acctService;
	private Log log = LogFactory.getLog(NegativeBalanceServiceImpl.class);

	@Override
	public void updateNegativeBalance(Integer crdr, Integer maBlanceBy,
			Long chargeAmount, String acctCode) throws MaAcctBalanceException {
		if (crdr == CRDRType.DEBIT.getValue()) {// 借
			// 余额方向为加
			if (maBlanceBy == AmountByEnum.ADD.getCode()) {// 不管是冲正，还是更新都统一的处理
				log.info("关于借方账户[" + acctCode + "]余额方向【" + maBlanceBy + "】");
				this.handlerUpdateCrebitBalance(chargeAmount, chargeAmount,
						acctCode, crdr);
			} else if (maBlanceBy == AmountByEnum.REDUCE.getCode()) {// 余额方向为减
				log.info("关于借方账户[" + acctCode + "]余额方向【" + maBlanceBy + "】");
				this.handlerUpdateCrebitBalance((-1) * chargeAmount,
						chargeAmount, acctCode, crdr);
			}
		} else if (crdr == CRDRType.CREDIT.getValue()) {// 贷
			// 余额方向为加
			if (maBlanceBy == AmountByEnum.ADD.getCode()) {// 不管是冲正，还是更新都统一的处理
				log.info("关于更新贷方[" + acctCode + "]余额方向【" + maBlanceBy + "】");
				this.handlerUpdateCrebitBalance(chargeAmount, chargeAmount,
						acctCode, crdr);
			} else if (maBlanceBy == AmountByEnum.REDUCE.getCode()) {// 余额方向为减
				log.info("关于更新贷方[" + acctCode + "]余额方向【" + maBlanceBy + "】");
				this.handlerUpdateCrebitBalance((-1) * chargeAmount,
						chargeAmount, acctCode, crdr);
			}
		}
	}

	/**
	 * 更新余额
	 * 
	 * @param amount
	 * @param acctCode
	 * @throws MaAcctBalanceException
	 */
	private void handlerUpdateCrebitBalance(Long amount, Long creditAmount,
			String acctCode, Integer crdr) throws MaAcctBalanceException {
		boolean result = false;
		try {
			log.info("更新[" + "余额方向为[" + crdr + "] 账户号为:" + acctCode + "]的余额");
			result = this.acctService.updateNegativeBalanceWithAcctCode(amount,
					creditAmount, acctCode, crdr);
		} catch (AcctServiceException e) {
			log.error(e.getMessage(), e);
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.INPUT_PARA_NULL, e.getMessage(), e);
		}
		if (!result) {
			log.error("账户" + acctCode + "余额操作失败");
			throw new MaAcctBalanceException(
					ErrorExceptionEnum.ACCT_BALANCE_ERROR, "账户余额操作失败");
		}

	}

	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}
}
