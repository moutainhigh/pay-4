package com.pay.rm.service.service.facade.ruleprovider;

import java.util.Map;

import com.pay.rm.service.common.RCRuleType;
import com.pay.rm.service.dto.rmlimit.rule.BaseRule;
import com.pay.rm.service.service.facade.RCLimitService;

public class FOPayToBankB2BProvider implements BaseProvider {
	private RCLimitService rcLimitService;
	@Override
	public BaseRule getRule(Map<String, String> map) {
		return BusinessLimitUtil.getRule(rcLimitService, RCRuleType.TYPE_FO_PAY2BANK_B2B.getType(), map);
	}
	@Override
	public void setRcLimitService(RCLimitService rcLimitService) {
		this.rcLimitService = rcLimitService;

	}

}
