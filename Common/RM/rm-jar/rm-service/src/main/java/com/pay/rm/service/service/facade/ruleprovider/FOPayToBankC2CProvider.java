/**
 * 付款到银行(银行直连)规则提供类  
 */
package com.pay.rm.service.service.facade.ruleprovider;

import java.util.Map;

import com.pay.rm.service.common.RCRuleType;
import com.pay.rm.service.dto.rmlimit.rule.BaseRule;
import com.pay.rm.service.service.facade.RCLimitService;

public class FOPayToBankC2CProvider implements BaseProvider {
	private RCLimitService rcLimitService;
	@Override
	public BaseRule getRule(Map<String, String> map) {
		
		return UserLimitUtil.getRule(rcLimitService, RCRuleType.TYPE_FO_PAY2BANK_C2C.getType(), map);
	}

	public void setRcLimitService(RCLimitService rcLimitService) {
		this.rcLimitService = rcLimitService;
	}

}
