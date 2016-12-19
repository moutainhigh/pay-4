/**
 * 
 */
package com.pay.controller.fo.pay2acct;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.app.base.api.annotation.OperatorPermission;
import com.pay.app.base.api.wrapper.SafeControllerWrapper;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.controller.fo.pay2bank.Pay2BankCommand;
import com.pay.fo.order.common.CommonlyUsedContactsUtil;
import com.pay.fo.order.common.OrderSmallType;
import com.pay.fo.order.common.OrderStatus;
import com.pay.fo.order.common.TradeType;
import com.pay.fo.order.dto.RecentPayeeDTO;
import com.pay.fo.order.dto.audit.WorkOrderDto;
import com.pay.fo.order.dto.base.PayToAcctOrderDTO;
import com.pay.fundout.service.ma.AccountInfo;
import com.pay.fundout.service.ma.MemberInfo;
import com.pay.fundout.util.AmountUtils;
import com.pay.pe.dto.AccountingFeeRe;
import com.pay.pe.dto.AccountingFeeRes;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.rm.facade.dto.RCLimitResultDTO;
import com.pay.util.IDContentUtil;
import com.pay.util.SpringControllerUtils;
import com.pay.util.StringUtil;

/**
 * @author NEW
 * 
 */
public class Pay2AcctController extends AbstractPay2AcctController {

	private String indexView;
	private String confirmView;
	private String successView;
	private String errorView;
	private String failView;
	private String recentPayee;
	private CommonlyUsedContactsUtil commonlyUsedContactsService;
	protected AccountingService accountingService; // 算费服务

	public ModelAndView caculateFee(HttpServletRequest request, HttpServletResponse response, Pay2AcctCommand command) throws Exception {

		LoginSession loginSession = SessionHelper.getLoginSession(request);
		long memberCode = Long.valueOf(loginSession.getMemberCode());

		String payeeLoginName = StringUtil.null2String(request.getParameter("payeeLoginName"));
		if(null != payeeLoginName){
			MemberInfo payeeMemberInfo = memberQueryFacadeService.getMemberInfo(payeeLoginName);
			if(null != payeeMemberInfo){
				command.setPayeeMemberCode(payeeMemberInfo.getMemberCode());
			}
		}
		command.setPayerMemberCode(memberCode);
		command.setPayerMemberType(loginSession.getMemberType());

		JsonObject json = new JsonObject();
		String error = null;

		String requestAmount = StringUtil.null2String(request.getParameter("requestAmount"));
		if (!AmountUtils.checkAmount(requestAmount) || Double.parseDouble(requestAmount) <= 0) {
			error = "请求算费参数不正确";
		} else {
			command.setOrderAmount(AmountUtils.toLongAmount(requestAmount));
			caculateFee(command, loginSession.getMemberCode());

			loadBalance(command);
			json.addProperty("feeStr", command.getRequestFee());
			json.addProperty("fee", command.getPayerFee());
			long balance = command.getCurrentBanlance();
			balance = balance - (command.getOrderAmount() + command.getPayerFee());
			json.addProperty("balance", balance);
			json.addProperty("realpayAmountStr", command.getRealpayAmountStr());
			json.addProperty("realoutAmountStr", command.getRealoutAmountStr());
			json.addProperty("realpayAmount", command.getRealpayAmount());
			json.addProperty("realoutAmount", command.getRealoutAmount());
		}

		json.addProperty("error", StringUtil.null2String(error));
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		return null;
	}

