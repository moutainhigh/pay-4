package com.pay.credit.dao.creditorder;

import java.util.List;
import java.util.Map;

import com.pay.credit.model.creditorder.CreditOrderDetailed;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;

public interface ICreditOrderDetailedDao extends BaseDAO<CreditOrderDetailed>{

	List<CreditOrderDetailed> findCreditOrderDetailed(
			CreditOrderDetailed creditOrderDetailed,Page page);

	List<CreditOrderDetailed> findCreditOrderDetailedAll(String creditOrderId);

	void updateCreditDetail(Map map);

}
