/** @Description 
 * @project 	fo-channel-manager
 * @file 		ConfigBankDAOImpl.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-2		Henry.Zeng			Create 
 */
package com.pay.fundout.channel.dao.configbank.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pay.fundout.channel.dao.configbank.ConfigBankDAO;
import com.pay.fundout.channel.model.configbank.FundoutConfigBank;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.base.exception.PlatformDaoException;

/**
 * <p>
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-11-2
 * @see
 */
public class ConfigBankDAOImpl extends BaseDAOImpl implements ConfigBankDAO {

	@Override
	public long addConfigBank(FundoutConfigBank model) {
		return (Long) super.create(model);
	}

	@Override
	public int modifyConfigBank(FundoutConfigBank model) {
		return super.update(model) == true ? 1 : 0;
	}

	@Override
	public Page<FundoutConfigBank> queryConfigBank(
			Page<FundoutConfigBank> page, FundoutConfigBank pojo) {
		return super.findByQuery("findBySelective", page, pojo);
	}

	@Override
	public FundoutConfigBank queryConfigBankById(Long configId) {
		return (FundoutConfigBank) super.findById(configId);
	}

	@Override
	public String queryFoBank2Withdraw(Map<String, Object> params) {

		String withdrawBankId = "";
		try {
			withdrawBankId = (String) super.findObjectByCriteria(
					"findFoBank2Withdraw", params);
			logger.info("withdrawBankId:" + withdrawBankId);
			if (StringUtils.isNotEmpty(withdrawBankId)) {
				return withdrawBankId;
			}
		} catch (PlatformDaoException e) {
			logger.error(e.getMessage(), e);
		}
		return withdrawBankId;
	}

	@Override
	public List queryConfigBank(FundoutConfigBank pojo) {
		return super.findByQuery("findBySelective", pojo);
	}

	@Override
	public List getAllConfigBankList() {
		return super.findByQuery("getConfigBankList", new HashMap());
	}

}
