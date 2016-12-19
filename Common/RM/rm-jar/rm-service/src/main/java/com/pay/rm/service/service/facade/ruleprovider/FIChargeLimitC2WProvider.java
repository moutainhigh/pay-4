/**
 * 
 */
package com.pay.rm.service.service.facade.ruleprovider;

import java.util.Map;

import com.pay.rm.service.common.RCRuleType;
import com.pay.rm.service.dto.rmlimit.rule.BaseRule;
import com.pay.rm.service.service.facade.RCLimitService;

/**
 * @author Administrator
 *
 */
public class FIChargeLimitC2WProvider implements BaseProvider {
	private RCLimitService rcLimitService;
	/* (non-Javadoc)
	 * @see com.pay.rm.service.service.facade.ruleprovider.BaseProvider#getRule(java.util.Map)
	 */
	@Override
	public BaseRule getRule(Map<String, String> map) {
		return UserLimitUtil.getRule(rcLimitService, RCRuleType.TYPE_FI_CHARGE_LIMIT_C2W.getType(), map);
	}

	/* (non-Javadoc)
	 * @see com.pay.rm.service.service.facade.ruleprovider.BaseProvider#setRcLimitService(com.pay.rm.service.service.facade.RCLimitService)
	 */
	@Override
	public void setRcLimitService(RCLimitService rcLimitService) {
		this.rcLimitService = rcLimitService;

	}

}
