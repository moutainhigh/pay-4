package com.pay.poss.enterprisemanager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
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

import com.pay.acc.comm.LiquidateModeEnum;
import com.pay.acc.comm.MerchantLevelEnum;
import com.pay.acc.comm.MerchantTypeEnum;
import com.pay.fundout.autofundout.service.AutoFundoutConfigService;
import com.pay.inf.dto.Bank;
import com.pay.inf.dto.CityDTO;
import com.pay.inf.dto.ProvinceDTO;
import com.pay.inf.service.BankService;
import com.pay.inf.service.CityService;
import com.pay.inf.service.ProvinceService;
import com.pay.poss.enterprisemanager.dto.MerchantDto;
import com.pay.poss.enterprisemanager.enums.DepartmentEnum;
import com.pay.poss.enterprisemanager.enums.NationEnum;
import com.pay.poss.enterprisemanager.formbean.EnterpriseFormBean;
import com.pay.poss.enterprisemanager.model.Relation;
import com.pay.poss.enterprisemanager.service.IEnterpriseService;
import com.pay.poss.merchantmanager.service.IMerchantService;
import com.pay.poss.merchantmanager.service.ISignMessageService;
import com.pay.rm.facade.RcLimitRuleFacade;
import com.pay.rm.service.model.rmlimit.risklevel.RcRiskLevel;

/**
 * 
 * @Description
 * @project poss-membermanager
 * @file MerchantEditForCheckController.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-9-25 gungun_zhang Create
 */

public class EnterpriseViewController extends SimpleFormController {

	private Log log = LogFactory.getLog(EnterpriseViewController.class);
	private IEnterpriseService enterpriseService;
	private CityService cityService;
	private ProvinceService provinceService;
	private BankService bankService;
	private ISignMessageService iSignMessageService;
	private AutoFundoutConfigService autoFundoutConfigService;
	private IMerchantService merchantService;

	// 风控等级依赖fo包
	private RcLimitRuleFacade rcLimitRuleFacade;

	/**
	 * @param rcLimitRuleFacade
	 *            the rcLimitRuleFacade to set
	 */
	public void setRcLimitRuleFacade(RcLimitRuleFacade rcLimitRuleFacade) {
		this.rcLimitRuleFacade = rcLimitRuleFacade;
	}

	public void setAutoFundoutConfigService(
			AutoFundoutConfigService autoFundoutConfigService) {
		this.autoFundoutConfigService = autoFundoutConfigService;
	}

	/**
	 * @param merchantService
	 *            the merchantService to set
	 */
	public void setMerchantService(IMerchantService merchantService) {
		this.merchantService = merchantService;
	}

	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request)
			throws Exception {
		log.debug("MerchantEditForCheckController.referenceData() is running...");
		Map<String, Object> dataMap = this.initData();
		String memberCode = request.getParameter("memberCode");
		String userRelation = request.getParameter("userRelation");
		List<MerchantDto> merchantList = null;
		boolean hasAutoFo = false;
		if (StringUtils.isNotEmpty(memberCode)) {
			merchantList = this.enterpriseService
					.getMerchantListByMemberCode(memberCode);
			if (merchantList.size() > 0) {
				for (MerchantDto merchantDto : merchantList) {
					merchantDto.setCityName(this.cityService.findByCityCode(
							merchantDto.getCity().intValue())
							.getCityname());
					if (StringUtils.isNotEmpty(merchantDto.getCityBank())) {
						merchantDto.setCityBankName(this.cityService
								.findByCityCode(
										Integer.valueOf(merchantDto
												.getCityBank())).getCityname());
					}
//					boolean isAutoFo = autoFundoutConfigService
//							.findByMemberCodeAndBankCard(
//									Long.valueOf(memberCode),
//									merchantDto.getBankAcct(),
//									merchantDto.getBankId());
//					if (!isAutoFo) {
//						hasAutoFo = true;
//						merchantDto.setAutoFundout(1);
//					} else {
//						merchantDto.setAutoFundout(0);
//					}

				}
				dataMap.put("merchantDto", (merchantList.get(0)));
			}
		}

		dataMap.put("merchantList", merchantList);
		dataMap.put("hasAutoFo", hasAutoFo);
		if(StringUtils.isNotEmpty(userRelation)){//BD判断
			dataMap.put("userRelation",userRelation);
		}
		return dataMap;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		EnterpriseFormBean enterpriseFormBean = (EnterpriseFormBean) command;

		String sign = null;
		Map<String, String> paraMap = new HashMap<String, String>();
		MerchantDto merchantDtoOld = this.enterpriseService
				.getMerchantListByMemberCode(
						enterpriseFormBean.getMemberCode().toString()).get(0);
		if (!enterpriseFormBean.getBizLicenceCode().equals(
				merchantDtoOld.getBizLicenceCode())) {
			paraMap.put("bizLicenceCode",
					enterpriseFormBean.getBizLicenceCode());
		}
		if (!enterpriseFormBean.getGovCode()
				.equals(merchantDtoOld.getGovCode())) {
			paraMap.put("govCode", enterpriseFormBean.getGovCode());
		}
		if (!enterpriseFormBean.getTaxCode()
				.equals(merchantDtoOld.getTaxCode())) {
			paraMap.put("taxCode", enterpriseFormBean.getTaxCode());
		}
		String isAllowResult = null;
		if (merchantDtoOld.getEmail().length() > 0
				&& !merchantDtoOld.getEmail().trim().toLowerCase()
						.equals(enterpriseFormBean.getEmail().toLowerCase())) {
			paraMap.put("email", enterpriseFormBean.getEmail());
			isAllowResult = merchantService
					.isAllowCreateOrUpdateMerchant(paraMap);
		}

		// if(paraMap.size()!=0){
		// isAllowResult =
		// this.enterpriseService.isAllowCreateOrUpdateMerchant(paraMap);
		// isAllowResult = isAllowResult +
		// this.enterpriseService.isMccExist(enterpriseFormBean.getIndustry());
		// added by wangtq 2011-01-10
		// SignMessageDto dto = new SignMessageDto();
		// dto.setDepartmentName(enterpriseFormBean.getSignDepart());
		// if(iSignMessageService.countSignMessageByCondition(dto)== 0){
		// isAllowResult = "您输入的签约人部门不存在,请与管理员联系...";
		// }
		// }
		

		Boolean isSuccess = false;
		if (StringUtils.isEmpty(isAllowResult)) {
			MerchantDto merchantDto = new MerchantDto();
			BeanUtils.copyProperties(enterpriseFormBean, merchantDto);
			
			isSuccess = enterpriseService.updateMerchantTrans(merchantDto);

			if (isSuccess) {
				sign = "修改成功!";
			} else {
				sign = "修改失败!请与管理员联系...";
			}
		} else {
			sign = isAllowResult;
		}

		return new ModelAndView(getSuccessView()).addObject("msg", sign)
				.addObject("isSuccess", isSuccess);

	}

	private Map<String, Object> initData() {

		List<ProvinceDTO> provinceList = provinceService.findAll();
		List<Relation> relationList = new ArrayList<Relation>();

		for (int i = 0; i < provinceList.size(); i++) {
			ProvinceDTO province = (ProvinceDTO) provinceList.get(i);
			List<CityDTO> cityList = cityService.findByProvinceId(province
					.getProvincecode());

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

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
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
