package com.pay.app.controller.base.bankacct;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.pay.app.base.api.annotation.OperatorPermission;
import com.pay.base.model.LiquidateInfo;

/**
 * 功能：企业BSP增加银行卡号，用于提现等功能的银行账户列表
 * 
 * @author 戴德荣
 * 
 */
public class BspBankAcctController extends CorpBankAcctController {

	/**
	 * 编辑银行卡
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author 戴德荣
	 */
	@OperatorPermission(operatPermission = "BSP_CHANGE_BANK_NO")
	public ModelAndView editBankCard(HttpServletRequest request,
			HttpServletResponse response) {
		return super.editBankCard(request, response).addObject("isBsp", true);
	}

	/**
	 * 提交增加或修改银行卡
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @author 戴德荣
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "BSP_CHANGE_BANK_NO")
	public ModelAndView addOrUpdateBankCard(HttpServletRequest request,
			HttpServletResponse response, LiquidateInfo corpBankAcc)
			throws Exception {
		return super.addBankCard(request, response, corpBankAcc).addObject(
				"isBsp", true);
	}

}
