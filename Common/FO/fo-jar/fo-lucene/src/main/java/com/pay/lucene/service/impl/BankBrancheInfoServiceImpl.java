/**
 *  File: BankBrancheInfoServiceImpl.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-2   terry     Create
 *
 */
package com.pay.lucene.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.lucene.dao.BankBrancheInfoDao;
import com.pay.lucene.dto.BankBrancheInfoDto;
import com.pay.lucene.model.BankBrancheInfo;
import com.pay.lucene.service.BankBrancheInfoService;
import com.pay.util.BeanConvertUtil;

/**
 * 
 */
public class BankBrancheInfoServiceImpl implements BankBrancheInfoService {

	private BankBrancheInfoDao bankBrancheInfoDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.lucene.service.BankBrancheInfoService#addBankBranche(com.pay
	 * .lucene.dto.BankBrancheInfoDto)
	 */
	@Override
	public long addBankBrancheRnTx(final BankBrancheInfoDto bankInfo) {

		return bankBrancheInfoDao.addBankBranche(BeanConvertUtil.convert(
				BankBrancheInfo.class, bankInfo));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.lucene.service.BankBrancheInfoService#loadAll()
	 */
	@Override
	public List<BankBrancheInfoDto> loadAll() {
		List<BankBrancheInfoDto> resultList = null;
		if (null == resultList) {
			List<BankBrancheInfo> list = bankBrancheInfoDao.loadAll();
			if (null != list && !list.isEmpty()) {
				resultList = new ArrayList<BankBrancheInfoDto>();
				for (BankBrancheInfo bankInfo : list) {
					resultList.add(BeanConvertUtil.convert(
							BankBrancheInfoDto.class, bankInfo));
				}
			}
		}

		return resultList;
	}

	/**
	 * 获取所有有效开户行信息
	 * 
	 * @return
	 */
	@Override
	public List<BankBrancheInfoDto> loadAllActive() {
		List<BankBrancheInfoDto> resultList = null;
		if (null == resultList) {
			List<BankBrancheInfo> list = bankBrancheInfoDao.loadAllActive();
			if (null != list && !list.isEmpty()) {
				resultList = new ArrayList<BankBrancheInfoDto>();
				for (BankBrancheInfo bankInfo : list) {
					resultList.add(BeanConvertUtil.convert(
							BankBrancheInfoDto.class, bankInfo));
				}
			}
		}

		return resultList;
	}

	public void setBankBrancheInfoDao(final BankBrancheInfoDao bankBrancheInfoDao) {
		this.bankBrancheInfoDao = bankBrancheInfoDao;
	}

	@Override
	public boolean delBankBrancheRnTx(final Long id) {

		return bankBrancheInfoDao.delBankBranche(id);
	}

	
	
	@Override
	public List<BankBrancheInfoDto> findByCondition(final BankBrancheInfoDto bankInfo) {

		List<BankBrancheInfoDto> result = null;

		List<BankBrancheInfo> list = bankBrancheInfoDao
				.findByCondition(BeanConvertUtil.convert(BankBrancheInfo.class,
						bankInfo));
		if (null != list && !list.isEmpty()) {
			result = new ArrayList<BankBrancheInfoDto>();
			for (BankBrancheInfo bank : list) {
				result.add(BeanConvertUtil.convert(BankBrancheInfoDto.class,
						bank));
			}
		}
		return result;
	}

	@Override
	public List<BankBrancheInfoDto> findByCondition(final Page page,
			final BankBrancheInfoDto bankInfo) {

		List<BankBrancheInfoDto> result = null;

		List<BankBrancheInfo> list = bankBrancheInfoDao.findByCondition(page,
				BeanConvertUtil.convert(BankBrancheInfo.class, bankInfo));
		if (null != list && !list.isEmpty()) {
			result = new ArrayList<BankBrancheInfoDto>();
			for (BankBrancheInfo bank : list) {
				result.add(BeanConvertUtil.convert(BankBrancheInfoDto.class,
						bank));
			}
		}
		return result;
	}

	@Override
	public boolean updateBankBrancheRnTx(final BankBrancheInfoDto bankInfo) {

		return bankBrancheInfoDao.updateBankBranche(BeanConvertUtil.convert(
				BankBrancheInfo.class, bankInfo));
	}

	@Override
	public BankBrancheInfoDto findById(final Long id) {

		return BeanConvertUtil.convert(BankBrancheInfoDto.class,
				bankBrancheInfoDao.findById(id));
	}

	@Override
	public BankBrancheInfoDto findByBankName(final String bankName) {

		return BeanConvertUtil.convert(BankBrancheInfoDto.class,
				bankBrancheInfoDao.findByBankName(bankName));
	}

	@Override
	public BankBrancheInfoDto findByBankNameAndType(final String bankName,
			final Integer type) {
		return BeanConvertUtil.convert(BankBrancheInfoDto.class,
				bankBrancheInfoDao.findByBankName(bankName));
	}

	@Override
	public List<BankBrancheInfoDto> loadCommonBanks() {
		List<BankBrancheInfoDto> resultList = null;
		if (null == resultList) {
			List<BankBrancheInfo> list = bankBrancheInfoDao.loadCommonBanks();
			if (null != list && !list.isEmpty()) {
				resultList = new ArrayList<BankBrancheInfoDto>();
				for (BankBrancheInfo bankInfo : list) {
					resultList.add(BeanConvertUtil.convert(
							BankBrancheInfoDto.class, bankInfo));
				}
			}
		}

		return resultList;
	}

	@Override
	public List<BankBrancheInfoDto> loadSpecialBanks(final String bankName) {
		List<BankBrancheInfoDto> resultList = null;
		if (null == resultList) {
			List<BankBrancheInfo> list = bankBrancheInfoDao
					.loadSpecialBanks(bankName);
			if (null != list && !list.isEmpty()) {
				resultList = new ArrayList<BankBrancheInfoDto>();
				for (BankBrancheInfo bankInfo : list) {
					resultList.add(BeanConvertUtil.convert(
							BankBrancheInfoDto.class, bankInfo));
				}
			}
		}

		return resultList;
	}

}
