package com.pay.pe.service.payment.common;

import java.io.Serializable;
import java.util.List;

import com.pay.pe.dto.AccountEntryDTO;

/**
 * 
 *
 */
public final class PaymentResponse implements Serializable{
	private List<AccountEntryDTO> entries ;

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -4647615508810486410L;

	/**
	 * 记帐结果.
	 */
	private int paymentResult;
	
	/**
	 * 记帐出错的错误信息.
	 */
	private String errorMsg;
	
	/**
	 * 收款方账号.
	 */
	private String payeeAcctCode;
	
	/**
	 * 收款方费用.
	 */
	private Long payeeFee = 0L;
	
	/**
	 * 付款方账号.
	 */
	private String payerAcctCode;
	
	/**
	 * 付款方
	 */
	private Long payerFee =0L;
	
	/**
	 * 分录凭证号.
	 */
	private long voucherCode;

	/*是否已经计收款方费*/
	private boolean hasCaculatedPayeePrice= Boolean.FALSE;
	/*是否已经计付款方费*/
	private boolean hasCaculatedPayerPrice= Boolean.FALSE;



	/*价格策略CODE*/
	private Long priceStrategyCode;
	
	
	/**
	 * 收款方价格策略CODE
	 */
	private Long payeeFeePriceStrategyCode;

	/**
	 * 付款方价格策略CODE
	 */
	private Long payerFeePriceStrategyCode;

	public Long getPayeeFeePriceStrategyCode() {
    	return payeeFeePriceStrategyCode;
    }

	public void setPayeeFeePriceStrategyCode(Long payeeFeePriceStrategyCode) {
    	this.payeeFeePriceStrategyCode = payeeFeePriceStrategyCode;
    }

	public Long getPayerFeePriceStrategyCode() {
    	return payerFeePriceStrategyCode;
    }

	public void setPayerFeePriceStrategyCode(Long payerFeePriceStrategyCode) {
    	this.payerFeePriceStrategyCode = payerFeePriceStrategyCode;
    }

	public Long getPriceStrategyCode() {
    	return priceStrategyCode;
    }

	public void setPriceStrategyCode(Long priceStrategyCode) {
    	this.priceStrategyCode = priceStrategyCode;
    }

	/**
	 * @return Returns the paymentResult.
	 */
	public int getPaymentResult() {
		return paymentResult;
	}

	/**
	 * @param paymentResult The paymentResult to set.
	 */
	public void setPaymentResult(final int paymentResult) {
		this.paymentResult = paymentResult;
	}

	/**
	 * @return Returns the errorMsg.
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg The errorMsg to set.
	 */
	public void setErrorMsg(final String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * @return Returns the payeeAcctCode.
	 */
	public String getPayeeAcctCode() {
		return payeeAcctCode;
	}

	/**
	 * @param payeeAcctCode The payeeAcctCode to set.
	 */
	public void setPayeeAcctCode(String payeeAcctCode) {
		this.payeeAcctCode = payeeAcctCode;
	}

	/**
	 * @return Returns the payeeFee.
	 */
	public Long getPayeeFee() {
		return payeeFee;
	}

	/**
	 * @param payeeFee The payeeFee to set.
	 */
	public void setPayeeFee(Long payeeFee) {
		this.payeeFee = payeeFee;
	}

	/**
	 * @return Returns the payerAcctCode.
	 */
	public String getPayerAcctCode() {
		return payerAcctCode;
	}

	/**
	 * @param payerAcctCode The payerAcctCode to set.
	 */
	public void setPayerAcctCode(String payerAcctCode) {
		this.payerAcctCode = payerAcctCode;
	}

	/**
	 * @return Returns the payerFee.
	 */
	public Long getPayerFee() {
		return payerFee;
	}

	/**
	 * @param payerFee The payerFee to set.
	 */
	public void setPayerFee(Long payerFee) {
		this.payerFee = payerFee;
	}

	/**
	 * @return Returns the voucherCode.
	 */
	public long getVoucherCode() {
		return voucherCode;
	}

	/**
	 * @param voucherCode The voucherCode to set.
	 */
	public void setVoucherCode(long voucherCode) {
		this.voucherCode = voucherCode;
	}


	public List<AccountEntryDTO> getEntries() {
		return entries;
	}

	public void setEntries(List<AccountEntryDTO> entries) {
		this.entries = entries;
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
	
}
