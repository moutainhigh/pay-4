package com.pay.rm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.rm.result.dto.CybsResult;
import com.pay.rm.result.service.CybsResultService;


/***
 * 风控订单
 * @author ddl
 *
 */
public class CybsResultController extends MultiActionController{

	private String findCybsResult;
	private CybsResultService cybsResultService;
	private String diskorderList;
	

	public void setDiskorderList(String diskorderList) {
		this.diskorderList = diskorderList;
	}


	public void setFindCybsResult(String findCybsResult) {
		this.findCybsResult = findCybsResult;
	}


	public void setCybsResultService(CybsResultService cybsResultService) {
		this.cybsResultService = cybsResultService;
	}


	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView(findCybsResult);
	}


	/****
	 * 查询风险订单
	 * @param request
	 * @param response
	 * @return
	 */
	public  ModelAndView list(HttpServletRequest request,HttpServletResponse response){
			CybsResult cy = request(request,response); //返回数据
			Page page=PageUtils.getPage(request);
			List<CybsResult> cybsResults = cybsResultService.findCybsResult(cy, page);
			ModelAndView view = new ModelAndView(diskorderList);
			view.addObject("list", cybsResults);
			view.addObject("request",cy);
			return view.addObject("page",page);
	}
	
	public  List<CybsResult> findCybs(HttpServletRequest request,HttpServletResponse response){
		CybsResult cy = request(request,response);
		List<CybsResult> cybsResults=cybsResultService.findCybsResult(cy); //查询数据
		return cybsResults;
	}
	
	public  CybsResult request(HttpServletRequest request,HttpServletResponse response){//请求参数封装
		String merchantReferenceCode=request.getParameter("merchantReferenceCode").trim();
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		String decision=request.getParameter("decision");
		String merchantDefinedData1=request.getParameter("merchantDefinedData1").trim();
		CybsResult cy=new CybsResult();
		cy.setMerchantReferenceCode(merchantReferenceCode);
		cy.setMerchantDefinedData1(merchantDefinedData1);
		cy.setDecision(decision);
		cy.setStartDate(startDate);
		cy.setEndDate(endDate);
		return cy;
	}
	
	public ModelAndView cybsExcel(HttpServletRequest request,HttpServletResponse response){
		List<CybsResult> findCybs = findCybs( request, response);
		  for (CybsResult cybsResult : findCybs) {
			  if(cybsResult.getDecision().equals("ACCEPT")){
				  cybsResult.setDecision("通过");
			  }else if(cybsResult.getDecision().equals("REJECT")){
				  cybsResult.setDecision("拒绝");
			  }else if(cybsResult.getDecision().equals("MONITOR")){
				  cybsResult.setDecision("监控");
			  }else if(cybsResult.getDecision().equals("REAUDIT")){
				  cybsResult.setDecision("审核");
			  }
		  }
		try {
			String[] headers=new String[]{"会员号","商户订单号","风险评分","发卡国家","下单IP","IP国家","cybs返回码","风险结果","下单时间"};
			String[] fields=new String[]{"merchantDefinedData1","merchantReferenceCode","afsreplyAfsresult","afsreplyBincountry","billtoIPaddress",
					"afsreplyIpCountry","reasonCode","decision","createDate"};
			Workbook grid = SimpleExcelGenerator.generateGrid("风 险 订 单", headers, fields,findCybs);
			Date myDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
			String dd = sdf.format(myDate);
			response.setContentType("application/force-download");
			response.setContentType("applicationnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + dd + ".xls");
			grid.write(response.getOutputStream());
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
