/**
 * 
 */
package com.pay.txncore.dao;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.txncore.dto.RefundExceptionBatchDetailDTO;

/**
 * @author PengJiangbo
 *
 */
@SuppressWarnings("rawtypes")
public interface RefundExceptionBatchDetailDAO extends BaseDAO {

	/**
	 * 批次详情插入
	 * @param lists
	 * @return boolean
	 */
	boolean insertRefundExceptionBatchDetail(List<RefundExceptionBatchDetailDTO> lists) ;
	
	/**
	 * 批次详情插入
	 * @param lists
	 * @param object
	 * @return List<RefundExceptionBatchDetailDTO>
	 */
	List<RefundExceptionBatchDetailDTO> insertRefundExceptionBatchDetail(List<RefundExceptionBatchDetailDTO> lists, Object object) ;
}
