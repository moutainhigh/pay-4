 /** @Description 
 * @project 	fo-withdraw
 * @file 		BulidEnterpriseWithdrawServiceImpl.java 
 * Copyright © 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-12-11		Henry.Zeng			Create 
*/
package com.pay.fundout.autofundout.buildorder.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.exception.MaAccountQueryUntxException;
import com.pay.acc.exception.MaBankCardBindException;
import com.pay.acc.exception.MaMemberQueryException;
import com.pay.acc.service.account.constantenum.PayForEnum;
import com.pay.acc.service.member.dto.BankCardBindBO;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.fundout.autofundout.buildorder.service.AbstractBulidFundoutOrderService;
import com.pay.fundout.autofundout.buildorder.service.BulidFundoutOrderService;
import com.pay.fundout.autofundout.custom.model.AutoFundoutResult;
import com.pay.fundout.autofundout.custom.model.AutofundoutMail;
import com.pay.fundout.autofundout.processor.util.AutoFundoutType;
import com.pay.fundout.withdraw.dto.WithdrawOrderAppDTO;
import com.pay.fundout.withdraw.dto.requestproc.WithdrawRequestDTO;
import com.pay.fundout.withdraw.service.failproc.OrderAfterFailProcAlertService;
import com.pay.pe.dto.AccountingDto;
import com.pay.pe.dto.AccountingFeeRe;
import com.pay.pe.dto.AccountingFeeRes;
import com.pay.poss.base.common.order.WithdrawOrderStatus;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.poss.common.accounting.WithdrawBusinessType;
import com.pay.poss.dto.withdraw.notify.Notify2QueueRequest;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.util.DateUtil;
import com.pay.util.JSonUtil;

/**
 * <p></p>
 * @author Henry.Zeng
 * @since 2010-12-11
 * @see 
 */
public class BulidEnterpriseWithdrawServiceImpl extends AbstractBulidFundoutOrderService implements BulidFundoutOrderService {

	private OrderAfterFailProcAlertService orderAfterFailProcAlertService;
	private Log logger = LogFactory.getLog(BulidEnterpriseWithdrawServiceImpl.class);
	private Long successTemplateId;
	private Long failTemplateId;
	public void setOrderAfterFailProcAlertService(
			OrderAfterFailProcAlertService orderAfterFailProcAlertService) {
		this.orderAfterFailProcAlertService = orderAfterFailProcAlertService;
	}
	
	public void setSuccessTemplateId(Long successTemplateId) {
		this.successTemplateId = successTemplateId;
	}

	public void setFailTemplateId(Long failTemplateId) {
		this.failTemplateId = failTemplateId;
	}

	/**
	 * 提现手续费计算
	 * @param command
	 */
	private Long caculateFee(Long amount,Long memberCode){
		
		AccountingFeeRe accountingFeeRe = new AccountingFeeRe();
		accountingFeeRe.setAmount(amount);
		accountingFeeRe.setPayer(memberCode);
		AccountingFeeRes dealResponse = null;
		try {
			dealResponse = accountingFeeService.caculateFee(accountingFeeRe);
		} catch (Exception e) {
			logger.error("caculate fee error:", e);
		}
		Long fee = dealResponse.getPayerFee();
		if(fee==null){
			return 0L;
		}
		return fee;
	}

	
	
