/**
 *  File: FoModeServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-28      Sunsea.Li      Changes
 *  
 */
package com.pay.fundout.channel.service.mode.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.pay.fundout.channel.dao.mode.FundoutModeDAO;
import com.pay.fundout.channel.dto.mode.FundoutModeDTO;
import com.pay.fundout.channel.model.mode.FundoutMode;
import com.pay.fundout.channel.service.mode.FoModeService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;

/**
 * 出款方式服务具体实现类
 * 
 * @author Sunsea.Li
 * 
 */
public class FoModeServiceImpl implements FoModeService {
	private FundoutModeDAO fundoutModeDAO; // 出款方式数据库访问对象

	public void setFundoutModeDAO(FundoutModeDAO fundoutModeDAO) {
		this.fundoutModeDAO = fundoutModeDAO;
	}

	@Override
	public Map<String, Object> createFoMode(FundoutModeDTO dto) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		FundoutMode model = new FundoutMode();
		BeanUtils.copyProperties(dto, model);
		long modeId = (Long) fundoutModeDAO.create(model);
		resultMap.put("modeId", modeId);
		return resultMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryFoModeInfo(Page<FundoutModeDTO> resultPage, FundoutModeDTO dto) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		FundoutMode model = new FundoutMode();

		BeanUtils.copyProperties(dto, model);

		Page<FundoutMode> pageModel = new Page<FundoutMode>();

		PageUtils.setServicePage(pageModel, resultPage);

		pageModel = fundoutModeDAO.queryFundoutModeInfos(pageModel, model);

		FundoutModeDTO fundoutModeDTO = new FundoutModeDTO();

		resultPage.setResult((List<FundoutModeDTO>) PageUtils.changePageList(pageModel.getResult(), fundoutModeDTO, null));

		PageUtils.setServicePage(resultPage, pageModel);

		resultMap.put("resultPage", resultPage);

		return resultMap;

	}

	@Override
	public FundoutModeDTO queryFoModeInfoById(Long modeId) {
		FundoutModeDTO dto = new FundoutModeDTO();
		BeanUtils.copyProperties(fundoutModeDAO.queryFundoutModeInfoById(modeId), dto);
		return dto;
	}

	
	@Override
	public FundoutModeDTO queryFoModeInfoByCode(String code) {
		FundoutModeDTO dto = new FundoutModeDTO();
		BeanUtils.copyProperties(fundoutModeDAO.queryFundoutModeInfoByCode(code), dto);
		return dto;
	}

	
	@Override
	public int updateFoModeInfo(FundoutModeDTO dto) {
		FundoutMode model = new FundoutMode();
		BeanUtils.copyProperties(dto, model);
		return fundoutModeDAO.updateFundoutModeInfo(model);
	}

}
