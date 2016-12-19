
package com.pay.fundout.autofundout.processor.service.impl;

import java.util.Date;

import com.pay.fundout.autofundout.custom.model.AutoFundoutResult;
import com.pay.fundout.autofundout.processor.service.AbstractAutoFundoutService;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.util.DateUtil;
/**
 * 
 * @author NEW
 *
 */
public class AutoMoreTimeFundoutServiceImpl extends AbstractAutoFundoutService {


	@Override
	protected boolean autoCheckAmount(AutoFundoutResult param) {
		//预留金额
		Long tempAmount = param.getAutoFundoutConfig().getRetainedAmount();
		
		Long memberCode = param.getAutoFundoutConfig().getMemberCode();
		Integer accountType = param.getAutoFundoutConfig().getAccType();
		
		/**
		 * 获取当前日期指定时间点的可提现余额
		 */
		String tempDateStr = DateUtil.formatDateTime("yyyy-MM-dd", new Date());
		Date date = DateUtil.parse("yyyy-MM-dd HH:mm", tempDateStr+" "+param.getAutoTimeConfig().getTimeSource());
		
		//可提现金额
		Long balance = this.withdrawMemberFacadeService.getWithdrawAmount(memberCode,accountType);
		
		Long withdrawAmount = withdrawMemberFacadeService.getTheDateWithdrawBalance(memberCode, accountType,date);
		
		withdrawAmount = balance > withdrawAmount ? withdrawAmount : balance;
		
		//计算提现金额
		Long amount = Long.valueOf(withdrawAmount - tempAmount.longValue());

		//如果提现金额小于等于零，则验证失败，否则通过
		if(amount.longValue() > 0){
			param.setAmount(amount);
			return true;
		}else{
			param.setErrorMessage("提现金额小于零!");
			LogUtil.error(AutoMoreTimeFundoutServiceImpl.class,"定期提现金额小于零",OPSTATUS.FAIL,param.getAutoFundoutConfig().getMemberCode().toString(),
					"[memberCode=" + param.getAutoFundoutConfig().getMemberCode() + "&memberType=" + 
					param.getAutoFundoutConfig().getMemberType() + "&bankName=" + param.getAutoFundoutConfig().getBankName() + 
					"&bankCode=" + param.getAutoFundoutConfig().getBankCode() + "&(可提现金额(" + withdrawAmount + 
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
