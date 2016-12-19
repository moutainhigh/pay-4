/**
 * 
 */
package com.pay.controller.fo.pay2bank;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;
import com.pay.acc.common.MaConstant;
import com.pay.app.base.api.annotation.OperatorPermission;
import com.pay.app.base.api.wrapper.SafeControllerWrapper;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.fo.order.common.CommonlyUsedContactsUtil;
import com.pay.fo.order.common.OrderSmallType;
import com.pay.fo.order.common.OrderStatus;
import com.pay.fo.order.common.OrderType;
import com.pay.fo.order.dto.RecentPayeeDTO;
import com.pay.fo.order.dto.audit.WorkOrderDto;
import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fundout.service.ma.AccountInfo;
import com.pay.fundout.service.ma.MemberInfo;
import com.pay.fundout.util.AmountUtils;
import com.pay.inf.dto.Bank;
import com.pay.lucene.common.util.Provinces;
import com.pay.lucene.dto.SearchParamInfoDTO;
import com.pay.lucene.dto.SearchResultInfoDTO;
import com.pay.pe.dto.AccountingFeeRe;
import com.pay.pe.dto.AccountingFeeRes;
import com.pay.rm.facade.dto.RCLimitResultDTO;
import com.pay.util.SpringControllerUtils;
import com.pay.util.StringUtil;

/**
 * @author NEW
 * 
 */
public class Pay2BankController extends AbstractPay2BankController {

	private String indexView;

	private String confirmView;

	private String successView;

	private String errorView;

	private String recentPayee;

	private CommonlyUsedContactsUtil commonlyUsedContactsService;

	
	
