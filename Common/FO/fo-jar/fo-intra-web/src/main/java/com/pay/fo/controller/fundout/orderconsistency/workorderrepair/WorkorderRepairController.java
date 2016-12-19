/**
 *  File: WorkorderRepairController.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-29      Sunsea.Li      Changes
 *  
 */
package com.pay.fo.controller.fundout.orderconsistency.workorderrepair;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.pay.fo.order.model.base.FundoutOrder;
import com.pay.fundout.withdraw.service.orderconsistency.WorkorderRepairService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.base.controller.AbstractBaseController;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.TimeUtil;

/**
 * 后台支撑功能-工单状态及工作流修复控制器
 * 
 * @author Sunsea.Li
 * 
 */
public class WorkorderRepairController extends AbstractBaseController {
	private WorkorderRepairService workorderRepairService;

	public void setWorkorderRepairService(
			WorkorderRepairService workorderRepairService) {
		this.workorderRepairService = workorderRepairService;
	}

	/**
	 * 进入工单状态及工作流修复初始页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(URL("workorderRepairInit")).addObject(
				"startDate", TimeUtil.getDate(-3)).addObject("endDate",
				TimeUtil.getDate());
	}

	/**
	 * 查询工单列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView queryList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String sequenceId = request.getParameter("sequenceId");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		Map paraMap = new HashMap();
		paraMap.put("sequenceId", sequenceId);
		paraMap.put("startDate", startDate);
		paraMap.put("endDate", endDate);

		Page<FundoutOrder> resultPage = PageUtils.getPage(request);
		Map<String, Object> model = new HashMap<String, Object>();
		model = workorderRepairService.queryWorkorders(paraMap, resultPage);
		return new ModelAndView(URL("workorderRepairList"))
				.addAllObjects(model);
	}

	/**
	 * 具体的工单状态及工作流修复动作
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView workorderRepair(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String orderId = request.getParameter("orderId");
		String userId = SessionUserHolderUtil.getLoginId();

		Map<String, Object> model = workorderRepairService
				.workorderRepairRdTx(orderId);
		return index(request, response).addAllObjects(model);
	}
}
