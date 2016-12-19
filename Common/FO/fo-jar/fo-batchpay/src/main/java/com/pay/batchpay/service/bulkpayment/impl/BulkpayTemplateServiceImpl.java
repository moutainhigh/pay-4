/**
 * 
 */
package com.pay.batchpay.service.bulkpayment.impl;

import java.util.List;
import java.util.Map;

import com.pay.batchpay.dao.bulkpayment.BulkpayTemplateDao;
import com.pay.batchpay.dto.BulkPaymentTemplate;
import com.pay.batchpay.service.bulkpayment.BulkpayTemplateService;

/**
 * @author PengJiangbo
 *
 */
public class BulkpayTemplateServiceImpl implements BulkpayTemplateService {
	
	//注入
	private BulkpayTemplateDao bulkpayTemplateDao ;

	public void setBulkpayTemplateDao(BulkpayTemplateDao bulkpayTemplateDao) {
		this.bulkpayTemplateDao = bulkpayTemplateDao;
	}

	@Override
	public void insertBulkpayBatch(Map<String, Object> map) {
		this.bulkpayTemplateDao.insertBulkpayBatch(map); 
	}

	@Override
	public List<BulkPaymentTemplate> findBulkpayByBulkorderNo(String bulkorderNo) {
		return this.bulkpayTemplateDao.findBulkpayByBulkorderNo(bulkorderNo) ;
	}

	@Override
	public void insertBulkpayFundoutBatch(Map<String, Object> map) {
		this.bulkpayTemplateDao.insertBulkpayFundoutBatch(map);
	}
	

}
