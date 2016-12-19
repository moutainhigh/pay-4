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

import com.pay.inf.exception.AppException;
import com.pay.poss.safemanager.dto.OperatorBindDto;
import com.pay.poss.safemanager.dto.OperatorBindParamDto;
import com.pay.poss.safemanager.service.BindMobileService;

/**
 * @Description 绑定手机 管理
 * @project 	ma-manager
 * @file 		BindMobileManagerContoller.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 pay Corporation. All rights reserved.
 * Date				Author			Changes
 * 2011-11-22		DaiDeRong			Create
 */
public class BindMobileManagerContoller extends MultiActionController {
	
	
	private BindMobileService bindMobileService;
	private String indexView;
	private String listView;
	private String detailView;
	
	
	
	
	/**
	 * @param bindMobileService the bindMobileService to set
	 */
	public void setBindMobileService(BindMobileService bindMobileService) {
		this.bindMobileService = bindMobileService;
	}


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
            HttpServletResponse response,OperatorBindParamDto bindParamDto){
		//绑定数据查询用户 
		List<OperatorBindDto> list = bindMobileService.queryOperatorBindInfos(bindParamDto);
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
            HttpServletResponse response,OperatorBindParamDto bindParamDto){
		List<OperatorBindDto> list = bindMobileService.queryOperatorBindInfos(bindParamDto);
		return new ModelAndView(detailView).addObject("dto", list.get(0));
	}
	 /**
	  * 执行绑定信息
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception
	  */
	public ModelAndView exeBind(HttpServletRequest request,
            HttpServletResponse response) throws Exception{
		String newMobile = ServletRequestUtils.getStringParameter(request, "newMobile"); 
		Long operatorId = ServletRequestUtils.getLongParameter(request, "operatorId");
		boolean isUpdate = bindMobileService.modifyBindMobile(operatorId, newMobile);
		response.getWriter().write(isUpdate?"S":"F");
		return null;
	}
	
	/**
	 * 取消绑定
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView unBind(HttpServletRequest request,
            HttpServletResponse response,OperatorBindParamDto operatorBindParamDto) throws Exception{
		Long operatorId = ServletRequestUtils.getLongParameter(request, "operatorId");
		boolean isUpdate = false;
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		try{
			 isUpdate = bindMobileService.unBindMobile(operatorId);
			 response.getWriter().write(isUpdate?"S":"F");
			return null;
		}catch (AppException e) {
			
			 response.getWriter().write(e.getMessage());
			 return null;
		}
		
	}
	
	
	
	
	

}
