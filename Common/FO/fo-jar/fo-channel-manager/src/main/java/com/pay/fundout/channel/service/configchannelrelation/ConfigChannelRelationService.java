 /** @Description 
 * @project 	fo-channel-manager
 * @file 		ConfigBankService.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-2		Henry.Zeng			Create 
*/
package com.pay.fundout.channel.service.configchannelrelation;

import java.util.List;
import java.util.Map;

import com.pay.fundout.channel.dto.configchannelrelation.FundoutConfigChannelRelationDTO;
import com.pay.fundout.channel.model.configchannelrelation.FundoutConfigChannelRelation;
import com.pay.inf.dao.Page;

/**
 * <p>配置出款产品与出款渠道的映射</p>
 * @author Henry.Zeng
 * @since 2010-11-2
 * @see 
 */
public interface ConfigChannelRelationService {
	/**
	 * 添加配置信息
	 * @param dto
	 * @return
	 */
	public Map<String,Object> addConfigChannelRelation(FundoutConfigChannelRelationDTO dto);
	/**
	 * 更新配置信息
	 * @param dto
	 * @return
	 */
	public int modifyConfigChannelRelationRdTx(FundoutConfigChannelRelationDTO dto);
	
	/**
	 * 更新配置信息
	 * @param dto
	 * @return
	 */
	public boolean delConfigChannelRelationRdTx(final Long configId);
	/**
	 * 查询配置信息ById
	 * @param configId
	 * @return
	 */
	public FundoutConfigChannelRelationDTO queryConfigChannelRelationById(Long configId );
	/**
	 * 
	 * @param page
	 * @param fundoutConfigBankDTO
	 * @return
	 */
	public Map<String,Object> queryConfigChannelRelation(Page<FundoutConfigChannelRelationDTO> page , FundoutConfigChannelRelationDTO fundoutConfigChannelRelationDTO );
	
	
	/**
	 * 查询配置
	 * @param pojo
	 * @return FundoutConfigBank
	 */
	public List<FundoutConfigChannelRelation> queryConfigChannelRelation(FundoutConfigChannelRelation pojo );
	
}
