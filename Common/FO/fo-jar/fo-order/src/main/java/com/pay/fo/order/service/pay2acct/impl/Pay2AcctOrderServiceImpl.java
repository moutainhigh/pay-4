/**
 * 
 */
package com.pay.fo.order.service.pay2acct.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.fo.order.common.OrderProcessType;
import com.pay.fo.order.common.OrderSmallType;
import com.pay.fo.order.common.OrderStatus;
import com.pay.fo.order.common.OrderType;
import com.pay.fo.order.common.OrderValidateCode;
import com.pay.fo.order.dto.audit.WorkOrderDto;
import com.pay.fo.order.dto.base.PayToAcctOrderDTO;
import com.pay.fo.order.service.OrderCallbackService;
import com.pay.fo.order.service.afterprocess.OrderAfterProcessService;
import com.pay.fo.order.service.audit.WorkorderService;
import com.pay.fo.order.service.base.PayToAcctOrderProcessService;
import com.pay.fo.order.service.pay2acct.Pay2AcctOrderService;
import com.pay.fo.order.service.pay2acct.Pay2AcctOrderValidateService;
import com.pay.fo.order.service.validate.PaymentValidateService;
import com.pay.fundout.service.ma.AccountInfo;
import com.pay.fundout.service.ma.AccountQueryFacadeService;
import com.pay.fundout.service.ma.MemberInfo;
import com.pay.fundout.service.ma.MemberQueryFacadeService;
import com.pay.pe.helper.AcctType;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.rm.FoRcLimitFacade;
import com.pay.rm.facade.dto.RCLimitResultDTO;
import com.pay.rm.facade.util.RCLIMITCODE;
import com.pay.util.IDContentUtil;
import com.pay.util.StringUtil;

/**
 * @author NEW
 *
 */
public class Pay2AcctOrderServiceImpl implements Pay2AcctOrderService {

	private Log log = LogFactory.getLog(getClass());

	/**
	 * 付款到账户订单处理服务对象
	 */
	private PayToAcctOrderProcessService payToAcctOrderProcessService;

	/**
	 * 订单后处理服务类
	 */
	private OrderAfterProcessService orderAfterProcessService;
	/**
	 * 订单回调服务类(更新订单状态、构建记账对象、发送通知)
	 */
	private OrderCallbackService orderCallbackService;

	/**
	 * 成功出款记账服务类（调用MA更新余额）
	 */
	private AccountingService successAccountingService;
	/**
	 * 工单服务
	 */
	private WorkorderService workorderService;

	@Override
	public void createOrder(PayToAcctOrderDTO order) {
		try {
			Long orderId = payToAcctOrderProcessService.createOrderRnTx(order);
			order.setOrderId(orderId);
			order.setOrderStatus(OrderStatus.PROCESSED_SUCCESS.getValue());
			order.setUpdateDate(new Date());
			order.setProcessType(OrderProcessType.COMMON_PAY2ACCT_SUCCESS);
			orderAfterProcessService.process(order, orderCallbackService,
					successAccountingService);
		} catch (Exception e) {
			LogUtil.error(this.getClass(), "单笔付款到账户", OPSTATUS.EXCEPTION, "",
					"", e.getMessage(), "", "存储订单信息或更新余额发生异常");
			throw new RuntimeException(e);
		}

	}

	@Override
	public boolean updateOrderStatus(PayToAcctOrderDTO order, int oldStatus) {
		try {
			return payToAcctOrderProcessService.updateOrderStatusRdTx(order,
					oldStatus);
		} catch (Exception e) {
			LogUtil.error(this.getClass(), "单笔付款到账户", OPSTATUS.EXCEPTION, "",
					"", e.getMessage(), "", "更新订单状态发生异常");
			log.error(e.getMessage(), e);
			return false;
		}

	}

