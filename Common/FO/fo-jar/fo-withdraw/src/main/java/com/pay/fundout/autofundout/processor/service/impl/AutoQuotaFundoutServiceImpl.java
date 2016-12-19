 /** @Description 
 * @project 	fo-withdraw
 * @file 		AutoQuotaFundoutServiceImpl.java 
 * Copyright © 2006-2010 pay Corporation. All rights reserved
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
public class AutoQuotaFundoutServiceImpl extends AbstractAutoFundoutService {

	/* (non-Javadoc)
	 * @see com.pay.fundout.autofundout.processor.service.AbstractAutoFundoutService#autoCheckAmount(com.pay.fundout.autofundout.custom.model.AutoFundoutResult)
	 */
	@Override
	protected boolean autoCheckAmount(AutoFundoutResult param) {
		
		//可提现金额
		Long withdrawAmount = this.withdrawMemberFacadeService.getWithdrawAmount(param.getAutoFundoutConfig().getMemberCode(),param.getAutoFundoutConfig().getAccType());
		
		if(withdrawAmount >= param.getAutoQuotaConfig().getBaseAmount()+param.getAutoFundoutConfig().getRetainedAmount()){
			param.setAmount(param.getAutoQuotaConfig().getBaseAmount());
			return true;
		}else{
			param.setErrorMessage("定额可提现金额小于提现金额+留存金额!");
			LogUtil.error(AutoQuotaFundoutServiceImpl.class,"定额可提现金额小于提现金额+留存金额",OPSTATUS.FAIL,param.getAutoFundoutConfig().getMemberCode().toString(),
					"[memberCode=" + param.getAutoFundoutConfig().getMemberCode() + "&memberType=" + 
					param.getAutoFundoutConfig().getMemberType() + "&bankName=" + param.getAutoFundoutConfig().getBankName() + 
					"&bankCode=" + param.getAutoFundoutConfig().getBankCode() + 
					"&可提现金额(" + withdrawAmount + ")>=提现金额(" + param.getAutoQuotaConfig().getBaseAmount() + 
					")+留存金额(" + param.getAutoFundoutConfig().getRetainedAmount() + ")",param.getErrorMessage(),"",param.getErrorMessage());
			
			String title = "委托提现失败";
			String errorDesc = "[余额不足]";
			
			param.setTitle(title);
			param.setAmount(withdrawAmount);
			param.setErrorDesc(errorDesc);
			return false;
		}
		
	}}
