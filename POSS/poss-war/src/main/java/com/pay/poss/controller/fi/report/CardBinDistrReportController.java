package com.pay.poss.controller.fi.report;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.poss.client.ReportService;
import com.pay.util.SimpleExcelGenerator;
/**
 * cardbin 分布表
 * @author delin
 * @date 2016年7月13日18:05:19
 */
public class CardBinDistrReportController extends MultiActionController{
	
	
	private String index;
	
	private String list;
	
	private ReportService reportService;
	
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView(index);
	}
	
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
		String memberCode=request.getParameter("memberCode");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		String cardType=request.getParameter("cardType");
		String merchantType=request.getParameter("merchantType");
		Map<String,Object> paraMap=new HashMap<String, Object>();
		paraMap.put("memberCode",memberCode);
		paraMap.put("startTime",startTime);
		paraMap.put("endTime",endTime);
		paraMap.put("cardType",cardType);
		paraMap.put("merchantType",merchantType);
		Map<String,Object> resMap=reportService.queryCardBinRep(paraMap);
		List<Map<String,Object>> resList=(List<Map<String, Object>>) resMap.get("resultList");
		return new ModelAndView(list).addObject("resList", resList);
	}
	
	public ModelAndView download(HttpServletRequest request,HttpServletResponse response){
		String memberCode=request.getParameter("memberCode");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		String cardType=request.getParameter("cardType");
		String merchantType=request.getParameter("merchantType");
		Map<String,Object> paraMap=new HashMap<String, Object>();
		paraMap.put("memberCode",memberCode);
		paraMap.put("startTime",startTime);
		paraMap.put("endTime",endTime);
		paraMap.put("cardType",cardType);
		paraMap.put("merchantType",merchantType);
		Map<String,Object> resMap=reportService.queryCardBinRepDown(paraMap);
		List<Map<String,Object>> resList=(List<Map<String, Object>>) resMap.get("resultList");
		for (Map<String, Object> map : resList) {
			Long tradeDate = Long.valueOf(map.get("tradeDate")+"");
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			map.put("tradeDate", format.format(tradeDate));
		}
		try {
			String[] headers = new String[] { "会员号", "商户名称", "起止时间", "卡种",
					"地区","所占比例"};
			String[] fields = new String[] { "memberCode", "merchantName", "tradeDate","cardType",
					"cntyDesc","sPercent"};
			Workbook grid = SimpleExcelGenerator.generateGrid("卡 bin 分 布 表",
					headers, fields, resList);
			Date myDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
			String dd = sdf.format(myDate);
			response.setContentType("application/force-download");
			response.setContentType("applicationnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename="
					+"KBFB"+ dd + ".xls");
			grid.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}
	public void setIndex(String index) {
		this.index = index;
	}

	public void setList(String list) {
		this.list = list;
	}
}
