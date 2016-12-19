package com.pay.fundout.withdraw.validate.rule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fundout.withdraw.service.autoriskcontrol.AutoRiskControlService;
import com.pay.inf.rule.MessageRule;

/**
 * 系统交易类监控-充值类预警-24小时内会员账户单笔充值金额达50000元
 */
public class RechargeAmountCheckRule extends MessageRule {
	
	private Log logger = LogFactory.getLog(RechargeAmountCheckRule.class);

	private AutoRiskControlService autoRiskControlService;

	public void setAutoRiskControlService(AutoRiskControlService autoRiskControlService) {
		this.autoRiskControlService = autoRiskControlService;
	}

	@Override
	protected boolean makeDecision(Object order) throws Exception {
		FundoutOrderDTO fundoutOrderDTO = (FundoutOrderDTO) order;
		String message = autoRiskControlService.validateDepositAmount(fundoutOrderDTO.getPayerMemberCode());
		fundoutOrderDTO.setFailedReason(message);
		if (null != message) {
			logger.info("充值类预警-24小时内会员账户单笔充值金额达50000元");
			return false;
		} else {
			return true;
		}
	}

}
