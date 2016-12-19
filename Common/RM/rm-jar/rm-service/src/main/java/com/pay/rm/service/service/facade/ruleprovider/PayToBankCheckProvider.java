/**
 * 付款到银行(我有用户)规则提供类  
 */
package com.pay.rm.service.service.facade.ruleprovider;


import java.util.Map;

import com.pay.rm.service.dto.rmlimit.rule.BaseRule;
import com.pay.rm.service.service.facade.RCLimitService;


public class PayToBankCheckProvider implements BaseProvider {
	
	private RCLimitService rcLimitService;
	@Override
	public BaseRule getRule(Map<String, String> mapint) {
		/**
		 * 这个限额属于免费的额度，不属于风控，暂时先设定供外部调用
		 */
		BaseRule br = new BaseRule();
		br.setDayLimit(2000000);
		return br;
		//return BusinessLimitUtil.getRule(rcLimitService, RCRuleType.TYPE_FO_BANK_ACCOUNT.getBusinessType(), mapint);
	}

	public void setRcLimitService(RCLimitService rcLimitService) {
		this.rcLimitService = rcLimitService;
	}

}
