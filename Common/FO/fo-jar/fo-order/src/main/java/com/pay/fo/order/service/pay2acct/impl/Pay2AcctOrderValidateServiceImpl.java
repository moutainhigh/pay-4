/**
 * 
 */
package com.pay.fo.order.service.pay2acct.impl;

import com.pay.fo.order.common.OrderType;
import com.pay.fo.order.service.base.PayToAcctOrderService;
import com.pay.fo.order.service.pay2acct.Pay2AcctOrderValidateService;
import com.pay.fundout.util.AmountUtils;
import com.pay.rm.facade.dto.RCLimitResultDTO;
import com.pay.util.StringUtil;

/**
 * @author NEW
 *
 */
public class Pay2AcctOrderValidateServiceImpl implements
		Pay2AcctOrderValidateService {
	
	private PayToAcctOrderService payToAcctOrderService;

	@Override
	public String validateRCLimitInfo(long memberCode,long paymentAmount,RCLimitResultDTO rcLimitInfo) {
		String message = null;
		if(null==rcLimitInfo){
			message = "暂不支持该业务";
		}else{
			if(paymentAmount>rcLimitInfo.getSingleLimit()){//单笔限额
				message = "单笔最多付款"+AmountUtils.numberFormat(rcLimitInfo.getSingleLimit())+"元";
			}
			if(StringUtil.isNull(message)){
				Integer monthTimes = payToAcctOrderService.countCurrentMonthPaymentTimes(OrderType.PAY2ACCT.getValue(),memberCode);
				Integer dayTimes = payToAcctOrderService.countCurrentDayPaymentTimes(OrderType.PAY2ACCT.getValue(),memberCode);
				int monthLimitTimes = rcLimitInfo.getMonthTimes() - monthTimes.intValue();
				int dayLimitTimes = rcLimitInfo.getDayTimes() - dayTimes.intValue();
				if(monthLimitTimes<dayLimitTimes){//如果每月剩余次数小于每日剩余次数,则先校验每月次数
					if(monthTimes+1 > rcLimitInfo.getMonthTimes()){ //每月限次  每月最多付款${pay2BankMonthLimitTimes}次，您今天还可以付款${allowPaymentTimes}次
						if(monthLimitTimes<0){
							monthLimitTimes = 0;
						}
						message = "每月最多付款"+rcLimitInfo.getMonthTimes()+"次，您今天还可以付款"+monthLimitTimes+"次";
					}
				}else{
					if(dayTimes+1> rcLimitInfo.getDayTimes()){ //每日限次
						if(dayLimitTimes<0){
							dayLimitTimes = 0;
						}
						message = "每日最多付款"+rcLimitInfo.getDayTimes()+"次，您今天还可以付款"+dayLimitTimes+"次";
					}
				}
			}
			
			if(StringUtil.isNull(message)){
				Long currentDayAmount = payToAcctOrderService.sumCurrentDayPaymentAmount(OrderType.PAY2ACCT.getValue(),memberCode);
				Long currentMonthAmount = payToAcctOrderService.sumCurrentMonthPaymentAmount(OrderType.PAY2ACCT.getValue(),memberCode);
				long monthLimitAmount = rcLimitInfo.getMonthLimit()-currentMonthAmount.longValue();
				long dayLimitAmount = rcLimitInfo.getDayLimit()-currentDayAmount.longValue();
				if(monthLimitAmount<dayLimitAmount){//如果每月剩余限额小于每日剩余限额,则先校验每月限额
					if(paymentAmount+currentMonthAmount > rcLimitInfo.getMonthLimit()){//每月限额
						if(monthLimitAmount<0){
							monthLimitAmount = 0;
						}
						message = "每月最多付款"+AmountUtils.numberFormat(rcLimitInfo.getMonthLimit())+"元，您今天还可以付款"+AmountUtils.numberFormat(monthLimitAmount)+"元";
					}
				}else{
					if(paymentAmount+currentDayAmount>rcLimitInfo.getDayLimit()){//每日限额
						if(dayLimitAmount<0){
							dayLimitAmount = 0;
						}
						message = "每日最多付款"+AmountUtils.numberFormat(rcLimitInfo.getDayLimit())+"元，您今天还可以付款"+AmountUtils.numberFormat(dayLimitAmount)+"元";
					}
				}
			}
		}
		
		return message;
	}

	/**
	 * @param payToAcctOrderService the payToAcctOrderService to set
	 */
	public void setPayToAcctOrderService(PayToAcctOrderService payToAcctOrderService) {
		this.payToAcctOrderService = payToAcctOrderService;
	}

	
	
	
	

}
