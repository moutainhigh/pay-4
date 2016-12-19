/**
 * 
 */
package com.pay.controller.fo.pay2acct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.acc.service.member.MemberProductService;
import com.pay.fo.order.service.audit.SingleAuditQueryService;
import com.pay.fo.order.service.audit.WorkorderService;
import com.pay.fo.order.service.base.PayToAcctOrderService;
import com.pay.fo.order.service.pay2acct.Pay2AcctOrderService;
import com.pay.fo.order.service.pay2acct.Pay2AcctOrderValidateService;
import com.pay.fo.order.service.validate.PaymentValidateService;
import com.pay.fundout.service.ma.AccountQueryFacadeService;
import com.pay.fundout.service.ma.MemberQueryFacadeService;
import com.pay.fundout.util.AmountUtils;
import com.pay.rm.FoRcLimitFacade;
import com.pay.rm.facade.dto.RCLimitResultDTO;
import com.pay.rm.facade.util.RCLIMITCODE;

/**
 * @author NEW
 *
 */
public abstract class AbstractPay2AcctController extends MultiActionController {
	
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
	 * 付款到账户验证服务类
	 */
	protected Pay2AcctOrderValidateService pay2AcctOrderValidateService;
	
	/**
	 * 付款到账户订单服务类
	 */
	protected Pay2AcctOrderService pay2AcctOrderService;
	

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
	 * @param pay2AcctOrderValidateService the pay2AcctOrderValidateService to set
	 */
	public void setPay2AcctOrderValidateService(
			Pay2AcctOrderValidateService pay2AcctOrderValidateService) {
		this.pay2AcctOrderValidateService = pay2AcctOrderValidateService;
	}

	/**
	 * @param pay2AcctOrderService the pay2AcctOrderService to set
	 */
	public void setPay2AcctOrderService(Pay2AcctOrderService pay2AcctOrderService) {
		this.pay2AcctOrderService = pay2AcctOrderService;
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
	
	/**
	 * 加载余额
	 * @param command
	 */
	protected void loadBalance(Pay2AcctCommand command){
		long balance = accountQueryFacadeService.getBalance(command.getPayerMemberCode(), command.getPayerAcctType());
		command.setCurrentBanlance(balance);
		command.setCurrentBanlanceStr(AmountUtils.numberFormat(balance));
		if(AmountUtils.checkAmount(command.getRequestAmount())){
			command.setOrderAmount(AmountUtils.toLongAmount(command.getRequestAmount()));
		}
	}
	
	
	/**
	 * 获取指定规则的限额
	 * @param riskControlRuleType
	 * @return
	 */
	protected RCLimitResultDTO getLimitAmount(Long memberCode,Integer memberType) throws Exception {
		RCLimitResultDTO rcLimitResultDTO = null;
		if(MemberTypeEnum.MERCHANT.getCode()==memberType){
			rcLimitResultDTO = foRcLimitFacade.getBusinessRcLimit(RCLIMITCODE.FO_PAY_ENTERPRISE_ACC2E.getKey(), null, memberCode);//企业用户
			
		}else if(MemberTypeEnum.INDIVIDUL.getCode()==memberType){
			rcLimitResultDTO = foRcLimitFacade.getUserRcLimit(RCLIMITCODE.FO_PAY_PERSONAL_ACC.getKey(), null, memberCode);//个人用户
		}
		if (rcLimitResultDTO != null) {
			return rcLimitResultDTO;
		}
		log.error("未找到指定规则的限额！");
		throw new Exception();
	}
	
	
}
