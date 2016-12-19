/**
 * 
 */
package com.pay.base.fi.dao.impl;

import java.util.Map;

import com.pay.base.fi.dao.ChargeBackOrderDao;
import com.pay.base.fi.model.ChargeBackMemberRelation;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author PengJiangbo
 *
 */
public class ChargeBackOrderDaoImpl extends BaseDAOImpl implements
		ChargeBackOrderDao {

	/* (non-Javadoc)
	 * @see com.pay.base.fi.dao.ChargeBackOrderDao#createRealtion(java.util.Map)
	 */
	@Override
	public void createRealtion(Map<String, Object> hMap) {
		this.getSqlMapClientTemplate().insert(this.getNamespace().concat("createRealtion"), hMap) ;
		
	}

	/* (non-Javadoc)
	 * @see com.pay.base.fi.dao.ChargeBackOrderDao#countStatus(java.util.Map)
	 */
	@Override
	public Integer countStatus(Map<String, Object> hMap) {
		// TODO Auto-generated method stub
		return (Integer) this.getSqlMapClientTemplate().queryForObject(this.getNamespace().concat("countStatus"), hMap) ;
	}

	/* (non-Javadoc)
	 * @see com.pay.base.fi.dao.ChargeBackOrderDao#queryRelation(java.util.Map)
	 */
	@Override
	public ChargeBackMemberRelation queryRelation(Map<String, Object> hMap) {
		return (ChargeBackMemberRelation) this.getSqlMapClientTemplate().queryForObject(this.getNamespace().concat("queryRelation"), hMap) ;
	}

}
