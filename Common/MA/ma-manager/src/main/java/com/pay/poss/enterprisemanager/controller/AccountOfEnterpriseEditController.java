package com.pay.poss.enterprisemanager.controller;

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

import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.poss.enterprisemanager.dto.EnterpriseSearchListDto;
import com.pay.poss.enterprisemanager.model.BaseData;
import com.pay.poss.enterprisemanager.service.IEnterpriseService;

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

public class AccountOfEnterpriseEditController extends SimpleFormController {

	private final Log log = LogFactory.getLog(AccountOfEnterpriseEditController.class);
	private IEnterpriseService enterpriseService;

	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request)
			throws Exception {
		log.debug("AccountOfEnterpriseEditController.referenceData is running...");

		String memberCode = request.getParameter("memberCode");
		EnterpriseSearchListDto enterprise = enterpriseService
				.queryEnterpriseByMemberCode(memberCode);
		List<BaseData> accountOfEnterpriseList = enterpriseService
				.queryAccountByMemberCode(memberCode);

		Map<String, Object> dataMap = new Hashtable<String, Object>();

		dataMap.put("enterprise", enterprise);
		dataMap.put("baseDataList", AcctTypeEnum.getBasicAcctTypes());
		dataMap.put("guaranteeDataList", AcctTypeEnum.getGuaranteeAcctTypes());
		dataMap.put("accountOfEnterpriseList", accountOfEnterpriseList);
		return dataMap;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		log.debug("AccountOfEnterpriseEditController.onSubmit is running...");

		String memberCode = request.getParameter("memberCode");
		String accountOfEnterprise[] = request
				.getParameterValues("accountType");
		Boolean isSuccess = enterpriseService.accountOfEnterpriseEditTrans(
				memberCode, accountOfEnterprise);
		String sign = "";
		if (isSuccess) {
			sign = "开通成功!";
		} else {
			sign = "开通失败,请与管理员联系...";
		}
		List<BaseData> accountOfEnterpriseList = enterpriseService
				.queryAccountByMemberCode(memberCode);
		EnterpriseSearchListDto enterprise = enterpriseService
				.queryEnterpriseByMemberCode(memberCode);

		Map<String, Object> dataMap = new Hashtable<String, Object>();
		dataMap.put("enterprise", enterprise);
		dataMap.put("sign", sign);
		dataMap.put("baseDataList", AcctTypeEnum.getBasicAcctTypes());
		dataMap.put("guaranteeDataList", AcctTypeEnum.getGuaranteeAcctTypes());
		dataMap.put("accountOfEnterpriseList", accountOfEnterpriseList);
		return new ModelAndView(this.getSuccessView()).addAllObjects(dataMap);
	}

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

}
