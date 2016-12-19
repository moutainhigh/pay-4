package com.pay.poss.appealmanager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.poss.appealmanager.common.Constants;
import com.pay.poss.appealmanager.dao.IDataDao;
import com.pay.poss.appealmanager.dto.BaseDataDto;
import com.pay.poss.appealmanager.dto.BaseDataRelationDto;
import com.pay.poss.appealmanager.model.AppealBaseData;
import com.pay.poss.appealmanager.model.AppealBaseDataRelation;
import com.pay.poss.appealmanager.service.IDataService;
/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		DataServiceImpl.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-12-20		gungun_zhang			Create
 */
public class DataServiceImpl implements IDataService{
	private Log log = LogFactory.getLog(DataServiceImpl.class);
	private IDataDao dataDao;
	
	@Override
	public Map<String,Object> getBaseDataByType(List<String> baseDataByTypeList){
		log.debug("DataServiceImpl.getBaseDataByType is running...");
		Map<String,Object> dataMap = new HashMap<String,Object>();
		if(baseDataByTypeList!=null){
			for(int i=0;i<baseDataByTypeList.size();i++){
				String baseDataType = baseDataByTypeList.get(i);
				if(baseDataType.equals(Constants.TYPE_APPEALSOURCE)){
					List<BaseDataDto> appealSourceList = this.dataDao.getBaseDataByType(baseDataType);
					dataMap.put("appealSourceList", appealSourceList);
				}else if(baseDataType.equals(Constants.TYPE_BUSINESSTYPE)){
					List<BaseDataDto> businessTypeList = this.dataDao.getBaseDataByType(baseDataType);
					dataMap.put("businessTypeList", businessTypeList);
				}else if(baseDataType.equals(Constants.TYPE_PRODUCTBIGTYPE)){
					List<BaseDataDto> productBigTypeList = this.dataDao.getBaseDataByType(baseDataType);
					dataMap.put("productBigTypeList", productBigTypeList);
				}else if(baseDataType.equals(Constants.TYPE_PRODUCTLITTLETYPE)){
					List<BaseDataDto> productLittleTypeList = this.dataDao.getBaseDataByType(baseDataType);
					dataMap.put("productLittleTypeList", productLittleTypeList);
				}else if(baseDataType.equals(Constants.TYPE_CUSTOMERREPLY)){
					List<BaseDataDto> customerReplyList = this.dataDao.getBaseDataByType(baseDataType);
					dataMap.put("customerReplyList", customerReplyList);
				}else if(baseDataType.equals(Constants.TYPE_APPEALTYPE)){
					List<BaseDataDto> appealTypeList = this.dataDao.getBaseDataByType(baseDataType);
					dataMap.put("appealTypeList", appealTypeList);
				}else if(baseDataType.equals(Constants.TYPE_APPEALLEVEL)){
					List<BaseDataDto> appealLevelList = this.dataDao.getBaseDataByType(baseDataType);
					dataMap.put("appealLevelList", appealLevelList);
				}else if(baseDataType.equals(Constants.DEPT)){
//					List<Org> appealOrgList = orgService.findAllOrg();
//					List<BaseDataDto> appealDeptList = new ArrayList<BaseDataDto>();
//					for(int j=0;j<appealOrgList.size();j++){
//						Org org = appealOrgList.get(j);
//						BaseDataDto bs = new BaseDataDto();
//						bs.setCode(org.getOrgCode());
//						bs.setName(org.getOrgName());
//						appealDeptList.add(bs);
//					}
//					dataMap.put("appealDeptList", appealDeptList);
//				}else if(baseDataType.equals(Constants.OPTERATOR)){
//					List<Users> userList = userService.findAllUsers();
//					List<BaseDataDto> userBaseDataList = new ArrayList<BaseDataDto>();
//					for(int x=0;x<userList.size();x++){
//						Users users = userList.get(x);
//						BaseDataDto bs = new BaseDataDto();
//						bs.setCode(users.getUserKy().toString());
//						bs.setName(users.getUserName());
//						userBaseDataList.add(bs);
//					}
					
//					dataMap.put("userBaseDataList", userBaseDataList);
				}else if(baseDataType.equals(Constants.RELATION)){
					List<BaseDataRelationDto> relationList = this.dataDao.getAllBaseDataRelation();
					dataMap.put("relationList", relationList);
				}
			}
		}		
		return dataMap;
	}


