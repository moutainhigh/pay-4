/**
 * 
 */
package com.pay.fo.order.service.batchpayment;

import com.pay.fo.order.dto.batchpayment.BatchPaymentReqBaseInfoDTO;

/**
 * @author NEW
 *
 */
public interface BatchPaymentReqBaseInfoService {
	/**
	 * 存储批量付款请求基本信息
	 * @param info 需要保存的对象
	 * @return
	 */
	Long create(BatchPaymentReqBaseInfoDTO info);
	
	/**
	 * 更新批量付款请求基本信息
	 * @param info
	 * @return
	 */
	boolean update(BatchPaymentReqBaseInfoDTO info);
	/**
	 * 更新批量付款请求基本信息状态
	 * @param info 需要更新的对象
	 * @param status 当前状态
	 * @return
	 */
	boolean updateStatus(BatchPaymentReqBaseInfoDTO info,int status);
	
	/**
	 * 获取批量付款请求基本信息
	 * @param requestSeq  请求流水号
	 * @return
	 */
	BatchPaymentReqBaseInfoDTO getBatchPaymentReqBaseInfo(Long requestSeq);

	/**
	 * 获取批量付款请求基本信息
	 * @param memberCode  会员号
	 * @param requstType  请求类型
	 * @param businessBatchNo 业务批次号 
	 * @return
	 */
	BatchPaymentReqBaseInfoDTO getBatchPaymentReqBaseInfo(Long memberCode,Integer requstType,String businessBatchNo);
	
}
