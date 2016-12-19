package com.pay.pe.accumulated.chargebacklog.dto;


public class ChargeBackLogDto {
	
	private Long 		id;
	private String		payerAcctCode;
	private	Integer 	priceStrategyCode;
	private String 		voucherCode;
	private Long 		fee;
	private Integer 	status;
	private String		errorMsg;
	private Long		memberCode;
	private String		payeeAcctCode;
	private Integer 	dealType;
	
	public Integer getDealType() {
		return dealType;
	}
	public void setDealType(Integer dealType) {
		this.dealType = dealType;
	}
	public String getPayeeAcctCode() {
		return payeeAcctCode;
	}
	public void setPayeeAcctCode(String payeeAcctCode) {
		this.payeeAcctCode = payeeAcctCode;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPayerAcctCode() {
		return payerAcctCode;
	}
	public void setPayerAcctCode(String payerAcctCode) {
		this.payerAcctCode = payerAcctCode;
	}
	public Integer getPriceStrategyCode() {
		return priceStrategyCode;
	}
	public void setPriceStrategyCode(Integer priceStrategyCode) {
		this.priceStrategyCode = priceStrategyCode;
	}
	public String getVoucherCode() {
		return voucherCode;
	}
	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}
	public Long getFee() {
		return fee;
	}
	public void setFee(Long fee) {
		this.fee = fee;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

}
