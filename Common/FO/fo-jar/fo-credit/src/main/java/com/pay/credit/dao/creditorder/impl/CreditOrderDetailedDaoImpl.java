package com.pay.credit.dao.creditorder.impl;

import java.util.List;
import java.util.Map;

import com.pay.credit.dao.creditorder.ICreditOrderDetailedDao;
import com.pay.credit.model.creditorder.CreditOrderDetailed;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class CreditOrderDetailedDaoImpl extends BaseDAOImpl<CreditOrderDetailed> implements ICreditOrderDetailedDao {

	public List<CreditOrderDetailed> findCreditOrderDetailed(
			CreditOrderDetailed creditOrderDetailed,Page page) {
		 return super.findByCriteria("findByCriteria",creditOrderDetailed,page);
	}

	@Override
	public List<CreditOrderDetailed> findCreditOrderDetailedAll(
			String creditOrderId) {
		CreditOrderDetailed creditOrderDetailed = new CreditOrderDetailed();
			creditOrderDetailed.setCreditOrderId(creditOrderId);
		List<CreditOrderDetailed> queryForList = this.getSqlMapClientTemplate().queryForList("creditorderdetailed.findCreditOrderDetailedAll",creditOrderDetailed);
          return queryForList;
	}

	@Override
	public void updateCreditDetail(Map map) {
		this.getSqlMapClientTemplate().update("creditorderdetailed.updateCreditDetail", map);
	}
}
