package com.pay.pricingstrategy.service;

import java.util.Date;

import com.pay.pricingstrategy.dto.PricingStrategyDTO;

/**
 * @author
 *
 */
public class CalPriceInnerParam {
	private Long transactionAmount;// 交易金额
	private PricingStrategyDTO pricingStrategyDto;
	private int terminltype;// 终端类型
	private Date mfDateTime;
	private Integer paymentServiceCode;// 支付服务

	private Long memberCode;// 会员号

	public Integer getPaymentServiceCode() {
		return paymentServiceCode;
	}

	public void setPaymentServiceCode(Integer paymentServiceCode) {
		this.paymentServiceCode = paymentServiceCode;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	private String reservedCode;// 扩展代码

	public PricingStrategyDTO getPricingStrategyDto() {
		return pricingStrategyDto;
	}

	public void setPricingStrategyDto(PricingStrategyDTO pricingStrategyDto) {
		this.pricingStrategyDto = pricingStrategyDto;
	}

	public int getTerminltype() {
		return terminltype;
	}

	public void setTerminltype(int terminltype) {
		this.terminltype = terminltype;
	}

	public Long getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(Long transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	// public MfTime getMftime() {
	// return mfDateTime.getMfTime();
	// }
	//
	// public MfDateTime getMfDateTime() {
	// return mfDateTime;
	// }
	// public void setMfDateTime(MfDateTime mfDateTime) {
	// this.mfDateTime = mfDateTime;
	// }
	public String getReservedCode() {
		return reservedCode;
	}

	public void setReservedCode(String reservedCode) {
		this.reservedCode = reservedCode;
	}

	public Date getMfDateTime() {
		return mfDateTime;
	}

	public void setMfDateTime(Date mfDateTime) {
		this.mfDateTime = mfDateTime;
	}

}
