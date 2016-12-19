package com.pay.poss.merchantmanager.controller;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.pay.acc.comm.Constants;
import com.pay.acc.comm.LiquidateModeEnum;
import com.pay.acc.comm.MerchantLevelEnum;
import com.pay.acc.comm.MerchantTypeEnum;
import com.pay.inf.dto.Bank;
import com.pay.inf.dto.CityDTO;
import com.pay.inf.dto.ProvinceDTO;
import com.pay.inf.service.BankService;
import com.pay.inf.service.CityService;
import com.pay.inf.service.ProvinceService;
import com.pay.poss.merchantmanager.dto.MerchantDto;
import com.pay.poss.merchantmanager.enums.DepartmentEnum;
import com.pay.poss.merchantmanager.enums.NationEnum;
import com.pay.poss.merchantmanager.model.Relation;
import com.pay.poss.merchantmanager.service.IMerchantService;
import com.pay.rm.facade.RcLimitRuleFacade;
import com.pay.rm.service.model.rmlimit.risklevel.RcRiskLevel;


/**
 * 
 * @Description 
 * @project 	poss-membermanager
 * @file 		MerchantEditForCheckController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-9-25		gungun_zhang			Create
 */
 
 

public class MerchantEditForCheckController extends SimpleFormController{
	
	private Log log = LogFactory.getLog(MerchantEditForCheckController.class);
	private IMerchantService merchantService ;
	private CityService cityService;
	private ProvinceService provinceService;
	private BankService bankService;
	

	//风控等级依赖fo包
	private RcLimitRuleFacade rcLimitRuleFacade;
	

	/**
	 * @param rcLimitRuleFacade the rcLimitRuleFacade to set
	 */
	public void setRcLimitRuleFacade(RcLimitRuleFacade rcLimitRuleFacade) {
		this.rcLimitRuleFacade = rcLimitRuleFacade;
	}
	
    @Override
	protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
    	log.debug("MerchantEditForCheckController.referenceData() is running...");
    	Map<String,Object> dataMap = this.initData();
    	String memberCode = request.getParameter("memberCode");
    	Boolean isCheckedPerm = false;
    	Boolean isGoBackPerm = false;
    	MerchantDto merchantDto = null;
//    	if(StringUtils.isNotEmpty(memberCode)){
//    		merchantDto = this.merchantService.getMerchantByMemberCode(memberCode);
//    		isCheckedPerm = this.isCheckedPerm(merchantDto.getAudiStatus());
//    		isGoBackPerm = this.isGoBackPerm(merchantDto.getAudiStatus());
//    	}
    	
    	List<MerchantDto> merchantList = null;
    	if(StringUtils.isNotEmpty(memberCode)){
    		merchantList = this.merchantService.getMerchantListByMemberCode(memberCode);
    		if(merchantList.size()>0){
    			for(MerchantDto merchantDto1:merchantList){
        			merchantDto1.setCityName(this.cityService.findByCityCode(Integer.valueOf(merchantDto1.getCity())).getCityname());
        	    	if(StringUtils.isNotEmpty(merchantDto1.getCityBank())){
        	    		merchantDto1.setCityBankName(this.cityService.findByCityCode(Integer.valueOf(merchantDto1.getCityBank())).getCityname());
        	    	}
        		}
    			merchantDto = (merchantList.get(0));
        		isCheckedPerm = this.isCheckedPerm(merchantDto.getAudiStatus());
        		isGoBackPerm = this.isGoBackPerm(merchantDto.getAudiStatus());
    		}
    		
    	}
    	dataMap.put("isCheckedPerm", isCheckedPerm);
    	dataMap.put("isGoBackPerm", isGoBackPerm);
		dataMap.put("merchantDto", merchantDto == null ? new MerchantDto()
				: merchantDto);
    	dataMap.put("merchantList", merchantList);
    	
