package com.pay.fundout.withdraw.dao.autoriskcontrol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;

public interface AutoRiskControlDAO extends BaseDAO<HashMap> {

	public List findFundoutAmounts(Map params);

	public List findPayeeBankAccs(Map params);

	public List findReceivedPersonAccTransferAmounts(Map params);

	public List findReceivedPersonAccTransferTimes(Map params);
}
