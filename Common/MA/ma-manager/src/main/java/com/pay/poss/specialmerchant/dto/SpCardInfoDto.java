package com.pay.poss.specialmerchant.dto;

public class SpCardInfoDto {
	/**
	 * 主键Id
	 */
	private Long spMerchantCardId;
	/**
	 * 特约商户id 
	 */
	private Long spMerchantId;
	/**
	 * 卡种id  
	 */
	private Long cardTypeId;
	/**
	 * 折扣信息
	 */
	private String discountInfo;
	/**
	 * 卡种名称
	 */
	private String enumName;
	public Long getSpMerchantCardId() {
		return spMerchantCardId;
	}
	public void setSpMerchantCardId(Long spMerchantCardId) {
		this.spMerchantCardId = spMerchantCardId;
	}
	public Long getSpMerchantId() {
		return spMerchantId;
	}
	public void setSpMerchantId(Long spMerchantId) {
		this.spMerchantId = spMerchantId;
	}
	public Long getCardTypeId() {
		return cardTypeId;
	}
	public void setCardTypeId(Long cardTypeId) {
		this.cardTypeId = cardTypeId;
	}
	public String getDiscountInfo() {
		return discountInfo;
	}
	public void setDiscountInfo(String discountInfo) {
		this.discountInfo = discountInfo;
	}
	public String getEnumName() {
		return enumName;
	}
	public void setEnumName(String enumName) {
		this.enumName = enumName;
	}

}
