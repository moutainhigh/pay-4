/**
 *  File: MassPayServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-8     darv      Changes
 *  
 *
 */
package com.pay.poss.service.ma.batchpaytoaccount.impl;

import com.pay.acc.acct.dto.AcctDto;
import com.pay.acc.acct.service.AcctService;
import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.exception.MaAccountQueryUntxException;
import com.pay.acc.exception.MaMemberQueryException;
import com.pay.acc.exception.MaMemberVerifyException;
import com.pay.acc.service.account.AccountInfoService;
import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.account.dto.BalancesDto;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.MemberVerifyService;
import com.pay.acc.service.member.dto.MemberBaseInfoBO;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.acc.service.member.dto.MemberVerifyDto;
import com.pay.acc.service.member.dto.MemberVerifyResult;
import com.pay.poss.base.exception.PossUntxException;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.poss.service.ma.batchpaytoaccount.MassPayService;

/**
 * @author darv
 * 
 */
public class MassPayServiceImpl implements MassPayService {

	private AccountQueryService accountQueryService;

	private MemberQueryService memberQueryService;

	private AccountInfoService accountInfoService;

	private MemberVerifyService memberVerifyService;

	private AcctService acctService;

	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}

	public void setMemberVerifyService(MemberVerifyService memberVerifyService) {
		this.memberVerifyService = memberVerifyService;
	}

	public void setAccountInfoService(AccountInfoService accountInfoService) {
		this.accountInfoService = accountInfoService;
	}

	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}

	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

	@Override
	public AcctAttribDto getAcctAttrInfo(Long memberCode, Integer accountType) throws MaAccountQueryUntxException {
		return this.accountQueryService.doQueryAcctAttribNsTx(memberCode, accountType);
	}

	@Override
	public BalancesDto getBalancesInfo(Long memberCode, Integer accountType) throws PossUntxException,
			MaAccountQueryUntxException {
		return this.accountQueryService.doQueryBalancesNsTx(memberCode, accountType);
	}

	@Override
	public MemberInfoDto getMemberInfo(String loginName, Long memberCode, Integer memberType, Integer acctType)
			throws PossUntxException, MaMemberQueryException {
		return this.memberQueryService.doQueryMemberInfoNsTx(loginName, memberCode, memberType, acctType);
	}

	@Override
	public boolean payPwdIsOrder(long memberCode, int accountType, String payPwd) throws Exception {
		if (this.accountInfoService.doVerifyPayPasswordNsTx(memberCode, accountType, payPwd) == 1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public MemberVerifyDto getMemberVerify(Long memberCode) {
		try {
			return this.memberVerifyService.doQueryMemberVerifyInfoNsTx(memberCode);
		} catch (MaMemberVerifyException e) {
			LogUtil.error(getClass(), e.getMessage(), OPSTATUS.EXCEPTION, "memberCode:" + memberCode, "", e.toString(), "", "");
			return null;
		}
	}

	@Override
	public AcctDto getAcctInfo(Long memberCode, Integer acctTypeId) {
		try {
			return this.acctService.queryAcctByMemberCodeAndAcctTypeId(memberCode, acctTypeId);
		} catch (Exception e) {
			LogUtil.error(getClass(), e.getMessage(), OPSTATUS.EXCEPTION, "memberCode:" + memberCode, "accTypeId:" + acctTypeId,
					e.toString(), "", "");
			return null;
		}
	}

	@Override
	public boolean verifyRealName(Long memberCode) throws MaMemberVerifyException {
		return memberVerifyService.doQueryRealNameVerifyNsTx(memberCode);

	}

	public MemberBaseInfoBO queryMemberBaseInfoByMemberCode(Long membercode) throws MaMemberQueryException {
		return memberQueryService.queryMemberBaseInfoByMemberCode(membercode);
	}

	@Override
	public MemberVerifyResult getMemberVerifyResult(Long memberCode) throws Exception {
		return memberVerifyService.QueryMemberVerifyByMemberCode(memberCode);
	}

}
