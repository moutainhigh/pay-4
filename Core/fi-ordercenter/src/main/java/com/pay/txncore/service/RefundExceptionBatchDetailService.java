/**
 * 
 */
package com.pay.txncore.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.dto.RefundExceptionBatchDetailDTO;

/**
 * 
 * @author PengJiangbo
 *
 */
public interface RefundExceptionBatchDetailService {
	
	/**
	 * 
	 * @param lists
	 * @return
	 */
	boolean insertRefundExceptionBatchDetail(List<RefundExceptionBatchDetailDTO> lists) ;
	/**
	 * 
	 * @param lists
	 * @param object
	 * @return
	 */
	List<RefundExceptionBatchDetailDTO> insertRefundExceptionBatchDetail(List<RefundExceptionBatchDetailDTO> lists, Object object) ;
	/**
	 * 
	 * @param hMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	boolean updateRefundExceptionBatchDetailStatus(Map hMap) ;
	/**
	 * 
	 * @param hMap
	 * @return
	 */
	List<RefundExceptionBatchDetailDTO> queryRefundExceptionBatchDetail(Map hMap) ;
	/**
	 * 
	 * @param hMap
	 * @param page
	 * @return
	 */
	List<RefundExceptionBatchDetailDTO> queryRefundExceptionBatchDetail(Map hMap, Page page) ;
}
