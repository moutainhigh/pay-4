package com.pay.poss.controller.fi.coupon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.poss.client.GatewayClientService;
import com.pay.poss.security.util.SessionUserHolderUtil;
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
public class CouponController extends MultiActionController {

	private final Log logger = LogFactory.getLog(getClass());
	private String queryInit;
	private String addView;
	private String recordList;
	private String updateView;
	private GatewayClientService gatewayClientService;

	public void setQueryInit(String queryInit) {
		this.queryInit = queryInit;
	}

	public void setRecordList(String recordList) {
		this.recordList = recordList;
	}

	public void setAddView(String addView) {
		this.addView = addView;
	}

	public void setGatewayClientService(
			GatewayClientService gatewayClientService) {
		this.gatewayClientService = gatewayClientService;
	}

	public void setUpdateView(String updateView) {
		this.updateView = updateView;
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
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toAdd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView(addView);
	}

	/**
	 * 添加
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String couponNumber = request.getParameter("couponNumber");
		String effectDate = request.getParameter("effectDate");
		String expireDate = request.getParameter("expireDate");
		String value = request.getParameter("value");
		String minOrderAmount = request.getParameter("minOrderAmount");
		String scene = request.getParameter("scene");
		String orgCode = request.getParameter("orgCode");

		String operator = SessionUserHolderUtil.getLoginId();

		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("effectDate", effectDate);
		paraMap.put("expireDate", expireDate);
		paraMap.put("couponNumber", couponNumber);
		paraMap.put("value", value);
		paraMap.put("minOrderAmount", minOrderAmount);
		paraMap.put("scene", scene);
		paraMap.put("orgCode", orgCode);

		try{
			gatewayClientService.addCouponList(paraMap);
			response.getWriter().print(1);
		}catch(Exception e){
			e.printStackTrace();
			response.getWriter().print(0);
		}
		
		return null;

	}

	/**
	 * 查询网站信息列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView query(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String effectDate = StringUtil.null2String(request
				.getParameter("effectDate"));
		String expireDate = StringUtil.null2String(request
				.getParameter("expireDate"));
		String status = StringUtil.null2String(request.getParameter("status"));
		String couponNumber = request.getParameter("couponNumber");
		String value = request.getParameter("value");
		String minOrderAmount = request.getParameter("minOrderAmount");
		String scene = request.getParameter("scene");
		String orgCode = request.getParameter("orgCode");

		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("effectDate", effectDate);
		paraMap.put("expireDate", expireDate);
		paraMap.put("status", status);
		paraMap.put("couponNumber", couponNumber);
		paraMap.put("value", value);
		paraMap.put("minOrderAmount", minOrderAmount);
		paraMap.put("scene", scene);
		paraMap.put("orgCode", orgCode);

		List<Map> list = gatewayClientService.couponList(paraMap);
		return new ModelAndView(recordList).addObject("list", list);
	}

}
