package com.pay.credit.dao.finace.impl;

import java.util.List;

import com.pay.credit.dao.finace.FinaceProtocolDao;
import com.pay.credit.model.finace.FinaceProtocoDetail;
import com.pay.credit.model.finace.FinaceProtocol;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;


public class FinaceProtocolDaoImpl extends BaseDAOImpl<FinaceProtocol> implements FinaceProtocolDao{

	public List<FinaceProtocol> finaceProtocoQuery(FinaceProtocol protocol,
			Page page) {
		return super.findByCriteria("findByCriteria",protocol,page);
	}
}
