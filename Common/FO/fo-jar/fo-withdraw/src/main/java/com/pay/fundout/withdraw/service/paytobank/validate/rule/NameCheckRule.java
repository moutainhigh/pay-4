/**
 * 
 */
package com.pay.fundout.withdraw.service.paytobank.validate.rule;

import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

/**
 * 姓名检查规则判断
 * @author zliner
 *
 */
public class NameCheckRule  extends MessageRule {
	

	/**
	 * 姓名检查规则判断
	 * @param validateBean        待验证规则对象
	 * @return boolean            true表示验证规则通过，false表示该验证规则未通过
	 * @throws Exception          验证中抛出运行时异常
	 */
	protected boolean makeDecision(Object validateBean) throws Exception {
		
		Pay2BankDTO dto = (Pay2BankDTO)validateBean;
		String msgCode = "";
		
		if(StringUtil.isNull(dto.getPayeeName())){
			msgCode = "payeeName";
		}
		
		if(StringUtil.isNull(msgCode)){
			return true;
		}
		dto.setMessageId(msgCode);
		return false;
	}
}
