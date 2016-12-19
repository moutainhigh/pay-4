package com.pay.txncore.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.inf.exception.AppException;
import com.pay.txncore.dao.RefundExceptionBatchDAO;
import com.pay.txncore.dao.RefundExceptionBatchDetailDAO;
import com.pay.txncore.dto.RefundExceptionBatchDTO;
import com.pay.txncore.dto.RefundExceptionBatchDetailDTO;
import com.pay.txncore.service.RefundExceptionBatchService;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class RefundExceptionBatchServiceImpl implements
		RefundExceptionBatchService {
	
	private static final Log logger = LogFactory.getLog(RefundExceptionBatchDetailServiceImpl.class) ;
	
	//注入
	private RefundExceptionBatchDAO refundExceptionBatchDAO ;
	private RefundExceptionBatchDetailDAO refundExceptionBatchDetailDAO ;

	public void setRefundExceptionBatchDAO(
			RefundExceptionBatchDAO refundExceptionBatchDAO) {
		this.refundExceptionBatchDAO = refundExceptionBatchDAO;
	}
	
	public void setRefundExceptionBatchDetailDAO(
			RefundExceptionBatchDetailDAO refundExceptionBatchDetailDAO) {
		this.refundExceptionBatchDetailDAO = refundExceptionBatchDetailDAO;
	}

	@Override
	public void saveRefundExceptionBatchRnTx(Map hMap) {
		this.refundExceptionBatchDAO.create(hMap) ;
	}

	
	@Override
	public void saveRefundExceptionBatchRnTx(Map hMap, List lists) {
		try {
			this.refundExceptionBatchDAO.create(hMap) ;
			this.refundExceptionBatchDetailDAO.insertRefundExceptionBatchDetail(lists) ;
		} catch (Exception e) {
			logger.error("创建退款批量状态更新批次信息失败:" + e);
			throw new AppException() ;
		}
	}

	@Override
	public List<RefundExceptionBatchDetailDTO> saveRefundExceptionBatchRnTx(
			Map hMap, List lists, Object object) {
		List<RefundExceptionBatchDetailDTO> refundExceptionBatchDetails = null ;
		try {
			this.refundExceptionBatchDAO.create(hMap) ;
			refundExceptionBatchDetails = this.refundExceptionBatchDetailDAO.insertRefundExceptionBatchDetail(lists, null) ;
		} catch (Exception e) {
			logger.error("创建退款批量状态更新批次信息失败:" + e);
			throw new AppException() ;
		}
		return refundExceptionBatchDetails ;
	}

	@Override
	public List<RefundExceptionBatchDTO> queryRefundExceptionBatch(Map hMap) {
		return this.refundExceptionBatchDAO.findByCriteria(hMap) ;
	}

	@Override
	public List<RefundExceptionBatchDTO> queryRefundExceptionBatch(Map hMap,
			Page page) {
		return this.refundExceptionBatchDAO.findByCriteria(hMap, page) ;
	}

}