	@Override
	public void createOrderRnTx(AutoFundoutResult autoFundoutResult) throws PossException {
		
		WithdrawRequestDTO withdrawRequest = null;
		try {
			//构建订单请求对象
			try {
				withdrawRequest = getWithdrawRequest(autoFundoutResult);
			} catch (MaMemberQueryException e) {
				LogUtil.error(BulidEnterpriseWithdrawServiceImpl.class,"获取订单信息失败!",OPSTATUS.FAIL,
						"","[memberCode=" + autoFundoutResult.getAutoFundoutConfig().getMemberCode() + "&memberType=" + 
						autoFundoutResult.getAutoFundoutConfig().getMemberType(),e.getMessage(),"",e.getMessage());
			}		
			//构建订单申请对象
			WithdrawOrderAppDTO withdrawOrderAppDTO = getWithdrawOrderAppDTO(withdrawRequest);
			
			LogUtil.info(BulidEnterpriseWithdrawServiceImpl.class,"订单详细信息",OPSTATUS.START,
					withdrawOrderAppDTO.getMemberCode().toString(),withdrawOrderAppDTO.toString());
			
			if(withdrawRequest.getApplyWithdrawAmount() - withdrawOrderAppDTO.getFee() > 0 ){
				
				withdrawOrderAppDTO.setOrderAmount(withdrawRequest.getApplyWithdrawAmount()-withdrawOrderAppDTO.getFee());
				
				withdrawOrderAppDTO.setPayerAmount(withdrawRequest.getApplyWithdrawAmount()-withdrawOrderAppDTO.getFee());
				
				withdrawOrderAppDTO.setAmount(withdrawRequest.getApplyWithdrawAmount()-withdrawOrderAppDTO.getFee());
				
				withdrawOrderAppDTO.setType(3l);
				
				//保存订单
				Long seqId = withdrawOrderService.createWithdrawOrderRnTx(withdrawOrderAppDTO);
				
				LogUtil.info(BulidEnterpriseWithdrawServiceImpl.class,"订单保存成功",OPSTATUS.START,seqId.toString(),withdrawOrderAppDTO.toString());
				
				withdrawRequest.setStatus(withdrawOrderAppDTO.getStatus());
				withdrawRequest.setBusiType(withdrawOrderAppDTO.getBusiType());
				withdrawRequest.setSeqId(seqId);
				withdrawRequest.setApplyWithdrawAmount(withdrawOrderAppDTO.getAmount());
				//记账
				HandlerParam handlerParam = new HandlerParam();
				//这里只传进了个人.企业和个人记账规则相同.
				handlerParam.setWithdrawBusinessType(WithdrawBusinessType.WITHDRAW_REQ_PERSON.getBusinessType());
				handlerParam.setOrderStatus(Integer.valueOf(withdrawRequest.getStatus().toString()));
				handlerParam.setBaseOrderDto(withdrawRequest);
				//update by terry_ma
				AccountingDto accountingDto = new AccountingDto();
				accountingDto.setAmount(withdrawOrderAppDTO.getAmount());
				accountingDto.setHasCaculatedPrice(true);
				accountingDto.setOrderId(seqId);
				accountingDto.setOrderAmount(withdrawOrderAppDTO.getOrderAmount());
				accountingDto.setPayer(withdrawOrderAppDTO.getMemberCode());
				accountingDto.setPayerFee(Math.abs(withdrawOrderAppDTO.getFee()));
				handlerParam.setAccountingDto(accountingDto);
				boolean flag = orderAfterProcService.process(handlerParam, orderCallBackService, accountingService);
				if(!flag){
					orderAfterFailProcAlertService.saveOrderAfterFailProcRnTx(seqId, "自动提现");
					autoFundoutResult.setErrorMessage("记账失败");
					LogUtil.error(BulidEnterpriseWithdrawServiceImpl.class,"记账失败!",OPSTATUS.FAIL,
							autoFundoutResult.getAutoFundoutConfig().getMemberCode().toString(),
							"[memberCode=" + autoFundoutResult.getAutoFundoutConfig().getMemberCode() + "&memberType=" + 
							autoFundoutResult.getAutoFundoutConfig().getMemberType() + "&bankName=" + autoFundoutResult.getAutoFundoutConfig().getBankName() + 
							"&bankCode=" + autoFundoutResult.getAutoFundoutConfig().getBankCode() + 
							"&withdrawOrderId=" + seqId,autoFundoutResult.getErrorMessage(),"",autoFundoutResult.getErrorMessage());
					throw new PossException("记账失败",ExceptionCodeEnum.UN_KNOWN_EXCEPTION);
				}
			
				//发送消息
				sendMessage(withdrawRequest);
				
				//发送成功邮件
//				sendScuMail(autoFundoutResult);
			}else{
				sendFailMail(autoFundoutResult);
				return ;
			}
			
		} catch (MaAccountQueryUntxException e) {
			autoFundoutResult.setErrorMessage("保存订单组装订单请求对象获取账户号出现异常");
			LogUtil.error(BulidEnterpriseWithdrawServiceImpl.class,"保存订单组装订单请求对象获取账户号出现异常!",OPSTATUS.FAIL,
					autoFundoutResult.getAutoFundoutConfig().getMemberCode().toString(),
					"[memberCode=" + autoFundoutResult.getAutoFundoutConfig().getMemberCode() + "&memberType=" + 
					autoFundoutResult.getAutoFundoutConfig().getMemberType() + "&bankName=" + autoFundoutResult.getAutoFundoutConfig().getBankName() + 
					"&bankCode=" + autoFundoutResult.getAutoFundoutConfig().getBankCode(),e.getMessage(),"",e.getMessage());
		} catch (MaBankCardBindException e) {
			autoFundoutResult.setErrorMessage("保存订单组装订单请求对象获取银行卡信息出现异常");
			LogUtil.error(BulidEnterpriseWithdrawServiceImpl.class,"保存订单组装订单请求对象获取银行卡信息出现异常!",OPSTATUS.FAIL,
					autoFundoutResult.getAutoFundoutConfig().getMemberCode().toString(),
					"[memberCode=" + autoFundoutResult.getAutoFundoutConfig().getMemberCode() + "&memberType=" + 
					autoFundoutResult.getAutoFundoutConfig().getMemberType() + "&bankName=" + autoFundoutResult.getAutoFundoutConfig().getBankName() + 
					"&bankCode=" + autoFundoutResult.getAutoFundoutConfig().getBankCode(),e.getMessage(),"",e.getMessage());
		}
	}
	
	
	
	
	
