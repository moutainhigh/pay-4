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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.pay.fo.controller.fundout.WithdrawBaseController;
import com.pay.fundout.service.ma.AccountQueryFacadeService;
import com.pay.fundout.withdraw.common.util.WithDrawConstants;
import com.pay.fundout.withdraw.common.util.WithdrawJSON;
import com.pay.fundout.withdraw.dto.flowprocess.WithdrawAuditDTO;
import com.pay.fundout.withdraw.dto.flowprocess.WithdrawAuditQueryDTO;
import com.pay.fundout.withdraw.dto.flowprocess.WorkFlowHistory;
import com.pay.fundout.withdraw.dto.operatorlog.OperatorlogDTO;
import com.pay.fundout.withdraw.service.flowprocess.WithdrawOrderAuditService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.poss.service.inf.input.BankInfoFacadeService;
import com.pay.util.DateUtil;
import com.pay.util.SpringControllerUtils;
import com.pay.util.StringUtil;

/**
 * <p>
 * 结算节点controller
 * </p>
 * 
 * @author Jonathen Ni
 * @since 2010-09-16
 */
public class WithDrawLiquidateController extends WithdrawBaseController {
	private WithdrawOrderAuditService wdOrdAuditService;
	private BankInfoFacadeService bankInfoService;

	//private TradeQueryService tradeQueryService;

	private AccountQueryFacadeService accountQueryFacadeService;

	private String toView;

	private String viewName;

	private String auditDetailView;
	
	private String processingInitView;

	private String processingListView;

	private String processingDetailView;

	/**
	 * @param processingInitView the processingInitView to set
	 */
	public void setProcessingInitView(String processingInitView) {
		this.processingInitView = processingInitView;
	}

	/**
	 * @param processingListView the processingListView to set
	 */
	public void setProcessingListView(String processingListView) {
		this.processingListView = processingListView;
	}

	/**
	 * @param processingDetailView the processingDetailView to set
	 */
	public void setProcessingDetailView(String processingDetailView) {
		this.processingDetailView = processingDetailView;
	}

//	public void setTradeQueryService(TradeQueryService tradeQueryService) {
//		this.tradeQueryService = tradeQueryService;
//	}

	public void setAccountQueryFacadeService(
			AccountQueryFacadeService accountQueryFacadeService) {
		this.accountQueryFacadeService = accountQueryFacadeService;
	}

