package com.pay.fo.controller.fundout.orderconsistency.batchpaytoacct;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

import com.pay.fo.controller.fundout.WithdrawBaseController;
import com.pay.fundout.withdraw.dto.operatorlog.OperatorlogDTO;
import com.pay.fundout.withdraw.model.paytoaccount.Pay2AcctOrder;
import com.pay.fundout.withdraw.service.orderconsistency.PayToAccountConsistencyService;
import com.pay.poss.security.util.SessionUserHolderUtil;

/**
 * 业务类型
 * 
 * @Description
 * @project fo-withdraw-web
 * @file BusinessController.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved.
 *          Date Author Changes 2010-10-21 Jason.li Change
 */
public class MassPayOrderController extends WithdrawBaseController {
	
	private PayToAccountConsistencyService pay2accService;
	private String busiId;
	private String strMark;

	/**
	 * 初始化查询
	 */
	public ModelAndView processInit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(URL("init_doing"));
	}

	public ModelAndView processFail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(URL("init_fail"));
	}

	public ModelAndView searchMassPayOrder101(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("startDate", request.getParameter("startDate"));
		map.put("endDate", request.getParameter("endDate"));
		map.put("parentOrder", request.getParameter("parentOrder"));
		List<Pay2AcctOrder> orderList = pay2accService
				.searchMassPayOrder101(map);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("orderList", orderList);
		return new ModelAndView(URL("doingOrderList"), model);
	}

	public ModelAndView searchMassPayOrder112(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("startDate", request.getParameter("startDate"));
		map.put("endDate", request.getParameter("endDate"));
		map.put("parentOrder", request.getParameter("parentOrder"));
		List<Pay2AcctOrder> orderList = pay2accService
				.searchMassPayOrder112(map);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("orderList", orderList);
		return new ModelAndView(URL("failOrderList"), model);
	}

	public ModelAndView setOrderToSuccess(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long orderId = Long.valueOf(request.getParameter("orderId"));
		try {

			if (pay2accService.massPayOrderProceedToSuccess(orderId))
				response.getWriter().print("success");
			/**
			 * 写日志
			 */
			this.busiId = String.valueOf(orderId);
			this.strMark = "101批量付款子订单号:" + busiId + ":置为成功:处理成功";
			super.saveOperatorLog();
		} catch (Exception e) {
			/**
			 * 写日志
			 */
			this.busiId = String.valueOf(orderId);
			this.strMark = "101批量付款子订单号:" + busiId + ":置为成功:处理失败";
			super.saveOperatorLog();
			response.getWriter().print("fail");
		}
		return null;
	}

	public ModelAndView setOrderToFail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long orderId = Long.valueOf(request.getParameter("orderId"));
		try {
			if (pay2accService.massPayOrderProceedToFail(orderId))
				response.getWriter().print("success");
			/**
			 * 写日志
			 */
			this.busiId = String.valueOf(orderId);
			this.strMark = "101批量付款子订单号:" + busiId + ":置为失败:处理成功";
			super.saveOperatorLog();
		} catch (Exception e) {
			/**
			 * 写日志
			 */
			this.busiId = String.valueOf(orderId);
			this.strMark = "101批量付款子订单号:" + busiId + ":置为失败:处理失败";
			super.saveOperatorLog();
			response.getWriter().print("fail");
		}
		return null;
	}

	public ModelAndView createBackFundmentOrder(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		long orderId = Long.valueOf(request.getParameter("orderId"));
		try {

			if (pay2accService.createBackFundmentOrder(orderId))
				response.getWriter().print("success");
			/**
			 * 写日志
			 */
			this.busiId = String.valueOf(orderId);
			this.strMark = "112批量付款子订单号:" + busiId + ":补充退款单:处理成功";
			super.saveOperatorLog();
		} catch (Exception e) {
			/**
			 * 写日志
			 */
			this.busiId = String.valueOf(orderId);
			this.strMark = "112批量付款子订单号:" + busiId + ":补充退款单:处理失败";
			super.saveOperatorLog();
			response.getWriter().print("fail");
		}
		return null;
	}

	@Override
	public OperatorlogDTO buildOperatorLog() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		String operator = SessionUserHolderUtil.getLoginId();
		OperatorlogDTO operatorlogDTO = new OperatorlogDTO();
		operatorlogDTO.setBusiOrderId(busiId);
		operatorlogDTO.setCreationDate(new Date());
		operatorlogDTO.setOperator(operator);
		operatorlogDTO.setLogType(9);
		operatorlogDTO.setLogTypeDesc("批量付款子订单状态为101和112处理");
		operatorlogDTO.setMark(this.strMark);
		return operatorlogDTO;
	}

	public void setPay2accService(PayToAccountConsistencyService pay2accService) {
		this.pay2accService = pay2accService;
	}

}
