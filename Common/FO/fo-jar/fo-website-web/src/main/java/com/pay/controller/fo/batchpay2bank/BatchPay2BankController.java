/**
 * 
 */
package com.pay.controller.fo.batchpay2bank;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.account.dto.BalancesDto;
import com.pay.app.base.api.annotation.OperatorPermission;
import com.pay.app.base.api.wrapper.SafeControllerWrapper;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.fo.order.common.OrderType;
import com.pay.fo.order.dto.batchpayment.BatchPaymentReqBaseInfoDTO;
import com.pay.fundout.service.ma.AccountInfo;
import com.pay.fundout.service.ma.MemberInfo;
import com.pay.fundout.util.AmountUtils;
import com.pay.fundout.util.CheckCodeUtil;
import com.pay.util.DateUtil;
import com.pay.util.StringUtil;

/**
 * @author NEW
 *
 */
public class BatchPay2BankController extends AbstractBatchPay2BankController {
	
	private static final String DATE_FMT = "yyyy-MM-dd";
	
	private static final String DATETIME_FMT = "yyyy-MM-dd HH:mm";
	
	@SuppressWarnings("unused")
	private Integer maxSize;

	private String indexView;
	
	private String applyConfirmView;
	
	private String applySuccessView;
	
	private String errorView;
	
	private AccountQueryService accountQueryService;
	
	/**
	 * 请求批付到银行
	 * @param request
	 * @param response
	 * @param command
	 * @return
	 * @throws Exception
	 */
	//comment by PengJiangbo
	//@OperatorPermission(operatPermission = "FO_MASSPAY2BANK_UPLOAD")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response, BatchPay2BankCommand command)
			throws Exception {
		
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		ModelAndView view = new ModelAndView(indexView);
		command.setPayerMemberCode(Long.parseLong(loginSession.getMemberCode()));
		try{
			
			//初始化数据
			//init(command);
			setBatchPay2BankToken(request, command, "request");
			view.addObject("token", StringUtil.null2String(command.getToken()));
			backIndexView(command, view);
		}catch(Throwable e){
			log.error(e.getMessage(),e);
			view.setViewName(errorView);
		}
		return view.addObject("command", command);
	}

	/**
	 * @param command
	 * @param view
	 */
	private void backIndexView(BatchPay2BankCommand command, ModelAndView view) {
		//查询账户列表，获取可提现余额
		List<BalancesDto> balancesDtos = accountQueryService
				.doQueryBalancesBasicNsTx(command.getPayerMemberCode());
		
		view.addObject("accts", balancesDtos);
	}
	
