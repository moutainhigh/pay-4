package com.pay.fundout.withdraw.validate.rule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fo.bankcorp.model.BankChannelConfig;
import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.rule.MessageRule;

/**
 * 直联通道限制
 */
public class ChannelLimitCheckRule extends MessageRule {

	private Log logger = LogFactory.getLog(ChannelLimitCheckRule.class);

	private BaseDAO<BankChannelConfig> bankChannelConfigDao;

	public void setBankChannelConfigDao(
			BaseDAO<BankChannelConfig> bankChannelConfigDao) {
		this.bankChannelConfigDao = bankChannelConfigDao;
	}

	@Override
	protected boolean makeDecision(Object order) throws Exception {
		FundoutOrderDTO fundoutOrderDTO = (FundoutOrderDTO) order;
		BankChannelConfig bankChannelConfig = bankChannelConfigDao
				.findById(fundoutOrderDTO.getChannelCode());
		// 订单金额
		Long orderAmount = fundoutOrderDTO.getOrderAmount();
		// 单笔金额上限，留空为无限制
		Long upperLimit = bankChannelConfig.getUpperLimit();
		// 单笔金额下限，留空为无限制
		// Long lowerLimit = bankChannelConfig.getLowerLimit();
		if (upperLimit != null && upperLimit > 0) {
			if (orderAmount > upperLimit) {
				logger.info("直联通道限制,订单金额大于单笔金额上限");
				fundoutOrderDTO.setFailedReason(getMessageId());
				return false;
			}
		}

		return true;
	}

}