	private void sendFailMail(AutoFundoutResult param){
		AutofundoutMail mail = new AutofundoutMail();
		String amountStr = new BigDecimal(param.getAmount()).divide(new BigDecimal(1000),2,BigDecimal.ROUND_DOWN).toString();
		mail.setAmount(amountStr);
		if(AutoFundoutType.AUTO_TIME.getCode() == param.getAutoType().intValue()){
			mail.setAutoType(AutoFundoutType.AUTO_TIME.getDesc());
		}else if(AutoFundoutType.AUTO_QUOTA.getCode() == param.getAutoType().intValue()){
			mail.setAutoType(AutoFundoutType.AUTO_QUOTA.getDesc());
		}else{
			mail.setAutoType("未知");
		}
		mail.setCreateTime(DateUtil.dateToStr(new Date(),"yyyy-MM-dd HH:mm:ss"));
		mail.setFailReason("[余额不足]");
		mail.setSubject("自动提现失败");
		mail.setTemplateId(failTemplateId);
		
		try {
			MemberInfoDto memberInfo = this.memberQueryService.doQueryMemberInfoNsTx(null,param.getAutoFundoutConfig().getMemberCode(),null,null);
			mail.setEmailAddress(memberInfo.getLoginName());
			mail.setPayeeName(memberInfo.getMemberName());
		} catch (MaMemberQueryException e) {
			LogUtil.error(this.getClass(),"获取会员邮箱地址失败！邮件发送失败!",OPSTATUS.FAIL,param.getAutoFundoutConfig().getMemberCode().toString(),
					"[memberCode=" + param.getAutoFundoutConfig().getMemberCode() + "&memberType=" + 
					param.getAutoFundoutConfig().getMemberType() + "&bankName=" + param.getAutoFundoutConfig().getBankName() + 
					"&bankCode=" + param.getAutoFundoutConfig().getBankCode(),e.getMessage(),"",e.getMessage());
		}
		
//		messageService.sendMail(mail);
	}
	
