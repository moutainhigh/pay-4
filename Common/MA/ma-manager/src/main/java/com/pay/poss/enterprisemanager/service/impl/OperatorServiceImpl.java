package com.pay.poss.enterprisemanager.service.impl;

import java.util.List;

import com.pay.poss.enterprisemanager.dao.IOperatorDao;
import com.pay.poss.enterprisemanager.dto.OperatorSearchDto;
import com.pay.poss.enterprisemanager.dto.OperatorSearchListDto;
import com.pay.poss.enterprisemanager.service.IOperatorService;

/**
 * @Description
 * @project ma-manager
 * @file OperatorServiceImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0
 */
public class OperatorServiceImpl implements IOperatorService {

	private IOperatorDao operatorDao;

	@Override
	public List<OperatorSearchListDto> queryOperator(OperatorSearchDto dto) {

		return operatorDao.queryOperator(dto);
	}

	@Override
	public int queryOperatorCount(OperatorSearchDto dto) {

		return operatorDao.queryOperatorCount(dto);
	}

	@Override
	public OperatorSearchListDto queryOperator(String operatorId) {

		return operatorDao.queryOperatorById(operatorId);
	}

	/**
	 * @param operatorDao
	 *            the operatorDao to set
	 */
	public void setOperatorDao(IOperatorDao operatorDao) {
		this.operatorDao = operatorDao;
	}

}
