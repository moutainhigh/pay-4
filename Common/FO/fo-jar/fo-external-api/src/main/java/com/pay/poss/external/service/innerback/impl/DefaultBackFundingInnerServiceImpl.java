package com.pay.poss.external.service.innerback.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.poss.base.common.Constants;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.poss.common.accounting.AccountingResult;
import com.pay.poss.common.accounting.WithdrawBusinessType;
import com.pay.poss.dto.withdraw.order.BackFundmentOrder;
import com.pay.poss.dto.withdraw.order.OrderFailProcAlertModel;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.dto.withdraw.pricestrategy.CalFeeRequestDTO;
import com.pay.poss.external.service.innerback.BackFundingInnerService;
import com.pay.poss.external.service.innerback.BackFundingOrderDaoService;
import com.pay.poss.service.ma.input.account.impl.AbstractAccountingService;
import com.pay.poss.service.withdraw.orderafterproc.OrderAfterFailProcHandler;

public class DefaultBackFundingInnerServiceImpl extends
		AbstractAccountingService implements BackFundingInnerService {
	private Log log = LogFactory
			.getLog(DefaultBackFundingInnerServiceImpl.class);
	private BackFundingOrderDaoService orderDaoService;
	private OrderAfterFailProcHandler failHandler;

	private Set<String> allowRequest;
	// (业务类型,[OrderCode,DealCode,PSPkgCode])
	private Map<String, Integer[]> peParams = new HashMap<String, Integer[]>(11);

	public DefaultBackFundingInnerServiceImpl() {
		init();
	}

	private void init() {
		// 个人提现申请成功
		peParams.put(
				WithdrawBusinessType.WITHDRAW_REQ_PERSON.getBusinessType(),
				new Integer[] { 100, 101, 101 });
		// 个人提现订单成功
		peParams.put(WithdrawBusinessType.WITHDRAW_ORDER_SUCC_PERSON
				.getBusinessType(), new Integer[] { 100, 101, 101 });
		// 个人提现订单失败
		peParams.put(WithdrawBusinessType.WITHDRAW_ORDER_FAIL_PERSON
				.getBusinessType(), new Integer[] { 100, 101, 101 });
		// 个人提现退款订单失败
		peParams.put(WithdrawBusinessType.WITHDRAW_REFUNDORDER_PERSON
				.getBusinessType(), new Integer[] { 100, 101, 101 });

		// 个人充退请求成功
		peParams.put(WithdrawBusinessType.ACCTCHARGE_REFUND_REQ_PERSON
				.getBusinessType(), new Integer[] { 320, 323, 323 });
		// 个人充退订单成功
		peParams.put(WithdrawBusinessType.ACCTCHARGE_REFUND_ORDER_SUCC_PERSON
				.getBusinessType(), new Integer[] { 320, 323, 323 });
		// 个人充退订单失败
		peParams.put(WithdrawBusinessType.ACCTCHARGE_REFUND_ORDER_FAIL_PERSON
				.getBusinessType(), new Integer[] { 320, 323, 323 });

		// 个人付款到银行卡提交请求
		peParams.put(
				WithdrawBusinessType.PAYTOBANK_REQ_PERSON.getBusinessType(),
				new Integer[] { 120, 121, 121 });
		// 个人付款到银行卡出款成功
		peParams.put(WithdrawBusinessType.PAYTOBANK_ORDER_SUCC_PERSON
				.getBusinessType(), new Integer[] { 120, 121, 121 });
		// 个人付款到银行卡出款失败
		peParams.put(WithdrawBusinessType.PAYTOBANK_ORDER_FAIL_PERSON
				.getBusinessType(), new Integer[] { 120, 121, 121 });
		peParams.put(
				WithdrawBusinessType.PAYTOBANK_ORDER_FAIL_PERSON
						.getBusinessType() + "0",
				new Integer[] { 120, 122, 122 });

		// 付款到账户
		peParams.put(
				WithdrawBusinessType.PAYTOACCT_ORDER_PERSON_I.getBusinessType(),
				new Integer[] { 160, 161, 161 });
		peParams.put(
				WithdrawBusinessType.PAYTOACCT_ORDER_PERSON_O.getBusinessType(),
				new Integer[] { 160, 161, 161 });

		// 批量付款到账户提交请求
		peParams.put(WithdrawBusinessType.PAYTOACCT_BATCHORDER_REQ_PERSON
				.getBusinessType(), new Integer[] { 170, 170, 170 });
		// 批量付款到账户订单成功
		peParams.put(WithdrawBusinessType.PAYTOACCT_BATCHORDER_SUCC_PERSON
				.getBusinessType(), new Integer[] { 170, 170, 170 });
		// 批量付款到账户订单失败
		peParams.put(WithdrawBusinessType.PAYTOACCT_BATCHORDER_FAIL_PERSON
				.getBusinessType(), new Integer[] { 170, 170, 170 });
		// 信用卡还款提交请求
		peParams.put(
				WithdrawBusinessType.CREDITCARD_REPAY_REQ.getBusinessType(),
				new Integer[] { 190, 191, 191 });
		// 信用卡还款订单成功
		peParams.put(
				WithdrawBusinessType.CREDITCARD_REPAY_SUCC.getBusinessType(),
				new Integer[] { 190, 191, 191 });
		// 信用卡还款订单失败
		peParams.put(
				WithdrawBusinessType.CREDITCARD_REPAY_FAIL.getBusinessType(),
				new Integer[] { 190, 191, 191 });

		// 批量付款到银行订单失败
		peParams.put(
				WithdrawBusinessType.MASSPAYTOBANK_ORDER_FAIL.getBusinessType(),
				new Integer[] { 140, 141, 141 });
		peParams.put(
				WithdrawBusinessType.MASSPAYTOBANK_ORDER_FAIL.getBusinessType()
						+ "0", new Integer[] { 140, 142, 142 });

		// 批量付款到银行订单拒绝
		peParams.put(WithdrawBusinessType.MASSPAYTOBNAK_ORDER_REJECT
				.getBusinessType(), new Integer[] { 140, 141, 141 });
		peParams.put(
				WithdrawBusinessType.MASSPAYTOBNAK_ORDER_REJECT
						.getBusinessType() + "0",
				new Integer[] { 140, 142, 142 });

		allowRequest = new HashSet(peParams.keySet());
	}

	@Override
	public void processCancelOrderRnTx(BackFundmentOrder backFundOrder)
			throws PossException {
		String getAppType = backFundOrder.getAppType();
		if (allowRequest.contains(getAppType) == false) {
			log.error("必须指定业务类型 [" + getAppType + "]，且可选值为 " + allowRequest);
			throw new IllegalArgumentException("必须指定业务类型 [" + getAppType
					+ "]，且可选值为 " + allowRequest);
		}

		backFundOrder
				.setUniqueKy(String.valueOf(backFundOrder.getSequenceSrc()));
		// 保存退款订单(无论如何,都应该保存一笔退款订单,所以REQUIRES_NEW)
		boolean result = orderDaoService
				.saveBackFundingOrderRnTx(backFundOrder);

		if (!result) {
			throw new PossException("原订单号已存在,请不要重复提交!",
					ExceptionCodeEnum.ILLEGAL_PARAMETER);
		}

		handleAfterRdTx(backFundOrder);
	}

	private void handleAfterRdTx(BackFundmentOrder backFundOrder)
			throws PossException {
		HandlerParam param = new HandlerParam();
		param.setWithdrawBusinessType(backFundOrder.getAppType());
		param.setOrderStatus(Constants.ORDER_STATUS_SUCC);// 默认下成功记账
		param.setBaseOrderDto(backFundOrder);
		backFundOrder.setStatus(Constants.ORDER_STATUS_SUCC);
		orderDaoService.updateBackFundingOrderRnTx(backFundOrder);

		// 余额更新与订单状态须在一个事务，所以更新失败回滚
		if (AccountingResult.ACCOUNTING_FAIL.getResult().intValue() == (accounting(param))
				.intValue()) {
			LogUtil.info(DefaultBackFundingInnerServiceImpl.class,
					"退款订单余额更新失败", OPSTATUS.START,
					"handleAfterRdTx(BackFundmentOrder backFundOrder)",
					"退款订单号:" + backFundOrder.getSequenceId() + "");
			backFundOrder.setUniqueKy(backFundOrder.getSequenceId() + ""
					+ backFundOrder.getSequenceSrc());
			backFundOrder.setStatus(Constants.ORDER_STATUS_INIT);
			orderDaoService.updateBackFundingOrderRnTx(backFundOrder);
			throw new PossException("退款订单余额更新失败 [" + backFundOrder + "]",
					ExceptionCodeEnum.ACCTOUNTING_PROCESS_EXCEPTION);
		}
	}

	private OrderFailProcAlertModel buildAlertInfo(
			BackFundmentOrder backFundOrder) {
		OrderFailProcAlertModel result = new OrderFailProcAlertModel();
		result.setOrderSeq(backFundOrder.getSequenceId());
		result.setOrderStatus(backFundOrder.getStatus().intValue());
		result.setFailReason(backFundOrder.getReasons());
		result.setAppFrom(backFundOrder.getAppType());
		result.setUpdateTime(new Date());
		return result;
	}

	public void setOrderDaoService(BackFundingOrderDaoService orderDaoService) {
		this.orderDaoService = orderDaoService;
	}

	public void setFailHandler(OrderAfterFailProcHandler failHandler) {
		this.failHandler = failHandler;
	}

	@Override
	protected CalFeeRequestDTO buildCalFeeRequestDTO(HandlerParam param) {
		BackFundmentOrder order = (BackFundmentOrder) param.getBaseOrderDto();

		CalFeeRequestDTO result = new CalFeeRequestDTO();
		result.setOrderId(order.getSequenceId().toString());
		result.setSubmitAcctCode(order.getPayerMember().toString()
				+ order.getPayerAcctType());

		result.setHasCaculatedPrice(true);// 标识为已算费

		result.setAmount(order.getAppAmount().longValue());
		result.setOrderAmount(order.getAppAmount().longValue());
		result.setPayerFee(order.getAppFee().longValue());

		if (param.getWithdrawBusinessType().equals(
				WithdrawBusinessType.MASSPAYTOBNAK_ORDER_REJECT
						.getBusinessType())
				|| param.getWithdrawBusinessType().equals(
						WithdrawBusinessType.MASSPAYTOBANK_ORDER_FAIL
								.getBusinessType())
				|| param.getWithdrawBusinessType().equals(
						WithdrawBusinessType.PAYTOBANK_ORDER_FAIL_PERSON
								.getBusinessType())) {
			String key = param.getWithdrawBusinessType();
			if (order.getFeeSrc().longValue() < 0) {
				result.setOrderAmount(order.getAppAmount().longValue()
						+ order.getFeeSrc().longValue());// 收款方付手续费时，订单金额等于出款金额+手续费，否则订单金额等于出款金额
				key = param.getWithdrawBusinessType() + "0";
			}
			result.setOrderCode(peParams.get(key)[0]);
			result.setDealCode(peParams.get(key)[1]);
			result.setPaymentServicePkgCode(peParams.get(key)[2].toString());
		} else {
			result.setOrderCode(peParams.get(param.getWithdrawBusinessType())[0]);
			result.setDealCode(peParams.get(param.getWithdrawBusinessType())[1]);
			result.setPaymentServicePkgCode(peParams.get(param
					.getWithdrawBusinessType())[2].toString());
		}

		result.setPayMethod(1);
		result.setRequestDate(order.getUpdateTime());

		result.setPayerMemberAcctCode(order.getPayerMember().toString()
				+ order.getPayerAcctType());
		result.setPayer(order.getPayerMember().toString()
				+ order.getPayerAcctType());
		result.setPayerAcctType(order.getPayerAcctType().toString());
		result.setPayerFullMemberAcctCode(order.getPayerAcctCode());
		result.setPayerOrgType("1");// 会员机构类型为1
		result.setPayerServiceLevel(super.buildCalFeeServiceLevel(result,
				order.getPayerMember()));

		// 收款方：付款业务--批量内转科目 
		String subjectCode = order.getPayeeAcctCode();
		result.setPayeeOrgCode(subjectCode.substring(4, subjectCode.length()));// 科目后3级
		result.setPayeeOrgType("3");// 科目机构类型为3
		result.setPayeeServiceLevel(-1);
		result.setPayeeCurrencyCode("CNY");

		return result;
	}

}
