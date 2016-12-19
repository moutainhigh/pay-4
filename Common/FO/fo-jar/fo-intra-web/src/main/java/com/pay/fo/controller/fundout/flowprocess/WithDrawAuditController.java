/**
 *  File: WithDrawAuditController.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-13      Jonathen Ni      Changes
 *  
 *
 */
package com.pay.fo.controller.fundout.flowprocess;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.ModelAndView;

import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.fo.controller.fundout.WithdrawBaseController;
import com.pay.fundout.withdraw.common.util.WithDrawConstants;
import com.pay.fundout.withdraw.common.util.WithdrawJSON;
import com.pay.fundout.withdraw.dto.flowprocess.WithdrawAuditDTO;
import com.pay.fundout.withdraw.dto.flowprocess.WithdrawAuditQueryDTO;
import com.pay.fundout.withdraw.dto.flowprocess.WithdrawWFParameter;
import com.pay.fundout.withdraw.dto.flowprocess.WorkFlowHistory;
import com.pay.fundout.withdraw.dto.operatorlog.OperatorlogDTO;
import com.pay.fundout.withdraw.model.flowprocess.export.ExportWithdrawModel;
import com.pay.fundout.withdraw.service.flowprocess.WithdrawOrderAuditService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.base.common.poi.OperatorPoiInterface;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.poss.service.inf.input.BankInfoFacadeService;
import com.pay.util.DateUtil;
import com.pay.util.SpringControllerUtils;
import com.pay.util.StringUtil;

/**
 * @author Jonathen Ni
 * @since 2010-09-13
 */
public class WithDrawAuditController extends WithdrawBaseController {
	private WithdrawOrderAuditService wdOrdAuditService;
	private BankInfoFacadeService bankInfoService;
	private OperatorPoiInterface poiService;

	private String toView;

	private String viewName;

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

	public void setPoiService(OperatorPoiInterface poiService) {
		this.poiService = poiService;
	}

