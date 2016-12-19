 /** @Description 
 * @project 	poss-reconcile
 * @file 		RefundManageController.java 
 * Copyright (c) 2006-2020 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-8-25		Sunsea_Li		Create 
*/
package com.pay.fo.controller.refund;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.ModelAndView;

import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.base.common.poi.OperatorPoiInterface;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.PossUntxException;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.poss.refund.common.constant.RefundConstants;
import com.pay.poss.refund.common.util.MyControllerUtil;
import com.pay.poss.refund.model.RechargeRecordDto;
import com.pay.poss.refund.model.RefundDetailInfoDTO;
import com.pay.poss.refund.model.RefundOrderM;
import com.pay.poss.refund.model.RefundWorkOrderAndM;
import com.pay.poss.refund.model.WebQueryRefundDTO;
import com.pay.poss.refund.service.RefundManageService;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.DateUtil;
import com.pay.util.StringUtil;
/**
 * <p>充退管理相关操作Controller</p>
 * @author sunsea_li
 * @since 2010-8-25
 * @see 
 */
public class RefundManageController extends RefundAbstractController {
	
	private  transient RefundManageService refundManageService ;
	private OperatorPoiInterface operatorPoiInterface;

	public void setOperatorPoiInterface(OperatorPoiInterface operatorPoiInterface) {
		this.operatorPoiInterface = operatorPoiInterface;
	}

	public void setRefundManageService(RefundManageService refundManageService) {
		this.refundManageService = refundManageService;
	} 
	
	/**
	 * 进入充退申请页面
	 */
	public ModelAndView entryRefundApplyPage(HttpServletRequest request,
			HttpServletResponse response) {
		String viewName = urlMap.get("applyInit");
		return new ModelAndView(viewName, RefundConstants.entryRefundPage())
				.addObject("bankList", getBankList());
	}
	
	/**
	 * 查询充退申请分页列表
	 */
	public ModelAndView queryReChargeList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			PossUntxException {
		
		String viewName = urlMap.get("applyList");
		final WebQueryRefundDTO webQueryRefundDTO = new WebQueryRefundDTO();
		bind(request, webQueryRefundDTO, "webQueryRefundDTO", "yyyy-MM-dd");
		Page<RechargeRecordDto> resultPage = PageUtils.getPage(request);

		Map<String, Object> model = new HashMap<String, Object>();
		model = refundManageService.queryRechargeInfo(resultPage,
				webQueryRefundDTO);
		return new ModelAndView(viewName, model);
	}
	
	/**
	 * 处理充退申请动作
	 * @throws ServletException 
	 */
	public ModelAndView handerRefundApply(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		String viewName = urlMap.get("applyInit");
		final WebQueryRefundDTO webQueryRefundDTO = new WebQueryRefundDTO();
		bind(request, webQueryRefundDTO, "webQueryRefundDTO", "yyyy-MM-dd");
		RefundOrderM mDto = MyControllerUtil.wrapModel(request);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("webQueryRefundDTO", webQueryRefundDTO);// 原查询参数返回
		try {
			webQueryRefundDTO.setOperator(SessionUserHolderUtil.getLoginId());
			model = refundManageService.handerRefundApplyRdTx(mDto,
					webQueryRefundDTO);
		} catch (PossException e) {
			LogUtil.error(RefundManageController.class, "充退申请", OPSTATUS.FAIL,
					"", "", e.getMessage(), "", "");
			model.put("err", "申请失败：充退异常！");
		}
		model.put("bankList", getBankList());
		return new ModelAndView(viewName, model);
	}
	
	/**
	 * 进入重复支付充退申请页面
	 */
	public ModelAndView entryRepeatApplyPage(HttpServletRequest request,
			HttpServletResponse response) {
		String viewName = urlMap.get("repeatInit");
		return new ModelAndView(viewName, RefundConstants.entryRefundPage())
				.addObject("bankList", getBankList());
	}
	
	/**
	 * 查询重复支付充退申请分页列表
	 */
	public ModelAndView queryRepeatList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			PossUntxException {
		
		String viewName = urlMap.get("repeatList");
		final WebQueryRefundDTO webQueryRefundDTO = new WebQueryRefundDTO();
		bind(request, webQueryRefundDTO, "webQueryRefundDTO", "yyyy-MM-dd");
		webQueryRefundDTO.setQueryType("2");
		Page<RechargeRecordDto> resultPage = PageUtils.getPage(request);

		Map<String, Object> model = new HashMap<String, Object>();
		model = refundManageService.queryRechargeInfo(resultPage,
				webQueryRefundDTO);
		return new ModelAndView(viewName, model);
	}
	
