 /** @Description 
 * @project 	poss-withdraw
 * @file 		FoBankInfoService.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-20		Henry.Zeng			Create 
*/
package com.pay.fundout.withdraw.service.bank;

import java.util.List;
import java.util.Map;

/**
 * <p>出款提供获取银行数据</p>
 * @author Henry.Zeng
 * @since 2010-9-20
 * @see 
 */
public interface FoBankInfoService {
		
	/**
	 * 获取所有银行信息
	 * @return
	 */
	public List<Map<String,String>> getBankInfoList() ;
	
	
}
