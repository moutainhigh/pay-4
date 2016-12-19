/**
 *  File: BspWithdrawController.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  Jun 28, 2011   terry ma      create
 */
package com.pay.controller.fo.bsp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.service.MemberRelationQueryService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.acc.service.member.dto.BankCardBindBO;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.app.base.api.annotation.OperatorPermission;
import com.pay.app.base.api.wrapper.SafeControllerWrapper;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.fo.order.common.BankAcctType;
import com.pay.fo.order.common.OrderSmallType;
import com.pay.fo.order.common.OrderStatus;
import com.pay.fo.order.common.OrderType;
import com.pay.fo.order.common.TradeType;
import com.pay.fo.order.dto.audit.WorkOrderDto;
import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.service.bspwithdraw.BSPWithdrawOrderService;
import com.pay.fo.order.service.validate.PaymentValidateService;
import com.pay.fundout.util.AmountUtils;
import com.pay.util.AmountUtil;
import com.pay.util.StringUtil;

public class BspWithdrawController extends BspWithdrawCommonController {

	private String toview;
	private String confirmView;
	private String successView;
	protected MemberRelationQueryService memberRelationQueryService;

	private BSPWithdrawOrderService bspWithdrawOrderService;

	private PaymentValidateService paymentValidateService;

	public void setToview(String toview) {
		this.toview = toview;
	}

	public void setConfirmView(String confirmView) {
		this.confirmView = confirmView;
	}

	public void setSuccessView(String successView) {
		this.successView = successView;
	}

	public void setMemberRelationQueryService(
			MemberRelationQueryService memberRelationQueryService) {
		this.memberRelationQueryService = memberRelationQueryService;
	}

	@OperatorPermission(operatPermission = "OPERATOR_FO_WITHDRAW")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String targetBankId = request.getParameter("targetBankId");
		String withdrawAmount = request.getParameter("withdrawAmount");
		// 获取用户登录信息
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		Map paraMap = new HashMap();
		setParameter(loginSession, paraMap);

		if (null != targetBankId) {
			paraMap.put("targetBankId", targetBankId);
		}
		if (null != withdrawAmount) {
			paraMap.put("withdrawAmount", withdrawAmount);
		}

