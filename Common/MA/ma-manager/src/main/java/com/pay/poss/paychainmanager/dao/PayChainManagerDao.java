/**
 * 
 */
package com.pay.poss.paychainmanager.dao;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.paychainmanager.dto.PayChainExternalLog;
import com.pay.poss.paychainmanager.dto.PayChainManagerDto;
import com.pay.poss.paychainmanager.dto.PayChainStatDto;

/**
 * @Description 支付链管理dao
 * @project 	ma-manager
 * @file 		PayChainManagerDao.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2011-09-20	    tianqing_wang	Create
 */
public interface PayChainManagerDao extends  BaseDAO<PayChainManagerDto> {
	
	/**
	 * 支付链管理分页查询
	 * @param   paramDto
	 * @return  List<PayChainManagerDto>
	 */
	public List<PayChainManagerDto> queryPayChainByCondition(PayChainManagerDto paramDto);
	
	/**
	 *  支付链管理分页查询统计
	 * @param  paramDto
	 * @return Integer
	 */
	public Integer countPayChainByCondition(PayChainManagerDto paramDto);
	
	/**
	 *  修改支付链状态为生效或者关闭
	 * @param  paramMap
	 * @return Integer
	 */
	@SuppressWarnings("unchecked")
	public Integer updatePayChainStatus(Map paramMap);
	
	
	public Integer updatePayChainDate(Map paramMap);
	
	/**
	 * 统计支付总金额
	 * @param paramDto
	 * @return
	 */
	public PayChainStatDto queryPayChainSum(PayChainManagerDto paramDto);
	
	/**查询支付信息
	 * @param cardNo
	 * @param processStatus
	 * @return
	 */
	public List<PayChainExternalLog> queryPayChainExternalLog(PayChainExternalLog externalLog);


	public int countPayChainExternalLog(PayChainExternalLog externalLog);
}
