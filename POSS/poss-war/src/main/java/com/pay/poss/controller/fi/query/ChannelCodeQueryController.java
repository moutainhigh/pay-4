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
import com.pay.poss.client.ChannelClientService;
import com.pay.poss.client.GatewayOrderQueryService;
import com.pay.poss.dto.PaymentChannelItemDto;
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
public class ChannelCodeQueryController extends MultiActionController {

	private String initView;
	private String listView;
	private String detailView;
	private ChannelClientService channelClientService;
	private GatewayOrderQueryService gatewayOrderQueryService;

	/**
	 * 默认查询页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		final PaymentChannelItemDto paymentChannelItem = new PaymentChannelItemDto();
		final List paymentChannelItems = channelClientService
				.queryChannelItem(paymentChannelItem);
		return new ModelAndView(initView).addObject("paymentChannelItems",
				paymentChannelItems);
	}

	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView list(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		final String orgCode = StringUtil.null2String(request
				.getParameter("orgCode"));
		final String paymentOrderNo = StringUtil.null2String(request
				.getParameter("paymentOrderNo"));
		final String channelOrderNo = StringUtil.null2String(request
				.getParameter("channelOrderNo"));
		final String status = StringUtil.null2String(request
				.getParameter("status"));
		final String startTime = StringUtil.null2String(request
				.getParameter("startTime"));
		final String endTime = StringUtil.null2String(request
				.getParameter("endTime"));
		final String authorisation = StringUtil.null2String(request
				.getParameter("authorisation"));
		final String partnerId = StringUtil.null2String(request
				.getParameter("partnerId"));
		final String tradeOrderNo = StringUtil.null2String(request
				.getParameter("tradeOrderNo"));
		final String orderId = StringUtil.null2String(request
				.getParameter("orderId"));
		final String errorCode = StringUtil.null2String(request
				.getParameter("errorCode"));

		final Map paraMap = new HashMap();

		paraMap.put("paymentOrderNo", paymentOrderNo);
		paraMap.put("channelOrderNo", channelOrderNo);
		paraMap.put("status", status);
		paraMap.put("beginTime", startTime);
		paraMap.put("endTime", endTime);
		paraMap.put("orgCode", orgCode);
		paraMap.put("authorisation", authorisation);
		paraMap.put("partnerId", partnerId);
		paraMap.put("tradeOrderNo", tradeOrderNo);
		paraMap.put("orderId", orderId);
		paraMap.put("errorCode", errorCode);

		Page page = PageUtils.getPage(request);
		paraMap.put("page", page);

		final Map resultMap = gatewayOrderQueryService
				.queryChannelOrder(paraMap);
		final List<Map> channelOrders = (List<Map>) resultMap.get("result");
		final Map pageMap = (Map) resultMap.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);
		return new ModelAndView(listView).addObject("list", channelOrders)
				.addObject("page", page);

	}

	public void setInitView(final String initView) {
		this.initView = initView;
	}

	public void setListView(final String listView) {
		this.listView = listView;
	}

	public void setDetailView(final String detailView) {
		this.detailView = detailView;
	}

	public void setGatewayOrderQueryService(
			final GatewayOrderQueryService gatewayOrderQueryService) {
		this.gatewayOrderQueryService = gatewayOrderQueryService;
	}

	public void setChannelClientService(
			final ChannelClientService channelClientService) {
		this.channelClientService = channelClientService;
	}

}
