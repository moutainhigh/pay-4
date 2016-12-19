/**
 * 
 */
package com.pay.txncore.service.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.dao.RefundExceptionBatchDetailDAO;
import com.pay.txncore.dto.RefundExceptionBatchDetailDTO;
import com.pay.txncore.service.RefundExceptionBatchDetailService;

/**
 * 退款批量状态更新批次详情Service
 * @author PengJiangbo
 *
 */
public class RefundExceptionBatchDetailServiceImpl
		implements RefundExceptionBatchDetailService {

	//注入
	private RefundExceptionBatchDetailDAO refundExceptionBatchDetailDAO ;
	
	public void setRefundExceptionBatchDetailDAO(
			RefundExceptionBatchDetailDAO refundExceptionBatchDetailDAO) {
		this.refundExceptionBatchDetailDAO = refundExceptionBatchDetailDAO;
	}

	@Override
	public boolean insertRefundExceptionBatchDetail(
			List<RefundExceptionBatchDetailDTO> lists) {
		return this.refundExceptionBatchDetailDAO.insertRefundExceptionBatchDetail(lists);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public boolean updateRefundExceptionBatchDetailStatus(Map hMap) {
		return this.refundExceptionBatchDetailDAO.update(hMap) ;
	}

	@Override
	public List<RefundExceptionBatchDetailDTO> insertRefundExceptionBatchDetail(
			List<RefundExceptionBatchDetailDTO> lists, Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<RefundExceptionBatchDetailDTO> queryRefundExceptionBatchDetail(
			Map hMap) {
		return this.refundExceptionBatchDetailDAO.findByCriteria(hMap) ;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<RefundExceptionBatchDetailDTO> queryRefundExceptionBatchDetail(
			Map hMap, Page page) {
		return this.refundExceptionBatchDetailDAO.findByCriteria(hMap, page) ;
	}


}
