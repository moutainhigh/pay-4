package com.pay.pe.service;

import java.io.Serializable;
import java.util.List;



public class CalFeeReponse implements Serializable{

	private static final long serialVersionUID = 1L;
	/*是否已经计收款方费*/
	private boolean hasCaculatedPayeePrice;
	/*是否已经计付款方费*/
	private boolean hasCaculatedPayerPrice;

	/*收款人费用*/
	private Long payeeFee = 0L;
	/*付款人费用*/
	private Long payerFee = 0L;
	/*计费请求对象*/
	private CalFeeRequest calFeeRequest;
	/*计费结果对象*/
	private List<CalFeeDetail> calFeeDetails;
	/*价格策略CODE*/
	private Long priceStrategyCode=0L; 
	
	/**
	 * 付款方价格策略CODE
	 */
	private Long PayerPriceStrategyCode=0L;
	/**
	 * 收款方价格策略CODE
	 */
	private Long PayeePriceStrategyCode=0L;

	/**
	 * 凭证号
	 */
	private Long voucherCode;

	/*是否已算费*/
	private boolean hasCaculatedPrice;


	public boolean isHasCaculatedPrice() {
		return hasCaculatedPrice;
	}

	public void setHasCaculatedPrice(boolean hasCaculatedPrice) {
		this.hasCaculatedPrice = hasCaculatedPrice;
	}

	public Long getPriceStrategyCode() {
    	return priceStrategyCode;
    }

	public void setPriceStrategyCode(Long priceStrategyCode) {
    	this.priceStrategyCode = priceStrategyCode;
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

	public boolean isHasCaculatedPayeePrice() {
		return hasCaculatedPayeePrice;
	}

	public void setHasCaculatedPayeePrice(boolean hasCaculatedPayeePrice) {
		this.hasCaculatedPayeePrice = hasCaculatedPayeePrice;
	}

	public boolean isHasCaculatedPayerPrice() {
		return hasCaculatedPayerPrice;
	}

	public void setHasCaculatedPayerPrice(boolean hasCaculatedPayerPrice) {
		this.hasCaculatedPayerPrice = hasCaculatedPayerPrice;
	}

	public CalFeeRequest getCalFeeRequest() {
		return calFeeRequest;
	}

	public void setCalFeeRequest(CalFeeRequest calFeeRequest) {
		this.calFeeRequest = calFeeRequest;
	}

	public List<CalFeeDetail> getCalFeeDetails() {
		return calFeeDetails;
	}

	public void setCalFeeDetails(List<CalFeeDetail> calFeeDetails) {
		this.calFeeDetails = calFeeDetails;
	}
	
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("hasCaculatedPayeePrice="+hasCaculatedPayeePrice+"\n");
		buffer.append("hasCaculatedPayerPrice="+hasCaculatedPayerPrice+"\n");
		buffer.append("payeeFee="+payeeFee+"\n");
		buffer.append("payerFee="+payerFee+"\n");
		
		if(calFeeDetails!=null ){
			buffer.append("calFeeDetails.size="+calFeeDetails.size()+"\n");
			for(CalFeeDetail calFeeDetail :calFeeDetails){
				buffer.append("calFeeDetail="+calFeeDetail+"\n");
			}
		}		
		return buffer.toString();
	}

	public Long getPayerPriceStrategyCode() {
    	return PayerPriceStrategyCode;
    }

	public void setPayerPriceStrategyCode(Long payerPriceStrategyCode) {
    	PayerPriceStrategyCode = payerPriceStrategyCode;
    }

	public Long getPayeePriceStrategyCode() {
    	return PayeePriceStrategyCode;
    }

	public void setPayeePriceStrategyCode(Long payeePriceStrategyCode) {
    	PayeePriceStrategyCode = payeePriceStrategyCode;
    }
	
	public Long getVoucherCode() {
    	return voucherCode;
    }

	public void setVoucherCode(Long voucherCode) {
    	this.voucherCode = voucherCode;
    }
}
