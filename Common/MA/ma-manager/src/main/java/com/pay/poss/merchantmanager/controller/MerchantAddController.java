package com.pay.poss.merchantmanager.controller;

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
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.pay.acc.comm.LiquidateModeEnum;
import com.pay.acc.comm.MerchantLevelEnum;
import com.pay.acc.comm.MerchantTypeEnum;
import com.pay.inf.dto.Bank;
import com.pay.inf.dto.CityDTO;
import com.pay.inf.dto.ProvinceDTO;
import com.pay.inf.service.BankService;
import com.pay.inf.service.CityService;
import com.pay.inf.service.ProvinceService;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.merchantmanager.dto.MerchantDto;
import com.pay.poss.merchantmanager.enums.DepartmentEnum;
import com.pay.poss.merchantmanager.enums.NationEnum;
import com.pay.poss.merchantmanager.formbean.MerchantFormBean;
import com.pay.poss.merchantmanager.model.Relation;
import com.pay.poss.merchantmanager.service.IMerchantService;
import com.pay.poss.merchantmanager.service.ISignMessageService;
import com.pay.rm.facade.RcLimitRuleFacade;
import com.pay.rm.service.model.rmlimit.risklevel.RcRiskLevel;
import com.pay.util.StringUtil;

/**
 * 
 * @Description
 * @project poss-membermanager
 * @file MerchantAddController.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-8-11 gungun_zhang Create 2011-5-1 DDR update
 */

public class MerchantAddController extends SimpleFormController {

	private final Log log = LogFactory.getLog(MerchantAddController.class);
	private IMerchantService merchantService;
	private CityService cityService;
	private ProvinceService provinceService;
	private BankService bankService;
	private ISignMessageService iSignMessageService;
	// 风控等级依赖fo包
	private RcLimitRuleFacade rcLimitRuleFacade;

	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request)
			throws Exception {
		log.debug("MerchantAddController.referenceData() is running...");
		Map<String, Object> dataMap = this.initData();
		return dataMap;
	}

	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		log.debug("MerchantAddController.onSubmit() is running...");

		MerchantFormBean merchantFormBean = (MerchantFormBean) command;
		String sign = null;
		Map<String, String> paraMap = new HashMap<String, String>();
		// paraMap.put("bizLicenceCode", merchantFormBean.getBizLicenceCode());
		// paraMap.put("govCode", merchantFormBean.getGovCode());
		// paraMap.put("taxCode", merchantFormBean.getTaxCode());
		paraMap.put("email", merchantFormBean.getEmail());
		String isAllowResult = null;
		// /edit by ddr 现在只判断 email ;//判断 修改成不验证这些 商户营业执照号码 商户税务登记证号码 商户机构代码证号码
		isAllowResult = this.merchantService
				.isAllowCreateOrUpdateMerchant(paraMap);
		Boolean isSuccess = false;
		String merchantCode = null;
		if (StringUtils.isEmpty(isAllowResult)) {
			MerchantDto merchantDto = new MerchantDto();

			BeanUtils.copyProperties(merchantFormBean, merchantDto);
			// 日志暂时不保存,session超时会导致新增失败
			// SessionUserHolder user = MerchantUtils.getUserInfo(request);
			// merchantDto.setUserId(user.getUserKy());
			// merchantDto.setUserName(user.getUsername());
			// merchantDto.setUserDept(user.getOrgCode());
			try {
				
				String merchantCodePrefix = request.getParameter("merchantCodePrefix");
				
				merchantDto.setMerchantCodePrefix(merchantCodePrefix);
				merchantCode = merchantService.createMerchantTrans(merchantDto);
			} catch (PossException e) {
				log.error("MerchantAddController.createMerchant is error...", e);
			}

			if (merchantCode == null) {
				sign = "添加失败!请与管理员联系...";

			} else {
				sign = "添加已成功!";
				isSuccess = true;
			}

		} else {
			sign = isAllowResult;
		}

		return new ModelAndView(this.getSuccessView()).addObject("msg", sign)
				.addObject("isSuccess", isSuccess)
				.addObject("memberCode", merchantCode);
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

		dataMap.put("provinceList", provinceList);
		dataMap.put("relationList", relationList);
		dataMap.put("departmentEnum", departmentEnum);
		dataMap.put("liquidateModeEnum", liquidateModeEnum);
		dataMap.put("merchantLevelEnum", merchantLevelEnum);
		dataMap.put("merchantTypeEnum", merchantTypeEnum);
		List<RcRiskLevel> riskLevelList = rcLimitRuleFacade.getRiskLevelList();
		dataMap.put("riskLevelList", riskLevelList);
		dataMap.put("nationEnum", nationEnum);
		dataMap.put("bankList", bankList);

		return dataMap;
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

	/**
	 * @param rcLimitRuleFacade
	 *            the rcLimitRuleFacade to set
	 */
	public void setRcLimitRuleFacade(RcLimitRuleFacade rcLimitRuleFacade) {
		this.rcLimitRuleFacade = rcLimitRuleFacade;
	}

}
