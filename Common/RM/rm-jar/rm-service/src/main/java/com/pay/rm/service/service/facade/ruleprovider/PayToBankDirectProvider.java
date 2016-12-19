package com.pay.rm.service.service.facade.ruleprovider;

import java.util.Map;

import com.pay.rm.service.common.RCRuleType;
import com.pay.rm.service.dto.rmlimit.rule.BaseRule;
import com.pay.rm.service.service.facade.RCLimitService;



public class PayToBankDirectProvider implements BaseProvider {
	private RCLimitService rcLimitService;
	@Override
	public BaseRule getRule(Map<String, String> map) {
		// TODO Auto-generated method stub
		return BusinessLimitUtil.getRule(rcLimitService, RCRuleType.TYPE_FO_BANK_DIRECT.getType(), map);
	}

	public void setRcLimitService(RCLimitService rcLimitService) {
		this.rcLimitService = rcLimitService;
	}

}
