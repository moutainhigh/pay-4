/**
 * 
 */
package com.pay.fo.order.service.pay2bank.impl;

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
import com.pay.fo.order.common.TradeType;
import com.pay.fo.order.dto.audit.WorkOrderDto;
import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.service.OrderCallbackService;
import com.pay.fo.order.service.afterprocess.OrderAfterProcessService;
import com.pay.fo.order.service.audit.WorkorderService;
import com.pay.fo.order.service.base.FundoutOrderProcessService;
import com.pay.fo.order.service.pay2bank.Pay2BankOrderService;
import com.pay.fo.order.service.pay2bank.Pay2BankOrderValidateService;
import com.pay.fo.order.service.validate.PaymentValidateService;
import com.pay.fundout.service.ma.AccountInfo;
import com.pay.fundout.service.ma.AccountQueryFacadeService;
import com.pay.fundout.service.ma.MemberInfo;
import com.pay.fundout.service.ma.MemberQueryFacadeService;
import com.pay.inf.dto.CityDTO;
import com.pay.inf.dto.ProvinceDTO;
import com.pay.inf.service.BankService;
import com.pay.pe.dto.AccountingFeeRe;
import com.pay.pe.dto.AccountingFeeRes;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.poss.service.inf.input.ProvinceCityFacadeService;
import com.pay.rm.FoRcLimitFacade;
import com.pay.rm.facade.dto.RCLimitResultDTO;
import com.pay.rm.facade.util.RCLIMITCODE;
import com.pay.util.StringUtil;

/**
 * @author NEW
 *
 */
public class Pay2BankOrderServiceImpl implements Pay2BankOrderService {

	private Log log = LogFactory.getLog(getClass());

	/**
	 * 出款订单处理服务类
	 */
	private FundoutOrderProcessService fundoutOrderProcessService;
	/**
	 * 订单后处理服务类
	 */
	private OrderAfterProcessService orderAfterProcessService;
	/**
	 * 订单回调服务类(更新订单状态、构建记账对象、发送通知)
	 */
	private OrderCallbackService orderCallbackService;

	/**
	 * 申请记账服务类（调用MA更新余额）
	 */
	private AccountingService reqAccountingService;

	/**
	 * 成功出款记账服务类（调用MA更新余额）
	 */
	private AccountingService successAccountingService;

	/**
	 * 出款失败记账服务类（调用MA更新余额）
	 */
	private AccountingService failAccountingService;

	/**
	 * 退票记账服务类（调用MA更新余额）
	 * 
	 */
	private AccountingService refundAccountingService;

	private WorkorderService workorderService;

	@Override
	public void createOrder(FundoutOrderDTO order) {
		try {
			Long orderId = fundoutOrderProcessService.createOrderRnTx(order);
			order.setOrderId(orderId);
			order.setOrderStatus(OrderStatus.PROCESSING.getValue());
			order.setUpdateDate(new Date());
			order.setProcessType(OrderProcessType.COMMON_PAY2BANK_REQ);
			orderAfterProcessService.process(order, orderCallbackService,
					reqAccountingService);

		} catch (Throwable e) {
			LogUtil.error(this.getClass(), "单笔付款到银行", OPSTATUS.EXCEPTION, "",
					"", e.getMessage(), "", "存储订单信息或更新余额发生异常");
			throw new RuntimeException(e);
		}

	}

	@Override
	public boolean updateOrderStatus(FundoutOrderDTO order, int oldStatus) {
		try {
			return fundoutOrderProcessService.updateOrderStatusRdTx(order,
					oldStatus);
		} catch (Exception e) {
			LogUtil.error(this.getClass(), "单笔付款到银行", OPSTATUS.EXCEPTION, "",
					"", e.getMessage(), "", "更新订单状态发生异常");
			log.error(e.getMessage(), e);
			return false;
		}

	}

