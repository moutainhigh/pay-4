package com.pay.pricingstrategy.model;

import java.sql.Timestamp;


public class PricingStrategyDetail {
	
	private Long priceStrategyDetailCode;	//价格策略明细代码
	private Long priceStrategyCode;			//价格策略代码
	private Integer terminalTypeCode;		//终端类型代码
	private Timestamp effectiveFrom;		//起始时间
	private Timestamp effectiveTo;			//结束时间
	private Long rangBy;					//费率基数
	private Long rangFrom;					//交易额(起始)
	private Long rangTo;					//交易额(结束)
	private Long fixedCharge;				//固定费用
	private Long chargeRate;				//费率
	private Long maxCharge;					//上限
	private Long minCharge;					//下限
	private String reservedCode;			//扩展代码
	private String reservedName;			//扩展名称
	/**
	 * @return the priceStrategyDetailCode
	 */
	public Long getPriceStrategyDetailCode() {
		return priceStrategyDetailCode;
	}
	/**
	 * @param priceStrategyDetailCode the priceStrategyDetailCode to set
	 */
	public void setPriceStrategyDetailCode(Long priceStrategyDetailCode) {
		this.priceStrategyDetailCode = priceStrategyDetailCode;
	}
	/**
	 * @return the priceStrategyCode
	 */
	public Long getPriceStrategyCode() {
		return priceStrategyCode;
	}
	/**
	 * @param priceStrategyCode the priceStrategyCode to set
	 */
	public void setPriceStrategyCode(Long priceStrategyCode) {
		this.priceStrategyCode = priceStrategyCode;
	}
	/**
	 * @return the terminalTypeCode
	 */
	public Integer getTerminalTypeCode() {
		return terminalTypeCode;
	}
	/**
	 * @param terminalTypeCode the terminalTypeCode to set
	 */
	public void setTerminalTypeCode(Integer terminalTypeCode) {
		this.terminalTypeCode = terminalTypeCode;
	}
	/**
	 * @return the effectiveFrom
	 */
	public Timestamp getEffectiveFrom() {
		return effectiveFrom;
	}
	/**
	 * @param effectiveFrom the effectiveFrom to set
	 */
	public void setEffectiveFrom(Timestamp effectiveFrom) {
		this.effectiveFrom = effectiveFrom;
	}
	/**
	 * @return the effectiveTo
	 */
	public Timestamp getEffectiveTo() {
		return effectiveTo;
	}
	/**
	 * @param effectiveTo the effectiveTo to set
	 */
	public void setEffectiveTo(Timestamp effectiveTo) {
		this.effectiveTo = effectiveTo;
	}
	/**
	 * @return the rangBy
	 */
	public Long getRangBy() {
		return rangBy;
	}
	/**
	 * @param rangBy the rangBy to set
	 */
	public void setRangBy(Long rangBy) {
		this.rangBy = rangBy;
	}
	/**
	 * @return the rangFrom
	 */
	public Long getRangFrom() {
		return rangFrom;
	}
	/**
	 * @param rangFrom the rangFrom to set
	 */
	public void setRangFrom(Long rangFrom) {
		this.rangFrom = rangFrom;
	}
	/**
	 * @return the rangTo
	 */
	public Long getRangTo() {
		return rangTo;
	}
	/**
	 * @param rangTo the rangTo to set
	 */
	public void setRangTo(Long rangTo) {
		this.rangTo = rangTo;
	}
	/**
	 * @return the fixedCharge
	 */
	public Long getFixedCharge() {
		return fixedCharge;
	}
	/**
	 * @param fixedCharge the fixedCharge to set
	 */
	public void setFixedCharge(Long fixedCharge) {
		this.fixedCharge = fixedCharge;
	}
	/**
	 * @return the chargeRate
	 */
	public Long getChargeRate() {
		return chargeRate;
	}
	/**
	 * @param chargeRate the chargeRate to set
	 */
	public void setChargeRate(Long chargeRate) {
		this.chargeRate = chargeRate;
	}
	/**
	 * @return the maxCharge
	 */
	public Long getMaxCharge() {
		return maxCharge;
	}
	/**
	 * @param maxCharge the maxCharge to set
	 */
	public void setMaxCharge(Long maxCharge) {
		this.maxCharge = maxCharge;
	}
	/**
	 * @return the minCharge
	 */
	public Long getMinCharge() {
		return minCharge;
	}
	/**
	 * @param minCharge the minCharge to set
	 */
	public void setMinCharge(Long minCharge) {
		this.minCharge = minCharge;
	}
	/**
	 * @return the reservedCode
	 */
	public String getReservedCode() {
		return reservedCode;
	}
	/**
	 * @param reservedCode the reservedCode to set
	 */
	public void setReservedCode(String reservedCode) {
		this.reservedCode = reservedCode;
	}
	/**
	 * @return the reservedName
	 */
	public String getReservedName() {
		return reservedName;
	}
	/**
	 * @param reservedName the reservedName to set
	 */
	public void setReservedName(String reservedName) {
		this.reservedName = reservedName;
	}
}
