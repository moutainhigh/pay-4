/**
 *  File: RefundLiquidateController.java
 *  Description:风控清结算管理
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-17    jason_wang      Changes
 *  
 *
 */
package com.pay.fo.controller.refund;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossUntxException;
import com.pay.poss.refund.common.constant.RefundConstants;
import com.pay.poss.refund.model.RefundWorkOrderAndM;
import com.pay.poss.refund.model.WebQueryRefundDTO;
import com.pay.poss.refund.service.RefundManageService;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.poss.service.fi.input.FiBankFacadeService;

/**
 * @author jason_wang
 *
 */
public class RefundLiquidateController extends RefundAbstractController {

	private  transient RefundManageService refundManageService ;
	private FiBankFacadeService bankInfoService;

	public void setRefundManageService(RefundManageService refundManageService) {
		this.refundManageService = refundManageService;
	}
	
	/**
	 * @param bankInfoService the bankInfoService to set
	 */
	public void setBankInfoService(FiBankFacadeService bankInfoService) {
		this.bankInfoService = bankInfoService;
	}
	
	/**
	 * 进入清结算页面
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	public ModelAndView entryRefundLiquidatePage(HttpServletRequest request , HttpServletResponse response ) {
		String viewName = urlMap.get("refundLiquidateInit");
		WebQueryRefundDTO temp = (WebQueryRefundDTO)RefundConstants.entryRefundPage().get("webQueryRefundDTO");
		Map<String,Object> model = new HashMap<String, Object>();
		model.put("webQueryRefundDTO",temp);
		model.put("bankList",this.bankInfoService.getFiBankList());
		return new ModelAndView(viewName,model);
	}
	
	/**
	 * 查询充退清结算分页列表
	 */
	public ModelAndView queryRefundLiquidateList(HttpServletRequest request , HttpServletResponse response ) throws ServletException, PossUntxException {
		String viewName = urlMap.get("refundLiquidateList");
		final WebQueryRefundDTO webQueryRefundDTO = new WebQueryRefundDTO() ;
		bind(request, webQueryRefundDTO, "webQueryRefundDTO", "yyyy-MM-dd");
		Page<RefundWorkOrderAndM> resultPage = PageUtils.getPage(request);
		//设置登陆用户
		webQueryRefundDTO.setUserId(SessionUserHolderUtil.getLoginId());
		Map<String,Object> model = new HashMap<String, Object>();
		model = refundManageService.queryRefundLiquidateInfo(resultPage,webQueryRefundDTO);
		return new ModelAndView(viewName,model);
	}
	
	/**
	 * 进入充退清结算详细页面
	 * @throws ServletException 
	 */
	public ModelAndView initLiquidateDetail(HttpServletRequest request , HttpServletResponse response ) throws ServletException {
		String viewName = urlMap.get("refundLiquidateDetail");
		final WebQueryRefundDTO webQueryRefundDTO = new WebQueryRefundDTO() ;
		bind(request, webQueryRefundDTO, "webQueryRefundDTO", "yyyy-MM-dd");
		Map<String,Object> model = new HashMap<String, Object>();
		model = refundManageService.queryRefundLiquidateInfoDetail(webQueryRefundDTO);
		model.put("bankList",this.bankInfoService.getFiBankList());
		return new ModelAndView(viewName,model);
	}
	
	/**
	 * 处理批量充退清结算动作
	 * @throws ServletException 
	 */
	public ModelAndView handerRefundLiquidateBatch(HttpServletRequest request , HttpServletResponse response ) throws ServletException {
		String viewName = urlMap.get("refundLiquidateList");
		final WebQueryRefundDTO webQueryRefundDTO = new WebQueryRefundDTO() ;
		bind(request, webQueryRefundDTO, "webQueryRefundDTO", "yyyy-MM-dd");
		String userId = SessionUserHolderUtil.getLoginId();
		webQueryRefundDTO.setUserId(userId);
		Page<RefundWorkOrderAndM> resultPage = PageUtils.getPage(request);
		
		Map<String,Object> model = new HashMap<String, Object>();
		model = refundManageService.handeRefundLiquidateBatch(resultPage,webQueryRefundDTO);
		return new ModelAndView(viewName,model);
	}
	
	/**
	 * 处理单笔充退审核动作
	 * @throws ServletException 
	 */
	public ModelAndView handerRefundLiquidateSingle(HttpServletRequest request , HttpServletResponse response ) throws ServletException {
		String viewName = urlMap.get("assignTaskSuccess");
		String refundStatus = request.getParameter("refundStatus");
		String workOrderKy = request.getParameter("workOrderKy");
		String workflowKy = request.getParameter("workflowKy");
		String refundRemark = request.getParameter("refundRemark");
		String handleType = request.getParameter("handleType");
		String requestUrl = request.getParameter("requestUrl");
		String userId = SessionUserHolderUtil.getLoginId();
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("refundStatus",refundStatus);
		params.put("workOrderKy",workOrderKy);
		params.put("userId",userId);
		params.put("processInstanceId",workflowKy);
		params.put("refundRemark",refundRemark);
		params.put("handleType",handleType);
		params.put("requestUrl",requestUrl);
		
		params = refundManageService.handerRefundLiquidateSingle(params);
		return new ModelAndView(viewName,params);
	}
}
