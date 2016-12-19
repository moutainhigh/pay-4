package com.pay.pricingstrategy.model;

import java.sql.Timestamp;


public class PricingStrategy {

	private Integer paymentServiceCode;			//支付服务代码
	private Long priceStrategyCode;  			//价格策略代码
	private String priceStrategyName; 			//价格策略名称 
	private Integer priceStrategyType;			//价格策略类型  1-固定费用;2-费率;3-费率及下限; 4-费率及上限;5-费率及上下限;6-固定费用,费率;7-固定费用,费率及上限; 8-固定费用,费率及下限; 9-固定费用,费率及上下限;
	private Integer serviceLevelCode;			//服务等级
	private Long memberCode;					//会员号
	private Integer effectiveOn;				//生效范围 1-全局; 2-特定服务等级; 3-特定会员
	private Timestamp validDate;				//生效日期
	private Timestamp invalidDate;				//失效日期
	private Integer status;						//状态
	private Integer caculateMethod;				//计算方式 1-单笔交易; 2-每月固定; 3-特定时间;4-每月累计
	private Integer priceStrategySeq;			//价格策略次序
	private Long batchId;						//批次号
	
	/**
	 * @return  支付服务代码
	 */
	public Integer getPaymentServiceCode() {
		return paymentServiceCode;
	}
	/**
	 * @param paymentServiceCode 支付服务代码
	 */
	public void setPaymentServiceCode(Integer paymentServiceCode) {
		this.paymentServiceCode = paymentServiceCode;
	}
	/**
	 * @return 价格策略代码
	 */
	public Long getPriceStrategyCode() {
		return priceStrategyCode;
	}
	/**
	 * @param priceStrategyCode 价格策略代码
	 */
	public void setPriceStrategyCode(Long priceStrategyCode) {
		this.priceStrategyCode = priceStrategyCode;
	}
	/**
	 * @return 价格策略名称
	 */
	public String getPriceStrategyName() {
		return priceStrategyName;
	}
	/**
	 * @param priceStrategyName 价格策略名称
	 */
	public void setPriceStrategyName(String priceStrategyName) {
		this.priceStrategyName = priceStrategyName;
	}
	/**
	 * @return 价格策略类型  1-固定费用;2-费率;3-费率及下限; 4-费率及上限;5-费率及上下限;6-固定费用,费率;7-固定费用,费率及上限; 8-固定费用,费率及下限; 9-固定费用,费率及上下限;
	 */
	public Integer getPriceStrategyType() {
		return priceStrategyType;
	}
	/**
	 * @param priceStrategyType 价格策略类型  1-固定费用;2-费率;3-费率及下限; 4-费率及上限;5-费率及上下限;6-固定费用,费率;7-固定费用,费率及上限; 8-固定费用,费率及下限; 9-固定费用,费率及上下限;
	 */
	public void setPriceStrategyType(Integer priceStrategyType) {
		this.priceStrategyType = priceStrategyType;
	}
	/**
	 * @return 服务等级
	 */
	public Integer getServiceLevelCode() {
		return serviceLevelCode;
	}
	/**
	 * @param serviceLevelCode 服务等级
	 */
	public void setServiceLevelCode(Integer serviceLevelCode) {
		this.serviceLevelCode = serviceLevelCode;
	}
	/**
	 * @return 会员号
	 */
	public Long getMemberCode() {
		return memberCode;
	}
	/**
	 * @param memberCode 会员号
	 */
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	/**
	 * @return 生效范围 1-全局; 2-特定服务等级; 3-特定会员
	 */
	public Integer getEffectiveOn() {
		return effectiveOn;
	}
	/**
	 * @param effectiveOn 生效范围 1-全局; 2-特定服务等级; 3-特定会员
	 */
	public void setEffectiveOn(Integer effectiveOn) {
		this.effectiveOn = effectiveOn;
	}
	/**
	 * @return 生效日期
	 */
	public Timestamp getValidDate() {
		return validDate;
	}
	/**
	 * @param validDate 生效日期
	 */
	public void setValidDate(Timestamp validDate) {
		this.validDate = validDate;
	}
	/**
	 * @return 失效日期
	 */
	public Timestamp getInvalidDate() {
		return invalidDate;
	}
	/**
	 * @param invalidDate 失效日期
	 */
	public void setInvalidDate(Timestamp invalidDate) {
		this.invalidDate = invalidDate;
	}
	/**
	 * @return 状态
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * @param status 状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * @return 计算方式 1-单笔交易; 2-每月固定; 3-特定时间;4-每月累计
	 */
	public Integer getCaculateMethod() {
		return caculateMethod;
	}
	/**
	 * @param caculateMethod 计算方式 1-单笔交易; 2-每月固定; 3-特定时间;4-每月累计
	 */
	public void setCaculateMethod(Integer caculateMethod) {
		this.caculateMethod = caculateMethod;
	}
	/**
	 * @return 价格策略次序
	 */
	public Integer getPriceStrategySeq() {
		return priceStrategySeq;
	}
	/**
	 * @param priceStrategySeq 价格策略次序
	 */
	public void setPriceStrategySeq(Integer priceStrategySeq) {
		this.priceStrategySeq = priceStrategySeq;
	}
	/**
	 * @return 批次号
	 */
	public Long getBatchId() {
		return batchId;
	}
	/**
	 * @param batcrhId 批次号
	 */
	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}
	
}
