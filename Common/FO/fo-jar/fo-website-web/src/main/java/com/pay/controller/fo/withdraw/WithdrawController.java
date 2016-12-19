/**
 * 
 */ 
package com.pay.controller.fo.withdraw;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;
import com.pay.acc.acct.model.Acct;
import com.pay.acc.acct.service.AcctService;
import com.pay.acc.common.MaConstant;
import com.pay.acc.member.dto.EnterpriseBaseDto;
import com.pay.acc.member.model.WithdrawUnionBatchpayFee;
import com.pay.acc.member.service.EnterpriseBaseService;
import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.acc.service.account.dto.BalancesDto;
import com.pay.app.base.api.annotation.OperatorPermission;
import com.pay.app.base.api.wrapper.SafeControllerWrapper;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.client.CurrencyRateService;
import com.pay.fo.order.common.OrderSmallType;
import com.pay.fo.order.common.OrderStatus;
import com.pay.fo.order.common.OrderType;
import com.pay.fo.order.common.TradeType;
import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fundout.service.ma.AccountInfo;
import com.pay.fundout.service.ma.BankCardBindInfo;
import com.pay.fundout.service.ma.MemberInfo;
import com.pay.fundout.util.AmountUtils;
import com.pay.pe.dto.AccountingFeeRe;
import com.pay.pe.dto.AccountingFeeRes;
import com.pay.rm.facade.dto.RCLimitResultDTO;
import com.pay.rm.facade.util.RCLIMITCODE;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * @author NEW
 *
 */
public class WithdrawController extends AbstractWithdrawController {
	
	private String indexView;
	
	private String confirmView;
	
	private String successView;
	
	private String errorView;
	
	private AccountQueryService accountQueryService;
	
	private EnterpriseBaseService enterpriseBaseService ;
	private CurrencyRateService currencyRateService ;
	private AcctService acctService ;

	/**	
	 * 请求单笔提现
	 * @param request
	 * @param response
	 * @param command
	 * @return
	 * @throws Exception
	 */
	  // 20160423 Mack 修改mps去掉权限控制	@OperatorPermission(operatPermission = "OPERATOR_FO_WITHDRAW")
	 
	public ModelAndView index(final HttpServletRequest request,
			final HttpServletResponse response, final WithdrawCommand command) throws Exception {
		ModelAndView view = new ModelAndView(indexView);
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		EnterpriseBaseDto code = enterpriseBaseService
					.queryEnterpriseBaseByMemberCode(Long.valueOf(loginSession.getMemberCode()));
		command.setPayerMemberCode(new Long(loginSession.getMemberCode()));
		command.setPayerMemberType(loginSession.getMemberType());
		command.setPayerName(code.getZhName());
		String message = command.getFailedReason();
		try{
			//加载初始化信息
			init(view, command);
			setWithdrawToken(request, command, "request");
			view.addObject("token", StringUtil.null2String(command.getToken()));
		}catch(Exception e){
			log.error(message,e);
			view.setViewName(errorView);
		}
		
		return view.addObject("command",command).addObject("message", StringUtil.null2String(message));
	}
	
	/**
	 * 确认付款信息
	 * @param request
	 * @param response
	 * @param command
	 * @return
	 * @throws Exception 
	 */
	 // 20160423 Mack 修改mps去掉权限控制	@OperatorPermission(operatPermission = "OPERATOR_FO_WITHDRAW")
	  
