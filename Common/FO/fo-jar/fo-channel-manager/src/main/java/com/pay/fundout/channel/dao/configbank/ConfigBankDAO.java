/** @Description 
 * @project 	fo-channel-manager
 * @file 		ConfigBankDAO.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-2		Henry.Zeng			Create 
 */
package com.pay.fundout.channel.dao.configbank;

import java.util.List;
import java.util.Map;

import com.pay.fundout.channel.model.configbank.FundoutConfigBank;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;

/**
 * <p>
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-11-2
 * @see
 */
public interface ConfigBankDAO extends BaseDAO {
	/**
	 * 添加配置银行信息
	 * 
	 * @param dto
	 * @return
	 */
	public long addConfigBank(FundoutConfigBank model);

	/**
	 * 更新配置银行信息
	 * 
	 * @param dto
	 * @return
	 */
	public int modifyConfigBank(FundoutConfigBank model);

	/**
	 * 查询配置银行信息ById
	 * 
	 * @param configId
	 * @return
	 */
	public FundoutConfigBank queryConfigBankById(Long configId);

	/**
	 * 查询出款银行信息
	 * 
	 * @param configId
	 * @return
	 */
	public String queryFoBank2Withdraw(Map<String, Object> params);

	/**
	 * 
	 * @param page
	 * @param fundoutConfigBankDTO
	 * @return
	 */
	public Page<FundoutConfigBank> queryConfigBank(
			Page<FundoutConfigBank> page, FundoutConfigBank pojo);

	/**
	 * 查询出款银行
	 * 
	 * @param pojo
	 * @return FundoutConfigBank
	 */
	public List<FundoutConfigBank> queryConfigBank(FundoutConfigBank pojo);

	/**
	 * 获取已经配置过的银行,供查询条件使用
	 * 
	 * @return
	 */
	public List getAllConfigBankList();
}