	@Override
	public void foProcessSuccess(FundoutOrderDTO foOrder) {
		if (foOrder == null) {
			throw new RuntimeException("无效的订单");
		}
		foOrder.setOrderStatus(OrderStatus.PROCESSED_SUCCESS.getValue());
		foOrder.setUpdateDate(new Date());
		foOrder.setProcessType(OrderProcessType.COMMON_PAY2BANK_SUCCESS);
		orderAfterProcessService.process(foOrder, orderCallbackService,
				successAccountingService);

	}

	@Override
	public void foProcessFail(FundoutOrderDTO foOrder) {
		if (foOrder == null) {
			throw new RuntimeException("无效的订单");
		}
		foOrder.setOrderStatus(OrderStatus.PROCESSED_FAILURE.getValue());
		foOrder.setUpdateDate(new Date());
		foOrder.setProcessType(OrderProcessType.COMMON_PAY2BANK_FAIL);
		orderAfterProcessService.process(foOrder, orderCallbackService,
				failAccountingService);

	}

	@Override
	public void refundOrder(FundoutOrderDTO foOrder) {
		if (foOrder == null) {
			throw new RuntimeException("无效的订单");
		}
		foOrder.setOrderStatus(OrderStatus.REFUND_SUCCESS.getValue());
		foOrder.setUpdateDate(new Date());
		foOrder.setProcessType(OrderProcessType.COMMON_PAY2BANK_REFUND);
		orderAfterProcessService.process(foOrder, orderCallbackService,
				refundAccountingService);

	}

