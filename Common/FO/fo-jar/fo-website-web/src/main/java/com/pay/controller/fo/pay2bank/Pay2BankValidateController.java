/**
 *  File: Pay2BankValidateController.java
 *  Description:
 *  Copyright 2010 -2010 Corporation. All rights reserved.
 *  2010-10-12      bill_peng     Changes
 *  
 *
 */
package com.pay.controller.fo.pay2bank;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.pay.fo.order.common.OrderType;
import com.pay.util.SpringControllerUtils;
import com.pay.util.StringUtil;

/**
 * @author bill_peng
 * 
 */
public class Pay2BankValidateController extends AbstractPay2BankController {

	/**
	 * 验证银行卡卡号
	 * 
	 * @param request
	 * @param response
	 * @param command
	 * @return
	 * @throws Exception
	 */
	public ModelAndView checkBankAcct(HttpServletRequest request, HttpServletResponse response, Pay2BankCommand command) throws Exception {

		String message = null;
		String bankAcct = StringUtil.null2String(request.getParameter("bankAcct"));
		String orgCode = StringUtil.null2String(request.getParameter("orgCode"));
		String tradeType = StringUtil.null2String(request.getParameter("tradeType"));
		String msgCode = "";

		if (!StringUtil.isNull(bankAcct) && !StringUtil.isNull(orgCode) && !StringUtil.isNull(tradeType)) {
			if (!StringUtil.isNull(tradeType) && "0".equalsIgnoreCase(tradeType)) {
				if (!cardBinFacadeService.validateBankAcctCode(bankAcct, orgCode)) {
					msgCode = "暂不支持该银行账号";
				}
			}
			if (!StringUtil.isNull(msgCode)) {
				message = msgCode;
			}
		} else if (StringUtil.isNull(orgCode)) {
			message = "payeeBankCode";
		} else if (StringUtil.isNull(bankAcct)) {
			message = "payeeBankAccount";
		} else if (StringUtil.isNull(tradeType)) {
			message = "tradeType";
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(StringUtil.null2String(message));
		return null;
	}

	public ModelAndView checkBank(HttpServletRequest request, HttpServletResponse response, Pay2BankCommand command) throws Exception {

		String message = null;
		String orgCode = StringUtil.null2String(request.getParameter("orgCode"));

		if (!StringUtil.isNull(orgCode)) {
			Map<String, Object> map = new HashMap<String, Object>();
			// 目的银行编号
			map.put("targetBankId", orgCode);
			// 出款方式
			map.put("foMode", 1);
			// 出款业务
			map.put("fobusiness", OrderType.PAY2BANK.getValue());

			String fundoutBank = configBankService.queryFundOutBank2Withdraw(map);

			if (StringUtil.isNull(fundoutBank)) {
				message = "暂不支持该银行出款";
			}
		} else {
			message = "payeeBankCode";
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(StringUtil.null2String(message));
		return null;
	}

	public ModelAndView checkPaymentReason(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String fundoutBank = "";
		String orgCode = StringUtil.null2String(request.getParameter("orgCode"));

		if (!StringUtil.isNull(orgCode)) {
			Map<String, Object> map = new HashMap<String, Object>();
			// 目的银行编号
			map.put("targetBankId", orgCode);
			// 出款方式
			map.put("foMode", 1);
			// 出款业务
			map.put("fobusiness", OrderType.PAY2BANK.getValue());

			fundoutBank = configBankService.queryFundOutBank2Withdraw(map);
			fundoutBank = null == fundoutBank ? "" : fundoutBank;
		}
		SpringControllerUtils.renderText(response, fundoutBank);
		return null;
	}
}
