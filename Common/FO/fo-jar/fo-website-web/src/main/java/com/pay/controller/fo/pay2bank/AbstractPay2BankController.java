/**
 * 
 */
package com.pay.controller.fo.pay2bank;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.acc.service.member.MemberProductService;
import com.pay.fo.order.service.audit.SingleAuditQueryService;
import com.pay.fo.order.service.audit.WorkorderService;
import com.pay.fo.order.service.base.FundoutOrderService;
import com.pay.fo.order.service.pay2bank.Pay2BankOrderService;
import com.pay.fo.order.service.pay2bank.Pay2BankOrderValidateService;
import com.pay.fo.order.service.validate.PaymentValidateService;
import com.pay.fundout.channel.service.configbank.ConfigBankService;
import com.pay.fundout.service.inf.CardBinFacadeService;
import com.pay.fundout.service.ma.AccountQueryFacadeService;
import com.pay.fundout.service.ma.MemberQueryFacadeService;
import com.pay.fundout.util.AmountUtils;
import com.pay.inf.service.BankService;
import com.pay.lucene.service.LuceneService;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.rm.FoRcLimitFacade;
import com.pay.rm.facade.dto.RCLimitResultDTO;
import com.pay.rm.facade.util.RCLIMITCODE;

/**
 * @author NEW
 *
 */
public abstract class AbstractPay2BankController extends MultiActionController {

	
	protected transient Log log = LogFactory.getLog(getClass());
	/**
	 * 银行服务类
	 */
	protected BankService bankService;
	/**
	 * 会员查询服务类
	 */
	protected MemberQueryFacadeService memberQueryFacadeService;
	/**
	 * 风控限额限次查询服务类
	 */
	protected FoRcLimitFacade foRcLimitFacade;
	/**
	 * 出款订单服务类
	 */
	protected FundoutOrderService fundoutOrderService;
	/**
	 * 付款到银行订单服务类
	 */
	protected Pay2BankOrderService pay2BankOrderService;
	/**
	 * 查询开户行服务类
	 */
	protected LuceneService luceneService;
	
	/**
	 *  算费服务
	 */
	protected AccountingService accountingService;
	
	/**
	 * 支付验证服务类
	 */
	protected PaymentValidateService paymentValidateService;
	/**
	 * 单笔付款到银行验证服务类
	 */
	protected Pay2BankOrderValidateService pay2BankOrderValidateService;
	/**
	 * 账户查询服务类
	 */
	protected AccountQueryFacadeService accountQueryFacadeService;
	
	/**
	 * 卡Bin服务类
	 */
	protected CardBinFacadeService cardBinFacadeService;
	
	/** 会员产品开通权限**/
	protected MemberProductService memberProductService;
	
	/**
	 * 单笔复核查询服务
	 */
	protected SingleAuditQueryService singleAuditQueryService;
	
	/**
	 * 工单服务
	 */
	protected WorkorderService workorderService;
	
	/**
	 * 出款行配置服务类
	 */
	protected ConfigBankService configBankService;


