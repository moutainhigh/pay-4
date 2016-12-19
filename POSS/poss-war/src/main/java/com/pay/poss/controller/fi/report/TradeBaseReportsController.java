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

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.client.ReportService;
import com.pay.util.MapUtil;
import com.pay.util.SimpleExcelGenerator;

/**
 * 交易基本情况分析报表
 * @author delin
 * @Date 2016年7月5日15:04:26
 */
public class TradeBaseReportsController extends	 MultiActionController {

	private String queryInit;
	
	private String resultList;
	
	private ReportService reportService;
	
	
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView(queryInit);
	}
	
	/**
	 * 查询交易基本情况
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
		String memberCode=request.getParameter("memberCode");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		String cardType=request.getParameter("cardType");
		String merchantType=request.getParameter("merchantType");
		Page page = PageUtils.getPage(request);
		Map<String,Object> paraMap=new HashMap<String, Object>();
		paraMap.put("memberCode",memberCode);
		paraMap.put("startTime",startTime);
		paraMap.put("endTime",endTime);
		paraMap.put("cardType",cardType);
		paraMap.put("merchantType",merchantType);
		paraMap.put("page",page);
		Map<String,Object> resMap=reportService.queryTradeBaseRep(paraMap);
		List<Map<String,Object>> totalResList=(List<Map<String, Object>>) resMap.get("totalResultList");
		List<Map<String,Object>> resList=(List<Map<String, Object>>) resMap.get("resultList");
		Map pageMap = (Map) resMap.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);
		return new ModelAndView(resultList).addObject("resList", resList).addObject("paraMap", paraMap).addObject("page", page).addObject("totalResList",totalResList.get(0));
	}
	
	/**
	 * 下载交易基本情况
	 * @param request
	 * @param response
	 * @return
	 */
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
		Map<String,Object> resMap=reportService.queryTradeBaseRepDown(paraMap);
		List<Map<String,Object>> resList=(List<Map<String, Object>>) resMap.get("resultList");
		for (Map<String, Object> map : resList) {
			String failScale = String.valueOf(map.get("failScale"));
			if(failScale.startsWith(".")){
				map.put("failScale", "0"+failScale);
			}
			Long tradeDate = Long.valueOf(map.get("tradeDate")+"");
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			map.put("tradeDate", format.format(tradeDate));
		}
		try {
			String[] headers = new String[] { "会员号", "商户名称", "交易时间", "卡种",
					"交易总笔数","成功笔数","交易成功率","交易总额CNY","客单价CNY"};
			String[] fields = new String[] { "memberCode", "merchantName", "tradeDate",
					"cardOrg", "tradeCount","tradeSuccessCount","tradeSuccessRate","tradeAmount","perTicketSales"};
			Workbook grid = SimpleExcelGenerator.generateGrid("交易基本情况表",
					headers, fields, resList);
			Date myDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
			String dd = sdf.format(myDate);
			response.setContentType("application/force-download");
			response.setContentType("applicationnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ "JYBB"+dd + ".xls");
			grid.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void setResultList(String resultList) {
		this.resultList = resultList;
	}

	public void setQueryInit(String queryInit) {
		this.queryInit = queryInit;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}
	
}
