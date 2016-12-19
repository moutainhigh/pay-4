package com.pay.poss.controller.reconcile;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.pay.fundout.reconcile.dto.rcmanualreconciliation.ReconciliationDTO;
import com.pay.fundout.reconcile.service.rcmanualreconciliation.RcResultService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.DateUtil;

/**
 * 手工调账申请
 * 
 * @Description
 * @project fo-reconcile-web
 * @file FoReconcileApplyController.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved. Date
 *          Author Changes 2010-10-2 Volcano.Wu Create
 */
public class ReconcileApplyController extends ReconcileBaseController {
	
	private RcResultService rcResultService;


	public void setRcResultService(RcResultService rcResultService) {
		this.rcResultService = rcResultService;
	}
	
	
	/**
	 * 手工调账init
	 * @param request
	 * @param response
	 * @return init页面
	 */
	public ModelAndView queryapplyInit(HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> model = new HashMap<String,Object>();
		loadInfo(model);
		return new ModelAndView(URL("queryapplyinit"),model);
	}
	
	/**
	 * 手工调账list
	 * @param request
	 * @param response
	 * @return list页面
	 */
	public ModelAndView queryapplyList(HttpServletRequest request,HttpServletResponse response) {
		
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
		page = rcResultService.queryRcResult(page, params);
		model.put("page", page);
		return new ModelAndView(URL("queryapplylist"),model)
					.addObject("busiFlagList", busiFlagList)
					.addObject("reviseStatusList", reviseStatusList)
					.addObject("withdrawBusiTypeList", withdrawBusiTypeList)
					.addObject("bankList", bankList);
	}
	
	/**
	 * 申请调账
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	public ModelAndView manualReconciliationApply(HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> model = new HashMap<String,Object>();
		String RESULT_ID = request.getParameter("id");
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("RESULT_ID", RESULT_ID);
		ReconciliationDTO rc = rcResultService.queryReconciliationDTO(params);
		model.put("rc", rc);
		loadInfo(model);
		return new ModelAndView(URL("apply"),model);
	}
	
	/**
	 * 查看
	 * @param model
	 * @param params
	 * @param request
	 * @param response
	 */
	private void viewDetail(Map<String,Object> model,Map<String,Object> params,HttpServletRequest request,HttpServletResponse response){
		String RESULT_ID = request.getParameter("id");
		params.put("RESULT_ID", RESULT_ID);
		ReconciliationDTO rc = rcResultService.queryReconciliationDTO(params);
		model.put("rc", rc);
		loadInfo(model);
	}
	
	
	/**
	 * 查看
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	public ModelAndView applyDetail(HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> params = new HashMap<String, Object>();
		viewDetail(model,params,request,response);
		return new ModelAndView(URL("applydetail"),model);
	}
	
	/**
	 * 申请提交
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	public ModelAndView applySubmit(HttpServletRequest request,HttpServletResponse response) {
		String RESULT_ID = request.getParameter("RESULT_ID");//对账结果表ID
		String reason = request.getParameter("reason"); //申请理由
		String currentUserName = SessionUserHolderUtil.getLoginId(); //申请人
		String TRADE_SEQ = request.getParameter("TRADE_SEQ");//交易流水号
		
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("RESULT_ID", RESULT_ID); //对账结果表ID
		params.put("reason", reason); //申请理由
		params.put("APPLY_USER", currentUserName);//申请人
		params.put("TRADE_SEQ", TRADE_SEQ);//交易流水号
		params.put("REVISE_STATUS", 1);//更新状态
		
		rcResultService.insertRcApplyMain(params);
		return queryapplyInit(request,response);
	}
	
	/**
	 * 批量申请提交
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	public ModelAndView batchApplySubmit(HttpServletRequest request,HttpServletResponse response) {
		
		StringBuffer results = new StringBuffer();
		String ids = request.getParameter("ids"); //申请ID
		String reason = request.getParameter("reason"); //申请理由
		String currentUserName = SessionUserHolderUtil.getLoginId(); //申请人
		
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("reason", reason); //申请理由
		params.put("APPLY_USER", currentUserName);//申请人
		params.put("ids", ids);//批量申请id
		
		try{
			rcResultService.insertBatch(params);
			results.append("处理成功!");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.write(results.toString());
			out.flush();
			out.close();
		}catch(Exception e){
			results.append("处理失败!");
			log.error(e.getMessage(),e);
		}
		return null;
	}
}
