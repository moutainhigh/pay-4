/**
 *  File: MassPaytoBankServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-9      Jason_wang      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.masspaytobank.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acct.dto.AcctDto;
import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.exception.MaAccountQueryUntxException;
import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.acc.service.account.dto.BalancesDto;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.fundout.service.OrderStatus;
import com.pay.fundout.withdraw.dto.WithdrawOrderAppDTO;
import com.pay.fundout.withdraw.dto.masspaytobank.MassPaytobankImportBaseDTO;
import com.pay.fundout.withdraw.dto.masspaytobank.MassPaytobankImportDetailDTO;
import com.pay.fundout.withdraw.dto.masspaytobank.MassPaytobankOrderDTO;
import com.pay.fundout.withdraw.model.masspaytobank.MassPaytobankOrderTotalInfo;
import com.pay.fundout.withdraw.service.masspaytobank.MassPaytoBankService;
import com.pay.fundout.withdraw.service.masspaytobank.MassPaytobankImportBaseService;
import com.pay.fundout.withdraw.service.masspaytobank.MassPaytobankImportDetailService;
import com.pay.fundout.withdraw.service.masspaytobank.MassPaytobankOrderService;
import com.pay.fundout.withdraw.service.order.WithdrawOrderBusiType;
import com.pay.fundout.withdraw.service.order.WithdrawOrderService;
import com.pay.inf.dto.PageMsgDto;
import com.pay.inf.exception.AppException;
import com.pay.inf.service.ValidateMessageService;
import com.pay.jms.notification.request.RequestType;
import com.pay.pe.dto.AccountingDto;
import com.pay.pe.dto.AccountingFeeRe;
import com.pay.pe.dto.AccountingFeeRes;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.base.common.Constants;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.poss.common.accounting.WithdrawBusinessType;
import com.pay.poss.dto.withdraw.notify.Notify2QueueRequest;
import com.pay.poss.dto.withdraw.notify.NotifyTargetRequest;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.external.service.ordercallback.OrderCallBackService;
import com.pay.poss.service.ma.batchpaytoaccount.MassPayService;
import com.pay.poss.service.withdraw.inf.output.NotifyFacadeService;
import com.pay.poss.service.withdraw.orderafterproc.OrderAfterProcService;
import com.pay.rm.FoRcLimitFacade;
import com.pay.rm.facade.dto.RCLimitResultDTO;
import com.pay.rm.facade.util.RCLIMITCODE;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * 
 * @author Sean_yi
 * @createtime 2010-12-20
 * @filename MassPaytoBankServiceImpl.java
 * @version 1.0
 */
public class MassPaytoBankServiceImpl implements MassPaytoBankService {
	
	
	private FoRcLimitFacade foRcLimitFacade;

	/**
	 * @param foRcLimitFacade the foRcLimitFacade to set
	 */
	public void setFoRcLimitFacade(FoRcLimitFacade foRcLimitFacade) {
		this.foRcLimitFacade = foRcLimitFacade;
	}
	//算费服务
	protected AccountingService accountingFeeService;
	private Log logger = LogFactory.getLog(MassPaytoBankServiceImpl.class);

	private MassPayService massPayService;
	
	private MassPaytobankImportBaseService massPaytobankImportBaseService;
	
	private MassPaytobankImportDetailService massPaytobankImportDetailService;
	
	private MassPaytobankOrderService massPaytobankOrderService;
	
	
	private WithdrawOrderService withdrawOrderService;
	
	private NotifyFacadeService notifyFacadeService;
	
	private ValidateMessageService validateMessageService;	
	
	private OrderAfterProcService orderAfterProcService;
	//订单回调后处理服务 
	private OrderCallBackService massPaytobankReqOrderCallBackService;
	//订单后处理算费和更新余额服务
	private AccountingService massPaytobankReqAccountingService;
	private String orderProcessQueueName;
	private String massOrderProcessQueueName;
	private final static String PAGECODE = "MassPaytobank";
	private final static String SCENARIOID = "validation";
	private Long emailTemplateId;


