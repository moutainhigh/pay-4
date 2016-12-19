package com.pay.pe.report.controller;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.pe.report.dto.DateComparator;
import com.pay.pe.report.dto.QueryRequstParameter;
import com.pay.pe.report.dto.QueryResponseDTO;
import com.pay.pe.report.dto.SumaryResuleDTO;
import com.pay.pe.report.service.QueryReportServicre;
import com.pay.util.MfDate;
import com.pay.util.StringUtil;

public class ReportController extends MultiActionController{
		
	private String indexView;
	
	private String reportView;
	
	private QueryReportServicre queryReportService;
	
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MfDate date = new MfDate();
		date.minusDays(1);
		String dateStr = date.toString(MfDate.defaultDatePattern);
		return new ModelAndView(indexView).addObject("dateStr", dateStr);
	}
	
	public ModelAndView query(HttpServletRequest request, HttpServletResponse response,
			QueryReportCommand command) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		
		MfDate date = new MfDate();
		date.minusDays(1);
		
		if(StringUtil.isEmpty(command.getStartDate()) || StringUtil.isEmpty(command.getEndDate()) ){
			String dateStr = date.toString(MfDate.defaultDatePattern);
			map.put("startDate", dateStr);
			map.put("endDate", dateStr);
		}else{
			map.put("startDate", command.getStartDate());
			map.put("endDate", command.getEndDate());
		}
		
		List<SumaryResuleDTO> list  = queryReportService.getSumaryReportInfo(map);
		if(list != null && list.size() >0 ){
			DateComparator comparator = new DateComparator();
			Collections.sort(list,comparator);
		}
		return new ModelAndView(reportView).addObject("list", list);
	}

	
	public ModelAndView downloadRecordList(HttpServletRequest request, HttpServletResponse response,QueryReportCommand command)throws Exception {
	try {
		String fileName = "queryReportList.xls";
		String fileTemplate ="/properties/QueryReportTemplate.xls";
	
		Workbook book = null;
		Map<String, Object> map = new HashMap<String, Object>();
		List<QueryResponseDTO> detailList = null;
		if(command.getBusinessType() == null){
			return null;
		}
		QueryRequstParameter parameter = new QueryRequstParameter();
		parameter.setBusinessType(command.getBusinessType());
		parameter.setStartDate(command.getStartDate());
		parameter.setEndDate(command.getEndDate());
		if(StringUtil.isEmpty(command.getOrderStatus())){
			parameter.setOrderStatus(null);
		}else{
			parameter.setOrderStatus(command.getOrderStatus());
		}
		if(StringUtil.isEmpty(command.getChannel())){
			parameter.setChannel(null);
		}else{
			parameter.setChannel(command.getChannel());
		}
		parameter.setType(command.getType());
		detailList = queryReportService.getAllQueryReport(parameter);
		if(detailList != null && detailList.size() > 0){
			for(QueryResponseDTO dto : detailList){
				if(!StringUtil.isEmpty(dto.getOrderAmount())){
					dto.setOrderAmount(this.formatAmount(dto.getOrderAmount()));
				}else{
					dto.setOrderAmount("0.00");
				}
				if(!StringUtil.isEmpty(dto.getFee())){
					dto.setFee(this.formatAmount(dto.getFee()));
				}else{
					dto.setFee("0.00");
				}
				if(!StringUtil.isEmpty(dto.getRefundAmount())){
					dto.setRefundAmount(this.formatAmount(dto.getRefundAmount()));
				}else{
					dto.setRefundAmount("0.00");
				}
			}
		}
		map.put("businessType", command.getBusinessType());
		map.put("detailList", detailList);
		book = QueryReportWriteExcel.createWorkbook(map,fileTemplate);
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF8"));
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
