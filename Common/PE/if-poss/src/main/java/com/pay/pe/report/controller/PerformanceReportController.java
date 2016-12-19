package com.pay.pe.report.controller;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.pe.report.dto.PerformanceReportDTO;
import com.pay.pe.report.service.QueryReportServicre;
import com.pay.util.MfDate;

public class PerformanceReportController extends MultiActionController{
		
	private String indexView;
	
	private String reportView;
	
	private QueryReportServicre queryReportService;
	
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MfDate date = new MfDate();
		date.minusMonths(1);
		String dateStr = date.toString("yyyy-MM");
		return new ModelAndView(indexView).addObject("dateStr", dateStr);
	}
	
	public ModelAndView query(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String accTime = request.getParameter("startDate");
		
		List<PerformanceReportDTO> list  = queryReportService.getPerformanceReport(accTime);
		
		return new ModelAndView(reportView).addObject("list", list).addObject("accTime", accTime);
	}

	
	public ModelAndView downloadRecordList(HttpServletRequest request, HttpServletResponse response)throws Exception {
	try {
//		String fileName = "performance.xls";
		String fileTemplate ="/properties/performance_report.xls";
		
		String accTime = request.getParameter("startDate");
		
		List<PerformanceReportDTO> list  = queryReportService.getPerformanceReport(accTime);
	
		Workbook book = null;
		
		Map map = new HashMap();
		
		map.put("detailList", list);
		map.put("accTime", accTime);
		book = QueryReportWriteExcel.createWorkbook(map,fileTemplate);
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(accTime+".xls", "UTF8"));
		OutputStream os = new BufferedOutputStream(response.getOutputStream());
		book.write(os);
		os.flush();
		os.close();
		os = null;
		return null;
	} catch (RuntimeException e) {
			e.printStackTrace();
	}
	return new ModelAndView(this.indexView);
 }
	
	
	public String formatAmount(String amount) {
		if(StringUtils.isEmpty(amount)){
			return "0.00";
		}
		amount = amount.trim();
		BigDecimal value = new BigDecimal(amount);
		BigDecimal b = value.divide(new BigDecimal(1000));
		b = b.setScale(2, BigDecimal.ROUND_HALF_DOWN);
		DecimalFormat df = new DecimalFormat("#,##0.00");
		String s = df.format(b);
		return s;
	}
	
	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}
	
	public void setQueryReportService(QueryReportServicre queryReportService) {
		this.queryReportService = queryReportService;
	}

	public String getReportView() {
		return reportView;
	}

	public void setReportView(String reportView) {
		this.reportView = reportView;
	}

	public String getIndexView() {
		return indexView;
	}

	public QueryReportServicre getQueryReportService() {
		return queryReportService;
	}
	
}
