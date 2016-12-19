/**
 * 入款充值限额规则
 */
package com.pay.rm.service.service.facade.ruleprovider;

import java.util.Map;

import com.pay.rm.service.common.RCRuleType;
import com.pay.rm.service.dto.rmlimit.rule.BaseRule;
import com.pay.rm.service.service.facade.RCLimitService;

public class FIChargeLimitB2WProvider implements BaseProvider {
	private RCLimitService rcLimitService;
	@Override
	public BaseRule getRule(Map<String, String> mapint) {
		return BusinessLimitUtil.getRule(rcLimitService, RCRuleType.TYPE_FI_CHARGE_LIMIT_B2W.getType(), mapint);
	}

	@Override
	public void setRcLimitService(RCLimitService rcLimitService) {
		this.rcLimitService = rcLimitService;

	}

}