	/**
	 * @param payToAcctOrderProcessService
	 *            the payToAcctOrderProcessService to set
	 */
	public void setPayToAcctOrderProcessService(
			PayToAcctOrderProcessService payToAcctOrderProcessService) {
		this.payToAcctOrderProcessService = payToAcctOrderProcessService;
	}

	/**
	 * @param orderAfterProcessService
	 *            the orderAfterProcessService to set
	 */
	public void setOrderAfterProcessService(
			OrderAfterProcessService orderAfterProcessService) {
		this.orderAfterProcessService = orderAfterProcessService;
	}

	/**
	 * @param orderCallbackService
	 *            the orderCallbackService to set
	 */
	public void setOrderCallbackService(
			OrderCallbackService orderCallbackService) {
		this.orderCallbackService = orderCallbackService;
	}

	/**
	 * @param successAccountingService
	 *            the successAccountingService to set
	 */
	public void setSuccessAccountingService(
			AccountingService successAccountingService) {
		this.successAccountingService = successAccountingService;
	}

	public void setWorkorderService(WorkorderService workorderService) {
		this.workorderService = workorderService;
	}

	@Override
	public void createOrderRdTx(PayToAcctOrderDTO order, WorkOrderDto workOrder) {
		try {
			Long orderId = payToAcctOrderProcessService.createOrderRnTx(order);
			order.setOrderId(orderId);
			workOrder.setOrderSeq(orderId);
			workOrder.setOrderType(OrderType.PAY2ACCT.getValue());
			workOrder.setOrderSmallType(OrderSmallType.COMMON_PAY2ACCT
					.getValue());
			workorderService.createWorkorderRnTx(workOrder);
		} catch (Exception e) {
			LogUtil.error(this.getClass(), "单笔付款到账户", OPSTATUS.EXCEPTION, "",
					"", e.getMessage(), "", "存储订单信息或工单信息发生异常");
			throw new RuntimeException(e);
		}

	}

	@Override
	public void auditPass(WorkOrderDto workOrder) {
		try {
			PayToAcctOrderDTO order = payToAcctOrderProcessService
					.getOrder(workOrder.getOrderSeq());
			if (workorderService.updateWorkorderRnTx(workOrder)) {
				order.setOrderStatus(OrderStatus.PROCESSED_SUCCESS.getValue());
				order.setUpdateDate(workOrder.getUpdateDate());
				order.setProcessType(OrderProcessType.COMMON_PAY2ACCT_SUCCESS);
				orderAfterProcessService.process(order, orderCallbackService,
						successAccountingService);
			} else {
				LogUtil.error(this.getClass(), "单笔付款到账户", OPSTATUS.EXCEPTION,
						"", "", "", "", "更新工单信息发生异常");
				throw new RuntimeException("更新工单信息发生异常");
			}

		} catch (Throwable e) {
			LogUtil.error(this.getClass(), "单笔付款到账户", OPSTATUS.EXCEPTION, "",
					"", e.getMessage(), "", "更新工单信息或更新余额发生异常");
			throw new RuntimeException(e);
		}

	}

	@Override
	public void auditReject(WorkOrderDto workOrder) {
		try {
			PayToAcctOrderDTO order = payToAcctOrderProcessService
					.getOrder(workOrder.getOrderSeq());
			if (workorderService.updateWorkorderRnTx(workOrder)) {
				order.setOrderStatus(OrderStatus.AUDIT_REJECT.getValue());
				order.setUpdateDate(workOrder.getUpdateDate());
				if (!payToAcctOrderProcessService.updateOrderStatusRdTx(order,
						OrderStatus.INIT.getValue())) {
					throw new RuntimeException("更新订单信息发生异常");
				}
			} else {
				throw new RuntimeException("更新工单信息发生异常");
			}

		} catch (Throwable e) {
			LogUtil.error(this.getClass(), "单笔付款到账户", OPSTATUS.EXCEPTION, "",
					"", e.getMessage(), "", "更新工单信息或更新订单状态发生异常");
			throw new RuntimeException(e);
		}

	}

