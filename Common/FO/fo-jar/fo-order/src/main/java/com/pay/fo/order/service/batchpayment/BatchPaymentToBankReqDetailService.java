/**
 * 
 */
package com.pay.fo.order.service.batchpayment;

import java.util.List;

import com.pay.inf.exception.AppException;
import com.pay.fo.order.dto.batchpayment.BatchPaymentReqBaseInfoDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentToBankReqDetailDTO;
import com.pay.fo.order.dto.batchpayment.RequestDetail;

/**
 * @author NEW
 *
 */
public interface BatchPaymentToBankReqDetailService {

	/**
	 * 存储批付到银行请求明细信息
	 * @param detail
	 */
	void create(BatchPaymentToBankReqDetailDTO detail);
	
	/**
	 * 批量存储批付到银行请求明细信息
	 * @param details
	 * @param reqInfo
	 * @throws AppException 
	 */
	void create(List<RequestDetail> details,BatchPaymentReqBaseInfoDTO reqInfo) throws AppException;
	
	/**
	 * 更新批付到银行请求信息
	 * @param detail
	 * @return
	 */
	boolean updateStatus(BatchPaymentToBankReqDetailDTO detail,int oldStatus);
	
	/**
	 * 获取单笔批付到银行请求明细
	 * @param detailSeq
	 * @return
	 */
	BatchPaymentToBankReqDetailDTO getDetail(Long detailSeq);
	
	/**
	 * 获取验证明细列表
	 * @param requestSeq 请求流水号
	 * @param validateStatus 0：未验证通过；1：验证通过  
	 * @return
	 */
	List<BatchPaymentToBankReqDetailDTO> getValidateDetailList(Long requestSeq,Integer validateStatus);
	
	/**
	 * 获取创建订单明细列表
	 * @param requestSeq 请求流水号
	 * @param orderStatus 0：未生成订单；1：已生成订单
	 * @return
	 */
	List<BatchPaymentToBankReqDetailDTO> getCreateOrderDetailList(Long requestSeq,Integer orderStatus);
}
