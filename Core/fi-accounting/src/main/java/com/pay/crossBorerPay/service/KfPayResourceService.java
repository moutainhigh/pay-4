package com.pay.crossBorerPay.service;

import java.util.List;
import java.util.Map;

import com.pay.txncore.model.KfPayResource;

public interface KfPayResourceService {
	KfPayResource findDownloadUrl(KfPayResource kfPayResource);
	void save(KfPayResource kfPayResource);
	List<KfPayResource> findDownloadUrl(Map<String, Object> paraMap);
}
