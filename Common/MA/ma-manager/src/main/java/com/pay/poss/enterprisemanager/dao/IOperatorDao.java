package com.pay.poss.enterprisemanager.dao;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.enterprisemanager.dto.OperatorSearchDto;
import com.pay.poss.enterprisemanager.dto.OperatorSearchListDto;

/**
 * @Description 
 * @project 	ma-manager
 * @file 		IOperatorDao.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 */
public interface IOperatorDao extends BaseDAO<OperatorSearchListDto> {
	
	List<OperatorSearchListDto> queryOperator(OperatorSearchDto dto);
	
	int queryOperatorCount(OperatorSearchDto dto);
	
	OperatorSearchListDto queryOperatorById(String operatorId);

}
