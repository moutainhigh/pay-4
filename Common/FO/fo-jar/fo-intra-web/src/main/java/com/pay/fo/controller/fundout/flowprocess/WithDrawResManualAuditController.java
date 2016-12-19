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
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.pay.fo.controller.fundout.WithdrawBaseController;
import com.pay.fundout.withdraw.common.util.WithDrawConstants;
import com.pay.fundout.withdraw.common.util.WithdrawJSON;
import com.pay.fundout.withdraw.dto.flowprocess.WithdrawAuditDTO;
import com.pay.fundout.withdraw.dto.flowprocess.WithdrawResManualProcQueryDTO;
import com.pay.fundout.withdraw.dto.operatorlog.OperatorlogDTO;
import com.pay.fundout.withdraw.service.flowprocess.WithdrawOrderAuditService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.poss.service.inf.input.BankInfoFacadeService;
import com.pay.util.SpringControllerUtils;

/**
 * 提现结果手工审核SERVLET
 * 
 * @author Jonathen Ni
 * @since 2010-09-13
 */
public class WithDrawResManualAuditController extends WithdrawBaseController {
	private WithdrawOrderAuditService wdOrdAuditService;
	private BankInfoFacadeService bankInfoService;

	private String toView;

	private String viewName;

	private String auditDetailView;

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

	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		return new ModelAndView(toView).addObject("withdrawBankList", this.bankInfoService.getWithdrawBankList());
	}

	/**
	 * @auther Jonathen Ni
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView search(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String nodeName = WithDrawConstants.WITHDRAW_RESULT_PROCESS_MANUAL_AUDIT_NODE;
		String userId = SessionUserHolderUtil.getLoginId();

		Map<String, Page<WithdrawAuditDTO>> model = new HashMap<String, Page<WithdrawAuditDTO>>();

		WithdrawResManualProcQueryDTO resManualProcQueryDto = new WithdrawResManualProcQueryDTO();

		bind(request, resManualProcQueryDto, "resManualProcQueryDto", null);

		Page<WithdrawAuditDTO> page = PageUtils.getPage(request); // 分页

		page = wdOrdAuditService.searchWithdrawResProcManualInfo(page, resManualProcQueryDto, nodeName);

		model.put("page", page);
		return new ModelAndView(viewName, model).addObject("withdrawBankList", this.bankInfoService
				.getWithdrawBankList());
	}

	/**
	 * 人工初审提交
	 * 
	 * @Auther Jonathen Ni
	 */
	public ModelAndView audit(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String result = "";
		WithdrawJSON json = null;
		try {
			String[] wkKeys = request.getParameterValues("choose");
			String batchFailReason = null;
			String auditFailReason = null;
			StringBuffer sb = new StringBuffer("");
			String auditStatus = request.getParameter("auditStatus");
			if (auditStatus.equals("0")) {
				batchFailReason = request.getParameter("batchFailReason");
			}
			
			String userId = SessionUserHolderUtil.getLoginId();
			//提交工作流的格式暂时是自定的,以nodename##1(成功)或者nodename##0(失败) manualFirstAudit
			String taskMessage = "".intern();
			//taskMessage += auditRemark;

			if (wkKeys != null) {
				Long workOrderId = null;
				for (int i = 0; i < wkKeys.length; i++) {
					taskMessage = WithDrawConstants.MANUAL_FIRST_AUDIT_NODE + "##" + auditStatus + "##";
					workOrderId = new Long(wkKeys[i]);
					if (auditStatus.equals("0")) {
						if ((batchFailReason != null) && !batchFailReason.equals(""))
							auditFailReason = batchFailReason;
						else
							auditFailReason = request.getParameter(wkKeys[i]);
					}
					taskMessage += auditFailReason==null?"":auditFailReason;
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("userId", userId);
					map.put("nodeName", WithDrawConstants.MANUAL_FIRST_AUDIT_NODE);
					map.put("processKey", WithDrawConstants.PROCESS_NAME);
					map.put("previousUser", "");
					map.put("taskMessage", taskMessage);
					map.put("workOrderKy", workOrderId);
					
					String resId = this.wdOrdAuditService.withdrawResManualProcAuditRdTx(workOrderId, auditFailReason, Integer
							.parseInt(auditStatus),map);
					if(resId!=null && !resId.equals(""))
						sb.append(",").append(resId);
				}
			}

			boolean isSucc = true;
			String seqId = "";
			if(!sb.toString().equals("")){
				isSucc = false;
				seqId = sb.toString().substring(1, sb.toString().length());
			}
			json = WithdrawJSON.JsonBuilder();
			json.setSuccess(isSucc);
			json.setSequenceId(seqId==null?"":seqId);
			json.setReason("error");	
		} catch (Exception e) {
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

}
