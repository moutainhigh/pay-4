package com.pay.poss.systemmanager.dao.impl;

import java.util.List;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.systemmanager.dao.IOrgDao;
import com.pay.poss.systemmanager.model.Org;

/**
 * 部站相关数据操作与查询
 * @author 戴德荣
 * @date 2011-1-6 
 *
 */
public class OrgDaoImpl extends BaseDAOImpl<Org> implements IOrgDao {

	@Override
	public Org findByOrgCode(String orgCode) {
		return (Org) this.getSqlMapClientTemplate().queryForObject(namespace.concat("findByOrgCode"), orgCode);
	}

	@Override
	public List<Org> findAll() {
		return this.getSqlMapClientTemplate().queryForList(namespace.concat("allOrg"));
	}

}
