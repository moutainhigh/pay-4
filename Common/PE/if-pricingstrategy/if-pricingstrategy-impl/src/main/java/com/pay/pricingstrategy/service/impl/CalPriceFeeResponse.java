package com.pay.pricingstrategy.service.impl;

import java.io.Serializable;

/**
 * @author 
 * 计算费用返回值.
 */
public class CalPriceFeeResponse implements Serializable {

    /**
     * 序列号.
     */
    private static final long serialVersionUID = 6037060771415956512L;
    /**
     * 该交易的最大交易费用，如果没有返回0.
     */
    private Long maxFee = 0L;
    /**
     * 该交易的最小交易费用，如果没有返回0.
     */
    private Long mixFee = 0L;
    /**
     * 该交易的固定费用，如果没有返回0.
     */
    private Long fixedFee = 0L;
    /**
     * 返回该交易费用，如果没有返回0.
     */
    private Long fee = 0L;
    /**
     * 交易的费率，如果没有返回0.
     * 费率是万分之多少的，使用时需要除以10000.
     */
    private Long feeRate = 0L;
    
    /**
     * 价格策略CODE
     **/
    private Long priceStrategyCode=0L;
    
    public Long getPriceStrategyCode() {
    	return priceStrategyCode;
    }
	public void setPriceStrategyCode(Long priceStrategyCode) {
    	this.priceStrategyCode = priceStrategyCode;
    }
	public Long getMaxFee() {
        return maxFee;
    }
    public void setMaxFee(Long maxFee) {
        this.maxFee = maxFee;
    }
    public Long getMixFee() {
        return mixFee;
    }
    public void setMixFee(Long mixFee) {
        this.mixFee = mixFee;
    }
    public Long getFixedFee() {
        return fixedFee;
    }
    public void setFixedFee(Long fixedFee) {
        this.fixedFee = fixedFee;
    }
    public Long getFee() {
        return fee;
    }
    public void setFee(Long fee) {
        this.fee = fee;
    }
    public Long getFeeRate() {
        return feeRate;
    }
    public void setFeeRate(Long feeRate) {
        this.feeRate = feeRate;
    }
	/**
	 * Constructs a <code>String</code> with all attributes
	 * in name = value format.
	 *
	 * @return a <code>String</code> representation 
	 * of this object.
	 */
	public String toString()
	{
	    final String TAB = "    ";
	    
	    StringBuffer retValue = new StringBuffer();
	    
	    retValue.append("CalPriceFeeResponse ( ")
	        .append(super.toString()).append(TAB)
	        .append("maxFee = ").append(this.maxFee).append(TAB)
	        .append("mixFee = ").append(this.mixFee).append(TAB)
	        .append("fixedFee = ").append(this.fixedFee).append(TAB)
	        .append("fee = ").append(this.fee).append(TAB)
	        .append("feeRate = ").append(this.feeRate).append(TAB)
	        .append(" )");
	    
	    return retValue.toString();
	}

}