	@Override
	public String insertAppealBaseData(BaseDataDto BaseDataDto) {
		String sign ="true";
		try{
			AppealBaseData appealBaseData = new AppealBaseData();
			appealBaseData.setDataCode(BaseDataDto.getCode());
			appealBaseData.setDataName(BaseDataDto.getName());
			appealBaseData.setDataType(BaseDataDto.getType());
			this.dataDao.insertAppealBaseData(appealBaseData);
		}catch(Exception e){
			log.error("DataServiceImpl.insertAppealBaseData is error ...");
			sign = null;
			e.printStackTrace();
		}
		return sign;
	}


	@Override
	public String insertAppealBaseDataRelation(
			BaseDataRelationDto baseDataRelationDto) {
		String sign ="true";
		try{
			AppealBaseDataRelation appealBaseDataRelation = new AppealBaseDataRelation();
			appealBaseDataRelation.setFatherDataCode(baseDataRelationDto.getFatherDataCode());
			appealBaseDataRelation.setSonDataCode(baseDataRelationDto.getSonDataCode());
			appealBaseDataRelation.setRelationType(baseDataRelationDto.getRelationType());
			this.dataDao.insertAppealBaseDataRelation(appealBaseDataRelation);
		}catch(Exception e){
			log.error("DataServiceImpl.insertAppealBaseDataRelation is error ...");
			sign = null;
			e.printStackTrace();
		}
		return sign;
	}


	@Override
	public String deleteAppealBaseData(String baseDataId) {
		String sign ="删除成功!";
		try{			
			this.dataDao.deleteAppealBaseData(baseDataId);
		}catch(Exception e){
			log.error("DataServiceImpl.deleteAppealBaseData is error ...");
			sign = "删除失败!请与管理员联系...";
			e.printStackTrace();
		}
		return sign;
	}


	@Override
	public String deleteAppealBaseDataRelation(String baseDataRelationId) {
		String sign ="删除成功!";
		try{			
			this.dataDao.deleteAppealBaseDataRelation(baseDataRelationId);
		}catch(Exception e){
			log.error("DataServiceImpl.deleteAppealBaseDataRelation is error ...");
			sign = "删除失败!请与管理员联系...";
			e.printStackTrace();
		}
		return sign;
	}


	public void setDataDao(IDataDao dataDao) {
		this.dataDao = dataDao;
	}


	@Override
	public List<BaseDataDto> getDept() {
		
		return this.dataDao.getDept();
	}


	@Override
	public List<BaseDataDto> getAppealStatus() {
		return this.dataDao.getAppealStatus();
	}


	@Override
	public List<BaseDataDto> queryBaseData(BaseDataDto baseDataDto) {
		return this.dataDao.queryBaseData(baseDataDto);
	}


	@Override
	public Integer queryBaseDataCount(BaseDataDto baseDataDto) {
		return this.dataDao.queryBaseDataCount(baseDataDto);
	}
	
	@Override
	public List<BaseDataRelationDto> queryBaseDataRelation(
			BaseDataRelationDto baseDataRelationDto) {
		return this.dataDao.queryBaseDataRelation(baseDataRelationDto);
	}


	@Override
	public Integer queryBaseDataRelationCount(
			BaseDataRelationDto baseDataRelationDto) {
		return this.dataDao.queryBaseDataRelationCount(baseDataRelationDto);
	}

	@Override
	public List<BaseDataDto> getAllBaseData() {
		return this.dataDao.getAllBaseData();
	}


	@Override
	public String isBaseDataCodeExist(String baseDataCode) {
		return this.dataDao.isBaseDataCodeExist(baseDataCode);
	}


	@Override
	public String isRelationExist(String fatherDataCode, String sonDataCode) {
		return this.dataDao.isRelationExist(fatherDataCode,sonDataCode);
	}


//	public void setOrgService(IOrgService orgService) {
//		this.orgService = orgService;
//	}
//
//
//	public void setUserService(IUserService userService) {
//		this.userService = userService;
//	}


	
}