	public void setAccountingFeeService(AccountingService accountingFeeService) {
		this.accountingFeeService = accountingFeeService;
	}

	public void setEmailTemplateId(Long emailTemplateId) {
		this.emailTemplateId = emailTemplateId;
	}

	@Override
	public String validateAccountInfo(Long memberCode, Integer accountType, Long amount) {
		String errorInfo = "";
		try {
			AcctDto acctDto = this.massPayService.getAcctInfo(memberCode, accountType);
			if (acctDto != null && acctDto.getStatus() != 1) {
				return "您的账户无效或已冻结";
			}
			AcctAttribDto acctAttribDto = this.massPayService.getAcctAttrInfo(memberCode, accountType);
			if (acctAttribDto != null ) {
				return "您的账户不是企业会员类型";
			}
			BalancesDto balancesDto = this.massPayService.getBalancesInfo(memberCode, accountType);
			if (balancesDto == null) {
				errorInfo = "您的账户异常";
			} else if(balancesDto.getBalance().longValue() < amount.longValue()){
				errorInfo = "您的账户余额不足";
			} else if (acctAttribDto.getFrozen() == 0 ) {
				errorInfo = "您的账户已冻结";
			} else if (acctAttribDto.getAllowOut() == 0 ) {
				errorInfo = "您的账户止出";
			} else if (acctAttribDto.getPayAble() == 0 ) {
				errorInfo = "您的账户不可进行付款";
			} else if (acctAttribDto.getAllowTransferOut() == 0 ) {
				errorInfo = "您的账户不可以转出";
			} 
		} catch (Exception e) {
			errorInfo = "您的账户状态有误";
		}
		return errorInfo;
	}

	@Override
	public boolean validatePayPwd(Long memberCode, Integer accountType, String payPwd) {
		try {
			return this.massPayService.payPwdIsOrder(memberCode, accountType, payPwd);
		} catch (Exception e) {
			LogUtil.error(MassPaytoBankServiceImpl.class, "批量付款到银行", OPSTATUS.EXCEPTION, "支付密码", "", e.getLocalizedMessage(), "", "验证支付密码异常");
		}
		return false;
	}

	/**
	 * 保存上传文件基本信息和详细信息
	 * @param baseinfo
	 * @return
	 * @throws AppException 
	 */
	@Override
	public void saveUploadInfoRdTx(MassPaytobankImportBaseDTO baseinfo) throws AppException {
		try{
			//付款方付费
			baseinfo.setRealPayAmount(baseinfo.getValidAmount().longValue() + baseinfo.getTotalFee().longValue());
			baseinfo.setRealOutAmount(baseinfo.getValidAmount());
			//TODO 所有存储方法，提供两种实现
			Long uploadSeq = massPaytobankImportBaseService.saveMassPaytobankImportBaseInfo(baseinfo);
			baseinfo.setUploadSeq(uploadSeq);
			if(uploadSeq == null){
				throw new RuntimeException("保存上传文件基本信息发生异常");
			}
			List<MassPaytobankImportDetailDTO> list = baseinfo.getMassPaytoBankImportDetails();
			if(list  == null || list.size() == 0){
				throw new RuntimeException("至少需要上传一条明细记录");
			}
			//采用Batch的方式存储
			massPaytobankImportDetailService.saveMassPaytobankImportDetailsRdTx(list,uploadSeq);
		}catch(Exception ex){
			LogUtil.error(MassPaytoBankServiceImpl.class, "批量付款到银行", OPSTATUS.EXCEPTION, "", "", ex.getMessage(), "", "详细信息发生异常");
			throw new AppException(ex);
		}
	}

