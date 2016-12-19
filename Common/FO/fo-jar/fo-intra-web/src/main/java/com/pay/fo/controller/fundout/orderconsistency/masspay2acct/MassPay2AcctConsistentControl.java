/**
 *  File: MassPay2AcctConsistentControl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-13      Jonathen Ni      Changes
 *  
 *
 */
package com.pay.fo.controller.fundout.orderconsistency.masspay2acct;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.pay.fo.controller.fundout.WithdrawBaseController;
import com.pay.fundout.withdraw.common.util.WithdrawJSON;
import com.pay.fundout.withdraw.dto.batchpaytoaccount.MasspayImportRecordDTO;
import com.pay.fundout.withdraw.dto.operatorlog.OperatorlogDTO;
import com.pay.fundout.withdraw.service.orderconsistency.BatchPay2AccountConsistencyService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.SpringControllerUtils;
import com.pay.util.TimeUtil;

/**
 * @author Jonathen Ni
 * @since 2010-09-13
 */
public class MassPay2AcctConsistentControl extends WithdrawBaseController {
	private BatchPay2AccountConsistencyService massPay2AcctConsistentService;

	private String toView;

	private String viewName;

	private String transmitView;

	/**
	 * @param transmitView
	 *            the transmitView to set
	 */
	public void setTransmitView(String transmitView) {
		this.transmitView = transmitView;
	}

	private String auditDetailView;

	public void setAuditDetailView(String auditDetailView) {
		this.auditDetailView = auditDetailView;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public void setToView(String toView) {
		this.toView = toView;
	}
	
	public void setMassPay2AcctConsistentService(
			BatchPay2AccountConsistencyService massPay2AcctConsistentService) {
		this.massPay2AcctConsistentService = massPay2AcctConsistentService;
	}

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(toView).addObject("startDate",
				TimeUtil.getDate(-3)).addObject("endDate", TimeUtil.getDate());
	}

	/**
	 * @auther Jonathen Ni
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView search(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String batchNum = request.getParameter("batchNum");
		String payerMember = request.getParameter("payerMember");
		Page<MasspayImportRecordDTO> page = PageUtils.getPage(request); // 分页
		
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("batchNum", batchNum);
		map.put("payerMember", payerMember);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		page = this.massPay2AcctConsistentService.search(map,page);
		Map<String, Page<MasspayImportRecordDTO>> model = new HashMap<String, Page<MasspayImportRecordDTO>>();
		model.put("page", page);
		return new ModelAndView(viewName, model).addObject("batchNum", batchNum).addObject("payerMember",payerMember);
	}

	public ModelAndView repairMassPay2AcctSubOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String batchNum = request.getParameter("batchNum");
		String payerMember = request.getParameter("payerMember");
		WithdrawJSON json = WithdrawJSON.JsonBuilder();
		String operator  = SessionUserHolderUtil.getLoginId();
		try {
			this.massPay2AcctConsistentService.repairMassPay2AcctOrder(batchNum, payerMember,operator);
			json.setSuccess(true);
			json.setReason("生成子订单成功");
		} catch (Exception e) {
			//log.error("audit error...");
			json.setSuccess(false);
			json.setReason("生成子订单失败");
		} finally {
			SpringControllerUtils.renderText(response, json.toString());
		}
		return null;
	}
	
	@Override
	public OperatorlogDTO buildOperatorLog() {
		// TODO Auto-generated method stub
		return null;
	}

}
