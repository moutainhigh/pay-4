package com.pay.poss.enterprisemanager.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.enterprisemanager.dto.ProductSearchDto;
import com.pay.poss.enterprisemanager.enums.ActivationModeEnum;
import com.pay.poss.enterprisemanager.enums.ProductAllowObjectEnum;
import com.pay.poss.enterprisemanager.enums.ProductStatusEnum;
import com.pay.poss.enterprisemanager.enums.ProductTypeEnum;
import com.pay.poss.enterprisemanager.enums.ProductTypeOrderEnum;
import com.pay.poss.enterprisemanager.formbean.ProductSearchFormBean;
import com.pay.poss.enterprisemanager.service.IEnterpriseService;


/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		ProductListForMenuController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2011-1-11		gungun_zhang			Create
 */

public class ProductListForMenuController extends SimpleFormController {

	private Log log = LogFactory.getLog(ProductListForMenuController.class);
	private IEnterpriseService enterpriseService ;	
	

	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		log.debug("ProductListForMenuController.referenceData() is running...");
				
		Map<String, Object> dataMap = this.initData();
		
		return dataMap;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {
		log.debug("ProductListForMenuController.onSubmit() is running...");
		ProductSearchFormBean productSearchFormBean = (ProductSearchFormBean) command;
		if(StringUtils.isNotEmpty(productSearchFormBean.getProductId())){
			this.enterpriseService.productDeleteTrans(productSearchFormBean.getProductId());
		}
		ProductSearchDto productSearchDto = new ProductSearchDto();
		
		BeanUtils.copyProperties(productSearchFormBean, productSearchDto);
				
		Page<ProductSearchDto> page = PageUtils.getPage(request);
		productSearchDto.setPageEndRow((page.getPageNo()*page.getPageSize())+"");
		if((page.getPageNo()-1)==0){
			productSearchDto.setPageStartRow("0");
		}else{
			productSearchDto.setPageStartRow((page.getPageNo()-1)*page.getPageSize()+"");
		}
		List<ProductSearchDto> productList = enterpriseService.queryProduct(productSearchDto);
		Integer productListCount = enterpriseService.queryProductCount(productSearchDto);
		
		page.setResult(productList);
		page.setTotalCount(productListCount);
				
		Map<String, Object> dataMap = this.initData();
		dataMap.put("page",page);
		return new ModelAndView(this.getSuccessView()).addAllObjects(dataMap);
	}
	
	 private Map<String,Object> initData(){
	   Map<String,Object> dataMap = new HashMap<String,Object>();
	   ProductAllowObjectEnum[] allowObjectEnum = ProductAllowObjectEnum.values();
	   ProductTypeEnum[] productTypeEnum = ProductTypeEnum.values();
	   ActivationModeEnum[] activationModeEnum = ActivationModeEnum.values();
	   ProductStatusEnum[] productStatusEnum = ProductStatusEnum.values();
	   dataMap.put("productStatusEnum", productStatusEnum);
	   dataMap.put("allowObjectEnum", allowObjectEnum);
	   dataMap.put("productTypeEnum", productTypeEnum);
	   dataMap.put("activationModeEnum", activationModeEnum);
	   dataMap.put("productTypeOrderEnum", ProductTypeOrderEnum.values());
    	
	   return dataMap;
	}
	

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}
	
}
