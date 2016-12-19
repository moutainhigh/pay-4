package com.pay.fundout.withdraw.validate.rule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fundout.withdraw.service.autoriskcontrol.AutoRiskControlService;
import com.pay.inf.rule.MessageRule;

/**
 * 自动风控(反洗钱类监控-24小时内企业会员账户出款到银行卡或提现累计超过200万)
 */
public class Pay2bankAmountAddupCheckRule extends MessageRule {
	
	private Log logger = LogFactory.getLog(Pay2bankAmountAddupCheckRule.class);

	private AutoRiskControlService autoRiskControlService;

	public void setAutoRiskControlService(AutoRiskControlService autoRiskControlService) {
		this.autoRiskControlService = autoRiskControlService;
	}

	@Override
	protected boolean makeDecision(Object order) throws Exception {

		FundoutOrderDTO fundoutOrderDTO = (FundoutOrderDTO) order;
		String message = autoRiskControlService.validateFundoutAmount(fundoutOrderDTO.getPayerMemberCode());
		fundoutOrderDTO.setFailedReason(message);
		if (null != message) {
			logger.info("反洗钱类监控-24小时内企业会员账户出款到银行卡或提现累计超过200万");
			return false;
		} else {
			return true;
		}
	}

}
