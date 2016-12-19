package com.pay.poss.controller.fi.crosspay;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.util.StringUtil;

/**
 * 
 * File: WithdrawalsApprovalController.java Description:提现审核 Copyright 2006-2014
 * HNAPAY Corporation. All rights reserved. Date Author Changes 2014年9月23日
 * liuqinghe Create
 *
 */
public class WithdrawalsApprovalController extends MultiActionController {

	//private PartnerWithdrawOrderService partnerWithdrawOrderService;

	private String initPage;

	private String queryList;

	private final Log logger = LogFactory.getLog(getClass());

	

	public String getInitPage() {
		return initPage;
	}

	public void setInitPage(String initPage) {
		this.initPage = initPage;
	}

	public String getQueryList() {
		return queryList;
	}

	public void setQueryList(String queryList) {
		this.queryList = queryList;
	}

	/**
	 * 默认查询页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(initPage);
	}

	public ModelAndView search(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String partnerId = StringUtil.null2String(request
				.getParameter("partnerId"));
		String sDate = StringUtil.null2String(request.getParameter("sDate"));
		String eDate = StringUtil.null2String(request.getParameter("eDate"));

		Map<String, Object> partnerWithdrawMap = new HashMap<String, Object>();

		if (StringUtils.isNotBlank(partnerId)) {
			partnerWithdrawMap.put("partnerId", partnerId);
		}
		if (StringUtils.isNotBlank(sDate)) {
			partnerWithdrawMap.put("startDate", sDate);
		}
		if (StringUtils.isNotBlank(eDate)) {
			partnerWithdrawMap.put("endDate", eDate);
		}

//		Page<PartnerWithdrawOrder> page = PageUtils.getPage(request);
//
//		page = partnerWithdrawOrderService.queryPartnerWithdrawOrderForPage(
//				partnerWithdrawMap, page);

		return new ModelAndView(queryList);
	}

	/**
	 * 提现审核通过
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView approval(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String id = StringUtil.null2String(request.getParameter("id"));

		try {
			long t_id = Long.parseLong(id);
//			PartnerWithdrawOrder pwo = partnerWithdrawOrderService
//					.findById(t_id);
//			pwo.setStatus(WithdrawalsApprovalStatusEnum.APPR_PASS.getValue());
//			pwo.setAuditDate(new Date());
//			Authentication authentication = SecurityContextHolder.getContext()
//					.getAuthentication();
//			SessionUserHolder sessionUserHolder = (SessionUserHolder) authentication
//					.getPrincipal();
//			String userName = sessionUserHolder.getUsername();
//			pwo.setOperator(userName);
//			partnerWithdrawOrderService.updatePartnerWithdrawOrder(pwo);
		} catch (Exception e) {
			logger.error("", e);
		}

		return search(request, response);
	}

	/**
	 * 提现完成
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView complete(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String id = StringUtil.null2String(request.getParameter("id"));

		try {
			long t_id = Long.parseLong(id);
//			PartnerWithdrawOrder pwo = partnerWithdrawOrderService
//					.findById(t_id);
//			pwo.setStatus(WithdrawalsApprovalStatusEnum.APPR_COMP.getValue());
//			Authentication authentication = SecurityContextHolder.getContext()
//					.getAuthentication();
//			SessionUserHolder sessionUserHolder = (SessionUserHolder) authentication
//					.getPrincipal();
//			String userName = sessionUserHolder.getUsername();
//			pwo.setOperator(userName);
//			partnerWithdrawOrderService.updatePartnerWithdrawOrder(pwo);
		} catch (Exception e) {
			logger.error("", e);
		}

		return search(request, response);
	}

}
