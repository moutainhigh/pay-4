package com.pay.api.service.impl;

import java.util.List;

import com.pay.api.model.MerchantConfigure;
import com.pay.api.service.MerchantConfigureService;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;

public class MerchantConfigureServiceImpl implements MerchantConfigureService {

	private BaseDAO<MerchantConfigure> merchantConfigureDao;
	
	public void setMerchantConfigureDao(BaseDAO<MerchantConfigure> merchantConfigureDao) {
		this.merchantConfigureDao = merchantConfigureDao;
	}
	
	@Override
	public Integer create(MerchantConfigure merchantConfigure) {
		return (Integer)merchantConfigureDao.create(merchantConfigure);
	}
	
	@Override
	public boolean delete(Long id) {
		return merchantConfigureDao.delete(id);
	}
	
	@Override
	public MerchantConfigure findById(Integer id) {
		return (MerchantConfigure) merchantConfigureDao.findById(id);
	}
	
	@Override
	public List<MerchantConfigure> query(Page page, Object criteria) {
		List<MerchantConfigure> list = merchantConfigureDao.findByCriteria(criteria, page);
		return list;
	}
	
	@Override
	public boolean update(MerchantConfigure merchantConfigure) {
		return merchantConfigureDao.update(merchantConfigure);
	}

	@Override
	public MerchantConfigure findObjectByCriteria(Object criteria) {
		return (MerchantConfigure)merchantConfigureDao.findObjectByCriteria(criteria);
	}
}
