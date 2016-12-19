/**
 * 
 */
package com.pay.rm.service.service.facade.ruleprovider;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.rm.service.common.RCRuleType;
import com.pay.rm.service.dto.rmlimit.rule.BaseRule;
import com.pay.rm.service.service.facade.RCLimitService;

/**
 * @author Administrator
 *
 */
public class WithdrawBusinessLimitProvider implements BaseProvider {
	private Log log = LogFactory.getLog(WithdrawBusinessLimitProvider.class);
	private RCLimitService rcLimitService;
	/* (non-Javadoc)
	 * @see com.pay.poss.riskconctrol.service.ruleprovider.BaseProvider#getRule(int, java.util.Map)
	 */
	@Override
	public BaseRule getRule(Map<String, String> mapint) {
		return BusinessLimitUtil.getRule(rcLimitService, RCRuleType.TYPE_BUSINESS_LIMITATION.getType(), mapint);
	}
	public void setRcLimitService(RCLimitService rcLimitService) {
		this.rcLimitService = rcLimitService;
	}

}
