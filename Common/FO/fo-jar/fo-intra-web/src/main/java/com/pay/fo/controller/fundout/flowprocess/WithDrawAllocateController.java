/**
 *  File: WithDrawAuditController.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-13      Jonathen Ni      Changes
 *  
 *
 */
package com.pay.fo.controller.fundout.flowprocess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.pay.fo.controller.fundout.WithdrawBaseController;
import com.pay.fundout.withdraw.dto.flowprocess.WithdrawAuditDTO;
import com.pay.fundout.withdraw.dto.flowprocess.WorkFlowHistory;
import com.pay.fundout.withdraw.dto.operatorlog.OperatorlogDTO;
import com.pay.fundout.withdraw.service.flowprocess.WithdrawOrderAuditService;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.poss.service.inf.input.BankInfoFacadeService;
import com.pay.util.SpringControllerUtils;

/**
 * @author Jonathen Ni
 * @since 2010-09-13
 */
public class WithDrawAllocateController extends WithdrawBaseController {
	private WithdrawOrderAuditService wdOrdAuditService;
	private BankInfoFacadeService bankInfoService;

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

	public void setWdOrdAuditService(WithdrawOrderAuditService wdOrdAuditService) {
		this.wdOrdAuditService = wdOrdAuditService;
	}

	/**
	 * @param bankInfoService
	 *            the bankInfoService to set
	 */
	public void setBankInfoService(BankInfoFacadeService bankInfoService) {
		this.bankInfoService = bankInfoService;
	}

/*	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = wdOrdAuditService.getAuditAndReAuditUsers();
		return new ModelAndView(toView, model)
				.addObject("withdrawBankList", this.bankInfoService.getWithdrawBankList());
	}*/

	/**
	 * @auther Jonathen Ni
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
/*	public ModelAndView search(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String nodeName = WithDrawConstants.TASK_ASSIGN_AUDIT_NODE;
		String userId = this.getSessionUserHolder().getUsername();

		WithdrawAuditQueryDTO quditQueryDto = new WithdrawAuditQueryDTO();

		bind(request, quditQueryDto, "auditQueryDto", null);

		Page<WithdrawAuditDTO> page = PageUtils.getPage(request); // 分页
		Map<String, Object> model = wdOrdAuditService.getAuditAndReAuditUsers();
		page = wdOrdAuditService.search(userId, nodeName, page, quditQueryDto);

		model.put("page", page);
		String viewPage = "".intern();
		if (StringUtils.isNotEmpty(quditQueryDto.getHandleUser())) {
			viewPage = this.transmitView;
		} else {
			viewPage = this.viewName;
		}

		return new ModelAndView(viewPage, model).addObject("withdrawBankList", this.bankInfoService
				.getWithdrawBankList());
	}*/

	/**
	 * 转派任务
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
/*	public ModelAndView transmitTask(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 分配人员
		String assigner = request.getParameter("userId");

		String wkKey = request.getParameter("wkKey");
		String[] processKeys = null;
		if (wkKey != null) {
			processKeys = wkKey.split("::");
		}
		wdOrdAuditService.transmitTask(processKeys, assigner);
		return search(request, response);
	}*/

	/**
	 * <p>
	 * 展示提现申请订单详细的信息
	 * </p>
	 * 
	 * @auther Jonathen Ni
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView showAuditOrderDetail(HttpServletRequest request, HttpServletResponse response)
			throws PossException {

		String orderId = request.getParameter("orderId");
		String workFlowId = request.getParameter("workFlowId");
		String nodeId = request.getParameter("nodeId");
		
		String orderDtosequenceId = request.getParameter("orderId");

		Map<String, Object> model = new HashMap<String, Object>();

		WithdrawAuditDTO orderDto = wdOrdAuditService.showOrderInfo(Long.parseLong(orderId));
		model.put("order", orderDto);

		// 获得工作流审核节点历史数据
		List<WorkFlowHistory> wfHisList = this.wdOrdAuditService.queryWorkFlowHisInfoByorderkKy(workFlowId);
		model.put("history", wfHisList);

		return new ModelAndView(auditDetailView, model).addObject("withdrawBankList", this.bankInfoService
				.getWithdrawBankList()).addObject("orderDtosequenceId", orderDtosequenceId);
	}

	/**
	 * <p>
	 * 分控任务分配
	 * </p>
	 * 
	 * @throws Exception
	 * @Auther Jonathen Ni
	 */
	public ModelAndView allocate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String result = "failer";
		try {
			// 分配人员
			String assigner = request.getParameter("userId");
			String loginNane = SessionUserHolderUtil.getLoginId();
			// 当前待分配任务的状态
			String auditStatus = request.getParameter("auditStatus");

			String wkKey = request.getParameter("wkKey");
			String[] processKeys = null;
			if (wkKey != null) {
				processKeys = wkKey.split("::");
			}
			this.wdOrdAuditService.allocateTaskRdTx(auditStatus, processKeys, assigner, loginNane);
			result = "success";
			// return search(request, response);
		} catch (Exception e) {
			log.error("audit error...");
		} finally {
			SpringControllerUtils.renderText(response, result);
		}
		return null;
	}

/*	public void loadUserByRole(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		Map<String, Object> model = new HashMap<String, Object>();
		String auditStatus = request.getParameter("auditStatus");
		List<String> list = new ArrayList<String>();
		PrintWriter out = null;
		try {
			list = this.wdOrdAuditService.queryUser(auditStatus);
			StringBuffer sb = new StringBuffer();

			for (String userName : list) {
				sb.append("<option value='").append(userName).append("'>").append(userName).append("</option>");
			}

			model.put("result", sb.toString());

			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			out = response.getWriter();
			out.write(sb.toString());

		} catch (Exception e) {
			throw new ServletException("PARAMETER ERROR! auditStatus is：" + auditStatus);
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}*/

	@Override
	public OperatorlogDTO buildOperatorLog() {
		// TODO Auto-generated method stub
		return null;
	}

}
