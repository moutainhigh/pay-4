 /** @Description 
 * @project 	poss-withdraw
 * @file 		WithdrawBaseController.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-6		Henry.Zeng			Create 
*/
package com.pay.fo.controller.fundout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.acc.service.account.constantenum.RiskGradeEnum;
import com.pay.acc.service.account.constantenum.SettlePeriodEnum;
import com.pay.fo.controller.fundout.common.MultiActionAndLogProcController;
import com.pay.fundout.withdraw.dto.operatorlog.OperatorlogDTO;

/**
 * <p>提现基类Controller
 * 提现Controller公共的方法在此添加
 * </p>
 * @author Henry.Zeng
 * @since 2010-9-6
 * @see 
 */
public abstract class WithdrawBaseController extends MultiActionAndLogProcController {

	@Override
	abstract public OperatorlogDTO buildOperatorLog();
	
	protected List<Map<String,String>> getAccountModeList(){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for(SettlePeriodEnum it:SettlePeriodEnum.values()){
			Map<String,String> map = new HashMap<String, String>();
			map = new HashMap<String, String>();
			map.put("text", it.getMessage());
			map.put("value", String.valueOf(it.getCode()));
			list.add(map);
		}
		return list;
	}
	
	protected List<Map<String,String>> getRiskLeveCodeList(){
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		for(RiskGradeEnum it:RiskGradeEnum.values()){
			Map<String,String> map = new HashMap<String, String>();
			map = new HashMap<String, String>();
			map.put("text", it.getMessage());
			map.put("value", String.valueOf(it.getCode()));
			list.add(map);
		}
		return list;
	}
}