	/**
	 * 确认付款保存订单信息，工单信息，工作流信息
	 * @param base
	 * @throws AppException 
	 */
	@Override
	public void confirmApplicationRdTx(MassPaytobankImportBaseDTO base) throws AppException {
		try{
			MassPaytobankImportBaseDTO baseDto = new MassPaytobankImportBaseDTO();
			baseDto.setBusinessNo(base.getBusinessNo());
			baseDto.setUploadSeq(base.getUploadSeq());
			baseDto.setUploadDate(new Date());
			baseDto.setStatus(1);//更新批量付款文件处理状态为处理中
			massPaytobankImportBaseService.updatePaytobankImportBase(baseDto);
		}catch(Exception ex){
			throw new AppException(ex);
		}
	}
	
	
	/**
	 * 处理订单
	 * @param dealId
	 */
	private boolean processOrder(MassPaytobankOrderDTO dto,WithdrawBusinessType businessType,AccountingService accountingService,OrderCallBackService callBackSercie){
		HandlerParam param = new HandlerParam();
		param.setBaseOrderDto(dto);
		param.setOrderStatus(new Integer(String.valueOf(dto.getStatus())));
		param.setWithdrawBusinessType(businessType.getBusinessType());
		
		//update terry_ma
		AccountingDto accountingDto = new AccountingDto();
		accountingDto.setHasCaculatedPrice(true);
		accountingDto.setPayerFee(Math.abs(dto.getTotalFee()));
		accountingDto.setAmount(dto.getRealOutAmount());
		accountingDto.setOrderAmount(dto.getTotalAmount());
		accountingDto.setOrderId(dto.getMassOrderSeq());
		accountingDto.setPayer(dto.getPayerMemberCode());
		param.setAccountingDto(accountingDto);
		
		boolean result = orderAfterProcService.process(param,callBackSercie,accountingService);
		return result;
	}
	
	/**
	 * 将base对象转化为Order
	 * @param base
	 * @return
	 */
	private MassPaytobankOrderDTO psssToOrder(MassPaytobankImportBaseDTO base){
		MassPaytobankOrderDTO dto = new MassPaytobankOrderDTO();
		dto.setBusinessNo(base.getBusinessNo());
		dto.setPayerAcctType(base.getPayerAcctType());
		dto.setPayerMemberCode(base.getPayerMomberCode());
		dto.setStatus(OrderStatus.INIT.getValue());//创建
		dto.setPayerOperator(base.getOperatorid());
		dto.setTotalAmount(base.getValidAmount());
		dto.setRealPayAmount(base.getRealPayAmount());
		dto.setRealOutAmount(base.getRealOutAmount());
		dto.setTotalNum(base.getValidNum());
		dto.setTotalFee(base.getTotalFee());
		dto.setIsPayerPayFee(base.getIsPayerPayFee());
		dto.setPayerAcctCode(this.getAcctCode(base.getPayerMomberCode(), base.getPayerAcctType()));
		return dto;
	}
	/**
	 * 获得账户号
	 * @param memberCode
	 * @param acctType
	 * @return
	 */
	private String getAcctCode(Long memberCode ,Integer acctType){
		AcctAttribDto acctAttribDto =null;
		try {
			acctAttribDto = this.massPayService.getAcctAttrInfo(memberCode, acctType);
		} catch (MaAccountQueryUntxException e) {
			LogUtil.info(MassPaytoBankServiceImpl.class, "批量付款到银行", OPSTATUS.EXCEPTION, "getAcctCode", "");
		}
		if(acctAttribDto == null){
			return null;
		}
		return acctAttribDto.getAcctCode();
	}

