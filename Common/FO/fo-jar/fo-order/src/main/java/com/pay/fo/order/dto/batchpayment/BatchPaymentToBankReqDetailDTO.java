package com.pay.fo.order.dto.batchpayment;

import java.beans.PropertyDescriptor;
import java.util.Date;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.pay.util.StringUtil;

public class BatchPaymentToBankReqDetailDTO extends RequestDetail {

	/**
	 * 属性名称
	 */
	public static String[] title_columns_propties = new String[] {
			"payeeBankProvinceName", "payeeBankCityName", "payeeBankName",
			"payeeOpeningBankName", "tradeType", "payeeName",
			"payeeBankAcctCode", "requestAmount", "remark", "foreignOrderId" };
	
	/**
	 * 字段名称
	 */
	public static String[] title_columns_value = new String[] { "开户银行所属省份",
			"开户银行所属城市", "银行名称", "开户行名称", "收款方账户类型", "收款方姓名", "收款方银行账号", "金额",
			"备注", "商家订单号" };

	/**
	 * 字段长度
	 */
	public static Integer[] title_columns_length = new Integer[] { 200, 100,
			60, 256, 1, 256, 32, 19, 150, 30 };
	/**
	 * 对私
	 */
	public static String PESONER = "C";
	/**
	 * 对公
	 */
	public static String ENTERPRICE = "B";

	/**
	 * 明细流水号
	 */
	private Long detailSeq;

	/**
	 * 请求流水号
	 */
	private Long requestSeq;

	/**
	 * 商户订单号
	 */
	private String foreignOrderId;

	/**
	 * 收款方名称
	 */
	private String payeeName;

	/**
	 * 收款方银行账号
	 */
	private String payeeBankAcctCode;

	/**
	 * 收款方账户类型（交易类型）
	 */
	private String tradeType;

	/**
	 * 收款行银行代码
	 */
	private String payeeBankCode;

	/**
	 * 收款行银行名称
	 */
	private String payeeBankName;

	/**
	 * 收款方开户行名称
	 */
	private String payeeOpeningBankName;

	/**
	 * 收款行所在省份代码
	 */
	private String payeeBankProvince;

	/**
	 * 收款行所在省份名称
	 */
	private String payeeBankProvinceName;

	/**
	 * 收款行所在城市代码
	 */
	private String payeeBankCity;

	/**
	 * 收款行所在城市名称
	 */
	private String payeeBankCityName;

	/**
	 * 请求付款金额
	 */
	private String requestAmount;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 验证状态
	 */
	private Integer validStatus;

	/**
	 * 验证错误信息
	 */
	private String errorMsg;

	/**
	 * 付款金额
	 */
	private Long paymentAmount;

	/**
	 * 是否是付款方付手续费
	 */
	private Integer isPayerPayFee;

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
	 * 订单状态
	 */
	private Integer orderStatus;

	/**
	 * 订单状态(描述)
	 */
	private String orderStatusDesc;

	/**
	 * 创建日期
	 */
	private Date createDate;

	/**
	 * 更新日期
	 */
	private Date updateDate;
	
	/**
	 * 开户行联行号
	 */
	private String bankNumber;
	
	//------------------------added  PengJiangbo
	/**
	 * 付款方账户类型
	 */
	private Integer payerAcctType;
	/**
	 * 收款方账户类型
	 */
	private Integer payeeAcctType;
	/**
	 * 付款方币种代码
	 */
	private String payerCurrencyCode;
	/**
	 * 收款方币种代码
	 */
	private String payeeCurrencyCode;
	//------------------------added  PengJiangbo end

	/**
	 * @return the detailSeq
	 */
	public Long getDetailSeq() {
		return detailSeq;
	}

