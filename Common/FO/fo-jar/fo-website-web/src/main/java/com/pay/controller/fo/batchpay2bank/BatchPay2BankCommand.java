/**
 * 
 */
package com.pay.controller.fo.batchpay2bank;

import java.io.Serializable;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.pay.fo.order.common.OrderType;

/**
 * @author NEW
 *
 */
public class BatchPay2BankCommand implements Serializable {

	private static final long serialVersionUID = 8984652038081475028L;

	/**
	 * 请求流水号
	 */
	private Long requestSeq;

	/**
	 * 请求类型
	 */
	private final Integer requestType = OrderType.BATCHPAY2BANK.getValue();

	/**
	 * 业务批次号
	 */
	private String businessBatchNo;

	/**
	 * 付款方名称
	 */
	private String payerName;

	/**
	 * 付款方登录标识
	 */
	private String payerLoginName;

	/**
	 * 付款方会员号
	 */
	private Long payerMemberCode;

	/**
	 * 付款方会员类型
	 */
	private Integer payerMemberType;

	/**
	 * 付款方账号
	 */
	private String payerAcctCode;

	/**
	 * 付款方账号类型
	 */
	private Integer payerAcctType;

	/**
	 * 创建者
	 */
	private String creator;

	/**
	 * 创建日期
	 */
	private Date createDate;

	/**
	 * 审核状态
	 */
	private Integer status;

	/**
	 * 请求付款金额
	 */
	private Long requestAmount;

	/**
	 * 请求付款次数
	 */
	private Integer requestCount;

	/**
	 * 有效金额
	 */
	private Long validAmount;

	/**
	 * 有效金额(格式化)
	 */
	private String validAmountStr;

	/**
	 * 无效金额(格式化)
	 */
	private String invalidAmountStr;

	/**
	 * 有效笔数
	 */
	private Integer validCount;

	/**
	 * 是否是付款方付手续费
	 */
	private final Integer isPayerPayFee = 1;

	/**
	 * 手续费
	 */
	private Long fee;

	/**
	 * 实际付款金额
	 */
	private Long realpayAmount;

	/**
	 * 实际出款金额
	 */
	private Long realoutAmount;

	/**
	 * 请求来源
	 */
	private String requestSrc;

	/**
	 * 错误信息
	 */
	private String errorMsg;

	/**
	 * 请求付款金额（格式化）
	 */
	private String requestAmountStr;
	/**
	 * 手续费（格式化）
	 */
	private String requestFee;
	/**
	 * 实际付款金额（格式化）
	 */
	private String realpayAmountStr;
	/**
	 * 实际出款金额（格式化）
	 */
	private String realoutAmountStr;

	/**
	 * 当前余额
	 */
	private Long currentBanlance;
	/**
	 * 当前余额(格式化)
	 */
	private String currentBanlanceStr;

	/**
	 * 请求文件
	 */
	// private byte[] paymentFile;

	private MultipartFile paymentFile;

	/**
	 * 请求文件名
	 */
	private String paymentFileName;

	/**
	 * 请求令牌
	 */
	private String token;

	/**
	 * 验证码
	 */
	private String randCode;
	/**
	 * 查询起始时间
	 */
	private String beginDate;
	/**
	 * 查询截止时间
	 */
	private String endDate;

	/**
	 * 复核备注
	 */
	private String auditRemark;

	/**
	 * 处理类型 0 默认 1 定期
	 */
	private Integer processType;
	/**
	 * 执行日期
	 */
	private String excuteDateStr;
	/**
	 * 执行时间（小时值）
	 */
	private Integer excuteHour;
	/**
	 * 当前日期
	 */
	private String currentDate;
	
	/**
	 * 付款方货币代码
	 */
	private String payerCurrencyCode ;
	/**
	 * 收款方货币代码
	 * @return
	 */
	private String payeeCurrencyCode;
	/**
	 * 收款方账户类型
	 * @return
	 */
	private Integer payeeAcctType;
	

