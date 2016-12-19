 /** @Description 
 * @project 	fo-withdraw
 * @file 		AbstractAutoFundoutService.java 
 * Copyright © 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-12-11		Henry.Zeng			Create 
*/
package com.pay.fundout.autofundout.processor.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.exception.MaAccountQueryUntxException;
import com.pay.acc.exception.MaBankCardBindException;
import com.pay.acc.exception.MaMemberQueryException;
import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.acc.service.member.BankCardBindService;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.BankCardBindBO;
import com.pay.acc.service.member.dto.MemberBaseInfoBO;
import com.pay.fo.order.common.OrderSmallType;
import com.pay.fo.order.common.OrderType;
import com.pay.fo.order.common.TradeType;
import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.service.autofundout.AutoFundoutOrderService;
import com.pay.fo.order.service.validate.PaymentValidateService;
import com.pay.fundout.autofundout.buildorder.service.impl.BulidEnterpriseWithdrawServiceImpl;
import com.pay.fundout.autofundout.custom.model.AutoFundoutResult;
import com.pay.fundout.autofundout.message.MessageService;
import com.pay.fundout.autofundout.processor.service.impl.AutoTimeFundoutServiceImpl;
import com.pay.fundout.autofundout.processor.util.AutoFundoutType;
import com.pay.fundout.service.OrderStatus;
import com.pay.inf.service.BankService;
import com.pay.pe.dto.AccountingFeeRe;
import com.pay.pe.dto.AccountingFeeRes;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.poss.service.inf.input.ProvinceCityFacadeService;
import com.pay.poss.service.ma.member.WithdrawMemberFacadeService;
import com.pay.rm.FoRcLimitFacade;
import com.pay.rm.facade.dto.RCLimitResultDTO;
import com.pay.rm.facade.util.RCLIMITCODE;

/**
 * <p></p>
 * @author Henry.Zeng
 * @since 2010-12-11
 * @see 
 */
