/**
 * 
 */
package com.pay.controller.fo.batchpay2bank;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.service.member.MemberProductService;
import com.pay.fo.order.service.base.FundoutOrderService;
import com.pay.fo.order.service.batchpay2bank.BatchPay2BankOrderValidateService;
import com.pay.fo.order.service.batchpay2bank.BatchPay2BankRequestService;
import com.pay.fo.order.service.batchpayment.BatchPaymentAuditQueryService;
import com.pay.fo.order.service.batchpayment.BatchPaymentOrderService;
import com.pay.fo.order.service.batchpayment.BatchPaymentReqBaseInfoService;
import com.pay.fo.order.service.batchpayment.BatchPaymentToBankReqDetailService;
import com.pay.fo.order.service.validate.PaymentValidateService;
import com.pay.fundout.service.ma.AccountQueryFacadeService;
import com.pay.fundout.service.ma.MemberQueryFacadeService;

/**
 * @author NEW
 *
 */
public abstract class AbstractBatchPay2BankController extends MultiActionController {
	
	protected transient Log log = LogFactory.getLog(getClass());
	
	
	/**
	 * 会员查询服务类
	 */
	protected MemberQueryFacadeService memberQueryFacadeService;
	
	/**
	 * 支付验证服务类
	 */
	protected PaymentValidateService paymentValidateService;
	
	
	/**
	 * 账户查询服务类
	 */
	protected AccountQueryFacadeService accountQueryFacadeService;

	
	/**
	 * 批量付款到银行请求服务类
	 */
	protected BatchPay2BankRequestService batchPay2BankRequestService;
	
	
	/**
	 * 批量付款请求基本信息服务类
	 */
	protected BatchPaymentReqBaseInfoService batchPaymentReqBaseInfoService;
	
	/**
	 * 批量付款到银行请求明细服务类
	 */
	protected BatchPaymentToBankReqDetailService batchPaymentToBankReqDetailService;
	
	/**
	 * 批量付款订单服务类
	 */
	protected BatchPaymentOrderService batchPaymentOrderService;
	/**
	 * 出款订单服务类
	 */
	protected FundoutOrderService fundoutOrderService;
	
	/**
	 * 批量付款复核查询服务
	 */
	protected BatchPaymentAuditQueryService batchPaymentAuditQueryService;
	
	
	protected BatchPay2BankOrderValidateService batchPay2BankOrderValidateService;
	
	/**
	 * 会员产品服务
	 */
	protected MemberProductService memberProductService;
	

	/**
	 * @param memberQueryFacadeService the memberQueryFacadeService to set
	 */
	public void setMemberQueryFacadeService(
			MemberQueryFacadeService memberQueryFacadeService) {
		this.memberQueryFacadeService = memberQueryFacadeService;
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
	 * @param batchPay2BankRequestService the batchPay2BankRequestService to set
	 */
	public void setBatchPay2BankRequestService(
			BatchPay2BankRequestService batchPay2BankRequestService) {
		this.batchPay2BankRequestService = batchPay2BankRequestService;
	}


	/**
	 * @param batchPaymentReqBaseInfoService the batchPaymentReqBaseInfoService to set
	 */
	public void setBatchPaymentReqBaseInfoService(
			BatchPaymentReqBaseInfoService batchPaymentReqBaseInfoService) {
		this.batchPaymentReqBaseInfoService = batchPaymentReqBaseInfoService;
	}


	/**
	 * @param batchPaymentToBankReqDetailService the batchPaymentToBankReqDetailService to set
	 */
	public void setBatchPaymentToBankReqDetailService(
			BatchPaymentToBankReqDetailService batchPaymentToBankReqDetailService) {
		this.batchPaymentToBankReqDetailService = batchPaymentToBankReqDetailService;
	}


	/**
	 * @param batchPaymentOrderService the batchPaymentOrderService to set
	 */
	public void setBatchPaymentOrderService(
			BatchPaymentOrderService batchPaymentOrderService) {
		this.batchPaymentOrderService = batchPaymentOrderService;
	}


	/**
	 * @param fundoutOrderService the fundoutOrderService to set
	 */
	public void setFundoutOrderService(FundoutOrderService fundoutOrderService) {
		this.fundoutOrderService = fundoutOrderService;
	}


	/**
	 * @param batchPaymentAuditQueryService the batchPaymentAuditQueryService to set
	 */
	public void setBatchPaymentAuditQueryService(
			BatchPaymentAuditQueryService batchPaymentAuditQueryService) {
		this.batchPaymentAuditQueryService = batchPaymentAuditQueryService;
	}


	/**
	 * @param batchPay2BankOrderValidateService the batchPay2BankOrderValidateService to set
	 */
	public void setBatchPay2BankOrderValidateService(
			BatchPay2BankOrderValidateService batchPay2BankOrderValidateService) {
		this.batchPay2BankOrderValidateService = batchPay2BankOrderValidateService;
	}


	/**
	 * @param memberProductService the memberProductService to set
	 */
	public void setMemberProductService(MemberProductService memberProductService) {
		this.memberProductService = memberProductService;
	}

	
	
}