	@Override
	public String validate(PayToAcctOrderDTO order) {
		String message = null;

		MemberInfo payer = memberQueryFacadeService.getMemberInfo(order
				.getPayerMemberCode());
		if (null == payer) {
			log.error("会员不存在[memberCode:" + order.getPayerMemberCode() + "]");
			message = OrderValidateCode.PAYER_MEMBER_NOT_EXISTS.getCode();
		} else {
			order.setPayerLoginName(payer.getLoginName());
			order.setPayerName(payer.getMemberName());
		}

		if (StringUtil.isNull(message)) {
			if (!StringUtil.isNull(order.getForeignOrderId())
					&& !validUTF8Length(order.getForeignOrderId(), 1, 30)) {
				log.error("商户订单号不能大于30个字符的长度[ForeignOrderId:"
						+ order.getForeignOrderId() + "]");
				message = OrderValidateCode.FOREIGNORDERID_ERROR.getCode();
			}
		}
		if (StringUtil.isNull(message)) {
			if (StringUtil.isNull(order.getPayeeLoginName())
					|| !validUTF8Length(order.getPayeeLoginName(), 1, 256)
					|| !IDContentUtil.validateIdentity(order
							.getPayeeLoginName())) {
				log.error("收款方用户名不能为空，且不能大于256个字符的长度[PayeeName:"
						+ order.getPayeeLoginName() + "]");
				message = OrderValidateCode.PAYEE_LOGIN_NAME_ERROR.getCode();
			}
		}

		if (StringUtil.isNull(message)) {
			if (null == order.getOrderAmount() || 0 >= order.getOrderAmount()) {
				log.error("付款金额不能小于或等于0[OrderAmount:" + order.getOrderAmount()
						+ "]");
				message = OrderValidateCode.PAYER_ORDERAMOUNT_ERROR.getCode();
			}
		}

		if (StringUtil.isNull(message)) {
			if (!StringUtil.isNull(order.getPaymentReason())
					&& !validUTF8Length(order.getPaymentReason(), 1, 150)) {
				log.error("付款理由不能大于150个字符[PaymentReason:"
						+ order.getPaymentReason() + "]");
				message = OrderValidateCode.PAYMENTREASON_ERROR.getCode();
			}
		}

		// 验证付款方会员状态
		if (StringUtil.isNull(message)) {
			message = paymentValidateService.validatePayerMemberInfo(order
					.getPayerMemberCode());
		}

		// 验证付款方账户状态
		if (StringUtil.isNull(message)) {
			message = paymentValidateService.validatePayerAcctInfo(
					order.getPayerMemberCode(), order.getPayerAcctType());
			if (StringUtil.isNull(message)) {
				AccountInfo payerAcct = accountQueryFacadeService
						.getAccountInfo(order.getPayerMemberCode(),
								AcctTypeEnum.BASIC_CNY.getCode());
				if (null != payerAcct) {
					order.setPayerAcctCode(payerAcct.getAcctCode());
					order.setPayerAcctType(AcctTypeEnum.BASIC_CNY.getCode());
				}
			}
		}

		// 验证付款金额
		if (StringUtil.isNull(message)) {
			message = paymentValidateService.validatePayerBanlance(
					order.getOrderAmount(), 1, 0, order.getPayerMemberCode(),
					order.getPayerAcctType());
		}

		// 验证收款方会员信息
		MemberInfo payee = memberQueryFacadeService.getMemberInfo(order
				.getPayeeLoginName());
		if (StringUtil.isNull(payee)) {
			message = OrderValidateCode.PAYEE_USERNAME_NOT_REGIST.getCode();
		}
		if (StringUtil.isNull(message)) {
			order.setPayeeMemberType(payee.getMemberType());
			order.setPayeeName(payee.getMemberName());
			order.setPayeeMemberCode(payee.getMemberCode());
			message = paymentValidateService.validatePayeeMemberInfo(payee,
					String.valueOf(payer.getMemberCode()));
		}

		// 验证收款方账号信息
		if (StringUtil.isNull(message)) {
			message = paymentValidateService.validatePayeeAcctInfo(
					payee.getMemberCode(), AcctTypeEnum.BASIC_CNY.getCode());
			if (StringUtil.isNull(message)) {
				AccountInfo payeeAcct = accountQueryFacadeService
						.getAccountInfo(payee.getMemberCode(),
								AcctTypeEnum.BASIC_CNY.getCode());
				if (null != payeeAcct) {
					order.setPayeeAcctCode(payeeAcct.getAcctCode());
					order.setPayeeAcctType(AcctTypeEnum.BASIC_CNY.getCode());
				}
			}
		}

		// 验证风控限额限次数据
		if (StringUtil.isNull(message)) {
			RCLimitResultDTO rcLimitResultDTO = getLimitAmount(
					payer.getMemberCode(), payer.getMemberType());
			message = pay2AcctOrderValidateService.validateRCLimitInfo(
					payer.getMemberCode(), order.getOrderAmount(),
					rcLimitResultDTO);
		}

		return message;
	}

