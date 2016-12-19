package com.pay.txncore.service.chargeback;

import java.util.List;
import java.util.Map;

import com.pay.txncore.model.BouncedRateListVO;

public interface BouncedFineTaskService {

	List<Map<String, Object>> queryFineRuleAndRate();

	void insertBouncedFine(Map<String, Object> fineRuleAndRate);

	List<Map> findBouncedFineOrder(Map<String, String> params);

	void updateBouncedFine(Map map);
	
	public void batchCreate(List<BouncedRateListVO> list);

	void updateFineRuleAndRate(Map map);

	Long queryBouncedFineOrderNo();
}
