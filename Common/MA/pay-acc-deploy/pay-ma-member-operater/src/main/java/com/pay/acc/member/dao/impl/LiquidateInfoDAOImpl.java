package com.pay.acc.member.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.acc.member.dao.LiquidateInfoDAO;
import com.pay.acc.member.model.LiquidateInfo;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

@SuppressWarnings("unchecked")
public class LiquidateInfoDAOImpl extends BaseDAOImpl<LiquidateInfo> implements
		LiquidateInfoDAO {

	@Override
	public int countSettlePeriod(final Long memberCode, final int accountMode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberCode", memberCode);
		paramMap.put("accountMode", accountMode);
		Integer obj = (Integer) getSqlMapClientTemplate().queryForObject(namespace.concat("countLiquidateInfoByAccountMode"), paramMap);
		if (null == obj) {
			return 0;
		}
		return obj;
	}

	@Override
	public List<LiquidateInfo> findByCriteria(final Map<String, Object> hMap,
			final Page<?> page) {
		return super.findByCriteria(hMap, page) ;
	}

	@Override
	public int updateLiquidateInfoStatus(final Map<String, Object> hMap) {
		return this.getSqlMapClientTemplate().update(this.getNamespace().concat("updateLiquidateInfoStatus"), hMap) ;
	}

}
