/**
 *  File: BankBrancheInfoService.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-2   terry     Create
 *
 */
package com.pay.lucene.service;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.lucene.dto.BankBrancheInfoDto;

/**
 * 
 */
public interface BankBrancheInfoService {

	/**
	 * 
	 * @param id
	 * @return
	 */
	public BankBrancheInfoDto findById(final Long id);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public BankBrancheInfoDto findByBankName(final String bankName);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public BankBrancheInfoDto findByBankNameAndType(final String bankName,
			final Integer type);

	/**
	 * 
	 * @param bankInfo
	 * @return
	 */
	public long addBankBrancheRnTx(BankBrancheInfoDto bankInfo);

	/**
	 * 
	 * @param bankInfo
	 * @return
	 */
	public boolean updateBankBrancheRnTx(BankBrancheInfoDto bankInfo);

	/**
	 * 
	 * @param bankInfo
	 * @return
	 */
	public boolean delBankBrancheRnTx(final Long id);

	/**
	 * 获取所有开户行信息
	 * 
	 * @return
	 */
	public List<BankBrancheInfoDto> loadAll();

	/**
	 * 获取所有有效开户行信息
	 * 
	 * @return
	 */
	public List<BankBrancheInfoDto> loadAllActive();

	/**
	 * 获取所有普通银行有效开户行信息
	 * 
	 * @return
	 */
	public List<BankBrancheInfoDto> loadCommonBanks();

	/**
	 * 获取指定银行
	 * 
	 * @return
	 */
	public List<BankBrancheInfoDto> loadSpecialBanks(final String bankName);

	
	/**
	 * 获取所有开户行信息
	 * 
	 * @return
	 */
	public List<BankBrancheInfoDto> findByCondition(BankBrancheInfoDto bankInfo);

	/**
	 * 获取所有开户行信息
	 * 
	 * @return
	 */
	public List<BankBrancheInfoDto> findByCondition(Page page,
			BankBrancheInfoDto bankInfo);
}
