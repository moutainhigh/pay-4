/**
 *  File: OrderMassPayToBankController.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-28     darv      Changes
 *  
 *
 */
package com.pay.fo.controller.fundout.orderconsistency.masspaytobank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.pay.fundout.withdraw.service.masspaytobank.MassPaytobankImportBaseService;
import com.pay.fundout.withdraw.service.masspaytobank.MassPaytobankImportDetailService;
import com.pay.fundout.withdraw.service.masspaytobank.MassPaytobankOrderService;
import com.pay.fundout.withdraw.service.orderconsistency.OrderMassPayToBankService;
import com.pay.poss.base.controller.AbstractBaseController;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.TimeUtil;

/**
 * @author darv
 * 
 */
@SuppressWarnings("unchecked")
public class OrderMassPayToBankController extends AbstractBaseController {

	private OrderMassPayToBankService orderMassPayToBankService;

	private MassPaytobankOrderService massPaytobankOrderService;

	private MassPaytobankImportBaseService massPaytobankImportBaseService;

	private MassPaytobankImportDetailService massPaytobankImportDetailService;

	public void setMassPaytobankImportDetailService(
			MassPaytobankImportDetailService massPaytobankImportDetailService) {
		this.massPaytobankImportDetailService = massPaytobankImportDetailService;
	}

	public void setMassPaytobankImportBaseService(
			MassPaytobankImportBaseService massPaytobankImportBaseService) {
		this.massPaytobankImportBaseService = massPaytobankImportBaseService;
	}

	public void setMassPaytobankOrderService(
			MassPaytobankOrderService massPaytobankOrderService) {
		this.massPaytobankOrderService = massPaytobankOrderService;
	}

	public void setOrderMassPayToBankService(
			OrderMassPayToBankService orderMassPayToBankService) {
		this.orderMassPayToBankService = orderMassPayToBankService;
	}

	/**
	 * 查看有未生成子订单的成功总订单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(URL("noChildInit")).addObject("startDate",
				TimeUtil.getDate(-3)).addObject("endDate", TimeUtil.getDate());
	}

	public ModelAndView noChildList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("massOrderSeq", StringUtils.trimToNull(request
				.getParameter("massOrderSeq")));
		params.put("payerMemberCode", StringUtils.trimToNull(request
				.getParameter("payerMemberCode")));
		params.put("businessNo", StringUtils.trimToNull(request
				.getParameter("businessNo")));
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		if (startDate != null && startDate.trim().length() > 0) {
			params.put("startDate", startDate);
		}
		if (endDate != null && endDate.trim().length() > 0) {
			params.put("endDate", endDate);
		}
		List orderList = orderMassPayToBankService
				.getMasspayToBankOrder(params);
		return new ModelAndView(URL("noChildList")).addObject("orderList",
				orderList);
	}

	/**
	 * 查看未生成订单的明细
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView noChildDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map params = new HashMap();
		params.put("uploadSeq", request.getParameter("uploadSeq"));
		String uploadSeq = request.getParameter("uploadSeq");
		String massOrderSeq = request.getParameter("massOrderSeq");
		List detailList = orderMassPayToBankService.getDetail(params);
		return new ModelAndView(URL("noChildDetail")).addObject("detailList",
				detailList).addObject("massOrderSeq", massOrderSeq).addObject(
				"uploadSeq", uploadSeq);
	}

	/**
	 * 重成子订单并记日志
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView generatorOrder(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String uploadSeq = request.getParameter("uploadSeq");
		String massOrderSeq = request.getParameter("massOrderSeq");
		String operator = SessionUserHolderUtil.getLoginId();

		orderMassPayToBankService.createSingleOrderRnTx(uploadSeq,
				massOrderSeq, operator);
		return new ModelAndView(new RedirectView(request.getContextPath()
				+ URL("toNoChildInit")));
	}
}
