package com.pay.credit.service.creditorder;

import java.util.List;
import java.util.Map;

import com.pay.credit.model.creditorder.CreditOrderDetailed;
import com.pay.inf.dao.Page;

public interface ICreditOrderDetailedService {

	List<CreditOrderDetailed> findCreditOrderDetailed(
			CreditOrderDetailed creditOrderDetailed,Page page);

	List<CreditOrderDetailed> findCreditOrderDetailedAll(String creditOrderId);

	void updateCreditDetail(List<Map<String, String>> resultScore);

}
