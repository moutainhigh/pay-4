package com.pay.credit.service.finace.impl;

import java.util.List;

import com.pay.credit.dao.finace.FinaceProtocolDetailDao;
import com.pay.credit.model.finace.FinaceProtocoDetail;
import com.pay.credit.service.finace.FinaceProtocolDetailService;

public class FinaceProtocolDetailServiceImpl implements FinaceProtocolDetailService{

	private FinaceProtocolDetailDao finaceProtocolDetailDao;
	
	public void setFinaceProtocolDetailDao(
			FinaceProtocolDetailDao finaceProtocolDetailDao) {
		this.finaceProtocolDetailDao = finaceProtocolDetailDao;
	}


	@Override
	public List<FinaceProtocoDetail> finaceProtocoDetailByCreditOrderId(
			String creditOrderId) {
		return finaceProtocolDetailDao.finaceProtocoDetailByCreditOrderId(creditOrderId);
	}

}
