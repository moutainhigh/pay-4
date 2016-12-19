/**
 * 
 */
package com.pay.poss.enterprisemanager.service;

import java.util.List;

import com.pay.poss.enterprisemanager.dto.OperatorSearchDto;
import com.pay.poss.enterprisemanager.dto.OperatorSearchListDto;

/**
 * @Description 
 * @project 	ma-manager
 * @file 		IOperatorService.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 */
public interface IOperatorService {
	
	List<OperatorSearchListDto> queryOperator(OperatorSearchDto dto);
	
	int queryOperatorCount(OperatorSearchDto dto);
	
	OperatorSearchListDto queryOperator(String operatorId);

}