	/**
	 * 付款到银行算费
	 * 
	 * @param command
	 */
	private void caculateFee(Pay2AcctCommand command, String memberCode) {

		Long fee = null;
		AccountingFeeRe accountingFeeRe = new AccountingFeeRe();
		accountingFeeRe.setAmount(command.getOrderAmount());
		accountingFeeRe.setPayer(memberCode);
		accountingFeeRe.setPayee(String.valueOf(command.getPayeeMemberCode()));
		try {
			AccountingFeeRes accountingFeeRes = accountingService.caculateFee(accountingFeeRe);
			// 收款方付费与付款方付费都从payerFee中取值
			fee = accountingFeeRes.getPayerFee();
		} catch (Exception e) {
			log.error("caculateFee error:", e);
		}
		if (fee == null) {
			fee = 0L;
		}
		command.setPayerFee(fee);
		command.setRequestFee(AmountUtils.numberFormat(fee));

		if (command.getIsPayerPayFee() == 1) {
			command.setRealpayAmount(fee + command.getOrderAmount());
			command.setRealoutAmount(command.getOrderAmount());
		} else {
			command.setRealpayAmount(command.getOrderAmount());
			command.setRealoutAmount(command.getOrderAmount() - fee);
		}

		command.setRealoutAmountStr(AmountUtils.numberFormat(command.getRealoutAmount()));
		command.setRealpayAmountStr(AmountUtils.numberFormat(command.getRealpayAmount()));
	}

	/**
	 * 加载余额
	 * 
	 * @param command
	 * @param loginSession
	 */
	protected void loadBalance(Pay2BankCommand command) {
		long balance = accountQueryFacadeService.getBalance(command.getPayerMemberCode(), command.getPayerAcctType());
		command.setCurrentBanlance(balance);
		command.setCurrentBanlanceStr(AmountUtils.numberFormat(balance));
		if (AmountUtils.checkAmount(command.getRequestAmount())) {
			command.setOrderAmount(AmountUtils.toLongAmount(command.getRequestAmount()));
		}
	}

