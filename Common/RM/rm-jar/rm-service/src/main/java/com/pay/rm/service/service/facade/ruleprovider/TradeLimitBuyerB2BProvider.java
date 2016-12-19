/**
 * B2B交易（买方/出账）
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
public class TradeLimitBuyerB2BProvider implements BaseProvider {
	private RCLimitService rcLimitService;
	/* (non-Javadoc)
	 * @see com.pay.poss.riskconctrol.service.ruleprovider.BaseProvider#getRule(java.util.Map)
	 */
	@Override
	public BaseRule getRule(Map<String, String> mapint) {
		// TODO Auto-generated method stub
		return BusinessLimitUtil.getRule(rcLimitService, RCRuleType.TYPE_FO_TRADE_LIMIT_B2B_BUYER.getType(), mapint);
	}

	/* (non-Javadoc)
	 * @see com.pay.poss.riskconctrol.service.ruleprovider.BaseProvider#setRcLimitService(com.pay.poss.riskconctrol.service.limit.RCLimitService)
	 */
	@Override
	public void setRcLimitService(RCLimitService rcLimitService) {
		this.rcLimitService = rcLimitService;
	}

}
