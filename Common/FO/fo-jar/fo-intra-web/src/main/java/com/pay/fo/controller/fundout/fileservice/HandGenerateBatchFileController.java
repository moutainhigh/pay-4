/** @Description 
 * @project 	poss-war
 * @file 		HandGenerateBatchFileController.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-19		Henry.Zeng			Create 
 */
package com.pay.fo.controller.fundout.fileservice;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.account.constantenum.AcctAttribEnum;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.fo.controller.fundout.WithdrawBaseController;
import com.pay.fundout.channel.service.channel.FundoutChannelService;
import com.pay.fundout.service.ma.AccountQueryFacadeService;
import com.pay.fundout.withdraw.dto.flowprocess.WithdrawAuditDTO;
import com.pay.fundout.withdraw.dto.operatorlog.OperatorlogDTO;
import com.pay.fundout.withdraw.model.flowprocess.WithdrawAuditQuery;
import com.pay.fundout.withdraw.service.bank.FoBankInfoService;
import com.pay.fundout.withdraw.service.fileservice.HandBatchFileService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.base.model.BatchFileInfo;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.poss.service.inf.input.BankInfoFacadeService;
import com.pay.util.DateUtil;

/**
 * <p>
 * 手工生成批次文件
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-9-19
 * @see
 */
public class HandGenerateBatchFileController extends WithdrawBaseController {

	
	private HandBatchFileService batchFileService;
	
	
	private FoBankInfoService bankInfoService;

	public FoBankInfoService getBankInfoService() {
		return bankInfoService;
	}

	public void setBankInfoService(FoBankInfoService bankInfoService) {
		this.bankInfoService = bankInfoService;
	}

	private BankInfoFacadeService bankFacadeService;

	private AccountQueryFacadeService accountQueryFacadeService;

	private AccountQueryService accountQueryService;

	private FundoutChannelService fundoutChannelService;
	

	public void setBankFacadeService(BankInfoFacadeService bankFacadeService) {
		this.bankFacadeService = bankFacadeService;
	}

	// set 注入
	public void setBatchFileService(final HandBatchFileService param) {
		this.batchFileService = param;
	}

	public void setFundoutChannelService(
			FundoutChannelService fundoutChannelService) {
		this.fundoutChannelService = fundoutChannelService;
	}

	/**
	 * @param accountQueryFacadeService
	 *            the accountQueryFacadeService to set
	 */
	public void setAccountQueryFacadeService(
			AccountQueryFacadeService accountQueryFacadeService) {
		this.accountQueryFacadeService = accountQueryFacadeService;
	}

