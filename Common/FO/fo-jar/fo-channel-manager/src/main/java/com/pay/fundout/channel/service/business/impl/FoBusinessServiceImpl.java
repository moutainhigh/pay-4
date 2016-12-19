/**
 *  File: FoBusinessServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-28      Sunsea.Li      Changes
 *  
 */
package com.pay.fundout.channel.service.business.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.pay.fundout.channel.dao.business.FundoutBusinessDAO;
import com.pay.fundout.channel.dto.business.FundoutBusinessDTO;
import com.pay.fundout.channel.model.business.FundoutBusiness;
import com.pay.fundout.channel.service.business.FoBusinessService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;


/**出款方式服务具体实现类
 * @author Sunsea.Li
 *
 */
public class FoBusinessServiceImpl implements FoBusinessService {
	private FundoutBusinessDAO fundoutBusinessDAO;		//出款业务数据库访问对象
	
	public void setFundoutBusinessDAO(FundoutBusinessDAO fundoutBusinessDAO) {
		this.fundoutBusinessDAO = fundoutBusinessDAO;
	}
	@Override
	public Map<String, Object> createFoBusiness(FundoutBusinessDTO foBusiness) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		FundoutBusiness pojo = new FundoutBusiness();
		BeanUtils.copyProperties(foBusiness, pojo);
		long businessId = (Long) fundoutBusinessDAO.create(pojo);
		resultMap.put("businessId", businessId);
		resultMap.put("foBusiness", foBusiness);
		return resultMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryFoBusinessInfo(Page<FundoutBusinessDTO> resultPage, FundoutBusinessDTO dto) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		FundoutBusiness model = new FundoutBusiness();
		
		BeanUtils.copyProperties(dto,model);
		
		Page<FundoutBusiness> pageModel = new Page<FundoutBusiness>();
		
		PageUtils.setServicePage(pageModel, resultPage );
		
		pageModel = fundoutBusinessDAO.queryFoBusinessInfos(pageModel, model);
		
		FundoutBusinessDTO fundoutBusinessDTO = new FundoutBusinessDTO();
		
		resultPage.setResult((List<FundoutBusinessDTO>) PageUtils.changePageList(pageModel.getResult(), fundoutBusinessDTO, null));
		
		PageUtils.setServicePage(resultPage,pageModel);
		
		resultMap.put("resultPage", resultPage);
		
		return resultMap;
	}
	
	
	@Override
	public FundoutBusinessDTO queryFoBusinessInfoByCode(String businessCode) {
		FundoutBusinessDTO businessDTO = new FundoutBusinessDTO();
		FundoutBusiness fundoutBusiness = fundoutBusinessDAO.queryFoBusinessInfoByCode(businessCode);
		BeanUtils.copyProperties(fundoutBusiness, businessDTO);
		return businessDTO;
	} 
	
	@Override
	public FundoutBusinessDTO queryFoBusinessInfoById(Long businessId) {
		FundoutBusinessDTO businessDTO = new FundoutBusinessDTO();
		FundoutBusiness fundoutBusiness = fundoutBusinessDAO.findById(businessId);
		BeanUtils.copyProperties(fundoutBusiness, businessDTO);
		return businessDTO;
	}

	@Override
	public int updateFoBusinessInfo(FundoutBusinessDTO foBusiness) {
		FundoutBusiness pojo = new FundoutBusiness();
		BeanUtils.copyProperties(foBusiness, pojo);
		return fundoutBusinessDAO.updateFoBusinessInfo(pojo);
	}

}
