package com.pay.acc.service.cidverify.impl;

import com.pay.acc.service.cidverify.IdcVerifyBankService;
import com.pay.acc.service.cidverify.dto.IdcBankVerifyDto;
import com.pay.inf.dao.BaseDAO;

/**
 * @author lei.jiangl
 * @version
 * @data 2010-10-2 下午06:34:35
 */
public class IdcVerifyBankServiceImpl implements IdcVerifyBankService {

	private BaseDAO mainDao;

	public void setMainDao(BaseDAO mainDao) {
		this.mainDao = mainDao;
	}

	@Override
	public IdcBankVerifyDto saveBank(IdcBankVerifyDto idcBankVerify) {
		Long id = (Long) mainDao.create(idcBankVerify);
		idcBankVerify.setId(id);
		return idcBankVerify;
	}
}