	//发送消息
	private void sendMessage(WithdrawRequestDTO withdrawRequest){
		String jsonStr = JSonUtil.toJSonString(withdrawRequest.getSeqId());
		try{
			notifyFacadeService.sendRequest(buildNotify2QueueRequest(jsonStr));
		}catch(Exception e){
			LogUtil.error(BulidEnterpriseWithdrawServiceImpl.class,"发送消息失败!",OPSTATUS.FAIL,
					withdrawRequest.getSeqId().toString(),"[memberCode=" + withdrawRequest.getMemberCode() + "&memberType=" + 
					withdrawRequest.getMemberType() + "&bankName=" + withdrawRequest.getAccountName() + 
					"&bankCode=" + withdrawRequest.getBankKy(),e.getMessage(),"",e.getMessage());
		}
	}
	
	//构建对象产生
	private Notify2QueueRequest buildNotify2QueueRequest(String jsonStr) {
		Notify2QueueRequest request = new Notify2QueueRequest();
		request.setQueueName(queueName);
		request.setTargetObject(jsonStr);
		return request;
	}
	
	//构建订单对象
	private WithdrawOrderAppDTO getWithdrawOrderAppDTO(WithdrawRequestDTO withdrawRequest){
		WithdrawOrderAppDTO withdrawOrderAppDTO = new WithdrawOrderAppDTO();
		
		withdrawOrderAppDTO.setMemberCode(Long.valueOf(withdrawRequest.getMemberCode()));
		withdrawOrderAppDTO.setMemberAcc(withdrawRequest.getMemberAcc());
		withdrawOrderAppDTO.setMemberType(Long.valueOf(withdrawRequest.getMemberType()));
		withdrawOrderAppDTO.setMemberAccType(withdrawRequest.getMemberAccType());
		withdrawOrderAppDTO.setPrioritys(withdrawRequest.getPrioritys());
		withdrawOrderAppDTO.setAccountName(withdrawRequest.getAccountName());
		withdrawOrderAppDTO.setBankAcct(withdrawRequest.getBankAcct());
		withdrawOrderAppDTO.setBankAcctType(withdrawRequest.getBankAcctType());
		withdrawOrderAppDTO.setBankKy(withdrawRequest.getBankKy());
		withdrawOrderAppDTO.setOrderRemarks(withdrawRequest.getOrderRemarks());
		withdrawOrderAppDTO.setBankBranch(withdrawRequest.getBankBranch());
		withdrawOrderAppDTO.setBankProvince(withdrawRequest.getBankProvince());
		withdrawOrderAppDTO.setBankCity(withdrawRequest.getBankCity());
		//类型
		withdrawOrderAppDTO.setType(withdrawRequest.isBusiness()?1L:0L);//提现类别
		withdrawOrderAppDTO.setTradeType(withdrawRequest.isBusiness()?1:0);//交易类型
		withdrawOrderAppDTO.setBusiType(0L);//0:个人提现
		//订单状态
		withdrawOrderAppDTO.setStatus(Long.valueOf(WithdrawOrderStatus.INIT.getValue()));
		
		//设置withdrawType,规则引擎返回1 (手工)
		withdrawOrderAppDTO.setWithdrawType((short)withdrawRequest.getWithdrawType());
		
		//设置出款银行
		withdrawOrderAppDTO.setWithdrawBankCode(withdrawRequest.getWithdrawBankCode());
		//算费接口
		withdrawOrderAppDTO.setFee(caculateFee(withdrawRequest.getApplyWithdrawAmount(), withdrawOrderAppDTO.getMemberCode()));
		
		
		return withdrawOrderAppDTO;
	}