	public void setBankInfoService(BankInfoFacadeService bankInfoService) {
		this.bankInfoService = bankInfoService;
	}

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {

		AcctTypeEnum[] acctTypes = AcctTypeEnum.getBasicAcctTypes();
		return new ModelAndView(toView, DateUtil.getInitTime())
				.addObject("withdrawBankList",
						this.bankInfoService.getWithdrawBankList())
				.addObject("accountModeList", this.getAccountModeList())
				.addObject("riskLeveCodeList", this.getRiskLeveCodeList())
				.addObject("acctTypes", acctTypes);
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
		String nodeName = WithDrawConstants.AUDIT_NODE;
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
		return new ModelAndView(viewName, model)
				.addObject("withdrawBankList",
						this.bankInfoService.getWithdrawBankList())
				.addObject("accountModeList", this.getAccountModeList())
				.addObject("riskLeveCodeList", this.getRiskLeveCodeList());
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
		String nodeId = request.getParameter("nodeId");

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
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView delay(HttpServletRequest request,
			HttpServletResponse response) {
		WithdrawJSON json = null;
		try {
			String[] wkKeys = request.getParameterValues("wkKey");
			if (wkKeys == null) {
				String wkKey = request.getParameter("wkKey");
				wkKeys = new String[1];
				wkKeys[0] = wkKey;
			}
			String auditRemark = StringUtil.null2String(request
					.getParameter("auditRemark"));
			String userId = SessionUserHolderUtil.getLoginId();
			String taskMessage = WithDrawConstants.DELAY + "##" + 3 + "##";
			taskMessage += auditRemark;
			WithdrawWFParameter wfPara = new WithdrawWFParameter();
			wfPara.setAssigner("");
			wfPara.setNodeName(WithDrawConstants.DELAY);
			wfPara.setProcessKey(WithDrawConstants.PROCESS_NAME);
			wfPara.setTaskMessage(taskMessage);
			wfPara.setUserId(userId);

			String seqId = this.wdOrdAuditService.delay(wfPara, wkKeys,
					auditRemark);

			json = WithdrawJSON.JsonBuilder();
			if (seqId == null || seqId.equals(""))
				json.setSuccess(true);
			else
				json.setSuccess(false);
			json.setSequenceId(seqId == null ? "" : seqId);
			json.setReason("error");
		} catch (Exception e) {
			log.error("to delay error...");
		} finally {
			SpringControllerUtils.renderText(response, json.toString());
		}
		return null;

	}

	/**
	 * <p>
	 * 审核提现
	 * </p>
	 * 
	 * @throws Exception
	 * @Auther Jonathen Ni
	 */
	public ModelAndView audit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String result = "failer";
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
			// String nodeName = request.getParameter("nodeName");
			String userId = SessionUserHolderUtil.getLoginId();
			// 提交工作流的格式暂时是自定的,以nodename##0(通过)或者nodename##1(拒绝)
			int aggreFlag = 0;
			if (auditStatus == WithDrawConstants.FIRST_AUDIT_REJECT)
				aggreFlag = 1;
			else if (auditStatus == WithDrawConstants.FIRST_AUDIT_WAIT)
				aggreFlag = 2;
			String taskMessage = WithDrawConstants.AUDIT_NODE + "##"
					+ aggreFlag + "##";
			taskMessage += auditRemark;
			WithdrawWFParameter wfPara = new WithdrawWFParameter();
			wfPara.setAssigner("");
			wfPara.setNodeName(WithDrawConstants.AUDIT_NODE);
			wfPara.setProcessKey(WithDrawConstants.PROCESS_NAME);
			wfPara.setTaskMessage(taskMessage);
			wfPara.setUserId(userId);

			String seqId = this.wdOrdAuditService.firstAudit(wfPara, wkKeys,
					auditStatus, auditRemark);

			json = WithdrawJSON.JsonBuilder();
			if (seqId == null || seqId.equals(""))
				json.setSuccess(false);
			else
				json.setSuccess(true);
			json.setSequenceId(seqId);
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

	/**
	 * 导出EXCEL电子表格
	 * 
	 * @Auther Jonathen Ni
	 */
	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView exportExcel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Workbook workbook = null;
		XLSTransformer transformer = new XLSTransformer();
		String userId = SessionUserHolderUtil.getLoginId();
		WithdrawAuditQueryDTO quditQueryDto = new WithdrawAuditQueryDTO();
		bind(request, quditQueryDto, "auditQueryDto", null);
		String startDate = request.getParameter("startDate");
		if (StringUtils.isNotEmpty(startDate)) {
			quditQueryDto.setStartDate(DateUtil.strToDate(startDate,
					"yyyy-MM-dd HH:mm:ss"));
		}
		String endDate = request.getParameter("endDate");
		if (StringUtils.isNotEmpty(endDate)) {
			quditQueryDto.setEndDate(DateUtil.strToDate(endDate,
					"yyyy-MM-dd HH:mm:ss"));
		}
		List<ExportWithdrawModel> auditList = this.wdOrdAuditService
				.queryExportAuditInfo(userId, WithDrawConstants.AUDIT_NODE,
						quditQueryDto);
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("list", auditList);
		workbook = transformer.transformXLS(
				getClass().getResourceAsStream(
						"/properties/withdrawQueryListTemplate.xls"), beans);
		String currentDate = DateUtil.getNowDate("yyyyMMdd");
		response.setContentType("application/octet-stream; charset=UTF-8");
		response.setHeader(
				"Content-Disposition",
				"attachment;filename="
						+ new String(("AuditDataFile" + currentDate + "-"
								+ System.currentTimeMillis() + ".xls")
								.getBytes(), "ISO8859_1"));
		ServletOutputStream outputStream = response.getOutputStream();
		try {
			workbook.write(outputStream);
			response.setStatus(HttpServletResponse.SC_OK);
			outputStream.flush();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} finally {
			if (outputStream != null) {
				outputStream.close();
			}
		}
		return null;
	}

}
