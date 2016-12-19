package com.pay.credit.service.finace.impl;

import java.util.List;

import com.pay.credit.dao.finace.FinaceProtocolDao;
import com.pay.credit.model.finace.FinaceProtocoDetail;
import com.pay.credit.model.finace.FinaceProtocol;
import com.pay.credit.service.finace.FinaceProtocolService;
import com.pay.inf.dao.Page;

public class FinaceProtocolServiceImpl implements FinaceProtocolService{

	private FinaceProtocolDao finaceProtocolDao;
	
	public void setFinaceProtocolDao(FinaceProtocolDao finaceProtocolDao) {
		this.finaceProtocolDao = finaceProtocolDao;
	}
	public List<FinaceProtocol> finaceProtocoQuery(FinaceProtocol protocol,
			Page page) {
		return finaceProtocolDao.finaceProtocoQuery(protocol,page);
	}
}
