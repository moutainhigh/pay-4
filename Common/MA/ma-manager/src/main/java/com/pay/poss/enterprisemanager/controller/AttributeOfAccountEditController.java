package com.pay.poss.enterprisemanager.controller;


import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.pay.poss.base.common.PossIpUtil;
import com.pay.poss.base.common.SessionUserUtils;
import com.pay.poss.enterprisemanager.formbean.AttributeFormBean;
import com.pay.poss.enterprisemanager.model.AccountAttribute;
import com.pay.poss.enterprisemanager.service.IEnterpriseService;
import com.pay.poss.security.model.SessionUserHolder;



/**
 * 
 * @Description 
 * @project 	poss-membermanager
 * @file 		AttributeOfAccountEditController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-10-25		gungun_zhang			Create
 */
 

public class AttributeOfAccountEditController extends SimpleFormController{
	
	private Log log = LogFactory.getLog(AttributeOfAccountEditController.class);
	private IEnterpriseService enterpriseService;
    @Override
	protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
    	log.debug("AttributeOfAccountEditController.referenceData() is running...");
    	
    	String accountCode = request.getParameter("accountCode");
    	String type = request.getParameter("type");
    	AccountAttribute accAttribute = this.enterpriseService.queryAttributeByAccCode(accountCode);
    	Map<String,Object> dataMap = new Hashtable<String,Object>();
    	dataMap.put("accountCode", accountCode);
    	dataMap.put("accAttribute", accAttribute);
    	dataMap.put("type", type);
		return dataMap;
	}

    @SuppressWarnings("unchecked")
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {    
    	log.debug("AttributeOfAccountEditController.onSubmit() is running...");
    	
		AttributeFormBean attributeFormBean = (AttributeFormBean)command;
		String accountCode = attributeFormBean.getAcctCode();
		AccountAttribute accAttribute = new AccountAttribute();
		AccountAttribute accAttributeOld = this.enterpriseService.queryAttributeByAccCode(accountCode);
		accAttribute.setAcctCode(accountCode);
 
		if(attributeFormBean.getAllowIn()==null&&!accAttributeOld.getAllowIn().equals(1)){
			accAttribute.setAllowIn(1);
		}
		if(attributeFormBean.getAllowIn()!=null&&!accAttributeOld.getAllowIn().equals(0)){
			accAttribute.setAllowIn(0);
		}
		if(attributeFormBean.getAllowOut()==null&&!accAttributeOld.getAllowOut().equals(1)){
			accAttribute.setAllowOut(1);
		}
		if(attributeFormBean.getAllowOut()!=null&&!accAttributeOld.getAllowOut().equals(0)){
			accAttribute.setAllowOut(0);
		}
		if(attributeFormBean.getFrozen()==null&&!accAttributeOld.getFrozen().equals(1)){
			accAttribute.setFrozen(1);
		}
		if(attributeFormBean.getFrozen()!=null&&!accAttributeOld.getFrozen().equals(0)){
			accAttribute.setFrozen(0);
		}

		Boolean isSucess = false;
		//获取当前登录用户及页面入参
		SessionUserHolder user = SessionUserUtils.getUserInfo(request);
		String ip = PossIpUtil.getIp(request);
		if(user!=null){
			Map paramMap = new HashMap();
			paramMap.put("loginIp", ip);
			paramMap.put("loginName", user.getUsername());
			paramMap.put("accAttribute", accAttribute);
			isSucess = enterpriseService.updateAcctAttributeTrans(paramMap);
		}
		String sign = null;
		if(isSucess){
			sign="修改成功!";
		}else{
			sign="修改失败!请与管理员联系...";
		}
		AccountAttribute accAttributeTemp = this.enterpriseService.queryAttributeByAccCode(accountCode);
		Map<String, Object> dataMap = new Hashtable<String, Object>();	
		dataMap.put("sign", sign);
		dataMap.put("accAttribute", accAttributeTemp);
		dataMap.put("accountCode", accountCode);
		dataMap.put("type", request.getParameter("type"));
		
        return new ModelAndView(this.getSuccessView()).addAllObjects(dataMap);
	
   }

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}
  
  

	
	
}
