/**
 * 
 */
package com.pay.poss.yk.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.yk.dto.ExternalLogDto;
import com.pay.poss.yk.dto.ExternalLogSearchDto;
import com.pay.poss.yk.service.IYkCompensateService;

/**
 * @Description 后台易卡充值补单
 * @project 	ma-manager
 * @file 		RechargeCompensateController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 woyo Corporation. All rights reserved.
 * Date				Author			Changes
 * 2011-6-3			DDR				Create
 */
public class RechargeCompensateController extends MultiActionController {
	
	private IYkCompensateService ykCompensateService;
	
	private String indexView;
	private String compensateListView;
	
	

	public void setYkCompensateService(IYkCompensateService ykCompensateService) {
		this.ykCompensateService = ykCompensateService;
	}



	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}

	

	public void setCompensateListView(String compensateListView) {
		this.compensateListView = compensateListView;
	}


	public ModelAndView	index(HttpServletRequest request,HttpServletResponse response){
		return new ModelAndView(indexView);
	}
	/**
	 * 查询需要补单的订单
	 * @param request
	 * @param response
	 * @param paramDto
	 * @return
	 */
	public ModelAndView	compensateOrderPage(HttpServletRequest request,HttpServletResponse response,ExternalLogSearchDto paramDto){
		Page<ExternalLogDto> paramPage = PageUtils.getPage(request);
		paramDto.setExternalType(1);//易卡充值为1
		Page<ExternalLogDto> page = ykCompensateService.search(paramPage, paramDto);
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("page", page);
		return new ModelAndView(compensateListView,map2);
	}
	
	
	public ModelAndView aplayRefund(HttpServletRequest request,
			HttpServletResponse response) throws  IOException{
		String orderNo = ServletRequestUtils.getStringParameter(request, "orderNo","");
		ExternalLogDto dto  = ykCompensateService.findExternalLogByOrderNo(orderNo);
		
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		boolean isok = false;
		String exceptionMsg = "";
		try{
			if(!dto.isRefundAble()){
				isok = false;
				exceptionMsg = "，不允许退款，请确认充值状态";
			}else{
				ykCompensateService.aplayRefund(orderNo);
				isok = true;
			}
			
		}catch (Exception e) {
			isok = false;
			exceptionMsg = "，后台操作异常";
		}
		response.getWriter().write(isok?"S":("申请退款出现问题"+exceptionMsg));
		return null;
	}
	
	
	/**
	 * 查询订单状态
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView	queryOrderStatus(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String orderNo = ServletRequestUtils.getStringParameter(request, "orderNo","");
		ExternalLogDto dto  = ykCompensateService.findExternalLogByOrderNo(orderNo);
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		String status = dto.getProcessStatus() +"";
		response.getWriter().write(status);
		return null;
	}
	
	
	
}
