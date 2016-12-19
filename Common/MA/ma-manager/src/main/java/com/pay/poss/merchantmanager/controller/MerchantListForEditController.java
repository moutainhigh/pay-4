package com.pay.poss.merchantmanager.controller;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.pay.acc.comm.MerchantCheckStatusEnum;
import com.pay.acc.comm.MerchantLevelEnum;
import com.pay.acc.comm.MerchantStatusEnum;
import com.pay.acc.comm.MerchantTypeEnum;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.merchantmanager.dto.MerchantSearchDto;
import com.pay.poss.merchantmanager.dto.MerchantSearchListDto;
import com.pay.poss.merchantmanager.formbean.MerchantSearchFormBean;
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

public class MerchantListForEditController extends SimpleFormController {

	private Log log = LogFactory.getLog(MerchantListForEditController.class);
	private IMerchantService merchantService ;

	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		log.debug("MerchantListForEditController.referenceData() is running...");
		
		MerchantStatusEnum[] merchantStatusEnum = MerchantStatusEnum.values();
		MerchantCheckStatusEnum[] merchantCheckStatusEnum = MerchantCheckStatusEnum.values();
		
		Map<String, Object> dataMap = new Hashtable<String, Object>();
		dataMap.put("merchantStatusEnum",merchantStatusEnum);
		dataMap.put("merchantCheckStatusEnum", merchantCheckStatusEnum);
		return dataMap;

	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {
		log.debug("MerchantListForEditController.onSubmit() is running...");
		
		MerchantSearchFormBean merchantSearchFormBean = (MerchantSearchFormBean) command;
		MerchantSearchDto merchantSearchDto = new MerchantSearchDto();
		
		BeanUtils.copyProperties(merchantSearchFormBean, merchantSearchDto);
		if(StringUtils.isNotEmpty(merchantSearchFormBean.getMerchantName())){
			
			if(Pattern.compile("^[a-z].+$").matcher(merchantSearchFormBean.getMerchantName()).matches()){
				merchantSearchDto.setMerchantSearchKey(merchantSearchFormBean.getMerchantName());
				merchantSearchDto.setMerchantName(null);
			}
		}
		Page<MerchantSearchListDto> page = PageUtils.getPage(request);
		merchantSearchDto.setPageEndRow((page.getPageNo()*page.getPageSize())+"");
		if((page.getPageNo()-1)==0){
			merchantSearchDto.setPageStartRow("0");
		}else{
			merchantSearchDto.setPageStartRow((page.getPageNo()-1)*page.getPageSize()+"");
		}
		List<MerchantSearchListDto> merchantList = merchantService.queryMerchant(merchantSearchDto);
		merchantList = this.getNameByCode(merchantList);
		Integer merchantListCount = merchantService.queryMerchantCount(merchantSearchDto);
		
		page.setResult(merchantList);
		page.setTotalCount(merchantListCount);
		return new ModelAndView(this.getSuccessView()).addObject("page", page);
	}
	
	
	private List<MerchantSearchListDto> getNameByCode(List<MerchantSearchListDto> merchantList){
		if (merchantList != null && merchantList.size() != 0) {
			for(int i=0;i<merchantList.size();i++){
				MerchantSearchListDto merchant=(MerchantSearchListDto)merchantList.get(i);
				if (StringUtils.isNotEmpty(merchant.getMerchantStatus())) {
					MerchantStatusEnum merchantStatusEnum = MerchantStatusEnum.getByCode(Integer.valueOf(merchant.getMerchantStatus()));
					if(merchantStatusEnum!=null){
						merchant.setMerchantStatus(merchantStatusEnum.getDescription());
					}
					
				}
				if (StringUtils.isNotEmpty(merchant.getMerchantCheckStatus())) {
					MerchantCheckStatusEnum merchantCheckStatusEnum = MerchantCheckStatusEnum.getByCode(Integer.valueOf(merchant.getMerchantCheckStatus()));
					if(merchantCheckStatusEnum!=null){
						merchant.setMerchantCheckStatus(merchantCheckStatusEnum.getDescription());
					}
					
				}
				if (StringUtils.isNotEmpty(merchant.getMerchantType())) {
					MerchantTypeEnum merchantTypeEnum = MerchantTypeEnum.getByCode(Integer.valueOf(merchant.getMerchantType()));
					if(merchantTypeEnum!=null){
						merchant.setMerchantType(merchantTypeEnum.getDescription());
					}
					
				}
				if (StringUtils.isNotEmpty(merchant.getServiceLevel())) {
					MerchantLevelEnum merchantLevelEnum = MerchantLevelEnum.getByCode(Integer.valueOf(merchant.getServiceLevel()));
					if(merchantLevelEnum!=null){
						merchant.setServiceLevel(merchantLevelEnum.getDescription());
					}
					
				}				
				
			}
		}
			return merchantList;
		
	}

	public void setMerchantService(IMerchantService merchantService) {
		this.merchantService = merchantService;
	}

	

	

}
