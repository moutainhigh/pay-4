/**
 * 
 */
package com.pay.poss.amountma.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.amountma.dao.IFrozenLogDao;
import com.pay.poss.amountma.dto.FrozenLogDto;

/**
 * @Description
 * @project ma-manager
 * @file FrozenLogDaoImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2011-5-2 DDR Create
 */
public class FrozenLogDaoImpl extends BaseDAOImpl<FrozenLogDto> implements
		IFrozenLogDao {

	
	
	private BaseDAO<FrozenLogDto> pageDaoCommon;

	public void setPageDaoCommon(BaseDAO<FrozenLogDto> pageDaoCommon) {
		this.pageDaoCommon = pageDaoCommon;
	}

	@Override
	public Page<FrozenLogDto> findPage(Page<FrozenLogDto> page,
			FrozenLogDto frozeLog) {	
		return pageDaoCommon.findByQuery(namespace.concat("search"), page,
				frozeLog);
	}

	@Override
	public int addFrozenAmount(Long memberCode, BigDecimal frozenAmount,String acctCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("frozenAmount", frozenAmount.multiply(BigDecimal.valueOf(1000))
				.longValue());
		map.put("memberCode", memberCode);
		map.put("acctCode",acctCode);
		return getSqlMapClientTemplate().update(
				namespace.concat("addFrozenAmount"), map);
	}

	@Override
	public int freeFrozenAmount(Long memberCode, BigDecimal frozenAmount,String acctCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("frozenAmount", frozenAmount.multiply(BigDecimal.valueOf(1000))
				.longValue());
		map.put("memberCode", memberCode);
		map.put("acctCode",acctCode);
		return getSqlMapClientTemplate().update(
				namespace.concat("freeFrozenAmount"), map);
	}

	@Override
	public int updateFrozenLogStatus(Long id, Integer status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("id", id);
		return getSqlMapClientTemplate().update(
				namespace.concat("updateFrozenLogStatus"), map);
	}

}
