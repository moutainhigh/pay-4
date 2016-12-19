package com.pay.poss.enterprisemanager.dao.impl;

import java.util.List;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.enterprisemanager.dao.IOperatorDao;
import com.pay.poss.enterprisemanager.dto.OperatorSearchDto;
import com.pay.poss.enterprisemanager.dto.OperatorSearchListDto;

/**
 * @Description
 * @project ma-manager
 * @file OperatorDaoImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0
 */
public class OperatorDaoImpl extends BaseDAOImpl<OperatorSearchListDto>
		implements IOperatorDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<OperatorSearchListDto> queryOperator(OperatorSearchDto dto) {

		return (List<OperatorSearchListDto>) getSqlMapClientTemplate()
				.queryForList(super.getNamespace().concat("queryOperator"), dto);
	}

	@Override
	public int queryOperatorCount(OperatorSearchDto dto) {

		return (Integer) getSqlMapClientTemplate().queryForObject(
				super.getNamespace().concat("queryOperatorCount"), dto);
	}

	@Override
	public OperatorSearchListDto queryOperatorById(String operatorId) {

		return (OperatorSearchListDto) getSqlMapClientTemplate()
				.queryForObject(super.getNamespace().concat("queryOperatorById"),operatorId);
	}

}