	public void createMassPaytobankDetailOrder(final long massOrderSeq){
		final MassPaytobankOrderDTO order = this.massPaytobankOrderService.getMassPaytobankOrderInfo(massOrderSeq);
		if(order ==null){
			throw new RuntimeException("没有找到相匹配的批量付款订单信息:"+massOrderSeq);
		}
		final MassPaytobankImportBaseDTO base = this.massPaytobankImportBaseService.getMassPaytobankImportBaseByBusinessNo(order.getBusinessNo(),order.getPayerMemberCode());
		if(null == base){
			throw new RuntimeException("没有找到相匹配的批量付款请求:"+order.getBusinessNo());
		}
		List<MassPaytobankImportDetailDTO>  details = massPaytobankImportDetailService.getValidMassPaytobankImportDetails(base.getUploadSeq());
		for (MassPaytobankImportDetailDTO detail : details) {
			try {
				WithdrawOrderAppDTO withdrawOrderAppDTO=buildWithdrawOrderAppDTO(detail, base, order);
				massPaytobankImportDetailService.createSingleOrderRnTx(detail,withdrawOrderAppDTO);
				String jsonStr = JSonUtil.toJSonString(withdrawOrderAppDTO.getSequenceId());
				//通知后台处理
				notifyFacadeService.sendRequest(buildNotify2QueueRequest(jsonStr,orderProcessQueueName));
			} catch (Exception e) {
				LogUtil.error(getClass(), e.toString(), OPSTATUS.EXCEPTION, "batchNum:"+base.getBusinessNo(), "detailId:"+detail.getDetailSeq(), "", "", "");
			}
		}
			
	}
	
	
	/**
	 *  更新工单状态
	 * @param massOrderSeq
	 * @param operator
	 * @param op 1 审核通过  2审核拒绝
	 * @param remark 备注
	 * @throws AppException
	 */
	public void updateWorkOrderRdTx(long massOrderSeq,String operator,int op,String remark) throws AppException{
		try{
			Date update =new Date();
			MassPaytobankImportBaseDTO baseDto = new MassPaytobankImportBaseDTO();
			baseDto.setUploadSeq(massOrderSeq);
			int status = op==1?2:3;//审批通过时，更新批量付款文件处理状态为成功2，审批拒绝时，更新批量付款文件处理状态为失败3
			
			baseDto.setStatus(status);
			baseDto.setUpdateDate(update);
			baseDto.setAuditOperator(operator);
			baseDto.setAuditRemark(remark);
			baseDto.setOldStatus(1);//原状态为1 处理中
			//TODO 更新是判断状态
			boolean result = massPaytobankImportBaseService.updatePaytobankImportBase(baseDto);
		}catch(Exception e){
			throw new AppException("更新工单异常",e);
		}
	}
	
	private WithdrawOrderAppDTO buildWithdrawOrderAppDTO(MassPaytobankImportDetailDTO detail,MassPaytobankImportBaseDTO base,MassPaytobankOrderDTO order){
		WithdrawOrderAppDTO withdrawOrderAppDTO = new WithdrawOrderAppDTO();
		withdrawOrderAppDTO.setMemberCode(base.getPayerMomberCode());
		withdrawOrderAppDTO.setMemberType(new Long(MemberTypeEnum.MERCHANT.getCode()));
		withdrawOrderAppDTO.setMemberAccType(new Long(base.getPayerAcctType()));
		withdrawOrderAppDTO.setMemberAcc(this.getAcctCode(base.getPayerMomberCode(), base.getPayerAcctType()));
		withdrawOrderAppDTO.setOrderAmount(detail.getAmount()); //设置订单金额
		//withdrawOrderAppDTO.setPrioritys(Short.valueOf("5"));
		withdrawOrderAppDTO.setAccountName(detail.getPayeeName());
		withdrawOrderAppDTO.setBankAcct(detail.getPayeeBankAcct());
		//withdrawOrderAppDTO.setBankAcctType(withdrawRequest.getBankAcctType());
		withdrawOrderAppDTO.setBankKy(detail.getBankCode().toString());
		withdrawOrderAppDTO.setOrderRemarks(detail.getRemark());
		withdrawOrderAppDTO.setBankBranch(detail.getOpeningBankName());
		withdrawOrderAppDTO.setBankProvince(Short.valueOf(detail.getProvinceCode().toString()));
		withdrawOrderAppDTO.setBankCity(Short.valueOf(String.valueOf(detail.getCityCode())));
		withdrawOrderAppDTO.setWithdrawBankCode(String.valueOf(detail.getBankCode()));//出款银行
		withdrawOrderAppDTO.setOrderSeqId(detail.getBusinessOrderId());
	
		withdrawOrderAppDTO.setAmount(detail.getAmount());
		withdrawOrderAppDTO.setPayerAmount(detail.getAmount());
		withdrawOrderAppDTO.setFee(detail.getFee());
		//交易类型
		withdrawOrderAppDTO.setType(1L);
		if("c".equalsIgnoreCase(detail.getTradeType())){
			withdrawOrderAppDTO.setTradeType(0);
		}else{
			withdrawOrderAppDTO.setTradeType(1);
		}
		
		withdrawOrderAppDTO.setBusiType(new Long(WithdrawOrderBusiType.MASSPAY2BANK.getCode()));
		//TODO 待添加应付账户时 更改 订单状态为初始状态，记账成功后再改为处理中的状态
		withdrawOrderAppDTO.setStatus(Long.valueOf(OrderStatus.PROCESSING.getValue()));
		withdrawOrderAppDTO.setMassOrderSeq(order.getMassOrderSeq());
		return withdrawOrderAppDTO;
	}
	
	
	/**
	 * 付款到银行算费
	 * @param command
	 */
	public Long caculateFee(Long paymentAmount,Integer isPayerPayFee,String memberCode){
		
		AccountingFeeRe accountingFeeRe = new AccountingFeeRe();
		accountingFeeRe.setAmount(paymentAmount);
		accountingFeeRe.setPayer(memberCode);
		AccountingFeeRes dealResponse = null;
		try {
			dealResponse = accountingFeeService.caculateFee(accountingFeeRe);
		} catch (Exception e) {
			logger.error("caculate fee error:", e);
		}
		
		Long fee = null;
		//收款方付费与付款方付费都从payerFee中取值
		fee = dealResponse.getPayerFee();
		if(fee==null){
			fee = 0L;
		}
		return fee;
	}

