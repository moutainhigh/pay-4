/**
 *  File: FoReconcileQueryController.java
 *  Description:
 *  Copyright 2010-2010 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  2010-10-2   Sandy_Yang  create
 *  
 *
 */
package com.pay.poss.controller.reconcile;

import static com.pay.fundout.reconcile.common.util.ReconcileUtils.string2Timestamp;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.pay.fundout.reconcile.dto.rcresult.ReconcileResultDTO;
import com.pay.fundout.reconcile.dto.rcresult.ReconcileResultSummaryDTO;
import com.pay.fundout.reconcile.service.common.GetSelectListInfoService;
import com.pay.fundout.reconcile.service.rcresult.QueryReconcileResultService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;

/**
 * 查询对账结果Controller
 * 
 * @author Sandy_Yang
 */
public class ReconcileQueryController extends ReconcileBaseController {

	private QueryReconcileResultService reconcileQueryService;
	private GetSelectListInfoService getSelectListInfoService;

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		getBankCode(map);// 获取银行科目,填充下拉列表
		return new ModelAndView(urlMap.get("initQuery"), map);

	}

	/**
	 * 查询对账结果列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView queryResultList(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		String view = urlMap.get("queryList");
		getBankCode(map);// 获取银行科目,填充下拉列表
		String withDrawBankId = request.getParameter("withDrawBankId"); // 出款银行
		String startDate = request.getParameter("startDate"); // 开始时间
		String endDate = request.getParameter("endDate"); // 结束时间

		Page<ReconcileResultSummaryDTO> page = PageUtils.getPage(request);

		bindValue(map, withDrawBankId, startDate, endDate);

		page = reconcileQueryService.queryReconcileList(map, page);

		log.info("querySummerList : page " + page);
		map.put("page", page);
		return new ModelAndView(view, map);
	}

	/**
	 * 绑定入参并查询
	 * 
	 * @param map
	 * @param bankCode
	 * @param startDate
	 * @param endDate
	 * @param pageNo
	 */
	private void bindValue(Map<String, Object> map, String withDrawBankId,
			String startDate, String endDate) {
		map.put("withDrawBankId", withDrawBankId);
		map.put("startDate", string2Timestamp(startDate));
		map.put("endDate", string2Timestamp(endDate));
	}

	public ModelAndView initDetail(HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> param = new HashMap<String, Object>();
		bindMapValue(param, request);
		if (log.isInfoEnabled()) {
			log.info("initDetail.param : " + param);
		}
		return new ModelAndView(urlMap.get("initDetail"), param);
	}

	private void bindMapValue(Map<String, Object> param,
			HttpServletRequest request) {
		param.put("busiFlag", Long.valueOf(request.getParameter("busiFlag")));
		param.put("allCount", Long.valueOf(request.getParameter("allCount")));
		param.put("countFlag", request.getParameter("countFlag"));
		param.put("withdrawBankId", request.getParameter("withdrawBankId"));
		param.put("startDate",
				string2Timestamp(request.getParameter("startDate")));
		param.put("endDate", string2Timestamp(request.getParameter("endDate")));

	}

	/**
	 * 查询对账结果详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView queryResultDetail(HttpServletRequest request,
			HttpServletResponse response) {

		Map<String, Object> param = new HashMap<String, Object>();

		bindMapValue(param, request);

		if (log.isInfoEnabled()) {
			log.info("initDetail.param : " + param);
		}

		Page<ReconcileResultDTO> pageDetail = PageUtils.getPage(request);

		pageDetail = reconcileQueryService.queryReconcileDetail(param,
				pageDetail);

		param.put("page", pageDetail);
		if (pageDetail != null && pageDetail.getResult() != null
				&& pageDetail.getResult().size() > 0)
			param.put("allAmount", pageDetail.getResult().get(0).getAllAmount());

		return new ModelAndView(urlMap.get("detailList"), param);
	}

	/**
	 * 导出查询结果至Excel
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView exportExcel(HttpServletRequest request,
			HttpServletResponse response) {
		// 流写数据
		return null;
	}

	/**
	 * 获取银行科目
	 * 
	 * @param map
	 */
	private void getBankCode(Map<String, Object> map) {
		map.put("bankCodeList", getSelectListInfoService.getBankOrgCodeList());
	}

	public void setReconcileQueryService(
			QueryReconcileResultService reconcileQueryService) {
		this.reconcileQueryService = reconcileQueryService;
	}

	public void setGetSelectListInfoService(
			GetSelectListInfoService getSelectListInfoService) {
		this.getSelectListInfoService = getSelectListInfoService;
	}
}
