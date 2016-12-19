/**
 * 
 */
package com.pay.rm.service.service.facade.ruleprovider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.rm.service.dto.rmlimit.rule.BaseRule;
import com.pay.rm.service.model.rmlimit.innerlimit.RcInnerLimit;
import com.pay.rm.service.service.facade.RCLimitService;

/**
 * @author Administrator
 * 
 */
public class InnerLimitProvider implements BaseProvider {
	private RCLimitService rcLimitService;
	private Log log = LogFactory.getLog(InnerLimitProvider.class);

	@Override
	public BaseRule getRule(Map<String, String> map) {

		String strUserLevel = map.get("userLevel");
		if (strUserLevel == null)
			strUserLevel = "0"; // 给定默认的用户等级
		String strBusinessType = map.get("businessType");
		if (strBusinessType == null)
			return null; // 没有指定业务类型,找不到规则
		Map<String, String> mapsys = new HashMap<String, String>();
		mapsys.put("sysBusiness", strBusinessType);
		mapsys.put("userLevel", strUserLevel);
		BaseRule baseRule = new BaseRule();
		List<RcInnerLimit> limitList = rcLimitService.searchSysLimit(mapsys);
		if (limitList != null && limitList.size() > 0) {
			RcInnerLimit rcInnerLimit = limitList.get(0);
			baseRule.setSingleLimit(rcInnerLimit.getSingleLimit());
			baseRule.setDayLimit(rcInnerLimit.getDayLimit());
			baseRule.setMonthLimit(rcInnerLimit.getMonthLimit());
			baseRule.setDayTimes(rcInnerLimit.getDayTimes().intValue());
			baseRule.setMonthTimes(rcInnerLimit.getMonthTimes().intValue());
			return baseRule;
		} else {
			log.error("没有找到限额 sysBusiness:" + strBusinessType + ":userLevel:"
					+ strUserLevel);
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.pay.rm.service.service.facade.ruleprovider.BaseProvider#
	 * setRcLimitService(com.pay.rm.service.service.facade.RCLimitService)
	 */
	@Override
	public void setRcLimitService(RCLimitService rcLimitService) {
		this.rcLimitService = rcLimitService;

	}

}
