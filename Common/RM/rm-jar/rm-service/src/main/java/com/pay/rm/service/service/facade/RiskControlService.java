package com.pay.rm.service.service.facade;

import java.util.List;
import java.util.Map;

import com.pay.rm.base.exception.RMFacadeException;
import com.pay.rm.service.dto.rmlimit.rule.BaseRule;

public interface RiskControlService {


	/**
	 * type 规则类型 RCRuleType枚举
	 * businessId 业务ID 根据规则类型，传入相关的业务ID
	 * 
	 */
	public List<BaseRule> getRules(long businessId,int type);
	/**
	 * 
	 * @param ruleType  规则类型 RCRuleType.type
	 * @param map
	 * businessId 商户会员ID(商户必选) //用来取的商户客制化的限额限次规则
	 * riskLevel风险等级(1-5)(商户必选) 目前没有定义好，默认为5
	 * userLevel会员认证等级(个人用户必选)
	 * @return
	 */
	public List<BaseRule> getRules(int ruleType,Map<String,String> map);

	/**
	 * 
	 * @param mcc MCC编码 一个四位的数字
	 * @param userlevel 风险等级，商户分5级
	 * @return 获得一个参数表,作为限额限次建议值
	 */
	public List<Map<String, Long>> getMCCLimits(int mcc,int riskLevel);
	/**
	 * 商户开户时通过这个接口设置限额限次数据
	 * 
	 * @param businessId
	 * @param mcc
	 * @param userlevel
	 * @return 风控MIS设置限额限次参数值
	 */
	public boolean setMccLimit(long businessId,int mcc,List<Map<String, Long>> listLimit);
	/**
	 * 商户开户时,设置商户客制限额默认值
	 * @param businessId 商户编码
	 * @param mcc  行业编码
	 * @param riskLevel 风险等级1~5
	 * @return
	 */
	public boolean setDefaultMccLimit(long businessId,int mcc,int riskLevel) throws RMFacadeException;
	/**
	 * 判断MCC是否存在
	 * @param mcc
	 * @return
	 */
	public boolean isExistMcc(int mcc)throws RMFacadeException;
	
	/**
	 * 修改商户客制限额默认值
	 * @param businessId 商户编码
	 * @param mcc  行业编码
	 * @param riskLevel 风险等级1~5
	 * @return
	 */
	public boolean updateBusinessLimitCustomTrans(long businessId,int mcc,int riskLevel) throws RMFacadeException;
}
