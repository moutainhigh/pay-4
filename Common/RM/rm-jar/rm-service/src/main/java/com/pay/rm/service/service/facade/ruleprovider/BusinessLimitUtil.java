/**
 * 
 */
package com.pay.rm.service.service.facade.ruleprovider;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.rm.service.dto.rmlimit.rule.BaseRule;
import com.pay.rm.service.model.rmlimit.businesslimit.RcBusinessLimit;
import com.pay.rm.service.model.rmlimit.businesslimitcustom.RcBusinessLimitCustom;
import com.pay.rm.service.model.rmlimit.mcclimit.RcMccLimit;
import com.pay.rm.service.service.facade.RCLimitService;


/**
 * @author Administrator
 *
 */
public class BusinessLimitUtil {
	
	protected static BaseRule getRule(RCLimitService rcLimitService,int businessType,Map<String,String> mapint){
		BaseRule br = new BaseRule();
		Log log = LogFactory.getLog(BusinessLimitUtil.class);
		Map<String,String> map = new HashMap<String,String>();
		map.put("businessType", String.valueOf(businessType));
		if(mapint.get("businessId")!=null)
			map.put("businessId", String.valueOf(mapint.get("businessId")));
		else
			map.put("businessId", String.valueOf("0"));
		String status = String.valueOf(mapint.get("status"));
		if(null != status){
			map.put("status", status);
		}
		List<RcBusinessLimitCustom>  businessLimitCustomList= rcLimitService.searchBusinessLimitCustom(map);
		if(businessLimitCustomList == null || businessLimitCustomList.size() == 0){
			log.info("没有客制化规则");
			map.put("businessType", String.valueOf(businessType));
			if(mapint.get("riskLevel")!=null)
				map.put("riskLevel", String.valueOf(mapint.get("riskLevel")));
			else{
				map.put("riskLevel", String.valueOf("200")); //如果没有传入风险等级，默认给定最低风险等级5级
				log.warn("缺失风险等级参数，已设置为默认值200");
			}
			List<RcBusinessLimit>  businessLimitList= rcLimitService.searchBusinessLimit(map);
			if(businessLimitList == null || businessLimitList.size()==0){
				log.error("没有配置规则"+businessType+":"+map.get("riskLevel")+":"+map.get("businessId"));
				return null;
			}else{
				String strMcc = mapint.get("mcc");
				RcBusinessLimit rcBusinessLimit  = businessLimitList.get(0);
				if(strMcc!=null&&strMcc.trim().length()>0&&!"0".equals(strMcc)&&rcBusinessLimit.getStatus()==0){   //与行业限额相关的限额,行业编码不为空,行业限额相关标志有效0
					RcMccLimit rcMccLimit = rcLimitService.getMCCLimit(Long.valueOf(strMcc)); 
					BigDecimal bSingleLimit = new BigDecimal(rcBusinessLimit.getSingleLimit()); //单笔限额倍数
					BigDecimal bDayLimit = new BigDecimal(rcBusinessLimit.getDayLimit());//每日限额倍数  
					BigDecimal mSingleLimit = new BigDecimal(rcMccLimit.getSingleLimit());//行业单笔限额
					
					br.setBatchAccounts(rcBusinessLimit.getBatchAccounts());
					br.setDayLimit(bDayLimit.multiply(mSingleLimit).longValue());  //行业限额乘以每日限额倍数
					br.setDayTimes(rcBusinessLimit.getDayTimes().intValue());
					br.setMonthLimit(rcBusinessLimit.getMonthLimit());
					br.setMonthTimes(rcBusinessLimit.getMonthTimes().intValue());
					br.setSingleLimit(bSingleLimit.multiply(mSingleLimit).longValue());//行业限额乘以单笔限额倍数
				}else{//与行业限额无关的限额
					
					br.setBatchAccounts(rcBusinessLimit.getBatchAccounts());
					br.setDayLimit(rcBusinessLimit.getDayLimit());
					br.setDayTimes(rcBusinessLimit.getDayTimes().intValue());
					br.setMonthLimit(rcBusinessLimit.getMonthLimit());
					br.setMonthTimes(rcBusinessLimit.getMonthTimes().intValue());
					br.setSingleLimit(rcBusinessLimit.getSingleLimit());
				}
				return br;
			}
			
		}else{
			RcBusinessLimitCustom rcBusinessLimitCustom  = businessLimitCustomList.get(0);
			br.setBatchAccounts(rcBusinessLimitCustom.getBatchAccounts());
			br.setDayLimit(rcBusinessLimitCustom.getDayLimit());
			br.setDayTimes(rcBusinessLimitCustom.getDayTimes().intValue());
			br.setMonthLimit(rcBusinessLimitCustom.getMonthLimit());
			br.setMonthTimes(rcBusinessLimitCustom.getMonthTimes().intValue());
			br.setSingleLimit(rcBusinessLimitCustom.getSingleLimit());
			
			return br;
		}
	}
	
}
