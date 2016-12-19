package com.pay.acc.service.account;

public interface EntryPriseRiskControlService {
	
	
	/**
	 * 根据会员号，风控等级验证
	 * @param 	memberCode 会员号
	 * @param 	level 风控等级
	 * @return  true:false
	 */
	boolean queryRiskControl(Long memberCode,int level);
	
	
	/**根据会员号，结算周期验证
	 * @param memberCode 会员号
	 * @param period 结算周期
	 * @return true:false
	 */
	boolean querySettlePeriod(Long memberCode,int period);
	
	/**查询结算周期
	 * @param memberCode会员号
	 * @return Integer
	 */
	public Integer queryEnterPriseSettlePeriod(Long memberCode);

}
