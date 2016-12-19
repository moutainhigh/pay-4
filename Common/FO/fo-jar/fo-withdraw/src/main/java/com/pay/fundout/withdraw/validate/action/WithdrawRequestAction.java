package com.pay.fundout.withdraw.validate.action;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.pay.fo.bankcorp.dto.BankCorpPaymentReqDTO;
import com.pay.fo.bankcorp.dto.BankCorpPaymentRespDTO;
import com.pay.fo.bankcorp.service.BankCorporateProcessService;
import com.pay.fo.order.common.OrderStatus;
import com.pay.fundout.withdraw.service.flowprocess.WithdrawService;
import com.pay.inf.rule.AbstractAction;
import com.pay.poss.base.exception.PossException;

/**
 * 请求直联出款处理
 */
public class WithdrawRequestAction extends AbstractAction {
	final Logger log = LoggerFactory.getLogger(getClass());

	private BankCorporateProcessService bankCorporateProcessService;
	private WithdrawService withdrawService;

	public void setBankCorporateProcessService(BankCorporateProcessService bankCorporateProcessService) {
		this.bankCorporateProcessService = bankCorporateProcessService;
	}

	public void setWithdrawService(WithdrawService withdrawService) {
		this.withdrawService = withdrawService;
	}

	@Override
	protected void doExecute(Object order) throws Exception {
		BankCorpPaymentReqDTO reqDTO = new BankCorpPaymentReqDTO();
		BeanUtils.copyProperties(order, reqDTO);

		BankCorpPaymentRespDTO resp = bankCorporateProcessService.doPayment(reqDTO);

		Map<String, String> param = new HashMap<String, String>();
		param.put("orderId", resp.getTradeOrderId().toString());
		String isSuccess = "0";
		if (resp.getOrderStatus() == OrderStatus.PROCESSED_SUCCESS.getValue()) {
			isSuccess = "1";
		} else if (resp.getOrderStatus() == OrderStatus.PROCESSED_FAILURE.getValue()) {
			isSuccess = "2";
			param.put("failReason", resp.getFailedReason());
		} else {
			log.error("无效的请求参数:" + resp);
			return;
		}
		param.put("isSuccess", isSuccess);
		try {
			withdrawService.bankBackRdTx(param);
		} catch (PossException e) {
			log.error("银企直连处理发生异常", e);
		}
	}

}
