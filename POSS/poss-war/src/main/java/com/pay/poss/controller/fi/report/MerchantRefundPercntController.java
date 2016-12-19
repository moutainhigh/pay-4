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
 * 商户退款比例报表
 * @author delin
 *@date 2016年7月7日17:22:08
 */
public class MerchantRefundPercntController extends MultiActionController {

	private String index ;
	
	private String list;
	
	private ReportService reportService;
	
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView(index);
	}

	public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
		ModelAndView listView = new ModelAndView(list);
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
		Map<String,Object> resMap=reportService.queryRefundPercntRep(paraMap);
	
		Map pageMap = (Map) resMap.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);
		List<Map<String,Object>> totalResList=(List<Map<String, Object>>) resMap.get("totalResultList");
		if(totalResList!=null && totalResList.size()>0){
			listView.addObject("totalResList", totalResList.get(0));
		}
		List<Map<String,Object>> resList=(List<Map<String, Object>>) resMap.get("resultList");
		for (Map<String, Object> map : resList) {
			String failScale = String.valueOf(map.get("refundPercnt"));
			if(failScale.startsWith(".")){
				map.put("refundPercnt", "0"+failScale);
			}
		}
		return listView.addObject("resList", resList).addObject("page", page).addObject("paraMap", paraMap); 
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
		String target=request.getParameter("target");
		Map<String,Object> paraMap=new HashMap<String, Object>();
		paraMap.put("memberCode",memberCode);
		paraMap.put("startTime",startTime);
		paraMap.put("endTime",endTime);
		paraMap.put("cardType",cardType);
		paraMap.put("merchantType",merchantType);
		paraMap.put("target",target);
		Map<String,Object> resMap=reportService.queryRefundPercntRepDown(paraMap);
		List<Map<String,Object>> resList=(List<Map<String, Object>>) resMap.get("resultList");
		for (Map<String, Object> map : resList) {
			String failScale = String.valueOf(map.get("refundPercnt"));
			if(failScale.startsWith(".")){
				map.put("refundPercnt", "0"+failScale);
			}
			Long tradeDate = Long.valueOf(map.get("tradeDate")+"");
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			map.put("tradeDate", format.format(tradeDate));
		}
		try {
			String[] headers = new String[] { "会员号", "商户名称", "起止时间", "卡种",
					"退款笔数","交易总笔数","退款比例"};
			String[] fields = new String[] { "memberCode", "merchantName", "tradeDate","cardOrg",
					"refundCount", "tradeTotalCount","refundPercnt"};
			Workbook grid = SimpleExcelGenerator.generateGrid("商户退款比例报表",
					headers, fields, resList);
			Date myDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
			String dd = sdf.format(myDate);
			response.setContentType("application/force-download");
			response.setContentType("applicationnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ "JYTK"+dd + ".xls");
			grid.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public void setIndex(String index) {
		this.index = index;
	}

	public void setList(String list) {
		this.list = list;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	} 
}