	/**
	 * @param detailSeq
	 *            the detailSeq to set
	 */
	public void setDetailSeq(Long detailSeq) {
		this.detailSeq = detailSeq;
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
	 * @return the foreignOrderId
	 */
	public String getForeignOrderId() {
		return foreignOrderId;
	}

	/**
	 * @param foreignOrderId
	 *            the foreignOrderId to set
	 */
	public void setForeignOrderId(String foreignOrderId) {
		this.foreignOrderId = foreignOrderId;
	}

	/**
	 * @return the payeeName
	 */
	public String getPayeeName() {
		if (!StringUtil.isEmpty(payeeName)) {
			return payeeName.trim();
		}
		return payeeName;
	}

	/**
	 * @param payeeName
	 *            the payeeName to set
	 */
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	/**
	 * @return the payeeBankAcctCode
	 */
	public String getPayeeBankAcctCode() {
		return payeeBankAcctCode;
	}

	/**
	 * @param payeeBankAcctCode
	 *            the payeeBankAcctCode to set
	 */
	public void setPayeeBankAcctCode(String payeeBankAcctCode) {
		this.payeeBankAcctCode = payeeBankAcctCode;
	}

	/**
	 * @return the tradeType
	 */
	public String getTradeType() {
		return tradeType;
	}

	/**
	 * @param tradeType
	 *            the tradeType to set
	 */
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	/**
	 * @return the payeeBankCode
	 */
	public String getPayeeBankCode() {
		return payeeBankCode;
	}

	/**
	 * @param payeeBankCode
	 *            the payeeBankCode to set
	 */
	public void setPayeeBankCode(String payeeBankCode) {
		this.payeeBankCode = payeeBankCode;
	}

	/**
	 * @return the payeeBankName
	 */
	public String getPayeeBankName() {
		return payeeBankName;
	}

	/**
	 * @param payeeBankName
	 *            the payeeBankName to set
	 */
	public void setPayeeBankName(String payeeBankName) {
		this.payeeBankName = payeeBankName;
	}

	/**
	 * @return the payeeOpeningBankName
	 */
	public String getPayeeOpeningBankName() {
		return payeeOpeningBankName;
	}

	/**
	 * @param payeeOpeningBankName
	 *            the payeeOpeningBankName to set
	 */
	public void setPayeeOpeningBankName(String payeeOpeningBankName) {
		this.payeeOpeningBankName = payeeOpeningBankName;
	}

	/**
	 * @return the payeeBankProvince
	 */
	public String getPayeeBankProvince() {
		return payeeBankProvince;
	}

	/**
	 * @param payeeBankProvince
	 *            the payeeBankProvince to set
	 */
	public void setPayeeBankProvince(String payeeBankProvince) {
		this.payeeBankProvince = payeeBankProvince;
	}

	/**
	 * @return the payeeBankProvinceName
	 */
	public String getPayeeBankProvinceName() {
		return payeeBankProvinceName;
	}

	/**
	 * @param payeeBankProvinceName
	 *            the payeeBankProvinceName to set
	 */
	public void setPayeeBankProvinceName(String payeeBankProvinceName) {
		this.payeeBankProvinceName = payeeBankProvinceName;
	}

	/**
	 * @return the payeeBankCity
	 */
	public String getPayeeBankCity() {
		return payeeBankCity;
	}

	/**
	 * @param payeeBankCity
	 *            the payeeBankCity to set
	 */
	public void setPayeeBankCity(String payeeBankCity) {
		this.payeeBankCity = payeeBankCity;
	}

	/**
	 * @return the payeeBankCityName
	 */
	public String getPayeeBankCityName() {
		return payeeBankCityName;
	}

	/**
	 * @param payeeBankCityName
	 *            the payeeBankCityName to set
	 */
	public void setPayeeBankCityName(String payeeBankCityName) {
		this.payeeBankCityName = payeeBankCityName;
	}

	/**
	 * @return the requestAmount
	 */
	public String getRequestAmount() {
		return requestAmount;
	}

	/**
	 * @param requestAmount
	 *            the requestAmount to set
	 */
	public void setRequestAmount(String requestAmount) {
		this.requestAmount = requestAmount;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the validStatus
	 */
	public Integer getValidStatus() {
		return validStatus;
	}

	/**
	 * @param validStatus
	 *            the validStatus to set
	 */
	public void setValidStatus(Integer validStatus) {
		this.validStatus = validStatus;
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
	 * @return the paymentAmount
	 */
	public Long getPaymentAmount() {
		return paymentAmount;
	}

	/**
	 * @param paymentAmount
	 *            the paymentAmount to set
	 */
	public void setPaymentAmount(Long paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	/**
	 * @return the isPayerPayFee
	 */
	public Integer getIsPayerPayFee() {
		return isPayerPayFee;
	}

	/**
	 * @param isPayerPayFee
	 *            the isPayerPayFee to set
	 */
	public void setIsPayerPayFee(Integer isPayerPayFee) {
		this.isPayerPayFee = isPayerPayFee;
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
	 * @return the orderStatus
	 */
	public Integer getOrderStatus() {
		return orderStatus;
	}

	/**
	 * @param orderStatus
	 *            the orderStatus to set
	 */
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
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
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate
	 *            the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the orderStatusDesc
	 */
	public String getOrderStatusDesc() {
		return orderStatusDesc;
	}

	/**
	 * @param orderStatusDesc
	 *            the orderStatusDesc to set
	 */
	public void setOrderStatusDesc(String orderStatusDesc) {
		this.orderStatusDesc = orderStatusDesc;
	}

	public String getBankNumber() {
		return bankNumber;
	}

	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}

	public Integer getPayerAcctType() {
		return payerAcctType;
	}

	public void setPayerAcctType(Integer payerAcctType) {
		this.payerAcctType = payerAcctType;
	}

	public Integer getPayeeAcctType() {
		return payeeAcctType;
	}

	public void setPayeeAcctType(Integer payeeAcctType) {
		this.payeeAcctType = payeeAcctType;
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

	@Override
	public String toString() {
		return "BatchPaymentToBankReqDetailDTO [detailSeq=" + detailSeq
				+ ", requestSeq=" + requestSeq + ", foreignOrderId="
				+ foreignOrderId + ", payeeName=" + payeeName
				+ ", payeeBankAcctCode=" + payeeBankAcctCode + ", tradeType="
				+ tradeType + ", payeeBankCode=" + payeeBankCode
				+ ", payeeBankName=" + payeeBankName
				+ ", payeeOpeningBankName=" + payeeOpeningBankName
				+ ", payeeBankProvince=" + payeeBankProvince
				+ ", payeeBankProvinceName=" + payeeBankProvinceName
				+ ", payeeBankCity=" + payeeBankCity + ", payeeBankCityName="
				+ payeeBankCityName + ", requestAmount=" + requestAmount
				+ ", remark=" + remark + ", validStatus=" + validStatus
				+ ", errorMsg=" + errorMsg + ", paymentAmount=" + paymentAmount
				+ ", isPayerPayFee=" + isPayerPayFee + ", fee=" + fee
				+ ", realpayAmount=" + realpayAmount + ", realoutAmount="
				+ realoutAmount + ", orderStatus=" + orderStatus
				+ ", orderStatusDesc=" + orderStatusDesc + ", createDate="
				+ createDate + ", updateDate=" + updateDate + ", bankNumber="
				+ bankNumber + ", payerAcctType=" + payerAcctType
				+ ", payeeAcctType=" + payeeAcctType + ", payerCurrencyCode="
				+ payerCurrencyCode + ", payeeCurrencyCode="
				+ payeeCurrencyCode + "]";
	}

}