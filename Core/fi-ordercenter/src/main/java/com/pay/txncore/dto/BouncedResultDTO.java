package com.pay.txncore.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 拒付导入结果表
 * File: BouncedResultDTO.java Description: 
 * Copyright 2016-2030 IPAYLINKS Corporation. 
 * All rights reserved. 
 * Date Author Changes 2016年5月10日
 * mmzhang Create
 *
 */
public class BouncedResultDTO {

	private Long  id;
	private String batchNo; // 批次号
	private String orderId; // 商户订单号
	private String channelOrderNo; // 渠道订单号
	private String tradeOrderNo; // 网关订单号
	private String refNo; // 档案号
	private String partnerId; // 会员号
	private String bussinessNo; // 商户编号
	private String bussinessName; // 商户名称
	private String cardholderCardno; // 银行卡号
	private BigDecimal bankAmount; // 银行拒付金额
	private BigDecimal orderAmount; // 订单金额
	private String currencyCode; // 交易币种
	private String bankCurrencyCode; // 银行拒付币种
	private String authorisation; // 授权码
	private Date tranDate; // 交易日期
	private String bouncedType; // 拒付类型
	private String bouncedReason; // 拒付理由
	private String visableCode; // 显示理由码
	private String reasonCode; // 拒付理由码
	private String orgCode; // 渠道
	private String orgName; // 渠道
	private BigDecimal overRefundAmount; // 已退金额
	private BigDecimal doingRefundAmount; // 退款中金额
	private BigDecimal overBouncedAmount; // 已拒付金额
	private BigDecimal canBouncedAmount; // 可拒付金额
	private BigDecimal doingBouncedAmount; // 拒付中金额
	private BigDecimal refundAmount; 
	private String status; // 匹配状态
	private Date createDate; // 录入日期
	private Date lastDate; // 最晚回复日期
	private String settlementFlg; // 订单清算状态
	private String assureSettlementFlg; // 保证金清算状态
	private BigDecimal payAmount; // 支付金额
	private BigDecimal transferRate; // 支付汇率
	private String transferCurrencyCode; // 支付币种
	private String cardOrg; 
	private String registerFlag; 
	
	private String cpdDate; //CPD日期
	private String oldRefNo; //渠道原档号
	private String operator; //操作员
	private String remark; // 备注
	private String settlementCurrencyCode;
	private String merchantCode; //gc标志

	private BigDecimal settlementRate;
	private BigDecimal floatValue;
	private String checkFlag;
	private String bouncedRemark;
	private String[] ids;

	private String sbankAmount; // 银行拒付金额
	
	private String stranDate; // 交易日期
	
	private String soverBouncedAmount; // 已拒付金额
	private String sdoingBouncedAmount; // 拒付中金额
	private String spayAmount; // 支付金额
	private String stransferRate; // 支付交易汇率
	private String rate; // 拒付币种到支付币种的交易汇率
	
	private String sorderAmount; // 订单金额
	private String scanBouncedAmount; // 可拒付金额
	
	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String getBouncedRemark() {
		return bouncedRemark;
	}

