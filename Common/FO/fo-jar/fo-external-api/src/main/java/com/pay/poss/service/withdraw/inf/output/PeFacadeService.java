/**
 *  File: PeFacadeService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-27      zliner      Changes
 *  
 *
 */
package com.pay.poss.service.withdraw.inf.output;

import com.pay.pe.service.CalFeeReponse;
import com.pay.poss.dto.withdraw.pricestrategy.CalFeeRequestDTO;


/**
 * PE门面服务
 * @author zliner
 *
 */
@Deprecated
public interface PeFacadeService {
	/**
	 * 进行计价 响应  带有 calFeeDetails 相当于分录对象（包含  acctCode  amount  crdr) 
	 * @param dealRequest 请求对象 
	 * @return calFeeReponse 
	 * @throws 
	 */
	CalFeeReponse calculateFeeDetail(CalFeeRequestDTO dealRequest) ;
	/**
	 * 进行计价 只是单单计算费用 
	 * @param dealRequest 请求对象 
	 * @return calFeeReponse 响应
	 * @throws 
	 */
	CalFeeReponse caculateFee(CalFeeRequestDTO dealRequest) ;
}
