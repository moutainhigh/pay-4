package com.pay.poss.controller.fi.crosspay;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.StringUtil;

/**
 * 交易冻结
 * 
 * @Description
 * @file PartnerExchRateController
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2012 hnapayay Corporation. All rights reserved.
 *          Date
 */
public class RefusePaymentOrderController extends MultiActionController {

	private String queryInit;
	
	public void setQueryInit(String queryInit) {
		this.queryInit = queryInit;
	}
	
	private String recordList;

	public void setRecordList(String recordList) {
		this.recordList = recordList;
	}
	
	private String refuseOrderPage;

	public String getRefuseOrderPage() {
		return refuseOrderPage;
	}

	public void setRefuseOrderPage(String refuseOrderPage) {
		this.refuseOrderPage = refuseOrderPage;
	}


	/**
	 * 默认查询页面
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(queryInit);
	}
	
	/**
	 * 查询交易信息列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String tradeOrderNo = StringUtil.null2String(request.getParameter("tradeOrderNo"));
		String partnerId = StringUtil.null2String(request.getParameter("partnerId"));
		
		Map<String, Object> refusePaymentOrderCriteria = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(tradeOrderNo)){
			refusePaymentOrderCriteria.put("tradeOrderNo", tradeOrderNo);
		}
		if(StringUtils.isNotBlank(partnerId)){
			refusePaymentOrderCriteria.put("partnerId", partnerId);
		}
		
		return new ModelAndView(recordList);
	}
	
	/**
	 * 拒付申请
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView refuseOrderApp(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> conMap = new HashMap<String, Object>();
		String tradeOrderNo = StringUtil.null2String(request.getParameter("tradeOrderNo"));
		String operator = SessionUserHolderUtil.getLoginId();
		conMap.put("tradeOrderNo", tradeOrderNo);
		conMap.put("operator", operator);
		//refusePaymentOrderService.refuseOrderApp(conMap);
		return null;
	}
	
	/**
	 * 查询交易信息列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView listRefuseOrders(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 拒付订单号
		String id = StringUtil.null2String(request.getParameter("id"));
		// 原订单号
		String tradeOrderNo = StringUtil.null2String(request.getParameter("tradeOrderNo"));
		// 交易时间
		String orderTime = StringUtil.null2String(request.getParameter("orderTime"));
		Map<String, Object> refusePaymentOrderCriteria = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(tradeOrderNo)){
			refusePaymentOrderCriteria.put("tradeOrderNo", tradeOrderNo);
		}
		if(StringUtils.isNotBlank(id)){
			refusePaymentOrderCriteria.put("id", id);
		}
		if(StringUtils.isNotBlank(orderTime)){
			refusePaymentOrderCriteria.put("orderTime", orderTime);
		}
		
//		Page<RefusePaymentOrder> page = PageUtils.getPage(request);
//		page = refusePaymentOrderService.queryRefusePaymentOrderForPage1(refusePaymentOrderCriteria, page);
		return new ModelAndView(recordList);
	}
	
	/**
	 * 查询拒付订单详情
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView getRefuseOrder(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> conMap = new HashMap<String, Object>();
		String id = StringUtil.null2String(request.getParameter("id"));
		conMap.put("id", id);
//		Map<String, Object> refuseOrderMap = refusePaymentOrderService.getRefuseOrder(conMap);
		return new ModelAndView(refuseOrderPage);
	}
	
	/**
	 * 更新拒付订单状态
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView updateRefuseOrderStatus(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> conMap = new HashMap<String, Object>();
		String id = StringUtil.null2String(request.getParameter("id"));
		String tradeOrderNo = StringUtil.null2String(request.getParameter("tradeOrderNo"));
		String refuseStatus = StringUtil.null2String(request.getParameter("refuseStatus"));
		String refuseReason = StringUtil.null2String(request.getParameter("refuseReason"));
		
		String operator = SessionUserHolderUtil.getLoginId();
		conMap.put("id", id);
		conMap.put("tradeOrderNo", tradeOrderNo);
		conMap.put("refuseReason", refuseReason);
		conMap.put("refuseStatus", refuseStatus);
		conMap.put("operator", operator);
		//refusePaymentOrderService.updateRefuseOrderStatus(conMap);
		return null;
	}
}
