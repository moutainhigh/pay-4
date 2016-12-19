/**
 * 
 */
package com.pay.acc.translog.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 *
 */
public class TransLog implements Serializable {
	private Long id;
	private Long serialNo;
	private Integer payType;
	private Date payDate;
	private Date confirmDate;
	private Integer status;
	private Long amount;
	private String recvAcct;
	private String payAcct;
	private Long relatxSerialNo;
	private Integer acctType;
	private Date createDate;
	private Date updateDate;

	private Long linkId;

	public Long getLinkId() {
		return linkId;
	}

	public void setLinkId(Long linkId) {
		this.linkId = linkId;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the serialNo
	 */
	public Long getSerialNo() {
		return serialNo;
	}

	/**
	 * @param serialNo
	 *            the serialNo to set
	 */
	public void setSerialNo(Long serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * @return the payType
	 */
	public Integer getPayType() {
		return payType;
	}

	/**
	 * @param payType
	 *            the payType to set
	 */
	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	/**
	 * @return the payDate
	 */
	public Date getPayDate() {
		return payDate;
	}

	/**
	 * @param payDate
	 *            the payDate to set
	 */
	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	/**
	 * @return the confirmDate
	 */
	public Date getConfirmDate() {
		return confirmDate;
	}

	/**
	 * @param confirmDate
	 *            the confirmDate to set
	 */
	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
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
	 * @return the amount
	 */
	public Long getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(Long amount) {
		this.amount = amount;
	}

	/**
	 * @return the recvAcct
	 */
	public String getRecvAcct() {
		return recvAcct;
	}

	/**
	 * @param recvAcct
	 *            the recvAcct to set
	 */
	public void setRecvAcct(String recvAcct) {
		this.recvAcct = recvAcct;
	}

	/**
	 * @return the payAcct
	 */
	public String getPayAcct() {
		return payAcct;
	}

	/**
	 * @param payAcct
	 *            the payAcct to set
	 */
	public void setPayAcct(String payAcct) {
		this.payAcct = payAcct;
	}

	/**
	 * @return the relatxSerialNo
	 */
	public Long getRelatxSerialNo() {
		return relatxSerialNo;
	}

	/**
	 * @param relatxSerialNo
	 *            the relatxSerialNo to set
	 */
	public void setRelatxSerialNo(Long relatxSerialNo) {
		this.relatxSerialNo = relatxSerialNo;
	}

	/**
	 * @return the acctType
	 */
	public Integer getAcctType() {
		return acctType;
	}

	/**
	 * @param acctType
	 *            the acctType to set
	 */
	public void setAcctType(Integer acctType) {
		this.acctType = acctType;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TransLog [acctType=" + acctType + ", amount=" + amount
				+ ", confirmDate=" + confirmDate + ", createDate=" + createDate
				+ ", id=" + id + ", payAcct=" + payAcct + ", payDate="
				+ payDate + ", payType=" + payType + ", recvAcct=" + recvAcct
				+ ", relatxSerialNo=" + relatxSerialNo + ", serialNo="
				+ serialNo + ", status=" + status + ", updateDate="
				+ updateDate + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((acctType == null) ? 0 : acctType.hashCode());
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result
				+ ((confirmDate == null) ? 0 : confirmDate.hashCode());
		result = prime * result
				+ ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((payAcct == null) ? 0 : payAcct.hashCode());
		result = prime * result + ((payDate == null) ? 0 : payDate.hashCode());
		result = prime * result + ((payType == null) ? 0 : payType.hashCode());
		result = prime * result
				+ ((recvAcct == null) ? 0 : recvAcct.hashCode());
		result = prime * result
				+ ((relatxSerialNo == null) ? 0 : relatxSerialNo.hashCode());
		result = prime * result
				+ ((serialNo == null) ? 0 : serialNo.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((updateDate == null) ? 0 : updateDate.hashCode());
		return result;
	}

}
