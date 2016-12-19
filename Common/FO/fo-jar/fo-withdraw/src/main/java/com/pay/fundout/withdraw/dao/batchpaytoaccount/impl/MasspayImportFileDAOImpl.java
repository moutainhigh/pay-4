/**
 *  File: MasspayImportFileDAOImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-26     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dao.batchpaytoaccount.impl;

import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.dao.batchpaytoaccount.MasspayImportFileDAO;
import com.pay.fundout.withdraw.model.batchpaytoaccount.MasspayImportFile;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author darv
 * 
 */
@SuppressWarnings("unchecked")
public class MasspayImportFileDAOImpl extends BaseDAOImpl<MasspayImportFile>
		implements MasspayImportFileDAO {

	@Override
	public long createMasspayImportFile(MasspayImportFile masspayImportFile) {
		return (Long) this.create("create", masspayImportFile);
	}

	@Override
	public Long getSeqId() {
		return (Long) this.getSqlMapClientTemplate().queryForObject(
				this.namespace.concat("getSeqId"));
	}

	@Override
	public Integer isExistForBatchNum(Map params) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				this.namespace.concat("isExistForBatchNum"), params);
	}

	@Override
	public List getImportFileByOperatorsAll(Map params) {
		return this.getSqlMapClientTemplate().queryForList(
				this.namespace.concat("getImportFileByOperatorsAll"), params);
	}

	@Override
	public Integer getImportFileByOperatorsCount(Map params) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				this.namespace.concat("getImportFileByOperatorsCount"), params);
	}

	@Override
	public List getImportFileByOperatorsPage(Map params) {
		return this.getSqlMapClientTemplate().queryForList(
				this.namespace.concat("getImportFileByOperatorsPage"), params);
	}

	@Override
	public Long getDayTotalAmount(Long memberCode) {
		return (Long) getSqlMapClientTemplate().queryForObject(
				namespace.concat("getDayTotalAmount"), memberCode);
	}

	@Override
	public Long getMonthTotalAmount(Long memberCode) {
		return (Long) getSqlMapClientTemplate().queryForObject(
				namespace.concat("getMonthTotalAmount"), memberCode);
	}

	@Override
	public Integer getMonthTotalCount(Long memberCode) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				namespace.concat("getMonthTotalCount"), memberCode);
	}

	@Override
	public Integer getDayTotalCount(Long memberCode) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				namespace.concat("getDayTotalCount"), memberCode);
	}

	@Override
	public void updateBatchNum(Map params) {
		getSqlMapClientTemplate().update(namespace.concat("updateBatchNum"),
				params);
	}

	@Override
	public MasspayImportFile getImportFileByBatchNumAndPayer(Map params) {
		return (MasspayImportFile) getSqlMapClientTemplate().queryForObject(
				namespace.concat("getImportFileByBatchNumAndPayer"), params);
	}

	@Override
	public boolean updateMasspayImportFile(MasspayImportFile masspayImportFile) {
		int result = getSqlMapClientTemplate().update(
				namespace.concat("update"), masspayImportFile);
		return result == 1;
	}
}
