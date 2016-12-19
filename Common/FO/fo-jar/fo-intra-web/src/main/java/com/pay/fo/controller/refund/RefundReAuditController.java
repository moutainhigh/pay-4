/**
 *  File: AuditManagerController.java
 *  Description:
 *  Copyright  2010 iPayLinks Corporation. All rights reserved.
 *  2010-9-14    jason_wang      Changes
 *  2016-07-29   nico.shao		 Changes 增加了复核这里的日志以及同步处理。同时只允许跑一个过程
 *
 */
package com.pay.fo.controller.refund;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.ModelAndView;

import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.base.common.poi.OperatorPoiInterface;
import com.pay.poss.base.exception.PossUntxException;
import com.pay.poss.refund.common.constant.RefundConstants;
import com.pay.poss.refund.model.RefundDetailInfoDTO;
import com.pay.poss.refund.model.RefundOrderM;
import com.pay.poss.refund.model.RefundWorkOrderAndM;
import com.pay.poss.refund.model.WebQueryRefundDTO;
import com.pay.poss.refund.service.RefundManageService;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.poss.service.fi.input.FiBankFacadeService;
import com.pay.util.DateUtil;
import com.pay.util.StringUtil;

/**
 * @author Jason_wang
 *
 */
public class RefundReAuditController extends RefundAbstractController {

	private final transient Log log = LogFactory.getLog(getClass());  //transient ?
	private  transient RefundManageService refundManageService ;
	
	private OperatorPoiInterface operatorPoiInterface;
	private FiBankFacadeService bankInfoService;
	
	//add by nico.shao 2016-07-29
	// 最好是加一个时间限制，可以在两个小时后，自动复原。如果出问题的话，比如某一个异常没有抓住，可以在两个小时后还原
	// 这样的话，可以无需重启poss 
	// 批量处理状态： 简单处理， 只允许一个批量处理状态在执行过程中  ，防止nginx 重发 
		
	private static String batchstatus = "0";
	private static long lastBatchSetTime = 0;
		
	/*
	 *  上一次设置的时间 和当前时间相比，是否超时了 
	*/
	private static boolean checkBatchProcessOverTime(long deltaSecond){
		long nowTime = new Date().getTime();
		long delta = nowTime - lastBatchSetTime;
		if((Math.abs(delta))> deltaSecond){
			return true;
		}
		return false;
	}
		
	private static String setBatchStatus() {
		batchstatus = "1";
		lastBatchSetTime = new Date().getTime();   
		return batchstatus;
	}

	private static String resetBatchStatus() {
		batchstatus = "0";
		return batchstatus;
	}
		
	/*
	 * 返回为 true ,表示在批处理过程中
	 */
	private static boolean checkInBatchStatus(){
		if(batchstatus.equals("1")){
			if(checkBatchProcessOverTime(7200)){
				resetBatchStatus();
				return false;
			}
			return true;
		}				
		return false;
	}
	//end 2016-07-29
		
	public void setRefundManageService(RefundManageService refundManageService) {
		this.refundManageService = refundManageService;
	} 
	
	public void setOperatorPoiInterface(OperatorPoiInterface operatorPoiInterface) {
		this.operatorPoiInterface = operatorPoiInterface;
	}
	
	/**
	 * @param bankInfoService the bankInfoService to set
	 */
	public void setBankInfoService(FiBankFacadeService bankInfoService) {
		this.bankInfoService = bankInfoService;
	}

	/**
	 * 进入复核页面
	 * @param request
	 * @param response
	 * @return ModelAndView
	 */
	public ModelAndView entryRefundReAuditPage(HttpServletRequest request , HttpServletResponse response ) {
		String viewName = urlMap.get("refundReAuditInit");
		WebQueryRefundDTO temp = (WebQueryRefundDTO)RefundConstants.entryRefundPage().get("webQueryRefundDTO");
		Map<String,Object> model = new HashMap<String, Object>();
		model.put("webQueryRefundDTO",temp);
		model.put("bankList",this.bankInfoService.getFiBankList());
		AcctTypeEnum[] acctTypes = AcctTypeEnum.getBasicAcctTypes();
		model.put("acctTypes", acctTypes);
		return new ModelAndView(viewName,model);
	}
	