		List<BankCardBindBO> bankList = (List<BankCardBindBO>) paraMap
				.get("bankList");
		if (null == bankList || bankList.isEmpty()) {
			paraMap.put("errorMsg", getErrorMsg("nobindingbankcard"));
		}
		// 获取银行名称及卡号屏毕处理
		transferBankNo(bankList);
		return new ModelAndView(toview, paraMap);
	}

	/**
	 * 出金确认
	 * 
	 * @param request
	 * @param response
	 * @param bspCommand
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "OPERATOR_FO_WITHDRAW")
	public ModelAndView confirm(HttpServletRequest request,
			HttpServletResponse response, BspWithdrawCommand bspCommand)
			throws Exception {

		// 获取用户登录信息
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		String memberCode = loginSession.getMemberCode();
		Long availableBalance = getavailableBalance(Long.valueOf(memberCode));
		String amount = bspCommand.getWithdrawAmount();
		Long withdrawBankSequenceId = bspCommand.getWithdrawBankSequenceId();

		// 验证用户是否绑定选择出款行
		BankCardBindBO bank = getWithdrawBank(memberCode,
				withdrawBankSequenceId);
		if (null == bank) {
			return index(request, response).addObject("errorMsg",
					getErrorMsg("nobindingbankcard"));
		}

		// 验证本次出金金额
		if (null == amount || !AmountUtil.checkAmount(amount)) {
			return index(request, response).addObject("errorMsg",
					getErrorMsg("AmountConvertError"));
		}

		BigDecimal withdrawAmount = new BigDecimal(bspCommand
				.getWithdrawAmount());
		if (withdrawAmount.compareTo(new BigDecimal(0)) <= 0) {
			return index(request, response).addObject("errorMsg",
					getErrorMsg("AmountConvertError"));
		}

		BigDecimal balance = new BigDecimal(availableBalance)
				.divide(new BigDecimal(1000));
		if (withdrawAmount.compareTo(balance) == 1) {
			return index(request, response).addObject("errorMsg",
					getErrorMsg("noEnoughAmount"));
		}

		// 验证是否支持
		if (!isValidChannel(String.valueOf(bank.getBankId()))) {
			return index(request, response).addObject("errorMsg",
					getErrorMsg("bankChannelNotExists"));
		}

		request.getSession().setAttribute("withdrawBank", bank);
		request.getSession().setAttribute("withdrawAmount", withdrawAmount);
		request.getSession().setAttribute("bspWithdrawFlag", "withdrawConfirm");
		return new ModelAndView(confirmView).addObject("merchantName",
				bspCommand.getMerchantName()).addObject("withdrawBank", bank)
				.addObject("bankNo", transferBankNo(bank.getBankAcctId()))
				.addObject("bankName", bspCommand.getBankName()).addObject(
						"withdrawAmount", withdrawAmount.toString());
	}

	/**
	 * 确认出金
	 * 
	 * @param request
	 * @param response
	 * @param bspCommand
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "OPERATOR_FO_WITHDRAW")
	public ModelAndView submit(HttpServletRequest request,
			HttpServletResponse response, BspWithdrawCommand bspCommand)
			throws Exception {

		// 获取用户登录信息
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		String memberCode = loginSession.getMemberCode();
		String loginName = loginSession.getLoginName();
		String operator = loginSession.getOperatorIdentity();
		String platformName = loginSession.getUsteelName();
		BigDecimal withdrawAmount = (BigDecimal) request.getSession()
				.getAttribute("withdrawAmount");

		// 防刷新
		synchronized (loginSession.getMemberCode()) {
			String token = (String) request.getSession().getAttribute(
					"bspWithdrawFlag");
			if (null == token || !"withdrawConfirm".equals(token)) {

				return index(request, response).addObject("errorMsg",
						"不允许重复提交！");
			}
			request.getSession().removeAttribute("bspWithdrawFlag");
		}

		BankCardBindBO bank = (BankCardBindBO) request.getSession()
				.getAttribute("withdrawBank");
		SafeControllerWrapper safeControllerWrapper = new SafeControllerWrapper(
				request, new String[] { "payPwd" });
		String payPwd = safeControllerWrapper.getParameter("payPwd");

		String checkPayPwd = paymentValidateService.validatePaymentPassword(
				Long.valueOf(memberCode), AcctTypeEnum.BASIC_CNY.getCode(),
				loginSession.getOperatorId(), payPwd);

		Map paraMap = new HashMap();
		paraMap.put("merchantName", bspCommand.getMerchantName());
		paraMap.put("withdrawBank", bank);
		paraMap.put("bankNo", transferBankNo(bank.getBankAcctId()));
		paraMap.put("bankName", bspCommand.getBankName());
		paraMap.put("withdrawAmount", withdrawAmount.toString());

		if (!StringUtil.isEmpty(checkPayPwd)) {
			paraMap.put("errorMsg", checkPayPwd);
			request.getSession().setAttribute("bspWithdrawFlag",
					"withdrawConfirm");
			return new ModelAndView(confirmView, paraMap);
		}
		FundoutOrderDTO order = buildOrder(bank, memberCode, loginName,
				withdrawAmount);
		WorkOrderDto workorder = buildWorkOrder(memberCode, operator,
				platformName);
		// 存储订单，更新余额并更新订单状态
		try {
			bspWithdrawOrderService.createOrder(order, workorder);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			return index(request, response).addObject("errorMsg", "出金失败");

		}

		request.getSession().removeAttribute("withdrawAmount");
		paraMap.put("sequenceId", order.getOrderId());
		paraMap.put("create_date", order.getCreateDate());
		return new ModelAndView(successView, paraMap);
	}

	private boolean isValidChannel(String targetBankId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("targetBankId", targetBankId);// 目的银行编号
		map.put("foMode", "1");// 出款方式:暂时为1手工,以后直连接入在进行修改
		map.put("fobusiness", String.valueOf(0));// 出款业务:出金0
		String outBankCode = configBankService.queryFundOutBank2Withdraw(map);
		return !StringUtil.isEmpty(outBankCode);
	}

	private WorkOrderDto buildWorkOrder(String memberCode, String operator,
			String usTeelName) throws Exception {
		WorkOrderDto workOrder = new WorkOrderDto();

		List<MemberInfoDto> memberInfoDtos = memberRelationQueryService
				.selectMemberRelationListByMember(Long.valueOf(memberCode), 2L);
		MemberInfoDto memberInfoDto = memberInfoDtos.get(0);
		workOrder.setAuditMembercode(memberInfoDto.getMemberCode());
		workOrder.setCreateDate(new Date());
		workOrder.setCreateMembercode(Long.valueOf(memberCode));
		workOrder.setCreateOperator(operator);
		workOrder.setOrderType(OrderType.WITHDRAW.getValue());
		workOrder.setStatus(0);
		workOrder.setExternalInfo(usTeelName);
		return workOrder;
	}

	private FundoutOrderDTO buildOrder(BankCardBindBO bank, String memberCode,
			String loginName, BigDecimal withdrawAmount) throws Exception {
		FundoutOrderDTO order = new FundoutOrderDTO();

		long withdrawAmountPerCent = withdrawAmount.multiply(
				new BigDecimal(1000)).longValue();

		order.setOrderType(OrderType.WITHDRAW.getValue());
		order.setOrderSmallType(OrderSmallType.BSP_WITHDRAW.getValue());
		order.setOrderStatus(OrderStatus.INIT.getValue());
		order.setOrderAmount(withdrawAmountPerCent);
		order.setIsPayerPayFee(1);
		order.setFee(0L);
		order.setRealoutAmount(withdrawAmountPerCent);
		order.setRealpayAmount(withdrawAmountPerCent + order.getFee());

		MemberInfoDto memberInfoDto = getMemberInfo(loginName);
		order.setPayerMemberCode(Long.valueOf(memberCode));// memberCode
		AcctAttribDto acctAttribDto = getAcctattrib(memberCode);
		order.setPayerAcctCode(acctAttribDto.getAcctCode().toString());
		order.setPayerMemberType(memberInfoDto.getMemberType());
		order.setPayerAcctType(AcctTypeEnum.BASIC_CNY.getCode());
		order.setPayerLoginName(loginName);
		order.setPayerName(memberInfoDto.getMemberName());

		order.setPayeeName(memberInfoDto.getMemberName());// 收款人姓名,实名认证姓名
		order.setPayeeBankCode(bank.getBankId().toString());
		order.setPayeeBankName(bank.getName());
		order.setPayeeBankCity(bank.getCity().toString());
		// order.setPayeeBankCityName("payeeBankCityName");
		order.setPayeeBankProvince(bank.getProvince().toString());
		// order.setPayeeBankProvinceName("PayeeBankProvinceName");
		order.setPayeeOpeningBankName(bank.getBranchBankName());
		order.setPayeeBankAcctCode(bank.getBankAcctId());// 银行卡号
		order.setPayeeBankAcctType(BankAcctType.DebitCard.getValue());// 默认借记卡0,存折1,信用卡2
		if (memberInfoDto.getMemberType() == MemberTypeEnum.MERCHANT.getCode()) {
			order.setTradeType(TradeType.TO_BUSINESS.getValue());
		} else {
			order.setTradeType(TradeType.TO_INDIVIDUAL.getValue());
		}
		order.setTradeAlias(OrderSmallType.BSP_WITHDRAW.getDesc());
		order.setCreateDate(new Date());
		order.setFundoutMode(1);
		order.setPriority(5);
		return order;
	}

	private void setParameter(LoginSession loginSession, Map paraMap)
			throws Exception {

		String memberCode = loginSession.getMemberCode();
		String loginName = loginSession.getLoginName();
		String platformName = loginSession.getUsteelName();

		MemberInfoDto memberInfoDto = getMemberInfo(loginName);

		paraMap.put("loginName", loginName);
		paraMap.put("merchantName", memberInfoDto.getMemberName());
		paraMap.put("platformName", platformName);

		// 获取可出金余额
		paraMap.put("availableBalances",
				AmountUtils.numberFormat(getavailableBalance(memberInfoDto
						.getMemberCode())));

		List<BankCardBindBO> bankList = getBankList(Long.valueOf(memberCode));
		paraMap.put("bankList", bankList);
	}

	private String transferBankNo(String bankNo) {

		return "****" + bankNo.substring(bankNo.length() - 4);
	}

	/**
	 * @param bspWithdrawOrderService
	 *            the bspWithdrawOrderService to set
	 */
	public void setBspWithdrawOrderService(
			BSPWithdrawOrderService bspWithdrawOrderService) {
		this.bspWithdrawOrderService = bspWithdrawOrderService;
	}

	/**
	 * @param paymentValidateService
	 *            the paymentValidateService to set
	 */
	public void setPaymentValidateService(
			PaymentValidateService paymentValidateService) {
		this.paymentValidateService = paymentValidateService;
	}

}