	/**
	 * 处理重复支付充退申请动作
	 * @throws ServletException 
	 */
	public ModelAndView handerRepeatApply(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		String viewName = urlMap.get("repeatInit");
		final WebQueryRefundDTO webQueryRefundDTO = new WebQueryRefundDTO();
		bind(request, webQueryRefundDTO, "webQueryRefundDTO", "yyyy-MM-dd");
		RefundOrderM mDto = MyControllerUtil.wrapModel(request);
		mDto.setApplyFrom("REPEAT");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("webQueryRefundDTO", webQueryRefundDTO);// 原查询参数返回
		try {
			webQueryRefundDTO.setOperator(SessionUserHolderUtil.getLoginId());
			model = refundManageService.handerRefundApplyRdTx(mDto,
					webQueryRefundDTO);
		} catch (PossException e) {
			LogUtil.error(RefundManageController.class, "充退申请", OPSTATUS.FAIL,
					"", "", e.getMessage(), "", "");
			model.put("err", "申请失败：充退异常！");
		}
		model.put("bankList", getBankList());
		return new ModelAndView(viewName, model);
	}
	
	/**
	 * 进入充退审核页面
	 */
	public ModelAndView entryRefundAuditPage(HttpServletRequest request , HttpServletResponse response ) {
		String viewName = urlMap.get("auditInit");
		WebQueryRefundDTO temp = (WebQueryRefundDTO)RefundConstants.entryRefundPage().get("webQueryRefundDTO");
		Map model = new HashMap();
		model.put("webQueryRefundDTO",temp);
		model.put("bankList",getBankList());
		AcctTypeEnum[] acctTypes = AcctTypeEnum.getBasicAcctTypes();
		model.put("acctTypes", acctTypes);
		return new ModelAndView(viewName,model);
	}
	/**
	 * 查询充退审核分页列表
	 */
	public ModelAndView queryRefundList(HttpServletRequest request , HttpServletResponse response ) throws ServletException, PossUntxException {
		String viewName = urlMap.get("auditList");
		final WebQueryRefundDTO webQueryRefundDTO = new WebQueryRefundDTO() ;
		bind(request, webQueryRefundDTO, "webQueryRefundDTO", "yyyy-MM-dd");
		Page<RefundWorkOrderAndM> resultPage = PageUtils.getPage(request);
		Map<String,Object> model = new HashMap<String, Object>();
		webQueryRefundDTO.setStatus(Arrays.asList(RefundConstants.REFUND_STATUS_0,RefundConstants.REFUND_STATUS_3));
		model = refundManageService.queryRefundInfo(resultPage,webQueryRefundDTO);
		List<RefundWorkOrderAndM> result = resultPage.getResult();
		for (RefundWorkOrderAndM refundWorkOrderAndM : result) {
			String refundOrderNo = refundWorkOrderAndM.getRefundOrderM().getRefundOrderNo();
			RefundOrderM refundOrderM2 = queryTradeOrderNo(refundOrderNo);
			RefundOrderM refundOrderM = refundWorkOrderAndM.getRefundOrderM();
			refundOrderM.setTradeOrderNo(refundOrderM2.getTradeOrderNo());
			refundOrderM.setCurrencyCode(refundOrderM2.getCurrencyCode());
			refundWorkOrderAndM.setRefundOrderM(refundOrderM);
		}
		AcctTypeEnum[] acctTypes = AcctTypeEnum.getBasicAcctTypes();
		model.put("acctTypes", acctTypes);
		return new ModelAndView(viewName,model);
	}
	
	
	/****
	 * 通过退款订单号查旬网关订单号
	 */
	
	public RefundOrderM queryTradeOrderNo(String refundOrderNo){
		if(StringUtils.isNotBlank(refundOrderNo)){
			RefundOrderM refundOrderM = refundManageService.queryTradeOrderNo(refundOrderNo);
			return refundOrderM;
		}
		return null;
	}
	
