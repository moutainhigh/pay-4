package com.pay.pe.account.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.pay.acc.exception.MaMemberQueryException;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.MemberBaseInfoBO;
import com.pay.inf.dao.Page;
import com.pay.pe.account.dao.AccountingFeeFailedDao;
import com.pay.pe.account.dto.AccountingFeeFailedDto;
import com.pay.pe.account.dto.AccountingFeeFailedParamDto;
import com.pay.pe.account.service.AccountingFeeFailedService;
import com.pay.pe.accumulated.chargebackentry.service.ChargeBackService;
import com.pay.util.StringUtil;

public class AccountingFeeFailedServiceImpl implements
		AccountingFeeFailedService {

	private AccountingFeeFailedDao accountingFeeFailedDao;
	private MemberQueryService memberQueryService;
	private ChargeBackService chargeBackService;
	
	
	

	public void setChargeBackService(ChargeBackService chargeBackService) {
		this.chargeBackService = chargeBackService;
	}

	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

	public void setAccountingFeeFailedDao(
			AccountingFeeFailedDao accountingFeeFailedDao) {
		this.accountingFeeFailedDao = accountingFeeFailedDao;
	}

	@Override
	public Page<AccountingFeeFailedDto> searchPage(
			AccountingFeeFailedParamDto dto,
			Page<AccountingFeeFailedDto> pageParam) {

		String acctName = dto.getAcctName();
		List<Long> memberCodes = null;
		if (!StringUtil.isEmpty(acctName)) {
			memberCodes = memberQueryService.queryMemberByName(acctName);
			dto.setMemberCodes(memberCodes);
		}

		Page<AccountingFeeFailedDto> pageT =   accountingFeeFailedDao.search(dto, pageParam);
		List<AccountingFeeFailedDto> list = new ArrayList<AccountingFeeFailedDto>();
		for(AccountingFeeFailedDto tempDto:pageT.getResult()){
			try {
				MemberBaseInfoBO bo = memberQueryService.queryMemberBaseInfoByMemberCode(tempDto.getMemberCode());
				tempDto.setAcctName(bo.getName());
			} catch (MaMemberQueryException e) {
				e.printStackTrace();
			}
			
			list.add(tempDto);
		}
		pageT.setResult(list);
		return pageT;
		
	}

	@Override
	public boolean chargeback(long voucherCode, int dealType) {
		boolean chargeback  = chargeBackService.doUpdateBalance(voucherCode, dealType);
		return chargeback;
	}

}