	/**
	 * @param fundoutOrderProcessService
	 *            the fundoutOrderProcessService to set
	 */
	public void setFundoutOrderProcessService(
			FundoutOrderProcessService fundoutOrderProcessService) {
		this.fundoutOrderProcessService = fundoutOrderProcessService;
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
	 * @param reqAccountingService
	 *            the reqAccountingService to set
	 */
	public void setReqAccountingService(AccountingService reqAccountingService) {
		this.reqAccountingService = reqAccountingService;
	}

	/**
	 * @param successAccountingService
	 *            the successAccountingService to set
	 */
	public void setSuccessAccountingService(
			AccountingService successAccountingService) {
		this.successAccountingService = successAccountingService;
	}

	/**
	 * @param failAccountingService
	 *            the failAccountingService to set
	 */
	public void setFailAccountingService(AccountingService failAccountingService) {
		this.failAccountingService = failAccountingService;
	}

	/**
	 * @param refundAccountingService
	 *            the refundAccountingService to set
	 */
	public void setRefundAccountingService(
			AccountingService refundAccountingService) {
		this.refundAccountingService = refundAccountingService;
	}

	public void setWorkorderService(WorkorderService workorderService) {
		this.workorderService = workorderService;
	}

	@Override
	public void createOrderRdTx(FundoutOrderDTO order, WorkOrderDto workOrder) {
		try {
			Long orderId = fundoutOrderProcessService.createOrderRnTx(order);
			order.setOrderId(orderId);
			workOrder.setOrderSeq(orderId);
			workOrder.setOrderType(OrderType.PAY2BANK.getValue());
			workOrder.setOrderSmallType(OrderSmallType.COMMON_PAY2BANK
					.getValue());
			workorderService.createWorkorderRnTx(workOrder);
		} catch (Throwable e) {
			LogUtil.error(this.getClass(), "单笔付款到银行", OPSTATUS.EXCEPTION, "",
					"", e.getMessage(), "", "存储订单信息或工单信息发生异常");
			throw new RuntimeException(e);
		}

	}

	@Override
	public void auditPass(WorkOrderDto workOrder) {
		try {
			FundoutOrderDTO order = fundoutOrderProcessService
					.getOrder(workOrder.getOrderSeq());
			if (workorderService.updateWorkorderRnTx(workOrder)) {
				order.setOrderStatus(OrderStatus.PROCESSING.getValue());
				order.setUpdateDate(workOrder.getUpdateDate());
				order.setProcessType(OrderProcessType.COMMON_PAY2BANK_REQ);
				orderAfterProcessService.process(order, orderCallbackService,
						reqAccountingService);
			} else {
				LogUtil.error(this.getClass(), "单笔付款到银行", OPSTATUS.EXCEPTION,
						"", "", "", "", "更新工单信息发生异常");
				throw new RuntimeException("更新工单信息发生异常");
			}

		} catch (Throwable e) {
			LogUtil.error(this.getClass(), "单笔付款到银行", OPSTATUS.EXCEPTION, "",
					"", e.getMessage(), "", "更新工单信息或更新余额发生异常");
			throw new RuntimeException(e);
		}

	}

	@Override
	public void auditReject(WorkOrderDto workOrder) {
		try {
			FundoutOrderDTO order = fundoutOrderProcessService
					.getOrder(workOrder.getOrderSeq());
			if (workorderService.updateWorkorderRnTx(workOrder)) {
				order.setOrderStatus(OrderStatus.AUDIT_REJECT.getValue());
				order.setUpdateDate(workOrder.getUpdateDate());
				if (!fundoutOrderProcessService.updateOrderStatusRdTx(order,
						OrderStatus.INIT.getValue())) {
					throw new RuntimeException("更新订单信息发生异常");
				}
			} else {
				throw new RuntimeException("更新工单信息发生异常");
			}

		} catch (Throwable e) {
			LogUtil.error(this.getClass(), "单笔付款到银行", OPSTATUS.EXCEPTION, "",
					"", e.getMessage(), "", "更新工单信息或更新订单状态发生异常");
			throw new RuntimeException(e);
		}

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

	@Override
	public String validate(FundoutOrderDTO order) {

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
			if (StringUtil.isNull(order.getPayeeName())
					|| !validUTF8Length(order.getPayeeName(), 1, 256)) {
				log.error("收款方名称不能为空，且不能大于256个字符的长度[PayeeName:"
						+ order.getPayeeName() + "]");
				message = OrderValidateCode.PAY2BANK_PAYEE_NAME_ERROR.getCode();
			}
		}
		if (StringUtil.isNull(message)) {
			if (StringUtil.isNull(order.getPayeeBankAcctCode())
					|| !validUTF8Length(order.getPayeeBankAcctCode(), 1, 32)) {
				log.error("收款方银行账号不能为空，且不能大于32个字符的长度[PayeeBankAcctCode:"
						+ order.getPayeeBankAcctCode() + "]");
				message = OrderValidateCode.PAY2BANK_PAYEE_BANKACCT_ERROR
						.getCode();
			}
		}
		if (StringUtil.isNull(message)) {
			if (!StringUtil.isNull(order.getPayeeMobile())
					&& !validUTF8Length(order.getPayeeMobile(), 0, 11)) {
				log.error("收款方手机号不能大于11个字符的长度[PayeeMobile:"
						+ order.getPayeeMobile() + "]");
				message = OrderValidateCode.PAY2BANK_PAYEE_MOBILE_ERROR
						.getCode();
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
			String bankName = bankService.getBankById(order.getPayeeBankCode());
			if (StringUtil.isNull(bankName)) {
				log.error("暂不支持收款账号所属银行[PayeeBankCode:"
						+ order.getPayeeBankCode() + "]");
				message = OrderValidateCode.PAY2BANK_PAYEE_BANKCODE_ERROR
						.getCode();
			}
		}

		if (StringUtil.isNull(message)) {
			if (!StringUtil.isNull(order.getPayeeBankName())
					&& !validUTF8Length(order.getPayeeBankName(), 0, 60)) {
				log.error("收款账号所属银行名称不能大于60个字符[PayeeBankName:"
						+ order.getPayeeBankName() + "]");
				message = OrderValidateCode.PAY2BANK_PAYEE_BANKNAME_ERROR
						.getCode();
			}
		}

		if (StringUtil.isNull(message)) {
			if (!StringUtil.isNull(order.getPayeeOpeningBankName())
					&& !validUTF8Length(order.getPayeeOpeningBankName(), 0, 256)) {
				log.error("收款账号开户银行名称不能大于256个字符[PayeeOpeningBankName:"
						+ order.getPayeeOpeningBankName() + "]");
				message = OrderValidateCode.PAY2BANK_PAYEE_OPENINGBANKNAME_ERROR
						.getCode();
			}
		}

		if (StringUtil.isNull(message)) {
			if (!StringUtil.isNull(order.getPayeeBankProvinceName())
					&& !validUTF8Length(order.getPayeeBankProvinceName(), 0, 20)) {
				log.error("收款账号所属省份名称不能大于20个字符[PayeeBankProvinceName:"
						+ order.getPayeeBankProvinceName() + "]");
				message = OrderValidateCode.PAY2BANK_PAYEE_BANKPROVINCENAME_ERROR
						.getCode();
			} else {
				ProvinceDTO province = provinceCityFacadeService
						.getProvinceByName(order.getPayeeBankProvinceName());
				if (null == province) {
					log.error("收款账号所属省份名称不正确[PayeeBankProvinceName:"
							+ order.getPayeeBankProvinceName() + "]");
					message = OrderValidateCode.PAY2BANK_PAYEE_BANKPROVINCENAME_NOT_RIGHT
							.getCode();
				} else {
					order.setPayeeBankProvince(province.getProvincecode()
							.toString());
				}
			}
		}

		if (StringUtil.isNull(message)) {
			if (!StringUtil.isNull(order.getPayeeBankCityName())
					&& !validUTF8Length(order.getPayeeBankCityName(), 0, 20)) {
				log.error("收款账号所属城市名称不能大于20个字符[PayeeBankCityName:"
						+ order.getPayeeBankCityName() + "]");
				message = OrderValidateCode.PAY2BANK_PAYEE_BANKCITYNAME_ERROR
						.getCode();
			} else {
				CityDTO city = provinceCityFacadeService.getCityByName(order
						.getPayeeBankCityName());
				if (null == city) {
					log.error("收款账号所属城市名称不正确[PayeeBankProvinceName:"
							+ order.getPayeeBankCityName() + "]");
					message = OrderValidateCode.PAY2BANK_PAYEE_BANKCITYNAME_NOT_RIGHT
							.getCode();
				} else {
					order.setPayeeBankCity(city.getCitycode().toString());
				}
			}
		}

		if (StringUtil.isNull(message)) {
			if (null == order.getTradeType()
					|| null == TradeType.get(order.getTradeType())) {
				log.error("暂不支持的交易类型[tradeTyped:" + order.getTradeType() + "]");
				message = OrderValidateCode.PAY2BANK_TRADE_TYPE_ERROR.getCode();
			}
		}

		if (StringUtil.isNull(message)) {
			if (!StringUtil.isNull(order.getPaymentReason())
					&& !validUTF8Length(order.getPaymentReason(), 1, 150)) {
				log.error("付款理由不能大于150个字符[PaymentReason:"
						+ order.getPaymentReason() + "]");
				message = OrderValidateCode.PAYMENTREASON_ERROR.getCode();
			}
		} else {
			return message;
		}

		RCLimitResultDTO rcLimitResult = getLimitAmount(
				order.getPayerMemberCode(), payer.getMemberType());

		// 验证付款方会员状态
		if (StringUtil.isNull(message)) {
			message = paymentValidateService.validatePayerMemberInfo(order
					.getPayerMemberCode());
		}

		// 验证付款方账户状态
		if (StringUtil.isNull(message)) {
			message = paymentValidateService.validatePayerAcctInfo(
					order.getPayerMemberCode(), AcctTypeEnum.BASIC_CNY.getCode());
		}

		// 验证付款金额
		if (StringUtil.isNull(message)) {
			long fee = caculateFee(order.getPayerMemberCode(),
					order.getOrderAmount());
			order.setFee(fee);
			message = paymentValidateService.validatePayerBanlance(
					order.getOrderAmount(), 1, fee, order.getPayerMemberCode(),
					AcctTypeEnum.BASIC_CNY.getCode());
		}

		// 验证付款到银行收款方基本信息
		if (StringUtil.isNull(message)) {
			AccountInfo payerAcct = accountQueryFacadeService.getAccountInfo(
					order.getPayerMemberCode(), AcctTypeEnum.BASIC_CNY.getCode());
			message = paymentValidateService.validatePayeeBankAcctInfo(
					order.getPayeeBankName(), order.getPayeeBankAcctCode(),
					order.getPayeeBankCode().toString(),
					OrderType.PAY2BANK.getValue(), 1);
			if (StringUtil.isNull(message)) {
				order.setPayerAcctCode(payerAcct.getAcctCode());
				order.setPayerAcctType(AcctTypeEnum.BASIC_CNY.getCode());
			}
		}

		// 验证风控限额限次数据
		if (StringUtil.isNull(message)) {
			message = pay2BankOrderValidateService.validateRCLimitInfo(
					order.getPayerMemberCode(), order.getOrderAmount(),
					rcLimitResult);
		}

		return message;
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
					RCLIMITCODE.FO_PAY_ENTERPRISE_BANK2E.getKey(), null,
					memberCode);// 企业用户
		} else if (MemberTypeEnum.INDIVIDUL.getCode() == memberType) {
			rcLimitResultDTO = foRcLimitFacade
					.getUserRcLimit(RCLIMITCODE.FO_PAY_PERSONAL_BANK.getKey(),
							null, memberCode);// 个人用户
		}
		return rcLimitResultDTO;
	}

