package com.pay.acc.member.service;

import java.util.List;
import java.util.Map;

import com.pay.acc.member.dto.LiquidateInfoDto;
import com.pay.acc.member.exception.MemberException;
import com.pay.acc.member.exception.MemberUnknowException;
import com.pay.acc.member.model.LiquidateInfo;
import com.pay.inf.dao.Page;

public interface LiquidateInfoService {

	public void insertLiquidateInfo(LiquidateInfoDto liquidateInfo);

	/**
	 * 根据商户会员代码查询LiquidateInfo列表（状态为已经验证）
	 * 
	 * @param memberCode
	 *            会员号
	 * @return 商户会员已经的银行卡绑定列表
	 */
	public List<LiquidateInfoDto> queryLiquidateInfoByMemberCode(Long memberCode)
			throws MemberException, MemberUnknowException;

	/**
	 * 查询结算周期
	 * 
	 * @param memberCode
	 *            会员号
	 * @param accountMode
	 *            结算周期枚举值
	 * @return
	 */
	boolean querySettlePeriod(Long memberCode, int period);
	
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
