package com.pay.poss.merchantmanager.controller;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.pay.poss.merchantmanager.enums.DepartmentEnum;
import com.pay.poss.merchantmanager.enums.NationEnum;
import com.pay.poss.merchantmanager.model.Relation;
import com.pay.poss.merchantmanager.service.IMerchantService;
import com.pay.poss.merchantmanager.service.ISignMessageService;
import com.pay.rm.facade.RcLimitRuleFacade;
import com.pay.rm.service.model.rmlimit.risklevel.RcRiskLevel;


/**
 * 
 * @Description 
 * @project 	poss-membermanager
 * @file 		MerchantEditForEditController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-9-24		gungun_zhang			Create
 */
 

public class MerchantEditForEditController extends SimpleFormController{
	
	private Log log = LogFactory.getLog(MerchantEditForEditController.class);
	private IMerchantService merchantService ;
	private CityService cityService;
	private ProvinceService provinceService;
	private BankService bankService;
	private ISignMessageService iSignMessageService;

	//风控等级依赖fo包
	private RcLimitRuleFacade rcLimitRuleFacade;
	

	/**
	 * @param rcLimitRuleFacade the rcLimitRuleFacade to set
	 */
	public void setRcLimitRuleFacade(RcLimitRuleFacade rcLimitRuleFacade) {
		this.rcLimitRuleFacade = rcLimitRuleFacade;
	}
	
//    @Override
//	protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
//    	log.debug("MerchantEditForEditController.referenceData() is running...");
//    	Map<String,Object> dataMap = this.initData();
//    	String memberCode = request.getParameter("memberCode");
//    	MerchantDto merchantDto = null;
//    	Boolean isEditPerm = false;
//    	if(StringUtils.isNotEmpty(memberCode)){
//    		merchantDto = this.merchantService.getMerchantListByMemberCode(memberCode); 
//    		if(merchantDto!=null){
//    			isEditPerm = this.isEditPerm(merchantDto.getAudiStatus());
//    		}
//    		   		
//    	}
//    	merchantDto.setCityName(this.cityService.findByCityCode(Integer.valueOf(merchantDto.getCity())).getCityname());
//    	if(StringUtils.isNotEmpty(merchantDto.getCityBank())){
//    		merchantDto.setCityBankName(this.cityService.findByCityCode(Integer.valueOf(merchantDto.getCityBank())).getCityname());
//    	}
//    	dataMap.put("merchantDto", merchantDto);
//    	dataMap.put("isEditPerm", isEditPerm);
//		return dataMap;
//	}

//    protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
//			BindException errors) throws Exception {    
//    	log.debug("MerchantEditForEditController.onSubmit() is running...");
//    	
//		MerchantFormBean merchantFormBean = (MerchantFormBean)command;
//		Boolean isEditPerm = false;
//		String sign = null;
//		Map<String,String> paraMap = new HashMap<String,String>();
//		MerchantDto merchantDtoOld = this.merchantService.getMerchantByMemberCode(merchantFormBean.getMemberCode());
//		if(!merchantDtoOld.getBizLicenceCode().trim().equals(merchantFormBean.getBizLicenceCode())){
//			paraMap.put("bizLicenceCode", merchantFormBean.getBizLicenceCode());
//		}
//		if(!merchantDtoOld.getGovCode().trim().equals(merchantFormBean.getGovCode())){
//			paraMap.put("govCode", merchantFormBean.getGovCode());
//		}
//		if(!merchantDtoOld.getTaxCode().trim().equals(merchantFormBean.getTaxCode())){
//			paraMap.put("taxCode", merchantFormBean.getTaxCode());
//		}
//		if(!merchantDtoOld.getEmail().trim().equals(merchantFormBean.getEmail())){
//			paraMap.put("email", merchantFormBean.getEmail());
//		}
//		
//		String isAllowResult=null;
//		if(paraMap.size()!=0){
//			isAllowResult = this.merchantService.isAllowCreateOrUpdateMerchant(paraMap);
//			isAllowResult = isAllowResult + this.merchantService.isMccExist(merchantFormBean.getIndustry());
//			//added by wangtq 2011-01-10
//			SignMessageDto dto = new SignMessageDto();
//			dto.setDepartmentName(merchantFormBean.getSignDepart());
//			if(iSignMessageService.countSignMessageByCondition(dto)== 0){
//				isAllowResult = "您输入的签约人部门不存在,请与管理员联系...";
//			}
//		}
//		
//		if(StringUtils.isEmpty(isAllowResult)){
//			MerchantDto merchantDto = new MerchantDto();
//			
//			BeanUtils.copyProperties(merchantFormBean,merchantDto);
////暂时不记操作日志			
////			SessionUserHolder user = MerchantUtils.getUserInfo(request);
////			merchantDto.setUserId(user.getUserKy());
////			merchantDto.setUserName(user.getUsername());
////			merchantDto.setUserDept(user.getOrgCode());
//			
//			Boolean isSuccess = merchantService.updateMerchantTrans(merchantDto);
//			
//			if(isSuccess){
//				sign="修改成功!" ;
//			}else{
//				sign="修改失败!请与管理员联系..." ;
//			}
//			MerchantDto newMerchantDto = null;			
//	    	if(StringUtils.isNotEmpty(merchantDto.getMemberCode())){
//	    		newMerchantDto = this.merchantService.getMerchantByMemberCode(merchantDto.getMemberCode());
//	    		if(newMerchantDto!=null){
//	    			isEditPerm = this.isEditPerm(newMerchantDto.getAudiStatus());
//	    		}
//	    		
//	    	}
//			Map<String, Object> dataMap = this.initData();
//			newMerchantDto.setCityName(this.cityService.findByCityCode(Integer.valueOf(newMerchantDto.getCity())).getCityname());
//			if(StringUtils.isNotEmpty(newMerchantDto.getCityBank())){
//				newMerchantDto.setCityBankName(this.cityService.findByCityCode(Integer.valueOf(newMerchantDto.getCityBank())).getCityname());
//			}
//			dataMap.put("merchantDto",newMerchantDto);
//			dataMap.put("sign", sign);
//			dataMap.put("isEditPerm", isEditPerm);		
//	        return new ModelAndView(this.getSuccessView()).addAllObjects(dataMap);
//		}else{
//			sign = isAllowResult;
//			if(merchantDtoOld!=null){
//				isEditPerm = this.isEditPerm(merchantDtoOld.getAudiStatus());
//    		}
//	    		
//	    	Map<String, Object> dataMap = this.initData();
//	    	merchantDtoOld.setCityName(this.cityService.findByCityCode(Integer.valueOf(merchantDtoOld.getCity())).getCityname());
//	    	if(StringUtils.isNotEmpty(merchantDtoOld.getCityBank())){
//	    		merchantDtoOld.setCityBankName(this.cityService.findByCityCode(Integer.valueOf(merchantDtoOld.getCityBank())).getCityname());
//	    	}
//			dataMap.put("sign", sign);
//			dataMap.put("merchantDto",merchantDtoOld);
//			dataMap.put("isEditPerm", isEditPerm);
//			return new ModelAndView(this.getSuccessView()).addAllObjects(dataMap);
//		}
//		
//		
//				
//		
//             
//   }
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
    private Boolean isEditPerm(String audiStatus){
    	if(StringUtils.isNotEmpty(audiStatus)){
    		if(audiStatus.equals(Constants.CHECK_NO.toString())||audiStatus.equals(Constants.CHECK_READYCHECK.toString())){
    			return true;
    		}
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
	public ISignMessageService getiSignMessageService() {
		return iSignMessageService;
	}
	public void setiSignMessageService(ISignMessageService iSignMessageService) {
		this.iSignMessageService = iSignMessageService;
	}
	
}
