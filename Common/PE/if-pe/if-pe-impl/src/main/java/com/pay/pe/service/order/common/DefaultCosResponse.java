package com.pay.pe.service.order.common;

import java.util.List;

import com.pay.pe.dto.AccountEntryDTO;
import com.pay.pe.exception.ErrorCodeType;

/**
 *
 */
public class DefaultCosResponse implements COSResponse {
	/**
	 * 处理的订单号.
	 */
	private String orderNo;

	/**
	 * 处理的交易号.
	 */
	private String dealId;

	/**
	 * 处理的类型.
	 */
	private int cosActionType;

	/**
	 * 处理的结果.
	 */
	private int cosActionStatus;

	/**
	 * 
	 */
	private Long orderSeqId;
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8752536404813843933L;

	private ErrorCodeType errorCodeType;

	public ErrorCodeType getErrorCodeType() {
		return errorCodeType;
	}

	public void setErrorCodeType(ErrorCodeType errorCodeType) {
		this.errorCodeType = errorCodeType;
	}

	/**
	 * @param dealId
	 *            The dealId to set.
	 */
	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	/**
	 * @param orderNo
	 *            The orderNo to set.
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/*
	 * (non-Javadoc)
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/*
	 * (non-Javadoc)
	 */
	public String getDealId() {
		return dealId;
	}

	/*
	 * (non-Javadoc)
	 */
	public int getCosActionType() {
		return cosActionType;
	}

	/*
	 * (non-Javadoc)
	 */
	public void setCosActionType(final int actionType) {
		this.cosActionType = actionType;
	}

	/*
	 * (non-Javadoc)
	 */
	public int getCosActionStatus() {
		return cosActionStatus;
	}

	/*
	 * (non-Javadoc)
	 */
	public void setCosActionStatus(final int actionStatus) {
		this.cosActionStatus = actionStatus;
	}

	public Long getOrderSeqId() {
		return orderSeqId;
	}

	public void setOrderSeqId(Long orderSeqId) {
		this.orderSeqId = orderSeqId;
	}

	/**
	 * Constructs a <code>String</code> with all attributes in name = value
	 * format.
	 * 
	 * @return a <code>String</code> representation of this object.
	 */
	public String toString() {
		final String TAB = "    ";

		StringBuffer retValue = new StringBuffer();

		retValue.append("DefaultCosResponse ( ").append(super.toString())
				.append(TAB).append("orderNo = ").append(this.orderNo).append(
						TAB).append("dealId = ").append(this.dealId)
				.append(TAB).append("cosActionType = ").append(
						this.cosActionType).append(TAB).append(
						"cosActionStatus = ").append(this.cosActionStatus)
				.append(TAB).append("orderSeqId = ").append(this.orderSeqId)
				.append(TAB).append("errorCodeType = ").append(
						this.errorCodeType).append(TAB).append(" )");

		return retValue.toString();
	}

	private List<AccountEntryDTO> entries;

	@Override
	public List<AccountEntryDTO> getDealEntries() {
		return this.entries;
	}

	@Override
	public void setDealEntries(List<AccountEntryDTO> entries) {
		this.entries = entries;
	}

	private long voucherCode;

	@Override
	public long getVoucherCode() {
		return voucherCode;
	}

	@Override
	public void setVoucherCode(long voucherCode) {
		this.voucherCode = voucherCode;
	}

}
