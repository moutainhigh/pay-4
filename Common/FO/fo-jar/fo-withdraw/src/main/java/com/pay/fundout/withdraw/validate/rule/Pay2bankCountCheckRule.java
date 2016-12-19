package com.pay.fundout.withdraw.validate.rule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fundout.withdraw.service.autoriskcontrol.AutoRiskControlService;
import com.pay.inf.rule.MessageRule;

/**
 * 系统交易类监控-交易类预警-企业24小时内向同一银行账户出款超过5笔
 */
public class Pay2bankCountCheckRule extends MessageRule {

	private Log logger = LogFactory.getLog(Pay2bankCountCheckRule.class);

	private AutoRiskControlService autoRiskControlService;

	public void setAutoRiskControlService(AutoRiskControlService autoRiskControlService) {
		this.autoRiskControlService = autoRiskControlService;
	}

	@Override
	protected boolean makeDecision(Object order) throws Exception {
		FundoutOrderDTO fundoutOrderDTO = (FundoutOrderDTO) order;
		String message = autoRiskControlService.validatePayeeBankAcc(fundoutOrderDTO.getPayerMemberCode(), fundoutOrderDTO.getPayeeBankAcctCode());
		fundoutOrderDTO.setFailedReason(message);
		if (null != message) {
			logger.info("交易类预警-企业24小时内向同一银行账户出款超过5笔");
			return false;
		} else {
			return true;
		}
	}

}
