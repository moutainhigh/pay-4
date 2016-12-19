package com.pay.rm.result.dao.impl;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.rm.result.dao.CybsResultDAO;
import com.pay.rm.result.dto.CybsResult;

public class CybsResultDAOImpl extends BaseDAOImpl<CybsResult> implements CybsResultDAO{


	@Override
	public List<CybsResult> findCybsResult(CybsResult cy) {
		// TODO Auto-generated method stub
		return super.findByCriteria(cy);
	}
	@Override
	public List<CybsResult> findCybsResult(CybsResult cy,Page page) {
		// TODO Auto-generated method stub
		return super.findByCriteria("findByCriteria",cy,page);
	}

}
