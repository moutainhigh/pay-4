/**
 *  File: WithdrawMemberFacadeServiceImpl.java
 *  Description:会员相关服务facade
 *  Copyright 2010-2010 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  2010-9-16   Sandy_Yang  create
 *  
 *
 */
package com.pay.poss.service.ma.member.impl;

import java.util.Date;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.exception.MaAccountQueryUntxException;
import com.pay.acc.exception.MaMemberQueryException;
import com.pay.acc.exception.MaMemberVerifyException;
import com.pay.acc.service.account.dto.BalancesDto;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.poss.service.adapter.AbstractExternalAdapter;
import com.pay.poss.service.ma.member.WithdrawMemberFacadeService;

/**
 * 会员相关服务facade
 * @author Sandy_Yang
 */
public class WithdrawMemberFacadeServiceImpl extends AbstractExternalAdapter implements
		WithdrawMemberFacadeService {
	
	public Long getWithdrawAmount(Long memberCode, int accountType) {
			BalancesDto balancesDto;
		try {
			balancesDto = accountQueryService.doQueryBalancesNsTx(memberCode, accountType);
			return balancesDto.getWithdrawBalance();
		} catch (com.pay.acc.exception.MaAccountQueryUntxException e) {
			log.error("query withdraw amount error!MemberCode:"+memberCode,e);
		}
		return 0L;
	}
	public boolean canWithdraw(Long memberCode,int acctType) {
		try {
			AcctAttribDto acctAttribDto = accountQueryService.doQueryAcctAttribNsTx(memberCode, acctType);
			return acctAttribDto.getAllowWithdrawal() == 1;
		} catch (MaAccountQueryUntxException e) {
			log.error("query allow withdraw error!MemberCode:",e);
		}
		return false;
	}
	public boolean doQueryRealNameVerify(Long memberCode) {
			boolean memberVerify = false;
			try {
				memberVerify = memberVerifyService.doQueryRealNameVerifyNsTx(memberCode);
			} catch (MaMemberVerifyException e) {
				log.error("query allow withdraw error!MemberCode:",e);
			}
		return memberVerify;
	}

	
	public MemberInfoDto qyeryMember(Long memberCode) {
		if(memberCode!=null){
			try{
				return this.memberQueryService.doQueryMemberInfoNsTx(null, memberCode, null, null);
			}catch (MaMemberQueryException e) {
				log.error("query allow withdraw error!MemberCode:",e);
			}
		}
		return null;
	}
	@Override
	public Long getBalance(Long memberCode, int accountType) {
		BalancesDto balancesDto;
		try {
			balancesDto = accountQueryService.doQueryBalancesNsTx(memberCode, accountType);
			return balancesDto.getBalance();
		} catch (com.pay.acc.exception.MaAccountQueryUntxException e) {
			log.error("query balance amount error!MemberCode:"+memberCode,e);
		}
		return 0L;
	}
	@Override
	public Long doQueryBalanceByEntryRntx(Long memberCode,
			Integer accountType) {
		
		return accountQueryService.doQueryBalanceByEntryRntx(memberCode, accountType);
	}
	
	public Long getTheDateWithdrawBalance(Long memberCode, int acctType,Date date){
		
		try {
			AcctAttribDto acctAttribDto = accountQueryService.doQueryAcctAttribNsTx(memberCode, acctType);
			return accountQueryService.selectBalanceByAcctCodeAndDate(acctAttribDto.getAcctCode(), date);
		} catch (MaAccountQueryUntxException e) {
			log.error("query allow withdraw error!MemberCode:",e);
		}
		return 0L;
		
	}
}
