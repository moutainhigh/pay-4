package com.pay.poss.membermanager.controller;
/*package com.pay.poss.membermanager.controller;

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
import com.pay.commonservice.dto.CityDTO;
import com.pay.commonservice.dto.ProvinceDTO;
import com.pay.commonservice.service.CityService;
import com.pay.commonservice.service.ProvinceService;
import com.pay.ma.constant.MemberInfoStatusEnum;
import com.pay.ma.service.bo.member.EnterpriseBO;
import com.pay.ma.service.bo.member.EnterpriseSearchBO;
import com.pay.poss.base.dao.model.Page;
import com.pay.poss.base.util.PageUtils;
import com.pay.poss.membermanager.formbean.EnterpriseSearchFormBean;
import com.pay.poss.merchantmanager.model.Relation;
import com.pay.poss.service.ma.MB4MaServiceApi;

*//**
 * 
 * @Description
 * @project poss-membermanager
 * @file PersonalMemberListController.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-8-3 gungun_zhang Create
 *//*
public class EnterpriseMemberListController extends SimpleFormController {

	private Log log = LogFactory.getLog(EnterpriseMemberListController.class);
	private MB4MaServiceApi memberService;
	private CityService cityService;
	private ProvinceService provinceService;

	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		log.debug("EnterpriseMemberListController.referenceData() is running...");

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
		Map<String, Object> dataMap = new Hashtable<String, Object>();
		dataMap.put("provinceList", provinceList);
		dataMap.put("relationList", relationList);
		return dataMap;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {
		log.debug("EnterpriseMemberListController.onSubmit() is running...");
		EnterpriseSearchFormBean memberSearchFormBean = (EnterpriseSearchFormBean) command;
		EnterpriseSearchBO enterpriseSearchBO = new EnterpriseSearchBO();

		if (StringUtils.isNotEmpty(memberSearchFormBean.getUserName())) {
			enterpriseSearchBO.setUserName(memberSearchFormBean.getUserName());
		}
		if (StringUtils.isNotEmpty(memberSearchFormBean.getBusinessName())) {
			enterpriseSearchBO.setBusinessName(memberSearchFormBean.getBusinessName());
		}
		if (StringUtils.isNotEmpty(memberSearchFormBean.getWebsite())) {
			enterpriseSearchBO.setWebsite(memberSearchFormBean.getWebsite());
		}
		if (StringUtils.isNotEmpty(memberSearchFormBean.getContactPerson())) {
			enterpriseSearchBO.setContactPerson(memberSearchFormBean.getContactPerson());
		}
		if (StringUtils.isNotEmpty(memberSearchFormBean.getContactPhone())) {
			enterpriseSearchBO.setContactPhone(memberSearchFormBean.getContactPhone());
		}
		if (StringUtils.isNotEmpty(memberSearchFormBean.getProvince())) {
			enterpriseSearchBO.setProvince(memberSearchFormBean.getProvince());
		}
		if (StringUtils.isNotEmpty(memberSearchFormBean.getCity())) {
			enterpriseSearchBO.setCity(memberSearchFormBean.getCity());
		}

		Page<EnterpriseBO> page = PageUtils.getPage(request);
		List<EnterpriseBO> memberList = memberService.queryEnterpriseMember(enterpriseSearchBO, page.getPageNo(), page
				.getPageSize());
		if (memberList != null && memberList.size() != 0) {
			for (int i = 0; i < memberList.size(); i++) {
				EnterpriseBO enterprise = memberList.get(i);
				if (StringUtils.isNotEmpty(enterprise.getProvince())) {
					enterprise.setProvince(provinceService.findById(Integer.valueOf(enterprise.getProvince()))
							.getProvincename());
				}
				if (StringUtils.isNotEmpty(enterprise.getCity())) {
					enterprise.setCity(cityService.findByCityCode(Integer.valueOf(enterprise.getCity())).getCityname());
				}
				enterprise.setStatusString(MemberInfoStatusEnum.getByCode(enterprise.getStatus()).getDescription());
			}
		}
		Long memberListCount = memberService.queryEnterpriseMemberCount(enterpriseSearchBO);
		page.setResult(memberList);
		page.setTotalCount(memberListCount.intValue());

		return new ModelAndView(this.getSuccessView()).addObject("page", page);
	}

	
	public void setMemberService(MB4MaServiceApi memberService) {
		this.memberService = memberService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	public void setProvinceService(ProvinceService provinceService) {
		this.provinceService = provinceService;
	}

}
*/