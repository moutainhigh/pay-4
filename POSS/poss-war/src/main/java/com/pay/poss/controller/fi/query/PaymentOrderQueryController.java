package com.pay.poss.controller.fi.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.client.GatewayOrderQueryService;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;

/**
 * 网站管理poss 后台
 * 
 * @Description
 * @file SitesetController
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2012 hnapayay Corporation. All rights reserved.
 *          Date
 */
public class PaymentOrderQueryController extends MultiActionController {

	private String initView;
	private String listView;
	private String detailView;
	private GatewayOrderQueryService gatewayOrderQueryService;

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
		return new ModelAndView(initView);
	}

	/**
	 * 查询网站信息列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String partnerId = StringUtil.null2String(request
				.getParameter("partnerId"));
		String paymentOrderNo = StringUtil.null2String(request
				.getParameter("paymentOrderNo"));
		String tradeOrderNo = StringUtil.null2String(request
				.getParameter("tradeOrderNo"));
		String status = StringUtil.null2String(request.getParameter("status"));
		String startTime = StringUtil.null2String(request
				.getParameter("startTime"));
		String endTime = StringUtil
				.null2String(request.getParameter("endTime"));

		Map paraMap = new HashMap();

		paraMap.put("payee", partnerId);
		paraMap.put("paymentOrderNo", paymentOrderNo);
		paraMap.put("tradeOrderNo", tradeOrderNo);
		paraMap.put("status", status);
		paraMap.put("beginTime", startTime);
		paraMap.put("endTime", endTime);

		Page page = PageUtils.getPage(request);
		paraMap.put("page", page);

		Map resultMap = gatewayOrderQueryService.queryPaymentOrder(paraMap);
		List<Map> paymentOrders = (List<Map>) resultMap.get("result");

		Map pageMap = (Map) resultMap.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);
		return new ModelAndView(listView).addObject("list", paymentOrders)
				.addObject("page", page);

	}

	public void setInitView(String initView) {
		this.initView = initView;
	}

	public void setListView(String listView) {
		this.listView = listView;
	}

	public void setDetailView(String detailView) {
		this.detailView = detailView;
	}

	public void setGatewayOrderQueryService(
			GatewayOrderQueryService gatewayOrderQueryService) {
		this.gatewayOrderQueryService = gatewayOrderQueryService;
	}

}