	private Long caculateFee(Long payerMemberCode, Long orderAmount) {

		Long fee = null;
		AccountingFeeRe accountingFeeRe = new AccountingFeeRe();
		accountingFeeRe.setAmount(orderAmount);
		accountingFeeRe.setPayer(payerMemberCode.toString());
		try {
			AccountingFeeRes accountingFeeRes = reqAccountingService
					.caculateFee(accountingFeeRe);
			fee = accountingFeeRes.getPayerFee();
		} catch (Exception e) {
			log.error("caculateFee error:", e);
		}
		if (fee == null) {
			fee = 0L;
		}
		return fee;
	}

	/**
	 * 账户查询服务类
	 */
	private AccountQueryFacadeService accountQueryFacadeService;

	/**
	 * 风控限额限次查询服务类
	 */
	private FoRcLimitFacade foRcLimitFacade;

	/**
	 * 会员查询服务类
	 */
	private MemberQueryFacadeService memberQueryFacadeService;

	/**
	 * 支付验证服务类
	 */
	private PaymentValidateService paymentValidateService;

	/**
	 * 单笔付款到银行验证服务类
	 */
	private Pay2BankOrderValidateService pay2BankOrderValidateService;

	/**
	 * 省份城市信息服务类
	 */
	private ProvinceCityFacadeService provinceCityFacadeService;

	/**
	 * 银行服务类
	 */
	private BankService bankService;

	public void setLog(Log log) {
		this.log = log;
	}

	public void setAccountQueryFacadeService(
			AccountQueryFacadeService accountQueryFacadeService) {
		this.accountQueryFacadeService = accountQueryFacadeService;
	}

	public void setFoRcLimitFacade(FoRcLimitFacade foRcLimitFacade) {
		this.foRcLimitFacade = foRcLimitFacade;
	}

	public void setMemberQueryFacadeService(
			MemberQueryFacadeService memberQueryFacadeService) {
		this.memberQueryFacadeService = memberQueryFacadeService;
	}

	public void setPaymentValidateService(
			PaymentValidateService paymentValidateService) {
		this.paymentValidateService = paymentValidateService;
	}

	public void setPay2BankOrderValidateService(
			Pay2BankOrderValidateService pay2BankOrderValidateService) {
		this.pay2BankOrderValidateService = pay2BankOrderValidateService;
	}

	public void setProvinceCityFacadeService(
			ProvinceCityFacadeService provinceCityFacadeService) {
		this.provinceCityFacadeService = provinceCityFacadeService;
	}

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

}
