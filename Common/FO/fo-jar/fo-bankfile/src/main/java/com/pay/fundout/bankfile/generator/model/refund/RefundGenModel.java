package com.pay.fundout.bankfile.generator.model.refund;

import com.pay.fundout.bankfile.generator.model.FileDetailMode;

/**		
 *  @author lIWEI
 *  @Date 2011-6-23
 *  @Description
 *  @Copyright Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 */
public class RefundGenModel extends FileDetailMode {
	private static final long serialVersionUID = -7422567139120852596L;

	private String serialNo; // 记录顺序号
	
	private String orderSeqFormat;
	private String amountFormat;
	private String remarkFormat;
	private String channelType;
	private String applyAmountString;

	public String getApplyAmountString() {
		return applyAmountString;
	}

	public void setApplyAmountString(String applyAmountString) {
		this.applyAmountString = applyAmountString;
	}

	public String getChannelType() {
		return channelType;
	}

	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}

	public String getRemarkFormat() {
		return remarkFormat;
	}

	public void setRemarkFormat(String remarkFormat) {
		this.remarkFormat = remarkFormat;
	}

	public String getOrderSeqFormat() {
		return orderSeqFormat;
	}

	public void setOrderSeqFormat(String orderSeqFormat) {
		this.orderSeqFormat = orderSeqFormat;
	}

	public String getAmountFormat() {
		return amountFormat;
	}

	public void setAmountFormat(String amountFormat) {
		this.amountFormat = amountFormat;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
}