	public void setBankInfoService(BankInfoFacadeService bankInfoService) {
		this.bankInfoService = bankInfoService;
	}

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

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		return new ModelAndView(toView, DateUtil.getInitTime()).addObject(
				"withdrawBankList", this.bankInfoService.getWithdrawBankList())
				.addObject("accountModeList", this.getAccountModeList());
	}

	/**
	 * @auther Jonathen Ni
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView search(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 当前节点为复审
		String nodeName = WithDrawConstants.LIQUIDATE_NODE;
		String userId = SessionUserHolderUtil.getLoginId();

		Map<String, Page<WithdrawAuditDTO>> model = new HashMap<String, Page<WithdrawAuditDTO>>();

		WithdrawAuditQueryDTO auditQueryDto = new WithdrawAuditQueryDTO();

		bind(request, auditQueryDto, "auditQueryDto", null);
		String startDate = request.getParameter("startDate");
		if (StringUtils.isNotEmpty(startDate)) {
			auditQueryDto.setStartDate(DateUtil.strToDate(startDate,
					"yyyy-MM-dd HH:mm:ss"));
		}
		String endDate = request.getParameter("endDate");
		if (StringUtils.isNotEmpty(endDate)) {
			auditQueryDto.setEndDate(DateUtil.strToDate(endDate,
					"yyyy-MM-dd HH:mm:ss"));
		}

		Page<WithdrawAuditDTO> page = PageUtils.getPage(request); // 分页

		//Map<String, Object> inBalanceMap = new HashMap<String, Object>();// 入款余额
		page = wdOrdAuditService.search(userId, nodeName, page, auditQueryDto);
		//List<WithdrawAuditDTO> resultList = page.getResult();
		/*for (WithdrawAuditDTO withdrawAuditDTO : resultList) {
			inBalanceMap.put("K" + withdrawAuditDTO.getSequenceId().toString(),
					getInBalance(withdrawAuditDTO));
		}*/
		model.put("page", page);
		return new ModelAndView(viewName, model)
				.addObject("withdrawBankList",
						this.bankInfoService.getWithdrawBankList())
				.addObject("accountModeList", this.getAccountModeList());
				//.addObject("inBalanceMap", inBalanceMap);
	}

	/**
	 * 获取入款余额
	 * 
	 * @param memberCode
	 * @return
	 */
	private Long getInBalance(WithdrawAuditDTO withdrawAuditDTO) {
//		Long gatewayAmount = tradeQueryService
//				.tradeAmountQueryForMerchant(withdrawAuditDTO.getMemberCode());
//		Long balance = accountQueryFacadeService.getBalance(withdrawAuditDTO
//				.getMemberCode(), withdrawAuditDTO.getMemberAccType()
//				.intValue());
//		if (gatewayAmount == null) {
//			gatewayAmount = 0L;
//		}
//		if (balance == null) {
//			balance = 0L;
//		}
		return 0L;//balance - gatewayAmount + withdrawAuditDTO.getAmount();
	}

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
	public ModelAndView showAuditOrderDetail(HttpServletRequest request,
			HttpServletResponse response) throws PossException {

		String orderId = request.getParameter("orderId");
		String workOrderKy = request.getParameter("workOrderKy");
		// String nodeId = request.getParameter("nodeId");

		String orderDtosequenceId = request.getParameter("orderId");

		Map<String, Object> model = new HashMap<String, Object>();

		WithdrawAuditDTO orderDto = wdOrdAuditService.showOrderInfo(Long
				.parseLong(orderId));
		model.put("order", orderDto);

		// 获得工作流审核节点历史数据
		List<WorkFlowHistory> wfHisList = this.wdOrdAuditService
				.queryWorkFlowHisInfoByWorkKy(workOrderKy);
		model.put("history", wfHisList);

		return new ModelAndView(auditDetailView, model).addObject(
				"withdrawBankList", this.bankInfoService.getWithdrawBankList())
				.addObject("orderDtosequenceId", orderDtosequenceId);
	}

	/**
	 * <p>
	 * 复核提现
	 * </p>
	 * 
	 * @throws Exception
	 * @Auther Jonathen Ni
	 */
	public ModelAndView audit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		WithdrawJSON json = null;
		try {
			String[] wkKeys = request.getParameterValues("wkKey");
			if (wkKeys == null) {
				String wkKey = request.getParameter("wkKey");
				wkKeys = new String[1];
				wkKeys[0] = wkKey;
			}
			int auditStatus = Integer.parseInt(request
					.getParameter("auditStatus"));
			String auditRemark = StringUtil.null2String(request
					.getParameter("auditRemark"));
			String userId = SessionUserHolderUtil.getLoginId();
			// 提交工作流的格式暂时是自定的,以nodename##1(退回)或者nodename##0(拒绝) liquidate
			String taskMessage = WithDrawConstants.LIQUIDATE_NODE + "##"
					+ auditStatus + "##";
			taskMessage += auditRemark;

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			map.put("nodeName", WithDrawConstants.LIQUIDATE_NODE);
			map.put("processKey", WithDrawConstants.PROCESS_NAME);
			map.put("previousUser", "");
			map.put("taskMessage", taskMessage);

			json = WithdrawJSON.JsonBuilder();
			String seqId = this.wdOrdAuditService.liquidateAudit(map, wkKeys,
					auditStatus, auditRemark);
			json.setSuccess(true);
			json.setSequenceId(seqId == null ? "" : seqId);
			json.setReason("error");
		} catch (Exception e) {
			json.setSuccess(false);
			json.setReason("error");
			log.error("audit error...");
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

	/** 查询处理中数据初始页
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView processingIndex(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		return new ModelAndView(processingInitView, DateUtil.getInitTime()).addObject(
				"withdrawBankList", this.bankInfoService.getWithdrawBankList())
				.addObject("accountModeList", this.getAccountModeList());
	}

	/**处理中数据结果列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView processingSearch(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 当前节点为复审
		String nodeName = WithDrawConstants.PROCESSING_NODE;
		String userId = SessionUserHolderUtil.getLoginId();

		Map<String, Page<WithdrawAuditDTO>> model = new HashMap<String, Page<WithdrawAuditDTO>>();

		WithdrawAuditQueryDTO auditQueryDto = new WithdrawAuditQueryDTO();

		bind(request, auditQueryDto, "auditQueryDto", null);
		String startDate = request.getParameter("startDate");
		if (StringUtils.isNotEmpty(startDate)) {
			auditQueryDto.setStartDate(DateUtil.strToDate(startDate,
					"yyyy-MM-dd HH:mm:ss"));
		}
		String endDate = request.getParameter("endDate");
		if (StringUtils.isNotEmpty(endDate)) {
			auditQueryDto.setEndDate(DateUtil.strToDate(endDate,
					"yyyy-MM-dd HH:mm:ss"));
		}

		Page<WithdrawAuditDTO> page = PageUtils.getPage(request); // 分页

		page = wdOrdAuditService.search(userId, nodeName, page, auditQueryDto);

		model.put("page", page);
		return new ModelAndView(processingListView, model)
				.addObject("withdrawBankList",
						this.bankInfoService.getWithdrawBankList())
				.addObject("accountModeList", this.getAccountModeList());
	}

	/**处理中数据详情
	 * @param request
	 * @param response
	 * @return
	 * @throws PossException
	 */
	public ModelAndView showProcessingOrderDetail(HttpServletRequest request,
			HttpServletResponse response) throws PossException {

		String orderId = request.getParameter("orderId");
		String workOrderKy = request.getParameter("workOrderKy");
		// String nodeId = request.getParameter("nodeId");

		String orderDtosequenceId = request.getParameter("orderId");

		Map<String, Object> model = new HashMap<String, Object>();

		WithdrawAuditDTO orderDto = wdOrdAuditService.showOrderInfo(Long
				.parseLong(orderId));
		model.put("order", orderDto);

		// 获得工作流审核节点历史数据
		List<WorkFlowHistory> wfHisList = this.wdOrdAuditService
				.queryWorkFlowHisInfoByWorkKy(workOrderKy);
		model.put("history", wfHisList);

		return new ModelAndView(processingDetailView, model).addObject(
				"withdrawBankList", this.bankInfoService.getWithdrawBankList())
				.addObject("orderDtosequenceId", orderDtosequenceId);
	}

}
