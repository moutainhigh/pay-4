/**
 *  File: WithdrawRuleServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-15     jason.li    Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.withdrawrule.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fundout.withdraw.dao.withdrawbankconfig.WithdrawBankConfigDAO;
import com.pay.fundout.withdraw.dao.withdrawbusiness.WithdrawBusinessDAO;
import com.pay.fundout.withdraw.dao.withdrawrule.WithdrawRuleDAO;
import com.pay.fundout.withdraw.dao.withdrawtype.WithdrawTypeDAO;
import com.pay.fundout.withdraw.dto.bank.WithdrawBankConfigDTO;
import com.pay.fundout.withdraw.dto.busitype.WithdrawBusinessDTO;
import com.pay.fundout.withdraw.dto.rule.WithdrawRuleDTO;
import com.pay.fundout.withdraw.dto.type.WithdrawTypeDTO;
import com.pay.fundout.withdraw.model.bank.WithdrawBankConfig;
import com.pay.fundout.withdraw.model.busitype.WithdrawBusiness;
import com.pay.fundout.withdraw.model.rule.WithdrawRule;
import com.pay.fundout.withdraw.model.type.WithdrawType;
import com.pay.fundout.withdraw.service.withdrawrule.WithdrawRuleService;

/**
 * @author jason.li
 * 
 */
public class WithdrawRuleServiceImpl implements WithdrawRuleService {
	private Log log = LogFactory.getLog(WithdrawRuleServiceImpl.class);
	private WithdrawRuleDAO withdrawRuleDao;
	private WithdrawTypeDAO withdrawTypeDao;
	private WithdrawBusinessDAO withdrawBusinessDao;
	private WithdrawBankConfigDAO withdrawBankConfigDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.fundout.withdraw.service.withdrawrule.WithdrawRuleService#getRule
	 * (int, long, int, int)
	 */
	@Override
	public WithdrawRule getRule(Map<String, String> map) {
		WithdrawRule rule = null;
		try {
			int destBankId = Integer.parseInt(map.get("destBankId"));
			long amount = Long.parseLong(map.get("amount"));
			int mtype = Integer.parseInt(map.get("mtype"));
			int bankAccoutType = Integer.parseInt(map.get("bankAccoutType"));
			rule = withdrawRuleDao.getRule(destBankId, amount, mtype,
					bankAccoutType);
		} catch (NumberFormatException ne) {
			log.error("获得银行出款规则失败:参数错误：" + ne.getMessage());
		} catch (Exception e) {
			log.error("获得银行出款规则失败：" + e.getMessage());
			e.printStackTrace();
			rule = null;
		}
		return rule;

	}

	@Override
	public long createWithdrawRule(WithdrawRule wr) {
		return (Long) withdrawRuleDao.create(wr);
	}

	@Override
	public long createWithdrawBankConfig(WithdrawBankConfig wb) {
		return (Long) withdrawBankConfigDao.create(wb);
	}

	@Override
	public long createWithdrawBusiness(WithdrawBusiness wb) {
		return (Long) withdrawBusinessDao.create(wb);
	}

	@Override
	public long createWithdrawType(WithdrawType wt) {
		return (Long) withdrawTypeDao.create(wt);
	}

	@Override
	public List<WithdrawBankConfigDTO> searchBankConfigs(Map<String, String> map) {
		return withdrawBankConfigDao.findByQuery("withdrawbankconfig.search",
				map);
	}

	@Override
	public List<WithdrawBusinessDTO> searchBusinesses(Map<String, String> map) {
		return withdrawBusinessDao.findByQuery("withdrawbusiness.search", map);
	}

	@Override
	public List<WithdrawTypeDTO> searchTypes(Map<String, String> map) {
		return withdrawTypeDao.findByQuery("withdrawtype.search", map);
	}

	@Override
	public boolean updateWithdrawBankConfig(WithdrawBankConfig wbc) {
		return withdrawBankConfigDao.update(wbc);
	}

	@Override
	public boolean updateWithdrawBusiness(WithdrawBusiness wb) {
		return withdrawBusinessDao.update(wb);
	}

	@Override
	public boolean updateWithdrawRule(WithdrawRule wr) {
		return withdrawRuleDao.update(wr);
	}

	@Override
	public boolean updateWithdrawType(WithdrawType wt) {
		return withdrawTypeDao.update(wt);
	}

	/**
	 * @param withdrawRuleDao
	 *            the withdrawRuleDao to set
	 */
	public void setwithdrawRuleDao(WithdrawRuleDAO withdrawRuleDao) {
		this.withdrawRuleDao = withdrawRuleDao;
	}

	/**
	 * @param withdrawTypeDao
	 *            the withdrawTypeDao to set
	 */
	public void setwithdrawTypeDao(WithdrawTypeDAO withdrawTypeDao) {
		this.withdrawTypeDao = withdrawTypeDao;
	}

	/**
	 * @param withdrawBusinessDao
	 *            the withdrawBusinessDao to set
	 */
	public void setwithdrawBusinessDao(WithdrawBusinessDAO withdrawBusinessDao) {
		this.withdrawBusinessDao = withdrawBusinessDao;
	}

	/**
	 * @param withdrawBankConfigDao
	 *            the withdrawBankConfigDao to set
	 */
	public void setwithdrawBankConfigDao(
			WithdrawBankConfigDAO withdrawBankConfigDao) {
		this.withdrawBankConfigDao = withdrawBankConfigDao;
	}

	@Override
	public List<WithdrawRuleDTO> searchRules(Map<String, String> map) {
		return withdrawRuleDao.findByQuery("withdrawrule.search", map);
	}

	@Override
	public int getWithdrawType(Map<String, String> map) {
		Integer otype = (Integer) withdrawRuleDao.findObjectByCriteria(
				"withdrawrule.getWithdrawType", map);
		if (otype != null) {
			return otype.intValue();
		} else {
			return -1;
		}
	}

}
