/**
 *  File: PeFacadeServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-27      zliner      Changes
 *  
 *
 */
package com.pay.poss.service.withdraw.inf.output.impl;

import org.springframework.beans.BeanUtils;

import com.pay.pe.service.CalFeeReponse;
import com.pay.pe.service.CalFeeRequest;
import com.pay.pe.service.PEService;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.poss.dto.withdraw.pricestrategy.CalFeeRequestDTO;
import com.pay.poss.service.withdraw.inf.output.PeFacadeService;

/**
 * pe门面服务实现
 * @author zliner
 *
 */
@Deprecated
public class PeFacadeServiceImpl implements PeFacadeService {
	//pe的记账和算费服务
	private PEService peService;
	//set注入
	public void setPeService(final PEService param) {
		this.peService = param;
	}
	/**
	 * 进行计价 响应  带有 calFeeDetails 相当于分录对象（包含  acctCode  amount  crdr) 
	 * @param dealRequestDto 请求对象 
	 * @return calFeeReponse 
	 * @throws 
	 */
	public CalFeeReponse calculateFeeDetail(CalFeeRequestDTO dealRequestDto)  {
		LogUtil.info(this.getClass(), "构建记账对象", OPSTATUS.SUCCESS, dealRequestDto.toString(),"");
		return null;
		//return peService.calculateFeeDetail(buildCalFeeReq(dealRequestDto));
	}
	/**
	 * 进行计价 只是单单计算费用 
	 * @param dealRequest         请求对象 
	 * @return calFeeReponse      响应
	 * @throws 
	 */
	public CalFeeReponse caculateFee(CalFeeRequestDTO dealRequestDto) {
		LogUtil.info(this.getClass(), "构建算手续费对象", OPSTATUS.SUCCESS, dealRequestDto.toString(),"");
		return null;//peService.caculateFee(buildCalFeeReq(dealRequestDto));
	}
	//构建算费请求对象
	private CalFeeRequest buildCalFeeReq(CalFeeRequestDTO dealRequestDto) {
		CalFeeRequest calFeeReq = new CalFeeRequest();
		BeanUtils.copyProperties(dealRequestDto, calFeeReq);
		return calFeeReq;
	}
}