public abstract class AbstractAutoFundoutService implements AutoFundoutService {
	protected Log logger = LogFactory.getLog(AbstractAutoFundoutService.class);
	protected AutoFundoutOrderService autoFundoutOrderService;
	protected PaymentValidateService paymentValidateService;
	protected FoRcLimitFacade foRcLimitFacade;
	protected WithdrawMemberFacadeService withdrawMemberFacadeService;
	protected MessageService messageService;
	protected MemberQueryService memberQueryService;
	protected ProvinceCityFacadeService provinceCityFacadeService;
	protected BankService bankService;
	protected BankCardBindService bankCardBindService;	//银行卡信息查询服务
	protected AccountQueryService accountQueryService;	//账号查询
	protected AccountingService accountingFeeService;
	/**
	 * @param bankService the bankService to set
	 */
	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	
	/**
	 * @param provinceCityFacadeService the provinceCityFacadeService to set
	 */
	public void setProvinceCityFacadeService(
			ProvinceCityFacadeService provinceCityFacadeService) {
		this.provinceCityFacadeService = provinceCityFacadeService;
	}
	/**
	 * @param memberQueryService the memberQueryService to set
	 */
	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}
	/**
	 * @param foRcLimitFacade the foRcLimitFacade to set
	 */
	public void setFoRcLimitFacade(FoRcLimitFacade foRcLimitFacade) {
		this.foRcLimitFacade = foRcLimitFacade;
	}
	/**
	 * @param bankCardBindService the bankCardBindService to set
	 */
	public void setBankCardBindService(BankCardBindService bankCardBindService) {
		this.bankCardBindService = bankCardBindService;
	}

	/**
	 * @param accountingFeeService the accountingFeeService to set
	 */
	public void setAccountingFeeService(AccountingService accountingFeeService) {
		this.accountingFeeService = accountingFeeService;
	}
	/**
	 * @param accountQueryService the accountQueryService to set
	 */
	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}
	/**
	 * @param autoFundoutOrderService the autoFundoutOrderService to set
	 */
	public void setAutoFundoutOrderService(
			AutoFundoutOrderService autoFundoutOrderService) {
		this.autoFundoutOrderService = autoFundoutOrderService;
	}
	/**
	 * @param messageService the messageService to set
	 */
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}


	/**
	 * @param withdrawMemberFacadeService the withdrawMemberFacadeService to set
	 */
	public void setWithdrawMemberFacadeService(
			WithdrawMemberFacadeService withdrawMemberFacadeService) {
		this.withdrawMemberFacadeService = withdrawMemberFacadeService;
	}


	/**
	 * @param paymentValidateService the paymentValidateService to set
	 */
	public void setPaymentValidateService(
			PaymentValidateService paymentValidateService) {
		this.paymentValidateService = paymentValidateService;
	}
	
	
	/**
	 * 风控规则校验
	 * @param autoFundoutResult
	 * @return
	 */
	private boolean autoRiskCheckRule(AutoFundoutResult param){

		RCLimitResultDTO rcLimitResultDTO = null;
		if (2 == param.getAutoFundoutConfig().getMemberType()) {
			//企业用户
			rcLimitResultDTO = foRcLimitFacade.getBusinessRcLimit(RCLIMITCODE.FO_ENTERPRISE_WITHDRAW.getKey(), null, param.getAutoFundoutConfig().getMemberCode());
		}else if(1 == param.getAutoFundoutConfig().getMemberType()){
			//个人用户
			rcLimitResultDTO = foRcLimitFacade.getBusinessRcLimit(RCLIMITCODE.FO_PERSONAL_WITHDRAW.getKey(), null, param.getAutoFundoutConfig().getMemberCode());
		}else{
			param.setErrorMessage("没有该会员类型:memberCode=" + param.getAutoFundoutConfig().getMemberCode() + 
								"&memberType=" + param.getAutoFundoutConfig().getMemberType());
			LogUtil.error(AutoTimeFundoutServiceImpl.class,"未知会员类型",OPSTATUS.FAIL,param.getAutoFundoutConfig().getMemberCode().toString(),
					"[memberCode=" + param.getAutoFundoutConfig().getMemberCode() + "&memberType=" + 
					param.getAutoFundoutConfig().getMemberType() + "&bankName=" + param.getAutoFundoutConfig().getBankName() + 
					"&bankCode=" + param.getAutoFundoutConfig().getBankCode() + 
					"&提现金额=" + param.getAmount(),param.getErrorMessage(),"",param.getErrorMessage());
			return false;
		}
		if(rcLimitResultDTO == null) {
			param.setErrorMessage("委托提现金额超过单笔限额!");
			return false;
		}
		//验证是否超过单笔限额
		if(param.getAmount().longValue() > rcLimitResultDTO.getSingleLimit()){
			//如果为定时的，超过单笔限额，则取单笔最大限额
			if(AutoFundoutType.AUTO_TIME.getCode() == param.getAutoType().intValue()){
				param.setAmount(rcLimitResultDTO.getSingleLimit());
				return true;
			}
			param.setErrorMessage("委托提现金额超过单笔限额!");
			LogUtil.error(AutoTimeFundoutServiceImpl.class,"委托提现金额超过单笔限额",OPSTATUS.FAIL,param.getAutoFundoutConfig().getMemberCode().toString(),
					"[memberCode=" + param.getAutoFundoutConfig().getMemberCode() + "&memberType=" + 
					param.getAutoFundoutConfig().getMemberType() + "&bankName=" + param.getAutoFundoutConfig().getBankName() + 
					"&bankCode=" + param.getAutoFundoutConfig().getBankCode() + 
					"&提现金额=" + param.getAmount() + "&单笔限额金额=" + rcLimitResultDTO.getSingleLimit(),param.getErrorMessage(),"",param.getErrorMessage());
			
			String title = "委托提现失败";
			String errorDesc = "[提现金额超过单笔提现限额]";
			
			param.setTitle(title);
			param.setAmount(param.getAmount());
			param.setErrorDesc(errorDesc);
			return false;
		}else{
			return true;
		}
	
	}
	
	/**
	 * 可提现金额是否满足提现金额
	 * @param autoFundoutResult
	 * @return
	 */
	abstract protected boolean autoCheckAmount(AutoFundoutResult autoFundoutResult);
	
	
	@Override
	public void processAutoFundout(AutoFundoutResult autoFundoutResult) throws PossException{
		boolean bl = false;
		try {
			//1 验证会员账号状态
			String errorMsg = paymentValidateService.validatePayerAcctInfo(autoFundoutResult.getAutoFundoutConfig().getMemberCode(), autoFundoutResult.getAutoFundoutConfig().getAccType());
			if(errorMsg == null){
				//2 验证提现金额是否超过可提现金额
				if(autoCheckAmount(autoFundoutResult)){
					//3验证风控限额限次
					if(autoRiskCheckRule(autoFundoutResult)){
						bl = true;
					}
				}
			}else{
				String title = "委托提现失败";
				String errorDesc = "["+errorMsg+"]";
				autoFundoutResult.setTitle(title);
				autoFundoutResult.setErrorDesc(errorDesc);
			}
			//验证不通过,发送失败邮件
			if(!bl){
				messageService.sendFailEmail(autoFundoutResult);
				LogUtil.error(AbstractAutoFundoutService.class,"委托提现风控校验失败!",OPSTATUS.FAIL,autoFundoutResult.getAutoFundoutConfig().getMemberCode().toString(),
						"[memberCode=" + autoFundoutResult.getAutoFundoutConfig().getMemberCode() + "&memberType=" + 
						autoFundoutResult.getAutoFundoutConfig().getMemberType() + "&bankName=" + autoFundoutResult.getAutoFundoutConfig().getBankName() + 
						"&bankCode=" + autoFundoutResult.getAutoFundoutConfig().getBankCode(),autoFundoutResult.getErrorDesc(),"",autoFundoutResult.getErrorDesc());
				//验证通过
			}else{
				//1创建订单并记账
				FundoutOrderDTO order = getFundoutOrderDTO(autoFundoutResult);
				LogUtil.info(this.getClass(),"订单详细信息",OPSTATUS.START,
						order.getPayerMemberCode().toString(),order.toString());
				
				
				if(order.getRealoutAmount() > 0 ){
					autoFundoutOrderService.createOrder(order);
				}else{
					String title = "委托提现失败";
					String errorDesc = "[委托提现金额不足以支付手续费]";
					autoFundoutResult.setTitle(title);
					autoFundoutResult.setErrorDesc(errorDesc);
					messageService.sendFailEmail(autoFundoutResult);
					LogUtil.error(AbstractAutoFundoutService.class,"委托提现金额不足以支付手续费",OPSTATUS.FAIL,autoFundoutResult.getAutoFundoutConfig().getMemberCode().toString(),
							"[memberCode=" + autoFundoutResult.getAutoFundoutConfig().getMemberCode() + "&memberType=" + 
							autoFundoutResult.getAutoFundoutConfig().getMemberType() + "&bankName=" + autoFundoutResult.getAutoFundoutConfig().getBankName() + 
							"&bankCode=" + autoFundoutResult.getAutoFundoutConfig().getBankCode(),autoFundoutResult.getErrorDesc(),"",autoFundoutResult.getErrorDesc());
					return ;
				}
				//发送消息到后台创建工单
				messageService.sendMessage(order);
				//发送成功邮件
				messageService.sendScuEmail(autoFundoutResult);
			}
			
		} catch (Exception e) {
			LogUtil.error(AbstractAutoFundoutService.class,"委托提现风控校验失败!",OPSTATUS.FAIL,autoFundoutResult.getAutoFundoutConfig().getMemberCode().toString(),
					"[memberCode=" + autoFundoutResult.getAutoFundoutConfig().getMemberCode() + "&memberType=" + 
					autoFundoutResult.getAutoFundoutConfig().getMemberType() + "&bankName=" + autoFundoutResult.getAutoFundoutConfig().getBankName() + 
					"&bankCode=" + autoFundoutResult.getAutoFundoutConfig().getBankCode(),e.getMessage(),"",e.getMessage());
			return;
		}
		
	}
	
	private FundoutOrderDTO getFundoutOrderDTO(AutoFundoutResult autoFundoutResult) throws MaAccountQueryUntxException, MaBankCardBindException, MaMemberQueryException{
		FundoutOrderDTO fundoutOrderDTO = new FundoutOrderDTO();
		Long memberCode = autoFundoutResult.getAutoFundoutConfig().getMemberCode();
		fundoutOrderDTO.setPayerMemberCode(memberCode);
		fundoutOrderDTO.setPayerMemberType(autoFundoutResult.getAutoFundoutConfig().getMemberType());
		
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
		
		MemberBaseInfoBO baseInfoBO = memberQueryService.queryMemberBaseInfoByMemberCode(memberCode);
		
		fundoutOrderDTO.setPayerName(baseInfoBO.getName());
		fundoutOrderDTO.setPayerLoginName(baseInfoBO.getLoginName());
		fundoutOrderDTO.setPayerAcctCode(acctAttribDto.getAcctCode());
		fundoutOrderDTO.setPayerAcctType(10);
		fundoutOrderDTO.setPayeeBankAcctCode(autoFundoutResult.getAutoFundoutConfig().getBankAccCode());	//银行卡号
		fundoutOrderDTO.setPayeeBankAcctType(0);//默认借记卡0,存折1,信用卡2
		fundoutOrderDTO.setOrderAmount(autoFundoutResult.getAmount());	//提现金额
		
		BankCardBindBO tempBankCard = getSelectBankCardBindBo(memberCode, fundoutOrderDTO.getPayeeBankAcctCode());
		
		if(tempBankCard == null){
			autoFundoutResult.setErrorMessage("保存订单组装订单请求对象获取银行卡信息为空");
			LogUtil.error(BulidEnterpriseWithdrawServiceImpl.class,"保存订单组装订单请求对象获取银行卡信息为空!",OPSTATUS.FAIL,
					autoFundoutResult.getAutoFundoutConfig().getMemberCode().toString(),
					"[memberCode=" + autoFundoutResult.getAutoFundoutConfig().getMemberCode() + "&memberType=" + 
					autoFundoutResult.getAutoFundoutConfig().getMemberType() + "&bankName=" + autoFundoutResult.getAutoFundoutConfig().getBankName() + 
					"&bankCode=" + autoFundoutResult.getAutoFundoutConfig().getBankCode(),autoFundoutResult.getErrorMessage(),"",autoFundoutResult.getErrorMessage());
		}else{
			fundoutOrderDTO.setBankNumber(String.valueOf(tempBankCard.getBranchBankId()));
		}
		
		//MemberInfoDto memberInfoDto = memberQueryService.doQueryMemberInfoNsTx(null,autoFundoutResult.getAutoFundoutConfig().getMemberCode(),null,null);
		
		fundoutOrderDTO.setPayeeName(tempBankCard.getName());	//账号名称
		fundoutOrderDTO.setPayeeBankCity(String.valueOf(tempBankCard.getCity()));	//开户行城市
		fundoutOrderDTO.setPayeeBankProvince(String.valueOf(tempBankCard.getProvince()));	//开户行省份
		fundoutOrderDTO.setPaymentReason(autoFundoutResult.getAutoFundoutConfig().getRemark());
		fundoutOrderDTO.setPayeeBankCode(autoFundoutResult.getAutoFundoutConfig().getBankCode());	//银行编号
		fundoutOrderDTO.setPayeeBankName(bankService.getBankById(fundoutOrderDTO.getPayeeBankCode()));
		fundoutOrderDTO.setPayeeOpeningBankName(tempBankCard.getBranchBankName());	//开户行支行名称
		fundoutOrderDTO.setPriority(5); //优先级
		//类型
		fundoutOrderDTO.setOrderSmallType(OrderSmallType.TRUST_WITHDRAW.getValue());//提现类别
		
		if(baseInfoBO.getMemberType()==MemberTypeEnum.MERCHANT.getCode()){
			fundoutOrderDTO.setTradeType(TradeType.TO_BUSINESS.getValue());//交易类型
		}else{
			fundoutOrderDTO.setTradeType(TradeType.TO_INDIVIDUAL.getValue());//交易类型
		}
		
		
		fundoutOrderDTO.setOrderType(OrderType.WITHDRAW.getValue());//0:个人提现
		//订单状态
		fundoutOrderDTO.setOrderStatus(OrderStatus.INIT.getValue());
		
		//设置withdrawType,规则引擎返回1 (手工)
		fundoutOrderDTO.setFundoutMode(1);
		//设置是否付款方收费
		fundoutOrderDTO.setIsPayerPayFee(0);
		fundoutOrderDTO.setCreateDate(new Date());
		fundoutOrderDTO.setUpdateDate(new Date());
		
		fundoutOrderDTO.setPayeeBankProvinceName(provinceCityFacadeService.getProvince(Integer.valueOf(fundoutOrderDTO.getPayeeBankProvince())).getProvincename());
		fundoutOrderDTO.setPayeeBankCityName(provinceCityFacadeService.getCity(Integer.valueOf(fundoutOrderDTO.getPayeeBankCity())).getCityname());
		
		//算费接口
		long fee = caculateFee(fundoutOrderDTO.getOrderAmount(), fundoutOrderDTO.getPayerMemberCode());
		fundoutOrderDTO.setFee(fee);
		//TODO 手续费
		fundoutOrderDTO.setRealpayAmount(autoFundoutResult.getAmount()+fee);
		fundoutOrderDTO.setRealoutAmount(autoFundoutResult.getAmount());
		
		
		return fundoutOrderDTO;
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
}
