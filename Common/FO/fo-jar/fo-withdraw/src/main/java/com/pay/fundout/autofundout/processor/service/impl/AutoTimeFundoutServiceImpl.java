 /** @Description 
 * @project 	fo-withdraw
 * @file 		AutoTimeFundoutServiceImpl.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-12-11		Henry.Zeng			Create 
*/
package com.pay.fundout.autofundout.processor.service.impl;

import com.pay.fundout.autofundout.custom.model.AutoFundoutResult;
import com.pay.fundout.autofundout.processor.service.AbstractAutoFundoutService;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;

/**
 * <p></p>
 * @author Henry.Zeng
 * @since 2010-12-11
 * @see 
 */
public class AutoTimeFundoutServiceImpl extends AbstractAutoFundoutService {


	@Override
	protected boolean autoCheckAmount(AutoFundoutResult param) {
		//预留金额
		Long tempAmount = param.getAutoFundoutConfig().getRetainedAmount();
		
		Long memberCode = param.getAutoFundoutConfig().getMemberCode();
		Integer accountType = param.getAutoFundoutConfig().getAccType();
		//可提现金额
		Long balance = this.withdrawMemberFacadeService.getWithdrawAmount(memberCode,accountType);
		
		// 期结提现判断 update by terry_ma
		Integer settleFlag = param.getAutoTimeConfig().getSettleFlag();
		//TODO 上日日终金额
		Long settleAmount = withdrawMemberFacadeService.doQueryBalanceByEntryRntx(memberCode, accountType);
		
		Long amount = 0L;
		if(1 == settleFlag){
			
			if(logger.isInfoEnabled()){
				logger.info("auto withdraw member:"+ memberCode);
				logger.info("MA return settleAmount:"+ settleAmount);
				logger.info("member balance:" + balance);
			}
			
			amount = balance > settleAmount ? settleAmount : balance;
		}else{
			//计算提现金额
			amount = Long.valueOf(balance.longValue() - tempAmount.longValue());
		}

		//如果提现金额小于等于零，则验证失败，否则通过
		if(amount.longValue() > 0){
			param.setAmount(amount);
			return true;
		}else{
			param.setErrorMessage("提现金额小于零!");
			LogUtil.error(AutoTimeFundoutServiceImpl.class,"定期提现金额小于零",OPSTATUS.FAIL,param.getAutoFundoutConfig().getMemberCode().toString(),
					"[memberCode=" + param.getAutoFundoutConfig().getMemberCode() + "&memberType=" + 
					param.getAutoFundoutConfig().getMemberType() + "&bankName=" + param.getAutoFundoutConfig().getBankName() + 
					"&bankCode=" + param.getAutoFundoutConfig().getBankCode() + "&(可提现金额(" + balance + 
					")-留存金额(" + tempAmount + ")=提现金额(" + amount + ")",param.getErrorMessage(),"",param.getErrorMessage());
			
			String title = "委托提现失败";
			String errorDesc = "[余额不足]";
			
			param.setTitle(title);
			param.setAmount(amount);
			param.setErrorDesc(errorDesc);
			return false;
		}
	}

	
}
