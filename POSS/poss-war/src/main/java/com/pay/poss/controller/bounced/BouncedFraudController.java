package com.pay.poss.controller.bounced;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.ModelAndView;

import com.pay.fo.controller.fundout.common.MultiActionAndLogProcController;
import com.pay.fundout.withdraw.dto.operatorlog.OperatorlogDTO;
import com.pay.poss.client.FraudBouncedQueryService;
import com.pay.poss.controller.fi.dto.BouncedFraudResultDTO;
import com.pay.poss.controller.fi.dto.BouncedOrderVO;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;

/**
 * 拒付欺诈报表
 *  File: BouncedFraudController.java
 *  Description:
 *  Copyright 2016-2030 IPAYLINKS Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2016年6月12日   mmzhang     Create
 *
 */
public class BouncedFraudController extends MultiActionAndLogProcController {

	private FraudBouncedQueryService fraudBouncedQueryService;
	private String exportinDetailCorp;

	/**
	 * 初始化页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		Calendar time = Calendar.getInstance();
		Date todayDate = time.getTime();
		return new ModelAndView(urlMap.get("init")).addObject("todayDate",
				todayDate);
	}
	
	/**
	 * 查询报表
	 * 日期和会员号/二级渠道号做为主键
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ModelAndView query(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Calendar time = Calendar.getInstance();
		Date todayDate = time.getTime();
		String beginCreateDate = StringUtil.null2String(request.getParameter("beginCreateDate"));
		String endCreateDate = StringUtil.null2String(request.getParameter("endCreateDate"));
		//导出标志
		String export = request.getParameter("export");
		//<option value='5'>IPAY</option>
		//<option value='8'>GC</option>
		String flag = StringUtil.null2String(request.getParameter("flag"));
		//<option value='0'>商户</option>
		//<option value='1'>渠道</option>
		String type = StringUtil.null2String(request.getParameter("type"));
		String partnerId = StringUtil.null2String(request.getParameter("partnerId"));
		String merchantNo = StringUtil.null2String(request.getParameter("merchantNo"));
		String orgcodeQuery = StringUtil.null2String(request.getParameter("orgcodeQuery"));
		Map<String, List<BouncedFraudResultDTO>> BouncedFraudMaps=null;
		String enddate=endCreateDate.substring(0,4).concat(endCreateDate.substring(5,7));
		
		 BouncedFraudMaps = fraudBouncedQueryService.computeBouncedPossForView(beginCreateDate, endCreateDate, flag, type,
				partnerId, merchantNo,orgcodeQuery);	
		
		
		 if (StringUtils.isNotBlank(export)) {
				return exportResult2(request, response, BouncedFraudMaps,beginCreateDate,endCreateDate,flag,type,orgcodeQuery);
			}
		 if("true".equals(orgcodeQuery))
		 {
			 type="3";
		 }
		 StringBuilder datesb=new StringBuilder();
			datesb.append(endCreateDate.substring(0, 4).concat("/"));
			datesb.append(endCreateDate.substring(5,7).concat("/"));
			datesb.append("01");
			datesb.append("-");
			datesb.append(endCreateDate.substring(0, 4).concat("/"));
			datesb.append(endCreateDate.substring(5,7).concat("/"));
			datesb.append(endCreateDate.substring(8,10));
		
		return new ModelAndView(urlMap.get("list")).addObject("map", BouncedFraudMaps)
				.addObject("flag",flag)
				.addObject("type",type)
				.addObject("enddate",enddate)
				.addObject("date",datesb.toString())
				.addObject("orgcodeQuery",orgcodeQuery)
				;

	}

	
	/**
	 * 导出查询结果(excel)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView exportResult(final HttpServletRequest request,
			final HttpServletResponse response, Map<String, List<BouncedFraudResultDTO>> BouncedFraudMaps,
			String beginCreateDate,String endCreateDate,String flag,String type,String orgcodeQuery ) throws Exception {
		String excelname= "JFQZ_".concat(beginCreateDate).concat("_").concat(endCreateDate);
		setResonseHeader(request, response,excelname);
		String enddate=endCreateDate.substring(0,4).concat(endCreateDate.substring(5,7));
		 StringBuilder datesb=new StringBuilder();
			datesb.append(endCreateDate.substring(0, 4).concat("/"));
			datesb.append(endCreateDate.substring(5,7).concat("/"));
			datesb.append("01");
			datesb.append("-");
			datesb.append(endCreateDate.substring(0, 4).concat("/"));
			datesb.append(endCreateDate.substring(5,7).concat("/"));
			datesb.append(endCreateDate.substring(8,10));
		if("true".equals(orgcodeQuery))
		 {
			 type="3";
		 }
		return new ModelAndView(exportinDetailCorp).addObject(
				"map", BouncedFraudMaps).addObject("flag",flag)
				.addObject("type",type)
				.addObject("enddate",enddate)
				.addObject("date",datesb.toString());
	}
	public ModelAndView exportResult2(final HttpServletRequest request,
			final HttpServletResponse response, Map<String, List<BouncedFraudResultDTO>> BouncedFraudMaps,
			String beginCreateDate,String endCreateDate,String flag,String type,String orgcodeQuery ) throws Exception {
		String excelname= "JFQZ_".concat(beginCreateDate).concat("_").concat(endCreateDate);
		//setResonseHeader(request, response,excelname);
		String enddate=endCreateDate.substring(0,4).concat(endCreateDate.substring(5,7));
		StringBuilder datesb=new StringBuilder();
		datesb.append(endCreateDate.substring(0, 4).concat("/"));
		datesb.append(endCreateDate.substring(5,7).concat("/"));
		datesb.append("01");
		datesb.append("-");
		datesb.append(endCreateDate.substring(0, 4).concat("/"));
		datesb.append(endCreateDate.substring(5,7).concat("/"));
		datesb.append(endCreateDate.substring(8,10));
		if("true".equals(orgcodeQuery))
		{
			type="3";
		}
		
		//private String merchantNo; 
		//private String cardOrg; 
		try {
			String[] headers = new String[] { "商户号", "商户名称","渠道类别", "拒付笔数", "总订单数",
					"拒付率","欺诈金额","总交易额","欺诈金额比例"};
			String[] fields = new String[] { "partnerId", "partnerName", "cardOrg","bouncedCount",
					"totalCount", "sbouncedRate","fraudAmount","totalAmount","sfraudRate"};
			if("0".equals(type))
			{
				headers = new String[] { "商户号", "商户名称", "渠道类别","拒付笔数", "总订单数",
						"拒付率","欺诈金额","总交易额","欺诈金额比例"};
				fields = new String[] { "partnerId", "partnerName","cardOrg", "bouncedCount",
						"totalCount", "sbouncedRate","fraudAmount","totalAmount","sfraudRate"};
			}else if("1".equals(type))
			{
				headers = new String[] { "二级渠道号", "渠道类别", "拒付笔数", "总订单数",
						"拒付率","欺诈金额","总交易额","欺诈金额比例"};
				fields = new String[] { "merchantNo", "cardOrg", "bouncedCount",
						"totalCount", "sbouncedRate","fraudAmount","totalAmount","sfraudRate"};
			}else if("3".equals(type))
			{
				headers = new String[] { "所属渠道", "渠道类别", "拒付笔数", "总订单数",
						"拒付率","欺诈金额","总交易额","欺诈金额比例"};
				fields = new String[] { "orgName", "cardOrg", "bouncedCount",
						"totalCount", "sbouncedRate","fraudAmount","totalAmount","sfraudRate"};
			}	
				
			Workbook grid = SimpleExcelGenerator.generateGridByMap("拒付欺诈报表",
					headers, fields, BouncedFraudMaps,enddate,datesb.toString());
			Date myDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
			String dd = sdf.format(myDate);
			response.setContentType("application/force-download");
			response.setContentType("applicationnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ excelname + ".xls");
			grid.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  null;
	}
	
	
	/**
	 * 设置文件下载响应
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	protected void setResonseHeader(HttpServletRequest request,
			HttpServletResponse response,String excelname) throws Exception {
		response.setHeader("Expires", "0");
		response.setHeader("Pragma" ,"public");
		response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Cache-Control", "public");
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String((excelname+".xls").getBytes("UTF-8"),
						"ISO8859_1"));
	}
	
	
	
	@Override
	public OperatorlogDTO buildOperatorLog() {
		return null;
	}



	public void setFraudBouncedQueryService(FraudBouncedQueryService fraudBouncedQueryService) {
		this.fraudBouncedQueryService = fraudBouncedQueryService;
	}

	public void setExportinDetailCorp(String exportinDetailCorp) {
		this.exportinDetailCorp = exportinDetailCorp;
	}
	

}