	private boolean validUTF8Length(String paramValue, int min, int max) {
		String tmp = StringUtil.null2String(paramValue);
		int length = tmp.getBytes().length;
		if (length < min || length > max) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 获取指定规则的限额
	 * 
	 * @param riskControlRuleType
	 * @return
	 */
	private RCLimitResultDTO getLimitAmount(Long memberCode, Integer memberType) {
		RCLimitResultDTO rcLimitResultDTO = null;
		if (MemberTypeEnum.MERCHANT.getCode() == memberType) {
			rcLimitResultDTO = foRcLimitFacade.getBusinessRcLimit(
					RCLIMITCODE.FO_PAY_ENTERPRISE_ACC2E.getKey(), null,
					memberCode);// 企业用户

		} else if (MemberTypeEnum.INDIVIDUL.getCode() == memberType) {
			rcLimitResultDTO = foRcLimitFacade.getUserRcLimit(
					RCLIMITCODE.FO_PAY_PERSONAL_ACC.getKey(), null, memberCode);// 个人用户
		}
		return rcLimitResultDTO;
	}

	/**
	 * 会员查询服务类
	 */
	private MemberQueryFacadeService memberQueryFacadeService;
	/**
	 * 风控限额限次查询服务类
	 */
	private FoRcLimitFacade foRcLimitFacade;

	/**
	 * 支付验证服务类
	 */
	private PaymentValidateService paymentValidateService;

	/**
	 * 账户查询服务类
	 */
	private AccountQueryFacadeService accountQueryFacadeService;

	/**
	 * 付款到账户验证服务类
	 */
	private Pay2AcctOrderValidateService pay2AcctOrderValidateService;

	public void setLog(Log log) {
		this.log = log;
	}

	public void setMemberQueryFacadeService(
			MemberQueryFacadeService memberQueryFacadeService) {
		this.memberQueryFacadeService = memberQueryFacadeService;
	}

	public void setFoRcLimitFacade(FoRcLimitFacade foRcLimitFacade) {
		this.foRcLimitFacade = foRcLimitFacade;
	}

	public void setPaymentValidateService(
			PaymentValidateService paymentValidateService) {
		this.paymentValidateService = paymentValidateService;
	}

	public void setAccountQueryFacadeService(
			AccountQueryFacadeService accountQueryFacadeService) {
		this.accountQueryFacadeService = accountQueryFacadeService;
	}

	public void setPay2AcctOrderValidateService(
			Pay2AcctOrderValidateService pay2AcctOrderValidateService) {
		this.pay2AcctOrderValidateService = pay2AcctOrderValidateService;
	}

}
