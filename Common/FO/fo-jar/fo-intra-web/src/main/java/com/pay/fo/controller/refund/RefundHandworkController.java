package com.pay.fo.controller.refund;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.refund.model.RefundHandworkBatchDTO;
import com.pay.poss.refund.service.RefundHandworkService;
import com.pay.util.DateUtil;


/**
 * 手工生成批次
 * @Description 
 * @project 	poss-refund
 * @file 		RefundHandworkController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 pay Corporation. All rights reserved.
 * Date				Author			Changes
 * 2010-9-19		Volcano.Wu			Create
 */
public class RefundHandworkController extends RefundAbstractController{
	
	private RefundHandworkService refundHandworkService;
	
	/**
	 * 进入查询充退数据Init页面
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response) throws Exception {
		return new ModelAndView(urlMap.get("handworkbatchInit")).addObject("bankList", this.getBankListInf());
	}
	
	/**
	 * 条件查询分页 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView search(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> searchMap = new HashMap<String, Object>();
		String RECHARGE_BANK = request.getParameter("RECHARGE_BANK");//银行编号
		String RECHARGE_ORDER_SEQ = request.getParameter("RECHARGE_ORDER_SEQ"); //充值流水
		String ORDER_KY = request.getParameter("ORDER_KY");//交易号
		String MEMBER_CODE = request.getParameter("MEMBER_CODE");//会员号
		String STARTTIME = request.getParameter("STARTTIME"); //超始时间
		String ENDTIME = request.getParameter("ENDTIME");//结束时间
		if(StringUtils.isNotEmpty(RECHARGE_BANK))
			searchMap.put("RECHARGE_BANK", RECHARGE_BANK);
		if(StringUtils.isNotEmpty(RECHARGE_ORDER_SEQ))
			searchMap.put("RECHARGE_ORDER_SEQ", RECHARGE_ORDER_SEQ);
		if(StringUtils.isNotEmpty(ORDER_KY))
			searchMap.put("ORDER_KY",ORDER_KY);
		if(StringUtils.isNotEmpty(STARTTIME))
			searchMap.put("STARTTIME", DateUtil.strToDate(STARTTIME,"yyyy-MM-dd"));
		if(StringUtils.isNotEmpty(ENDTIME))
			searchMap.put("ENDTIME", DateUtil.strToDate(ENDTIME,"yyyy-MM-dd"));
		if(StringUtils.isNotEmpty(MEMBER_CODE))
			searchMap.put("MEMBER_CODE", MEMBER_CODE);
		
		Page<RefundHandworkBatchDTO> page = PageUtils.getPage(request); //分页
		page = refundHandworkService.search(page, searchMap);
		model.put("page", page);
		model.put("bankList", this.getBankListInf());
		return new ModelAndView(urlMap.get("handworkbatchList"),model);
	}
	
	/**
	 * 生成批次
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView handwork(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String,Object> model = new HashMap<String,Object>();
		String[] workorderIds = request.getParameterValues("choose");
		List<String> workorders = new ArrayList<String>();
		for (String workorderId : workorderIds) {
			workorders.add(workorderId);
		}
		if(workorders != null && workorders.size() > 0){
			model = refundHandworkService.handworkBatch(workorders);
		}
		return new ModelAndView(urlMap.get("handworkbatchSuccess"),model);
	}

	public void setRefundHandworkService(RefundHandworkService refundHandworkService) {
		this.refundHandworkService = refundHandworkService;
	}
}
