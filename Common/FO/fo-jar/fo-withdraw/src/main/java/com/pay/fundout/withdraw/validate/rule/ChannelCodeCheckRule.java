package com.pay.fundout.withdraw.validate.rule;

import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.service.base.FundoutOrderService;
import com.pay.fundout.channel.model.channel.ChannelRequest;
import com.pay.fundout.channel.service.channel.ChannelService;
import com.pay.inf.rule.MessageRule;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;

/**
 * 更新订单渠道编号
 */
public class ChannelCodeCheckRule extends MessageRule {

	private FundoutOrderService fundoutOrderService;
	private ChannelService channelService;

	@Override
	protected boolean makeDecision(Object order) throws Exception {

		FundoutOrderDTO fundoutOrderDTO = (FundoutOrderDTO) order;
		ChannelRequest request = new ChannelRequest();
		request.setProductCode(fundoutOrderDTO.getOrderType().toString());
		request.setTargetBank(fundoutOrderDTO.getPayeeBankCode());
		request.setChannelFlag(0);
		String channelId = channelService.getFundoutChannel(request);

		boolean bl = false;
		if (null != channelId) {
			bl = true;
		} else {
			request.setChannelFlag(1);
			channelId = channelService.getFundoutChannel(request);
		}

		try {
			// 目前只支持目的行到出款行
			fundoutOrderDTO.setChannelCode(channelId);
			fundoutOrderDTO.setFundoutBankCode(fundoutOrderDTO.getPayeeBankCode());
			fundoutOrderService.updateOrder(fundoutOrderDTO);
		} catch (Exception e) {
			throw new PossException("update fundoutOrder ChannelCode error...", ExceptionCodeEnum.DATA_ACCESS_EXCEPTION, e);
		}

		if (bl) {
			return true;
		} else {
			return false;
		}
	}

	public void setFundoutOrderService(FundoutOrderService fundoutOrderService) {
		this.fundoutOrderService = fundoutOrderService;
	}

	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}

}
