package com.pay.txncore.crosspay.service;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.txncore.model.PartnerRateFloat;

public interface PartnerRateFloatService {
	
	void batchCreate(List<PartnerRateFloat> rates);
	
	Long save(PartnerRateFloat rateFloat);
	
	List<PartnerRateFloat> findByCriteria(PartnerRateFloat rate,Page page);
	
	List<PartnerRateFloat> findByCriteria(PartnerRateFloat rate);
	
	PartnerRateFloat getPartnerRateFloat(String partnerId);
	
	PartnerRateFloat getPartnerRateFloat(PartnerRateFloat rate);
	
	boolean delete(PartnerRateFloat rateFloat);

}