	public void setBouncedRemark(String bouncedRemark) {
		this.bouncedRemark = bouncedRemark;
	}

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}


	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getChannelOrderNo() {
		return channelOrderNo;
	}

	public void setChannelOrderNo(String channelOrderNo) {
		this.channelOrderNo = channelOrderNo;
	}

	public String getTradeOrderNo() {
		return tradeOrderNo;
	}

	public void setTradeOrderNo(String tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getBussinessNo() {
		return bussinessNo;
	}

	public void setBussinessNo(String bussinessNo) {
		this.bussinessNo = bussinessNo;
	}

	public String getBussinessName() {
		return bussinessName;
	}

	public void setBussinessName(String bussinessName) {
		this.bussinessName = bussinessName;
	}

	public String getCardholderCardno() {
		return cardholderCardno;
	}

	public void setCardholderCardno(String cardholderCardno) {
		this.cardholderCardno = cardholderCardno;
	}

	public BigDecimal getBankAmount() {
		return bankAmount;
	}

	public void setBankAmount(BigDecimal bankAmount) {
		this.bankAmount = bankAmount;
	}

	public String getBankCurrencyCode() {
		return bankCurrencyCode;
	}

	public void setBankCurrencyCode(String bankCurrencyCode) {
		this.bankCurrencyCode = bankCurrencyCode;
	}

	public String getAuthorisation() {
		return authorisation;
	}

	public void setAuthorisation(String authorisation) {
		this.authorisation = authorisation;
	}


	public String getBouncedType() {
		return bouncedType;
	}

	public void setBouncedType(String bouncedType) {
		this.bouncedType = bouncedType;
	}

	public String getBouncedReason() {
		return bouncedReason;
	}

	public void setBouncedReason(String bouncedReason) {
		this.bouncedReason = bouncedReason;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public BigDecimal getOverRefundAmount() {
		return overRefundAmount;
	}

	public void setOverRefundAmount(BigDecimal overRefundAmount) {
		this.overRefundAmount = overRefundAmount;
	}

	public BigDecimal getDoingRefundAmount() {
		return doingRefundAmount;
	}

	public void setDoingRefundAmount(BigDecimal doingRefundAmount) {
		this.doingRefundAmount = doingRefundAmount;
	}

	public BigDecimal getOverBouncedAmount() {
		return overBouncedAmount;
	}

	public void setOverBouncedAmount(BigDecimal overBouncedAmount) {
		this.overBouncedAmount = overBouncedAmount;
	}

	public BigDecimal getCanBouncedAmount() {
		return canBouncedAmount;
	}

	public void setCanBouncedAmount(BigDecimal canBouncedAmount) {
		this.canBouncedAmount = canBouncedAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public Date getLastDate() {
		return lastDate;
	}

	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getSettlementFlg() {
		return settlementFlg;
	}

	public void setSettlementFlg(String settlementFlg) {
		this.settlementFlg = settlementFlg;
	}

	public String getAssureSettlementFlg() {
		return assureSettlementFlg;
	}

	public void setAssureSettlementFlg(String assureSettlementFlg) {
		this.assureSettlementFlg = assureSettlementFlg;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}


	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}



	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getVisableCode() {
		return visableCode;
	}

	public void setVisableCode(String visableCode) {
		this.visableCode = visableCode;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public BigDecimal getTransferRate() {
		return transferRate;
	}

	public void setTransferRate(BigDecimal transferRate) {
		this.transferRate = transferRate;
	}

	public String getTransferCurrencyCode() {
		return transferCurrencyCode;
	}

	public void setTransferCurrencyCode(String transferCurrencyCode) {
		this.transferCurrencyCode = transferCurrencyCode;
	}

	public String getCardOrg() {
		return cardOrg;
	}

	public void setCardOrg(String cardOrg) {
		this.cardOrg = cardOrg;
	}

	public Date getTranDate() {
		return tranDate;
	}

	public void setTranDate(Date tranDate) {
		this.tranDate = tranDate;
	}


	public String getRegisterFlag() {
		return registerFlag;
	}

	public void setRegisterFlag(String registerFlag) {
		this.registerFlag = registerFlag;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getCpdDate() {
		return cpdDate;
	}

	public void setCpdDate(String cpdDate) {
		this.cpdDate = cpdDate;
	}

	public String getOldRefNo() {
		return oldRefNo;
	}

	public void setOldRefNo(String oldRefNo) {
		this.oldRefNo = oldRefNo;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getSettlementCurrencyCode() {
		return settlementCurrencyCode;
	}

	public void setSettlementCurrencyCode(String settlementCurrencyCode) {
		this.settlementCurrencyCode = settlementCurrencyCode;
	}

	public BigDecimal getSettlementRate() {
		return settlementRate;
	}

	public void setSettlementRate(BigDecimal settlementRate) {
		this.settlementRate = settlementRate;
	}

	public BigDecimal getDoingBouncedAmount() {
		return doingBouncedAmount;
	}

	public void setDoingBouncedAmount(BigDecimal doingBouncedAmount) {
		this.doingBouncedAmount = doingBouncedAmount;
	}

	public BigDecimal getFloatValue() {
		return floatValue;
	}

	public void setFloatValue(BigDecimal floatValue) {
		this.floatValue = floatValue;
	}

	public String getSbankAmount() {
		return sbankAmount;
	}

	public void setSbankAmount(String sbankAmount) {
		this.sbankAmount = sbankAmount;
	}

	public String getStranDate() {
		return stranDate;
	}

	public void setStranDate(String stranDate) {
		this.stranDate = stranDate;
	}

	public String getSoverBouncedAmount() {
		return soverBouncedAmount;
	}

	public void setSoverBouncedAmount(String soverBouncedAmount) {
		this.soverBouncedAmount = soverBouncedAmount;
	}

	public String getSdoingBouncedAmount() {
		return sdoingBouncedAmount;
	}

	public void setSdoingBouncedAmount(String sdoingBouncedAmount) {
		this.sdoingBouncedAmount = sdoingBouncedAmount;
	}

	public String getSpayAmount() {
		return spayAmount;
	}

	public void setSpayAmount(String spayAmount) {
		this.spayAmount = spayAmount;
	}

	public String getStransferRate() {
		return stransferRate;
	}

	public void setStransferRate(String stransferRate) {
		this.stransferRate = stransferRate;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getSorderAmount() {
		return sorderAmount;
	}

	public void setSorderAmount(String sorderAmount) {
		this.sorderAmount = sorderAmount;
	}

	public String getScanBouncedAmount() {
		return scanBouncedAmount;
	}

	public void setScanBouncedAmount(String scanBouncedAmount) {
		this.scanBouncedAmount = scanBouncedAmount;
	}

	


}