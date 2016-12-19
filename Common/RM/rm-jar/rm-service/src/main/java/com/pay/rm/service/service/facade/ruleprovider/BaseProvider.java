/**
 * 风控规则基础类
 */
package com.pay.rm.service.service.facade.ruleprovider;

import java.util.Map;

import com.pay.rm.service.dto.rmlimit.rule.BaseRule;
import com.pay.rm.service.service.facade.RCLimitService;

/**
 * @author Administrator
 *
 */
public interface BaseProvider {

	public abstract BaseRule getRule(Map<String,String> map);
	public void setRcLimitService(RCLimitService rcLimitService);
}