		return dataMap;
	}

    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {    
    		log.debug("MerchantEditForCheckController.onSubmit() is running...");
    		
    		String type =(String) request.getParameter("type");
    		String memberCode = request.getParameter("memberCode");
    		String merchantCode = request.getParameter("merchantCode");
    		String merchantEmail =request.getParameter("merchantEmail");
    		String merchantName = request.getParameter("merchantName");
//    		String compayLinkerName = request.getParameter("compayLinkerName");
//    		String compayLinkerTel = request.getParameter("compayLinkerTel");
//    		String signDepart = request.getParameter("signDepart");
    		
    		String sign = null;
    		Integer status =null;
    		Boolean isSucess=null;
			if(StringUtils.isNotEmpty(type) && StringUtils.isNotEmpty(memberCode)){
//				MerchantDto merchantDto = new MerchantDto();
//暂时不记日志				
//				SessionUserHolder user = MerchantUtils.getUserInfo(request);
//				merchantDto.setUserId(user.getUserKy());
//				merchantDto.setUserName(user.getUsername());
//				merchantDto.setUserDept(user.getOrgCode());
//				merchantDto.setMemberCode(memberCode);
//				merchantDto.setEmail(merchantEmail);
//				merchantDto.setZhName(merchantName);
//				merchantDto.setMerchantCode(merchantCode);
//				merchantDto.setCompayLinkerName(compayLinkerName);
//				merchantDto.setCompayLinkerTel(compayLinkerTel);
//				merchantDto.setSignDepart(signDepart);
				
				if(type.equals("checkOk")){
					status = Constants.CHECK_YES;//审核通过
					isSucess= merchantService.updateMerchantStatusTrans(Long.valueOf(memberCode), merchantEmail, merchantName, status);
	    		}else if(type.equals("goBack")){
	    			status = Constants.CHECK_NO;//审核未通过
	    			isSucess= merchantService.updateMerchantStatusTrans(Long.valueOf(memberCode), merchantEmail, merchantName, status);
	    		} else{
	    			sign = "操作类型未知,操作失败!请与管理员联系...";
//	    			Map<String,Object> dataMap = this.initData();
//	    			merchantDto = this.merchantService.getMerchantByMemberCode(memberCode);
//	        	  	merchantDto.setCityName(this.cityService.findByCityCode(Integer.valueOf(merchantDto.getCity())).getCityname());
//	        	  	if(StringUtils.isNotEmpty(merchantDto.getCityBank())){
//	        	  		merchantDto.setCityBankName(this.cityService.findByCityCode(Integer.valueOf(merchantDto.getCityBank())).getCityname());
//	        	  	}
//	        	  	isCheckedPerm = this.isCheckedPerm(merchantDto.getAudiStatus());
//	        	  	isGoBackPerm = this.isGoBackPerm(merchantDto.getAudiStatus());
//	        	  	dataMap.put("merchantDto", merchantDto);
//	        	  	dataMap.put("sign", sign);
//	        	  	dataMap.put("isCheckedPerm", isCheckedPerm);
//	            	dataMap.put("isGoBackPerm", isGoBackPerm);
	    			isSucess = false;
	    		}
				
				if(isSucess){
					sign="操作成功!" ;
				}else{
					sign="操作失败!请与管理员联系..." ;
				}
//				Map<String,Object> dataMap = this.initData();
//    			merchantDto = this.merchantService.getMerchantByMemberCode(memberCode);
//        	  	merchantDto.setCityName(this.cityService.findByCityCode(Integer.valueOf(merchantDto.getCity())).getCityname());
//        		if(StringUtils.isNotEmpty(merchantDto.getCityBank())){
//        			merchantDto.setCityBankName(this.cityService.findByCityCode(Integer.valueOf(merchantDto.getCityBank())).getCityname());
//        		}
//        	  	isCheckedPerm = this.isCheckedPerm(merchantDto.getAudiStatus());
//        	  	isGoBackPerm = this.isGoBackPerm(merchantDto.getAudiStatus());
//        	  	dataMap.put("merchantDto", merchantDto);
//        	  	dataMap.put("sign", sign);
//        	  	dataMap.put("isCheckedPerm", isCheckedPerm);
//            	dataMap.put("isGoBackPerm", isGoBackPerm);
    			
			}else{
				sign="商户标识未知或操作类型未知,操作失败!请与管理员联系..." ;
			}
					
			return new ModelAndView(this.getSuccessView()).addObject("msg", sign).addObject("isSuccess", isSucess);
   }
    
    private Map<String,Object> initData(){
    	
    	List<ProvinceDTO> provinceList = provinceService.findAll();
		List<Relation> relationList = new ArrayList<Relation>();

		for (int i = 0; i < provinceList.size(); i++) {
			ProvinceDTO province = (ProvinceDTO) provinceList.get(i);
			List<CityDTO> cityList = cityService.findByProvinceId(province.getProvincecode());

			for (int j = 0; j < cityList.size(); j++) {
				CityDTO city = (CityDTO) cityList.get(j);
				Relation relation = new Relation();
				relation.setFatherCode(province.getProvincecode().toString());
				relation.setCode(city.getCitycode().toString());
				relation.setName(city.getCityname());
				relationList.add(relation);
			}
		}
		
		DepartmentEnum[] departmentEnum = DepartmentEnum.values();
		LiquidateModeEnum[] liquidateModeEnum = LiquidateModeEnum.values();
		MerchantLevelEnum[] merchantLevelEnum = MerchantLevelEnum.values();
		MerchantTypeEnum[] merchantTypeEnum = MerchantTypeEnum.values();
		NationEnum[] nationEnum = NationEnum.values();
		List<Bank> bankList = bankService.getWithdrawBanks();
		
		Map<String, Object> dataMap = new Hashtable<String, Object>();
		dataMap.put("bankList", bankList);
		dataMap.put("provinceList", provinceList);
		dataMap.put("relationList", relationList);
		dataMap.put("departmentEnum", departmentEnum);
		dataMap.put("liquidateModeEnum", liquidateModeEnum);
		dataMap.put("merchantLevelEnum", merchantLevelEnum);
		dataMap.put("merchantTypeEnum", merchantTypeEnum);
		List<RcRiskLevel> riskLevelList = rcLimitRuleFacade.getRiskLevelList();
		dataMap.put("riskLevelList", riskLevelList);
		dataMap.put("nationEnum", nationEnum);
		
    	return dataMap;
    }
  
    private Boolean isCheckedPerm(String audiStatus){
    	if(audiStatus.equals(Constants.CHECK_READYCHECK.toString())){
    		return true;
    	}

    	return false;
    }
    private Boolean isGoBackPerm(String audiStatus){
    	if(audiStatus.equals(Constants.CHECK_READYCHECK.toString())){
	    	return true;	
	    }
    	return false;
    }
	public void setMerchantService(IMerchantService merchantService) {
		this.merchantService = merchantService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	public void setProvinceService(ProvinceService provinceService) {
		this.provinceService = provinceService;
	}

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}
	
}
