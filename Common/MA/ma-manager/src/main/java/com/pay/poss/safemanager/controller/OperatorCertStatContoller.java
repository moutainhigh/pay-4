/**
 * 
 */
package com.pay.poss.safemanager.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.poss.safemanager.dto.OperatorCertStatDto;
import com.pay.poss.safemanager.service.OperatorCertManageService;

/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		OperatorCertStatContoller.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2011 pay Corporation. All rights reserved.
 * Date				Author				Changes
 * 2011-12-1			DaiDeRong			Create
 */
public class OperatorCertStatContoller extends MultiActionController {
	
	
	private String indexView;
	private String listView;
	
	private OperatorCertManageService operatorCertManageService;

	/**
	 * @param indexView the indexView to set
	 */
	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}

	/**
	 * @param listView the listView to set
	 */
	public void setListView(String listView) {
		this.listView = listView;
	}


	/**
	 * @param operatorCertManageService the operatorCertManageService to set
	 */
	public void setOperatorCertManageService(
			OperatorCertManageService operatorCertManageService) {
		this.operatorCertManageService = operatorCertManageService;
	}
	
	
	
	/**
	 * 到统计首页导航页
	 * @param request
	 * @param response
	 * @return 
	 */
	public ModelAndView index(HttpServletRequest request,
            HttpServletResponse response){
		return new ModelAndView(indexView);
	}
	
	
	/**
	 * 统计查询 
	 * @param request
	 * @param response
	 * @param bindParamDto
	 * @return
	 */
	public ModelAndView search(HttpServletRequest request,
            HttpServletResponse response){
		
		String beginTime = ServletRequestUtils.getStringParameter(request, "beginTime","");
		String endTime = ServletRequestUtils.getStringParameter(request, "endTime","");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		Date begin = null;
		Date end  = null ; 
		OperatorCertStatDto dto = new OperatorCertStatDto();
		try {
			begin = sdf.parse(beginTime);
		    end = sdf.parse(endTime);
		  //绑定数据查询用户 
		    dto = operatorCertManageService.queryCertStat(begin, end);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		return new ModelAndView(listView).addObject("dto", dto);
	}
	

}