	public ModelAndView confirm(final HttpServletRequest request,
			final HttpServletResponse response, WithdrawCommand command) throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		long memberCode = Long.parseLong(loginSession.getMemberCode());
		command.setPayerMemberCode(memberCode);
		command.setPayerMemberType(loginSession.getMemberType());
		synchronized (loginSession.getMemberCode()) {
			ModelAndView view = new ModelAndView(confirmView);
			try{
				//验证Token
				if(!WithdrawValidateUtils.validateToken(request)){
					return index(request, response, command);
				}
				
				//是否是返回修改请求
				String back = request.getParameter("back");
				if(!StringUtil.isNull(back)){
					command = getCommand(request);
					return index(request, response, command);
				}
				
				// 验证请求信息
				String message = WithdrawValidateUtils.validateInputInfo(command);
				if(!StringUtil.isNull(message)){
					command.setFailedReason(message);
					return index(request, response, command);
				}
				//加载余额
				loadBalance(command);
				
				//获取风控限额限次数据
				RCLimitResultDTO rcLimitResult =  initLimitAmount(command);
				
			
				//计算手续费
				//modified by PengJiangbo 2016-05-10 Start
				message = caculateFee(command,loginSession.getMemberCode());
				
				//验证付款方会员状态
				if(StringUtil.isNull(message)){
					message = paymentValidateService.validatePayerMemberInfo(memberCode);
				}
				//modified by PengJiangbo 2016-05-10 End
				//验证付款金额
				if(StringUtil.isNull(message)){
					message = paymentValidateService.validatePayerBanlance(command.getOrderAmount(), command.getIsPayerPayFee(), command.getFee(), memberCode, command.getPayerAcctType());
				}
				
				//验证提现收款方基本信息
				if(StringUtil.isNull(message)){
					message = paymentValidateService.validatePayeeBankAcctInfo(command.getPayeeBankName(), command.getPayeeBankAcctCode(), String.valueOf(command.getPayeeBankCode()), command.getOrderType(), command.getFundoutMode());
				}
				
				//验证风控限额限次数据
				if(StringUtil.isNull(message)){
					message = withdrawOrderValidateService.validateRCLimitInfo(memberCode, command.getOrderAmount(), rcLimitResult);
				}
				
				if(!StringUtil.isNull(message)){
					List<BankCardBindInfo> bankList = bankCardBindFacadeService.getBankCardBindInfoList(command.getPayerMemberCode());
					view.addObject("bankList",bankList);
					boolean isHaveProduct = memberProductService.isHaveProduct(command.getPayerMemberCode(), "AUTO_WITHDRAW");
					view.addObject("isHaveProduct",isHaveProduct);
					view.setViewName(indexView);
					view.addObject("message", message);
					setWithdrawToken(request, command, "request");
				}else{
					cachedCommand(request, command);
					setWithdrawToken(request, command, "confirm");
				}
				view.addObject("token", StringUtil.null2String(command.getToken()));
			}catch(Exception e){
				log.error("withdraw error:",e);
				view.setViewName(errorView);
			}
			return view.addObject("command",command);
		}
	}
	
	/**
	 * 确认付款信息
	 * @param request
	 * @param response
	 * @param command
	 * @return
	 * @throws Exception
	 */
	   // 20160423 Mack 修改mps去掉权限控制	@OperatorPermission(operatPermission = "OPERATOR_FO_WITHDRAW")
	   
	public ModelAndView pay(final HttpServletRequest request,
			final HttpServletResponse response, WithdrawCommand command) throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		long memberCode = Long.parseLong(loginSession.getMemberCode());
		command.setPayerMemberCode(memberCode);
		command.setPayerMemberType(loginSession.getMemberType());
		synchronized (loginSession.getMemberCode()) {
			ModelAndView view = new ModelAndView(successView);
			try{
				//验证Token
				if(!WithdrawValidateUtils.validateToken(request)){
					return index(request, response, command);
				}
				
				command = getCommand(request);
				
				//验证请求信息
				String message = WithdrawValidateUtils.validateInputInfo(command);
				if(!StringUtil.isNull(message)){
					command.setFailedReason(message);
					return index(request, response, command);
				}
				
				//验证支付密码
				SafeControllerWrapper safeControllerWrapper = new SafeControllerWrapper(request,new String[]{"payPwd"});
				String paymentPwd = safeControllerWrapper.getParameter("payPwd");
				message = paymentValidateService.validatePaymentPassword(memberCode, command.getPayerAcctType(), loginSession.getOperatorId(), paymentPwd);
				if(!StringUtil.isNull(message)){
					view.setViewName(confirmView);
					view.addObject("validateMsg", StringUtil.null2String(message));
					setWithdrawToken(request, command, "confirm");
					view.addObject("token", StringUtil.null2String(command.getToken()));
					return view.addObject("command",command);
				}
				//加载余额
				loadBalance(command);
				
				//获取风控限额限次数据
				RCLimitResultDTO rcLimitResult =  initLimitAmount(command);
				
			
				//计算手续费
				//modified by PengJiangbo 2016-05-10 Start
				message = caculateFee(command,loginSession.getMemberCode());
				
				//验证付款方会员状态
				if(StringUtil.isNull(message)){
					message = paymentValidateService.validatePayerMemberInfo(memberCode);
				}
				//modified by PengJiangbo 2016-05-10 End
				
				//验证付款金额
				if(StringUtil.isNull(message)){
					message = paymentValidateService.validatePayerBanlance(command.getOrderAmount(), command.getIsPayerPayFee(), command.getFee(), memberCode, command.getPayerAcctType());
				}
				
				//验证提现收款方基本信息
				if(StringUtil.isNull(message)){
					message = paymentValidateService.validatePayeeBankAcctInfo(command.getPayeeBankName(), command.getPayeeBankAcctCode(), String.valueOf(command.getPayeeBankCode()), command.getOrderType(), command.getFundoutMode());
				}
				
				//验证风控限额限次数据
				if(StringUtil.isNull(message)){
					message = withdrawOrderValidateService.validateRCLimitInfo(memberCode, command.getOrderAmount(), rcLimitResult);
				}
				
				if(!StringUtil.isNull(message)){
					List<BankCardBindInfo> bankList = bankCardBindFacadeService.getBankCardBindInfoList(command.getPayerMemberCode());
					view.addObject("bankList",bankList);
					boolean isHaveProduct = memberProductService.isHaveProduct(command.getPayerMemberCode(), "AUTO_WITHDRAW");
					view.addObject("isHaveProduct",isHaveProduct);
					view.setViewName(indexView);
					view.addObject("message", message);
					setWithdrawToken(request, command, "request");
				}else{
					//完善订单信息
					FundoutOrderDTO order = buildOrder(command);
					Map rateResultMap = recCurrencyRate_new( command.getCurrencyCode(),"CNY", "1",memberCode+""); //获取商户限额汇率的装换；
					String exchangeRate="1"; //汇率
					if(null != rateResultMap && rateResultMap.containsKey("exchangeRate")){
						//算出费用
						exchangeRate = (String) rateResultMap.get("exchangeRate") ;
					}
					String currencyCode = command.getCurrencyCode();
					if(StringUtils.isNotEmpty(currencyCode)){
						if(currencyCode.equals("CNY")){
							exchangeRate="1";
						}
					}
					order.setFundoutRate(exchangeRate); //提现时增加汇率字段用于计算限额信息   2016年4月28日20:24:50  delin.dong
					//通过 “收款方名称”来判断“交易类型”{0:付款到个人;1:付款到企业"}，在清算出款生成银行文件时要用到，账务部需求。 add by davis.guo at 2016-08-24
					String payeeName = order.getPayeeName();
					if(payeeName != null && payeeName.length()>5 && (payeeName.indexOf("公司")!=-1 || payeeName.indexOf("企业")!=-1 || payeeName.indexOf("集团")!=-1))
					{
						order.setTradeType(1);
					}
					else
					{
						order.setTradeType(0);
					}
					//end davis.guo============================================================================================
					//存储订单、更新余额、发送消息
					withdrawOrderService.createOrder(order);
					command.setOrderId(order.getOrderId());
					
					removeCommandCache(request);
					removeWithdrawToken(request);
				}
				view.addObject("token", StringUtil.null2String(command.getToken()));
			}catch(Exception e){
				log.error("提现发生异常",e);
				view.setViewName(errorView);
			}
			return view.addObject("command",command);
		}
	}
	
	private FundoutOrderDTO buildOrder(WithdrawCommand command) {
		FundoutOrderDTO order = new FundoutOrderDTO();
		Long realpayAmount = command.getRealpayAmount() ;
		command.setCreateDate(new Date());
		BeanUtils.copyProperties(command, order);
		
		
		MemberInfo payer = memberQueryFacadeService.getMemberInfo(command.getPayerMemberCode());
		order.setBalance(command.getCurrentBanlance() - realpayAmount);
		order.setPayerMemberType(payer.getMemberType());
		order.setPayerName(payer.getMemberName());
		order.setPayeeName(command.getPayeeName());
		order.setPayerLoginName(payer.getLoginName());
		if(payer.getMemberType()==MemberTypeEnum.MERCHANT.getCode()){
			order.setTradeType(TradeType.TO_BUSINESS.getValue());
		}else{
			order.setTradeType(TradeType.TO_INDIVIDUAL.getValue());
		}
		order.setTradeAlias(OrderSmallType.COMMON_WITHDRAW.getDesc());
		
		// 增加联行号的引入
		List<BankCardBindInfo> bankList = bankCardBindFacadeService.getBankCardBindInfoList(command.getPayerMemberCode());
		for(BankCardBindInfo info : bankList){
			if(info.getBankAcctId().equals(command.getPayeeBankAcctCode())){
				order.setBankNumber(String.valueOf(info.getBranchBankId()));
				break;
			}
		}
		// 结束修改
		
		AccountInfo payerAcct = accountQueryFacadeService.getAccountInfo(command.getPayerMemberCode(), command.getPayerAcctType());
		order.setPayerAcctCode(payerAcct.getAcctCode());
		
		order.setOrderStatus(OrderStatus.INIT.getValue());
		
		
		return order;
	}

	/**
	 * 查询该银行是否支持提现
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView getWithdrawChannel(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		String targetBankId = request.getParameter("targetBankId");
		//TODO 出款方式:暂时为1手工,以后直连接入在进行修改
		boolean result = paymentValidateService.isAllowBankFundout(targetBankId, OrderType.WITHDRAW.getValue(), 1);
		String supportWithdraw = "1";
		if (!result) {
			supportWithdraw = "0";
		}
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(supportWithdraw);
		} catch (IOException e) {
			log.info("获取输出流出错");
		}
		return null;
	}
	
	public ModelAndView caculateFee(final HttpServletRequest request,
			final HttpServletResponse response, final WithdrawCommand command) throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		command.setPayerMemberCode(Long.valueOf(loginSession.getMemberCode()));
		command.setPayerMemberType(loginSession.getMemberType());
		JsonObject json = new JsonObject();
		String error = null;
		
		String requestAmount = StringUtil.null2String(request.getParameter("requestAmount"));
		//added  PengJiangbo--------------
		String errorMsg = null ;
		boolean isPossSetFee = true ; 
		//added  PengJiangbo--------------
		if(!AmountUtils.checkAmount(requestAmount)||Double.parseDouble(requestAmount)<=0){
			error =  "请求算费参数不正确";
		}else{
			command.setOrderAmount(AmountUtils.toLongAmount(requestAmount));
			errorMsg = caculateFee(command,loginSession.getMemberCode());
			loadBalance(command);
			json.addProperty("feeStr", command.getRequestFee());
			json.addProperty("fee", command.getFee());
			long balance = command.getCurrentBanlance();
			balance = balance - (command.getOrderAmount()+command.getFee());
			json.addProperty("balance", balance);
		}
		if(!StringUtils.isEmpty(errorMsg)){
			isPossSetFee = false ;
			json.addProperty("errorMsg", errorMsg);
		}
		json.addProperty("isPossSetFee", isPossSetFee);
		json.addProperty("error", StringUtil.null2String(error));
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(json.toString());
		return null;
	}
	
	

	/**
	 * 设置提现令牌信息
	 * @param request
	 * @param command
	 * @param string
	 */
	private void setWithdrawToken(final HttpServletRequest request,final WithdrawCommand command,final String step){
		String token = step+UUID.randomUUID().toString();
		command.setToken(token);
		request.getSession().setAttribute("token_withdraw", token);
	}
	
	private void removeWithdrawToken(final HttpServletRequest request){
		request.getSession().removeAttribute("token_withdraw");
	}
	
	/**
	 * 将请求表单信息存储到Session中
	 * @param request
	 * @param command
	 */
	private void cachedCommand(final HttpServletRequest request,final WithdrawCommand command){
		request.getSession().setAttribute("Pay2bankCommand", command);
	}
	/**
	 * 删除Session中的表单信息
	 * @param request
	 */
	private void removeCommandCache(final HttpServletRequest request){
		request.getSession().removeAttribute("Pay2bankCommand");
	}
	
	/**
	 * 获取Session中的表单信息
	 * @param request
	 * @return
	 */
	private WithdrawCommand getCommand(final HttpServletRequest request){
		Object obj = request.getSession().getAttribute("Pay2bankCommand");
		if(obj!=null){
			return (WithdrawCommand)obj;
		}
		return new WithdrawCommand();
	}
	/**
	 * 加载初始化信息
	 * @param view
	 * @param command
	 * @throws Exception 
	 */
	private void init(final ModelAndView view, final WithdrawCommand command) throws Exception {
		List<BankCardBindInfo> bankList = bankCardBindFacadeService.getBankCardBindInfoList(command.getPayerMemberCode());
		view.addObject("bankList",bankList);
		//用户产品开通权限
		boolean isHaveProduct = memberProductService.isHaveProduct(command.getPayerMemberCode(), "AUTO_WITHDRAW");
		view.addObject("isHaveProduct",isHaveProduct);
		//查询账户列表，获取可提现余额
		
		List<BalancesDto> balancesDtos = accountQueryService
				.doQueryBalancesBasicNsTx(command.getPayerMemberCode());
		for (BalancesDto balancesDto : balancesDtos) {
			   String acctName = balancesDto.getAcctName();
			   balancesDto.setAcctName(acctName.replaceAll("基本结算", ""));
		}
		view.addObject("accts", balancesDtos);
		//loadBalance(command);
		//初始化限额限次信息
		initLimitAmount(command);
		
	}
	
	
	/**
	 * 获取可提现余额
	 * @param command
	 */
	private void loadBalance(final WithdrawCommand command) {
		long balance = accountQueryFacadeService.getWithdrawBalance(command.getPayerMemberCode(), command.getPayerAcctType());
		command.setCurrentBanlance(balance);
		command.setCurrentBanlanceStr(AmountUtils.numberFormat(balance));
		if(AmountUtils.checkAmount(command.getRequestAmount())){
			command.setOrderAmount(AmountUtils.toLongAmount(command.getRequestAmount()));
		}
		
	}

	/**
	 * 初始化限额
	 * @param command
	 * @throws Exception 
	 */
	private RCLimitResultDTO initLimitAmount(WithdrawCommand command) throws Exception{
		Long memberCode = command.getPayerMemberCode();
		MemberInfo info = memberQueryFacadeService.getMemberInfo(memberCode);
//		command.setPayeeName(info.getMemberName());
		command.setPayerName(info.getMemberName());
		command.setPayerMemberType(info.getMemberType());
	
		Map rateResultMap = recCurrencyRate_new( command.getCurrencyCode(),"CNY", "1",memberCode+""); //获取商户限额汇率的装换；
		String exchangeRate="1"; //汇率
		if(null != rateResultMap && rateResultMap.containsKey("exchangeRate")){
			//算出费用
			exchangeRate = (String) rateResultMap.get("exchangeRate") ;
		}
		String currencyCode = command.getCurrencyCode();
		//限额人民币不乘汇率
		if(StringUtils.isNotEmpty(currencyCode)){
			if(currencyCode.equals("CNY")){
				exchangeRate="1";
			}
		}
		//查询交易限次限额
		RCLimitResultDTO rule = getLimitAmount(memberCode,info.getMemberType());
		command.setSingleLimitAmount(rule.getSingleLimit());
		command.setSingleLimitAmountStr(AmountUtils.numberFormat(rule.getSingleLimit()));
		//当日交易的金额
		Long currentDayAmount = fundoutOrderService.sumCurrentDayPaymentAmount(command.getOrderType(), memberCode);
		//当月交易的金额
		Long currentMonthAmount = fundoutOrderService.sumCurrentMonthPaymentAmount(command.getOrderType(), memberCode);
		//当月交易的次数
		Integer monthTimes = fundoutOrderService.countCurrentMonthPaymentTimes(command.getOrderType(), memberCode);
		//当日交易的次数
		Integer dayTimes = fundoutOrderService.countCurrentDayPaymentTimes(command.getOrderType(), memberCode);
		//当月可交易次数                       //（每月限定次数）    -   （当月已交易次数） 
		int monthLimitTimes = rule.getMonthTimes() - monthTimes.intValue();
		//当日可交易次数		//（每天限定次数）   -  （当日已交易次数）
		int dayLimitTimes = rule.getDayTimes() - dayTimes.intValue();
			//当前可交易次数	
		int limitTimes = 0;
		//当每月交易次数 小于每天交易次数  当前交易次数为 每月交易次数 
		if(monthLimitTimes < dayLimitTimes){
			limitTimes = monthLimitTimes;
		}else{ 
		//当每月交易次数大于每天交易次数 则 去每日交易次数
			limitTimes =  dayLimitTimes;
		}
		//       当月可交易金额      =    ((  每月限额    -    当月交易的金额)/汇率）)                    
		BigDecimal monthAmount = new BigDecimal(rule.getMonthLimit()-currentMonthAmount).divide(new BigDecimal(exchangeRate) ,2, BigDecimal.ROUND_HALF_UP);
		long monthLimitAmount = monthAmount.longValue();
		
		//    当日可交易金额         =        ((每日限额- 每日交易金额)/汇率)   修改限额计算方式 delin.dong 2016年5月5日 11:23:27   
		BigDecimal dayAmount=new BigDecimal(rule.getDayLimit()-currentDayAmount).divide(new BigDecimal(exchangeRate),2, BigDecimal.ROUND_HALF_UP);
		long dayLimitAmount = dayAmount.longValue();
		
		long allowPaymentAmount = 0L;
		if(monthLimitAmount < dayLimitAmount){
			allowPaymentAmount = monthLimitAmount;
		}else{
			allowPaymentAmount = dayLimitAmount;
		}
		if (allowPaymentAmount < 0) {
			allowPaymentAmount = 0;
		}
		if (limitTimes < 0) {
			limitTimes = 0;
		}
		rule.setDayLimit(dayAmount.longValue()); //解决可提示提现金额和实际可提现金额不符的问题， 2016年4月28日21:43:20 delin.dong
		rule.setMonthLimit(monthAmount.longValue());
		rule.setSingleLimit(new BigDecimal(rule.getSingleLimit()).divide(new BigDecimal(exchangeRate),2, BigDecimal.ROUND_HALF_UP).longValue());
		
		command.setMonthLimitAmount(monthLimitAmount);
		command.setDayLimitAmount(dayLimitAmount);
		command.setDayLimitAmountStr(AmountUtils.numberFormat(dayLimitAmount));
		command.setMonthLimitAmountStr(AmountUtils.numberFormat(monthLimitAmount));
		
		command.setMonthLimitTimes(monthLimitTimes);
		command.setAllowPaymentTimes(limitTimes);
		command.setAllowPaymentAmount(allowPaymentAmount);
		command.setAllowPaymentAmountStr(AmountUtils.numberFormat(allowPaymentAmount));
		command.setTodayPaymentAmount(currentDayAmount);
		command.setCurrentMonthPaymentAmount(currentMonthAmount);
		return rule;
	}
	
	/**
	 * 获取指定规则的限额
	 * @param riskControlRuleType
	 * @return
	 */
	private RCLimitResultDTO getLimitAmount(final Long memberCode,final Integer memberType) throws Exception {
		RCLimitResultDTO rcLimitResultDTO = null;
		if(MemberTypeEnum.MERCHANT.getCode()==memberType){
			rcLimitResultDTO = foRcLimitFacade.getBusinessRcLimit(RCLIMITCODE.FO_ENTERPRISE_WITHDRAW.getKey(), null, memberCode);//企业用户
			
		}else if(MemberTypeEnum.INDIVIDUL.getCode()==memberType){
			rcLimitResultDTO = foRcLimitFacade.getUserRcLimit(RCLIMITCODE.FO_PERSONAL_WITHDRAW.getKey(), null, memberCode);//个人用户
		}
		if (rcLimitResultDTO != null) {
			return rcLimitResultDTO;
		}
		log.error("未找到指定规则的限额！");
		throw new Exception();
	}
	
	/**
	 * 提现算费
	 * @param command
	 */
	private String caculateFee(final WithdrawCommand command,final String memberCode){
		Long fee = 0L;
			AccountingFeeRe accountingFeeRe = new AccountingFeeRe();
			//modified by PengJiangbo 2016.05.04
			//accountingFeeRe.setAmount(command.getOrderAmount());
			accountingFeeRe.setAmount(null == command.getOrderAmount()? 0L : command.getOrderAmount());
			//modified by PengJiangbo end
			accountingFeeRe.setPayer(memberCode);
			accountingFeeRe.setPayerCurrencyCode(command.getCurrencyCode());
			accountingFeeRe.setPayeeCurrencyCode(command.getCurrencyCode());
			accountingFeeRe.setPayerAcctType(command.getPayerAcctType());
			//accountingFeeRe.setBankCode(command.getPayeeBankCode());
			//---------added by  PengJiangbo
			//String errorMsg = ConfigurationCalcFee(command, memberCode,
			//		accountingFeeRe);
			String errorMsg = ConfigurationCalcFee_new(command, memberCode,
					accountingFeeRe);
			//--------added by  PengJiangbo end
			try {
				AccountingFeeRes accountingFeeRes = accountingService
						.caculateFee(accountingFeeRe);
				// 收款方付费与付款方付费都从payerFee中取值
				fee = accountingFeeRes.getPayerFee();
			} catch (Exception e) {
				log.error("caculateFee error:", e);
			}
			if(fee==null){
				fee = 0L;
			}
		command.setFee(fee);
		command.setRequestFee(AmountUtils.numberFormat(fee));
		
		if(command.getIsPayerPayFee()==1){
			if(command.getOrderAmount()==null){
				command.setRealpayAmount(fee+0);
				command.setRealoutAmount(0L);
			}else{
				command.setRealpayAmount(fee+command.getOrderAmount());
				command.setRealoutAmount(command.getOrderAmount());
			}
		}else{
			if(command.getOrderAmount()==null){
				command.setRealpayAmount(0L);
				command.setRealoutAmount(fee);
			}else{
				command.setRealpayAmount(command.getOrderAmount());
				command.setRealoutAmount(command.getOrderAmount()-fee);
			}
		}
		
		command.setRealoutAmountStr(AmountUtils.numberFormat(command.getRealoutAmount()));
		command.setRealpayAmountStr(AmountUtils.numberFormat(command.getRealpayAmount()));
		return errorMsg ;
	}

	/**
	 * 获取对应配置的提现手续费
	 * @param command
	 * @param memberCode
	 * @param accountingFeeRe
	 * @return
	 */
	private String ConfigurationCalcFee_new(final WithdrawCommand command, final String memberCode, final AccountingFeeRe accountingFeeRe){
		String errorMsg = null ;
		//收款方与付款方付费都从payerFee中取值
		Long payerFee = 0L ;
		//获取提现币种
		String withDrawCurrencyCode = command.getCurrencyCode() ;
		//根据提现币种,获取账户提现手续费信息
		Acct withAcct = this.acctService.queryAcctWithFeeByMemberCodeAndCurrencyCode(Long.valueOf(memberCode), withDrawCurrencyCode) ;
		String acctWithDrawCurrencyCode = withAcct.getAcctWithDrawCurrencyCode() ;
		Long acctWithDrawFee = withAcct.getAcctWithDrawFee() ;
		//商户提现账户设置有对应的提现手续费信息
		if(StringUtils.isNotEmpty(acctWithDrawCurrencyCode) && (null != acctWithDrawFee)){
			//提现币种和手续费币种一致时，直接取配置的提现手续费
			if(withDrawCurrencyCode.equals(acctWithDrawCurrencyCode)){
				payerFee = acctWithDrawFee ;
			}else{
				//获取商户当日的清算费率
				Map rateResultMap = this.recCurrencyRate_new(acctWithDrawCurrencyCode, withDrawCurrencyCode, "1", memberCode) ;
				//
				if(null != rateResultMap && rateResultMap.containsKey("exchangeRate")){
					//算出费用
					String exchangeRate = (String) rateResultMap.get("exchangeRate") ;
					if(logger.isInfoEnabled()){
						logger.info("提现手续费，货币币种：" + withDrawCurrencyCode +",目标币种：" + acctWithDrawCurrencyCode + "的汇率为：" +exchangeRate );
					}
					payerFee = (long) (Double.valueOf(exchangeRate) * acctWithDrawFee) ;
					logger.info("提现手续费为(单位：厘)：" + payerFee);
				}else{
					errorMsg = "没有找到汇率，请联系支付运营人员！" ;
					if(logger.isInfoEnabled()){
						logger.info("没有找到对应的清算汇率！");
					}
				}
			}
		}else{
			errorMsg = "未设置提现手续费，请联系支付运营人员！" ;
		}
		accountingFeeRe.setPayerFee(payerFee) ;
		accountingFeeRe.setHasCaculatedPrice(true) ;
		return errorMsg ;
	}

	/**
	 * 取配置算出手续费
	 * @param command
	 * @param memberCode
	 * @param accountingFeeRe
	 * @return
	 */
	@Deprecated
	private String ConfigurationCalcFee(final WithdrawCommand command,
			final String memberCode, final AccountingFeeRe accountingFeeRe) {
		String errorMsg = null ;
		WithdrawUnionBatchpayFee unionFee = this.recFee(Long.valueOf(memberCode)) ;
		String withdrawFeeCurrency = unionFee.getWithdrawFeeCurrency() ;
		Long withdrawFee = unionFee.getWithdrawFee() ;
		String currencyCode = command.getCurrencyCode() ;
		Long payerFee = 0L ;
		if(StringUtils.isNotEmpty(withdrawFeeCurrency) && (null != withdrawFee && 0 != withdrawFee)){
			if(withdrawFeeCurrency.equals(currencyCode)){
				payerFee = withdrawFee ;
			}else{
				List<Map> listRate = this.recCurrencyRate(withdrawFeeCurrency, currencyCode, "1", Long.valueOf(memberCode)) ;
				if(CollectionUtils.isNotEmpty(listRate)){
					Map<String, Object> rateMap = listRate.get(0) ;
					String exchangeRate = (String) rateMap.get("exchangeRate") ;
					if(logger.isInfoEnabled()){
						logger.info("设置币种的手续费(单位：厘)[withdrawFee]为："
								+ unionFee.getWithdrawFee() + "  "
								+ unionFee.getWithdrawFeeCurrency()
								+ ",当前商户有效清算费率[exchangeRate]为:" + exchangeRate);
					}
					payerFee = (long) (Double.valueOf(exchangeRate) * unionFee.getWithdrawFee()) ;
					System.out.println("对应的提现手续费为(单位：厘)：" + payerFee);
				}else{
					errorMsg = "没有找到汇率，请联系支付运营人员！" ;
					if(logger.isInfoEnabled()){
						logger.info("没有找到对应的清算汇率！");
					}
				}
				
			}
		}else{
			errorMsg = "未设置提现手续费，请联系支付运营人员！" ;
		}
		accountingFeeRe.setPayerFee(payerFee) ;
		accountingFeeRe.setHasCaculatedPrice(true);
		return errorMsg;
	}
	
	/**
	 * 检查对应的渠道提现是否超过单笔最大额度
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView checkFundAmountLimit(final HttpServletRequest request, final HttpServletResponse response) throws Exception {

		String bankCode = request.getParameter("bankCode");
		BigDecimal requestAmount = new BigDecimal(request.getParameter("requestAmount"));
		String city = request.getParameter("city");
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		String targetCode = bankCode.concat("-").concat(String.valueOf(loginSession.getMemberType()));
		// 如果是中国银行对私，需要判断是够同城(上海)
		if("10003001".equals(bankCode) && loginSession.getMemberType() == MaConstant.MEMBER_TYPE_PERSON){
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
	
	//获取后台给商户配置的提现手续费
	@Deprecated
	private WithdrawUnionBatchpayFee recFee(final Long memberCode){
		WithdrawUnionBatchpayFee withdrawUnionBatchpayFee = this.enterpriseBaseService.queryWithdrawUnionBatchByCode(memberCode) ;
		return withdrawUnionBatchpayFee ;
	}
	//获取当前商户清算费率配置
	@Deprecated
	private List<Map> recCurrencyRate(final String currency, final String targetCurrency, final String status, final Long memberCode){
		Map<String, Object> paraMap = new HashMap();
		paraMap.put("currency", currency);
		paraMap.put("targetCurrency", targetCurrency);
		/*paraMap.put("effectDate", effectDate);
		paraMap.put("expireDate", expireDate);*/
		paraMap.put("status",status);
		Map<String, Object> rateMap = this.currencyRateService.settlementRateQuery(paraMap) ;
		List<Map> resultList = (List<Map>) rateMap.get("list") ;
		return resultList ;
	}
	/**
	 * 获取商户当前的清算费率配置
	 * @param currency
	 * @param targetCurrency
	 * @param status
	 * @param memberCode
	 * @return
	 */
	private Map recCurrencyRate_new(final String currency, final String targetCurrency, final String status, final String memberCode){
		Map resultMap = null ;
		resultMap = this.currencyRateService.singleSettlementRateQuery(currency, targetCurrency, memberCode, status) ;
		return resultMap ;
	}
	/***
	 * 计算单笔可提现金额
	 * @param command
	 */
	public void caculateWithdrawBanlance(HttpServletRequest request,HttpServletResponse response,WithdrawCommand command){
				
		try {
		LoginSession loginSession = SessionHelper.getLoginSession(request);

		command.setPayerMemberCode(Long.parseLong(loginSession.getMemberCode()));
		
		Map rateResultMap = recCurrencyRate_new(command.getCurrencyCode(),"CNY" , "1", loginSession.getMemberCode()); //获取商户限额汇率的装换；
		String exchangeRate="1"; //汇率
		if(null != rateResultMap && rateResultMap.containsKey("exchangeRate")){
			//算出费用
			exchangeRate = (String) rateResultMap.get("exchangeRate") ;
		}
		String currencyCode = command.getCurrencyCode();
		//限额人民币不乘汇率  2016年4月21日21:15:47 ddl  
		if(StringUtils.isNotEmpty(currencyCode)){
			if(currencyCode.equals("CNY")){
				exchangeRate="1";
			}
		}
		//加载可提现余额
		loadBalance(command);
		//计算手续费
		caculateFee(command,loginSession.getMemberCode());
		//初始化限额限次信息
		initLimitAmount(command);
		//手续费
		Long fee = command.getFee();
			
		// 可提现余额    =  账户余额    - 手续费
		Long withdrawBanlance= command.getCurrentBanlance()-fee;// /汇率
		Long monthLimitAmount=command.getMonthLimitAmount();
		Long dayLimitAmount=command.getDayLimitAmount();
		//单笔限额  = (单笔限额*汇率)	
		BigDecimal singleAmount =new BigDecimal(command.getSingleLimitAmount()).divide(new BigDecimal(exchangeRate),2, BigDecimal.ROUND_HALF_UP);
		Long singleLimitAmount=singleAmount.longValue();
		
		List<Long> asList = Arrays.asList(new Long[]{withdrawBanlance,monthLimitAmount,dayLimitAmount,singleLimitAmount});
		Collections.sort(asList);
		if(asList.get(0)<0L){
			command.setMaximumCashBalance(0L);
		}else{
			command.setMaximumCashBalance(asList.get(0));
		}
		String reqMsg = JSonUtil.toJSonString(command);
		response.getWriter().print(reqMsg);
		} catch (Exception e) {
		
			e.printStackTrace();
		}
/*		command.setWithdrawBanlance(withdrawBanlance);*/
	}
	
	public void setAccountQueryService(final AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}

	public void setEnterpriseBaseService(final EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}

	public void setCurrencyRateService(final CurrencyRateService currencyRateService) {
		this.currencyRateService = currencyRateService;
	}

	public void setAcctService(final AcctService acctService) {
		this.acctService = acctService;
	}
	/**
	 * @param indexView the indexView to set
	 */
	public void setIndexView(final String indexView) {
		this.indexView = indexView;
	}

	/**
	 * @param confirmView the confirmView to set
	 */
	public void setConfirmView(final String confirmView) {
		this.confirmView = confirmView;
	}

	/**
	 * @param successView the successView to set
	 */
	public void setSuccessView(final String successView) {
		this.successView = successView;
	}

	/**
	 * @param errorView the errorView to set
	 */
	public void setErrorView(final String errorView) {
		this.errorView = errorView;
	}

}
