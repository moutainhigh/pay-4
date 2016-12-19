package com.pay.acc.service.account.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.member.dto.EnterpriseBaseDto;
import com.pay.acc.member.service.EnterpriseBaseService;
import com.pay.acc.member.service.LiquidateInfoService;
import com.pay.acc.service.account.EntryPriseRiskControlService;

public class EntryPriseRiskControlServiceImpl implements
		EntryPriseRiskControlService {

	private LiquidateInfoService liquidateInfoService;
	private EnterpriseBaseService enterpriseBaseService;
	private final Log log = LogFactory.getLog(getClass());

	@Override
	public boolean queryRiskControl(Long memberCode, int level) {
		Long leveCode = enterpriseBaseService
				.queryEnterpriseRiskLeveCode(memberCode);
		if (null != leveCode && leveCode == level) {
			return true;
		}
		return false;
	}

	@Override
	public Integer queryEnterPriseSettlePeriod(Long memberCode) {

		EnterpriseBaseDto enterpriseBaseDto = enterpriseBaseService
				.queryEnterpriseBaseByMemberCode(memberCode);
		return enterpriseBaseDto.getSettlementCycle();
	}

	@Override
	public boolean querySettlePeriod(Long memberCode, int period) {
		return liquidateInfoService.querySettlePeriod(memberCode, period);
	}

	public void setLiquidateInfoService(
			LiquidateInfoService liquidateInfoService) {
		this.liquidateInfoService = liquidateInfoService;
	}

	public void setEnterpriseBaseService(
			EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}

}
