/**
 * 
 */
package com.pay.fundout.service.ma.impl;

import org.springframework.beans.BeanUtils;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.exception.MaAccountQueryUntxException;
import com.pay.acc.service.account.dto.BalancesDto;
import com.pay.fundout.service.ma.AccountInfo;
import com.pay.fundout.service.ma.AccountQueryFacadeService;
import com.pay.poss.service.adapter.AbstractExternalAdapter;

/**
 * @author NEW
 *
 */
public class AccountQueryFacadeServiceImpl extends AbstractExternalAdapter implements AccountQueryFacadeService {

	@Override
	public long getBalance(long memberCode, int acctType) {
		BalancesDto balanceBO = null;
		Long balance = new Long(0);
		try {
			balanceBO = accountQueryService.doQueryBalancesNsTx(memberCode,acctType);
			balance = balanceBO.getBalance();
		} catch (MaAccountQueryUntxException e) {
			log.error("call accountQueryService.doQueryBalanceNsTx failed", e);
		}
		return balance;
	}

	@Override
	public AccountInfo getAccountInfo(long memberCode, int acctType) {
		AccountInfo info = null;
		
		try {
			AcctAttribDto dto = accountQueryService.doQueryAcctAttribNsTx(memberCode, acctType);
			if(dto!=null){
				info = new AccountInfo();
				BeanUtils.copyProperties(dto, info);
			}
			
		} catch (MaAccountQueryUntxException e) {
			log.error("call accountQueryService.doQueryAcctAttribNsTx faild",e);
		}
		return info;
	}

	@Override
	public long getWithdrawBalance(long memberCode, int acctType) {
		BalancesDto balanceBO = null;
		Long balance = new Long(0);
		try {
			balanceBO = accountQueryService.doQueryBalancesNsTx(memberCode,acctType);
			balance = balanceBO.getWithdrawBalance();
		} catch (MaAccountQueryUntxException e) {
			log.error("call accountQueryService.doQueryBalanceNsTx failed", e);
		}
		return balance;
	}

}
