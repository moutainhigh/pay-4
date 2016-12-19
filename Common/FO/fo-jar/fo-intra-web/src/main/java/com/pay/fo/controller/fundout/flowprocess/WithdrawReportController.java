/**
 *  File: WithdrawReportController.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-9      Jason_wang      Changes
 *  
 *
 */
package com.pay.fo.controller.fundout.flowprocess;

import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.ModelAndView;

import com.pay.fo.controller.fundout.WithdrawBaseController;
import com.pay.fundout.withdraw.dto.flowprocess.WithdrawAuditDTO;
import com.pay.fundout.withdraw.dto.flowprocess.WithdrawReportQueryDTO;
import com.pay.fundout.withdraw.dto.flowprocess.WorkFlowHistory;
import com.pay.fundout.withdraw.dto.operatorlog.OperatorlogDTO;
import com.pay.fundout.withdraw.service.flowprocess.WithdrawOrderAuditService;
import com.pay.fundout.withdraw.service.flowprocess.WithdrawReportService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.service.inf.input.BankInfoFacadeService;
import com.pay.util.DateUtil;

/**
 * @author Jason_wang
 *
 */
public class WithdrawReportController extends WithdrawBaseController {

	private WithdrawReportService service;
	private BankInfoFacadeService bankInfoService;
	private WithdrawOrderAuditService wdOrdAuditService;
	
	
	/**
	 * @param wdOrdAuditService the wdOrdAuditService to set
	 */
	public void setWdOrdAuditService(WithdrawOrderAuditService wdOrdAuditService) {
		this.wdOrdAuditService = wdOrdAuditService;
	}
	
	/**
	 * @param service the service to set
	 */
	public void setService(WithdrawReportService service) {
		this.service = service;
	}
	
	/**
	 * @param bankInfoService the bankInfoService to set
	 */
	public void setBankInfoService(BankInfoFacadeService bankInfoService) {
		this.bankInfoService = bankInfoService;
	}
	
