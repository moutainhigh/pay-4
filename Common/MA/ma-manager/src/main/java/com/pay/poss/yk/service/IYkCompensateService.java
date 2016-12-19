/**
 * 
 */
package com.pay.poss.yk.service;

import com.pay.inf.dao.Page;
import com.pay.poss.yk.dto.ExternalLogDto;
import com.pay.poss.yk.dto.ExternalLogSearchDto;

/**
 * @Description 
 * @project 	ma-manager
 * @file 		ICompensateService.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 woyo Corporation. All rights reserved.
 * Date				Author			Changes
 * 2011-6-2			DDR				Create
 */
public interface IYkCompensateService  {

	/**
	 * 分页查询
	 * @param paramPage
	 * @param dto
	 * @return page
	 */
	public Page<ExternalLogDto> search(Page<ExternalLogDto> paramPage,ExternalLogSearchDto dto);
	
	/**
	 * 申请退款,直接调用网关的退款
	 * @param orderNo
	 */
	public void aplayRefund(String orderNo) ;
	
	/**
	 *  查询易卡充值日志
	 * @param orderNo
	 * @return
	 */
	public ExternalLogDto findExternalLogByOrderNo(String orderNo);
}
