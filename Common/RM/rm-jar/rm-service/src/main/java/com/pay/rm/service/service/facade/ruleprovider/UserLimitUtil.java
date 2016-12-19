package com.pay.rm.service.service.facade.ruleprovider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.rm.service.dto.rmlimit.rule.BaseRule;
import com.pay.rm.service.model.rmlimit.userlimit.RcUserLimit;
import com.pay.rm.service.service.facade.RCLimitService;


public class UserLimitUtil {
	protected static BaseRule getRule(RCLimitService rcLimitService,int businessType,Map<String,String> mapint){
		
		Log log = LogFactory.getLog(UserLimitUtil.class);
		BaseRule br = new BaseRule();
		String strUserLevel = mapint.get("userLevel");
		if(strUserLevel==null||"".equals(strUserLevel)){
			strUserLevel ="2";
			log.error("用户等级未设置，默认为已认证！");
		} 
		
		int userLevel = Integer.parseInt(strUserLevel);
		Map<String,String> map = new HashMap<String,String>();
		map.put("businessType", String.valueOf(businessType));
		map.put("userLevel", String.valueOf(userLevel));
		
		List<RcUserLimit>  userLimitList= rcLimitService.searchUserLimit(map);
		if(userLimitList.size()==0){
			log.error("没有找到限额限次规则");
			return null;
		}else{
			RcUserLimit rcUserLimit  = userLimitList.get(0);
			br.setDayLimit(rcUserLimit.getDayLimit());
			br.setDayTimes(rcUserLimit.getDayTimes().intValue());
			br.setMonthLimit(rcUserLimit.getMonthLimit());
			br.setMonthTimes(rcUserLimit.getMonthTimes().intValue());
			br.setSingleLimit(rcUserLimit.getSingleLimit());
			
			return br;
		}
	}
}