	/**
	 * @param bankService the bankService to set
	 */
	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}


	/**
	 * @param memberQueryFacadeService the memberQueryFacadeService to set
	 */
	public void setMemberQueryFacadeService(
			MemberQueryFacadeService memberQueryFacadeService) {
		this.memberQueryFacadeService = memberQueryFacadeService;
	}




	/**
	 * @param foRcLimitFacade the foRcLimitFacade to set
	 */
	public void setFoRcLimitFacade(FoRcLimitFacade foRcLimitFacade) {
		this.foRcLimitFacade = foRcLimitFacade;
	}


	/**
	 * @param fundoutOrderService the fundoutOrderService to set
	 */
	public void setFundoutOrderService(FundoutOrderService fundoutOrderService) {
		this.fundoutOrderService = fundoutOrderService;
	}


	/**
	 * @param luceneService the luceneService to set
	 */
	public void setLuceneService(LuceneService luceneService) {
		this.luceneService = luceneService;
	}


	/**
	 * @param accountingService the accountingService to set
	 */
	public void setAccountingService(AccountingService accountingService) {
		this.accountingService = accountingService;
	}


	/**
	 * @param paymentValidateService the paymentValidateService to set
	 */
	public void setPaymentValidateService(
			PaymentValidateService paymentValidateService) {
		this.paymentValidateService = paymentValidateService;
	}


	/**
	 * @param pay2BankOrderValidateService the pay2BankOrderValidateService to set
	 */
	public void setPay2BankOrderValidateService(
			Pay2BankOrderValidateService pay2BankOrderValidateService) {
		this.pay2BankOrderValidateService = pay2BankOrderValidateService;
	}


	/**
	 * @param accountQueryFacadeService the accountQueryFacadeService to set
	 */
	public void setAccountQueryFacadeService(
			AccountQueryFacadeService accountQueryFacadeService) {
		this.accountQueryFacadeService = accountQueryFacadeService;
	}


	/**
	 * @param cardBinFacadeService the cardBinFacadeService to set
	 */
	public void setCardBinFacadeService(CardBinFacadeService cardBinFacadeService) {
		this.cardBinFacadeService = cardBinFacadeService;
	}


	/**
	 * @param pay2BankOrderService the pay2BankOrderService to set
	 */
	public void setPay2BankOrderService(Pay2BankOrderService pay2BankOrderService) {
		this.pay2BankOrderService = pay2BankOrderService;
	}


	public void setMemberProductService(MemberProductService memberProductService) {
		this.memberProductService = memberProductService;
	}


	public void setSingleAuditQueryService(
			SingleAuditQueryService singleAuditQueryService) {
		this.singleAuditQueryService = singleAuditQueryService;
	}


	public void setWorkorderService(WorkorderService workorderService) {
		this.workorderService = workorderService;
	}
	
	
	
	
	public void setConfigBankService(ConfigBankService configBankService) {
		this.configBankService = configBankService;
	}


	/**
	 * 获取指定规则的限额
	 * @param riskControlRuleType
	 * @return
	 */
	protected RCLimitResultDTO getLimitAmount(Long memberCode,Integer memberType) throws Exception {
		RCLimitResultDTO rcLimitResultDTO = null;
		if(MemberTypeEnum.MERCHANT.getCode()==memberType){
			rcLimitResultDTO = foRcLimitFacade.getBusinessRcLimit(RCLIMITCODE.FO_PAY_ENTERPRISE_BANK2E.getKey(), null, memberCode);//企业用户
			
		}else if(MemberTypeEnum.INDIVIDUL.getCode()==memberType){
			rcLimitResultDTO = foRcLimitFacade.getUserRcLimit(RCLIMITCODE.FO_PAY_PERSONAL_BANK.getKey(), null, memberCode);//个人用户
		}
		if (rcLimitResultDTO != null) {
			return rcLimitResultDTO;
		}
		log.error("未找到指定规则的限额！");
		throw new Exception();
	}
	
	
	/**
	 * 加载余额
	 * @param command
	 * @param loginSession
	 */
	protected void loadBalance(Pay2BankCommand command){
		long balance = accountQueryFacadeService.getBalance(command.getPayerMemberCode(), command.getPayerAcctType());
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
	protected RCLimitResultDTO initLimitAmount(Pay2BankCommand command) throws Exception{
		Long memberCode = command.getPayerMemberCode();
		RCLimitResultDTO rule = getLimitAmount(memberCode,command.getPayerMemberType());
		command.setSingleLimitAmount(rule.getSingleLimit());
		command.setSingleLimitAmountStr(AmountUtils.numberFormat(rule.getSingleLimit()));
		
		
		Long currentDayAmount = fundoutOrderService.sumCurrentDayPaymentAmount(command.getOrderType(), memberCode);
		Long currentMonthAmount = fundoutOrderService.sumCurrentMonthPaymentAmount(command.getOrderType(), memberCode);
		Integer monthTimes = fundoutOrderService.countCurrentMonthPaymentTimes(command.getOrderType(), memberCode);
		Integer dayTimes = fundoutOrderService.countCurrentDayPaymentTimes(command.getOrderType(), memberCode);
		
		int monthLimitTimes = rule.getMonthTimes() - monthTimes.intValue();
		int dayLimitTimes = rule.getDayTimes() - dayTimes.intValue();
		int limitTimes = 0;
		if(monthLimitTimes < dayLimitTimes){
			limitTimes = monthLimitTimes;
		}else{
			limitTimes =  dayLimitTimes;
		}
		
		long monthLimitAmount = rule.getMonthLimit()-currentMonthAmount.longValue();
		long dayLimitAmount = rule.getDayLimit()-currentDayAmount.longValue();
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
	
	
	
}