	public String getInvalidAmountStr() {
		return invalidAmountStr;
	}

	public void setInvalidAmountStr(String invalidAmountStr) {
		this.invalidAmountStr = invalidAmountStr;
	}

	public String getValidAmountStr() {
		return validAmountStr;
	}

	public void setValidAmountStr(String validAmountStr) {
		this.validAmountStr = validAmountStr;
	}

	/**
	 * @return the requestSeq
	 */
	public Long getRequestSeq() {
		return requestSeq;
	}

	/**
	 * @param requestSeq
	 *            the requestSeq to set
	 */
	public void setRequestSeq(Long requestSeq) {
		this.requestSeq = requestSeq;
	}

	/**
	 * @return the businessBatchNo
	 */
	public String getBusinessBatchNo() {
		return businessBatchNo;
	}

	/**
	 * @param businessBatchNo
	 *            the businessBatchNo to set
	 */
	public void setBusinessBatchNo(String businessBatchNo) {
		this.businessBatchNo = businessBatchNo;
	}

	/**
	 * @return the payerName
	 */
	public String getPayerName() {
		return payerName;
	}

	/**
	 * @param payerName
	 *            the payerName to set
	 */
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	/**
	 * @return the payerLoginName
	 */
	public String getPayerLoginName() {
		return payerLoginName;
	}

	/**
	 * @param payerLoginName
	 *            the payerLoginName to set
	 */
	public void setPayerLoginName(String payerLoginName) {
		this.payerLoginName = payerLoginName;
	}

	/**
	 * @return the payerMemberCode
	 */
	public Long getPayerMemberCode() {
		return payerMemberCode;
	}

	/**
	 * @param payerMemberCode
	 *            the payerMemberCode to set
	 */
	public void setPayerMemberCode(Long payerMemberCode) {
		this.payerMemberCode = payerMemberCode;
	}

	/**
	 * @return the payerMemberType
	 */
	public Integer getPayerMemberType() {
		return payerMemberType;
	}

	/**
	 * @param payerMemberType
	 *            the payerMemberType to set
	 */
	public void setPayerMemberType(Integer payerMemberType) {
		this.payerMemberType = payerMemberType;
	}

	/**
	 * @return the payerAcctCode
	 */
	public String getPayerAcctCode() {
		return payerAcctCode;
	}

	/**
	 * @param payerAcctCode
	 *            the payerAcctCode to set
	 */
	public void setPayerAcctCode(String payerAcctCode) {
		this.payerAcctCode = payerAcctCode;
	}

	/**
	 * @return the creator
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * @param creator
	 *            the creator to set
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return the requestAmount
	 */
	public Long getRequestAmount() {
		return requestAmount;
	}

	/**
	 * @param requestAmount
	 *            the requestAmount to set
	 */
	public void setRequestAmount(Long requestAmount) {
		this.requestAmount = requestAmount;
	}

	/**
	 * @return the requestCount
	 */
	public Integer getRequestCount() {
		return requestCount;
	}

	/**
	 * @param requestCount
	 *            the requestCount to set
	 */
	public void setRequestCount(Integer requestCount) {
		this.requestCount = requestCount;
	}

	/**
	 * @return the validAmount
	 */
	public Long getValidAmount() {
		return validAmount;
	}

	/**
	 * @param validAmount
	 *            the validAmount to set
	 */
	public void setValidAmount(Long validAmount) {
		this.validAmount = validAmount;
	}

	/**
	 * @return the validCount
	 */
	public Integer getValidCount() {
		return validCount;
	}

	/**
	 * @param validCount
	 *            the validCount to set
	 */
	public void setValidCount(Integer validCount) {
		this.validCount = validCount;
	}

	/**
	 * @return the isPayerPayFee
	 */
	public Integer getIsPayerPayFee() {
		return isPayerPayFee;
	}

	/**
	 * @return the fee
	 */
	public Long getFee() {
		return fee;
	}

	/**
	 * @param fee
	 *            the fee to set
	 */
	public void setFee(Long fee) {
		this.fee = fee;
	}

