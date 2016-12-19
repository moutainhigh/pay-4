package com.pay.poss.merchantmanager.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.pay.acc.comm.Constants;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.merchantmanager.dto.MerchantSearchDto;
import com.pay.poss.merchantmanager.dto.MerchantSearchListDto;
import com.pay.poss.merchantmanager.service.IMerchantService;

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

public class MerchantListForActivationController extends SimpleFormController {

	private Log log = LogFactory.getLog(MerchantListForActivationController.class);
	private IMerchantService merchantService ;

	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		log.debug("MerchantListForActivationController.referenceData() is running...");
		return null;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {
		log.debug("MerchantListForActivationController.onSubmit() is running...");
				
		MerchantSearchDto merchantSearchDto = new MerchantSearchDto();
		merchantSearchDto.setMerchantStatus(Constants.STATUS_UNACTIVATION.toString());//会员状态为未激活
		merchantSearchDto.setDayCount(Constants.UNACTIVATIONDATE);	//过期天数
		merchantSearchDto.setCheckCodeStatus(Constants.URLUNACTIVATION);//url链接是否有效
		Page<MerchantSearchListDto> page = PageUtils.getPage(request);
		merchantSearchDto.setPageEndRow((page.getPageNo()*page.getPageSize())+"");
		if((page.getPageNo()-1)==0){
			merchantSearchDto.setPageStartRow("0");
		}else{
			merchantSearchDto.setPageStartRow((page.getPageNo()-1)*page.getPageSize()+"");
		}
		
		List<MerchantSearchListDto> merchantList = merchantService.queryMerchantOfUnActive(merchantSearchDto);
		
		Integer merchantListCount = merchantService.queryMerchantOfUnActiveCount(merchantSearchDto);
		
		page.setResult(merchantList);
		page.setTotalCount(merchantListCount);
		return new ModelAndView(this.getSuccessView()).addObject("page", page);
	}

	public void setMerchantService(IMerchantService merchantService) {
		this.merchantService = merchantService;
	}
	
	
		



}
