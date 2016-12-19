package com.pay.txncore.dao.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.dao.BouncedFraudQueryDAO;
import com.pay.txncore.dto.BouncedFraudResultDTO;

public class BouncedFraudQueryDAOImpl extends
		BaseDAOImpl<BouncedFraudResultDTO> implements BouncedFraudQueryDAO {


	@Override
	public List<BouncedFraudResultDTO> bouncedFraudQuery(Map<String, Object> map) {
		return super.findByCriteria("bouncedFraudQuery", map);
	}

	
}
