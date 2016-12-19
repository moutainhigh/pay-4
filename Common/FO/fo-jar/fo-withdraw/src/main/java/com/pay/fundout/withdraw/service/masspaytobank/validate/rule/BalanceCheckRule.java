/**
 * 
 */
package com.pay.fundout.withdraw.service.masspaytobank.validate.rule;

import com.pay.inf.rule.MessageRule;
import com.pay.poss.service.ma.pay2bank.Pay2BankValidateException;
import com.pay.poss.service.ma.pay2bank.Pay2BankValidateService;

/**
 * 余额校验规则
 * @author zliner
 *
 */
public class BalanceCheckRule  extends MessageRule  {
	
	private Pay2BankValidateService pay2BankValidateService;
	
	
	
	
	public void setPay2BankValidateService(
			Pay2BankValidateService pay2BankValidateService) {
		this.pay2BankValidateService = pay2BankValidateService;
	}





	/**
	 * 余额校验规则判断
	 * @param validateBean        待验证规则对象
	 * @return boolean            true表示验证规则通过，false表示该验证规则未通过
	 * @throws Exception          验证中抛出运行时异常
	 */
	protected boolean makeDecision(Object validateBean) throws Exception {
		MassPaytobankDTO dto = (MassPaytobankDTO)validateBean;
		String msgCode = "";
		
		
		long balanceLong =pay2BankValidateService.getBalance(dto.getPayerMemberCode(), dto.getPayerAccountType());
		
		if(balanceLong<=0){
			dto.setMessageId("payerAccountBalance");
			return false;
		}
		long realPaymentAmount =  dto.getPaymentAmount();
		
		
		if(dto.getIsPayerPayFee().intValue()==1){
			realPaymentAmount += dto.getFee();
		}
		
		if(balanceLong <= 0|| realPaymentAmount > balanceLong){
			msgCode = Pay2BankValidateException.NOT_ENOUGH_BALANCE.getCode();
			dto.setMessageId(msgCode);
			return false;
		}
		
		return true;
	}
}