	/**
	 * @param accountQueryService
	 *            the accountQueryService to set
	 */
	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}

	/**
	 * 初始化页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {

		List<Map<String, String>> fundoutChannelList = fundoutChannelService
				.getFundoutChannel();
		List<Map<String, String>> targetBankList = bankFacadeService
				.getWithdrawBankList();

		AcctTypeEnum[] acctTypes = AcctTypeEnum.getBasicAcctTypes();
		return new ModelAndView(urlMap.get("init"), DateUtil.getInitTime())
				.addObject("targetBankList", targetBankList)
				.addObject("fundoutChannelList", fundoutChannelList)
				.addObject("accountModeList", this.getAccountModeList())
				.addObject("acctTypes", acctTypes);
	}

	/**
	 * 查询提现列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView query(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {

		Page<WithdrawAuditDTO> page = PageUtils.getPage(request);

		WithdrawAuditQuery auditQueryDTO = new WithdrawAuditQuery();

		bind(request, auditQueryDTO, "auditQueryDTO", null);

		String busiType = auditQueryDTO.getBusiType() == null ? ""
				: auditQueryDTO.getBusiType();

		auditQueryDTO.setStatus("4"); // 复核通过

		auditQueryDTO.setBatchStatus2("2"); // 未出批次或已废除

		// Map<String,Object> isLoaningMap = new HashMap<String,Object>();//是否垫资
		// Map<String,Object> inBalanceMap = new HashMap<String,Object>();//入款余额
		page = batchFileService.search(page, auditQueryDTO);
		/*
		 * List<WithdrawAuditDTO> resultList = page.getResult(); for
		 * (WithdrawAuditDTO withdrawAuditDTO : resultList) {
		 * isLoaningMap.put("K"+withdrawAuditDTO.getSequenceId().toString(),
		 * getIsLoaning(withdrawAuditDTO.getMemberCode()));withdrawBankList
		 * inBalanceMap.put("K"+withdrawAuditDTO.getSequenceId().toString(),
		 * getInBalance(withdrawAuditDTO)); }
		 */

		List<Map<String, String>> fundoutChannelList = fundoutChannelService
				.getFundoutChannel();
		List<Map<String, String>> targetBankList = bankFacadeService
				.getWithdrawBankList();

		return new ModelAndView(urlMap.get("list")).addObject("page", page)
				.addObject("fundoutChannelList", fundoutChannelList)
				.addObject("targetBankList", targetBankList)
				.addObject("busiType", busiType)
				.addObject("accountModeList", this.getAccountModeList());
		// .addObject("isLoaningMap", isLoaningMap).addObject("inBalanceMap",
		// inBalanceMap);
	}

	/**
	 * Ajax请求获取入款金额
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView getInbalanceAjax(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		String memberCode = request.getParameter("memberCode");
		String memberAccType = request.getParameter("memberAccType");
		String amount = request.getParameter("amount");

		// Long gatewayAmount =
		// tradeQueryService.tradeAmountQueryForMerchant(Long.valueOf(memberCode));
		Long balance = accountQueryFacadeService.getBalance(
				Long.valueOf(memberCode), Integer.valueOf(memberAccType));
		// if (gatewayAmount == null) {
		// gatewayAmount = 0L;
		// }
		if (balance == null) {
			balance = 0L;
		}
		// Long inBalance = balance -gatewayAmount+Long.valueOf(amount);
		DecimalFormat myformat = new DecimalFormat();
		myformat.applyPattern("##,###.00");
		// String innerHtml = myformat.format(inBalance/1000);
		try {
			response.setCharacterEncoding("UTF-8");
			// response.getWriter().print(innerHtml);
		} catch (Exception e) {
			log.info("获取输出流出错");
		}
		return null;
	}

	/**
	 * 获取入款余额
	 * 
	 * @param memberCode
	 * @return
	 */
	private Long getInBalance(WithdrawAuditDTO withdrawAuditDTO) {
		// Long gatewayAmount =
		// tradeQueryService.tradeAmountQueryForMerchant(withdrawAuditDTO.getMemberCode());
		// Long balance =
		// accountQueryFacadeService.getBalance(withdrawAuditDTO.getMemberCode(),
		// withdrawAuditDTO.getMemberAccType().intValue());
		// if (gatewayAmount == null) {
		// gatewayAmount = 0L;
		// }
		// if (balance == null) {
		// balance = 0L;
		// }
		return 0L;// balance -gatewayAmount+withdrawAuditDTO.getAmount();
	}

	private boolean getIsLoaning(Long memberCode) {
		return accountQueryService.countIsAllowAcctribByMemberCode(memberCode,
				AcctAttribEnum.ALLOW_ADVANCE_MONEY);
	}

	/**
	 * 下一步操作
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView nextStep(HttpServletRequest request,
			HttpServletResponse response) {
		String sequenceIdList = request.getParameter("sequenceList");
		String busiType = request.getParameter("busiType");
		ModelAndView view = new ModelAndView(urlMap.get("nextStep"));

		List<Map<String, String>> fundoutChannelList = fundoutChannelService
				.getFundoutChannel();

		view.addObject("sequenceIdList", sequenceIdList)
				.addObject("fundoutChannelList", fundoutChannelList)
				.addObject("busiType", busiType);
		return view;
	}

	/**
	 * 手工生成批次类型
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView generateBatchFile(HttpServletRequest request,
			HttpServletResponse response) {

		String batchName = request.getParameter("batchName");

		String isSend = request.getParameter("isSend");

		String workorders = request.getParameter("sequenceIdList");

		String withdrawBankCode = request.getParameter("withdrawBankCode");

		// Long busiType = new Long(request.getParameter("busiType"));

		String userName = SessionUserHolderUtil.getLoginId();

		ModelAndView modelAndView = new ModelAndView();
		String viewName = urlMap.get("generatescuess");
		try {
			BatchFileInfo batchFileInfo = batchFileService
					.generateBatchInfoRdTx(workorders, isSend, batchName,
							withdrawBankCode, userName);
			if (batchFileInfo == null) {
				throw new PossException("batchFileInfo is null",
						ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
			} else {
				modelAndView.addObject("batchFileInfo", batchFileInfo);
				modelAndView.setViewName(viewName);
				return modelAndView;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ModelAndView(urlMap.get("init"), DateUtil.getInitTime())
					.addObject("errorMsg", "生成批次文件失败,请重新勾选");
		}
	}

	@Override
	public OperatorlogDTO buildOperatorLog() {
		return null;
	}
	

}
