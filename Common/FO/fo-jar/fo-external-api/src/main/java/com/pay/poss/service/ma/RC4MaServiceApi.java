 /** @Description 
 * @project 	poss-external-api
 * @file 		MaServiceApi.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-8-27		Henry.Zeng			Create 
*/
package com.pay.poss.service.ma;

import java.math.BigDecimal;

import com.pay.poss.base.exception.PossUntxException;

/**
 * <p>关于Poss需要调用MA的API 定义在这里</p>
 * @author Henry.Zeng
 * @since 2010-8-27
 * @see 
 */
public interface RC4MaServiceApi {

	//该功能无需提供   2010-10-06 zliner修改 
//	/**
//	 * @param serialNumber 流水号19位数字类型
//	 * @param amount	        金额
//	 * @param acctCode	        账号
//	 * @param balanceFlag  加减标识 加传入1减传入2 
//	 * @return
//	 * @author Henry.Zeng
//	 * @see
//	 */
//	public Integer doModifyAmountRnTx(String serialNumber,BigDecimal amount,Long acctCode,Integer balanceFlag) throws PossUntxException ;
	
}
