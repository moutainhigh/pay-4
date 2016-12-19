/**
 * 
 */
package com.pay.poss.amountma.service;

import java.math.BigDecimal;

import com.pay.acc.service.account.AccountQueryService;
import com.pay.inf.dao.Page;
import com.pay.poss.amountma.dto.FrozenLogDto;

/**
 * @Description 
 * @project 	ma-manager
 * @file 		IForzenLogService.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2011-5-2			DDR				Create
 */
public interface IFrozenLogService {
	/**
	 * 分页查询
	 * @param paramPage
	 * @param dto
	 * @return 
	 */
	public Page<FrozenLogDto> search(Page<FrozenLogDto> paramPage,FrozenLogDto dto);
	
	public BigDecimal getBlanceByLongNameOrCode(String nameOrCode,Integer acctType) throws Exception;
	
	public Long getMemberCodeByLongNameOrCode(String nameOrCode);
	
	public boolean addFrozenLogRnTx(String nameOrCode,BigDecimal amount,String desc,Integer acctType);
	
	public boolean freeFrozenLogRnTx(String memberCode,Long id) ;
	
	
	public FrozenLogDto getById(Long id);
	
	public AccountQueryService getAccountQueryService();
	
}
