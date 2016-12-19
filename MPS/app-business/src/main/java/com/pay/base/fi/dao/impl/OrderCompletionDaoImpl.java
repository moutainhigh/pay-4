/**
 * 
 */
package com.pay.base.fi.dao.impl;

import java.util.Map;

import com.pay.base.fi.dao.OrderCompletionDao;
import com.pay.base.fi.model.CustomizationParam;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author PengJiangbo
 *
 */
public class OrderCompletionDaoImpl extends BaseDAOImpl<CustomizationParam>
		implements OrderCompletionDao {

	@Override
	public long insertCustomizationParam(CustomizationParam customizationParam) {
		long paraId = (Long) this.getSqlMapClientTemplate().insert(this.getNamespace().concat("insertCustomizationParam"), customizationParam) ;
		return paraId ;
	}

	@Override
	public CustomizationParam findCustomizationParam() {
		return (CustomizationParam) this.getSqlMapClientTemplate().queryForObject(this.getNamespace().concat("findCustomizationParam"));
	}

	@Override
	public int updateCustomizationParam(Map<String, Object> map) {
		int upResult = this.getSqlMapClientTemplate().update(this.getNamespace().concat("updateCustomizationParam"), map) ;
		return upResult ;
	}

}
