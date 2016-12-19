/**
 *  <p>File: AutoFundoutConfigServiceImpl.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author terry_ma
 *  @version 1.0  
 */
package com.pay.fundout.autofundout.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import com.pay.fundout.autofundout.custom.dao.AutoFundoutConfigDAO;
import com.pay.fundout.autofundout.custom.model.AutoFundoutConfig;
import com.pay.fundout.autofundout.dto.AutoFundoutConfigDto;
import com.pay.fundout.autofundout.service.AutoFundoutConfigService;

public class AutoFundoutConfigServiceImpl implements AutoFundoutConfigService {

	private AutoFundoutConfigDAO autoFundoutConfigDAO;
	private Log logger = LogFactory.getLog(AutoFundoutConfigServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.fundout.autofundout.service.AutoFundoutConfigService#findById
	 * (java.lang.Long)
	 */
	@Override
	public AutoFundoutConfigDto findById(Long memberCode) {
		Assert.notNull(memberCode, "memberCode must be not null.");
		AutoFundoutConfig autoFundoutConfig = autoFundoutConfigDAO
				.findById(memberCode);

		AutoFundoutConfigDto autoFundoutConfigDto = null;
		if (null != autoFundoutConfig) {
			autoFundoutConfigDto = new AutoFundoutConfigDto();
			BeanUtils.copyProperties(autoFundoutConfig, autoFundoutConfigDto);
		}
		return autoFundoutConfigDto;
	}

	public void setAutoFundoutConfigDAO(
			AutoFundoutConfigDAO autoFundoutConfigDAO) {
		this.autoFundoutConfigDAO = autoFundoutConfigDAO;
	}

	@Override
	public boolean findByMemberCodeAndBankCard(Long memberCode,
			String bankCard, String bankCode) {

		Assert.notNull(memberCode, "memberCode must be not null.");
		Assert.notNull(bankCard, "bankCard must be not null.");
		Assert.notNull(bankCode, "bankCode must be not null.");

		if (logger.isInfoEnabled()) {
			logger.info("query autofundout member:" + memberCode);
			logger.info("bankCard:" + bankCard);
			logger.info("bankCode:" + bankCode);

		}
		AutoFundoutConfig autoFundoutConfig = new AutoFundoutConfig();
		autoFundoutConfig.setBankCode(bankCode);
		autoFundoutConfig.setBankAccCode(bankCard);
		autoFundoutConfig.setMemberCode(memberCode);
		Integer count = autoFundoutConfigDAO
				.findByMemberCodeAndBankCard(autoFundoutConfig);
		if (count > 0)
			return false;
		return true;
	}

	@Override
	public boolean disableByMemberCodeAndBankCard(Long memberCode,
			String bankCard, String bankCode) {

		Assert.notNull(memberCode, "memberCode must be not null.");
		Assert.notNull(bankCard, "bankCard must be not null.");
		Assert.notNull(bankCode, "bankCode must be not null.");

		AutoFundoutConfig autoFundoutConfig = autoFundoutConfigDAO
				.findById(memberCode);
		return autoFundoutConfigDAO.disable(autoFundoutConfig.getSequenceid());
	}
}