	@Override
	public void notify(Long massOrderSeq) {
		List<WithdrawOrderAppDTO> details = withdrawOrderService.getWithdrawOrderListByMassOrderSeq(massOrderSeq);
		for (WithdrawOrderAppDTO withdrawOrderAppDTO : details) {
			String jsonStr = JSonUtil.toJSonString(withdrawOrderAppDTO.getSequenceId());
			//通知后台处理
			notifyFacadeService.sendRequest(buildNotify2QueueRequest(jsonStr,orderProcessQueueName));
		}
	}
	
	//构建消息对象
	private Notify2QueueRequest buildNotify2QueueRequest(String jsonStr,String queueName) {
		Notify2QueueRequest request = new Notify2QueueRequest();
		request.setQueueName(queueName);
		request.setTargetObject(jsonStr);
		return request;
	}

	public RCLimitResultDTO getRCLimitAmount(Long memberCode){
		RCLimitResultDTO rcLimitResultDTO =  foRcLimitFacade.getBusinessRcLimit(RCLIMITCODE.FO_PAY_ENTERPRISE_BANK2P.getKey(), null, memberCode);
		return rcLimitResultDTO;
	}
	
	/**
	 * 获取消息内容
	 * @param msgCode
	 * @param pageCode
	 * @param scenarioId
	 * @return
	 */
	public String getMessage(String msgCode,String pageCode,String scenarioId){
		Map<String, PageMsgDto> messageMap = validateMessageService.getPageMsgByPagecodeAndScenarioId(pageCode, scenarioId);
		if(null==messageMap || messageMap.isEmpty()|| messageMap.get(msgCode)==null){
			return "";
		}
		return messageMap.get(msgCode).getMsg();
	}
	/**
	 * 获取消息内容
	 * @param msgCode
	 * @return
	 */
	public String getMessage(String msgCode){
		return getMessage(msgCode,PAGECODE, SCENARIOID);
	}
	
	@Override
	public void rejectMassPaytobankOrder(Long massOrderSeq,String remark,String operatorId) throws AppException {
		updateWorkOrderRdTx(massOrderSeq, operatorId, 2,remark);
	}

	public void setOrderProcessQueueName(String orderProcessQueueName) {
		this.orderProcessQueueName = orderProcessQueueName;
	}

	public void setMassOrderProcessQueueName(String massOrderProcessQueueName) {
		this.massOrderProcessQueueName = massOrderProcessQueueName;
	}

