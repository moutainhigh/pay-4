/**
 * 
 */
package com.pay.account.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dto.Bank;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.inf.service.BankService;
import com.pay.util.JSonUtil;

/**
 * @author alex
 *
 */
public class BankHandler implements EventHandler {

	private BankService bankService;

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, Object> result = new HashMap<String, Object>();

		List<Bank> banks = bankService.getWithdrawBanks();

		result.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		result.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		result.put("bankList", banks);
		return JSonUtil.toJSonString(result);
	}

}
