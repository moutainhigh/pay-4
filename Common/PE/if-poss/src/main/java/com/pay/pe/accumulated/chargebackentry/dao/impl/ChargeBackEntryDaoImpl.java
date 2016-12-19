package com.pay.pe.accumulated.chargebackentry.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.pe.accumulated.chargebackentry.dao.ChargeBackEntryDao;
import com.pay.pe.accumulated.chargebackentry.dto.ChargeBackEntryDto;

public class ChargeBackEntryDaoImpl extends BaseDAOImpl<ChargeBackEntryDto>
		implements ChargeBackEntryDao {

	@Override
	public List<ChargeBackEntryDto> selectAccumulatedChargebackEntry(
			Long voucherCode) {
		Map<String, Object> paramMap=new HashMap<String,Object>(1);
		paramMap.put("voucherCode", voucherCode);
		return getSqlMapClientTemplate().queryForList(namespace.concat("selectAccumulatedChargebackEntry"),paramMap);
	}

	@Override
	public boolean updateChargeBackLogStatus(Long voucherCode, Integer status) {
		Map<String, Object> paramMap=new HashMap<String,Object>(2);
		paramMap.put("voucherCode", voucherCode);
		paramMap.put("status", status);
		Integer obj=getSqlMapClientTemplate().update(namespace.concat("updateChargeBackLogStatus"),paramMap);
		if(null!=obj && obj>0){
			return true;
		}
		return false;
	}

}
