/**
 * 
 */
package com.pay.fundout.withdraw.service.paytobank.validate.rule;

import java.util.HashMap;
import java.util.Map;

import com.pay.fundout.channel.service.configbank.ConfigBankService;
import com.pay.fundout.withdraw.service.order.WithdrawOrderBusiType;
import com.pay.inf.rule.MessageRule;
import com.pay.poss.service.ma.pay2bank.Pay2BankValidateException;
import com.pay.util.StringUtil;

/**
 * 银行相关信息校验规则
 * @author zliner
 *
 */
public class BankInfoCheckRule extends MessageRule{
	 
	private ConfigBankService configBankService;
	/**
	 * 银行相关信息校验规则判断
	 * @param validateBean        待验证规则对象
	 * @return boolean            true表示验证规则通过，false表示该验证规则未通过
	 * @throws Exception          验证中抛出运行时异常
	 */
	protected boolean makeDecision(Object validateBean) throws Exception {
		
		Pay2BankDTO dto = (Pay2BankDTO)validateBean;
		String msgCode = "";
		
		
		if(dto.getPayeeBankAccount().equals(dto.getPayeeRepeatBankAccount())){
			if(StringUtil.isNull(getOutBankCode(dto.getPayeeBankCode()))){
				msgCode = "NotFoundOutBank";
			}else{
				return true;
			}
		}else if(!dto.getPayeeBankAccount().equals(dto.getPayeeRepeatBankAccount())){
			msgCode = Pay2BankValidateException.NOT_THE_SAME.getCode();
		}else{
			msgCode = Pay2BankValidateException.BAD_OPENING_BANKACCOUNT_NAME.getCode();
		}
		dto.setMessageId(msgCode);
		return false;
	}
	
	private String getOutBankCode(String bankCode){
		
		Map<String,Object> map = new HashMap<String,Object>();
		//目的银行编号
		map.put("targetBankId", bankCode);
		//出款方式
		map.put("foMode", "1");
		//出款业务
		map.put("fobusiness", WithdrawOrderBusiType.PAY2BANK.getCode());//3 付款到银行
		
		return StringUtil.null2String(configBankService.queryFundOutBank2Withdraw(map));
	}

	public void setConfigBankService(ConfigBankService configBankService) {
		this.configBankService = configBankService;
	}
	
	
}
