/**
 * 
 */
package com.pay.fundout.withdraw.service.paytobank.validate.rule;

import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.fundout.withdraw.service.order.WithdrawOrderBusiType;
import com.pay.fundout.withdraw.service.order.WithdrawOrderService;
import com.pay.inf.rule.MessageRule;
import com.pay.poss.service.ma.pay2bank.Pay2BankValidateService;
import com.pay.rm.FoRcLimitFacade;
import com.pay.rm.facade.dto.RCLimitResultDTO;
import com.pay.rm.facade.util.RCLIMITCODE;
import com.pay.util.StringUtil;

/**
 * 付款金额检查规则
 * @author zliner
 *
 */
public class OrderAmountCheckRule extends MessageRule {
	private FoRcLimitFacade foRcLimitFacade;

	/**
	 * @param foRcLimitFacade the foRcLimitFacade to set
	 */
	public void setFoRcLimitFacade(FoRcLimitFacade foRcLimitFacade) {
		this.foRcLimitFacade = foRcLimitFacade;
	}
	
	
	
	private WithdrawOrderService withdrawOrderService;
	
	private Pay2BankValidateService pay2BankValidateService;
	

	public void setWithdrawOrderService(WithdrawOrderService withdrawOrderService) {
		this.withdrawOrderService = withdrawOrderService;
	}

	public void setPay2BankValidateService(
			Pay2BankValidateService pay2BankValidateService) {
		this.pay2BankValidateService = pay2BankValidateService;
	}




	/**
	 * 付款金额检查规则判断
	 * @param validateBean        待验证规则对象
	 * @return boolean            true表示验证规则通过，false表示该验证规则未通过
	 * @throws Exception          验证中抛出运行时异常
	 */
	protected boolean makeDecision(Object validateBean) throws Exception {
		
		Pay2BankDTO dto = (Pay2BankDTO)validateBean;
		String msgCode = "";
		
		long panymentAmount =  dto.getPaymentAmountLong();
		long realPaymentAmount = panymentAmount;
		long handlingCharge =  Math.abs(dto.getHandlingChargeLong());
		
		if(realPaymentAmount<=0){
			msgCode = "paymentAmount";
		}
		
		realPaymentAmount += handlingCharge;
		
		
		RCLimitResultDTO rule = getRCLimitAmount(dto.getMemberCode());
		if(null==rule){
			msgCode = "pay2BankWithoutAcctLimitAmount";
		}else{
			if(panymentAmount>rule.getSingleLimit()){//单笔限额
				msgCode = "OutSingleLimitAmountError";
			}
			if(StringUtil.isNull(msgCode)){
//				Integer monthTimes = withdrawOrderService.monthTimesTotal(dto.getMemberCode(),new int[]{WithdrawOrderBusiType.MASSPAY2BANK.getCode(),WithdrawOrderBusiType.PAY2BANK.getCode()});
//				Integer dayTimes = withdrawOrderService.dayTimesTotal(dto.getMemberCode(),new int[]{WithdrawOrderBusiType.MASSPAY2BANK.getCode(),WithdrawOrderBusiType.PAY2BANK.getCode()});
				//zengli 修改单笔付款到银行的限额只过滤单笔所有交易
				Integer monthTimes = withdrawOrderService.monthTimesTotal(dto.getMemberCode(),new int[]{WithdrawOrderBusiType.PAY2BANK.getCode()});
				Integer dayTimes = withdrawOrderService.dayTimesTotal(dto.getMemberCode(),new int[]{WithdrawOrderBusiType.PAY2BANK.getCode()});
				int monthLimitTimes = rule.getMonthTimes() - monthTimes.intValue();
				int dayLimitTimes = rule.getDayTimes() - dayTimes.intValue();
				if(monthLimitTimes<dayLimitTimes){//如果每月剩余次数小于每日剩余次数,则先校验每月次数
					if(monthTimes+1 > rule.getMonthTimes()){ //每月限次
						msgCode = "OutMonthLimitTimesError";
						dto.setLimitTimes(monthLimitTimes);
					}
				}else{
					if(dayTimes+1> rule.getDayTimes()){ //每日限次
						msgCode = "OutDayLimitTimesError";
						dto.setLimitTimes(dayLimitTimes);
					}
				}
			}
			
			if(StringUtil.isNull(msgCode)){
				//zengli 修改单笔付款到银行的限额只过滤单笔所有交易
//				Long currentDayAmount = withdrawOrderService.dayMoneyTotal(dto.getMemberCode(),new int[]{WithdrawOrderBusiType.MASSPAY2BANK.getCode(),WithdrawOrderBusiType.PAY2BANK.getCode()});
//				Long currentMonthAmount = withdrawOrderService.monthMoneyTotal(dto.getMemberCode(),new int[]{WithdrawOrderBusiType.MASSPAY2BANK.getCode(),WithdrawOrderBusiType.PAY2BANK.getCode()});
				Long currentDayAmount = withdrawOrderService.dayMoneyTotal(dto.getMemberCode(),new int[]{WithdrawOrderBusiType.PAY2BANK.getCode()});
				Long currentMonthAmount = withdrawOrderService.monthMoneyTotal(dto.getMemberCode(),new int[]{WithdrawOrderBusiType.PAY2BANK.getCode()});
				long monthLimitAmount = rule.getMonthLimit()-currentMonthAmount.longValue();
				long dayLimitAmount = rule.getDayLimit()-currentDayAmount.longValue();
				if(monthLimitAmount<dayLimitAmount){//如果每月剩余限额小于每日剩余限额,则先校验每月限额
					if(panymentAmount+currentMonthAmount > rule.getMonthLimit()){//每月限额
						msgCode = "OutMonthLimitAmountError";
						dto.setAllowPaymentAmountLong(monthLimitAmount);
					}
				}else{
					if(panymentAmount+currentDayAmount>rule.getDayLimit()){//每日限额
						msgCode = "OutDayLimitAmountError";
						dto.setAllowPaymentAmountLong(dayLimitAmount);
					}
				}
			}
		}
		
		if(StringUtil.isNull(msgCode)){
			return true;
		}
		dto.setMessageId(msgCode);
		return false;
	}
	
	private RCLimitResultDTO getRCLimitAmount(Long memberCode){
		RCLimitResultDTO rcLimitResultDTO = null;
		MemberInfoDto info = pay2BankValidateService.getMemberByMemberCode(memberCode);
		if(MemberTypeEnum.MERCHANT.getCode()==info.getMemberType().intValue()){
			rcLimitResultDTO = foRcLimitFacade.getBusinessRcLimit(RCLIMITCODE.FO_PAY_ENTERPRISE_BANK2E.getKey(), null, memberCode);
		}else if(MemberTypeEnum.INDIVIDUL.getCode()==info.getMemberType().intValue()){
			rcLimitResultDTO = foRcLimitFacade.getUserRcLimit(RCLIMITCODE.FO_PAY_PERSONAL_BANK.getKey(), null, memberCode);
		}
		return rcLimitResultDTO;
	}
}
