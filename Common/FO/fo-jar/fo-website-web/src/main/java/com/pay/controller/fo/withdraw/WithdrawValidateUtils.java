/**
 * 
 */
package com.pay.controller.fo.withdraw;

import javax.servlet.http.HttpServletRequest;

import com.pay.fundout.util.AmountUtils;
import com.pay.util.StringUtil;

/**
 * @author NEW
 *
 */
public class WithdrawValidateUtils {

	
	public static String validateInputInfo(WithdrawCommand command){
		
		//付款金额
		if(StringUtil.isNull(command.getRequestAmount())){
			return "请输入付款金额";
		}else if(!AmountUtils.checkAmount(command.getRequestAmount())||Double.parseDouble(command.getRequestAmount())<=0){
			return "请输入有效的付款金额";
		}
		
		return null;
	}
	
	
	public static boolean validateToken(HttpServletRequest request){
		String token = StringUtil.null2String(request.getParameter("token"));
		String token_withdraw = null;
		token_withdraw = StringUtil.null2String(request.getSession().getAttribute("token_withdraw"));
		request.getSession().removeAttribute("token_withdraw");
		if(StringUtil.isNull(token)||(StringUtil.isNull(token_withdraw)||!token.equalsIgnoreCase(token_withdraw))){
			return false;
		}
		return true;
	}
}
