package com.pay.txncore.crosspay.dao;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.txncore.crosspay.model.ExpressTracking;

public interface ExpressTrackingDAO extends BaseDAO {
	
	public List<ExpressTracking> queryTrackingDetailList(
			Map queryDetailPara, Integer pageNo,
			Integer pageSize);

	Map<String, Object> countTrackingDetailList(Map queryDetailPara);

	List<ExpressTracking> queryTrackingDetailList(Map queryDetailPara);
	
	boolean updateTrackingInfo(String sql,ExpressTracking expressTracking);

}