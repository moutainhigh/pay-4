 /** @Description 
 * @project 	poss-withdraw
 * @file 		FoBankInfoServiceMock.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-20		Henry.Zeng			Create 
*/
package com.pay.fundout.withdraw.service.bank.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.service.bank.FoBankInfoService;

/**
 * <p>提供Mock实现</p>
 * @author Henry.Zeng
 * @since 2010-9-20
 * @see 
 */
public class FoBankInfoServiceMock  implements FoBankInfoService {

	@Override
	public List<Map<String, String>> getBankInfoList() {
		List<Map<String,String>> wdBankCodeList = new ArrayList<Map<String,String>>();
		Map<String,String> bankCodeMap = new HashMap<String, String>();
		bankCodeMap.put("text", "招商银行");
		bankCodeMap.put("value", "001");
		wdBankCodeList.add(bankCodeMap);
		bankCodeMap = new HashMap<String, String>();
		bankCodeMap.put("text", "建设银行");
		bankCodeMap.put("value", "002");
		wdBankCodeList.add(bankCodeMap);
		bankCodeMap = new HashMap<String, String>();
		bankCodeMap.put("text", "工商银行");
		bankCodeMap.put("value", "003");
		wdBankCodeList.add(bankCodeMap);
		return wdBankCodeList;
	}

}
