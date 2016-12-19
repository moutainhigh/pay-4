/**
 * 
 */
package com.pay.controller.fo.withdraw;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.service.member.MemberProductService;
import com.pay.fo.order.service.base.FundoutOrderService;
import com.pay.fo.order.service.validate.PaymentValidateService;
import com.pay.fo.order.service.withdraw.WithdrawOrderService;
import com.pay.fo.order.service.withdraw.WithdrawOrderValidateService;
import com.pay.fundout.service.ma.AccountQueryFacadeService;
import com.pay.fundout.service.ma.BankCardBindFacadeService;
import com.pay.fundout.service.ma.MemberQueryFacadeService;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.rm.FoRcLimitFacade;

/**
 * @author NEW
 *
 */
public abstract class AbstractWithdrawController extends MultiActionController {

	
	protected transient Log log = LogFactory.getLog(getClass());
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
	 *  算费服务
	 */
	protected AccountingService accountingService;
	
	
	/**
	 * 支付验证服务类
	 */
	protected PaymentValidateService paymentValidateService;

	/**
	 * 账户查询服务类
	 */
	protected AccountQueryFacadeService accountQueryFacadeService;
	
	/**
	 * 银行卡绑定信息服务类
	 */
	protected BankCardBindFacadeService  bankCardBindFacadeService;
	
	/**
	 * 提现订单验证服务类
	 */
	protected WithdrawOrderValidateService withdrawOrderValidateService;
	

	/** 会员产品开通权限**/
	protected MemberProductService memberProductService;
	
	/**
	 * 提现订单服务类
	 */
	protected WithdrawOrderService withdrawOrderService;

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
	 * @param paymentValidateService the paymentValidateService to set
	 */
	public void setPaymentValidateService(
			PaymentValidateService paymentValidateService) {
		this.paymentValidateService = paymentValidateService;
	}




	/**
	 * @param accountQueryFacadeService the accountQueryFacadeService to set
	 */
	public void setAccountQueryFacadeService(
			AccountQueryFacadeService accountQueryFacadeService) {
		this.accountQueryFacadeService = accountQueryFacadeService;
	}


	/**
	 * @param bankCardBindFacadeService the bankCardBindFacadeService to set
	 */
	public void setBankCardBindFacadeService(
			BankCardBindFacadeService bankCardBindFacadeService) {
		this.bankCardBindFacadeService = bankCardBindFacadeService;
	}




	/**
	 * @param accountingService the accountingService to set
	 */
	public void setAccountingService(AccountingService accountingService) {
		this.accountingService = accountingService;
	}




	/**
	 * @param withdrawOrderValidateService the withdrawOrderValidateService to set
	 */
	public void setWithdrawOrderValidateService(
			WithdrawOrderValidateService withdrawOrderValidateService) {
		this.withdrawOrderValidateService = withdrawOrderValidateService;
	}




	/**
	 * @param memberProductService the memberProductService to set
	 */
	public void setMemberProductService(MemberProductService memberProductService) {
		this.memberProductService = memberProductService;
	}




	/**
	 * @param withdrawOrderService the withdrawOrderService to set
	 */
	public void setWithdrawOrderService(WithdrawOrderService withdrawOrderService) {
		this.withdrawOrderService = withdrawOrderService;
	}
	

}
