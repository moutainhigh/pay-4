package com.pay.credit.dao.finace.impl;

import java.util.List;

import com.pay.credit.dao.finace.FinaceProtocolDetailDao;
import com.pay.credit.model.finace.FinaceProtocoDetail;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class FinaceProtocolDetailDaoImpl  extends BaseDAOImpl<FinaceProtocoDetail> implements FinaceProtocolDetailDao{

	@Override
	public List<FinaceProtocoDetail> finaceProtocoDetailByCreditOrderId(
			String creditOrderId) {
		return super.findByCriteria("findByCriteria",creditOrderId);
	}

}
