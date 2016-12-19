package com.pay.txncore.crosspay.service.impl;

import java.util.List;




import com.pay.inf.dao.Page;
import com.pay.txncore.crosspay.dao.PartnerRateFloatDAO;
import com.pay.txncore.crosspay.service.PartnerRateFloatService;
import com.pay.txncore.model.PartnerRateFloat;
import com.pay.util.BeanConvertUtil;

public class PartnerRateFloatServiceImpl implements PartnerRateFloatService {
	
	private PartnerRateFloatDAO  partnerRateFloatDAO;

	public void setPartnerRateFloatDAO(PartnerRateFloatDAO partnerRateFloatDAO) {
		this.partnerRateFloatDAO = partnerRateFloatDAO;
	}
	
	
	@Override
	public void batchCreate(List<PartnerRateFloat> rates) {	
		partnerRateFloatDAO.batchCreate(rates);
	}
	
	@Override
	public List<PartnerRateFloat> findByCriteria(PartnerRateFloat rate,Page page) {
		return (List<PartnerRateFloat>) BeanConvertUtil.convert(
				PartnerRateFloat.class, partnerRateFloatDAO.findByCriteria(
						"findByCriteria",rate, page));
	}
	
	@Override
	public List<PartnerRateFloat> findByCriteria(PartnerRateFloat rate) {
		return (List<PartnerRateFloat>) BeanConvertUtil.convert(
				PartnerRateFloat.class, partnerRateFloatDAO.findByCriteria(
						"findByCriteria",rate));
	}
	
	public PartnerRateFloat getPartnerRateFloat(PartnerRateFloat rate){
		return partnerRateFloatDAO.findById(rate);
	}
	
	public PartnerRateFloat getPartnerRateFloat(String partnerId){
		
		PartnerRateFloat rateFloat = partnerRateFloatDAO
				   .findObjectByCriteria("findByPartnerId",partnerId);
		return rateFloat;
	}
	
	@Override
	public Long save(PartnerRateFloat rateFloat) {
		PartnerRateFloat rateFloat_ = this.getPartnerRateFloat(rateFloat.getPartnerId());
		if(rateFloat_==null){
			return (Long) partnerRateFloatDAO.create(rateFloat);
		}
		
		return null;
	}
	
	@Override
	public boolean delete(PartnerRateFloat rateFloat) {
		if(rateFloat!=null){
			return partnerRateFloatDAO.delete("delete",rateFloat);
		}
		
		return false;
	}

}
