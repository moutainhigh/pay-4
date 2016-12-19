package com.pay.poss.enterprisemanager.controller;


import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.pay.poss.base.exception.PossException;
import com.pay.poss.enterprisemanager.formbean.ProductJoinMenuFormBean;
import com.pay.poss.enterprisemanager.service.IEnterpriseService;
import com.pay.poss.featuremenu.model.Menu;
import com.pay.poss.membermanager.model.Product;


/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		ProductJoinMenuController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2011-1-11		gungun_zhang			Create
 */

public class ProductJoinMenuController extends SimpleFormController {

	private Log log = LogFactory.getLog(ProductJoinMenuController.class);
	private IEnterpriseService enterpriseService ;	
	

	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		log.debug("ProductJoinMenuController.referenceData() is running...");
				
		String productId = request.getParameter("productId");
		Product product = this.enterpriseService.queryProductById(productId);
		String productName = product.getName();
		String allowObject = product.getAllowObject()+"";
		
		Map<String, Object> dataMap = this.initData(productId,allowObject);
		
		dataMap.put("productId", productId);
		dataMap.put("productName", productName);
		dataMap.put("allowObject",allowObject);
		
		return dataMap;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws IOException {
		log.debug("ProductJoinMenuController.onSubmit() is running...");
		ProductJoinMenuFormBean productJoinMenuFormBean = (ProductJoinMenuFormBean)command;
		String productMenu[] = productJoinMenuFormBean.getFuncno();
		String productId = productJoinMenuFormBean.getProductId();
		String allowObject = productJoinMenuFormBean.getAllowObject();
		String productName = productJoinMenuFormBean.getProductName();
		try {
			this.enterpriseService.productJoinMenuEditTrans(productMenu,productId);
		} catch (PossException e) {
			response.getWriter().write("数据库操作异常");
			log.info("数据库操作异常");
		}
		Map<String,Object> dataMap = this.initData(productId,allowObject);
		dataMap.put("productName",productName);	
		dataMap.put("productId",productId);
		dataMap.put("allowObject",allowObject);
		response.getWriter().write("S");
		return null;
		//return new ModelAndView(this.getSuccessView()).addAllObjects(dataMap);
	}
	
	
	

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}
	private Map<String, Object> initData(String productId,String allowObject){
		Map<String, Object> dataMap = new Hashtable<String, Object>();
		List<Menu> menuOfProductTree = enterpriseService.getMenuOfProduct(productId);
		List<Menu> menuAllTree= enterpriseService.getAllMenu(allowObject);
		
		
		dataMap.put("menuAllTree", menuAllTree);
		dataMap.put("menuOfProductTree", menuOfProductTree);
		return dataMap;
	}
}
