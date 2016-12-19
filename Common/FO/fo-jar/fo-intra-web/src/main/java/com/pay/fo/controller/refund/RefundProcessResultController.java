package com.pay.fo.controller.refund;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.refund.common.constant.RefundConstants;
import com.pay.poss.refund.model.RefundProcessResultDTO;
import com.pay.poss.refund.service.RefundHandworkService;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.StringUtil;

/**
 * 手工处理充退结果
 * @Description 
 * @project 	poss-refund
 * @file 		RefundProcessResultController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 pay Corporation. All rights reserved.
 * Date				Author			Changes
 * 2010-9-21		Volcano.Wu			Create
 */
public class RefundProcessResultController extends RefundAbstractController{
	
	private RefundHandworkService refundHandworkService;

	/**
	 * 进入查询充退批次结果Init
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	public ModelAndView processresultInit(HttpServletRequest request,HttpServletResponse response) throws Exception {
		return new ModelAndView(urlMap.get("processresultInit")).addObject("bankList", this.getBankListInf());
	}
	
	/**
	 * 进入复核手工处理充退数据
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	public ModelAndView checkprocessresultInit(HttpServletRequest request,HttpServletResponse response) throws Exception {
		return new ModelAndView(urlMap.get("checkprocessresultInit")).addObject("bankList", this.getBankListInf());
	}
	
	
	/**
	 * 查询充退批次结果List
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView processresultList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		Map<String,Object> searchMap = new HashMap<String, Object>();
		String BATCH_NUM = request.getParameter("BATCH_NUM");//批次号
		String BATCH_NAME = request.getParameter("BATCH_NAME"); //批次名称
		String ORDER_KY = request.getParameter("ORDER_KY");//交易号
		String RECHARGE_ORDER_SEQ = request.getParameter("RECHARGE_ORDER_SEQ");//充值流水号
		String RECHARGE_BANK = request.getParameter("RECHARGE_BANK");//银行名称
		
		searchMap.put("ORDER_STATUS",RefundConstants.REFUND_HANDLE_STATUS_101);
		searchMap.put("BATCH_NUM", BATCH_NUM);
		searchMap.put("BATCH_NAME", BATCH_NAME);
		searchMap.put("ORDER_KY",ORDER_KY);
		searchMap.put("RECHARGE_ORDER_SEQ", RECHARGE_ORDER_SEQ);
		searchMap.put("RECHARGE_BANK", RECHARGE_BANK);
		searchMap.put("STATUS",RefundConstants.FILE_STATUS_DOWNLOADED);//已下载
		Page<RefundProcessResultDTO> page = PageUtils.getPage(request); //分页
		page = refundHandworkService.processresultList(page, searchMap);
		Map<String,Object> model = refundHandworkService.queryProcessResultSumInfo(searchMap);
		model.put("page", page);
		model.put("bankList", this.getBankListInf());
		return new ModelAndView(urlMap.get("processresultList"),model);
	}
	
	/**
	 * 人工审核成功
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	public ModelAndView processresultSuccess(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String ids = request.getParameter("ids");
		StringBuffer results = new StringBuffer("");
		refundHandworkService.processresultSuccess(ids);
		/*if()
			results.append("处理成功!");
		else
			results.append("处理失败!");*/
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.write(results.toString());
		out.flush();
		out.close();
		return null;
	}
	
	/**
	 * 人工审核失败
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	public ModelAndView processresultFailure(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String ids = request.getParameter("ids");
		StringBuffer results = new StringBuffer();
		refundHandworkService.processresultFailure(ids);
		/*if()
			results.append("处理成功!");
		else
			results.append("处理失败!");
		*/
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.write(results.toString());
		out.flush();
		out.close();
		return null;
	}
	
	
	/**
	 * 复核手工处理充退数据List
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	public ModelAndView checkprocessresultList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		Map<String,Object> model = new HashMap<String,Object>();
		Map<String,Object> searchMap = new HashMap<String, Object>();
		String BATCH_NUM = request.getParameter("BATCH_NUM");//批次号
		String BATCH_NAME = request.getParameter("BATCH_NAME"); //批次名称
		String RECHARGE_BANK = request.getParameter("RECHARGE_BANK");//银行名称
		String ORDER_STATUS = request.getParameter("ORDER_STATUS");//状态
		
		searchMap.put("BATCH_NUM", BATCH_NUM);
		searchMap.put("BATCH_NAME", BATCH_NAME);
		searchMap.put("RECHARGE_BANK",RECHARGE_BANK);
		searchMap.put("STATUS",RefundConstants.FILE_STATUS_DOWNLOADED);
		if(StringUtil.isEmpty(ORDER_STATUS)){
			searchMap.put("ORDER_STATUS",RefundConstants.REFUND_HANDLE_STATUS_101 + "," + 
											RefundConstants.REFUND_HANDLE_STATUS_102 + "," + 
											RefundConstants.REFUND_HANDLE_STATUS_103);
		}else{
			searchMap.put("ORDER_STATUS",ORDER_STATUS);
		}
		
		Page<RefundProcessResultDTO> page = PageUtils.getPage(request); //分页
		page = refundHandworkService.checkprocessresultList(page, searchMap);
		model.put("page", page);
		model.put("bankList", this.getBankListInf());
		return new ModelAndView(urlMap.get("checkprocessresultList"),model);
	}
	
	
	/**
	 * 充退处理确认
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	public ModelAndView processresultConfirm(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String viewName = urlMap.get("assignTaskSuccess");
		String ids = request.getParameter("ids");
		String userId = SessionUserHolderUtil.getLoginId();
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("ids",ids);
		params.put("userId",userId);
		
		params = refundHandworkService.confirmRefundInfo(params);
		params.put("requestUrl","refund.processresult.do?method=checkprocessresultInit");
		
		return new ModelAndView(viewName,params);
	}
	
	/**
	 * 充退处理退回
	 * @param request
	 * @param response
	 * @return ModelAndView
	 * @throws Exception
	 */
	public ModelAndView processresultBackspacing(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String viewName = urlMap.get("assignTaskSuccess");
		String ids = request.getParameter("ids");
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("ids",ids);
		params = refundHandworkService.backRefundInfo(params);
		
		params.put("requestUrl","refund.processresult.do?method=checkprocessresultInit");
		
		return new ModelAndView(viewName,params);
	}
	
	public void setRefundHandworkService(RefundHandworkService refundHandworkService) {
		this.refundHandworkService = refundHandworkService;
	}
	
	
}
