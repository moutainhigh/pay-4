package com.pay.fundout.withdraw.model.paytoaccount;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import com.pay.poss.dto.withdraw.orderhandlermanage.BaseOrderDTO;

public class Pay2AcctOrder extends BaseOrderDTO implements Serializable {
	private static final long serialVersionUID = -5301178821985187712L;

	private String payerCode;
	private String payerAcctCode;

	private String errorTips;
	private Integer payerAcctType;
	private Integer status;
	private Long payerMember;
	private String callSeq;
	private String payeeAcctCode;
	private Long parentOrder;
	private String remarks;
	private String payeeCode;
	private String busiCode;
	private Long payeeMember;
	private String payerName;
	private String payerMail;
	private String payerPhone;
	private Long payerFee = 0L;
	private String payeeName;
	private String payeeMail;
	private String payeePhone;
	private Long payeeFee = 0L;
	private java.util.Date updateDate;
	private Long amount;
	private Integer payeeAcctType;
	private Long sequenceId;
	private java.util.Date createDate;
	private String requestFrom;
	private Integer payeeMemType = -1;
	private Integer payerMemType = -1;

	public String getPayerCode() {
		return payerCode;
	}

	public void setPayerCode(String payerCode) {
		this.payerCode = payerCode;
	}

	public String getPayerAcctCode() {
		return payerAcctCode;
	}

	public void setPayerAcctCode(String payerAcctCode) {
		this.payerAcctCode = payerAcctCode;
	}

	public String getErrorTips() {
		return errorTips;
	}

	public void setErrorTips(String errorTips) {
		this.errorTips = errorTips;
	}

	public Integer getPayerAcctType() {
		return payerAcctType;
	}

	public void setPayerAcctType(Integer payerAcctType) {
		this.payerAcctType = payerAcctType;
	}

    /*吕宏修改:统一用父类订单状态*/
	public Integer getStatus() {
		return getInnerStatus();
	}

	public void setStatus(Integer status) {
		setInnerStatus(status);
	}

	public Long getPayerMember() {
		return payerMember;
	}

	public void setPayerMember(Long payerMember) {
		this.payerMember = payerMember;
	}

	public String getCallSeq() {
		return callSeq;
	}

	public void setCallSeq(String callSeq) {
		this.callSeq = callSeq;
	}

	public String getPayeeAcctCode() {
		return payeeAcctCode;
	}

	public void setPayeeAcctCode(String payeeAcctCode) {
		this.payeeAcctCode = payeeAcctCode;
	}

	public Long getParentOrder() {
		return parentOrder;
	}

	public void setParentOrder(Long parentOrder) {
		this.parentOrder = parentOrder;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getPayeeCode() {
		return payeeCode;
	}

	public void setPayeeCode(String payeeCode) {
		this.payeeCode = payeeCode;
	}

	public String getBusiCode() {
		return busiCode;
	}

	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}

	public Long getPayeeMember() {
		return payeeMember;
	}

	public void setPayeeMember(Long payeeMember) {
		this.payeeMember = payeeMember;
	}

	public java.util.Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Integer getPayeeAcctType() {
		return payeeAcctType;
	}

	public void setPayeeAcctType(Integer payeeAcctType) {
		this.payeeAcctType = payeeAcctType;
	}

	public Long getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(Long sequenceId) {
		this.sequenceId = sequenceId;
	}

	public java.util.Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public String getRequestFrom() {
		return requestFrom;
	}

	public void setRequestFrom(String requestFrom) {
		this.requestFrom = requestFrom;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getPayeeMail() {
		return payeeMail;
	}

	public void setPayeeMail(String payeeMail) {
		this.payeeMail = payeeMail;
	}

	public String getPayeePhone() {
		return payeePhone;
	}

	public void setPayeePhone(String payeePhone) {
		this.payeePhone = payeePhone;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getPayerMail() {
		return payerMail;
	}

	public void setPayerMail(String payerMail) {
		this.payerMail = payerMail;
	}

	public String getPayerPhone() {
		return payerPhone;
	}

	public void setPayerPhone(String payerPhone) {
		this.payerPhone = payerPhone;
	}

	public Long getPayerFee() {
		return payerFee;
	}

	public void setPayerFee(Long payerFee) {
		this.payerFee = payerFee;
	}

	public Long getPayeeFee() {
		return payeeFee;
	}

	public void setPayeeFee(Long payeeFee) {
		this.payeeFee = payeeFee;
	}

	public Integer getPayeeMemType() {
		return payeeMemType;
	}

	public void setPayeeMemType(Integer payeeMemType) {
		this.payeeMemType = payeeMemType;
	}

	public Integer getPayerMemType() {
		return payerMemType;
	}

	public void setPayerMemType(Integer payerMemType) {
		this.payerMemType = payerMemType;
	}

	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("PAY2ACCT ORDER:[callSeq=").append(callSeq).append(", ");
		result.append("requestFrom=").append(requestFrom).append(", ");
		result.append("payerAcctCode=").append(payerAcctCode).append(", ");
		result.append("payeeAcctCode=").append(payeeAcctCode).append(", ");
		result.append("amount=").append(amount).append(", ");
		result.append("errotTip=").append(errorTips).append("]");
		return result.toString();
	}

}
