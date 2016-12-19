/**
 * 
 */
package com.pay.poss.paychainmanager.dao.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.paychainmanager.dao.PayChainManagerDao;
import com.pay.poss.paychainmanager.dto.PayChainExternalLog;
import com.pay.poss.paychainmanager.dto.PayChainManagerDto;
import com.pay.poss.paychainmanager.dto.PayChainStatDto;

/**
 * @Description 
 * @project 	ma-manager
 * @file 		PayChainManagerDaoImpl.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2011-09-20		tianqing_wang				Create
 */
@SuppressWarnings("unchecked")
public class PayChainManagerDaoImpl extends BaseDAOImpl<PayChainManagerDto> implements PayChainManagerDao {

	public List<PayChainManagerDto> queryPayChainByCondition(PayChainManagerDto paramDto){
		return this.getSqlMapClientTemplate().queryForList("paychainmanager.queryPayChainByCondition",paramDto);
	}
	public Integer countPayChainByCondition(PayChainManagerDto paramDto){
		return (Integer)this.getSqlMapClientTemplate().queryForObject("paychainmanager.countPayChainByCondition",paramDto);
	}
	public Integer updatePayChainStatus(Map paramMap){
		Integer i = (Integer)this.getSqlMapClientTemplate().update("paychainmanager.updatePayChainStatus",paramMap);
		return i;
	}
	
	public Integer updatePayChainDate(Map paramMap){
		
		Integer i = (Integer)this.getSqlMapClientTemplate().update("paychainmanager.updatePayChainDate",paramMap);
		return i;
	}
	
	@Override
	public PayChainStatDto queryPayChainSum(PayChainManagerDto paramDto) {
		return (PayChainStatDto) this.getSqlMapClientTemplate().queryForObject("paychainmanager.queryPayChainCountSum",paramDto);
	}
	@Override
	public List<PayChainExternalLog> queryPayChainExternalLog(PayChainExternalLog externalLog) {
		return this.getSqlMapClientTemplate().queryForList("paychainmanager.queryPayChainExternalLog", externalLog);
	}
	@Override
	public int countPayChainExternalLog(PayChainExternalLog externalLog) {
		Integer obj=(Integer) this.getSqlMapClientTemplate().queryForObject("paychainmanager.countPayChainExternalLog",externalLog);
		if(null==obj){
			return 0;
		}
		return obj;
	}
	
}
