package com.pay.poss.merchantmanager.controller;


import org.springframework.web.servlet.mvc.SimpleFormController;
/**
 * 
 * @Description
 * @project poss-membermanager
 * @file MerchantListController.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-8-11 gungun_zhang Create
 */

public class ContractListController extends SimpleFormController {

//	private Log log = LogFactory.getLog(ContractListController.class);
//	private ContractServiceImpl contractService;
//	@Override
//	protected Map<Object,Object> referenceData(HttpServletRequest request) throws Exception {
//		log.debug("ContractListController.referenceData() is running...");		
//		return null;
//	}
//
//	public ModelAndView onSubmit(Object command, BindException errors){
//		log.debug("ContractListController.onSubmit() is running...");
//		ContractSearchFormBean 	contractSearchFormBean = (ContractSearchFormBean)command;
//		BusinessContractSearchBO businessContractSearchBO = new BusinessContractSearchBO();		
//		try {
//			BeanUtils.copyProperties(businessContractSearchBO,contractSearchFormBean);
//			
//		} catch (Exception e) {	
//			log.error("BeanUtils.copyProperties error");
//			e.printStackTrace();
//		}
//		List<BusinessContractBO> contractList = contractService.queryContract(businessContractSearchBO,1,5);
//		return new ModelAndView(this.getSuccessView()).addObject("contractList",contractList );
//
//	}
//
//	
//	
//
//	public void setContractService(ContractServiceImpl contractService) {
//		this.contractService = contractService;
//	}

}
