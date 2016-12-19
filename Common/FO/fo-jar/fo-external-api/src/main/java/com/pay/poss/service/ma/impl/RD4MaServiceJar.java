/** @Description 
 * @project 	poss-refund
 * @file 		RD4MaServiceJar.java 
 * Copyright (c) 2006-2020 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-27		sunsea.li		Create 
 */
package com.pay.poss.service.ma.impl;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.service.account.dto.BalancesDto;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.poss.base.exception.PossUntxException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.service.adapter.AbstractExternalAdapter;
import com.pay.poss.service.ma.RD4MaServiceApi;

/**
 * <p>
 * 对外调用ma服务接口 jar包依赖实现
 * </p>
 * 
 * @author sunsea.li
 * @since 2010-9-27
 * @see
 */
public class RD4MaServiceJar extends AbstractExternalAdapter implements
		RD4MaServiceApi {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.poss.service.ma.RD4MaServiceApi#doQueryMemberInfoNsTx(java.lang
	 * .String, java.lang.Long, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public MemberInfoDto doQueryMemberInfoNsTx(String loginName,
			Long memberCode, Integer memberType, Integer acctType)
			throws PossUntxException {
		log.debug(printLog(this, 1) + "memberQueryService :"
				+ printObjToString(memberQueryService));
		MemberInfoDto memberInfoDto = null;
		try {
			memberInfoDto = this.memberQueryService.doQueryMemberInfoNsTx(
					loginName, memberCode, memberType, acctType);
			log.debug(printLog(this, 2) + "memberInfoDto :"
					+ printObjToString(memberInfoDto));
		} catch (Exception e) {
			log.error(printLog(this, 3) + "doQueryMemberInfoNsTx:"
					+ e.getMessage());
			throw new PossUntxException(printLog(this, 3)
					+ "doQueryMemberInfoNsTx:" + e.getMessage(),
					ExceptionCodeEnum.ILLEGAL_PARAMETER);
		}
		return memberInfoDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.poss.service.ma.RD4MaServiceApi#doQueryBalancesNsTx(java.lang
	 * .Long, java.lang.Integer)
	 */
	@Override
	public BalancesDto doQueryBalancesNsTx(Long memberCode, Integer accountType)
			throws PossUntxException {
		log.debug(printLog(this, 1) + "accountQueryService :"
				+ printObjToString(accountQueryService));
		BalancesDto balancesDto = null;
		try {
			balancesDto = this.accountQueryService.doQueryBalancesNsTx(
					memberCode, accountType);
			log.debug(printLog(this, 2) + "balancesDto :"
					+ printObjToString(balancesDto));
		} catch (Exception e) {
			log.error(printLog(this, 3) + "doQueryBalancesNsTx:"
					+ e.getMessage());
			throw new PossUntxException(printLog(this, 3)
					+ "doQueryBalancesNsTx:" + e.getMessage(),
					ExceptionCodeEnum.ILLEGAL_PARAMETER);
		}
		return balancesDto;
	}

	@Override
	public AcctAttribDto doQueryAcctAttribNsTx(Long memberCode,
			Integer accountType) throws PossUntxException {
		log.debug(printLog(this, 1) + "accountQueryService :"
				+ printObjToString(accountQueryService));
		AcctAttribDto acctAttribDto = null;
		try {
			acctAttribDto = this.accountQueryService.doQueryAcctAttribNsTx(
					memberCode, accountType);
			log.debug(printLog(this, 2) + "acctAttribDto :"
					+ printObjToString(acctAttribDto));
		} catch (Exception e) {
			log.error(printLog(this, 3) + "doQueryAcctAttribNsTx:", e);
			throw new PossUntxException(printLog(this, 3)
					+ "doQueryAcctAttribNsTx:" + e.getMessage(),
					ExceptionCodeEnum.ILLEGAL_PARAMETER);
		}
		return acctAttribDto;
	}

}
