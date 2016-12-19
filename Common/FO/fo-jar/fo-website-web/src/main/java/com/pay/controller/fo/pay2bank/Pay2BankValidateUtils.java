/**
 * 
 */
package com.pay.controller.fo.pay2bank;

import javax.servlet.http.HttpServletRequest;

import com.pay.fundout.util.AmountUtils;
import com.pay.util.IDContentUtil;
import com.pay.util.StringUtil;

/**
 * @author NEW
 *
 */
public class Pay2BankValidateUtils {

	
	public static String validateInputInfo(Pay2BankCommand command){
		
		//收款方户名
		if(StringUtil.isNull(command.getPayeeName())){
			return "请输入收款方户名";
		}else if(command.getPayeeName().contains("<")||command.getPayeeName().contains("%3c")){
			return "请输入有效的收款方户名";
		}
		//账户类型
		if(StringUtil.isNull(command.getTradeType())){
			return "请选择账户类型";
		}
		//开户银行
		if(StringUtil.isNull(command.getPayeeBankCode())){
			return "请选择开户行";
		}
		//开户行所在城市
		if(StringUtil.isNull(command.getPayeeBankProvince())){
			return "请选择省份";
		}
		if(StringUtil.isNull(command.getPayeeBankCity())){
			return "请选择城市";
		}
		//开户行名称
		if(StringUtil.isNull(command.getPayeeOpeningBankName())){
			return "请选择开户行名称";
		}
		//银行账号
		if(StringUtil.isNull(command.getPayeeBankAcctCode())){
			return "请选择输入银行账号";
		}
		//重复银行账号
		//银行账号
		if(StringUtil.isNull(command.getPayeeRepeatBankAcctCode())){
			return "请选择重复银行账号";
		}
		if(!command.getPayeeBankAcctCode().equalsIgnoreCase(command.getPayeeRepeatBankAcctCode())){
			return "两次输入的银行账号不一致";
		}
		//付款金额
		if(StringUtil.isNull(command.getRequestAmount())){
			return "请输入付款金额";
		}else if(!AmountUtils.checkAmount(command.getRequestAmount())||Double.parseDouble(command.getRequestAmount())<=0){
			return "请输入有效的付款金额";
		}
		//付款理由 
		if(!StringUtil.isNull(command.getPaymentReason())&&(command.getPaymentReason().contains("<")||command.getPaymentReason().contains("%3c"))){
			return "请输入有效的付款理由";
		}else if(!vertifyReason(StringUtil.null2String(command.getPaymentReason()))){
			return "请输入有效的付款理由";
		}
		//收款方手机号码
		if(!StringUtil.isNull(command.getPayeeMobile())&&!IDContentUtil.validateMobile(command.getPayeeMobile())){
			return "请输入有效的收款方手机号码";
		}
		
		return null;
	}
	
	public static boolean vertifyReason(String vertifyCause) {
		int strc = 0;
		for (int i = 0; i < vertifyCause.length(); i++) {
			if (vertifyCause.toCharArray()[i] > 255) {
				strc += 2;
			} else {
				strc++;
			}
		}
		if (strc > 100) {
			return false;
		}
		return true;
	}
	
	public static boolean validateToken(HttpServletRequest request){
		String token = StringUtil.null2String(request.getParameter("token"));
		String token_pay2bank = null;
		token_pay2bank = StringUtil.null2String(request.getSession().getAttribute("token_pay2bank"));
		request.getSession().removeAttribute("token_pay2bank");
		if(StringUtil.isNull(token)||(StringUtil.isNull(token_pay2bank)||!token.equalsIgnoreCase(token_pay2bank))){
			return false;
		}
		return true;
	}
}