	/**
	 * 进入查询未处理数据页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView initNoDisposeDataPage(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		String viewName = urlMap.get("noDisposeInit");
		Date date = new Date();
		return new ModelAndView(viewName)
					.addObject("withdrawBankList", this.bankInfoService.getWithdrawBankList())
					.addObject("startDate",DateUtil.dateToStr(DateUtils.addDays(date,-3),"yyyy-MM-dd HH:mm:ss"))
					.addObject("endDate",DateUtil.dateToStr(date,"yyyy-MM-dd HH:mm:ss"));
	}
	
	
	/**
	 * 查询风控未处理数据
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView queryNoDisposeData(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		String viewName = urlMap.get("noDisposeList");
		WithdrawReportQueryDTO reportQueryDto = new WithdrawReportQueryDTO();

		bind(request, reportQueryDto, "reportQueryDto", null);

		String startTime = request.getParameter("startTime");
		if(StringUtils.isNotEmpty(startTime)){
			reportQueryDto.setStartTime(DateUtil.strToDate(startTime, "yyyy-MM-dd HH:mm:ss"));
		}
		String endTime = request.getParameter("endTime");
		if(StringUtils.isNotEmpty(endTime)){
			reportQueryDto.setEndTime(DateUtil.strToDate(endTime, "yyyy-MM-dd HH:mm:ss"));
		}
			
		
		Page<WithdrawAuditDTO> page = PageUtils.getPage(request); // 分页
		
		Map<String,Object> model = service.queryNoDisposeData(page, reportQueryDto);
		model.put("withdrawBankList", this.bankInfoService.getWithdrawBankList());
		return new ModelAndView(viewName,model);
	}
	
	/**
	 * 查询风控未处理数据详细信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView showNoDisposeDetailInfo(HttpServletRequest request, HttpServletResponse response) throws PossException {
		String viewName = urlMap.get("noDisposeDetailInfo");
		String orderId = request.getParameter("orderId");
		WithdrawAuditDTO orderDto = wdOrdAuditService.showOrderInfo(Long.valueOf(orderId));
		Map<String,Object> model = new HashMap<String, Object>();
		model.put("order", orderDto);
		model.put("withdrawBankList", this.bankInfoService.getWithdrawBankList());
		
		return new ModelAndView(viewName,model).addObject("orderDtosequenceId", orderId);
	}
	
	/**
	 * 进入查询未处理数据页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView initDisposedDataPage(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		String viewName = urlMap.get("disposedInit");
		Date date = new Date();
		return new ModelAndView(viewName)
					.addObject("withdrawBankList", this.bankInfoService.getWithdrawBankList())
					.addObject("startDate",DateUtil.dateToStr(DateUtils.addDays(date,-3),"yyyy-MM-dd HH:mm:ss"))
					.addObject("endDate",DateUtil.dateToStr(date,"yyyy-MM-dd HH:mm:ss"));
	}
	
	
	/**
	 * 查询风控未处理数据
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView queryDisposedData(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		String viewName = urlMap.get("disposedList");
		WithdrawReportQueryDTO reportQueryDto = new WithdrawReportQueryDTO();

		bind(request, reportQueryDto, "reportQueryDto", null);

		String startTime = request.getParameter("startTime");
		if(StringUtils.isNotEmpty(startTime)){
			reportQueryDto.setStartTime(DateUtil.strToDate(startTime, "yyyy-MM-dd HH:mm:ss"));
		}
		String endTime = request.getParameter("endTime");
		if(StringUtils.isNotEmpty(endTime)){
			reportQueryDto.setEndTime(DateUtil.strToDate(endTime, "yyyy-MM-dd HH:mm:ss"));
		}
			
		
		Page<WithdrawAuditDTO> page = PageUtils.getPage(request); // 分页
		
		Map<String,Object> model = service.queryDisposedData(page, reportQueryDto);
		model.put("withdrawBankList", this.bankInfoService.getWithdrawBankList());
		return new ModelAndView(viewName,model);
	}
	
	/**
	 * 查询风控未处理数据详细信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView showDisposedDetailInfo(HttpServletRequest request, HttpServletResponse response) throws PossException {
		String viewName = urlMap.get("disposedDetailInfo");
		String orderId = request.getParameter("orderId");
		String workOrderKy = request.getParameter("workOrderKy");
		WithdrawAuditDTO orderDto = wdOrdAuditService.showOrderInfo(Long.valueOf(orderId));
		// 获得工作流审核节点历史数据
		List<WorkFlowHistory> wfHisList = this.wdOrdAuditService.queryWorkFlowHisInfoByWorkKy(workOrderKy);
		
		Map<String,Object> model = new HashMap<String, Object>();
		model.put("order", orderDto);
		model.put("withdrawBankList", this.bankInfoService.getWithdrawBankList());
		model.put("history", wfHisList);
		return new ModelAndView(viewName,model).addObject("orderDtosequenceId", orderId);
	}
	
	/**
	 * 下载风控已处理数据
	 * @param request
	 * @param response
	 * @return
	 * @throws PossException
	 */
	public ModelAndView downloadDisposedData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		WithdrawReportQueryDTO reportQueryDto = new WithdrawReportQueryDTO();

		bind(request, reportQueryDto, "reportQueryDto", null);

		String startTime = request.getParameter("startTime");
		if(StringUtils.isNotEmpty(startTime)){
			reportQueryDto.setStartTime(DateUtil.strToDate(startTime, "yyyy-MM-dd HH:mm:ss"));
		}
		String endTime = request.getParameter("endTime");
		if(StringUtils.isNotEmpty(endTime)){
			reportQueryDto.setEndTime(DateUtil.strToDate(endTime, "yyyy-MM-dd HH:mm:ss"));
		}
		Map<String,Object> model = service.queryDisposedDataForDownload(reportQueryDto);
		XLSTransformer transformer = new XLSTransformer();

		Workbook book = transformer.transformXLS(this.getClass().getResourceAsStream("/properties/withdrawReportTemplate.xls"), model);
		String currentDate = DateUtil.getNowDate("yyyyMMdd");
		response.setContentType("application/octet-stream; charset=UTF-8");
		response.setHeader("Content-Disposition","attachment;filename="   
	             + new String(("WithdrawReport_" + currentDate + "_" + System.currentTimeMillis() + ".xls").getBytes(),"ISO8859_1"));
		OutputStream out = response.getOutputStream();
		book.write(out);
        out.flush();
        out.close();
        response.setStatus(HttpServletResponse.SC_OK);
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.pay.fundout.withdraw.controller.WithdrawBaseController#buildOperatorLog()
	 */
	@Override
	public OperatorlogDTO buildOperatorLog() {
		// TODO Auto-generated method stub
		return null;
	}

}
