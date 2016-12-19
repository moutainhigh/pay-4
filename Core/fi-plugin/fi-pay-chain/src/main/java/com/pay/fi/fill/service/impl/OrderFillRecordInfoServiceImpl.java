/**
 * 
 */
package com.pay.fi.fill.service.impl;

import java.util.List;
import java.util.Map;

import com.pay.fi.fill.dao.OrderFillRecordInfoDao;
import com.pay.fi.fill.model.FillRecordInfo;
import com.pay.fi.fill.service.OrderFillRecordInfoService;

/**
 * @author PengJiangbo
 *
 */
public class OrderFillRecordInfoServiceImpl implements
		OrderFillRecordInfoService {

	private OrderFillRecordInfoDao orderFillRecordInfoDao ;
	
	@Override
	public void orderFillRecordSave(List<FillRecordInfo> list) {
		
		this.orderFillRecordInfoDao.orderFillRecordSave(list);
	}

	public void setOrderFillRecordInfoDao(
			OrderFillRecordInfoDao orderFillRecordInfoDao) {
		this.orderFillRecordInfoDao = orderFillRecordInfoDao;
	}

	@Override
	public List<FillRecordInfo> findOrderFillRecordByReqBatchNo(Long reqBatchNo) {
		return this.orderFillRecordInfoDao.findOrderFillRecordByReqBatchNo(reqBatchNo) ;
	}

	@Override
	public int updateRecordStatusByReqBatchNo(Map<String, Object> hMap) {
		return this.orderFillRecordInfoDao.updateRecordStatusByReqBatchNo(hMap) ;
	}

	@Override
	public boolean updateFillRecordBatch(List<FillRecordInfo> list) {
		return this.orderFillRecordInfoDao.updateFillRecordBatch(list);
	}
	
}
