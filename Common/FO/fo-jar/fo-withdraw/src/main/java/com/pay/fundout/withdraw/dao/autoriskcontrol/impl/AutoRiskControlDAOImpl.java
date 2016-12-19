package com.pay.fundout.withdraw.dao.autoriskcontrol.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.dao.autoriskcontrol.AutoRiskControlDAO;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class AutoRiskControlDAOImpl extends BaseDAOImpl<HashMap> implements
		AutoRiskControlDAO {

	@Override
	public List findFundoutAmounts(Map params) {
		return super.findByQuery("autoriskcontrol.findfundoutamounts", params);
	}

	@Override
	public List findPayeeBankAccs(Map params) {
		return super.findByQuery("autoriskcontrol.findpayeebankaccs", params);
	}

	@Override
	public List findReceivedPersonAccTransferAmounts(Map params) {
		return super.findByQuery(
				"autoriskcontrol.findreceivedpersonacctransferamounts", params);
	}

	@Override
	public List findReceivedPersonAccTransferTimes(Map params) {
		return super.findByQuery(
				"autoriskcontrol.findreceivedpersonacctransfertimes", params);
	}
}
