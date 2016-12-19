/**
 * 
 */
package com.pay.fo.order.service.withdraw.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fo.order.common.OrderType;
import com.pay.fo.order.service.base.FundoutOrderService;
import com.pay.fo.order.service.withdraw.WithdrawOrderValidateService;
import com.pay.fundout.util.AmountUtils;
import com.pay.rm.facade.dto.RCLimitResultDTO;
import com.pay.util.StringUtil;

/**
 * @author NEW
 *
 */
public class WithdrawOrderValidateServiceImpl implements
		WithdrawOrderValidateService {
	
	private FundoutOrderService fundoutOrderService;
	
	@Override
	public String validateRCLimitInfo(long memberCode,long paymentAmount,RCLimitResultDTO rcLimitInfo) {
		String message = null;
		if(null==rcLimitInfo){
			message = "暂不支持该业务";
		}else{
			if(paymentAmount-rcLimitInfo.getSingleLimit()>10){//单笔限额 //差一分问题忽略
				message = "单笔最多提现"+AmountUtils.numberFormat(rcLimitInfo.getSingleLimit())+"元";
			}
			if(StringUtil.isNull(message)){
				Integer monthTimes = fundoutOrderService.countCurrentMonthPaymentTimes(OrderType.WITHDRAW.getValue(),memberCode);
				Integer dayTimes = fundoutOrderService.countCurrentDayPaymentTimes(OrderType.WITHDRAW.getValue(),memberCode);
				int monthLimitTimes = rcLimitInfo.getMonthTimes() - monthTimes.intValue();
				int dayLimitTimes = rcLimitInfo.getDayTimes() - dayTimes.intValue();
				if(monthLimitTimes<dayLimitTimes){//如果每月剩余次数小于每日剩余次数,则先校验每月次数
					if(monthTimes+1 > rcLimitInfo.getMonthTimes()){ //每月限次  每月最多提现${pay2BankMonthLimitTimes}次，您今天还可以提现${allowPaymentTimes}次
						if(monthLimitTimes<0){
							monthLimitTimes = 0;
						}
						message = "每月最多提现"+rcLimitInfo.getMonthTimes()+"次，您今天还可以提现"+monthLimitTimes+"次";
					}
				}else{
					if(dayTimes+1> rcLimitInfo.getDayTimes()){ //每日限次
						if(dayLimitTimes<0){
							dayLimitTimes = 0;
						}
						message = "每日最多提现"+rcLimitInfo.getDayTimes()+"次，您今天还可以提现"+dayLimitTimes+"次";
					}
				}
			}
			
			if(StringUtil.isNull(message)){
				long monthLimitAmount = rcLimitInfo.getMonthLimit(); //修改验证的方式 delin.dong 2016年5月5日 11:25:44
				long dayLimitAmount = rcLimitInfo.getDayLimit();
				if(rcLimitInfo.getMonthLimit()<rcLimitInfo.getDayLimit()){//如果每月剩余限额小于每日剩余限额,则先校验每月限额
					if(paymentAmount - rcLimitInfo.getMonthLimit()>10){//每月限额  修改可能出现的精度问题 delin.dong 2016年5月5日 14:10:43
						if(monthLimitAmount<0){
							monthLimitAmount = 0;
						}
						message = "每月最多提现"+AmountUtils.numberFormat(rcLimitInfo.getMonthLimit())+"元，您今天还可以提现"+AmountUtils.numberFormat(monthLimitAmount)+"元";
					}
				}else{
					if(paymentAmount-rcLimitInfo.getDayLimit()>10){//每日限额 修改可能出现的精度问题 delin.dong 2016年5月5日 14:10:43
						if(dayLimitAmount<0){
							dayLimitAmount = 0;
						}
						message = "每日最多提现"+AmountUtils.numberFormat(rcLimitInfo.getDayLimit())+"元，您今天还可以提现"+AmountUtils.numberFormat(dayLimitAmount)+"元";
					}
				}
			}
		}
		
		return message;
	}

	/**
	 * @param fundoutOrderService the fundoutOrderService to set
	 */
	public void setFundoutOrderService(FundoutOrderService fundoutOrderService) {
		this.fundoutOrderService = fundoutOrderService;
	}
	
	
	

}
