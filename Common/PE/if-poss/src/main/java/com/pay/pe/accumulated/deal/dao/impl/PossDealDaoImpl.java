package com.pay.pe.accumulated.deal.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.pe.accumulated.deal.dao.PossDealDao;
import com.pay.pe.dto.DealDto;

public class PossDealDaoImpl extends BaseDAOImpl<DealDto> implements
		PossDealDao {

	@Override
	public DealDto selectDealByVoucherCode(Long voucherCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>(1);
		paramMap.put("voucherCode", voucherCode);
		return (DealDto) getSqlMapClientTemplate().queryForObject(
				namespace.concat("selectDealByVoucherCode"), paramMap);
	}

}
