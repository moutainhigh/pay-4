package com.pay.pe.service;

import java.io.Serializable;
import java.util.List;

public class PaymentResponseDto implements Serializable{

	private static final long serialVersionUID = 1L;
	/*是否已经计费*/
//	private boolean hasCaculatedPrice;
	/*收款人费用*/
	private Long payeeFee;
	/*付款人费用*/
	private Long payerFee;
	/*计费请求对象*/
	private PaymentReqDto paymentReq;
	/*计费结果对象*/
	private List<PaymentDetailDto> paymentDetails;
	/*付款方价格策略CODE*/
	private Long payerPriceStrategyCode=0L;
	/*收款方价格策略CODE*/
	private Long payeePriceStrategyCode=0L;
	
	/**
	 * 凭证号
	 */
	private Long voucherCode;



//	public Long getPriceStrategyCode() {
//    	return priceStrategyCode;
//    }

//	public void setPriceStrategyCode(Long priceStrategyCode) {
//    	this.priceStrategyCode = priceStrategyCode;
//    }

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

//	public boolean isHasCaculatedPrice() {
//		return hasCaculatedPrice;
//	}

//	public void setHasCaculatedPrice(boolean hasCaculatedPrice) {
//		this.hasCaculatedPrice = hasCaculatedPrice;
//	}
	
	
	public List<PaymentDetailDto> getPaymentDetails() {
		return paymentDetails;
	}

	public PaymentReqDto getPaymentReq() {
		return paymentReq;
	}

	public void setPaymentReq(PaymentReqDto paymentReq) {
		this.paymentReq = paymentReq;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setPaymentDetails(List<PaymentDetailDto> paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

	public String toString(){
		StringBuffer buffer = new StringBuffer();
//		buffer.append("hasCaculatedPrice="+hasCaculatedPrice+"\n");
		buffer.append("payeeFee="+payeeFee+"\n");
		buffer.append("payerFee="+payerFee+"\n");
		buffer.append("payerPriceStrategyCode="+payerPriceStrategyCode+"\n");
		buffer.append("payeePriceStrategyCode="+payeePriceStrategyCode+"\n");
		
		if(paymentDetails!=null ){
			buffer.append("calFeeDetails.size="+paymentDetails.size()+"\n");
			for(PaymentDetailDto calFeeDetail :paymentDetails){
				buffer.append("calFeeDetail="+calFeeDetail+"\n");
			}
		}		
		return buffer.toString();
	}


	public Long getVoucherCode() {
    	return voucherCode;
    }

	public void setVoucherCode(Long voucherCode) {
    	this.voucherCode = voucherCode;
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
	
}

