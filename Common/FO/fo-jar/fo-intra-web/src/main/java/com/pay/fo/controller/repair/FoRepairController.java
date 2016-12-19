/**
 *  File: FoRepairController.java
 *  Description:
 *  Copyright 2006-2012 pay Corporation. All rights reserved.
 *  Author   Changes     Date               
 *  Sandy    Create      2013-3-13         
 *
 */
package com.pay.fo.controller.repair;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.pay.fundout.repair.BaseRepairService;
import com.pay.poss.base.controller.AbstractBaseController;

/**
 * @author Sandy
 * @Date 2013-3-13上午9:19:29
 * @Description 出款订单修复
 */
public class FoRepairController extends AbstractBaseController {
	
	private Map<String,BaseRepairService> repairMap;

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		return new ModelAndView(URL("init"));
	}
	
	/**
	 * 查询原始订单信息，包括渠道订单、工单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException 
	 */
	public ModelAndView queryOrder(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		String orderId = request.getParameter("orderId");
		BaseRepairService repairService = repairMap.get(type);
		String retValue = "";
		if (repairService != null) {
			retValue = repairService.getOrderInfos(orderId);
		}
		response.setCharacterEncoding("utf-8");
		response.getWriter().print(retValue);
		return null;
	}
	
	/**
	 * 修复
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public ModelAndView repairOrder(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		String orderId = request.getParameter("orderId");
		BaseRepairService repairService = repairMap.get(type);
		String retValue = "";
		if (repairService != null) {
			if(repairService.repairOrder(orderId)){
				retValue="1";
			}
		}
		response.setCharacterEncoding("utf-8");
		response.getWriter().print(retValue);
		return null;
	}
	
	/**
	 * @param repairMap the repairMap to set
	 */
	public void setRepairMap(Map<String, BaseRepairService> repairMap) {
		this.repairMap = repairMap;
	}
}