	/**
	 * 常用收款方
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView queryRecentPayee(HttpServletRequest request, HttpServletResponse response) throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		String memberCode = loginSession.getMemberCode();
		ModelAndView view = new ModelAndView(recentPayee);
		RecentPayeeDTO dto = new RecentPayeeDTO();
		dto.setType(2);
		dto.setPayerMembercode(Long.valueOf(memberCode));
		List<RecentPayeeDTO> recentPayeeDTOList = commonlyUsedContactsService.queryCommonlyUsedContacts(dto);
		return view.addObject("recentPayeeDTOList", recentPayeeDTOList);
	}

	/**
	 * 删除常用收款方
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView deleteRecentPayee(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String contactsId = request.getParameter("contactsId");
		boolean bl = false;
		if (StringUtils.isNotEmpty(contactsId) && StringUtils.isNumeric(contactsId)) {
			bl = commonlyUsedContactsService.deleteCommonlyUsedContacts(Long.valueOf(contactsId));
		}
		if (bl) {
			SpringControllerUtils.renderText(response, "success");
		}
		return null;
	}

	@OperatorPermission(operatPermission = "OPERATOR_FO_PAY2ACCOUNT")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, Pay2AcctCommand command) throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		ModelAndView view = new ModelAndView(indexView);
		long memberCode = Long.valueOf(loginSession.getMemberCode());
		command.setPayerMemberCode(memberCode);
		command.setPayerMemberType(loginSession.getMemberType());

		String message = command.getFailedReason();
		try {
			// 初始化数据
			init(command);
			setPay2AcctToken(request, command, "request");
			view.addObject("token", StringUtil.null2String(command.getToken()));
		} catch (Throwable e) {
			log.error(message, e);
			view.setViewName(errorView);
		}
		return view.addObject("command", command).addObject("message", StringUtil.null2String(message));
	}

	@OperatorPermission(operatPermission = "OPERATOR_FO_PAY2ACCOUNT")
	public ModelAndView confirm(HttpServletRequest request, HttpServletResponse response, Pay2AcctCommand command) throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		long memberCode = Long.valueOf(loginSession.getMemberCode());
		command.setPayerMemberCode(memberCode);
		command.setPayerMemberType(loginSession.getMemberType());
		
		String payeeLoginName = command.getPayeeLoginName();
		if(null != payeeLoginName){
			MemberInfo payeeMemberInfo = memberQueryFacadeService.getMemberInfo(payeeLoginName);
			if(null != payeeMemberInfo){
				command.setPayeeMemberCode(payeeMemberInfo.getMemberCode());
			}
		}

		synchronized (loginSession.getMemberCode()) {

			ModelAndView view = new ModelAndView(confirmView);

			try {
				// 验证Token
				if (!Pay2AcctValidateUtils.validateToken(request)) {
					return index(request, response, command);
				}

				// 是否是返回修改请求
				String back = request.getParameter("back");
				if (!StringUtil.isNull(back)) {
					command = getCommand(request);
					return index(request, response, command);
				}

				// 验证请求信息
				String message = Pay2AcctValidateUtils.validateInputInfo(command);
				if (!StringUtil.isNull(message)) {
					command.setFailedReason(message);
					return index(request, response, command);
				}

				boolean isHaveProduct = memberProductService.isHaveProduct(memberCode, "AUDIT_PAY2ACCT");
				if (isHaveProduct) {
					command.setIsHaveProduct(1);
				} else {
					command.setIsHaveProduct(0);
				}

				// 加载余额
				loadBalance(command);

				// 获取风控限额限次数据
				RCLimitResultDTO rcLimitResult = initLimitAmount(command);
				
				// 计算手续费
//				if(MemberType.CORPORATION.getValue() == command.getPayerMemberType()){
//				}
				caculateFee(command, loginSession.getMemberCode());

				command.setOrderAmountStr(AmountUtils.numberFormat(command.getOrderAmount()));
				command.setOrderAmount(AmountUtils.toLongAmount(command.getRequestAmount()));

				// 验证付款方会员状态
				message = paymentValidateService.validatePayerMemberInfo(memberCode);

				long fee = (null == command.getPayerFee()) ? 0L :  command.getPayerFee();
				// 验证付款金额
				if (StringUtil.isNull(message)) {
					message = paymentValidateService.validatePayerBanlance(command.getOrderAmount(), 1, fee, memberCode, command.getPayerAcctType());
				}

				// 验证收款方会员信息
				MemberInfo payee = memberQueryFacadeService.getMemberInfo(command.getPayeeLoginName());
				if (payee == null) {
					message = "收款方用户名尚未注册";
				}

				if (StringUtil.isNull(message)) {
					command.setPayeeMemberType(payee.getMemberType());
					command.setPayeeName(payee.getMemberName());
					message = paymentValidateService.validatePayeeMemberInfo(payee, loginSession.getMemberCode());
				}

				// 验证收款方账号信息
				if (StringUtil.isNull(message)) {
					message = paymentValidateService.validatePayeeAcctInfo(payee.getMemberCode(), command.getPayeeAcctType());
				}

				// 验证风控限额限次数据
				if (StringUtil.isNull(message)) {
					message = pay2AcctOrderValidateService.validateRCLimitInfo(memberCode, command.getOrderAmount(), rcLimitResult);
				}

				if (!StringUtil.isNull(message)) {
					view.setViewName(indexView);
					view.addObject("message", message);
					setPay2AcctToken(request, command, "request");
				} else {

					cachedCommand(request, command);
					setPay2AcctToken(request, command, "confirm");
				}

				view.addObject("token", StringUtil.null2String(command.getToken()));
			} catch (Exception e) {
				log.error("确认信息处理失败", e);
				view.setViewName(errorView);
			}

			return view.addObject("command", command);
		}
	}

	@OperatorPermission(operatPermission = "OPERATOR_FO_PAY2ACCOUNT")
	public ModelAndView pay(HttpServletRequest request, HttpServletResponse response, Pay2AcctCommand command) throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		long memberCode = Long.valueOf(loginSession.getMemberCode());
		command.setPayerMemberCode(memberCode);
		command.setPayerMemberType(loginSession.getMemberType());

		synchronized (loginSession.getMemberCode()) {

			ModelAndView view = new ModelAndView(successView);
			try {
				// 验证Token
				if (!Pay2AcctValidateUtils.validateToken(request)) {
					return index(request, response, command);
				}

				command = getCommand(request);

				// 验证请求信息
				String message = Pay2AcctValidateUtils.validateInputInfo(command);
				if (!StringUtil.isNull(message)) {
					command.setFailedReason(message);
					return index(request, response, command);
				}

				boolean isHaveProduct = memberProductService.isHaveProduct(memberCode, "AUDIT_PAY2ACCT");
				if (isHaveProduct) {
					command.setIsHaveProduct(1);
				} else {
					command.setIsHaveProduct(0);
				}
				// 验证支付密码
				SafeControllerWrapper safeControllerWrapper = new SafeControllerWrapper(request, new String[] { "pwd" });
				String paymentPwd = safeControllerWrapper.getParameter("pwd");
				message = paymentValidateService.validatePaymentPassword(memberCode, command.getPayerAcctType(), loginSession.getOperatorId(), paymentPwd);
				if (!StringUtil.isNull(message)) {
					view.setViewName(confirmView);
					view.addObject("passwordTip", StringUtil.null2String(message));
					setPay2AcctToken(request, command, "confirm");
					view.addObject("token", StringUtil.null2String(command.getToken()));
					return view.addObject("command", command);
				}

				// 加载余额
				loadBalance(command);

				// 获取风控限额限次数据
				RCLimitResultDTO rcLimitResult = initLimitAmount(command);

				command.setOrderAmountStr(AmountUtils.numberFormat(command.getOrderAmount()));
				command.setOrderAmount(AmountUtils.toLongAmount(command.getRequestAmount()));

				// 验证付款方会员状态
				message = paymentValidateService.validatePayerMemberInfo(memberCode);

				// 验证付款方账户状态
				if (StringUtil.isNull(message)) {
					message = paymentValidateService.validatePayerAcctInfo(memberCode, command.getPayerAcctType());
				}

				long fee = (null == command.getPayerFee()) ? 0L :  command.getPayerFee();
				// 验证付款金额
				if (StringUtil.isNull(message)) {
					message = paymentValidateService.validatePayerBanlance(command.getOrderAmount(), 1, fee, memberCode, command.getPayerAcctType());
				}

				// 验证收款方会员信息
				MemberInfo payee = memberQueryFacadeService.getMemberInfo(command.getPayeeLoginName());
				if (StringUtil.isNull(payee)) {
					message = "收款方用户名尚未注册";
				}
				if (StringUtil.isNull(message)) {
					command.setPayeeMemberType(payee.getMemberType());
					command.setPayeeName(payee.getMemberName());
					message = paymentValidateService.validatePayeeMemberInfo(payee, loginSession.getMemberCode());
				}

				// 验证收款方账号信息
				if (StringUtil.isNull(message)) {
					message = paymentValidateService.validatePayeeAcctInfo(payee.getMemberCode(), command.getPayeeAcctType());
				}

				// 验证风控限额限次数据
				if (StringUtil.isNull(message)) {
					message = pay2AcctOrderValidateService.validateRCLimitInfo(memberCode, command.getOrderAmount(), rcLimitResult);
				}

				if (!StringUtil.isNull(message)) {
					view.setViewName(indexView);
					view.addObject("message", message);
					setPay2AcctToken(request, command, "request");
					view.addObject("token", StringUtil.null2String(command.getToken()));
				} else {

					// 完善订单信息
					command.setPayeeLoginName(payee.getLoginName());
					command.setPayeeMemberCode(payee.getMemberCode());
					PayToAcctOrderDTO order = buildOrder(command);
					// 存储订单、更新余额、发送消息
					if (isHaveProduct) {// 如果定义了复核功能，存储工单信息，暂不支付订单
						// 构建复核工单信息
						WorkOrderDto workOrder = new WorkOrderDto();
						workOrder.setCreateDate(order.getCreateDate());
						workOrder.setCreateMembercode(memberCode);
						workOrder.setCreateOperator(loginSession.getOperatorIdentity());
						workOrder.setExternalInfo(loginSession.getUsteelName());
						workOrder.setStatus(0);
						pay2AcctOrderService.createOrderRdTx(order, workOrder);
					} else {
						// 存储订单、更新余额、发送消息
						pay2AcctOrderService.createOrder(order);

					}
					commonlyUsedContactsService.savePay2AcctCommonlyUsedContacts(order);
					command.setOrderId(order.getOrderId());
					removeCommandCache(request);
					removePay2AcctToken(request);
				}

			} catch (Exception e) {
				log.error(e);
				view.setViewName(errorView);
			}

			return view.addObject("command", command);
		}
	}

	private PayToAcctOrderDTO buildOrder(Pay2AcctCommand command) {
		PayToAcctOrderDTO order = new PayToAcctOrderDTO();
		BeanUtils.copyProperties(command, order);
		order.setCreateDate(new Date());

		MemberInfo payer = memberQueryFacadeService.getMemberInfo(command.getPayerMemberCode());
		order.setPayerMemberType(payer.getMemberType());
		order.setPayerName(payer.getMemberName());
		order.setPayerLoginName(payer.getLoginName());
		AccountInfo payerAcct = accountQueryFacadeService.getAccountInfo(command.getPayerMemberCode(), command.getPayerAcctType());
		order.setPayerAcctCode(payerAcct.getAcctCode());

		AccountInfo payeeAcct = accountQueryFacadeService.getAccountInfo(command.getPayeeMemberCode(), command.getPayerAcctType());
		order.setPayeeAcctCode(payeeAcct.getAcctCode());

		if (order.getPayeeMemberType() == MemberTypeEnum.MERCHANT.getCode()) {
			order.setTradeType(TradeType.TO_BUSINESS.getValue());
		} else {
			order.setTradeType(TradeType.TO_INDIVIDUAL.getValue());
		}
		order.setTradeAlias(OrderSmallType.COMMON_PAY2ACCT.getDesc());

		order.setOrderStatus(OrderStatus.INIT.getValue());

		return order;
	}

	public String checkPayee(HttpServletRequest request, HttpServletResponse response) throws Exception {

		LoginSession loginSession = SessionHelper.getLoginSession(request);

		String message = "";
		String payeeUserId = request.getParameter("payeeUserId");
		if (!StringUtil.isNull(payeeUserId)) {
			if (IDContentUtil.validateEmail(payeeUserId) || IDContentUtil.validateMobile(payeeUserId)) {
				MemberInfo payee = memberQueryFacadeService.getMemberInfo(payeeUserId);
				// 验证收款方信息
				message = paymentValidateService.validatePayeeMemberInfo(payee, loginSession.getMemberCode());
				if (StringUtil.isNull(message)) {
					message = paymentValidateService.validatePayeeAcctInfo(payee.getMemberCode(), AcctTypeEnum.BASIC_CNY.getCode());
				}
			} else {
				message = "请输入有效的收款方用户名";
			}

		} else {
			message = "请输入收款方用户名";
		}

		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(message == null ? "" : message);
		return null;
	}

	private void init(Pay2AcctCommand command) throws Exception {
		// 默认选择人民币账户 获取余额
		loadBalance(command);
		// 初始化限额限次信息
		initLimitAmount(command);
	}

	/**
	 * 加载风控限额限次信息
	 * 
	 * @param command
	 * @return
	 * @throws Exception
	 */
	private RCLimitResultDTO initLimitAmount(Pay2AcctCommand command) throws Exception {
		Long memberCode = command.getPayerMemberCode();
		RCLimitResultDTO rule = getLimitAmount(memberCode, command.getPayerMemberType());
		command.setSingleLimitAmount(rule.getSingleLimit());
		command.setSingleLimitAmountStr(AmountUtils.numberFormat(rule.getSingleLimit()));

		Long currentDayAmount = payToAcctOrderService.sumCurrentDayPaymentAmount(command.getOrderType(), memberCode);
		Long currentMonthAmount = payToAcctOrderService.sumCurrentMonthPaymentAmount(command.getOrderType(), memberCode);
		Integer monthTimes = payToAcctOrderService.countCurrentMonthPaymentTimes(command.getOrderType(), memberCode);
		Integer dayTimes = payToAcctOrderService.countCurrentDayPaymentTimes(command.getOrderType(), memberCode);

		int monthLimitTimes = rule.getMonthTimes() - monthTimes.intValue();
		int dayLimitTimes = rule.getDayTimes() - dayTimes.intValue();
		int limitTimes = 0;
		if (monthLimitTimes < dayLimitTimes) {
			limitTimes = monthLimitTimes;
		} else {
			limitTimes = dayLimitTimes;
		}

		long monthLimitAmount = rule.getMonthLimit() - currentMonthAmount.longValue();
		long dayLimitAmount = rule.getDayLimit() - currentDayAmount.longValue();
		long allowPaymentAmount = 0L;
		if (monthLimitAmount < dayLimitAmount) {
			allowPaymentAmount = monthLimitAmount;
		} else {
			allowPaymentAmount = dayLimitAmount;
		}
		if (allowPaymentAmount < 0) {
			allowPaymentAmount = 0;
		}
		if (limitTimes < 0) {
			limitTimes = 0;
		}
		command.setAllowPaymentTimes(limitTimes);
		command.setAllowPaymentAmount(allowPaymentAmount);
		command.setAllowPaymentAmountStr(AmountUtils.numberFormat(allowPaymentAmount));
		command.setDayLimitAmount(rule.getDayLimit());
		command.setDayLimitAmountStr(AmountUtils.numberFormat(rule.getDayLimit()));
		command.setMonthLimitAmountStr(AmountUtils.numberFormat(rule.getMonthLimit()));
		command.setMonthLimitAmount(rule.getMonthLimit());
		command.setTodayPaymentAmount(currentDayAmount);
		command.setCurrentMonthPaymentAmount(currentMonthAmount);
		return rule;
	}

