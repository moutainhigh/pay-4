/**
 * 
 */
package com.pay.batchpay.service.bulkpayment.impl;

import java.util.List;
import java.util.Map;

import com.pay.batchpay.dao.bulkpayment.BulkpayOrderDao;
import com.pay.batchpay.dto.BulkPaymentOrder;
import com.pay.batchpay.dto.OrderTemplateUnion;
import com.pay.batchpay.service.bulkpayment.BulkpayOrderService;

/**
 * @author PengJiangbo
 *
 */
public class BulkpayOrderServiceImpl implements BulkpayOrderService {

	//注入
	private BulkpayOrderDao bulkpayOrderDao ;
	
	public void setBulkpayOrderDao(BulkpayOrderDao bulkpayOrderDao) {
		this.bulkpayOrderDao = bulkpayOrderDao;
	}

	@Override
	public long insertBulkpayOrder(BulkPaymentOrder bulkPaymentOrder) {
		return this.bulkpayOrderDao.insertBulkpayOrder(bulkPaymentOrder) ;
	}

	@Override
	public List<OrderTemplateUnion> findBulkpayOrder(Map<String, Object> map) {
		return this.bulkpayOrderDao.findBulkpayOrder(map) ;
	}

	@Override
	public int updateBulkordedrStatus(Map<String, Object> map) {
		return this.bulkpayOrderDao.updateBulkordedrStatus(map) ;
	}

	@Override
	public String findBulkorderFilePath(Map<String, Object> map) {
		return this.bulkpayOrderDao.findBulkorderFilePath(map);
	}

}
