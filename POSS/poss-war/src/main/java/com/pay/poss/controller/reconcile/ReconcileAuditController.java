package com.pay.poss.controller.reconcile;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.pay.fundout.reconcile.dto.rcmanualreconciliation.ReconciliationDTO;
import com.pay.fundout.reconcile.service.rcmanualreconciliation.RcAuditService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.DateUtil;

/**
 * 手工调账审核
 * @Description 
 * @project 	fo-reconcile-web
 * @file 		FoReconcileAuditController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 pay Corporation. All rights reserved.
 * Date				Author			Changes
 * 2010-10-3		Volcano.Wu			Create
 */
public class ReconcileAuditController extends ReconcileBaseController{
	
	private RcAuditService rcAuditService;

	public void setRcAuditService(RcAuditService rcAuditService) {
		this.rcAuditService = rcAuditService;
	}

	/**
	 * 手工调账审核init
	 * @param request
	 * @param response
	 * @return init页面
	 */
	public ModelAndView queryauditInit(HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> model = new HashMap<String,Object>();
		loadInfo(model);
		return new ModelAndView(URL("queryauditinit"),model);
	}
	
	/**
	 * 手工调账审核list
	 * @param request
	 * @param response
	 * @return list页面
	 */
	public ModelAndView queryauditlist(HttpServletRequest request,HttpServletResponse response){
		
		Map<String,Page<ReconciliationDTO>> model = new HashMap<String,Page<ReconciliationDTO>>();
		
		String STARTTIME = request.getParameter("STARTTIME");//起始日期
		String ENDTIME = request.getParameter("ENDTIME");//结束日期
		String WITHDRAW_BANK_ID = request.getParameter("WITHDRAW_BANK_ID");//出款银行
		String BANK_TRADE_SEQ = request.getParameter("BANK_TRADE_SEQ");//银行订单号
		String WITHDRAW_BUSI_TYPE = request.getParameter("WITHDRAW_BUSI_TYPE");//出款业务
		String BUSI_FLAG = request.getParameter("BUSI_FLAG");//对账状态
		
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
		
		Page<ReconciliationDTO> page = PageUtils.getPage(request); //分页
		page = rcAuditService.queryRcResult(page, params);
		model.put("page", page);
		return new ModelAndView(URL("queryauditlist"),model)
					.addObject("bankList", bankList)
					.addObject("busiFlagList", busiFlagList)
					.addObject("reviseStatusList", reviseStatusList)
					.addObject("withdrawBusiTypeList", withdrawBusiTypeList);
	}
	
