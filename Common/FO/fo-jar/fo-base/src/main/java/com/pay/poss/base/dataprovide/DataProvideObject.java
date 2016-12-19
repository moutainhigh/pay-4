/** @Description 
 * @project 	poss-base
 * @file 		CacheObject.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-8-25		Henry.Zeng			Create 
 */
package com.pay.poss.base.dataprovide;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.base.exception.PlatformDaoException;
import com.pay.poss.base.exception.PossUntxException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.base.model.ParamsFileParser;

/**
 * <p>
 * 加载缓存信息类
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-8-25
 * @see
 */
public class DataProvideObject implements InitializingBean {

	protected transient Log log = LogFactory.getLog(getClass());

	private BaseDAO baseDao;

	private final static Map<String, List<ParamsFileParser>> dataProvideMap = new HashMap<String, List<ParamsFileParser>>();

	private List<ParamsFileParser> dataProvideList = null;

	public void setBaseDao(BaseDAO baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * 如果list为空 重新加载
	 * 
	 * @author Henry.Zeng
	 * @see
	 */
	public void init() throws PlatformDaoException {
		dataProvideList = baseDao.findByQuery("DP.queryParamsFileParserList",
				"");
		dataProvideMap.put("dataProvideList", dataProvideList);
	}

	/**
	 * 
	 * @return
	 * @author Henry.Zeng
	 * @see
	 */
	public List<ParamsFileParser> getDateProvideList() {
		if (dataProvideMap.get("dataProvideList") == null) {
			init();
		}
		return dataProvideList;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		try {
			init();
		} catch (PlatformDaoException e) {
			log.error("---加载ParamsFileParser信息失败...." + e.getMessage());
		}
	}

	/**
	 * 通过银行查询参数配置信息
	 * 
	 * @param bankCode
	 * @return
	 * @author Henry.Zeng
	 * @see
	 */
	public ParamsFileParser getParamsFileParserByBankCode(String bankCode)
			throws PossUntxException {
		for (ParamsFileParser paramsFileParser : getDateProvideList()) {
			if (bankCode.equals(paramsFileParser.getBankCode())) {
				return paramsFileParser;
			}
		}
		throw new PossUntxException("通过银行Code获取缓存信息失败,对应的银行Code是:" + bankCode,
				ExceptionCodeEnum.ILLEGAL_PARAMETER);
	}

	/**
	 * 通过银行查询参数配置信息
	 * 
	 * @param bankCode
	 * @return
	 * @author Henry.Zeng
	 * @see
	 */
	public ParamsFileParser getParamsFileParserByProviderCode(
			String providerCode) throws PossUntxException {
		for (ParamsFileParser paramsFileParser : getDateProvideList()) {
			if (providerCode.equals(paramsFileParser.getProviderCode())) {
				return paramsFileParser;
			}
		}
		throw new PossUntxException("通过银行服务代码获取缓存信息失败,对应的银行服务代码是:"
				+ providerCode, ExceptionCodeEnum.ILLEGAL_PARAMETER);
	}

}
