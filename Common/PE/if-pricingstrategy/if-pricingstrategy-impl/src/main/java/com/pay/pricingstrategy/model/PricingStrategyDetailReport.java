package com.pay.pricingstrategy.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class PricingStrategyDetailReport {

	private Long priceStrategyDetailCode; // 价格策略明细代码
	private Long priceStrategyCode; // 价格策略代码
	private Integer terminalTypeCode; // 终端类型代码
	private Timestamp effectiveFrom; // 起始时间
	private Timestamp effectiveTo; // 结束时间
	private BigDecimal rangBy; // 费率基数
	private BigDecimal rangFrom; // 交易额(起始)
	private BigDecimal rangTo; // 交易额(结束)
	private BigDecimal fixedCharge; // 固定费用
	private BigDecimal chargeRate; // 渠道手续费费率
	private BigDecimal maxCharge; // 上限
	private BigDecimal minCharge; // 下限
	private String reservedCode; // 扩展代码
	private String reservedName; // 扩展名称

	public Long getPriceStrategyDetailCode() {
		return priceStrategyDetailCode;
	}

	public void setPriceStrategyDetailCode(Long priceStrategyDetailCode) {
		this.priceStrategyDetailCode = priceStrategyDetailCode;
	}

	public Long getPriceStrategyCode() {
		return priceStrategyCode;
	}

	public void setPriceStrategyCode(Long priceStrategyCode) {
		this.priceStrategyCode = priceStrategyCode;
	}

	public Integer getTerminalTypeCode() {
		return terminalTypeCode;
	}

	public void setTerminalTypeCode(Integer terminalTypeCode) {
		this.terminalTypeCode = terminalTypeCode;
	}

	public Timestamp getEffectiveFrom() {
		return effectiveFrom;
	}

	public void setEffectiveFrom(Timestamp effectiveFrom) {
		this.effectiveFrom = effectiveFrom;
	}

	public Timestamp getEffectiveTo() {
		return effectiveTo;
	}

	public void setEffectiveTo(Timestamp effectiveTo) {
		this.effectiveTo = effectiveTo;
	}

	public BigDecimal getRangBy() {
		return rangBy;
	}

	public void setRangBy(BigDecimal rangBy) {
		this.rangBy = rangBy;
	}

	public BigDecimal getRangFrom() {
		return rangFrom;
	}

	public void setRangFrom(BigDecimal rangFrom) {
		this.rangFrom = rangFrom;
	}

	public BigDecimal getRangTo() {
		return rangTo;
	}

	public void setRangTo(BigDecimal rangTo) {
		this.rangTo = rangTo;
	}

	public BigDecimal getFixedCharge() {
		return fixedCharge;
	}

	public void setFixedCharge(BigDecimal fixedCharge) {
		this.fixedCharge = fixedCharge;
	}

	public BigDecimal getChargeRate() {
		return chargeRate;
	}

	public void setChargeRate(BigDecimal chargeRate) {
		this.chargeRate = chargeRate;
	}

	public BigDecimal getMaxCharge() {
		return maxCharge;
	}

	public void setMaxCharge(BigDecimal maxCharge) {
		this.maxCharge = maxCharge;
	}

	public BigDecimal getMinCharge() {
		return minCharge;
	}

	public void setMinCharge(BigDecimal minCharge) {
		this.minCharge = minCharge;
	}

	public String getReservedCode() {
		return reservedCode;
	}

	public void setReservedCode(String reservedCode) {
		this.reservedCode = reservedCode;
	}

	public String getReservedName() {
		return reservedName;
	}

	public void setReservedName(String reservedName) {
		this.reservedName = reservedName;
	}

}
