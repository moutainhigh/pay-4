package com.pay.rm.service.service.facade.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.exception.MaMemberQueryException;
import com.pay.acc.exception.MaMemberVerifyException;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.MemberVerifyService;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.rm.base.exception.RMFacadeException;
import com.pay.rm.base.exception.enums.ExceptionCodeEnum;
import com.pay.rm.service.common.RCRuleType;
import com.pay.rm.service.dto.rmlimit.businesslimitcustom.RcBusinessLimitCustomDTO;
import com.pay.rm.service.dto.rmlimit.rule.BaseRule;
import com.pay.rm.service.model.rmlimit.businesslimit.RcBusinessLimit;
import com.pay.rm.service.model.rmlimit.mcclimit.RcMccLimit;
import com.pay.rm.service.service.facade.RCLimitService;
import com.pay.rm.service.service.facade.RiskControlService;
import com.pay.rm.service.service.facade.ruleprovider.BaseProvider;
public class RiskControlServiceImpl implements RiskControlService {
	private Log log = LogFactory.getLog(RiskControlServiceImpl.class);
	private RCLimitService rcLimitService;
	private MemberQueryService memberQueryService;
	private MemberVerifyService memberVerifyService;
	/**
	 * @param memberVerifyService the memberVerifyService to set
	 */
	public void setMemberVerifyService(MemberVerifyService memberVerifyService) {
		this.memberVerifyService = memberVerifyService;
	}

	/**
	 * @param memberQueryService the memberQueryService to set
	 */
	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

	/**
	 * 获得规则
	 */
	@Override
	public List<BaseRule> getRules(long businessId,int type){
		Map<String,String> map = new HashMap<String,String>();
		map.put("businessId", String.valueOf(businessId));
		
		return this.getRules(type,map);
	}

	@Override
	public List<Map<String, Long>> getMCCLimits(int mcc, int riskLevel) {
		//返回的限额限次表
		List<Map<String, Long>> listLimit = new ArrayList<Map<String, Long>>();
		RcMccLimit rcMccLimit = rcLimitService.getMCCLimit(new Long(mcc));
		Map<String,String> map = new HashMap<String,String>();
		map.put("riskLevel", String.valueOf(riskLevel));
		
		List<RcBusinessLimit> businessLimitList=rcLimitService.searchBusinessLimit(map);
		
		for(RcBusinessLimit rcBusinessLimit:businessLimitList){

			
			if(rcBusinessLimit.getStatus()==0){//需要与MCC单笔限额相乘,status设为为0
				Map<String,Long> reMap = new HashMap<String,Long>();
				BigDecimal bSingleLimit = new BigDecimal(rcBusinessLimit.getSingleLimit());
				BigDecimal bDayLimit = new BigDecimal(rcBusinessLimit.getDayLimit());
				BigDecimal mSingleLimit = new BigDecimal(rcMccLimit.getSingleLimit());
				reMap.put("singleLimit", bSingleLimit.multiply(mSingleLimit).longValue());
				reMap.put("dayLimit", bDayLimit.multiply(mSingleLimit).longValue());
				reMap.put("dayTimes", rcBusinessLimit.getDayTimes());
				reMap.put("monthTimes", rcBusinessLimit.getMonthTimes());
				reMap.put("batchAccounts", rcBusinessLimit.getBatchAccounts());
				reMap.put("monthLimit", rcBusinessLimit.getMonthLimit());
				reMap.put("businessType", (long)rcBusinessLimit.getBusinessType());
				listLimit.add(reMap);
			}else{//不需要与MCC单笔限额相乘,status设为为1
				Map<String,Long> reMap = new HashMap<String,Long>();
				reMap.put("singleLimit", rcBusinessLimit.getSingleLimit());
				reMap.put("dayLimit", rcBusinessLimit.getDayLimit());
				reMap.put("monthLimit", rcBusinessLimit.getMonthLimit());
				reMap.put("dayTimes", rcBusinessLimit.getDayTimes());
				reMap.put("monthTimes", rcBusinessLimit.getMonthTimes());
				reMap.put("batchAccounts", rcBusinessLimit.getBatchAccounts());
				reMap.put("businessType", (long)rcBusinessLimit.getBusinessType());
				listLimit.add(reMap);
			}
		}
		
		return listLimit;
	}

