/**
 *  File: BankBrancheInfoDaoImpl.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-2   terry     Create
 *
 */
package com.pay.lucene.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.lucene.dao.BankBrancheInfoDao;
import com.pay.lucene.model.BankBrancheInfo;

/**
 * 
 */
public class BankBrancheInfoDaoImpl extends BaseDAOImpl<BankBrancheInfo>
		implements BankBrancheInfoDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.lucene.dao.BankBrancheInfoDao#addBankBranche(com.pay.lucene
	 * .model.BankBrancheInfo)
	 */
	@Override
	public long addBankBranche(final BankBrancheInfo bankInfo) {

		return (Long) super.create(bankInfo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.lucene.dao.BankBrancheInfoDao#loadAll()
	 */
	@Override
	public List<BankBrancheInfo> loadAll() {

		return getSqlMapClientTemplate().queryForList(
				namespace.concat("loadAll"));
	}

	/**
	 * 获取所有有效开户行信息
	 * 
	 * @return
	 */
	@Override
	public List<BankBrancheInfo> loadAllActive() {
		return getSqlMapClientTemplate().queryForList(
				namespace.concat("loadAllActive"));
	}

	@Override
	public boolean delBankBranche(final Long id) {

		return super.delete(id);
	}

	
	@Override
	public List<BankBrancheInfo> findByCondition(final BankBrancheInfo bankInfo) {

		return getSqlMapClientTemplate().queryForList(
				namespace.concat("findByCondition"), bankInfo);
	}

	@Override
	public List<BankBrancheInfo> findByCondition(final Page page,
			final BankBrancheInfo bankInfo) {

		Integer totalCount = (Integer) getSqlMapClientTemplate()
				.queryForObject(namespace.concat("findByCondition_COUNT"),
						bankInfo);
		page.setTotalCount(totalCount);
		return getSqlMapClientTemplate().queryForList(
				namespace.concat("findByCondition"), bankInfo, page.getFirst(),
				page.getPageSize());
	}

	@Override
	public boolean updateBankBranche(final BankBrancheInfo bankInfo) {

		return super.update(bankInfo);
	}

	@Override
	public BankBrancheInfo findById(final Long id) {

		return super.findById(id);
	}

	@Override
	public BankBrancheInfo findByBankName(final String bankName) {

		List result = getSqlMapClientTemplate().queryForList(
				namespace.concat("findByBankName"), bankName);

		if (null != result && !result.isEmpty()) {
			return (BankBrancheInfo) result.get(0);
		} else {
			return null;
		}
	}

	@Override
	public BankBrancheInfo findByBankNameAndType(final String bankName,
			final Integer type) {
		Map paraMap = new HashMap();
		paraMap.put("bankName", bankName);
		paraMap.put("type", type);
		List result = getSqlMapClientTemplate().queryForList(
				namespace.concat("findByBankNameAndType"), paraMap);
		if (null != result && !result.isEmpty()) {
			return (BankBrancheInfo) result.get(0);
		} else {
			return null;
		}

	}

	@Override
	public List<BankBrancheInfo> loadCommonBanks() {
		return getSqlMapClientTemplate().queryForList(
				namespace.concat("loadCommonBanks"));
	}

	@Override
	public List<BankBrancheInfo> loadSpecialBanks(final String bankName) {
		return getSqlMapClientTemplate().queryForList(
				namespace.concat("loadSpecialBanks"), bankName);
	}

}
