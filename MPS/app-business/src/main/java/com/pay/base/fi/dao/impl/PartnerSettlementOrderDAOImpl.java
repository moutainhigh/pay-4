/**
 * 
 */
package com.pay.base.fi.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.pay.base.fi.dao.PartnerSettlementOrderDAO;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author PengJiangbo
 *
 */
public class PartnerSettlementOrderDAOImpl extends BaseDAOImpl implements
		PartnerSettlementOrderDAO {

	@Override
	public Long queryAmountByMemberCodeAndCurrency(final Long memberCode,
			final String currency) {
		Map<String, Object> hMap = new HashMap<String, Object>() ;
		hMap.put("memberCode", memberCode) ;
		hMap.put("currency", currency) ;
		return (Long) this.getSqlMapClientTemplate().queryForObject(this.getNamespace().concat("queryAmountByMemberCodeAndCurrency"), hMap) ;
	}

}