	private void setPay2AcctToken(HttpServletRequest request, Pay2AcctCommand command, String step) {
		String token = step + UUID.randomUUID().toString();
		command.setToken(token);
		request.getSession().setAttribute("token_pay2acct", token);
	}

	private void removePay2AcctToken(HttpServletRequest request) {
		request.getSession().removeAttribute("token_pay2acct");
	}

	/**
	 * 将请求表单信息存储到Session中
	 * 
	 * @param request
	 * @param command
	 */
	private void cachedCommand(HttpServletRequest request, Pay2AcctCommand command) {
		request.getSession().setAttribute("Pay2AcctCommand", command);
	}

	/**
	 * 删除Session中的表单信息
	 * 
	 * @param request
	 */
	private void removeCommandCache(HttpServletRequest request) {
		request.getSession().removeAttribute("Pay2AcctCommand");
	}

	/**
	 * 获取Session中的表单信息
	 * 
	 * @param request
	 * @return
	 */
	private Pay2AcctCommand getCommand(HttpServletRequest request) {
		Object obj = request.getSession().getAttribute("Pay2AcctCommand");
		if (obj != null) {
			return (Pay2AcctCommand) obj;
		}
		return new Pay2AcctCommand();
	}

	public void setCommonlyUsedContactsService(CommonlyUsedContactsUtil commonlyUsedContactsService) {
		this.commonlyUsedContactsService = commonlyUsedContactsService;
	}

	/**
	 * @param indexView
	 *            the indexView to set
	 */
	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}

	/**
	 * @param confirmView
	 *            the confirmView to set
	 */
	public void setConfirmView(String confirmView) {
		this.confirmView = confirmView;
	}

	/**
	 * @param successView
	 *            the successView to set
	 */
	public void setSuccessView(String successView) {
		this.successView = successView;
	}

	/**
	 * @param errorView
	 *            the errorView to set
	 */
	public void setErrorView(String errorView) {
		this.errorView = errorView;
	}

	/**
	 * @param failView
	 *            the failView to set
	 */
	public void setFailView(String failView) {
		this.failView = failView;
	}

	public void setRecentPayee(String recentPayee) {
		this.recentPayee = recentPayee;
	}

	public void setAccountingService(AccountingService accountingService) {
		this.accountingService = accountingService;
	}

}
