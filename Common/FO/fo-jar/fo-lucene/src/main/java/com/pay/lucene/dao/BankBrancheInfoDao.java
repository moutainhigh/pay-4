/**
 *  File: BankBrancheInfoDao.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-2   terry     Create
 *
 */
package com.pay.lucene.dao;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.lucene.model.BankBrancheInfo;

/**
 * 
 */
public interface BankBrancheInfoDao {
 
	/**
	 * 
	 * @param id
	 * @return
	 */
	public BankBrancheInfo findById(final Long id);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public BankBrancheInfo findByBankName(final String bankName);

	public BankBrancheInfo findByBankNameAndType(final String bankName,
			final Integer type);

	/**
	 * 
	 * @param bankInfo
	 * @return
	 */
	public long addBankBranche(BankBrancheInfo bankInfo);

	/**
	 * 
	 * @param bankInfo
	 * @return
	 */
	public boolean updateBankBranche(BankBrancheInfo bankInfo);

	/**
	 * 
	 * @param bankInfo
	 * @return
	 */
	public boolean delBankBranche(final Long id);

	/**
	 * 获取所有开户行信息
	 * 
	 * @return 
	 */
	public List<BankBrancheInfo> loadAll();

	/**
	 * 获取所有有效开户行信息
	 * 
	 * @return
	 */
	public List<BankBrancheInfo> loadAllActive();

	/**
	 * 
	 * @return
	 */
	public List<BankBrancheInfo> loadCommonBanks();

	/**
	 * 
	 * @return
	 */
	public List<BankBrancheInfo> loadSpecialBanks(final String bankName);

	/**
	 * 获取所有开户行信息
	 * 
	 * @return
	 */
	public List<BankBrancheInfo> findByCondition(BankBrancheInfo bankInfo);

	/**
	 * 获取所有开户行信息
	 * 
	 * @return
	 */
	public List<BankBrancheInfo> findByCondition(Page page,
			BankBrancheInfo bankInfo);
}