	/**
	 * @return the realpayAmount
	 */
	public Long getRealpayAmount() {
		return realpayAmount;
	}

	/**
	 * @param realpayAmount
	 *            the realpayAmount to set
	 */
	public void setRealpayAmount(Long realpayAmount) {
		this.realpayAmount = realpayAmount;
	}

	/**
	 * @return the realoutAmount
	 */
	public Long getRealoutAmount() {
		return realoutAmount;
	}

	/**
	 * @param realoutAmount
	 *            the realoutAmount to set
	 */
	public void setRealoutAmount(Long realoutAmount) {
		this.realoutAmount = realoutAmount;
	}

	/**
	 * @return the requestSrc
	 */
	public String getRequestSrc() {
		return requestSrc;
	}

	/**
	 * @param requestSrc
	 *            the requestSrc to set
	 */
	public void setRequestSrc(String requestSrc) {
		this.requestSrc = requestSrc;
	}

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg
	 *            the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * @return the requestAmountStr
	 */
	public String getRequestAmountStr() {
		return requestAmountStr;
	}

	/**
	 * @param requestAmountStr
	 *            the requestAmountStr to set
	 */
	public void setRequestAmountStr(String requestAmountStr) {
		this.requestAmountStr = requestAmountStr;
	}

	/**
	 * @return the requestFee
	 */
	public String getRequestFee() {
		return requestFee;
	}

	/**
	 * @param requestFee
	 *            the requestFee to set
	 */
	public void setRequestFee(String requestFee) {
		this.requestFee = requestFee;
	}

	/**
	 * @return the realpayAmountStr
	 */
	public String getRealpayAmountStr() {
		return realpayAmountStr;
	}

	/**
	 * @param realpayAmountStr
	 *            the realpayAmountStr to set
	 */
	public void setRealpayAmountStr(String realpayAmountStr) {
		this.realpayAmountStr = realpayAmountStr;
	}

	/**
	 * @return the realoutAmountStr
	 */
	public String getRealoutAmountStr() {
		return realoutAmountStr;
	}

	/**
	 * @param realoutAmountStr
	 *            the realoutAmountStr to set
	 */
	public void setRealoutAmountStr(String realoutAmountStr) {
		this.realoutAmountStr = realoutAmountStr;
	}

	/**
	 * @return the requestType
	 */
	public Integer getRequestType() {
		return requestType;
	}

	/**
	 * @return the payerAcctType
	 */
	public Integer getPayerAcctType() {
		return payerAcctType;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the randCode
	 */
	public String getRandCode() {
		return randCode;
	}

	/**
	 * @param randCode
	 *            the randCode to set
	 */
	public void setRandCode(String randCode) {
		this.randCode = randCode;
	}

	/**
	 * @return the currentBanlance
	 */
	public Long getCurrentBanlance() {
		return currentBanlance;
	}

	/**
	 * @param currentBanlance
	 *            the currentBanlance to set
	 */
	public void setCurrentBanlance(Long currentBanlance) {
		this.currentBanlance = currentBanlance;
	}

	/**
	 * @return the currentBanlanceStr
	 */
	public String getCurrentBanlanceStr() {
		return currentBanlanceStr;
	}

	/**
	 * @param currentBanlanceStr
	 *            the currentBanlanceStr to set
	 */
	public void setCurrentBanlanceStr(String currentBanlanceStr) {
		this.currentBanlanceStr = currentBanlanceStr;
	}

	/**
	 * @return the paymentFileName
	 */
	public String getPaymentFileName() {
		return paymentFileName;
	}

	/**
	 * @param paymentFileName
	 *            the paymentFileName to set
	 */
	public void setPaymentFileName(String paymentFileName) {
		this.paymentFileName = paymentFileName;
	}

	/**
	 * @return the beginDate
	 */
	public String getBeginDate() {
		return beginDate;
	}

	/**
	 * @param beginDate
	 *            the beginDate to set
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the auditRemark
	 */
	public String getAuditRemark() {
		return auditRemark;
	}

	/**
	 * @param auditRemark
	 *            the auditRemark to set
	 */
	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}

