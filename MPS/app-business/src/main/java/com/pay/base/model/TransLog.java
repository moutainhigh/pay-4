/**
 *Copyright (c) 2006-2010 pay.com,Inc. All Rights Reserved.
 *@Project_Name app-business 
 *@CreateDate 下午05:45:36 2010-12-6
 */
package com.pay.base.model;

import java.util.Date;

import com.pay.app.model.Model;


/**
 * @author Sunny Ying
 * @description MA 交易日志
 * @version 下午05:45:36 & 2010-12-6
 */

public class TransLog implements Model{

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
	private Date updateDate;
	private Date createDate;
	private Long linkId;

	public Long getLinkId() {
		return linkId;
	}

	public void setLinkId(Long linkId) {
		this.linkId = linkId;
	}

	public Long getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Long serialNo) {
		this.serialNo = serialNo;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public Date getConfirmDate() {
		return confirmDate;
	}

	public void setConfirmDate(Date confirmDate) {
		this.confirmDate = confirmDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getRecvAcct() {
		return recvAcct;
	}

	public void setRecvAcct(String recvAcct) {
		this.recvAcct = recvAcct;
	}

	public String getPayAcct() {
		return payAcct;
	}

	public void setPayAcct(String payAcct) {
		this.payAcct = payAcct;
	}

	public Long getRelatxSerialNo() {
		return relatxSerialNo;
	}

	public void setRelatxSerialNo(Long relatxSerialNo) {
		this.relatxSerialNo = relatxSerialNo;
	}

	public Integer getAcctType() {
		return acctType;
	}

	public void setAcctType(Integer acctType) {
		this.acctType = acctType;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getId() {
		return id;
	}

	public void setPrimaryKey(Long id) {
		setId(id);
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
