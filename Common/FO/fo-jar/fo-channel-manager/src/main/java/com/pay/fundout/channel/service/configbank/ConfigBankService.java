 /** @Description 
 * @project 	fo-channel-manager
 * @file 		ConfigBankService.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-2		Henry.Zeng			Create 
*/
package com.pay.fundout.channel.service.configbank;

import java.util.List;
import java.util.Map;

import com.pay.fundout.channel.dto.configbank.FundoutConfigBankDTO;
import com.pay.fundout.channel.model.configbank.FundoutConfigBank;
import com.pay.inf.dao.Page;

/**
 * <p>配置出款行与目的行的映射</p>
 * @author Henry.Zeng
 * @since 2010-11-2
 * @see 
 */
public interface ConfigBankService {
	/**
	 * 添加配置银行信息
	 * @param dto
	 * @return
	 */
	public Map<String,Object> addConfigBank(FundoutConfigBankDTO dto);
	/**
	 * 更新配置银行信息
	 * @param dto
	 * @return
	 */
	public int modifyConfigBankRdTx(FundoutConfigBankDTO dto);
	
	/**
	 * 更新配置银行信息
	 * @param dto
	 * @return
	 */
	public boolean delConfigBankRdTx(final Long configId);
	/**
	 * 查询配置银行信息ById
	 * @param configId
	 * @return
	 */
	public FundoutConfigBankDTO queryConfigBankById(Long configId );
	/**
	 * 
	 * @param page
	 * @param fundoutConfigBankDTO
	 * @return
	 */
	public Map<String,Object> queryConfigBank(Page<FundoutConfigBankDTO> page , FundoutConfigBankDTO fundoutConfigBankDTO );
	
	
	/**
	 * @param 	 
	 * 【params 包含值
	 *   targetBankId 目的银行编号
	 *   foMode  出款方式
	 *   fobusiness  出款业务
	 * 】
	 * @return  fundoutBank 出款银行
	 */
	String queryFundOutBank2Withdraw(Map<String,Object> params); 
	
	/**
	 * 查询出款银行
	 * @param pojo
	 * @return FundoutConfigBank
	 */
	public List<FundoutConfigBank> queryConfigBank(FundoutConfigBank pojo );
	
	/**
	 * 获取已经配置过的银行,供查询条件使用
	 * @return
	 */
	public List<Map<String, String>> getAllConfigBankList();
}
