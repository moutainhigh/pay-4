/**
 * 
 */
package com.pay.poss.yk.dao;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.poss.yk.dto.ExternalLogDto;
import com.pay.poss.yk.dto.ExternalLogSearchDto;


/**
 * @Description 
 * @project 	ma-manager
 * @file 		FrozenLogDaoImpl.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2011-6-2			DDR				Create
 */
public interface IExternalLogDao extends BaseDAO<ExternalLogDto> {


	Page<ExternalLogDto> findPage(Page<ExternalLogDto> page,
			ExternalLogSearchDto searchDto);
	
	ExternalLogDto findByOrderNo(String orderNo);

}
