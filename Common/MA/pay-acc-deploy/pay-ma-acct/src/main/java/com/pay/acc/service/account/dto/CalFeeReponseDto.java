/**
 * 
 */
package com.pay.acc.service.account.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author Administrator
 * 
 */
public class CalFeeReponseDto implements Serializable {

	/* 是否已经计费 */
	private boolean hasCaculatedPrice;
	/* 收款人费用 */
	private Long payeeFee;
	/* 付款人费用 */
	private Long payerFee;
	/* 计费请求对象 */
	private CalFeeRequestDto calFeeRequestDto;
	/* 计费结果对象 */
	private List<CalFeeDetailDto> calFeeDetailDtos;

	/* 付款方价格策略CODE */
	private Long payerPriceStrategyCode = 0L;
	/* 收款方价格策略CODE */
	private Long payeePriceStrategyCode = 0L;
	
	private String merchantOrderId;

	/**
	 * 凭证号
	 */
	private Long voucherCode;
	
	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMerchantOrderId() {
		return merchantOrderId;
	}

	public void setMerchantOrderId(String merchantOrderId) {
		this.merchantOrderId = merchantOrderId;
	}

	public boolean isHasCaculatedPrice() {
		return hasCaculatedPrice;
	}

	public void setHasCaculatedPrice(boolean hasCaculatedPrice) {
		this.hasCaculatedPrice = hasCaculatedPrice;
	}

	public Long getPayeeFee() {
		return payeeFee;
	}

	public void setPayeeFee(Long payeeFee) {
		this.payeeFee = payeeFee;
	}

	public Long getPayerFee() {
		return payerFee;
	}

	public void setPayerFee(Long payerFee) {
		this.payerFee = payerFee;
	}

	public CalFeeRequestDto getCalFeeRequestDto() {
		return calFeeRequestDto;
	}

	public void setCalFeeRequestDto(CalFeeRequestDto calFeeRequestDto) {
		this.calFeeRequestDto = calFeeRequestDto;
	}

	public List<CalFeeDetailDto> getCalFeeDetailDtos() {
		return calFeeDetailDtos;
	}

	public void setCalFeeDetailDtos(List<CalFeeDetailDto> calFeeDetailDtos) {
		this.calFeeDetailDtos = calFeeDetailDtos;
	}

	public Long getPayerPriceStrategyCode() {
		return payerPriceStrategyCode;
	}

	public void setPayerPriceStrategyCode(Long payerPriceStrategyCode) {
		this.payerPriceStrategyCode = payerPriceStrategyCode;
	}

	public Long getPayeePriceStrategyCode() {
		return payeePriceStrategyCode;
	}

	public void setPayeePriceStrategyCode(Long payeePriceStrategyCode) {
		this.payeePriceStrategyCode = payeePriceStrategyCode;
	}

	public Long getVoucherCode() {
		return voucherCode;
	}

	public void setVoucherCode(Long voucherCode) {
		this.voucherCode = voucherCode;
	}

	@Override
	public String toString() {
		return "CalFeeReponseDto [calFeeDetailDtos=" + calFeeDetailDtos
				+ ", calFeeRequestDto=" + calFeeRequestDto
				+ ", hasCaculatedPrice=" + hasCaculatedPrice + ", payeeFee="
				+ payeeFee + ", payerFee=" + payerFee + "]";
	}

}
