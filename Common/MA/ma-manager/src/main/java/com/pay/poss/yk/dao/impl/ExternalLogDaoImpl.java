/**
 * 
 */
package com.pay.poss.yk.dao.impl;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.yk.dao.IExternalLogDao;
import com.pay.poss.yk.dto.ExternalLogDto;
import com.pay.poss.yk.dto.ExternalLogSearchDto;

/**
 * @Description
 * @project ma-manager
 * @file ExternalLogDaoImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2011-6-2 DDR Create
 */
public class ExternalLogDaoImpl extends BaseDAOImpl<ExternalLogDto> implements
		IExternalLogDao {

	private BaseDAO<ExternalLogDto> pageDaoCommon;

	public void setPageDaoCommon(BaseDAO<ExternalLogDto> pageDaoCommon) {
		this.pageDaoCommon = pageDaoCommon;
	}

	@Override
	public Page<ExternalLogDto> findPage(Page<ExternalLogDto> page,
			ExternalLogSearchDto searchDto) {

		return pageDaoCommon.findByQuery(namespace.concat("search"), page,
				searchDto);
	}

	@Override
	public ExternalLogDto findByOrderNo(String orderNo) {
		return (ExternalLogDto) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("findByOrderNo"), orderNo);
	}

}
