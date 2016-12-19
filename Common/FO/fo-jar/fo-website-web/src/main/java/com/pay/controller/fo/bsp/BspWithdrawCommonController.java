/**
 *  File: BspWithdrawCommonController.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  Jun 29, 2011   terry ma      create
 */
package com.pay.controller.fo.bsp;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.dto.BalancesDto;
import com.pay.acc.service.member.BankCardBindService;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.BankCardBindBO;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.fundout.channel.service.configbank.ConfigBankService;
import com.pay.inf.dto.PageMsgDto;
import com.pay.inf.service.BankService;
import com.pay.inf.service.ValidateMessageService;

public class BspWithdrawCommonController extends MultiActionController {

	private static String pageCode = "withdrawrequest";
	private static String scenarioId = "memberAcctSpecRuleError";
	private ValidateMessageService pageMsgService;
	private BankCardBindService bankCardBindService;
	private AccountQueryService accountQueryService;
	private MemberQueryService memberQueryService;
	private BankService bankService;
	protected ConfigBankService configBankService;

	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}

	public void setConfigBankService(ConfigBankService configBankService) {
		this.configBankService = configBankService;
	}

	public ValidateMessageService getPageMsgService() {
		return pageMsgService;
	}

	public void setPageMsgService(ValidateMessageService pageMsgService) {
		this.pageMsgService = pageMsgService;
	}

	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

	public void setBankCardBindService(BankCardBindService bankCardBindService) {
		this.bankCardBindService = bankCardBindService;
	}

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	/**
	 * 查询该银行是否支持提现
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView getWithdrawChannel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String targetBankId = request.getParameter("targetBankId");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("targetBankId", targetBankId);// 目的银行编号
		map.put("foMode", "1");// 出款方式:暂时为1手工,以后直连接入在进行修改
		map.put("fobusiness", String.valueOf(0));// 出款业务:提现0
		String outBankCode = configBankService.queryFundOutBank2Withdraw(map);
		String supportWithdraw = "1";
		if (StringUtils.isEmpty(outBankCode)) {
			supportWithdraw = "0";
		}
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(supportWithdraw);
		} catch (IOException e) {
			logger.info("获取输出流出错");
		}
		return null;
	}

	protected List<BankCardBindBO> getBankList(Long memberCode)
			throws Exception {
		// 获取银行卡列表
		List<BankCardBindBO> bankList = bankCardBindService
				.doQueryBankCardBindInfoForVerifyNsTx(memberCode);
		return bankList;
	}

	protected BankCardBindBO getWithdrawBank(String memberCode,
			final Long withdrawBankSequenceId) throws Exception {

		if (null == withdrawBankSequenceId) {
			return null;
		}
		List<BankCardBindBO> bankList = getBankList(Long.valueOf(memberCode));
		for (BankCardBindBO bank : bankList) {
			if (bank.getId().longValue() == withdrawBankSequenceId.longValue()) {
				return bank;
			}
		}
		return null;
	}

	protected Long getavailableBalance(Long memberCode) throws Exception {
		// 获取可提现余额
		BalancesDto balancesDto = accountQueryService.doQueryBalancesNsTx(
				Long.valueOf(memberCode), AcctTypeEnum.BASIC_CNY.getCode());
		Long availableBalances = balancesDto.getWithdrawBalance();
		return availableBalances;
	}

	protected void transferBankNo(List<BankCardBindBO> bankList) {
		for (BankCardBindBO bank : bankList) {
			String bankName = bankService.getBankById(bank.getBankId().toString());
			bank.setName(bankName);
			bank.setBankAcctId("****"
					+ bank.getBankAcctId().substring(
							bank.getBankAcctId().length() - 4));
		}
	}

	protected String getErrorMsg(String key) {
		Map<String, PageMsgDto> pageMsgMap = pageMsgService
				.getPageMsgByPagecodeAndScenarioId(pageCode, scenarioId);
		PageMsgDto pageMsgDto = pageMsgMap.get(key);
		return pageMsgDto.getMsg();
	}

	protected AcctAttribDto getAcctattrib(String memberCode) throws Exception {
		AcctAttribDto acctAttribDto = accountQueryService
				.doQueryAcctAttribNsTx(Long.valueOf(memberCode),
						AcctTypeEnum.BASIC_CNY.getCode());
		return acctAttribDto;
	}

	protected MemberInfoDto getMemberInfo(String loginName) throws Exception {
		MemberInfoDto memberInfoDto = memberQueryService.doQueryMemberInfoNsTx(
				loginName, null, null, null);
		return memberInfoDto;
	}

}
