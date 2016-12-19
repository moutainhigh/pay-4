package com.pay.fundout.withdraw.validate.action;

import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.service.base.FundoutOrderService;
import com.pay.inf.rule.AbstractAction;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;

/**
 * 更新订单失败原因
 */
public class FailedReasonSettingAction extends AbstractAction {

	private FundoutOrderService fundoutOrderService;

	@Override
	protected void doExecute(Object order) throws Exception {

		FundoutOrderDTO fundoutOrderDTO = (FundoutOrderDTO) order;
		try {
			fundoutOrderService.updateOrder(fundoutOrderDTO);
		} catch (Exception e) {
			throw new PossException("update fundoutOrder ChannelCode error...", ExceptionCodeEnum.DATA_ACCESS_EXCEPTION, e);
		}
	}

	public void setFundoutOrderService(FundoutOrderService fundoutOrderService) {
		this.fundoutOrderService = fundoutOrderService;
	}

}
