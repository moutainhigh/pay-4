/**
 *  File: WithholdingController.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-23   Sany        Create
 *
 */
package com.pay.controller.fo.withholding;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.common.pagination.PageUtil;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.inf.dao.Page;
import com.pay.util.DateUtil;

/**
 * 代扣查询
 */
public class WithholdingController extends MultiActionController {
	
	private String withholdingSummaryView;
	
	private String withholdingDetailView;
	
	private String withholdingDetailExcelView;
	
	private int pageSize = 20;
	
	//private WithholdingQueryService withholdingQueryService;
	/**
	 * 默认页
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView(withholdingSummaryView);
		Date startTime = getDate(-7);
		Date endTime = getDate(0);
		return view.addObject("startTime", startTime).addObject("endTime", endTime);
	}
	
	/**
	 * 代扣摘要查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView withholdingSummaryQuery(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		ModelAndView view = new ModelAndView(withholdingSummaryView);
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String batchNo = request.getParameter("batchNo");
		String status = request.getParameter("status");
		int pager_offset=getPagerOffset(request);
		Map<String,Object> map = new HashMap<String,Object>();
		if (StringUtils.isNotBlank(startTime)) {
			map.put("startTime", startTime);
			view.addObject("startTime", startTime);
		}
		if (StringUtils.isNotBlank(endTime)) {
			map.put("endTime", endTime);
			view.addObject("endTime", endTime);
		}
		if (StringUtils.isNotBlank(batchNo)) {
			map.put("batchNo", batchNo);
			view.addObject("batchNo", batchNo);
		}
		if (StringUtils.isNotBlank(status)) {
			map.put("status", status);
			view.addObject("status", status);
		}
		map.put("loginName", loginSession.getLoginName());
		//Page<WithholdingFileInfoDto> page =  withholdingQueryService.withholdingSummaryQuery(map, pager_offset, pageSize);
//		PageUtil pageUtil = new PageUtil(page.getPageNo(),
//				pageSize, page.getTotalCount());// 分页处理
//		view.addObject("pu", pageUtil);
//		view.addObject("dateList", page.getResult());
		return view;
	}
	
	/**
	 * 代扣明细查询
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView withholdingDetailQuery(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		ModelAndView view = new ModelAndView(withholdingDetailView);
		String fileKy = request.getParameter("fileKy");
		String status = request.getParameter("status");
		String export = request.getParameter("export");
		int pager_offset=getPagerOffset(request);
		Map<String,Object> map = new HashMap<String,Object>();
		if (StringUtils.isNotBlank(fileKy)) {
			map.put("fileKy", fileKy);
		}else {
			return view;
		}
		if (StringUtils.isNotBlank(status)) {
			map.put("status", status);
		}
		map.put("loginName", loginSession.getLoginName());
//		Page<WithholdingFileDetailDto> page =  withholdingQueryService.withholdingDetailQuery(map, pager_offset, pageSize);
//		if (StringUtils.isNotBlank(export)) {
//			Page<WithholdingFileDetailDto> downLoadPage =  withholdingQueryService.withholdingDetailQuery(map, 1, page.getTotalCount());
//			setResonseHeader(request, response);
//			transDataType(downLoadPage.getResult());
//			return new ModelAndView(withholdingDetailExcelView).addObject("dateList", downLoadPage.getResult());
//		}
//		PageUtil pageUtil = new PageUtil(page.getPageNo(),
//				pageSize, page.getTotalCount());// 分页处理
//		view.addObject("pu", pageUtil);
//		transDataType(page.getResult());
//		view.addObject("dateList", page.getResult());
		return view;
	}
	
	public void setWithholdingSummaryView(String withholdingSummaryView) {
		this.withholdingSummaryView = withholdingSummaryView;
	}

	public void setWithholdingDetailView(String withholdingDetailView) {
		this.withholdingDetailView = withholdingDetailView;
	}
	
	/**
	 * @param withholdingQueryService the withholdingQueryService to set
	 */
//	public void setWithholdingQueryService(
//			WithholdingQueryService withholdingQueryService) {
//		this.withholdingQueryService = withholdingQueryService;
//	}
	
	/**
	 * @param withholdingDetailExcelView the withholdingDetailExcelView to set
	 */
	public void setWithholdingDetailExcelView(String withholdingDetailExcelView) {
		this.withholdingDetailExcelView = withholdingDetailExcelView;
	}
	
	protected int getPagerOffset(HttpServletRequest request) {
		int pager_offset=1;
		if (StringUtils.isNumeric(request.getParameter("pager_offset"))) {
			pager_offset=Integer.parseInt(request.getParameter("pager_offset"));
		}
		return pager_offset;
	}
	
	/**
	 * 设置文件下载响应
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	protected void setResonseHeader(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Expires", "0");
		response.setHeader("Pragma" ,"public");
		response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Cache-Control", "public");
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(("WH-RESULT-LIAN-"+getDate() +".xls").getBytes("UTF-8"),
						"ISO8859_1"));
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView downloadFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		Long fileKy = Long.valueOf(request.getParameter("fileKy"));
		
//		WithholdingFileInfoDto fileInfo = withholdingQueryService
//				.withholdingSummaryQuery(fileKy);
//		if (fileInfo == null || !fileInfo.getMerchantCode().equals(loginSession.getLoginName())) {
//			return null;
//		}
		InputStream inputStream = null;
		OutputStream os = null;
		try {
//			inputStream = new FileInputStream(new File(fileInfo.getFileName()));
//
//			response.reset();
//			response.setContentType("text/html;charset=UTF-8");
//			response.setHeader("Content-disposition", "attachment; filename="
//					+ fileInfo.getFileNameDesc());
			response.setBufferSize(1024);

			os = new BufferedOutputStream(response.getOutputStream());
			byte[] b = new byte[1024];
			int len = 0;
			while ((len = inputStream.read(b)) > 0) {
				os.write(b, 0, len);
				os.flush();
			}
			os.close();
			inputStream.close();
			return null;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		} finally {
			os = null;
			inputStream = null;
		}
	}
	
//	private void transDataType(List<WithholdingFileDetailDto> list) {
//		for (WithholdingFileDetailDto withholdingFileDetailDto : list) {
//			if (withholdingFileDetailDto.getOrderAmount().matches("^\\d+\\.?\\d+$|^\\d+$")) {
//				String amount = withholdingFileDetailDto.getOrderAmount();
//				DecimalFormat df = new DecimalFormat("#,###.00");
//				withholdingFileDetailDto.setOrderAmount(df.format(Double.parseDouble(amount)));
//			}
//		}
//	}
	
	/**
	 * 返回时间
	 * @param offset 向前后偏移的天数 0为当天
	 * @return
	 */
	@SuppressWarnings("static-access")
	protected Date getDate(int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.DATE, offset);
		
		return DateUtil.skipDateTime(DateUtil.getTheDate(new Date()), offset);
	}
	protected String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		return dateFormat.format(new Date());
	}
}
