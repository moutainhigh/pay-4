package com.pay.poss.accounting.controller;


import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.service.account.constantenum.PayForEnum;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.accounting.dto.AccountingFeeDto;
import com.pay.poss.accounting.dto.AccountingFeeParamDto;
import com.pay.poss.accounting.service.AccountingFeeService;
import com.pay.util.DateUtil;


/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		AccountingFeeController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2012-3-2		DDR				Create
 */

public class AccountingFeeController extends MultiActionController{
	
	private String indexView;
	private String listView;
	private String exportFeeListPath;
	
	
	
	public void setExportFeeListPath(String exportFeeListPath) {
		this.exportFeeListPath = exportFeeListPath;
	}

	public String getIndexView() {
		return indexView;
	}

	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}

	
	public String getListView() {
		return listView;
	}

	public void setListView(String listView) {
		this.listView = listView;
	}



	private AccountingFeeService accountingFeeService;;


	public void setAccountingFeeService(AccountingFeeService accountingFeeService) {
		this.accountingFeeService = accountingFeeService;
	}

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		Date curTime = new Date();
		Date minTime =DateUtil.skipDateTime(curTime, -90) ;//最大时间是提前三个月左右
		PayForEnum[] payTypes = PayForEnum.values();
		map.put("curTime", curTime);
		map.put("minTime", minTime);
		map.put("payTypes",payTypes);
		return new ModelAndView(indexView,map);
	}
	
	public ModelAndView search(HttpServletRequest request,
			HttpServletResponse response,AccountingFeeParamDto dto){
		Page<AccountingFeeDto> paramPage = PageUtils.getPage(request);
		Page<AccountingFeeDto> page =  accountingFeeService.search(paramPage, dto);
		logger.info(page+"-->"+page.getResult());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		return new ModelAndView(listView,map);
	}
	
	
	public ModelAndView downloadFeeList(HttpServletRequest request,
			HttpServletResponse response, AccountingFeeParamDto dto)
			throws Exception {

		
		String fullPath = request.getSession().getServletContext()
				.getRealPath(exportFeeListPath);
		HSSFWorkbook book;
		OutputStream os = null;
		try {
			// 创建workbook
			book = accountingFeeService.exportFeeListExcel(dto, fullPath);
			// 设置格式
			// 输出
			response.setContentType("application/msexcel;charset=GBK");
			response.addHeader(
					"Content-Disposition",
					(new StringBuilder("attachment;   filename=\""))
							.append(new String("计费扣费成功明细.xls".getBytes("GB2312"),
									"ISO-8859-1")).append("\"").toString());
			os = response.getOutputStream();
			book.write(os);
			os.flush();
		} catch (FileNotFoundException e) {
			logger.error("写excel报错，模板文件不存在", e);
		} catch (Exception e) {
			logger.error("写excel报错IO", e);
		} finally {
			try {
				os.close();
			} catch (Exception e) {
				logger.error("关闭流出错", e);
			}
		}
		return null;
	}
	
	
	
	
}
