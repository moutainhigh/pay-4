/**
 * 
 */
package com.pay.fundout.withdraw.service.orderconsistency.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fundout.withdraw.model.paytoaccount.Pay2AcctOrder;
import com.pay.fundout.withdraw.service.orderconsistency.PayToAccountConsistencyService;
import com.pay.fundout.withdraw.service.paytoaccount.Pay2AcctOrderService;
import com.pay.inf.dao.BaseDAO;
import com.pay.poss.base.common.Constants;
import com.pay.poss.common.accounting.WithdrawBusinessType;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.external.service.ordercallback.OrderCallBackService;

/**
 * @author jason_li
 * 
 */
public class PayToAccountConsistencyServiceImpl implements
		PayToAccountConsistencyService {
	private Pay2AcctOrderService pay2AcctOrderService;
	private OrderCallBackService orderCallBackService;
	private BaseDAO<Pay2AcctOrder> daoService;
	private Log log = LogFactory
			.getLog(PayToAccountConsistencyServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.fundout.withdraw.service.orderconsistency.
	 * PayToAccountConsistencyService
	 * #createBackFundmentOrder(com.pay.fundout.withdraw
	 * .model.paytoaccount.Pay2AcctOrder)
	 */
	@Override
	public boolean createBackFundmentOrder(long orderId) throws Exception {
		Pay2AcctOrder pay2AcctOrder = daoService.findObjectByCriteria(
				"masspayorderconsistency.findById", orderId);
		pay2AcctOrder
				.setRequestFrom(WithdrawBusinessType.PAYTOACCT_BATCHORDER_FAIL_PERSON
						.getBusinessType());
		HandlerParam param = new HandlerParam();
		param.setBaseOrderDto(pay2AcctOrder);
		param.setOrderStatus(Constants.ORDER_STATUS_FAIL);
		param.setWithdrawBusinessType(WithdrawBusinessType.PAYTOACCT_BATCHORDER_FAIL_PERSON
				.getBusinessType());
		orderCallBackService.processCancelOrderRnTx(param);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.fundout.withdraw.service.orderconsistency.
	 * PayToAccountConsistencyService
	 * #massPayOrderProceedToFail(com.pay.fundout.withdraw
	 * .model.paytoaccount.Pay2AcctOrder)
	 */
	@Override
	public boolean massPayOrderProceedToFail(long orderId) throws Exception {
		Pay2AcctOrder pay2AcctOrder = daoService.findObjectByCriteria(
				"masspayorderconsistency.findById", orderId);
		pay2AcctOrder
				.setRequestFrom(WithdrawBusinessType.PAYTOACCT_BATCHORDER_FAIL_PERSON
						.getBusinessType());
		pay2AcctOrder.setInnerStatus(Constants.ORDER_STATUS_FAIL);
		HandlerParam param = new HandlerParam();
		param.setBaseOrderDto(pay2AcctOrder);
		param.setOrderStatus(Constants.ORDER_STATUS_FAIL);
		param.setWithdrawBusinessType(WithdrawBusinessType.PAYTOACCT_BATCHORDER_FAIL_PERSON
				.getBusinessType());
		orderCallBackService.processOrderRdTx(param, null);
		return true;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.fundout.withdraw.service.orderconsistency.
	 * PayToAccountConsistencyService
	 * #massPayOrderProceedToSuccess(com.pay.fundout
	 * .withdraw.model.paytoaccount.Pay2AcctOrder)
	 */
	@Override
	public boolean massPayOrderProceedToSuccess(long orderId) throws Exception {
		Pay2AcctOrder pay2AcctOrder = daoService.findObjectByCriteria(
				"masspayorderconsistency.findById", orderId);
		pay2AcctOrder.setInnerStatus(Constants.ORDER_STATUS_SUCC);
		pay2AcctOrder
				.setRequestFrom(WithdrawBusinessType.PAYTOACCT_BATCHORDER_SUCC_PERSON
						.getBusinessType());
		return pay2AcctOrderService.handleOrderRnTx(pay2AcctOrder,
				pay2AcctOrder.getRequestFrom());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.fundout.withdraw.service.orderconsistency.
	 * PayToAccountConsistencyService#searchMassPayOrder101(java.util.Map)
	 */
	@Override
	public List<Pay2AcctOrder> searchMassPayOrder101(Map<String, String> map)
			throws Exception {
		return daoService.findByQuery("masspayorderconsistency.searchOrder101",
				map);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.fundout.withdraw.service.orderconsistency.
	 * PayToAccountConsistencyService#searchMassPayOrder112(java.util.Map)
	 */
	@Override
	public List<Pay2AcctOrder> searchMassPayOrder112(Map<String, String> map)
			throws Exception {
		return daoService.findByQuery("masspayorderconsistency.searchOrder112",
				map);
	}

	public void setPay2AcctOrderService(
			Pay2AcctOrderService pay2AcctOrderService) {
		this.pay2AcctOrderService = pay2AcctOrderService;
	}

	public void setOrderCallBackService(
			OrderCallBackService orderCallBackService) {
		this.orderCallBackService = orderCallBackService;
	}

	public void setDaoService(BaseDAO<Pay2AcctOrder> daoService) {
		this.daoService = daoService;
	}

	public void setLog(Log log) {
		this.log = log;
	}

}
