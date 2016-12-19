/**
 *  File: RealNameValidateServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-24      bill_peng     Changes
 *  
 *
 */
package com.pay.poss.service.ma.pay2bank.impl;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.exception.MaAccountQueryUntxException;
import com.pay.acc.exception.MaMemberQueryException;
import com.pay.acc.service.account.dto.BalancesDto;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.acc.service.member.dto.MemberVerifyResult;
import com.pay.poss.service.adapter.AbstractExternalAdapter;
import com.pay.poss.service.ma.pay2bank.Pay2BankValidateException;
import com.pay.poss.service.ma.pay2bank.Pay2BankValidateService;

/**
 * @author bill_peng
 * 
 */
public class Pay2BankValidateServiceImpl extends AbstractExternalAdapter
		implements Pay2BankValidateService {

	@Override
	public String validatePayerAccount(long memberCode, int accountType) {

		AcctAttribDto bo = null;
		try {
			bo = accountQueryService.doQueryAcctAttribNsTx(memberCode,
					accountType);
		} catch (Exception e) {
			log.error(
					"call accountInfoService.doQueryMemberAcctInfoNsTx failed",
					e);
		}

		if (null == bo) {
			// 会员账户不存在
			return Pay2BankValidateException.NOT_FOUND_MEMBER_ACCOUNT.getCode();
		}

		if (0 == bo.getAllowOut()) {
			// 会员账户不允许出账
			return Pay2BankValidateException.NOT_ALLOWED_PAYMENT.getCode();
		}

		return null;

	}

	@Override
	public String validateCardBin(String bankCardCode, String orgCode) {
		
		/*if (cardBinService.isCreditCard(orgCode, bankCardCode)) {
			return Pay2BankValidateException.INVALID_BANK_CARD_CODE.getCode();
		}*/
		return null;
	}

	@Override
	public Long getBalance(Long memberCoee, int acctType) {
		BalancesDto balanceBO = null;
		Long balance = new Long(0);
		try {
			balanceBO = accountQueryService.doQueryBalancesNsTx(memberCoee,
					acctType);
			balance = balanceBO.getBalance();
		} catch (MaAccountQueryUntxException e) {
			log.error("call accountQueryService.doQueryBalanceNsTx failed", e);
		}
		return balance;
	}

	@Override
	public MemberInfoDto getMemberByMemberCode(Long memberCode) {
		try {
			MemberInfoDto mbrDto = memberQueryService.doQueryMemberInfoNsTx(
					null, memberCode, null, null);
			return mbrDto;
		} catch (MaMemberQueryException e) {
			log.error("call memberQueryService.doQueryMemberInfoNsTx failed", e);
		}
		return null;
	}

	@Override
	public MemberVerifyResult getMemberVerifyInfo(Long memberCode) {

		try {
			MemberVerifyResult result = memberVerifyService
					.QueryMemberVerifyByMemberCode(memberCode);
			return result;
		} catch (Exception e) {
			log.error(
					"call memberVerifyService.QueryMemberVerifyByMemberCode failed",
					e);
		}
		return null;
	}

	@Override
	public AcctAttribDto getAcctAttributeDto(Long memberCode, int acctType) {
		try {
			return accountQueryService.doQueryAcctAttribNsTx(memberCode,
					acctType);
		} catch (MaAccountQueryUntxException e) {
			log.error("call accountQueryService.doQueryAcctAttribNsTx faild", e);
		}
		return null;
	}

}