	 /**
	 * 预览上传信息
	 * @param request
	 * @param response
	 * @param command
	 * @return
	 * @throws Exception
	 */
	//@OperatorPermission(operatPermission = "FO_MASSPAY2BANK_UPLOAD")
	public ModelAndView confirm(HttpServletRequest request,
			HttpServletResponse response, BatchPay2BankCommand command)
			throws Exception {
		
		//command.setPayerAcctType(101);
		String payerAcctType = StringUtil.null2String(request.getParameter("payerAcctTypeTem"));
		String payerCurrencyCode = StringUtil.null2String(request.getParameter("payerCurrencyCodeTem"));
		
		//-----------added by pbj
		command.setPayerAcctType(Integer.parseInt(payerAcctType));
		command.setPayerCurrencyCode(payerCurrencyCode);
		command.setPayeeAcctType(Integer.parseInt(payerAcctType));
		command.setPayeeCurrencyCode(payerCurrencyCode);
		//------------added by pbj end
		
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		long memberCode = Long.valueOf(loginSession.getMemberCode());
		//String businessBatchNo = command.getBusinessBatchNo();
		
		InputStream file = command.getPaymentFile().getInputStream();
		ModelAndView view = new ModelAndView(applyConfirmView);
		command.setPayerMemberCode(memberCode);
		command.setPayerLoginName(loginSession.getLoginName());
		try{
			
			//校验输入信息
			String checkCode = command.getRandCode();
			if (!CheckCodeUtil.check(request, checkCode)) {
				init(command);
				view.setViewName(indexView);
				view.addObject("randError", "验证码错误,请重新输入");
				backIndexView(command, view);
				return view.addObject("command", command);
			}
			
			BatchPaymentReqBaseInfoDTO reqInfo = new BatchPaymentReqBaseInfoDTO();
			
			//--added by  PengJiangbo
			reqInfo.setPayerCurrencyCode(command.getPayerCurrencyCode());
			reqInfo.setPayeeCurrencyCode(command.getPayeeCurrencyCode());
			reqInfo.setPayerAcctType(command.getPayerAcctType());
			reqInfo.setPayeeAcctType(command.getPayeeAcctType());
			//--added by  PengJiangbo end
			
			//装配付款方基本信息
			MemberInfo payer = memberQueryFacadeService.getMemberInfo(memberCode);
			reqInfo.setPayerName(payer.getMemberName());
			reqInfo.setPayerMemberCode(memberCode);
			reqInfo.setPayerMemberType(payer.getMemberType());
			AccountInfo payerAcct = accountQueryFacadeService.getAccountInfo(memberCode, command.getPayerAcctType());
			reqInfo.setPayerAcctCode(payerAcct.getAcctCode());
			reqInfo.setBusinessBatchNo(command.getBusinessBatchNo().trim());
			reqInfo.setPayerAcctType(command.getPayerAcctType());
			reqInfo.setPayerLoginName(loginSession.getLoginName());
			reqInfo.setCreator(loginSession.getOperatorIdentity());
			reqInfo.setCreateDate(new Date());
			reqInfo.setIsPayerPayFee(command.getIsPayerPayFee());
			reqInfo.setProcessType(command.getProcessType());
			reqInfo.setRequestType(OrderType.BATCHPAY2BANK.getValue());
			reqInfo.setRequestSrc("内部批付到银行");
			//batchPay2BankRequestService.validateRequestInfo(reqInfo);
			//设置初始状态
			reqInfo.setStatus(0);
			
			// 解析导入文件信息
			String fileType = request.getParameter("fileType");
			if("xls".equals(fileType)){
				batchPay2BankRequestService.parseRequestInfo(file, reqInfo,1);
			}else if("csv".equals(fileType)){
				batchPay2BankRequestService.parseRequestInfo(file, reqInfo,2);
			}
			
			String message = reqInfo.getErrorMsg();
			
			
			
			
			//校验导入基本及明细信息
			if(StringUtil.isNull(message)){
				
				// 保存请求信息
				// 付款方付费
				reqInfo.setRealpayAmount(reqInfo.getValidAmount().longValue()
						+ reqInfo.getFee().longValue());
				reqInfo.setRealoutAmount(reqInfo.getValidAmount());
				
				batchPay2BankRequestService.saveRequestInfoRnTx(reqInfo);
				
				BeanUtils.copyProperties(reqInfo, command);
				
				command.setRealoutAmountStr(AmountUtils.numberFormat(command.getRealoutAmount()));
				command.setRealpayAmountStr(AmountUtils.numberFormat(command.getRealpayAmount()));
				command.setRequestAmountStr(AmountUtils.numberFormat(command.getRequestAmount()));
				command.setValidAmountStr(AmountUtils.numberFormat(command.getValidAmount()));
				command.setInvalidAmountStr(AmountUtils.numberFormat((command.getRequestAmount())-(command.getValidAmount())));
				command.setRequestFee(AmountUtils.numberFormat(reqInfo.getFee()));
				
				
			}
			
			
			if(!StringUtil.isNull(message)){
				init(command);
				view.setViewName(indexView);
				backIndexView(command, view);
				view.addObject("message", message);
				setBatchPay2BankToken(request, command, "request");
			}else{
				//added  PengJiangbo
				Long balance = loadBalance2(command);
				if(balance < command.getRealpayAmount()){
					view.addObject("currentBalanceLess", "友情提示：抱歉，余额不足，不能出款！") ;
				}
				//added  PengJiangbo
				cachedCommand(request, command);
				setBatchPay2BankToken(request, command, "confirm");
			}
			
			view.addObject("token", StringUtil.null2String(command.getToken()));
		}catch(Throwable e){
			log.error(e.getMessage(),e);
			view.setViewName(errorView);
			backIndexView(command, view) ;
		}
		return view.addObject("command", command);
	}
	
