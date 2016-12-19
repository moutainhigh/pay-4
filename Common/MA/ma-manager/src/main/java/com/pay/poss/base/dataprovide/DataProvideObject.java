/** @Description 
 * @project 	poss-base
 * @file 		CacheObject.java 
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
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
import com.pay.poss.base.model.ParamsFileParser;
import com.pay.rm.base.exception.enums.ExceptionCodeEnum;

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

	private transient BaseDAO baseDao;

	public void setBaseDao(BaseDAO baseDao) {
		this.baseDao = baseDao;
	}

	private final static Map<String, List<ParamsFileParser>> dateProvideMap = new HashMap<String, List<ParamsFileParser>>();

	private List<ParamsFileParser> dateProvideList = null;

	/**
	 * 如果list为空 重新加载
	 * 
	 * @author Henry.Zeng
	 * @see
	 */
	public void init() throws PlatformDaoException {
		List<ParamsFileParser> tempDateProvideList = baseDao.findByQuery(
				"DP.queryParamsFileParserList", "");
		dateProvideMap.put("dateProvideList", tempDateProvideList);
	}

	/**
	 * 
	 * @return
	 * @author Henry.Zeng
	 * @see
	 */
	public List<ParamsFileParser> getDateProvideList() {
		if (dateProvideMap.get("dateProvideList") != null) {
			dateProvideList = dateProvideMap.get("dateProvideList");
			if (dateProvideList == null) {
				init();
			}
		}
		return dateProvideList;
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
