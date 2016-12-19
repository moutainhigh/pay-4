package com.pay.fundout.withdraw.validate.action;

import java.util.HashMap;
import java.util.Map;

import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.service.base.FundoutOrderService;
import com.pay.fundout.channel.service.channel.FundoutChannelService;
import com.pay.inf.rule.AbstractAction;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;

/**
 * 更新订单渠道编号
 */
public class ChannelCodeSettingAction extends AbstractAction {

	private FundoutOrderService fundoutOrderService;
	private FundoutChannelService fundoutChannelService;

	@Override
	protected void doExecute(Object order) throws Exception {

		FundoutOrderDTO fundoutOrderDTO = (FundoutOrderDTO) order;

		Map<String, Object> args = new HashMap<String, Object>();
		args.put("withdrawBankCode", fundoutOrderDTO.getPayeeBankCode());
		args.put("withdrawType", fundoutOrderDTO.getOrderType());
		args.put("busiType", 2);
		String channelId = fundoutChannelService.getChannelId(args);

		// 目前只支持目的行到出款行
		fundoutOrderDTO.setChannelCode(channelId);
		fundoutOrderDTO.setFundoutBankCode(fundoutOrderDTO.getPayeeBankCode());
		try {
			fundoutOrderService.updateOrder(fundoutOrderDTO);
		} catch (Exception e) {
			throw new PossException("update fundoutOrder ChannelCode error...", ExceptionCodeEnum.DATA_ACCESS_EXCEPTION, e);
		}
	}

	public void setFundoutOrderService(FundoutOrderService fundoutOrderService) {
		this.fundoutOrderService = fundoutOrderService;
	}

	public void setFundoutChannelService(FundoutChannelService fundoutChannelService) {
		this.fundoutChannelService = fundoutChannelService;
	}

}
