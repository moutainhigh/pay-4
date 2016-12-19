package com.pay.channel.model;

import java.util.Date;

/**
 * 结算币种类
 * @author zhaoyang
 *
 */
public class SettlementCurrencyConfig {

	//记录自增ID
	private Long configId;
	//会员号
	private Long memberCode;
	//交易类型DCC/EDC
	private String payType;
	//交易币种
	private String tradeCurrencyCode;
	//支付币种
	private String payCurrencyCode;
	//结算币种
	private String settlementCurrencyCode;
	//操作人
	private String operator;
	//备注
	private String mark;
	/*同一个会员号、相同交易类型的配置的优先级，
	 * 数字的大小代表了等级的高低
	 */
	private Integer grade;
	//记录创建时间
	private Date   createTime;
	//更新时间
	private Date   updateTime;
	
	public Long getConfigId() {
		return configId;
	}
	public void setConfigId(Long configId) {
		this.configId = configId;
	}
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getTradeCurrencyCode() {
		return tradeCurrencyCode;
	}
	public void setTradeCurrencyCode(String tradeCurrencyCode) {
		this.tradeCurrencyCode = tradeCurrencyCode;
	}
	public String getPayCurrencyCode() {
		return payCurrencyCode;
	}
	public void setPayCurrencyCode(String payCurrencyCode) {
		this.payCurrencyCode = payCurrencyCode;
	}
	public String getSettlementCurrencyCode() {
		return settlementCurrencyCode;
	}
	public void setSettlementCurrencyCode(String settlementCurrencyCode) {
		this.settlementCurrencyCode = settlementCurrencyCode;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	
}