	public void setRcLimitService(RCLimitService rcLimitService) {
		this.rcLimitService = rcLimitService;
	}
	@Override
	public List<BaseRule> getRules(int ruleType,
			Map<String, String> map) {
		List<BaseRule> list = new ArrayList<BaseRule>();
		map.put("status", "1");
		
		try{
			getMemberInfo(map, ruleType);
			for(RCRuleType rcRuleType:RCRuleType.values()){
				if(rcRuleType.getType()==ruleType){
					//根据规则类型得到规则
					BaseRule baseRule = getRuleByRuleType(map, rcRuleType);
					if(baseRule!=null){
						list.add(baseRule);
						break;
					}
				}
			}
			
		}catch(Exception e){
			log.error("提现验证获取失败"+e.getMessage());
			e.printStackTrace();
		}
		return list;
	}
	
	
	private void getMemberInfo(Map<String,String> map,int ruleType) throws MaMemberQueryException, NumberFormatException, MaMemberVerifyException{
		String memberCode = map.get("businessId");
		if(memberCode != null ){
			if(ruleType == RCRuleType.TYPE_BUSINESS_LIMITATION.getType() || ruleType == RCRuleType.TYPE_FO_PAY2BANK_B2B.getType()
					|| ruleType == RCRuleType.TYPE_FO_BUSINESS_LIMIT_B2C.getType() || ruleType == RCRuleType.TYPE_FO_BUSINESS_LIMIT_B2B.getType()
					|| ruleType == RCRuleType.TYPE_FO_TRADE_LIMIT_B2C.getType() || ruleType == RCRuleType.TYPE_FO_TRADE_LIMIT_B2B_BUYER.getType()
					|| ruleType == RCRuleType.TYPE_FO_TRADE_LIMIT_B2B_SELLER.getType() || ruleType == RCRuleType.TYPE_FI_CHARGE_LIMIT_B2W.getType()
					|| ruleType == RCRuleType.TYPE_FO_BANK_DIRECT.getType()){
				MemberInfoDto memberInfoDto = memberQueryService.doQueryMemberInfoNsTx(null,Long.valueOf(memberCode),null,null);
				map.put("riskLevel", String.valueOf(memberInfoDto.getLevelCode()));
			}else{
				boolean isVerify = this.memberVerifyService.doQueryRealNameVerifyNsTx(Long.valueOf(memberCode));
				if (isVerify) { // 做了实名认证
					map.put("userLevel", "2");
				} else { // 未做实名认证
					map.put("userLevel", "1");
				}
			}
		}
	}
	
	/**
	 * 根据规则类型得到规则
	 * @param map
	 * @param rcRuleType
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	private BaseRule getRuleByRuleType(Map<String, String> map,
			RCRuleType rcRuleType) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		String cname = rcRuleType.getClassName();  // 获得类名
		BaseProvider provider = (BaseProvider)Class.forName(cname).newInstance(); //获得PROVIDER实现类的实例
		provider.setRcLimitService(rcLimitService); //注入风控限额服务
		BaseRule baseRule = provider.getRule(map);//调用具体业务的PROVIDER的GETRULE方法,得到具体业务的限额规则
		return baseRule;
	}

	@Override
	public boolean setMccLimit(long businessId,int mcc,List<Map<String, Long>> listLimit) {
		
		int businessType =1;
		long businessLimitCustomId=0;
		for(Map<String, Long> map:listLimit){
			RcBusinessLimitCustomDTO dto = new RcBusinessLimitCustomDTO();
			dto.setBusinessType(map.get("businessType").intValue());
			dto.setBusinessId(businessId);
			dto.setMcc((long)mcc);
			dto.setStatus(1);
			if(map.get("singleLimit")!=null)
				dto.setSingleLimit(map.get("singleLimit"));
			if(map.get("dayLimit")!=null)
				dto.setDayLimit(map.get("dayLimit"));
			if(map.get("monthLimit")!=null)
				dto.setMonthLimit(map.get("monthLimit"));
			else
				dto.setMonthLimit(0L);
			if(map.get("dayTimes")!=null)
				dto.setDayTimes(map.get("dayTimes"));
			if(map.get("monthTimes")!=null)
				dto.setMonthTimes(map.get("monthTimes"));
			if(map.get("batchAccounts")!=null)
				dto.setBatchAccounts(map.get("batchAccounts"));	
			else
				dto.setBatchAccounts(0L);
			businessLimitCustomId = rcLimitService.createBusinessLimitCustom(dto);
			businessType++;
		}
		return businessLimitCustomId >0;

	}

	@Override
	public boolean setDefaultMccLimit(long businessId, int mcc, int riskLevel)throws RMFacadeException{
		// TODO Auto-generated method stub
		RcMccLimit rcMccLimit = rcLimitService.getMCCLimit(new Long(mcc));
		if(rcMccLimit==null){
			return false;
	//		throw new RMFacadeException("rm to setDefaultMccLimit!",ExceptionCodeEnum.MCC_NOT_EXIST);
		}
		return setMccLimit(businessId, mcc, getMCCLimits(mcc, riskLevel));
		 
	}

	@Override
	public boolean isExistMcc(int mcc) throws RMFacadeException{
		RcMccLimit rcMccLimit = rcLimitService.getMCCLimit(new Long(mcc));
		if(rcMccLimit==null){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public boolean updateBusinessLimitCustomTrans(long businessId, int mcc, int riskLevel)
			throws RMFacadeException {
		
		if(isExistMcc(mcc)){
			try{
				rcLimitService.deleteBusinessLimitCustom(businessId);
				//throw new Exception("test");
				return setMccLimit(businessId, mcc, getMCCLimits(mcc, riskLevel));
			}catch(Exception e){
				throw new RMFacadeException("修改商户定制限额异常",ExceptionCodeEnum.UPDATE_BUSINESSLIMITCUSTOM_EXCEPTION);
			}
		}else{
			return false;
		}

	}
}
