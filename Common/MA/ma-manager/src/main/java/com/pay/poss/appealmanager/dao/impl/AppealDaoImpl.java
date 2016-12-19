package com.pay.poss.appealmanager.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.appealmanager.dao.IAppealDao;
import com.pay.poss.appealmanager.dto.AppealDto;
import com.pay.poss.appealmanager.dto.AppealTaskListDto;
import com.pay.poss.appealmanager.dto.AppealTaskSearchDto;
import com.pay.poss.appealmanager.model.Appeal;
import com.pay.poss.appealmanager.model.AppealHistory;

/**
 * 
 * @Description
 * @project ma-manager
 * @file AppealDaoImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 *          Date Author Changes 2010-12-20 gungun_zhang Create
 */

public class AppealDaoImpl extends BaseDAOImpl<Appeal> implements IAppealDao {
	private Log log = LogFactory.getLog(AppealDaoImpl.class);

	@Override
	public Long insertAppeal(Appeal appeal) {
		log.debug("AppealDaoImpl.insertAppeal is running...");
		return (Long) this.getSqlMapClientTemplate().insert(
				"appeal.insertAppeal", appeal);

	}

	@Override
	public void insertAppealHistory(AppealHistory appealHistory) {
		log.debug("AppealDaoImpl.insertAppealHistory is running...");
		this.getSqlMapClientTemplate().insert("appeal.insertAppealHistory",
				appealHistory);

	}

	@Override
	public String getAppealCode() {
		log.debug("AppealDaoImpl.getAppealCode is running...");
		return (String) this.getSqlMapClientTemplate().queryForObject(
				"appeal.getAppealCode");
	}

	@Override
	public List<AppealTaskListDto> getAppealListForDispense(
			AppealTaskSearchDto appealTaskSearchDto) {
		log.debug("AppealDaoImpl.getAppealListForDispense is running...");
		return this.getSqlMapClientTemplate().queryForList(
				"appeal.getAppealListForDispense", appealTaskSearchDto);
	}

	@Override
	public List<AppealTaskListDto> getAppealListForCallBack(
			AppealTaskSearchDto appealTaskSearchDto) {
		log.debug("AppealDaoImpl.getAppealListForDispense is running...");
		return this.getSqlMapClientTemplate().queryForList(
				"appeal.getAppealListForCallBack", appealTaskSearchDto);
	}

	@Override
	public List<AppealTaskListDto> getAppealListForDept(
			AppealTaskSearchDto appealTaskSearchDto) {
		log.debug("AppealDaoImpl.getAppealListForDispense is running...");
		return this.getSqlMapClientTemplate().queryForList(
				"appeal.getAppealListForDept", appealTaskSearchDto);
	}

	@Override
	public List<AppealTaskListDto> getAppealListForFinish(
			AppealTaskSearchDto appealTaskSearchDto) {
		log.debug("AppealDaoImpl.getAppealListForDispense is running...");
		return this.getSqlMapClientTemplate().queryForList(
				"appeal.getAppealListForFinish", appealTaskSearchDto);
	}

	@Override
	public List<AppealTaskListDto> getAppealListForSearch(
			AppealTaskSearchDto appealTaskSearchDto) {
		log.debug("AppealDaoImpl.getAppealListForSearch is running...");
		return this.getSqlMapClientTemplate().queryForList(
				"appeal.getAppealListForSearch", appealTaskSearchDto);
	}

	@Override
	public Integer getAppealListForSearchCount(
			AppealTaskSearchDto appealTaskSearchDto) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				"appeal.getAppealListForSearchCount", appealTaskSearchDto);
	}

	@Override
	public Integer getAppealListForDispenseCount() {
		log.debug("AppealDaoImpl.getAppealListForDispense is running...");
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				"appeal.getAppealListForDispenseCount");
	}

	@Override
	public Integer getAppealListForCallBackCount(
			AppealTaskSearchDto appealTaskSearchDto) {
		log.debug("AppealDaoImpl.getAppealListForDispense is running...");
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				"appeal.getAppealListForCallBackCount", appealTaskSearchDto);
	}

	@Override
	public Integer getAppealListForDeptCount(
			AppealTaskSearchDto appealTaskSearchDto) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				"appeal.getAppealListForDeptCount", appealTaskSearchDto);
	}

	@Override
	public Integer getAppealListForFinishCount(
			AppealTaskSearchDto appealTaskSearchDto) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				"appeal.getAppealListForFinishCount", appealTaskSearchDto);
	}

	@Override
	public AppealDto getAppealById(Long appealId) {
		log.debug("AppealDaoImpl.getAppealById is running...");
		return (AppealDto) this.getSqlMapClientTemplate().queryForObject(
				"appeal.getAppealById", appealId);
	}

	@Override
	public void updateAppeal(AppealDto appealDto) {
		log.debug("AppealDaoImpl.updateAppeal is running...");
		this.getSqlMapClientTemplate().update("appeal.updateAppeal", appealDto);

	}

	@Override
	public List<AppealHistory> getAppealHistoryByAppealId(Long appealId) {
		log.debug("AppealDaoImpl.getAppealHistoryByAppealId is running...");
		return this.getSqlMapClientTemplate().queryForList(
				"appeal.getAppealHistoryByAppealId", appealId);
	}

	@Override
	public String vaildateCallBack(String appealId) {
		log.debug("AppealDaoImpl.vaildateCallBack is running...");
		Integer count = (Integer) this.getSqlMapClientTemplate()
				.queryForObject("appeal.vaildateCallBack", appealId);
		if (count > 0) {
			return "true";
		} else {
			return "false";
		}

	}

	@Override
	public String vaildateDept(String appealId) {
		log.debug("AppealDaoImpl.vaildateDept is running...");
		Integer count = (Integer) this.getSqlMapClientTemplate()
				.queryForObject("appeal.vaildateDept", appealId);
		if (count > 0) {
			return "true";
		} else {
			return "false";
		}
	}

	@Override
	public String vaildateDispense(String appealId) {
		log.debug("AppealDaoImpl.vaildateDispense is running...");
		Integer count = (Integer) this.getSqlMapClientTemplate()
				.queryForObject("appeal.vaildateDispense", appealId);
		if (count > 0) {
			return "true";
		} else {
			return "false";
		}
	}

	@Override
	public String vaildateFinish(String appealId) {
		log.debug("AppealDaoImpl.vaildateFinish is running...");
		Integer count = (Integer) this.getSqlMapClientTemplate()
				.queryForObject("appeal.vaildateFinish", appealId);
		if (count > 0) {
			return "true";
		} else {
			return "false";
		}
	}

}