	/**
	 * 进入充退审核详细页面
	 * @throws ServletException 
	 */
	public ModelAndView initAuditDetail(HttpServletRequest request , HttpServletResponse response ) throws ServletException {
		String viewName = urlMap.get("auditDetail");
		final WebQueryRefundDTO webQueryRefundDTO = new WebQueryRefundDTO() ;
		bind(request, webQueryRefundDTO, "webQueryRefundDTO", "yyyy-MM-dd"); 
		Map<String,Object> model = new HashMap<String, Object>();
		model = refundManageService.queryRefundInfoDetail(webQueryRefundDTO);
		model.put("bankList", this.getBankListInf());
		return new ModelAndView(viewName,model);
	}
	
	/**
	 * 处理单笔充退审核动作
	 * @throws ServletException 
	 */
	public ModelAndView handerRefundAuditSingle(HttpServletRequest request , HttpServletResponse response ) throws ServletException {
		String viewName = urlMap.get("assignTaskSuccess");
		String refundStatus = request.getParameter("refundStatus");
		String workOrderKy = request.getParameter("workOrderKy");
		String refundRemark = request.getParameter("refundRemark");
		String oldStatus = request.getParameter("tempStatus");
		String handleType = request.getParameter("handleType");
		String requestUrl = request.getParameter("requestUrl");
		String userId = SessionUserHolderUtil.getLoginId();
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("refundStatus",refundStatus);
		params.put("workOrderKy",workOrderKy);
		params.put("userId",userId);
		params.put("refundRemark",refundRemark);
		params.put("requestUrl",requestUrl);
		params.put("tempStatus",oldStatus);
		params.put("handleType",handleType);
		params = refundManageService.handerRefundAuditSingle(params);
		return new ModelAndView(viewName,params);
	}
	
	/**
	 * 处理批量充退审核动作
	 * @throws ServletException 
	 */
	public ModelAndView handerRefundAuditBatch(HttpServletRequest request , HttpServletResponse response ) throws ServletException {
		String viewName = urlMap.get("auditList");
		String auditRefundRemark = StringUtil.null2String(request.getParameter("auditRefundRemark"));
		List<Map> channelOrders = (List<Map>) request.getAttribute("channleOrders");
		final WebQueryRefundDTO webQueryRefundDTO = new WebQueryRefundDTO() ;
		bind(request, webQueryRefundDTO, "webQueryRefundDTO", "yyyy-MM-dd");
		String userId = SessionUserHolderUtil.getLoginId();
		webQueryRefundDTO.setUserId(userId);
		Page<RefundWorkOrderAndM> resultPage = PageUtils.getPage(request);
		webQueryRefundDTO.setOldStatus(Arrays.asList(RefundConstants.REFUND_STATUS_0,RefundConstants.REFUND_STATUS_3));
		Map<String,Object> model = new HashMap<String, Object>();
		webQueryRefundDTO.setStatus(Arrays.asList(RefundConstants.REFUND_STATUS_0,RefundConstants.REFUND_STATUS_3));
		webQueryRefundDTO.setAuditRefundRemark(auditRefundRemark);//操作备注
		model = refundManageService.handerRefundAuditBatch(resultPage,webQueryRefundDTO,channelOrders);
		return new ModelAndView(viewName,model);
	}
	
	/**
	 * 下载审核信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView handleDownloadAuditInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		final WebQueryRefundDTO webQueryRefundDTO = new WebQueryRefundDTO() ;
		bind(request, webQueryRefundDTO, "webQueryRefundDTO", "yyyy-MM-dd");
		//设置登陆用户
		webQueryRefundDTO.setUserId(SessionUserHolderUtil.getLoginId());
		//TODO 查询该用户审核信息
		webQueryRefundDTO.setStatus(Arrays.asList(RefundConstants.REFUND_STATUS_0,RefundConstants.REFUND_STATUS_3));
		List<RefundDetailInfoDTO> auditList = refundManageService.queryAuditInfo(webQueryRefundDTO);
		
		
		HSSFWorkbook workbook = operatorPoiInterface.buildExcel(auditList);
		String currentDate = DateUtil.getNowDate("yyyyMMdd");
		response.setContentType("application/octet-stream; charset=UTF-8");
		response.setHeader("Content-Disposition","attachment;filename="   
	             +   new   String(("AuditDataFile" + currentDate + System.currentTimeMillis() + ".xls").getBytes(),"ISO8859_1"));
		OutputStream out = response.getOutputStream();
		workbook.write(out);
        out.flush();
        out.close();
        response.setStatus(HttpServletResponse.SC_OK);
		return null;
	}
	
}
