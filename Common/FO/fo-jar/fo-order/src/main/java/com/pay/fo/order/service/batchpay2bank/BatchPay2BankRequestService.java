/**
 * 
 */
package com.pay.fo.order.service.batchpay2bank;

import java.io.InputStream;

import com.pay.fo.order.dto.batchpayment.BatchPaymentReqBaseInfoDTO;
import com.pay.inf.exception.AppException;

/**
 * @author NEW
 * 
 */
public interface BatchPay2BankRequestService {

	/**
	 * 解析批次文件
	 * 
	 * @param file
	 * @param maxSize
	 * @param businessBatchNo
	 * @return
	 */
	void parseRequestInfo(byte[] file,BatchPaymentReqBaseInfoDTO reqInfo);
	
	void parseRequestInfo(InputStream file,BatchPaymentReqBaseInfoDTO reqInfo,int fileType);

	/**
	 * 验证请求信息
	 * 
	 * @param reqInfo
	 */
	void validateRequestInfo(BatchPaymentReqBaseInfoDTO reqInfo);

	/**
	 * 存储批付到银行请求信息
	 * 
	 * @param baseInfo
	 *            请求基本信息
	 * @param details
	 *            请求明细信息
	 * @throws AppException
	 */
	void saveRequestInfoRnTx(BatchPaymentReqBaseInfoDTO reqInfo)
			throws AppException;

	/**
	 * 确认提交批付到银行请求
	 * 
	 * @param reqInfo
	 * @throws AppException
	 */
	void confirmRequestInfoRdTx(BatchPaymentReqBaseInfoDTO reqInfo)
			throws AppException;

	
	/**
	 * 审核通过
	 * 
	 * @param reqInfo
	 * @param oldStatus
	 * @throws AppException
	 */
	void auditPassRequestRdTx(BatchPaymentReqBaseInfoDTO reqInfo,int oldStatus) throws AppException;
	
	/**
	 * 审核通过
	 * 
	 * @param requestSeq
	 *            请求流水号
	 * @param auditor
	 *            审核员
	 * @param auditRemark
	 *            审核备注
	 * @throws AppException
	 */
	void auditPassRequestRdTx(Long requestSeq, String auditor,
			String auditRemark) throws AppException;

	/**
	 * 审核拒绝
	 * 
	 * @param requestSeq
	 *            请求流水号
	 * @param auditor
	 *            审核员
	 * @param auditRemark
	 *            审核备注
	 */
	void auditRejectRequestRdTx(Long requestSeq, String auditor,
			String auditRemark);

	/**
	 * 处理完成的批量付款到银行的订单信息
	 */
	void processCompleteBatchPay2BankOrder();
	
	/**
	 * 执行任务，供自动批量付款到银行任务模块调用
	 * @param reqInfo
	 */
	 void executeTask(BatchPaymentReqBaseInfoDTO reqInfo );
	 
	 /**
	  * 关闭任务
	  * @param reqInfo
	  */
	 void closeTask(BatchPaymentReqBaseInfoDTO reqInfo);

}
