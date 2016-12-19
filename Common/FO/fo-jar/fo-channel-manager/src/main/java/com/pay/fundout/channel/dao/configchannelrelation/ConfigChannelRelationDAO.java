/** @Description 
 * @project 	fo-channel-manager
 * @file 		ConfigBankDAO.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-2		Henry.Zeng			Create 
 */
package com.pay.fundout.channel.dao.configchannelrelation;

import java.util.List;

import com.pay.fundout.channel.model.configchannelrelation.FundoutConfigChannelRelation;
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
public interface ConfigChannelRelationDAO extends
		BaseDAO<FundoutConfigChannelRelation> {
	/**
	 * 添加配置信息
	 * 
	 * @param dto
	 * @return
	 */
	public long addConfigChannelRelation(FundoutConfigChannelRelation model);

	/**
	 * 更新配置信息
	 * 
	 * @param dto
	 * @return
	 */
	public int modifyConfigChannelRelation(FundoutConfigChannelRelation model);

	/**
	 * 查询配置信息ById
	 * 
	 * @param configId
	 * @return
	 */
	public FundoutConfigChannelRelation queryConfigChannelRelationById(
			Long configId);

	/**
	 * 
	 * @param page
	 * @param fundoutConfigBankDTO
	 * @return
	 */
	public Page<FundoutConfigChannelRelation> queryConfigChannelRelation(
			Page<FundoutConfigChannelRelation> page,
			FundoutConfigChannelRelation pojo);

	/**
	 * 查询配置
	 * 
	 * @param pojo
	 * @return FundoutConfigBank
	 */
	public List<FundoutConfigChannelRelation> queryConfigChannelRelation(
			FundoutConfigChannelRelation pojo);
}
