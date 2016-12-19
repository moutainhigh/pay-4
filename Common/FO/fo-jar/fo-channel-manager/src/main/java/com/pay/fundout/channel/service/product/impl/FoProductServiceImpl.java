/**
 *  File: FoBusinessServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-28      Sunsea.Li      Changes
 *  
 */
package com.pay.fundout.channel.service.product.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.pay.fundout.channel.dao.product.FundoutProductDAO;
import com.pay.fundout.channel.dto.product.FundoutProductDTO;
import com.pay.fundout.channel.model.product.FundoutProduct;
import com.pay.fundout.channel.service.product.FoProductService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;


/**出款方式服务具体实现类
 * @author Sunsea.Li
 *
 */
public class FoProductServiceImpl implements FoProductService {
	private FundoutProductDAO fundoutProductDAO;		//出款产品数据库访问对象
	
	public void setFundoutProductDAO(FundoutProductDAO fundoutProductDAO) {
		this.fundoutProductDAO = fundoutProductDAO;
	}
	@Override
	public Map<String, Object> createFoProduct(FundoutProductDTO foProduct) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		FundoutProduct pojo = new FundoutProduct();
		BeanUtils.copyProperties(foProduct, pojo);
		long businessId = (Long) fundoutProductDAO.create(pojo);
		resultMap.put("businessId", businessId);
		resultMap.put("foProduct", foProduct);
		return resultMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryFoProductInfo(Page<FundoutProductDTO> resultPage, FundoutProductDTO dto) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		FundoutProduct model = new FundoutProduct();
		
		BeanUtils.copyProperties(dto,model);
		
		Page<FundoutProduct> pageModel = new Page<FundoutProduct>();
		
		PageUtils.setServicePage(pageModel, resultPage );
		
		pageModel = fundoutProductDAO.queryFoProductInfos(pageModel, model);
		
		FundoutProductDTO fundoutProductDTO = new FundoutProductDTO();
		
		resultPage.setResult((List<FundoutProductDTO>) PageUtils.changePageList(pageModel.getResult(), fundoutProductDTO, null));
		
		PageUtils.setServicePage(resultPage,pageModel);
		
		resultMap.put("resultPage", resultPage);
		
		return resultMap;
	}
	
	
	@Override
	public FundoutProductDTO queryFoProductInfoByCode(String productCode) {
		FundoutProductDTO productDTO = new FundoutProductDTO();
		FundoutProduct fundoutProduct = fundoutProductDAO.queryFoProductInfoByCode(productCode);
		BeanUtils.copyProperties(fundoutProduct, productDTO);
		return productDTO;
	} 
	
	@Override
	public int updateFoProductInfo(FundoutProductDTO foProduct) {
		FundoutProduct pojo = new FundoutProduct();
		BeanUtils.copyProperties(foProduct, pojo);
		return fundoutProductDAO.updateFoProductInfo(pojo);
	}

}
