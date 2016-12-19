package com.pay.poss.controller.reconcile;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.taskdefs.condition.Http;
import org.springframework.web.servlet.ModelAndView;

import com.pay.fundout.reconcile.dto.rcmanualreconciliation.ReconciliationDTO;
import com.pay.fundout.reconcile.service.rcmanualreconciliation.RcLogService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.util.DateUtil;

/**
 * 手工调账日志
 * @Description 
 * @project 	fo-reconcile-web
 * @file 		FoReconcileLogController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 pay Corporation. All rights reserved.
 * Date				Author			Changes
 * 2010-10-3		Volcano.Wu			Create
 */
public class ReconcileLogController extends ReconcileBaseController{

	private RcLogService rcLogService;
	
	public void setRcLogService(RcLogService rcLogService) {
		this.rcLogService = rcLogService;
	}

	/**
	 * 手工调账日志init
	 * @param request
	 * @param response
	 * @return init页面
	 */
	public ModelAndView querylogInit(HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> model = new HashMap<String,Object>();
		loadInfo(model);
		return new ModelAndView(URL("queryloginit"),model);
	}
	
	/**
	 * 手工调账日志list
	 * @param request
	 * @param response
	 * @return list页面
	 */
	public ModelAndView querylogList(HttpServletRequest request,HttpServletResponse response) {
		
		Map<String,Page<ReconciliationDTO>> model = new HashMap<String,Page<ReconciliationDTO>>();
		
		String STARTTIME = request.getParameter("STARTTIME");//起始日期
		String ENDTIME = request.getParameter("ENDTIME");//结束日期
		String WITHDRAW_BANK_ID = request.getParameter("WITHDRAW_BANK_ID");//出款银行
		String BANK_TRADE_SEQ = request.getParameter("BANK_TRADE_SEQ");//银行订单号
		String WITHDRAW_BUSI_TYPE = request.getParameter("WITHDRAW_BUSI_TYPE");//出款业务
		String BUSI_FLAG = request.getParameter("BUSI_FLAG");//对账状态
		String REVISE_STATUS = request.getParameter("REVISE_STATUS");//调账状态
		
		Map<String,Object> params = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(STARTTIME))
			params.put("STARTTIME", DateUtil.strToDate(STARTTIME,"yyyy-MM-dd"));
		if(StringUtils.isNotEmpty(ENDTIME))
			params.put("ENDTIME", DateUtil.strToDate(ENDTIME,"yyyy-MM-dd"));
		if(StringUtils.isNotEmpty(WITHDRAW_BANK_ID))
			params.put("WITHDRAW_BANK_ID", WITHDRAW_BANK_ID);
		if(StringUtils.isNotEmpty(BANK_TRADE_SEQ))
			params.put("BANK_TRADE_SEQ", BANK_TRADE_SEQ);
		if(StringUtils.isNotEmpty(WITHDRAW_BUSI_TYPE) && !WITHDRAW_BUSI_TYPE.endsWith("0"))
			params.put("WITHDRAW_BUSI_TYPE", WITHDRAW_BUSI_TYPE);
		if(StringUtils.isNotEmpty(BUSI_FLAG))
			params.put("BUSI_FLAG", BUSI_FLAG);
		if(StringUtils.isNotEmpty(REVISE_STATUS))
			params.put("REVISE_STATUS", REVISE_STATUS);
		
		Page<ReconciliationDTO> page = PageUtils.getPage(request); //分页
		page = rcLogService.queryRcLog(page, params);
		model.put("page", page);
		return new ModelAndView(URL("queryloglist"),model)
						  .addObject("bankList", bankList)
						  .addObject("busiFlagList", busiFlagList)
						  .addObject("reviseStatusList", reviseStatusList)
						  .addObject("withdrawBusiTypeList", withdrawBusiTypeList);
	}
	
	
	
	
	/**
	 * 日志详细
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	public ModelAndView log(HttpServletRequest request,HttpServletResponse response) {
		String RESULT_ID = request.getParameter("id");
		String APPLY_ID = request.getParameter("applyId");
		Map<String,Object> model = new HashMap<String, Object>();
		model.put("RESULT_ID", RESULT_ID);
		model.put("APPLY_ID", 	APPLY_ID);
		model = rcLogService.queryRcLogDetail(model);
		loadInfo(model);
		return new ModelAndView(URL("log"),model);
	}
}
