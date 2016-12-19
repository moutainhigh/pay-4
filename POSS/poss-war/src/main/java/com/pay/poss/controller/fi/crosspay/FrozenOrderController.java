package com.pay.poss.controller.fi.crosspay;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.poss.client.CrosspayTxncoreClientService;
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
public class FrozenOrderController extends MultiActionController {

	private String queryInit;
	private String frozenTradePage;
	private CrosspayTxncoreClientService crosspayTxncoreClientService;
	//private FrozenOrderService frozenOrderService;

	private String recordList;

	public void setRecordList(String recordList) {
		this.recordList = recordList;
	}

	public void setFrozenTradePage(String frozenTradePage) {
		this.frozenTradePage = frozenTradePage;
	}

	public void setQueryInit(String queryInit) {
		this.queryInit = queryInit;
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

		String tradeOrderNo = StringUtil.null2String(request
				.getParameter("tradeOrderNo"));
		String partnerId = StringUtil.null2String(request
				.getParameter("partnerId"));

		Map<String, Object> frozenOrderCriteria = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(tradeOrderNo)) {
			frozenOrderCriteria.put("tradeOrderNo", tradeOrderNo);
		}
		if (StringUtils.isNotBlank(partnerId)) {
			frozenOrderCriteria.put("partnerId", partnerId);
		}

		Object list = crosspayTxncoreClientService
				.queryFrozenOrder(frozenOrderCriteria);
		return new ModelAndView(recordList).addObject("list", list);
	}

	/**
	 * 查询订单信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView viewTradeOrder(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> conMap = new HashMap<String, Object>();
		String tradeOrderNo = StringUtil.null2String(request
				.getParameter("tradeOrderNo"));
		conMap.put("tradeOrderNo", tradeOrderNo);

		Map<String, Object> tradeOrderMap = crosspayTxncoreClientService
				.queryFrozenOrder(tradeOrderNo);
		return new ModelAndView(frozenTradePage).addObject("tradeOrderMap",
				tradeOrderMap);
	}

	/**
	 * 冻结交易
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView frozenOrder(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> conMap = new HashMap<String, Object>();
		String tradeOrderNo = StringUtil.null2String(request
				.getParameter("tradeOrderNo"));
		String frozenReason = StringUtil.null2String(request
				.getParameter("frozenReason"));
		String operator = SessionUserHolderUtil.getLoginId();
		conMap.put("tradeOrderNo", tradeOrderNo);
		conMap.put("frozenReason", frozenReason);
		conMap.put("operator", operator);
		//frozenOrderService.frozenOrder(conMap);
		return null;
	}

	/**
	 * 解冻交易
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView unFrozenOrder(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> conMap = new HashMap<String, Object>();
		String tradeOrderNo = StringUtil.null2String(request
				.getParameter("tradeOrderNo"));
		String operator = SessionUserHolderUtil.getLoginId();
		conMap.put("tradeOrderNo", tradeOrderNo);
		conMap.put("operator", operator);
		//frozenOrderService.unFrozenOrder(conMap);
		return null;
	}
}
