package com.pay.poss.membermanager.controller;
/*package com.pay.poss.membermanager.controller;

import java.util.Calendar;
import java.util.Date;
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

import com.pay.gateway.common.securedtransaction.enums.FundsFlowEnum;
import com.pay.gateway.common.securedtransaction.enums.TransStatusEnum;
import com.pay.gateway.common.transaction.TradeTypeEnum;
import com.pay.gateway.dto.transaction.QueryTransactionCountDTO;
import com.pay.gateway.dto.transaction.QueryTransactionDetailDTO;
import com.pay.gateway.dto.transaction.QueryTransactionParamDTO;
import com.pay.poss.base.dao.model.Page;
import com.pay.poss.base.util.PageUtils;
import com.pay.poss.membermanager.common.Constants;
import com.pay.poss.membermanager.formbean.MemberTradeSearchFormBean;
import com.pay.poss.service.gateway.MB4GateWayServiceApi;
import com.pay.util.DateUtil;

*//**
 * 
 * @Description
 * @project poss-membermanager
 * @file MemberAccountListController.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-8-3 gungun_zhang Create
 *//*

public class TradeListController extends SimpleFormController {

	private Log log = LogFactory.getLog(TradeListController.class);
	private MB4GateWayServiceApi memberService;

	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		log.debug("TradeListController.referenceData() is running...");

		String memberCode = (String) request.getParameter("memberCode");
		String userName = (String) request.getParameter("userName");
		userName = new String(userName.getBytes(Constants.ENCODING_ISO), Constants.ENCODING_UTF);
		String memberType = (String) request.getParameter("memberType");
		String titleText;

		if (StringUtils.isNotEmpty(memberType) && memberType.equals("0")) {
			titleText = Constants.TITLE_PERSONAL;

		} else if (StringUtils.isNotEmpty(memberType) && memberType.equals("1")) {
			titleText = Constants.TITLE_ENTERPRISE;

		} else {
			titleText = Constants.TITLE_ERROR;
		}

		FundsFlowEnum[] fundsFlowEnumArray = FundsFlowEnum.values();
		TransStatusEnum[] transStatusEnumArray = TransStatusEnum.values();
		TradeTypeEnum[] tradeTypeEnumArray = TradeTypeEnum.values();

		Map<String, Object> dataMap = new Hashtable<String, Object>();
		dataMap.put("userName", userName);
		dataMap.put("memberCode", memberCode);
		dataMap.put("memberType", memberType);
		dataMap.put("titleText", titleText);
		dataMap.put("fundsArray", fundsFlowEnumArray);
		dataMap.put("transStatusEnumArray", transStatusEnumArray);
		dataMap.put("tradeTypeEnumArray", tradeTypeEnumArray);
		return dataMap;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {
		log.debug("TradeListController.onSubmit() is running...");

		MemberTradeSearchFormBean memberTradeSearchFormBean = (MemberTradeSearchFormBean) command;
		QueryTransactionParamDTO queryTransactionParamDTO = new QueryTransactionParamDTO();

		if (StringUtils.isNotEmpty(memberTradeSearchFormBean.getMemberType())) {
			queryTransactionParamDTO.setMemberType(Integer.valueOf(memberTradeSearchFormBean.getMemberType()));
		}
		if (StringUtils.isNotEmpty(memberTradeSearchFormBean.getMemberCode())) {
			queryTransactionParamDTO.setMemberCode(Long.valueOf(memberTradeSearchFormBean.getMemberCode()));
		}
		if (StringUtils.isNotEmpty(memberTradeSearchFormBean.getTimeSelect())) {
			String timeSelect = memberTradeSearchFormBean.getTimeSelect();

			if (timeSelect.equals(Constants.CURRENTLY_DAY)) {
				queryTransactionParamDTO.setStartTime(new Date());
				queryTransactionParamDTO.setEndTime(new Date());
			} else if (timeSelect.equals(Constants.CURRENTLY_WEEK)) {
				Calendar calendar = Calendar.getInstance();
				int weekday = calendar.get(Calendar.DAY_OF_WEEK) - 1;
				calendar.add(Calendar.DATE, 1 - weekday);

				queryTransactionParamDTO.setStartTime(calendar.getTime());
				queryTransactionParamDTO.setEndTime(new Date());
			} else if (timeSelect.equals(Constants.CURRENTLY_MONTH)) {
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				queryTransactionParamDTO.setStartTime(calendar.getTime());
				queryTransactionParamDTO.setEndTime(new Date());
			} else {

			}
		}
		if (StringUtils.isNotEmpty(memberTradeSearchFormBean.getStartTime())) {
			queryTransactionParamDTO.setStartTime(DateUtil.parse("yyyy-MM-dd", memberTradeSearchFormBean.getStartTime()));
		}
		if (StringUtils.isNotEmpty(memberTradeSearchFormBean.getEndTime())) {
			queryTransactionParamDTO.setEndTime(DateUtil.parse("yyyy-MM-dd", memberTradeSearchFormBean.getEndTime()));
		}
		if (StringUtils.isNotEmpty(memberTradeSearchFormBean.getTransactionStatus())) {
			queryTransactionParamDTO.setTransactionStatus(Integer.valueOf(memberTradeSearchFormBean.getTransactionStatus()));
		} else {
			queryTransactionParamDTO.setTransactionStatus(TransStatusEnum.ALL.getCode());
		}
		if (StringUtils.isNotEmpty(memberTradeSearchFormBean.getFunds())) {
			queryTransactionParamDTO.setFunds(Integer.valueOf(memberTradeSearchFormBean.getFunds()));
		}
		if (StringUtils.isNotEmpty(memberTradeSearchFormBean.getCounterPartName())) {
			queryTransactionParamDTO.setCounterPartName(memberTradeSearchFormBean.getCounterPartName());
		}
		if (StringUtils.isNotEmpty(memberTradeSearchFormBean.getGoodsName())) {
			queryTransactionParamDTO.setGoodsName(memberTradeSearchFormBean.getGoodsName());
		}
		if (StringUtils.isNotEmpty(memberTradeSearchFormBean.getTransactionNo())) {
			queryTransactionParamDTO.setTransactionNo(memberTradeSearchFormBean.getTransactionNo());
		}
		if (StringUtils.isNotEmpty(memberTradeSearchFormBean.getTradeType())) {
			queryTransactionParamDTO.setTradeType(memberTradeSearchFormBean.getTradeType());
		}

		Page<QueryTransactionDetailDTO> page = PageUtils.getPage(request);

		List<QueryTransactionDetailDTO> tradeInfoList = memberService.queryTrade(queryTransactionParamDTO, page.getPageSize(),
				page.getPageNo());
		QueryTransactionCountDTO queryTransactionCountDTO = memberService.queryTradeCount(queryTransactionParamDTO);
		page.setResult(tradeInfoList);
		page.setTotalCount(queryTransactionCountDTO.getCount());

		Map<String, Object> dataMap = new Hashtable<String, Object>();
		dataMap.put("queryTransactionCountDTO", queryTransactionCountDTO);
		dataMap.put("page", page);

		return new ModelAndView(this.getSuccessView()).addAllObjects(dataMap);

	}

	public void setMemberService(MB4GateWayServiceApi memberService) {
		this.memberService = memberService;
	}

	

}
*/