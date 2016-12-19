package com.pay.pe.accumulated.deal.dao;

import com.pay.inf.dao.BaseDAO;
import com.pay.pe.dto.DealDto;

public interface PossDealDao extends BaseDAO<DealDto> {
	
	public DealDto selectDealByVoucherCode(Long voucherCode);

}
