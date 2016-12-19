package com.pay.poss.membermanager.formbean;



/**
 * @Description 
 * @project 	poss-membermanager
 * @file 		AccountInfoSearchFormBean.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-8-3		gungun_zhang			Create
 */
public class MemberTradeSearchFormBean {
	/**
	 * 查询者
	 */
	private String memberCode;
	/**
	 * 时间选择框:当天,本周,本月.
	 */
	private String timeSelect;
	/**
	 * 开始时间  
	 */
	private String startTime;
	/**
	 * 结束时间 
	 */
	private String endTime;
	/**
	 * 交易状态  0未付款,1付款中,2已付款,3关闭,4完成
	 */
	private String transactionStatus;
	/**
	 * 资金流向(交易类型)0全部;1收入;2支出
	 */
	private String funds;
	/**
	 * 对方名称  
	 */
	private String counterPartName;
	/**
	 * 消费名称 
	 */
	private String goodsName;
	/**
	 * 交易号 
	 */
	private String transactionNo;
	/**
	 * 会员类型
	 */
	private String memberType;
	/**
	 * 充提业务类型
	 */
	private String fifoType;
	/**
	 * 消费类型
	 */
	private String tradeType;
	
	
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getFifoType() {
		return fifoType;
	}
	public void setFifoType(String fifoType) {
		this.fifoType = fifoType;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public String getTimeSelect() {
		return timeSelect;
	}
	public void setTimeSelect(String timeSelect) {
		this.timeSelect = timeSelect;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	public String getFunds() {
		return funds;
	}
	public void setFunds(String funds) {
		this.funds = funds;
	}
	public String getCounterPartName() {
		return counterPartName;
	}
	public void setCounterPartName(String counterPartName) {
		this.counterPartName = counterPartName;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getTransactionNo() {
		return transactionNo;
	}
	public void setTransactionNo(String transactionNo) {
		this.transactionNo = transactionNo;
	}
	public String getMemberType() {
		return memberType;
	}
	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	
}
