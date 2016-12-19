/** @Description 
 * @project 	fo-channel-manager
 * @file 		ConfigBankDAOImpl.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-2		Henry.Zeng			Create 
 */
package com.pay.fundout.channel.dao.configchannelrelation.impl;

import java.util.List;

import com.pay.fundout.channel.dao.configchannelrelation.ConfigChannelRelationDAO;
import com.pay.fundout.channel.model.configchannelrelation.FundoutConfigChannelRelation;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * <p>
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-11-2
 * @see
 */
public class ConfigChannelRelationDAOImpl extends
		BaseDAOImpl<FundoutConfigChannelRelation> implements
		ConfigChannelRelationDAO {

	@Override
	public long addConfigChannelRelation(FundoutConfigChannelRelation model) {
		// TODO Auto-generated method stub
		return (Long) super.create(model);
	}

	@Override
	public int modifyConfigChannelRelation(FundoutConfigChannelRelation model) {
		// TODO Auto-generated method stub
		return super.update(model) == true ? 1 : 0;
	}

	@Override
	public Page<FundoutConfigChannelRelation> queryConfigChannelRelation(
			Page<FundoutConfigChannelRelation> page,
			FundoutConfigChannelRelation pojo) {
		return super.findByQuery("findBySelective", page, pojo);
	}

	@Override
	public FundoutConfigChannelRelation queryConfigChannelRelationById(
			Long configId) {
		return super.findById(configId);
	}

	@Override
	public List queryConfigChannelRelation(FundoutConfigChannelRelation pojo) {
		return super.findByQuery("findBySelective", pojo);
	}

}
