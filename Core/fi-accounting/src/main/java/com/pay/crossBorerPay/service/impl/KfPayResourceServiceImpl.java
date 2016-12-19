package com.pay.crossBorerPay.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pay.crossBorerPay.dao.KfPayResourceDao;
import com.pay.crossBorerPay.service.KfPayResourceService;
import com.pay.txncore.model.KfPayResource;

@Service(value="kfPayResourceServiceImpl")
public class KfPayResourceServiceImpl implements KfPayResourceService{
	@Autowired
	@Qualifier(value="kfPayResourceDaoImpl")
	private KfPayResourceDao kfPayResourceDaoImpl;
	@Override
	public KfPayResource findDownloadUrl(KfPayResource kfPayResource) {
		return kfPayResourceDaoImpl.findObjectByCriteria("queryConditions", kfPayResource);
	}
	@Override
	public List<KfPayResource> findDownloadUrl(Map<String, Object> paraMap) {
		return kfPayResourceDaoImpl.findByCriteria("queryConditionsMap", paraMap);
	}
	@Override
	public void save(KfPayResource kfPayResource) {
		kfPayResourceDaoImpl.create(kfPayResource);
	}
	
}
