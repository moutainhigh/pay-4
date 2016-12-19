package com.pay.fundout.withdraw.validate.rule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fundout.withdraw.service.autoriskcontrol.AutoRiskControlService;
import com.pay.inf.rule.MessageRule;

/**
 * 系统交易类监控-转账类预警-同一企业账户24小时内收到超过10次个人账户的转账
 */
public class TransferCountCheckRule extends MessageRule {

	private Log logger = LogFactory.getLog(TransferCountCheckRule.class);

	private AutoRiskControlService autoRiskControlService;

	public void setAutoRiskControlService(AutoRiskControlService autoRiskControlService) {
		this.autoRiskControlService = autoRiskControlService;
	}

	@Override
	protected boolean makeDecision(Object order) throws Exception {
		FundoutOrderDTO fundoutOrderDTO = (FundoutOrderDTO) order;
		String message = autoRiskControlService.validateReceivedPersonAccTransferTimes(fundoutOrderDTO.getPayerMemberCode());
		fundoutOrderDTO.setFailedReason(message);
		if (null != message) {
			logger.info("转账类预警-同一企业账户24小时内收到超过10次个人账户的转账");
			return false;
		} else {
			return true;
		}
	}

}
