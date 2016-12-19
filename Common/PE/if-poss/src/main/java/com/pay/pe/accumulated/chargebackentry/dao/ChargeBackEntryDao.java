package com.pay.pe.accumulated.chargebackentry.dao;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.pe.accumulated.chargebackentry.dto.ChargeBackEntryDto;

public interface ChargeBackEntryDao extends BaseDAO<ChargeBackEntryDto> {

	List<ChargeBackEntryDto> selectAccumulatedChargebackEntry(Long voucherCode);
	
	public boolean updateChargeBackLogStatus(Long voucherCode,Integer Status);
	
}