	/**
	 * 常用收款方
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView queryRecentPayee(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		String memberCode = loginSession.getMemberCode();
		ModelAndView view = new ModelAndView(recentPayee);
		RecentPayeeDTO dto = new RecentPayeeDTO();
		dto.setType(1);
		dto.setPayerMembercode(Long.valueOf(memberCode));
		List<RecentPayeeDTO> recentPayeeDTOList = commonlyUsedContactsService.queryCommonlyUsedContacts(dto);
		return view.addObject("recentPayeeDTOList", recentPayeeDTOList);
	}
	
	/**
	 * 删除常用收款方
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView deleteRecentPayee(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String contactsId = request.getParameter("contactsId");
		boolean bl = false;
		if(StringUtils.isNotEmpty(contactsId) && StringUtils.isNumeric(contactsId)){
			bl = commonlyUsedContactsService.deleteCommonlyUsedContacts(Long.valueOf(contactsId));
		}
		if(bl){
			SpringControllerUtils.renderText(response, "success");
		}
		return null;
	}
	
	
	
	/**
	 * 请求单笔付款到银行
	 * 
	 * @param request
	 * @param response
	 * @param command
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "OPERATOR_FO_PAY2BANK")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response, Pay2BankCommand command)
			throws Exception {
		ModelAndView view = new ModelAndView(indexView);
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		long memberCode = Long.valueOf(loginSession.getMemberCode());
		command.setPayerMemberCode(memberCode);
		command.setPayerMemberType(loginSession.getMemberType());

		String message = command.getFailedReason();
		try {
			// 加载初始化信息
			init(view, command);
			setPay2BankToken(request, command, "request");
			view.addObject("token", StringUtil.null2String(command.getToken()));
		} catch (Exception e) {
			log.error(message, e);
			view.setViewName(errorView);
		}

		return view.addObject("command", command).addObject("message",
				StringUtil.null2String(message));
	}



	/**
	 * 确认付款信息
	 * 
	 * @param request
	 * @param response
	 * @param command
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "OPERATOR_FO_PAY2BANK")
	public ModelAndView confirm(HttpServletRequest request,
			HttpServletResponse response, Pay2BankCommand command)
			throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		long memberCode = Long.valueOf(loginSession.getMemberCode());
		command.setPayerMemberCode(memberCode);
		command.setPayerMemberType(loginSession.getMemberType());

		synchronized (loginSession.getMemberCode()) {
			ModelAndView view = new ModelAndView(confirmView);
			try {

				// 验证Token
				if (!Pay2BankValidateUtils.validateToken(request)) {
					return index(request, response, command);
				}

				// 是否是返回修改请求
				String back = request.getParameter("back");
				if (!StringUtil.isNull(back)) {
					command = getCommand(request);
					return index(request, response, command);
				}

				// 验证请求信息
				String message = Pay2BankValidateUtils
						.validateInputInfo(command);
				if (!StringUtil.isNull(message)) {
					command.setFailedReason(message);
					return index(request, response, command);
				}

				boolean isHaveProduct = memberProductService.isHaveProduct(
						memberCode, "AUDIT_PAY2BANK");

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
				caculateFee(command, loginSession.getMemberCode());

				// 验证付款方会员状态
				message = paymentValidateService
						.validatePayerMemberInfo(memberCode);

				// 验证付款金额
				if (StringUtil.isNull(message)) {
					message = paymentValidateService.validatePayerBanlance(
							command.getOrderAmount(), command
									.getIsPayerPayFee(), command.getFee(),
							memberCode, command.getPayerAcctType());
				}

				// 验证付款到银行收款方基本信息
				if (StringUtil.isNull(message)) {
					message = paymentValidateService.validatePayeeBankAcctInfo(
							command.getPayeeBankName(), command
									.getPayeeBankAcctCode(), String
									.valueOf(command.getPayeeBankCode()),
							command.getOrderType(), command.getFundoutMode());
				}

				// 验证风控限额限次数据
				if (StringUtil.isNull(message)) {
					message = pay2BankOrderValidateService
							.validateRCLimitInfo(memberCode, command
									.getOrderAmount(), rcLimitResult);
				}

				if (!StringUtil.isNull(message)) {
					List<Bank> bankList = bankService.getWithdrawBanks();
					view.addObject("bankList", bankList);
					view.setViewName(indexView);
					view.addObject("message", message);
					setPay2BankToken(request, command, "request");
				} else {
					cachedCommand(request, command);
					setPay2BankToken(request, command, "confirm");
				}
				view.addObject("token", StringUtil.null2String(command
						.getToken()));
			} catch (Exception e) {
				log.error(e);
				view.setViewName(errorView);
			}
			return view.addObject("command", command);
		}
	}

	/**
	 * 处理付款申请
	 * 
	 * @param request
	 * @param response
	 * @param command
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "OPERATOR_FO_PAY2BANK")
	public ModelAndView pay(HttpServletRequest request,
			HttpServletResponse response, Pay2BankCommand command)
			throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		long memberCode = Long.valueOf(loginSession.getMemberCode());
		command.setPayerMemberCode(memberCode);
		command.setPayerMemberType(loginSession.getMemberType());

		synchronized (loginSession.getMemberCode()) {
			ModelAndView view = new ModelAndView(successView);

			boolean isHaveProduct = memberProductService.isHaveProduct(
					memberCode, "AUDIT_PAY2BANK");

			if (isHaveProduct) {
				command.setIsHaveProduct(1);
			} else {
				command.setIsHaveProduct(0);
			}

			try {
				// 验证Token
				if (!Pay2BankValidateUtils.validateToken(request)) {
					return index(request, response, command);
				}

				command = getCommand(request);

				// 验证请求信息
				String message = Pay2BankValidateUtils
						.validateInputInfo(command);
				if (!StringUtil.isNull(message)) {
					command.setFailedReason(message);
					return index(request, response, command);
				}

				// 验证支付密码
				SafeControllerWrapper safeControllerWrapper = new SafeControllerWrapper(
						request, new String[] { "paymentPwd" });
				String paymentPwd = safeControllerWrapper
						.getParameter("paymentPwd");
				message = paymentValidateService.validatePaymentPassword(
						memberCode, command.getPayerAcctType(), loginSession
								.getOperatorId(), paymentPwd);
				if (!StringUtil.isNull(message)) {
					view.setViewName(confirmView);
					view.addObject("validateMsg", StringUtil
							.null2String(message));
					setPay2BankToken(request, command, "confirm");
					view.addObject("token", StringUtil.null2String(command
							.getToken()));
					return view.addObject("command", command);
				}

				// 加载余额
				loadBalance(command);

				// 获取风控限额限次数据
				RCLimitResultDTO rcLimitResult = initLimitAmount(command);

				// 计算手续费
				//caculateFee(command, loginSession.getMemberCode());

				// 验证付款方会员状态
				message = paymentValidateService
						.validatePayerMemberInfo(memberCode);

				// 验证付款方账户状态
				if (StringUtil.isNull(message)) {
					message = paymentValidateService.validatePayerAcctInfo(
							memberCode, command.getPayerAcctType());
				}

				// 验证付款金额
				if (StringUtil.isNull(message)) {
					message = paymentValidateService.validatePayerBanlance(
							command.getOrderAmount(), command
									.getIsPayerPayFee(), command.getFee(),
							memberCode, command.getPayerAcctType());
				}

				// 验证付款到银行收款方基本信息
				if (StringUtil.isNull(message)) {
					message = paymentValidateService.validatePayeeBankAcctInfo(
							command.getPayeeBankName(), command
									.getPayeeBankAcctCode(), String
									.valueOf(command.getPayeeBankCode()),
							command.getOrderType(), command.getFundoutMode());
				}

				// 验证风控限额限次数据
				if (StringUtil.isNull(message)) {
					message = pay2BankOrderValidateService
							.validateRCLimitInfo(memberCode, command
									.getOrderAmount(), rcLimitResult);
				}

				if (!StringUtil.isNull(message)) {
					List<Bank> bankList = bankService.getWithdrawBanks();
					view.addObject("bankList", bankList);
					view.setViewName(indexView);
					view.addObject("message", message);
					setPay2BankToken(request, command, "request");
					view.addObject("token", StringUtil.null2String(command
							.getToken()));
				} else {

					// 完善订单信息
					FundoutOrderDTO order = buildOrder(command);
					// 存储订单、更新余额、发送消息
					if (isHaveProduct) {// 如果定义了复核功能，存储工单信息，暂不支付订单
						// 构建复核工单信息
						WorkOrderDto workOrder = new WorkOrderDto();
						workOrder.setCreateDate(order.getCreateDate());
						workOrder.setCreateMembercode(memberCode);
						workOrder.setCreateOperator(loginSession
								.getOperatorIdentity());
						workOrder.setExternalInfo(loginSession.getUsteelName());
						workOrder.setStatus(0);
						pay2BankOrderService.createOrderRdTx(order, workOrder);
					} else {
						pay2BankOrderService.createOrder(order);
					}
					commonlyUsedContactsService.savePay2BankCommonlyUsedContacts(order);
					command.setOrderId(order.getOrderId());
					removeCommandCache(request);
					removePay2BankToken(request);
				}

			} catch (Exception e) {
				log.error("pay to bank error:",e);
				view.setViewName(errorView);
			}

			return view.addObject("command", command);
		}
	}

	private FundoutOrderDTO buildOrder(Pay2BankCommand command) {
		FundoutOrderDTO order = new FundoutOrderDTO();

		BeanUtils.copyProperties(command, order);
		order.setCreateDate(new Date());

		MemberInfo payer = memberQueryFacadeService.getMemberInfo(command
				.getPayerMemberCode());
		order.setPayerMemberType(payer.getMemberType());
		order.setPayerName(payer.getMemberName());
		order.setPayerLoginName(payer.getLoginName());
		AccountInfo payerAcct = accountQueryFacadeService.getAccountInfo(
				command.getPayerMemberCode(), command.getPayerAcctType());
		order.setPayerAcctCode(payerAcct.getAcctCode());
		order.setTradeAlias(OrderSmallType.COMMON_PAY2BANK.getDesc());
		order.setOrderStatus(OrderStatus.INIT.getValue());
		order.setPriority(5);
		String[] bankinfos = command.getPayeeOpeningBankName().split(",");
		order.setPayeeOpeningBankName(bankinfos[0]);
		order.setBankNumber(bankinfos[1]);
		return order;
	}

	public ModelAndView caculateFee(HttpServletRequest request,
			HttpServletResponse response, Pay2BankCommand command)
			throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		long memberCode = Long.valueOf(loginSession.getMemberCode());
		command.setPayerMemberCode(memberCode);
		command.setPayerMemberType(loginSession.getMemberType());

		JsonObject json = new JsonObject();
		String error = null;

		String requestAmount = StringUtil.null2String(request
				.getParameter("requestAmount"));
		if (!AmountUtils.checkAmount(requestAmount)
				|| Double.parseDouble(requestAmount) <= 0) {
			error = "请求算费参数不正确";
		} else {
			command.setOrderAmount(AmountUtils.toLongAmount(requestAmount));
			caculateFee(command, loginSession.getMemberCode());
			loadBalance(command);
			json.addProperty("feeStr", command.getRequestFee());
			json.addProperty("fee", command.getFee());
			long balance = command.getCurrentBanlance();
			balance = balance - (command.getOrderAmount() + command.getFee());
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

	public ModelAndView queryOpeningBankNameList(HttpServletRequest request,
			HttpServletResponse response, Pay2BankCommand command)
			throws Exception {
		SessionHelper.getLoginSession(request);

		List<SearchResultInfoDTO> results = new ArrayList<SearchResultInfoDTO>();
		StringBuffer sbf = new StringBuffer();

		String bankCode = request.getParameter("bankOrgCode");
		try {

			SearchParamInfoDTO params = new SearchParamInfoDTO();
			params.setResultSize(300);
			params.setBankName(StringUtil
					.null2String(request.getParameter("b")));
			params.setCityName(StringUtil
					.null2String(request.getParameter("c")));
			params.setProvinceName(StringUtil.null2String(request
					.getParameter("p")));
			params
					.setKeyWord(StringUtil.null2String(request
							.getParameter("k")));

			if ("10003001".equals(bankCode)) {
				// get fundout channel

				Map<String, Object> map = new HashMap<String, Object>();
				// 目的银行编号
				map.put("targetBankId", String.valueOf(bankCode));
				// 出款方式
				map.put("foMode", "1");
				// 出款业务
				map.put("fobusiness", OrderType.PAY2BANK.getValue());// 3 付款到银行

				String fundoutBank = configBankService
						.queryFundOutBank2Withdraw(map);
				if ("10003001".equals(fundoutBank)) {
					if (Provinces.SHANGHAI_NAME
							.equals(params.getProvinceName())) {
						params.setType(2);
					} else {
						params.setType(1);
					}
				} else {
					params.setType(1);
				}
			} else {
				params.setType(1);
			}
			results = luceneService.searchBankUnionCodes(params);

			for (SearchResultInfoDTO searchResultInfoDTO : results) {
				sbf.append("<option value='"
						+ searchResultInfoDTO.getBankName() + ","
						+ searchResultInfoDTO.getBankNo() + "'>");
				sbf.append(searchResultInfoDTO.getBankName());
				sbf.append("</option>");
			}
		} catch (Exception e) {
			log.error("call luceneService.searchUnionBankCodeInfo faild", e);
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/hmtl");
		response.getWriter().print(sbf.toString());
		return null;
	}

	/**
	 * 加载初始化信息
	 * 
	 * @param view
	 * @param command
	 * @throws Exception
	 */
	public void init(ModelAndView view, Pay2BankCommand command)
			throws Exception {
		// 获取银行列表
		List<Bank> bankList = bankService.getWithdrawBanks();
		view.addObject("bankList", bankList);
		// 默认选择人民币账户 获取余额
		loadBalance(command);
		// 初始化限额限次信息
		initLimitAmount(command);

	}

