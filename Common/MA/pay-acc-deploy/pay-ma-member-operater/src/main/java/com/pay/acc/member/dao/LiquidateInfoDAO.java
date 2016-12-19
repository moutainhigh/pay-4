package com.pay.acc.member.dao;

import java.util.List;
import java.util.Map;

import com.pay.acc.member.model.LiquidateInfo;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;

public interface LiquidateInfoDAO extends BaseDAO<LiquidateInfo>{
	
	/**
	 * 查询结算周期 
	 * @param memberCode  会员号
	 * @param accountMode 结算周期枚举值
	 * @return
	 */
	int countSettlePeriod(Long memberCode,int accountMode);
	
	/**
	 * 查询绑定的银行卡信息列表
	 * @return
	 */
	List<LiquidateInfo> findByCriteria(Map<String, Object> hMap, Page<?> page) ;
	
	/**
	 * 更新审核状态
	 * @param hMap
	 * @return
	 */
	int updateLiquidateInfoStatus(Map<String, Object> hMap) ;

}
