package com.pay.poss.appealmanager.service;



import java.util.List;
import java.util.Map;

import com.pay.poss.appealmanager.dto.BaseDataDto;
import com.pay.poss.appealmanager.dto.BaseDataRelationDto;

/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		IDataService.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-12-20		gungun_zhang			Create
 */
 

public interface IDataService {
	public Map<String,Object> getBaseDataByType(List<String> baseDataByTypeList);
	public List<BaseDataDto> getAllBaseData();
	public List<BaseDataDto> getAppealStatus();
	public List<BaseDataDto> getDept();
	public List<BaseDataDto> queryBaseData(BaseDataDto baseDataDto);
	public Integer queryBaseDataCount(BaseDataDto baseDataDto);
	public List<BaseDataRelationDto> queryBaseDataRelation(BaseDataRelationDto baseDataRelationDto);
	public Integer queryBaseDataRelationCount(BaseDataRelationDto baseDataRelationDto);
	public String isBaseDataCodeExist(String baseDataCode);
	public String isRelationExist(String fatherDataCode,String sonDataCode);
	public String insertAppealBaseData(BaseDataDto BaseDataDto);
	public String insertAppealBaseDataRelation(BaseDataRelationDto baseDataRelationDto);
	public String deleteAppealBaseData(String baseDataId);
	public String deleteAppealBaseDataRelation(String baseDataRelationId);
}