	/**
	 * 审核确认
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	public ModelAndView audit(HttpServletRequest request,HttpServletResponse response) {
		Map<String,Object> model = new HashMap<String,Object>();
		String RESULT_ID = request.getParameter("id");
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("RESULT_ID", RESULT_ID);
		ReconciliationDTO rc = rcAuditService.queryReconciliationDTO(params);
		model.put("rc", rc);
		loadInfo(model);
		return new ModelAndView(URL("audit"),model);
	}
	
	/**
	 * 审核通过
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	public ModelAndView passSubmit(HttpServletRequest request,HttpServletResponse response)  {
		String APPLY_ID = request.getParameter("APPLY_ID");//申请ID
		String RESULT_ID = request.getParameter("RESULT_ID");//对账结果表ID
		String reason = request.getParameter("reason"); //申请理由
		String currentUserName = SessionUserHolderUtil.getLoginId(); //申请人
		String TRADE_SEQ = request.getParameter("TRADE_SEQ");//交易流水号
		
		Map<String,Object> updateParams = new HashMap<String, Object>();
		updateParams.put("REVISE_STATUS", 2);  //出款对账结果表－调账状态
		updateParams.put("PROCESS_STATUS", 2); //出款对账调账受理表-处理状态
		updateParams.put("LOG_PROCESS_STATUS", 2); //出款对账调账日志表－处理状态
		updateParams.put("RESULT_ID", RESULT_ID); //对账结果表ID
		updateParams.put("reason", reason); //申请理由
		updateParams.put("APPLY_USER", currentUserName);//申请人
		updateParams.put("TRADE_SEQ", TRADE_SEQ);//交易流水号
		updateParams.put("APPLY_ID", APPLY_ID); //申请ID
		
		try {
			Boolean bl = rcAuditService.processPassRdTx(updateParams);
		} catch (PossException e) {
			log.error(e.getMessage(),e);
		}
		return queryauditInit(request,response);
	}
	
	/**
	 * 审核驳回
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	public ModelAndView rejectSubmit(HttpServletRequest request,HttpServletResponse response) {
		String APPLY_ID = request.getParameter("APPLY_ID");//申请ID
		String RESULT_ID = request.getParameter("RESULT_ID");//对账结果表ID
		String reason = request.getParameter("reason"); //申请理由
		String currentUserName = SessionUserHolderUtil.getLoginId(); //申请人
		String TRADE_SEQ = request.getParameter("TRADE_SEQ");//交易流水号
		
		Map<String,Object> updateParams = new HashMap<String, Object>();
		updateParams.put("REVISE_STATUS", 3);  //出款对账结果表－调账状态
		updateParams.put("PROCESS_STATUS", 3); //出款对账调账受理表-处理状态
		updateParams.put("LOG_PROCESS_STATUS", 3); //出款对账调账日志表－处理状态
		updateParams.put("RESULT_ID", RESULT_ID); //对账结果表ID
		updateParams.put("reason", reason); //申请理由
		updateParams.put("APPLY_USER", currentUserName);//申请人
		updateParams.put("TRADE_SEQ", TRADE_SEQ);//交易流水号
		updateParams.put("APPLY_ID", APPLY_ID); //申请ID
		try {
			Boolean bl = rcAuditService.processPassRdTx(updateParams);
		}catch (PossException e) {
			log.error(e.getMessage(),e);
		}
		return queryauditInit(request,response);
	}
	
	/**
	 * 批量审核通过
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	public ModelAndView batchPassSubmit(HttpServletRequest request,HttpServletResponse response) {
		String ids = request.getParameter("ids");
		String reason = request.getParameter("reason"); //申请理由
		String currentUserName = SessionUserHolderUtil.getLoginId(); //申请人
		String TRADE_SEQ = request.getParameter("TRADE_SEQ");//交易流水号
		
		Map<String,Object> updateParams = new HashMap<String, Object>();
		updateParams.put("REVISE_STATUS", 2);  //出款对账结果表－调账状态 
		updateParams.put("PROCESS_STATUS", 2); //出款对账调账受理表-处理状态
		updateParams.put("LOG_PROCESS_STATUS", 2); //出款对账调账日志表－处理状态
		updateParams.put("reason", reason); //申请理由
		updateParams.put("APPLY_USER", currentUserName);//申请人
		updateParams.put("TRADE_SEQ", TRADE_SEQ);//交易流水号
		
		StringBuffer results = new StringBuffer();
		try{
			rcAuditService.processBatchPassRdTx(ids,updateParams);
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
	
	/**
	 * 批量审核驳回
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	public ModelAndView batchRejectSubmit(HttpServletRequest request,HttpServletResponse response) {
		String ids = request.getParameter("ids");
		String reason = request.getParameter("reason"); //申请理由
		String currentUserName = SessionUserHolderUtil.getLoginId(); //申请人
		String TRADE_SEQ = request.getParameter("TRADE_SEQ");//交易流水号
		
		Map<String,Object> updateParams = new HashMap<String, Object>();
		updateParams.put("REVISE_STATUS", 3);  //出款对账结果表－调账状态
		updateParams.put("PROCESS_STATUS", 3); //出款对账调账受理表-处理状态
		updateParams.put("LOG_PROCESS_STATUS", 3); //出款对账调账日志表－处理状态
		updateParams.put("reason", reason); //申请理由
		updateParams.put("APPLY_USER", currentUserName);//申请人
		updateParams.put("TRADE_SEQ", TRADE_SEQ);//交易流水号
		
		
		StringBuffer results = new StringBuffer();
		try{
			rcAuditService.processBatchRejectRdTx(ids,updateParams);
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
