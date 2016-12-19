/** @Description 
 * @project 	fo-withdraw
 * @file 		AutoQuotaFundoutServiceImpl.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-12-10		Henry.Zeng			Create 
 */
package com.pay.fundout.autofundout.quartz.service.impl;

import java.util.List;

import com.pay.fundout.autofundout.custom.model.AutoFundoutResult;
import com.pay.fundout.autofundout.processor.util.AutoFundoutType;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;

/**
 * <p>
 * 定额自动出款
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-12-10
 * @see
 */
public class QuotaQuartzServiceImpl extends AutofundoutAbstractService {

	@Override
	public void executeStartQuartz() {

		LogUtil.info(this.getClass(), "定额自动出款启动", OPSTATUS.SUCCESS, "", "");

		List<AutoFundoutResult> resultList = getAutoWithdrawService()
				.queryAutoQuotaFundoutResult();

		if (resultList == null || resultList.size() == 0) {
			return;
		}

		for (AutoFundoutResult result : resultList) {

			Long memberCode = result.getAutoFundoutConfig().getMemberCode();
			String bankAcctCode = result.getAutoFundoutConfig()
					.getBankAccCode();

			// 如果提现银行卡与ma绑定改变不作提现
			if (!verifyBindBankCard(memberCode, bankAcctCode)) {
				continue;
			}
			
			// 如果企业开通企业自动业务
			if(!isHaveProduct(memberCode)){
				continue;
			}
			result.setAutoType(AutoFundoutType.AUTO_QUOTA.getCode());
			LogUtil.info(this.getClass(), "定额自动出款启动", OPSTATUS.SUCCESS, result
					.getAutoQuotaConfig().getSequenceid()
					+ ","
					+ result.getAutoQuotaConfig().getBaseAmount()
					+ ","
					+ result.getAutoQuotaConfig().getConfigId(), "");
			getNotifyFacadeService().sendRequest(
					buildNotify2QueueRequest(result));
		}

	}
}
