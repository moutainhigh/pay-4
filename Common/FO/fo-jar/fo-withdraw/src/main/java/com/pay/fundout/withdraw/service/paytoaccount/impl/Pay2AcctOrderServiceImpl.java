package com.pay.fundout.withdraw.service.paytoaccount.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fundout.service.OrderStatus;
import com.pay.fundout.withdraw.model.paytoaccount.Pay2AcctOrder;
import com.pay.fundout.withdraw.service.paytoaccount.Pay2AcctOrderService;
import com.pay.fundout.withdraw.service.paytoaccount.Pay2AcctService;
import com.pay.inf.dao.BaseDAO;
import com.pay.pe.dto.AccountingDto;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.base.common.Constants;
import com.pay.poss.common.accounting.WithdrawBusinessType;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.external.service.ordercallback.OrderCallBackService;
import com.pay.poss.service.withdraw.orderafterproc.OrderAfterProcService;

public class Pay2AcctOrderServiceImpl implements Pay2AcctOrderService {
	private Log log = LogFactory.getLog(Pay2AcctOrderServiceImpl.class);
	private OrderAfterProcService afterProcService;
	private OrderCallBackService callBackService;
	private AccountingService acctServiceWithMM;
	private AccountingService acctServiceWithSM;
	private AccountingService acctServiceWithSMFail;
	private AccountingService acctServiceWithMS;
	private AccountingService fundAdjustmentReqAccountService;
	private BaseDAO daoService;

	@Override
	public boolean handleOrderRnTx(Pay2AcctOrder order, String requestFrom) {
		HandlerParam param = new HandlerParam();
		param.setWithdrawBusinessType(requestFrom);
		param.setOrderStatus(order.getStatus());
		param.setBaseOrderDto(order);

		// update terry_ma
		AccountingDto accountingDto = new AccountingDto();
		accountingDto.setAmount(order.getAmount());
		accountingDto.setOrderAmount(order.getAmount());
		accountingDto.setOrderId(order.getSequenceId());
		accountingDto.setPayer(order.getPayerMember());
		accountingDto.setPayee(order.getPayeeMember());
		param.setAccountingDto(accountingDto);

		if (Pay2AcctService.REQUEST_CODE_FOR_BATCH_FIRST.equals(requestFrom)) {
			// 批量调用第一步为:会员-中间科目
			return afterProcService.process(param, callBackService,
					acctServiceWithMS);
		}
		if (Pay2AcctService.REQUEST_CODE_FOR_BATCH_SECOND.equals(requestFrom)) {
			// 批量调用第2步为:中间科目-会员
			order.setStatus(Integer.valueOf(111));
			return afterProcService.process(param, callBackService,
					acctServiceWithSM);
		} else if (WithdrawBusinessType.PAYTOACCT_BATCHORDER_FAIL_PERSON
				.getBusinessType().equals(requestFrom)) {
			// batch pay 2 step back_fundment_order
			param.getAccountingDto().setPayee(order.getPayerMember());
			// 交易类型
			return afterProcService.process(param, callBackService,
					acctServiceWithSMFail);
		} else if (WithdrawBusinessType.FUNDADJUSTMENT_ORDER_REQ
				.getBusinessType().equalsIgnoreCase(requestFrom)) {// 资金调账申请
			// TODO
			// accountingDto.setBusinessType(PayForEnum.FO_PAYTO_ACCT.getCode());
			return afterProcService.process(param, callBackService,
					fundAdjustmentReqAccountService);

		} else {
			// member---->member
			return afterProcService.process(param, callBackService,
					acctServiceWithMM);
		}
	}

	@Override
	public boolean createOrderRnTx(Pay2AcctOrder order, String requestFrom) {
		if (Pay2AcctService.REQUEST_CODE_FOR_BATCH_FIRST.equals(requestFrom)) {
			return true;
		} else if (Pay2AcctService.REQUEST_CODE_FOR_BATCH_SECOND
				.equals(requestFrom)) {
			order.setStatus(Constants.ORDER_STATUS_INIT);
		} else if (Pay2AcctService.FUNDADJUSTMENT_ORDER_REQ
				.equalsIgnoreCase(requestFrom)) {
			order.setStatus(OrderStatus.INIT.getValue());
		} else {
			order.setRequestFrom(requestFrom);
		}
		try {
			long orderId = (Long) daoService.create("pay2acct.insertOrder",
					order);
			order.setSequenceId(orderId);
			return true;
		} catch (Exception e) {
			log.error("付款到账户保持订单失败 [" + order + "]", e);
			order.setStatus(Constants.ORDER_STATUS_FAIL);
			order.setErrorTips("付款到账户保持订单失败");
			return false;
		}
	}

	public void setAfterProcService(OrderAfterProcService afterProcService) {
		this.afterProcService = afterProcService;
	}

	public void setCallBackService(OrderCallBackService callBackService) {
		this.callBackService = callBackService;
	}

	public void setAcctServiceWithMM(AccountingService acctServiceWithMM) {
		this.acctServiceWithMM = acctServiceWithMM;
	}

	public void setAcctServiceWithSM(AccountingService acctServiceWithSM) {
		this.acctServiceWithSM = acctServiceWithSM;
	}

	public void setAcctServiceWithMS(AccountingService acctServiceWithMS) {
		this.acctServiceWithMS = acctServiceWithMS;
	}

	public void setDaoService(BaseDAO daoService) {
		this.daoService = daoService;
	}

	public void setAcctServiceWithSMFail(AccountingService acctServiceWithSMFail) {
		this.acctServiceWithSMFail = acctServiceWithSMFail;
	}

	public void setFundAdjustmentReqAccountService(
			AccountingService fundAdjustmentReqAccountService) {
		this.fundAdjustmentReqAccountService = fundAdjustmentReqAccountService;
	}

}