	//构建订单请求对象
	private WithdrawRequestDTO getWithdrawRequest(AutoFundoutResult autoFundoutResult) throws MaAccountQueryUntxException, MaBankCardBindException, MaMemberQueryException{
		WithdrawRequestDTO  request = new WithdrawRequestDTO();
		Long memberCode = autoFundoutResult.getAutoFundoutConfig().getMemberCode();
		request.setMemberCode(memberCode.toString());
		request.setMemberType(autoFundoutResult.getAutoFundoutConfig().getMemberType());
		
		//获取账户号
		AcctAttribDto acctAttribDto = accountQueryService.doQueryAcctAttribNsTx(memberCode,10);
		if(null == acctAttribDto){
			autoFundoutResult.setErrorMessage("保存订单组装订单请求对象获取账户号为空");
			LogUtil.error(BulidEnterpriseWithdrawServiceImpl.class,"保存订单组装订单请求对象获取账户号为空!",OPSTATUS.FAIL,
					autoFundoutResult.getAutoFundoutConfig().getMemberCode().toString(),
					"[memberCode=" + autoFundoutResult.getAutoFundoutConfig().getMemberCode() + "&memberType=" + 
					autoFundoutResult.getAutoFundoutConfig().getMemberType() + "&bankName=" + autoFundoutResult.getAutoFundoutConfig().getBankName() + 
					"&bankCode=" + autoFundoutResult.getAutoFundoutConfig().getBankCode(),autoFundoutResult.getErrorMessage(),"",autoFundoutResult.getErrorMessage());
			return null;
		}
		request.setMemberAcc(acctAttribDto.getAcctCode().toString());
		request.setAcctType(10);
		request.setBankAcct(autoFundoutResult.getAutoFundoutConfig().getBankAccCode());	//银行卡号
		request.setBankAcctType(0L);//默认借记卡0,存折1,信用卡2
		request.setApplyWithdrawAmount(autoFundoutResult.getAmount());	//提现金额
		
		BankCardBindBO tempBankCard = getSelectBankCardBindBo(memberCode, request.getBankAcct());
		
		if(tempBankCard == null){
			autoFundoutResult.setErrorMessage("保存订单组装订单请求对象获取银行卡信息为空");
			LogUtil.error(BulidEnterpriseWithdrawServiceImpl.class,"保存订单组装订单请求对象获取银行卡信息为空!",OPSTATUS.FAIL,
					autoFundoutResult.getAutoFundoutConfig().getMemberCode().toString(),
					"[memberCode=" + autoFundoutResult.getAutoFundoutConfig().getMemberCode() + "&memberType=" + 
					autoFundoutResult.getAutoFundoutConfig().getMemberType() + "&bankName=" + autoFundoutResult.getAutoFundoutConfig().getBankName() + 
					"&bankCode=" + autoFundoutResult.getAutoFundoutConfig().getBankCode(),autoFundoutResult.getErrorMessage(),"",autoFundoutResult.getErrorMessage());
		}
		
		//MemberInfoDto memberInfoDto = memberQueryService.doQueryMemberInfoNsTx(null,autoFundoutResult.getAutoFundoutConfig().getMemberCode(),null,null);
		
		request.setAccountName(tempBankCard.getName());	//账号名称
		request.setBankCity(Short.valueOf(tempBankCard.getCity().toString()));	//开户行城市
		request.setBankProvince(Short.valueOf(tempBankCard.getProvince().toString()));	//开户行省份
		request.setOrderRemarks(autoFundoutResult.getAutoFundoutConfig().getRemark());
		request.setBankKy(autoFundoutResult.getAutoFundoutConfig().getBankCode());	//银行编号
		request.setBankBranch(tempBankCard.getBranchBankName());	//开户行支行名称
		request.setPrioritys(Short.valueOf("5")); //优先级
		
		return request;
	}
	
	/**
	 * 获取绑定银行卡
	 * @param memberCode
	 * @param bankAccCode
	 * @return
	 * @throws MaBankCardBindException
	 */
	private BankCardBindBO getSelectBankCardBindBo(long memberCode , String bankAccCode ) throws MaBankCardBindException{
		//获取银行卡信息
		List<BankCardBindBO> bankList = bankCardBindService.doQueryBankCardBindInfoForVerifyNsTx(memberCode);
		if(bankList.isEmpty()){
			return null;
		}
		for(BankCardBindBO bankCardBindBO : bankList){
			if(bankAccCode != null && bankAccCode.equals(bankCardBindBO.getBankAcctId())){
				return bankCardBindBO;
			}
		}
		return null;
	}
	
	
}