	/**
	 * 确认提交申请
	 * @param request
	 * @param response
	 * @param command
	 * @return
	 * @throws Exception
	 */
	//@OperatorPermission(operatPermission = "FO_MASSPAY2BANK_UPLOAD")
	public ModelAndView pay(HttpServletRequest request,
			HttpServletResponse response, BatchPay2BankCommand command)
			throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		
		long memberCode = Long.valueOf(loginSession.getMemberCode());
		ModelAndView view = new ModelAndView(applySuccessView);
		command.setPayerMemberCode(memberCode);
		command.setPayerLoginName(loginSession.getLoginName());
		try{
			//验证Token
			if(!BatchPay2BankValidateUtils.validateToken(request)){
				return index(request, response, command);
			}
			
			command = getCommand(request);
			
			//验证支付密码
			SafeControllerWrapper safeControllerWrapper = new SafeControllerWrapper(request,new String[]{"passWord"});
			String paymentPwd = safeControllerWrapper.getParameter("passWord");
			String message = paymentValidateService.validatePaymentPassword(memberCode, command.getPayerAcctType(), loginSession.getOperatorId(), paymentPwd);
			
			if(!StringUtil.isNull(message)){
				view.setViewName(applyConfirmView);
				view.addObject("passwordTip", StringUtil.null2String(message));
				setBatchPay2BankToken(request, command, "confirm");
				view.addObject("token", StringUtil.null2String(command.getToken()));
				backIndexView(command, view) ;
				return view.addObject("command",command);
			}
			
			
			// 校验付款方会员信息
			if(StringUtil.isNull(message)){
				message = paymentValidateService.validatePayerMemberInfo(memberCode);
			}
			
			
			//验证付款方账户状态
			if(StringUtil.isNull(message)){
				message = paymentValidateService.validatePayerAcctInfo(memberCode, command.getPayerAcctType());
			}
			
			
			
			//更新请求基本信息状态为待审核状态
			if(StringUtil.isNull(message)){
				
				BatchPaymentReqBaseInfoDTO reqInfo = batchPaymentReqBaseInfoService.getBatchPaymentReqBaseInfo(command.getRequestSeq());
				
				reqInfo.setUpdateDate(new Date());
				reqInfo.setBusinessBatchNo(command.getBusinessBatchNo());
				reqInfo.setProcessType(command.getProcessType());
				//------------added  PengJiangbo 
				reqInfo.setPayerAcctType(command.getPayerAcctType());
				reqInfo.setPayeeAcctType(command.getPayeeAcctType());
				reqInfo.setPayerCurrencyCode(command.getPayerCurrencyCode());
				reqInfo.setPayeeCurrencyCode(command.getPayeeCurrencyCode());
				//------------added  PengJiangbo end 
				//删除了自动批量付款到银行的产品开通判断 -sandy 20120618
			    if(command.getProcessType()!=null && command.getProcessType()==1){
			    	String hour =String.valueOf(command.getExcuteHour());
			    	if(command.getExcuteHour()!=null&&command.getExcuteHour()<10){
			    		hour = "0" + hour;
			    	}
			    	Date excuteDate = DateUtil.strToDate(command.getExcuteDateStr()+" "+hour+":00", DATETIME_FMT);
			    	reqInfo.setExcuteDate(excuteDate);
			    }
				
				boolean isHaveProduct = memberProductService.isHaveProduct(memberCode, com.pay.fo.order.common.FundoutProduct.AUDIT_BATCH2BANK.getCode());
				//添加是否开通复核功能判断，如果未开通复核功能，直接调用复核通过逻辑
				if(isHaveProduct) {
					reqInfo.setStatus(1);
					batchPay2BankRequestService.confirmRequestInfoRdTx(reqInfo);
				}else{
					reqInfo.setAuditor(loginSession.getOperatorIdentity());
					reqInfo.setAuditRemark(command.getAuditRemark());
					reqInfo.setStatus(2);
					batchPay2BankRequestService.auditPassRequestRdTx(reqInfo, 0);
				}
			}
			
			if(!StringUtil.isNull(message)){
				init(command);
				view.setViewName(indexView);
				backIndexView(command, view) ;
				view.addObject("message", message);
				setBatchPay2BankToken(request, command, "request");
			}else{
				
				removeCommandCache(request);
				removeBatchPay2BankToken(request);
			}
			
			view.addObject("token", StringUtil.null2String(command.getToken()));
		}catch(Throwable e){
			log.error(e.getMessage(),e);
			view.setViewName(errorView);
			backIndexView(command, view) ;
		}
		return view.addObject("command", command);
		
	}
	
	/**
	 * 获取起始小时值
	 * @param request
	 * @param response
	 * @param command
	 * @return
	 * @throws Exception
	 */
	//@OperatorPermission(operatPermission = "FO_MASSPAY2BANK_UPLOAD")
	public ModelAndView getStartHour(HttpServletRequest request,
			HttpServletResponse response, BatchPay2BankCommand command)
			throws Exception {
		
		String dateStr = StringUtil.null2String(request.getParameter("dateStr"));
		
		
		Date selectDate = DateUtil.strToDate(dateStr, DATE_FMT);
		
		int startHour = 0;
		
		Date currentDate = new Date();
		if(DateUtil.getYear(selectDate)==DateUtil.getYear(currentDate) && DateUtil.getMonth(selectDate)==DateUtil.getMonth(currentDate)&& DateUtil.getDay(selectDate)==DateUtil.getDay(currentDate)){
			if(DateUtil.getMinute(currentDate)>=0){
				startHour = DateUtil.getHour(currentDate)+1;
			}
		}else if(currentDate.compareTo(selectDate)>0){
			startHour = -1;
		}
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(startHour);
		
		return null;
		
	}
	

	private void init(BatchPay2BankCommand command) throws Exception{
		
		command.setCurrentDate(DateUtil.dateToStr(new Date(), DATE_FMT));
		//默认选择人民币账户 获取余额
		command.setPayerAcctType(101);
		loadBalance(command);
	}
	

	/**
	 * 加载余额
	 * @param command
	 */
	private void loadBalance(BatchPay2BankCommand command){
		
		
		long balance = accountQueryFacadeService.getBalance(command.getPayerMemberCode(), command.getPayerAcctType());
		command.setCurrentBanlance(balance);
		command.setCurrentBanlanceStr(AmountUtils.numberFormat(balance));
		if(AmountUtils.checkAmount(command.getRequestAmountStr())){
			command.setRequestAmount(AmountUtils.toLongAmount(command.getRequestAmountStr()));
		}
	}
	
	private Long loadBalance2(BatchPay2BankCommand command){
		
		long balance = accountQueryFacadeService.getBalance(command.getPayerMemberCode(), command.getPayerAcctType());
		return balance ;
	}
	
	private void setBatchPay2BankToken(HttpServletRequest request,BatchPay2BankCommand command,String step){
		String token = step+UUID.randomUUID().toString();
		command.setToken(token);
		request.getSession().setAttribute("token_batchpay2bank", token);
	}
	
	private void removeBatchPay2BankToken(HttpServletRequest request){
		request.getSession().removeAttribute("token_batchpay2bank");
	}
	
	/**
	 * 将请求表单信息存储到Session中
	 * @param request
	 * @param command
	 */
	private void cachedCommand(HttpServletRequest request,BatchPay2BankCommand command){
		request.getSession().setAttribute("BatchPay2BankCommand", command);
	}
	/**
	 * 删除Session中的表单信息
	 * @param request
	 */
	private void removeCommandCache(HttpServletRequest request){
		request.getSession().removeAttribute("BatchPay2BankCommand");
	}
	
	/**
	 * 获取Session中的表单信息
	 * @param request
	 * @return
	 */
	private BatchPay2BankCommand getCommand(HttpServletRequest request){
		Object obj = request.getSession().getAttribute("BatchPay2BankCommand");
		if(obj!=null){
			return (BatchPay2BankCommand)obj;
		}
		return new BatchPay2BankCommand();
	}


	/**
	 * @param indexView the indexView to set
	 */
	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}



	/**
	 * @param applyConfirmView the applyConfirmView to set
	 */
	public void setApplyConfirmView(String applyConfirmView) {
		this.applyConfirmView = applyConfirmView;
	}


	/**
	 * @param successView the successView to set
	 */
	public void setApplySuccessView(String applySuccessView) {
		this.applySuccessView = applySuccessView;
	}



	/**
	 * @param errorView the errorView to set
	 */
	public void setErrorView(String errorView) {
		this.errorView = errorView;
	}
	
	
	
	
	/**
	 * @param maxSize the maxSize to set
	 */
	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}

	@Override
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		super.initBinder(request, binder);
		//binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}
	
	
}
