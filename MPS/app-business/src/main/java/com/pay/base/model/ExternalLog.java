/**
 *Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
 */
package com.pay.base.model;

import java.math.BigDecimal;
import java.util.Date;

import com.pay.app.model.Model;
import com.pay.base.common.enums.ExternalProcessStatus;
import com.pay.base.common.enums.ProcessStatus;

/**
 * @author fjl
 * @date 2011-9-19
 */
public class ExternalLog implements Model {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String orderNo;
	private Integer externalType;
	private String cardNo;
	private Integer processStatus;
	private BigDecimal amount;
	private Date createDate;
	private Date updateDate;
	private String remark;
	private Integer currencyCode;
	private Integer tradingWay;
	private String payerMemberCode;
	private String value1;
	private String value2;
	private Integer externalProcessStatus;
	
	private String payerName;
	private String payerContact;
	private String gatewayNo;


	// Constructors

	/** default constructor */
	public ExternalLog() {
	}

	/** minimal constructor */
	public ExternalLog(Long id, String orderNo, Integer externalType,
			Integer processStatus, BigDecimal amount, Date createDate) {
		this.id = id;
		this.orderNo = orderNo;
		this.externalType = externalType;
		this.processStatus = processStatus;
		this.amount = amount;
		this.createDate = createDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getExternalType() {
		return externalType;
	}

	public void setExternalType(Integer externalType) {
		this.externalType = externalType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Integer getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(Integer processStatus) {
		this.processStatus = processStatus;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(Integer currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Integer getTradingWay() {
		return tradingWay;
	}

	public void setTradingWay(Integer tradingWay) {
		this.tradingWay = tradingWay;
	}

	public String getPayerMemberCode() {
		return payerMemberCode;
	}

	public void setPayerMemberCode(String payerMemberCode) {
		this.payerMemberCode = payerMemberCode;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public Integer getExternalProcessStatus() {
		return externalProcessStatus;
	}

	public void setExternalProcessStatus(Integer externalProcessStatus) {
		this.externalProcessStatus = externalProcessStatus;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getPayerContact() {
		return payerContact;
	}

	public void setPayerContact(String payerContact) {
		this.payerContact = payerContact;
	}

	public String getGatewayNo() {
		return gatewayNo;
	}

	public void setGatewayNo(String gatewayNo) {
		this.gatewayNo = gatewayNo;
	}

	/*
	public String getStatusMsg() {
		if (externalProcessStatus != null) {
			ExternalProcessStatus status = ExternalProcessStatus
					.get(externalProcessStatus);
			if (status == ExternalProcessStatus.EXTERNAL_PROCESS_SUCCED) {
				return "充值成功";
			}
			if (status == ExternalProcessStatus.EXTERNAL_PROCESS_FAILED) {
				ProcessStatus getwayStatus = ProcessStatus.get(processStatus);
				if (ProcessStatus.PROCESS_REFUNDED == getwayStatus) {
					return "已退款";
				}
				if (ProcessStatus.PROCESS_REFUNDING == getwayStatus) {
					return "退款中";
				}
			}
			if (status == ExternalProcessStatus.EXTERNAL_PROCESS_TIMEOUT) {
				return "充值中";
			}
		}

		if (processStatus != null) {
			ProcessStatus getwayStatus = ProcessStatus.get(processStatus);
			return getwayStatus.getMessage();
		}
		return "";
	}
	*/
	@Override
	public void setPrimaryKey(Long id) {
		setId(id);
	}

}
