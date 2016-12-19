package com.pay.fundout.withdraw.service.paytoaccount.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.pay.fundout.withdraw.model.paytoaccount.Pay2AcctOrder;
import com.pay.fundout.withdraw.model.paytoaccount.Pay2AcctResponse;
import com.pay.fundout.withdraw.service.paytoaccount.Pay2AcctOrderService;
import com.pay.fundout.withdraw.service.paytoaccount.Pay2AcctService;
import com.pay.inf.dao.BaseDAO;
import com.pay.poss.base.common.Constants;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;

public class Pay2AcctServiceImpl implements Pay2AcctService {
	private Pay2AcctOrderService afterService;
	private Set<String> allowRequest = new HashSet<String>();
	private BaseDAO daoService;

	public Pay2AcctServiceImpl() {
		allowRequest.add(REQUEST_CODE_FOR_APP);
		allowRequest.add(REQUEST_CODE_FOR_FO);
		allowRequest.add(REQUEST_CODE_FOR_BATCH_FIRST);
		allowRequest.add(REQUEST_CODE_FOR_BATCH_SECOND);
	}

	@Override
	public Pay2AcctResponse pay2Acct(Pay2AcctOrder order) {
		String requestFrom = order.getRequestFrom();
		if (allowRequest.contains(requestFrom) == false) {
			LogUtil.error(Pay2AcctServiceImpl.class, order.toString(),
					OPSTATUS.START, "", "无效请求标识", null, null, null);
			order.setErrorTips("必须指定请求标识 [" + requestFrom + "]，且可选值为 "
					+ allowRequest);
			order.setStatus(Constants.ORDER_STATUS_FAIL);
			return buildResponse(order);
		}
		// try {
		// if (REQUEST_CODE_FOR_BATCH_FIRST.equals(requestFrom) ||
		// REQUEST_CODE_FOR_BATCH_SECOND.equals(requestFrom)) {
		// securityCheckCommittee.decide(buildPrincipal(order));
		// }
		// } catch (DeniedException e) {
		// LogUtil.error(Pay2AcctServiceImpl.class, order.toString(),
		// OPSTATUS.START, "", "安全校验失败", null, null, null);
		// order.setErrorTips(ErrorTipUtil.getErrorTip(e));
		// order.setStatus(Constants.ORDER_STATUS_FAIL);
		// if (REQUEST_CODE_FOR_BATCH_FIRST.equals(requestFrom) ||
		// REQUEST_CODE_FOR_APP.equals(requestFrom)) {
		// return buildResponse(order);
		// } else if (REQUEST_CODE_FOR_BATCH_SECOND.equals(requestFrom)) {
		// requestFrom =
		// WithdrawBusinessType.PAYTOACCT_BATCHORDER_FAIL_PERSON.getBusinessType();
		// }
		// }
		boolean flag = afterService.createOrderRnTx(order, requestFrom);
		if (flag == true) {
			flag = afterService.handleOrderRnTx(order, requestFrom);
		} else {
			order.setErrorTips("付款到账户失败");
			LogUtil.error(Pay2AcctServiceImpl.class, order.toString(),
					OPSTATUS.START, "", "保存付款到账户订单失败 ", null, null, null);
		}
		return buildResponse(order);
	}

	private Pay2AcctResponse buildResponse(Pay2AcctOrder order) {
		Pay2AcctResponse result = new Pay2AcctResponse();
		result.setSequenceId(order.getSequenceId());
		result.setTradeDate(order.getUpdateDate());
		result.setStatus(order.getStatus());
		result.setErrotTip(order.getErrorTips());
		return result;
	}

	public void setAfterService(Pay2AcctOrderService afterService) {
		this.afterService = afterService;
	}

	public void setDaoService(BaseDAO daoService) {
		this.daoService = daoService;
	}

	@Override
	public Pay2AcctOrder findById(Long sequenceId) {
		Object pay2AcctOrder = null;
		List list = daoService.findByQuery("pay2acct.findById", sequenceId);
		if (list != null && list.size() > 0) {
			pay2AcctOrder = daoService.findByQuery("pay2acct.findById",
					sequenceId).get(0);
		}
		if (pay2AcctOrder == null) {
			return null;
		} else {
			return (Pay2AcctOrder) pay2AcctOrder;
		}
	}

}
