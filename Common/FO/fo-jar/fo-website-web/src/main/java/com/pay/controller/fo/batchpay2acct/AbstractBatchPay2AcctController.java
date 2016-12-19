/**
 * 
 */
package com.pay.controller.fo.batchpay2acct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.service.member.MemberProductService;
import com.pay.fo.order.service.base.FundoutOrderService;
import com.pay.fo.order.service.base.PayToAcctOrderService;
import com.pay.fo.order.service.batchpay2acct.BatchPay2AcctOrderValidateService;
import com.pay.fo.order.service.batchpay2acct.BatchPay2AcctRequestService;
import com.pay.fo.order.service.batchpayment.BatchPaymentAuditQueryService;
import com.pay.fo.order.service.batchpayment.BatchPaymentOrderService;
import com.pay.fo.order.service.batchpayment.BatchPaymentReqBaseInfoService;
import com.pay.fo.order.service.batchpayment.BatchPaymentToAcctReqDetailService;
import com.pay.fo.order.service.validate.PaymentValidateService;
import com.pay.fundout.service.ma.AccountQueryFacadeService;
import com.pay.fundout.service.ma.MemberQueryFacadeService;

/**
 * @author NEW
 *
 */
public abstract class AbstractBatchPay2AcctController extends MultiActionController {
	
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
	 * 付款到账户服务类
	 */
	protected PayToAcctOrderService payToAcctOrderService;
	
	/**
	 * 批量付款到账户请求服务类
	 */
	protected BatchPay2AcctRequestService batchPay2AcctRequestService;
	
	
	/**
	 * 批量付款请求基本信息服务类
	 */
	protected BatchPaymentReqBaseInfoService batchPaymentReqBaseInfoService;
	
	
	/**
	 * 批量付款到账户请求明细服务类
	 */
	protected BatchPaymentToAcctReqDetailService batchPaymentToAcctReqDetailService;
	
	/**
	 * 批量付款订单服务类
	 */
	protected BatchPaymentOrderService batchPaymentOrderService;
	/**
	 * 批量付款复核查询服务
	 */
	protected BatchPaymentAuditQueryService batchPaymentAuditQueryService;
	
	
	protected BatchPay2AcctOrderValidateService batchPay2AcctOrderValidateService;
	
	/** 会员产品开通权限**/
	protected MemberProductService memberProductService;
	
	public void setMemberProductService(MemberProductService memberProductService) {
		this.memberProductService = memberProductService;
	}

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
	 * @param payToAcctOrderService the payToAcctOrderService to set
	 */
	public void setPayToAcctOrderService(PayToAcctOrderService payToAcctOrderService) {
		this.payToAcctOrderService = payToAcctOrderService;
	}

	/**
	 * @param batchPay2AcctRequestService the batchPay2AcctRequestService to set
	 */
	public void setBatchPay2AcctRequestService(
			BatchPay2AcctRequestService batchPay2AcctRequestService) {
		this.batchPay2AcctRequestService = batchPay2AcctRequestService;
	}


	/**
	 * @param batchPaymentReqBaseInfoService the batchPaymentReqBaseInfoService to set
	 */
	public void setBatchPaymentReqBaseInfoService(
			BatchPaymentReqBaseInfoService batchPaymentReqBaseInfoService) {
		this.batchPaymentReqBaseInfoService = batchPaymentReqBaseInfoService;
	}


	/**
	 * @param batchPaymentToAcctReqDetailService the batchPaymentToAcctReqDetailService to set
	 */
	public void setBatchPaymentToAcctReqDetailService(
			BatchPaymentToAcctReqDetailService batchPaymentToAcctReqDetailService) {
		this.batchPaymentToAcctReqDetailService = batchPaymentToAcctReqDetailService;
	}


	/**
	 * @param batchPaymentOrderService the batchPaymentOrderService to set
	 */
	public void setBatchPaymentOrderService(
			BatchPaymentOrderService batchPaymentOrderService) {
		this.batchPaymentOrderService = batchPaymentOrderService;
	}


	/**
	 * @param batchPaymentAuditQueryService the batchPaymentAuditQueryService to set
	 */
	public void setBatchPaymentAuditQueryService(
			BatchPaymentAuditQueryService batchPaymentAuditQueryService) {
		this.batchPaymentAuditQueryService = batchPaymentAuditQueryService;
	}


	/**
	 * @param batchPay2AcctOrderValidateService the batchPay2AcctOrderValidateService to set
	 */
	public void setBatchPay2AcctOrderValidateService(
			BatchPay2AcctOrderValidateService batchPay2AcctOrderValidateService) {
		this.batchPay2AcctOrderValidateService = batchPay2AcctOrderValidateService;
	}


	
	
}
