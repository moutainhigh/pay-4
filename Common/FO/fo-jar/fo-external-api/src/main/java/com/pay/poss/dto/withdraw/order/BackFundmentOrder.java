package com.pay.poss.dto.withdraw.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.pay.poss.dto.withdraw.orderhandlermanage.BaseOrderDTO;

public class BackFundmentOrder extends BaseOrderDTO implements Serializable {
	private static final long serialVersionUID = -8823254057583529177L;
	private Long sequenceId;
	private String orderCode;
	private Long sequenceSrc;
	private String codeSrc;
	private Date timeSrc;
	private BigDecimal amountSrc;
	private BigDecimal feeSrc;
	private String bankSrc;
	private String fromCode;
	private Long payerMember;
	private Integer payerAcctType;
	private String payerAcctCode;
	private String payerCode;
	private Long payeeMember;
	private Integer payeeAcctType;
	private String payeeAcctCode;
	private String payeeCode;
	private String appType;
	private BigDecimal appAmount;
	private BigDecimal appFee;
	private String reasons;
	private Date createTime;
	private Date updateTime;
	private Integer status;
	private String uniqueKy;
	
	public String getUniqueKy() {
		return uniqueKy;
	}

	public void setUniqueKy(String uniqueKy) {
		this.uniqueKy = uniqueKy;
	}

	

	public Long getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(Long sequenceId) {
		this.sequenceId = sequenceId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Long getSequenceSrc() {
		return sequenceSrc;
	}

	public void setSequenceSrc(Long sequenceSrc) {
		this.sequenceSrc = sequenceSrc;
	}

	public String getCodeSrc() {
		return codeSrc;
	}

	public void setCodeSrc(String codeSrc) {
		this.codeSrc = codeSrc;
	}

	public Date getTimeSrc() {
		return timeSrc;
	}

	public void setTimeSrc(Date timeSrc) {
		this.timeSrc = timeSrc;
	}

	public BigDecimal getAmountSrc() {
		return amountSrc;
	}

	public void setAmountSrc(BigDecimal amountSrc) {
		this.amountSrc = amountSrc;
	}

	public BigDecimal getFeeSrc() {
		return feeSrc;
	}

	public void setFeeSrc(BigDecimal feeSrc) {
		this.feeSrc = feeSrc;
	}

	public String getBankSrc() {
		return bankSrc;
	}

	public void setBankSrc(String bankSrc) {
		this.bankSrc = bankSrc;
	}

	public String getFromCode() {
		return fromCode;
	}

	public void setFromCode(String fromCode) {
		this.fromCode = fromCode;
	}

	public Long getPayerMember() {
		return payerMember;
	}

	public void setPayerMember(Long payerMember) {
		this.payerMember = payerMember;
	}

	public Integer getPayerAcctType() {
		return payerAcctType;
	}

	public void setPayerAcctType(Integer payerAcctType) {
		this.payerAcctType = payerAcctType;
	}

	public String getPayerAcctCode() {
		return payerAcctCode;
	}

	public void setPayerAcctCode(String payerAcctCode) {
		this.payerAcctCode = payerAcctCode;
	}

	public String getPayerCode() {
		return payerCode;
	}

	public void setPayerCode(String payerCode) {
		this.payerCode = payerCode;
	}

	public Long getPayeeMember() {
		return payeeMember;
	}

	public void setPayeeMember(Long payeeMember) {
		this.payeeMember = payeeMember;
	}

	public Integer getPayeeAcctType() {
		return payeeAcctType;
	}

	public void setPayeeAcctType(Integer payeeAcctType) {
		this.payeeAcctType = payeeAcctType;
	}

	public String getPayeeAcctCode() {
		return payeeAcctCode;
	}

	public void setPayeeAcctCode(String payeeAcctCode) {
		this.payeeAcctCode = payeeAcctCode;
	}

	public String getPayeeCode() {
		return payeeCode;
	}

	public void setPayeeCode(String payeeCode) {
		this.payeeCode = payeeCode;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public BigDecimal getAppAmount() {
		return appAmount;
	}

	public void setAppAmount(BigDecimal appAmount) {
		this.appAmount = appAmount;
	}

	public String getReasons() {
		return reasons;
	}

	public void setReasons(String reasons) {
		this.reasons = reasons;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	

    public BigDecimal getAppFee() {
		return appFee;
	}

	public void setAppFee(BigDecimal appFee) {
		this.appFee = appFee;
	}

	/*吕宏修改:统一用父类订单状态*/
	public Integer getStatus() {
		return getInnerStatus();
	}

	public void setStatus(Integer status) {
		setInnerStatus(status);
	}

	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append("BACKFUNDING ORDER:[sequenceSrc=").append(sequenceSrc).append(", ");
		result.append("appType=").append(appType).append(", ");
		result.append("payerAcctCode=").append(payerAcctCode).append(", ");
		result.append("payeeAcctCode=").append(payeeAcctCode).append(", ");
		result.append("appAmount=").append(appAmount).append(",");
		result.append("appFee=").append(appFee).append("]");
		return result.toString();
	}

}