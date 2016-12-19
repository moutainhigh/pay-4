 /** @Description 
 * @project 	fo-reconcile-web
 * @file 		ReconcileDownloadExcelController.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-9		Henry.Zeng			Create 
*/
package com.pay.poss.controller.reconcile;

import static com.pay.fundout.reconcile.common.util.ReconcileUtils.string2Timestamp;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.ModelAndView;

import com.pay.fundout.reconcile.dto.rcresult.ReconcileResultDTO;
import com.pay.fundout.reconcile.dto.rcresult.ReconcileResultSummaryDTO;
import com.pay.fundout.reconcile.service.rcmanualreconciliation.RcAuditService;
import com.pay.fundout.reconcile.service.rcmanualreconciliation.RcLogService;
import com.pay.fundout.reconcile.service.rcmanualreconciliation.RcResultService;
import com.pay.fundout.reconcile.service.rcresult.QueryReconcileResultService;
import com.pay.poss.base.common.poi.OperatorPoiInterface;

/**
 * <p>对账下载Excel文件处理Controller</p>
 * @author Henry.Zeng
 * @since 2010-10-9
 * @see 
 */
public class ReconcileDownloadExcelController extends ReconcileBaseController {
	
	/**
	 * 查询对账结果
	 */
	private transient QueryReconcileResultService reconcileQueryService;
	
	public void setReconcileQueryService(
			final QueryReconcileResultService reconcileQueryService) {
		this.reconcileQueryService = reconcileQueryService;
	}
	/**
	 * 调账审核Service
	 */
	private transient RcAuditService rcAuditService;
	
	public void setRcAuditService(final RcAuditService rcAuditService) {
		this.rcAuditService = rcAuditService;
	}
	
	/**
	 * 调账日志列表Service
	 */
	private transient RcLogService rcLogService;
	
	public void setRcLogService(final RcLogService rcLogService) {
		this.rcLogService = rcLogService;
	}
	
	/**
	 * 调账申请Service
	 */
	private transient RcResultService rcResultService;
	
	public void setRcResultService(final RcResultService rcResultService) {
		this.rcResultService = rcResultService;
	}
	
	
	/**
	 * 对账结果列表下载Excel
	 */
	private transient OperatorPoiInterface operatorResultList2Excel;
	
	public void setOperatorResultList2Excel(
			final OperatorPoiInterface operatorResultList2Excel) {
		this.operatorResultList2Excel = operatorResultList2Excel;
	}
	
	/**
	 * 对账结果明细下载Excel
	 */
	private transient OperatorPoiInterface operatorResultDetail2Excel;

	public void setOperatorResultDetail2Excel(
			final OperatorPoiInterface operatorResultDetail2Excel) {
		this.operatorResultDetail2Excel = operatorResultDetail2Excel;
	}
	/**
	 * 对账申请列表下载Excel
	 */
	private transient OperatorPoiInterface operatorReviseApply2Excel;
	
	public void setOperatorReviseApply2Excel(
			final OperatorPoiInterface operatorReviseApply2Excel) {
		this.operatorReviseApply2Excel = operatorReviseApply2Excel;
	}
	
	/**
	 * 对账审核列表下载Excel
	 */
	private transient OperatorPoiInterface operatorReviseAudit2Excel;
	
	public void setOperatorReviseAudit2Excel(
			final OperatorPoiInterface operatorReviseAudit2Excel) {
		this.operatorReviseAudit2Excel = operatorReviseAudit2Excel;
	}
	
	/**
	 * 对账日志列表下载Excel
	 */
	private transient OperatorPoiInterface operatorReviseLogList2Excel;
	
	public void setOperatorReviseLogList2Excel(
			final OperatorPoiInterface operatorReviseLogList2Excel) {
		this.operatorReviseLogList2Excel = operatorReviseLogList2Excel;
	}
	
	private void bindResultListMap(Map<String,Object> param,HttpServletRequest request){
		param.put("withDrawBankId", request.getParameter("withDrawBankId"));
		param.put("startDate",string2Timestamp(request.getParameter("startDate")));
		param.put("endDate", string2Timestamp(request.getParameter("endDate")));
	}
	
	/**
	 * 下载对账结果列表Excel文件
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView downloadResultList2Excel(HttpServletRequest request , HttpServletResponse response) throws Exception{
		
		Map<String,Object> map = new HashMap<String, Object>();
		
		bindResultListMap(map, request);
		
		List<ReconcileResultSummaryDTO>	resultSummaryDTOs = reconcileQueryService.queryReconcileList2Excel(map);
		HSSFWorkbook workbook = new HSSFWorkbook();
		if(resultSummaryDTOs != null){
			workbook =  operatorResultList2Excel.buildExcel(resultSummaryDTOs);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(new Date());
		response.setHeader("Content-Disposition",   "attachment;filename="   
		             +   new   String(("reconcileResultListFile"+dateStr+".xls").getBytes(),   "ISO8859_1"));  
		OutputStream out = response.getOutputStream();
		workbook.write(out);
        out.flush();
        out.close();
        response.setStatus(HttpServletResponse.SC_OK);
		return null;
	}
	
	private void bindResultDetailMap(Map<String,Object> param,HttpServletRequest request){
		param.put("busiFlag", Long.valueOf(request.getParameter("busiFlag")));
		param.put("countFlag", request.getParameter("countFlag"));
		param.put("withdrawBankId", request.getParameter("withdrawBankId"));
		param.put("startDate",string2Timestamp(request.getParameter("startDate")));
		param.put("endDate", string2Timestamp(request.getParameter("endDate")));
	}
	
	/**
	 * 下载对账结果明细Excel文件
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView downloadResultDetail2Excel(HttpServletRequest request , HttpServletResponse response) throws Exception{
		
		Map<String,Object> param = new HashMap<String, Object>();
		
		bindResultDetailMap(param, request);
		
		List<ReconcileResultDTO> reconcileResultDTOs = reconcileQueryService.queryReconcileDetail2Excel(param);
		
		HSSFWorkbook workbook = operatorResultDetail2Excel.buildExcel(reconcileResultDTOs);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(new Date());
		response.setHeader("Content-Disposition",   "attachment;filename="   
		             +   new   String(("reconcileResultDetailFile"+dateStr+".xls").getBytes(),"ISO8859_1"));  
		OutputStream out = response.getOutputStream();
		workbook.write(out);
        out.flush();
        out.close();
        response.setStatus(HttpServletResponse.SC_OK);
		
		return null;
	}
	
	/**
	 * 下载调账申请Excel文件
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView downloadReviseApply2Excel(HttpServletRequest request , HttpServletResponse response){

		
		return null;
	}
	
	/**
	 * 下载调账申请Excel文件
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView downloadReviseAudit2Excel(HttpServletRequest request , HttpServletResponse response){
		
		Map<String,Object> params = new HashMap<String, Object>();
		
		rcAuditService.queryRcResult2Excel(params);
		
		
		return null;
	}
	/**
	 * 下载调账日志列表Excel文件
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView downloadReviseLogList2Excel(HttpServletRequest request , HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView();
		return modelAndView;
	}
	
	
}