	public void setNotifyFacadeService(NotifyFacadeService notifyFacadeService) {
		this.notifyFacadeService = notifyFacadeService;
	}

	public void setMassPayService(MassPayService massPayService) {
		this.massPayService = massPayService;
	}

	public void setMassPaytobankImportBaseService(
			MassPaytobankImportBaseService massPaytobankImportBaseService) {
		this.massPaytobankImportBaseService = massPaytobankImportBaseService;
	}

	public void setMassPaytobankImportDetailService(
			MassPaytobankImportDetailService massPaytobankImportDetailService) {
		this.massPaytobankImportDetailService = massPaytobankImportDetailService;
	}
	
	public void setMassPaytobankOrderService(
			MassPaytobankOrderService massPaytobankOrderService) {
		this.massPaytobankOrderService = massPaytobankOrderService;
	}

	public void setWithdrawOrderService(WithdrawOrderService withdrawOrderService) {
		this.withdrawOrderService = withdrawOrderService;
	}

	

	public void setValidateMessageService(
			ValidateMessageService validateMessageService) {
		this.validateMessageService = validateMessageService;
	}

	public void setOrderAfterProcService(OrderAfterProcService orderAfterProcService) {
		this.orderAfterProcService = orderAfterProcService;
	}

	public void setMassPaytobankReqOrderCallBackService(
			OrderCallBackService massPaytobankReqOrderCallBackService) {
		this.massPaytobankReqOrderCallBackService = massPaytobankReqOrderCallBackService;
	}

	public void setMassPaytobankReqAccountingService(
			AccountingService massPaytobankReqAccountingService) {
		this.massPaytobankReqAccountingService = massPaytobankReqAccountingService;
	}

	@Override
	public void sendEmail(Map<String,String> param,String subject,long templateId) {
		NotifyTargetRequest request = new NotifyTargetRequest();
		String payerName = StringUtil.null2String(param.get("payerName"));
		if(payerName.contains("@")){
			try {
				MemberInfoDto dto =  massPayService.getMemberInfo(payerName, null, null, null);
				if(null!=dto){
					payerName = dto.getMemberName();
				}
			} catch (Exception e) {
				LogUtil.error(MassPaytoBankServiceImpl.class, "批量付款到银行", OPSTATUS.EXCEPTION, "", "", e.getMessage(), "", "call massPayService.getMemberInfo falied");
			}
		}
		param.put("payerName", StringUtil.null2String(payerName));
		request.setData(param);
		request.setNotifyType(RequestType.EMAIL.value());
		request.setFromAddress(Constants.SYSTEM_MAIL);
		request.setSubject(subject);
		request.setFromAddress(Constants.SYSTEM_MAIL);
		request.getRecAddress().add(param.get("targetAddress"));
		request.setTemplateId(Long.valueOf(templateId));
		request.setRequestTime(new Date());
		notifyFacadeService.notifyRequest(request);
		
	}

