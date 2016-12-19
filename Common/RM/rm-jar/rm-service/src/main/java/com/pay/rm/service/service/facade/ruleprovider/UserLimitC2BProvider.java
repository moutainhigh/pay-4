package com.pay.rm.service.service.facade.ruleprovider;

import java.util.Map;

import com.pay.rm.service.common.RCRuleType;
import com.pay.rm.service.dto.rmlimit.rule.BaseRule;
import com.pay.rm.service.service.facade.RCLimitService;

public class UserLimitC2BProvider implements BaseProvider{
	private RCLimitService rcLimitService;
	/* (non-Javadoc)
	 * @see com.pay.poss.riskconctrol.service.ruleprovider.BaseProvider#getRule(int, java.util.Map)
	 */
	@Override
	public BaseRule getRule(Map<String, String> mapint) {
		return UserLimitUtil.getRule(rcLimitService, RCRuleType.TYPE_FO_USER_LIMIT_C2B.getType(), mapint);
	}
	@Override
	public void setRcLimitService(RCLimitService rcLimitService) {
		this.rcLimitService = rcLimitService;
	}
}
