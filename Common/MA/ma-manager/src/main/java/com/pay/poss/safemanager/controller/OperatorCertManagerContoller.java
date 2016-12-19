/**
 * 
 */
package com.pay.poss.safemanager.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.poss.safemanager.dto.OperatorBindParamDto;
import com.pay.poss.safemanager.dto.OperatorCertDto;
import com.pay.poss.safemanager.service.OperatorCertManageService;

/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		OperatorCertManagerContoller.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2011 pay Corporation. All rights reserved.
 * Date				Author				Changes
 * 2011-12-1			DaiDeRong			Create
 */
public class OperatorCertManagerContoller extends MultiActionController {
	
	
	private String indexView;
	private String listView;
	private String detailView;
	
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
	 * @param detailView the detailView to set
	 */
	public void setDetailView(String detailView) {
		this.detailView = detailView;
	}

	/**
	 * @param operatorCertManageService the operatorCertManageService to set
	 */
	public void setOperatorCertManageService(
			OperatorCertManageService operatorCertManageService) {
		this.operatorCertManageService = operatorCertManageService;
	}
	
	
	
	/**
	 * 到导航页
	 * @param request
	 * @param response
	 * @param bindParamDto
	 * @return
	 */
	public ModelAndView index(HttpServletRequest request,
            HttpServletResponse response,OperatorBindParamDto bindParamDto){
		return new ModelAndView(indexView);
	}
	
	
	/**
	 * 到查询页
	 * @param request
	 * @param response
	 * @param bindParamDto
	 * @return
	 */
	public ModelAndView search(HttpServletRequest request,
            HttpServletResponse response,OperatorCertDto paramDto){
		//绑定数据查询用户 
		List<OperatorCertDto> list = operatorCertManageService.queryOperatorCertInfo(paramDto);
		return new ModelAndView(listView).addObject("list", list);
	}
	/**
	 * 查询详情信息
	 * @param request
	 * @param response
	 * @param bindParamDto
	 * @return
	 */
	public ModelAndView queryDetail(HttpServletRequest request,
            HttpServletResponse response){
		Long memberCode = ServletRequestUtils.getLongParameter(request, "memberCode",-1L);
		Long operatorId = ServletRequestUtils.getLongParameter(request, "operatorId",-1L);
		OperatorCertDto operatorCertDto = operatorCertManageService.queryOperatorCertUseInfo(memberCode, operatorId);
		return new ModelAndView(detailView).addObject("dto", operatorCertDto);
	}
	 
	/**
	 * 注销使用地点
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView removePlace(HttpServletRequest request,
            HttpServletResponse response) throws Exception{
		Long  certManageId = ServletRequestUtils.getLongParameter(request, "certManageId",-1L); 
		boolean isRemove = operatorCertManageService.removeUesPalceCert(certManageId);
		response.getWriter().write(isRemove?"S":"F");
		return null;
	}
	
	/**
	 * 注销整个证书
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView removeCert(HttpServletRequest request,
            HttpServletResponse response) throws Exception{
		Long  certMemberId = ServletRequestUtils.getLongParameter(request, "certMemberId",-1L); 
		boolean isRemove = operatorCertManageService.removeCert(certMemberId);
		response.getWriter().write(isRemove?"S":"F");
		return null;
	}
	

}