	@Override
	public void processCompleteMassPaytobankOrder() {

		List<MassPaytobankOrderDTO> dtos = massPaytobankOrderService.getCompleteMassPaytobankOrder();
		MemberInfoDto mbrInfo = null;
		for (MassPaytobankOrderDTO dto : dtos) {
			try{
				MassPaytobankOrderDTO order  = new MassPaytobankOrderDTO();
				order.setMassOrderSeq(dto.getMassOrderSeq());
				order.setStatus(OrderStatus.PROCESSED_SUCCESS.getValue());
				order.setUpdateDate(new Date());
				order.setOldStatus(OrderStatus.PROCESSING.getValue());
				if(!massPaytobankOrderService.updateMassPaytobankOrderInfo(order)){
					return;
				}
				if(null == mbrInfo){
					mbrInfo = massPayService.getMemberInfo(null, dto.getPayerMemberCode(), null, null);
				}
				
				if(null==mbrInfo){
					LogUtil.error(MassPaytoBankServiceImpl.class, "批量付款到银行", OPSTATUS.EXCEPTION, "", "", "没找到付款方会员信息！", "", "没找到付款方会员信息！");
					return;
				}
				
				MassPaytobankOrderTotalInfo info = massPaytobankOrderService.totalComplateMassPaytobankOrderInfo(dto.getMassOrderSeq());
				if(info != null){
					Map<String,String> param= new HashMap<String, String>();
					param.put("payerName", mbrInfo.getMemberName());
					param.put("operator", dto.getPayerOperator());
					param.put("businessNo", dto.getBusinessNo());
					param.put("totalNum", null2Integer(dto.getTotalNum()).toString());
					param.put("totalAmount", numberFormat(toBigDecimalAmount(dto.getTotalAmount())));
					param.put("validNum", null2Integer(info.getSuccNum()).toString());
					param.put("validAmount", numberFormat(toBigDecimalAmount(info.getSuccAmount())));
					param.put("errorNum", null2Integer(info.getFaildNum()).toString());
					param.put("errorAmount", numberFormat(toBigDecimalAmount(info.getFaildAmount())));
					param.put("targetAddress", mbrInfo.getLoginName());
					sendEmail(param,"批量付款到银行文件处理完成！",emailTemplateId);
				}
			}catch(Exception e){
				LogUtil.error(MassPaytoBankServiceImpl.class, "批量付款到银行", OPSTATUS.EXCEPTION, "processCompleteMassPaytobankOrder", "", e.getMessage(), "", "批量付款到银行异常");
			}
		}
		
	}
	
	private Integer null2Integer(Integer num){
		if(null==num){
			return Integer.valueOf(0);
		}
		return num;
	}
	
	
	/**
	 * 金额转BigDecimal
	 * @param amount
	 * @return
	 */
	private BigDecimal toBigDecimalAmount(Long amount){
		Long tmpAmount = amount;
		if (tmpAmount == null) {
			tmpAmount = 0L;
		}
		return new BigDecimal(tmpAmount).divide(new BigDecimal(1000),2, BigDecimal.ROUND_HALF_UP);

	}
	
	private static String numberFormat(BigDecimal num){
		 NumberFormat formatter   =   new   DecimalFormat( "#,###,###.##"); 
		 if(num==null){
			 return "NULL";
		 }
		 return formatter.format(num.doubleValue());
	}

	@Override
	public boolean passMassPaytoBankOrderRdTx(MassPaytobankImportBaseDTO base,
			String operator) throws AppException {
		MassPaytobankOrderDTO orderDto = this.psssToOrder(base);
		orderDto.setPayerOperator(operator);
		orderDto.setRemark(base.getRemark());
		Long orderSeq = massPaytobankOrderService.saveMassPaytobankOrderInfoRnTx(orderDto);
		MassPaytobankOrderDTO order = massPaytobankOrderService.getMassPaytobankOrderInfo(orderSeq);
		boolean status = processOrder(order,WithdrawBusinessType.MASSPAYTOBANK_ORDER_REQ, massPaytobankReqAccountingService,massPaytobankReqOrderCallBackService);
		if(status){
			MassPaytobankOrderDTO updateOrder = new MassPaytobankOrderDTO();
			updateOrder.setOldStatus(order.getStatus());
			updateOrder.setStatus(OrderStatus.PROCESSING.getValue());
			updateOrder.setMassOrderSeq(orderSeq);
			massPaytobankOrderService.updateMassPaytobankOrderInfo(updateOrder);
			String jsonStr = JSonUtil.toJSonString(orderSeq+"_toBank");
			notifyFacadeService.sendRequest(buildNotify2QueueRequest(jsonStr,massOrderProcessQueueName));
			updateWorkOrderRdTx(base.getUploadSeq(), operator, 1,base.getRemark());
		}else{
			LogUtil.error(MassPaytoBankServiceImpl.class, "批量付款到银行", OPSTATUS.EXCEPTION, "passMassPaytoBankOrder", "","更新余额发生异常", "", "批量付款到银行异常");
		}
		
		return status;
	}
}
