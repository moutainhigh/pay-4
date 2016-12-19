/** @Description 
 * @project 	fo-securitycheck
 * @file 		RiskDataDetail.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-2		Rick_lv			Create 
 */
package com.pay.fundout.securitycheck.riskdata;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.BaseDAO;

/**
 * <p>
 * 基础风控数据提取器
 * </p>
 * 
 * @author Rick_lv
 * @since 2010-11-2
 * @see
 */
public abstract class AbstractRiskDataStatist implements RiskDataStatist {
	protected Log log = LogFactory.getLog(getClass());
	protected BaseDAO daoService;

	public void setDaoService(BaseDAO daoService) {
		this.daoService = daoService;
	}

}
