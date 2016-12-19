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
 * 交易失败详细分析表
 * @author delin
 * @date 2016年7月6日 16:02:15
 */
public class TradeFailDealController extends	 MultiActionController {

	private String tradeFailIndex;
	
	private String tradeFailList;
	
	private ReportService reportService;

	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView(tradeFailIndex);
	}
	/**
	 * 交易失败详细报表
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
		String memberCode=request.getParameter("memberCode");
		String startDate=request.getParameter("startTime");
		String endDate=request.getParameter("endTime");
		String cardType=request.getParameter("cardType");
		String merchantType=request.getParameter("merchantType");
		String target=request.getParameter("target");
		Page page = PageUtils.getPage(request);
		Map<String,Object> paraMap=new HashMap<String, Object>();
		paraMap.put("memberCode",memberCode);
		paraMap.put("startTime",startDate);
		paraMap.put("endTime",endDate);
		paraMap.put("cardType",cardType);
		paraMap.put("merchantType",merchantType);
		paraMap.put("target",target);
		paraMap.put("page",page);
		Map<String,Object> resMap=reportService.queryTradeFailDealRep(paraMap);
		List<Map<String,Object>> resList=(List<Map<String, Object>>) resMap.get("resultList");
		for (Map<String, Object> map : resList) {
			String failScale = String.valueOf(map.get("failScale"));
			if(failScale.startsWith(".")){
				map.put("failScale", "0"+failScale);
			}			
		}
		Map pageMap = (Map) resMap.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);
		return new ModelAndView(tradeFailList).addObject("resList", resList).addObject("paraMap", paraMap).addObject("page",page);
	} 

	/**
	 * 下载交易基本情况
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView download(HttpServletRequest request,HttpServletResponse response){
		String memberCode=request.getParameter("memberCode");
		String startDate=request.getParameter("startTime");
		String endDate=request.getParameter("endTime");
		String cardType=request.getParameter("cardType");
		String merchantType=request.getParameter("merchantType");
		String target=request.getParameter("target");
		Map<String,Object> paraMap=new HashMap<String, Object>();
		paraMap.put("memberCode",memberCode);
		paraMap.put("startTime",startDate);
		paraMap.put("endTime",endDate);
		paraMap.put("cardType",cardType);
		paraMap.put("merchantType",merchantType);
		paraMap.put("target",target);
		Map<String,Object> resMap=reportService.queryTradeFailDealRepDown(paraMap);
		List<Map<String,Object>> resList=(List<Map<String, Object>>) resMap.get("resultList");
		for (Map<String, Object> map : resList) {
			String failScale = String.valueOf(map.get("failScale"));
			String target1 = String.valueOf(map.get("target"));
			if(failScale.startsWith(".")){
				map.put("failScale", "0"+failScale);
			}
			if(target1.equals("T")){
				map.put("target", "商户");
			}else if(target1.equals("C")){
				map.put("target", "收单行");
			}
			Long tradeDate = Long.valueOf(map.get("tradeDate")+"");
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			map.put("tradeDate", format.format(tradeDate));
			
		}
		try {
			String[] headers = new String[] { "会员号", "商户名称", "起止时间", "卡种",
					"返回码所属","总交易笔数","总失败笔数","总失败率","失败原因名称","所占比例"};
			String[] fields = new String[] { "memberCode", "merchantName", "tradeDate","cardOrg",
					"target","totalCount", "tradeFailCount","tradeFailRate","failDesc","failScale"};
			Workbook grid = SimpleExcelGenerator.generateGrid("交易失败详细分析表",
					headers, fields, resList);
			Date myDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
			String dd = sdf.format(myDate);
			response.setContentType("application/force-download");
			response.setContentType("applicationnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename="
					+"JYSB"+ dd + ".xls");
			grid.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public void setTradeFailIndex(String tradeFailIndex) {
		this.tradeFailIndex = tradeFailIndex;
	}
	public void setTradeFailList(String tradeFailList) {
		this.tradeFailList = tradeFailList;
	}
	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}
}
