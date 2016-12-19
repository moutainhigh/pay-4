 /** @Description 
 * @project 	poss-external-api-impl
 * @file 		MaServiceImpl.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-8-27		Henry.Zeng			Create 
*/
package com.pay.poss.service.ma.impl;

import com.pay.poss.service.adapter.AbstractExternalAdapter;
import com.pay.poss.service.ma.RC4MaServiceApi;

/**
 * <p></p>
 * @author Henry.Zeng
 * @since 2010-8-27
 * @see 
 */
public class RC4MaServiceJar extends AbstractExternalAdapter implements RC4MaServiceApi {
	//该功能无需提供   2010-10-06 zliner修改 
//	@Override
//	public Integer doModifyAmountRnTx(String serialNumber, BigDecimal amount,
//			Long acctCode, Integer balanceFlag) throws PossUntxException {
//		
//		log.debug("【调用MA调账接口入参】:" +"serialNumber :" +serialNumber + "amount :"+amount +"acctCode : "+acctCode +"balanceFlag :"+balanceFlag);
//		
//		Integer integer = 0; 
//		
//		if(amount == null) throw new PossUntxException("金额不允许为空!", ExceptionCodeEnum.ILLEGAL_PARAMETER);
//		try{	
//			integer = this.modifyAmountService.doModifyAmountRnTx(serialNumber, amount.longValue(), 
//					acctCode, BalanceHandlerEnum.ADD);
//			
//			log.debug("【调用MA调账接口返回参数】:" +"integer :" +integer);
//		}catch (MaModifyAmountException e) {
//			throw new PossUntxException("调用MA接口异常"+e.getMessage(), ExceptionCodeEnum.ILLEGAL_PARAMETER);
//		}
//		
//		return integer ;
//	}

}
