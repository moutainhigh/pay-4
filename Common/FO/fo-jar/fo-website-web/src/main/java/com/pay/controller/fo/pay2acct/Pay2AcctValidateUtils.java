/**
 * 
 */
package com.pay.controller.fo.pay2acct;

import javax.servlet.http.HttpServletRequest;

import com.pay.fundout.util.AmountUtils;
import com.pay.util.IDContentUtil;
import com.pay.util.StringUtil;


/**
 * @author NEW
 *
 */
public class Pay2AcctValidateUtils {
	
	public static String validateInputInfo(Pay2AcctCommand command){
		String idContent = StringUtil.null2String(command.getPayeeLoginName());
		if(StringUtil.isNull(idContent)){
			return "请输入收款方用户名";
		}else if (!IDContentUtil.validateEmail(idContent)&& !IDContentUtil.validateMobile(idContent)) {
			return "请输入有效的收款方用户名";
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
		String token_pay2acct = null;
		token_pay2acct = StringUtil.null2String(request.getSession().getAttribute("token_pay2acct"));
		request.getSession().removeAttribute("token_pay2acct");
		if(StringUtil.isNull(token)||(StringUtil.isNull(token_pay2acct)||!token.equalsIgnoreCase(token_pay2acct))){
			return false;
		}
		return true;
	}

}
