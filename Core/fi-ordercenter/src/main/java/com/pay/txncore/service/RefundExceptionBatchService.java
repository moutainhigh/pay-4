package com.pay.txncore.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.dto.RefundExceptionBatchDTO;
import com.pay.txncore.dto.RefundExceptionBatchDetailDTO;

/**
 * 退款批量状态更新Service
 * @author PengJiangbo
 *
 */
public interface RefundExceptionBatchService {
	
	/**
	 * 
	 * @param hMap
	 */
	void saveRefundExceptionBatchRnTx(Map hMap) ;
	/**
	 * 
	 * @param hMap
	 * @param lists
	 */
	void saveRefundExceptionBatchRnTx(Map hMap, List lists) ;
	/**
	 * 
	 * @param hMap
	 * @param lists
	 * @param object
	 * @return
	 */
	List<RefundExceptionBatchDetailDTO> saveRefundExceptionBatchRnTx(Map hMap, List lists, Object object) ;
	/**
	 * 批次查询
	 * @param hMap
	 * @return
	 */
	List<RefundExceptionBatchDTO> queryRefundExceptionBatch(Map hMap) ;
	/**
	 * 
	 * @param hMap
	 * @param page
	 * @return
	 */
	List<RefundExceptionBatchDTO> queryRefundExceptionBatch(Map hMap, Page page) ;
	
}
