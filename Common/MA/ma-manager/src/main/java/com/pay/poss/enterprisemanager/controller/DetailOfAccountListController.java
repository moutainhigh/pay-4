package com.pay.poss.enterprisemanager.controller;

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

import com.pay.acc.service.account.constantenum.PayForEnum;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.enterprisemanager.dto.EnterpriseSearchDto;
import com.pay.poss.enterprisemanager.dto.EnterpriseSearchListDto;
import com.pay.poss.enterprisemanager.enums.DealTypeEnum;
import com.pay.poss.enterprisemanager.enums.ValueFlowTypeEnum;
import com.pay.poss.enterprisemanager.formbean.EnterpriseSearchFormBean;
import com.pay.poss.enterprisemanager.service.IEnterpriseService;

/**
 * 
 * @Description
 * @project poss-membermanager
 * @file DetailOfAccountListController.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-10-22 gungun_zhang Create
 *          2016-08-04 nico.shao modify : 针对明细查询，增加了deal的voucherCode 和 balance的voucherCode 作为匹配条件
 *          							  有的时候，同一个dealid 或者 dealCode,如果有多条记录（比如业务错误），查询出来就不对了。
 */

public class DetailOfAccountListController extends SimpleFormController {

	private final Log log = LogFactory
			.getLog(DetailOfAccountListController.class);
	private IEnterpriseService enterpriseService;

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request)
			throws Exception {
		log.debug("DetailOfAccountListController.referenceData is running...");

		String accountCode = request.getParameter("accountCode");
		ValueFlowTypeEnum[] valueFlowTypeEnum = ValueFlowTypeEnum.values();
		DealTypeEnum[] dealTypeEnum = DealTypeEnum.values();
		Map<String, Object> dataMap = new Hashtable<String, Object>();
		dataMap.put("accountCode", accountCode);
		dataMap.put("dealTypeEnum", dealTypeEnum);
		dataMap.put("valueFlowTypeEnum", valueFlowTypeEnum);
		dataMap.put("dealTypeList", PayForEnum.SEARCH_TYPES);
		return dataMap;
	}

	/*
	 * add by nico.shao 2016-08-04 
	 */
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		log.info("DetailOfAccountListController.onSubmit is running...");
		EnterpriseSearchFormBean enterpriseSearchFormBean = (EnterpriseSearchFormBean) command;
		EnterpriseSearchDto enterpriseSearchDto = new EnterpriseSearchDto();
		BeanUtils.copyProperties(enterpriseSearchFormBean, enterpriseSearchDto);
		Page<EnterpriseSearchListDto> page = PageUtils.getPage(request);
		enterpriseSearchDto.setPageEndRow((page.getPageNo() * page
				.getPageSize()) + "");
		if ((page.getPageNo() - 1) == 0) {
			enterpriseSearchDto.setPageStartRow("0");
		} else {
			enterpriseSearchDto.setPageStartRow((page.getPageNo() - 1)
					* page.getPageSize() + "");
		}

		List<EnterpriseSearchListDto> detailOfAccountList = enterpriseService
				.queryDetailOfAccount(enterpriseSearchDto);
		
		if (detailOfAccountList != null) {
			for (EnterpriseSearchListDto enterpriseSearchListDto : detailOfAccountList) {
				Map<String, String> paraMap = new HashMap<String, String>(3);
				paraMap.put("dealId", enterpriseSearchListDto.getDealId());
				paraMap.put("acctCode",
						enterpriseSearchListDto.getAccountCode());
				if (enterpriseSearchListDto.getDealType().equals(
						String.valueOf(PayForEnum.FEE_AMOUNT.getCode()))) {
					paraMap.put("status", "1");
				} else {
					paraMap.put("status", "0");
				}
				String dealCode = enterpriseSearchListDto.getDealCode() == null ? ""
						: String.valueOf(enterpriseSearchListDto.getDealCode());
				paraMap.put("dealCode", dealCode);
				
				//add by nico.shao 2016-08-04 
				String voucherCode = enterpriseSearchListDto.getVoucherCode();
				if(!StringUtils.isEmpty(voucherCode)){
					paraMap.put("voucherCode", voucherCode);
				}
				//end 2016-08-04
				
				String accDetailBalance = enterpriseService
						.queryBalance(paraMap);
				enterpriseSearchListDto.setBalance(accDetailBalance);

				String dealType = enterpriseSearchListDto.getDealType();

				PayForEnum payForEnumType = PayForEnum.get(Integer
						.parseInt(dealType));

				if (null != payForEnumType) {
					enterpriseSearchListDto.setDealType(payForEnumType
							.getMessage());
				}

			}
		}
		Integer detailOfAccountListCount = enterpriseService
				.queryDetailOfAccountCount(enterpriseSearchDto);

		page.setResult(detailOfAccountList);
		page.setTotalCount(detailOfAccountListCount);

		DealTypeEnum[] dealTypeEnum = DealTypeEnum.values();
		Map<String, Object> dataMap = new Hashtable<String, Object>();
		dataMap.put("dealTypeEnum", dealTypeEnum);
		dataMap.put("dealTypeList", PayForEnum.SEARCH_TYPES);
		dataMap.put("page", page);
		 return new ModelAndView(this.getSuccessView()).addAllObjects(dataMap);
	}
}
