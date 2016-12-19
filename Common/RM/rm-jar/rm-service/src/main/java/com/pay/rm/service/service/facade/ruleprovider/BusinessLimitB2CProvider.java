/**
 * 转帐(B2C)规则提供类  
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
public class BusinessLimitB2CProvider implements BaseProvider {
	
	private RCLimitService rcLimitService;
	/* (non-Javadoc)
	 * @see com.pay.poss.riskconctrol.service.ruleprovider.BaseProvider#getRule()
	 */
	@Override
	public BaseRule getRule(Map<String,String> mapint) {

		return BusinessLimitUtil.getRule(rcLimitService, RCRuleType.TYPE_FO_BUSINESS_LIMIT_B2C.getType(), mapint);
	}
	@Override
	public void setRcLimitService(RCLimitService rcLimitService) {
		this.rcLimitService = rcLimitService;
	}
}