	/**
	 * 查询充退复核分页列表
	 */
	public ModelAndView queryRefundReAuditList(HttpServletRequest request , HttpServletResponse response ) throws ServletException, PossUntxException {
		String viewName = urlMap.get("refundReAuditList");
		final WebQueryRefundDTO webQueryRefundDTO = new WebQueryRefundDTO() ;
		bind(request, webQueryRefundDTO, "webQueryRefundDTO", "yyyy-MM-dd");
		Page<RefundWorkOrderAndM> resultPage = PageUtils.getPage(request);
		Map<String,Object> model = new HashMap<String, Object>();
		//设置登陆用户
		webQueryRefundDTO.setUserId(SessionUserHolderUtil.getLoginId());
		webQueryRefundDTO.setStatus(Arrays.asList(RefundConstants.REFUND_STATUS_1,RefundConstants.REFUND_STATUS_2));
		model = refundManageService.queryRefundReAuditInfo(resultPage,webQueryRefundDTO);
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
			RefundOrderM refundOrderM	= refundManageService.queryTradeOrderNo(refundOrderNo);
			return refundOrderM;
		}
		return null;
	}


	/**
	 * 进入充退复核详细页面
	 * @throws ServletException 
	 */
	public ModelAndView initReAuditDetail(HttpServletRequest request , HttpServletResponse response ) throws ServletException {
		String viewName = urlMap.get("refundReAuditDetail");
		final WebQueryRefundDTO webQueryRefundDTO = new WebQueryRefundDTO() ;
		bind(request, webQueryRefundDTO, "webQueryRefundDTO", "yyyy-MM-dd");
		Map<String,Object> model = new HashMap<String, Object>();
		model = refundManageService.queryRefundReAuditInfoDetail(webQueryRefundDTO);
		model.put("bankList", this.getBankListInf());
		return new ModelAndView(viewName,model);
	}
	
	/**
	 * 处理批量充退复核动作
	 * @throws ServletException 
	 */
	public ModelAndView handerRefundReAuditBatch(HttpServletRequest request , HttpServletResponse response ) throws ServletException {
		
		log.info("RefundReAuditBatch begin");		
		String viewName = urlMap.get("refundReAuditList");

		//add by nico.shao 2016-07-29
		if(checkInBatchStatus()){
			logger.error("在批次过程中，无法执行新的批处理过程");
			
			//这句可以么? 
			//return new ModelAndView(viewName).addObject("fileEmpty", "正在跑批处理中，请确认是否nginx自动重发。或者稍微再试。请查询跑批结果") ;
			Map<String,Object> model = new HashMap<String, Object>();  //一个空对象？ 
			return new ModelAndView(viewName,model);
		}
		setBatchStatus();
		//end 2016-07-29 
	
		String auditRefundRemark = StringUtil.null2String(request.getParameter("auditRefundRemark"));
		List<Map> channelOrders=(List<Map>)request.getAttribute("channleOrders");
		final WebQueryRefundDTO webQueryRefundDTO = new WebQueryRefundDTO() ;
		bind(request, webQueryRefundDTO, "webQueryRefundDTO", "yyyy-MM-dd");
		String userId = SessionUserHolderUtil.getLoginId();
		webQueryRefundDTO.setUserId(userId);
		Page<RefundWorkOrderAndM> resultPage = PageUtils.getPage(request);
		Map<String,Object> model = new HashMap<String, Object>();
		webQueryRefundDTO.setOldStatus(Arrays.asList(RefundConstants.REFUND_STATUS_1,RefundConstants.REFUND_STATUS_2));
		webQueryRefundDTO.setStatus(Arrays.asList(RefundConstants.REFUND_STATUS_1,RefundConstants.REFUND_STATUS_2));
		webQueryRefundDTO.setAuditRefundRemark(auditRefundRemark);//操作备注
		model = refundManageService.handeRefundReAuditBatch(resultPage,webQueryRefundDTO,channelOrders);
		
		resetBatchStatus();		
		log.info("RefundReAuditBatch end");
		return new ModelAndView(viewName,model);
	}
	
	/**
	 * 处理单笔充退复核动作
	 * @throws ServletException 
	 */
	public ModelAndView handerRefundReAuditSingle(HttpServletRequest request , HttpServletResponse response ) throws ServletException {
		String viewName = urlMap.get("assignTaskSuccess");
		String refundStatus = request.getParameter("refundStatus");
		String workOrderKy = request.getParameter("workOrderKy");
		String refundRemark = request.getParameter("refundRemark");
		String handleType = request.getParameter("handleType");
		String requestUrl = request.getParameter("requestUrl");
		String wstatus = request.getParameter("wstatus");
		String userId = SessionUserHolderUtil.getLoginId();
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("refundStatus",refundStatus);
		params.put("workOrderKy",workOrderKy);
		params.put("userId",userId);
		params.put("refundRemark",refundRemark);
		params.put("handleType",handleType);
		params.put("requestUrl",requestUrl);
		params.put("workOrderStatus",wstatus);
		
		params = refundManageService.handerRefundReAuditSingle(params);

		return new ModelAndView(viewName,params);
	}
	
	/**
	 * 下载审核信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView handleDownloadReAuditInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
		final WebQueryRefundDTO webQueryRefundDTO = new WebQueryRefundDTO() ;
		bind(request, webQueryRefundDTO, "webQueryRefundDTO", "yyyy-MM-dd");
		//设置登陆用户
		webQueryRefundDTO.setUserId(SessionUserHolderUtil.getLoginId());
		webQueryRefundDTO.setStatus(Arrays.asList(RefundConstants.REFUND_STATUS_1,RefundConstants.REFUND_STATUS_2));
		List<RefundDetailInfoDTO> auditList = refundManageService.queryReAuditInfo(webQueryRefundDTO);
		
		HSSFWorkbook workbook = operatorPoiInterface.buildExcel(auditList);
		String currentDate = DateUtil.getNowDate("yyyyMMdd");
		response.setContentType("application/octet-stream; charset=UTF-8");
		response.setHeader("Content-Disposition","attachment;filename="   
	             +   new   String(("ReAuditDataFile" + currentDate + System.currentTimeMillis() + ".xls").getBytes(),"ISO8859_1"));
		OutputStream out = response.getOutputStream();
		workbook.write(out);
        out.flush();
        out.close();
        response.setStatus(HttpServletResponse.SC_OK);
		return null;
	}
}
