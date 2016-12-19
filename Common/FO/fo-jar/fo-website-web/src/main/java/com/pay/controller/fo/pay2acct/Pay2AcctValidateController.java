/**
 * 
 */
package com.pay.controller.fo.pay2acct;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.fundout.service.ma.MemberInfo;
import com.pay.util.IDContentUtil;
import com.pay.util.StringUtil;

/**
 * @author NEW
 *
 */
public class Pay2AcctValidateController extends AbstractPay2AcctController {
	
	
	
	public String checkPayee(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		
		
		String message = "";
		String payeeUserId = request.getParameter("payeeUserId");
		if (!StringUtil.isNull(payeeUserId)) {
			if(IDContentUtil.validateEmail(payeeUserId)||IDContentUtil.validateMobile(payeeUserId)){
				MemberInfo payee = memberQueryFacadeService.getMemberInfo(payeeUserId);
				// 验证收款方信息
				message = paymentValidateService.validatePayeeMemberInfo(payee, loginSession.getMemberCode());
				if(StringUtil.isNull(message)){
					message = paymentValidateService.validatePayeeAcctInfo(payee.getMemberCode(), AcctTypeEnum.BASIC_CNY.getCode());
				}
			}else{
				message = "请输入有效的收款方用户名";
			}
			
			
		} else {
			message = "请输入收款方用户名";
		}
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(message == null ? "" : message);
		return null;
	}
	

}