	private void setPay2BankToken(HttpServletRequest request,
			Pay2BankCommand command, String step) {
		String token = step + UUID.randomUUID().toString();
		command.setToken(token);
		request.getSession().setAttribute("token_pay2bank", token);
	}

	private void removePay2BankToken(HttpServletRequest request) {
		request.getSession().removeAttribute("token_pay2bank");
	}

	/**
	 * 将请求表单信息存储到Session中
	 * 
	 * @param request
	 * @param command
	 */
	private void cachedCommand(HttpServletRequest request,
			Pay2BankCommand command) {
		request.getSession().setAttribute("Pay2bankCommand", command);
	}

	/**
	 * 删除Session中的表单信息
	 * 
	 * @param request
	 */
	private void removeCommandCache(HttpServletRequest request) {
		request.getSession().removeAttribute("Pay2bankCommand");
	}

	/**
	 * 获取Session中的表单信息
	 * 
	 * @param request
	 * @return
	 */
	private Pay2BankCommand getCommand(HttpServletRequest request) {
		Object obj = request.getSession().getAttribute("Pay2bankCommand");
		if (obj != null) {
			return (Pay2BankCommand) obj;
		}
		return new Pay2BankCommand();
	}

	/**
	 * 付款到银行算费
	 * 
	 * @param command
	 */
	private void caculateFee(Pay2BankCommand command, String memberCode) {

		Long fee = null;
		AccountingFeeRe accountingFeeRe = new AccountingFeeRe();
		accountingFeeRe.setAmount(command.getOrderAmount());
		accountingFeeRe.setPayer(memberCode);
		try {
			AccountingFeeRes accountingFeeRes = accountingService
					.caculateFee(accountingFeeRe);
			// 收款方付费与付款方付费都从payerFee中取值
			fee = accountingFeeRes.getPayerFee();
		} catch (Exception e) {
			log.error("caculateFee error:", e);
		}
		if (fee == null) {
			fee = 0L;
		}
		command.setFee(fee);
		command.setRequestFee(AmountUtils.numberFormat(fee));

		if (command.getIsPayerPayFee() == 1) {
			command.setRealpayAmount(fee + command.getOrderAmount());
			command.setRealoutAmount(command.getOrderAmount());
		} else {
			command.setRealpayAmount(command.getOrderAmount());
			command.setRealoutAmount(command.getOrderAmount() - fee);
		}

		command.setRealoutAmountStr(AmountUtils.numberFormat(command
				.getRealoutAmount()));
		command.setRealpayAmountStr(AmountUtils.numberFormat(command
				.getRealpayAmount()));

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

	public void setCommonlyUsedContactsService(CommonlyUsedContactsUtil commonlyUsedContactsService) {
		this.commonlyUsedContactsService = commonlyUsedContactsService;
	}

	public void setRecentPayee(String recentPayee) {
		this.recentPayee = recentPayee;
	}
	
	/**
	 * 检查对应的渠道提现是否超过单笔最大额度
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView checkFundAmountLimit(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String bankCode = request.getParameter("bankCode");
		BigDecimal requestAmount = new BigDecimal(request.getParameter("requestAmount"));
		String type = request.getParameter("tradeType");
		String city = request.getParameter("city");
		String realType = "";
		if(type.equals("0")){
			realType = "1";
		}else if(type.equals("1")){
			realType = "2";
		}
		
		String targetCode = bankCode.concat("-").concat(realType);
		// 如果是中国银行对私，需要判断是够同城(上海)
		if("10003001".equals(bankCode) && Integer.parseInt(realType) == MaConstant.MEMBER_TYPE_PERSON){
			// 同城(上海)
			if(Integer.parseInt(city) == 2900){
				targetCode = targetCode.concat("-1");
			}else{
				// 异地
				targetCode = targetCode.concat("-2");
			}
		}
		System.out.println(targetCode);
		
		StringBuilder stringBuilder = new StringBuilder();
		String separator = File.separator;
		stringBuilder.append(separator).append("opt").append(separator).append("pay").append(separator).append("config").append(separator)
		.append("fo").append(separator).append("fundamountlimit.properties");
		FileInputStream fileInputStream = new FileInputStream(stringBuilder.toString());
		Properties properties = new Properties();
		properties.load(fileInputStream);
		
		String overLimit = "";
		if(StringUtils.isNotBlank(properties.getProperty(targetCode))){
			if(new BigDecimal(properties.getProperty(targetCode)).compareTo(requestAmount) == -1){
				overLimit = properties.getProperty(targetCode);
			}
		}
		response.getWriter().print(overLimit);
		response.getWriter().close();
		return null;
	}

}
