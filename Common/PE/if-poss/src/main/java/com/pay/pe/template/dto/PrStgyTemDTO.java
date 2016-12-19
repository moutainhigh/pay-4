package com.pay.pe.template.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PrStgyTemDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5397371863397826841L;
	
	/**
	 * id
	 */
	private Long id;
	
	/**
	 * 模板名称
	 */
	private String templateName;
	/**
	 * 價格策略類型
	 */
	private Integer priceStrategyType;
	/**
	 * 固定费用 
	 */
	private Long fixedCharge;
	/**
	 *  费率
	 */
	private Long chargeRate;
	/**
	 * 起始金额 
	 */
	private Long rangFrom;
	/**
	 * 终止金额
	 */
	private Long rangTo;
	/**
	 * 上限 
	 */
	private Long maxCharge;
	/**
	 * 下限
	 */
	private Long minCharge;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 更新时间 
	 */
	private Date updateDate;
	/**
	 * 创建人  
	 */
	private String createdBy;
	/**
	 * 修改人   
	 */
	private String modifiedBy;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public Long getFixedCharge() {
		return fixedCharge;
	}
	public void setFixedCharge(Long fixedCharge) {
		this.fixedCharge = fixedCharge;
	}
	public Long getChargeRate() {
		return chargeRate;
	}
	public void setChargeRate(Long chargeRate) {
		this.chargeRate = chargeRate;
	}
	public Long getRangFrom() {
		return rangFrom;
	}
	public void setRangFrom(Long rangFrom) {
		this.rangFrom = rangFrom;
	}
	public Long getRangTo() {
		return rangTo;
	}
	public void setRangTo(Long rangTo) {
		this.rangTo = rangTo;
	}
	public Long getMinCharge() {
		return minCharge;
	}
	public void setMinCharge(Long minCharge) {
		this.minCharge = minCharge;
	}
	public Long getMaxCharge() {
		return maxCharge;
	}
	public void setMaxCharge(Long maxCharge) {
		this.maxCharge = maxCharge;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Integer getPriceStrategyType() {
		return priceStrategyType;
	}
	public void setPriceStrategyType(Integer priceStrategyType) {
		this.priceStrategyType = priceStrategyType;
	}
	
}