	/**
	 * @return the processType
	 */
	public Integer getProcessType() {
		return processType;
	}

	/**
	 * @param processType
	 *            the processType to set
	 */
	public void setProcessType(Integer processType) {
		this.processType = processType;
	}

	public String getExcuteDateStr() {
		return excuteDateStr;
	}

	public void setExcuteDateStr(String excuteDateStr) {
		this.excuteDateStr = excuteDateStr;
	}

	/**
	 * @return the excuteHour
	 */
	public Integer getExcuteHour() {
		return excuteHour;
	}

	/**
	 * @param excuteHour
	 *            the excuteHour to set
	 */
	public void setExcuteHour(Integer excuteHour) {
		this.excuteHour = excuteHour;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public MultipartFile getPaymentFile() {
		return paymentFile;
	}

	public void setPaymentFile(MultipartFile paymentFile) {
		this.paymentFile = paymentFile;
	}

	public void setPayerAcctType(Integer payerAcctType) {
		this.payerAcctType = payerAcctType;
	}

	public String getPayerCurrencyCode() {
		return payerCurrencyCode;
	}

	public void setPayerCurrencyCode(String payerCurrencyCode) {
		this.payerCurrencyCode = payerCurrencyCode;
	}

	public String getPayeeCurrencyCode() {
		return payeeCurrencyCode;
	}

	public void setPayeeCurrencyCode(String payeeCurrencyCode) {
		this.payeeCurrencyCode = payeeCurrencyCode;
	}

	public Integer getPayeeAcctType() {
		return payeeAcctType;
	}

	public void setPayeeAcctType(Integer payeeAcctType) {
		this.payeeAcctType = payeeAcctType;
	}

	@Override
	public String toString() {
		return "BatchPay2BankCommand [requestSeq=" + requestSeq
				+ ", requestType=" + requestType + ", businessBatchNo="
				+ businessBatchNo + ", payerName=" + payerName
				+ ", payerLoginName=" + payerLoginName + ", payerMemberCode="
				+ payerMemberCode + ", payerMemberType=" + payerMemberType
				+ ", payerAcctCode=" + payerAcctCode + ", payerAcctType="
				+ payerAcctType + ", creator=" + creator + ", createDate="
				+ createDate + ", status=" + status + ", requestAmount="
				+ requestAmount + ", requestCount=" + requestCount
				+ ", validAmount=" + validAmount + ", validAmountStr="
				+ validAmountStr + ", invalidAmountStr=" + invalidAmountStr
				+ ", validCount=" + validCount + ", isPayerPayFee="
				+ isPayerPayFee + ", fee=" + fee + ", realpayAmount="
				+ realpayAmount + ", realoutAmount=" + realoutAmount
				+ ", requestSrc=" + requestSrc + ", errorMsg=" + errorMsg
				+ ", requestAmountStr=" + requestAmountStr + ", requestFee="
				+ requestFee + ", realpayAmountStr=" + realpayAmountStr
				+ ", realoutAmountStr=" + realoutAmountStr
				+ ", currentBanlance=" + currentBanlance
				+ ", currentBanlanceStr=" + currentBanlanceStr
				+ ", paymentFile=" + paymentFile + ", paymentFileName="
				+ paymentFileName + ", token=" + token + ", randCode="
				+ randCode + ", beginDate=" + beginDate + ", endDate="
				+ endDate + ", auditRemark=" + auditRemark + ", processType="
				+ processType + ", excuteDateStr=" + excuteDateStr
				+ ", excuteHour=" + excuteHour + ", currentDate=" + currentDate
				+ ", payerCurrencyCode=" + payerCurrencyCode
				+ ", payeeCurrencyCode=" + payeeCurrencyCode
				+ ", payeeAcctType=" + payeeAcctType + "]";
	}

}
