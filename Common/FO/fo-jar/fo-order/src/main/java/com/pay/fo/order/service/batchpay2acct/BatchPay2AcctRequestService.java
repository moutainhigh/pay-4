/**
 * 
 */
package com.pay.fo.order.service.batchpay2acct;

import java.io.IOException;

import com.pay.fo.order.dto.batchpayment.BatchPaymentReqBaseInfoDTO;
import com.pay.inf.exception.AppException;

/**
 * @author NEW
 *
 */
public interface BatchPay2AcctRequestService {
	
	/**
	 * 解析请求信息
	 * @param file
	 * @param maxSize
	 * @return
	 * @throws IOException 
	 */
	BatchPaymentReqBaseInfoDTO parseRequestInfo(byte[] file,int maxSize) throws IOException;
	
	
	/**
	 * 解析请求信息 csv
	 * @param file
	 * @param maxSize
	 * @return
	 * @throws IOException
	 */
	BatchPaymentReqBaseInfoDTO parseRequestCsvInfo(byte[] file, int maxSize) throws IOException;
	
	/**
	 * 验证请求信息
	 * @param reqInfo
	 */
	void validateRequestInfo(BatchPaymentReqBaseInfoDTO reqInfo);
	
	/**
	 * 存储批付到账户请求信息
	 * @param baseInfo 请求基本信息
	 * @param details  请求明细信息
	 * @throws AppException 
	 */
	void saveRequestInfoRnTx(BatchPaymentReqBaseInfoDTO reqInfo) throws AppException;
	
	/**
	 * 确认提交批付到账户请求
	 * @param reqInfo
	 * @throws AppException 
	 */
	void confirmRequestInfoRdTx(BatchPaymentReqBaseInfoDTO reqInfo) throws AppException;
	
	/**
	 * 审核通过
	 * @param requestSeq 请求流水号
	 * @param auditor 审核员
	 * @param auditRemark 审核备注
	 * @throws AppException 
	 */
	void auditPassRequestRdTx(Long requestSeq,String auditor,String auditRemark) throws AppException;
	
	/**
	 * 没开通审核产品，模拟审核通过情况
	 * @param requestSeq 请求流水号
	 * @param auditor 审核员
	 * @param auditRemark 审核备注
	 * @param oldStaus 原状态
	 * @throws AppException 
	 */
	void auditPassRequestRdTx(Long requestSeq,String auditor,String auditRemark, int oldStaus) throws AppException;
	
	/**
	 * 审核拒绝
	 * @param requestSeq 请求流水号
	 * @param auditor 审核员
	 * @param auditRemark 审核备注
	 */
	void auditRejectRequestRdTx(Long requestSeq,String auditor,String auditRemark);
}
