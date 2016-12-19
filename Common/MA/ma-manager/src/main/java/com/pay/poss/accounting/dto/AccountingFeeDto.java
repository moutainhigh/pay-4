/**
 * 
 */
package com.pay.poss.accounting.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.pay.acc.service.account.constantenum.PayForEnum;

/**
 * @Description  累计收费成功
 * @project 	ma-manager
 * @file 		AccountingFeeDto.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Date				Author			Changes
 * 2012-3-2		DDR				Create
 */
public class AccountingFeeDto {
	
	private  Date createDate; 
	private String memberCode;
	private String acctName;
	private String orderNo;//流水号
	private Integer dealType;
	private BigDecimal orderAmount;
	private BigDecimal fee;
	private Integer userType;//1是个人，2是企业
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	
	
	
	public Integer getDealType() {
		return dealType;
	}
	public void setDealType(Integer dealType) {
		this.dealType = dealType;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public String getDealTypeMsg(){
		return PayForEnum.get(this.getDealType()).getMessage();
	}
	public String getUserTypeMsg(){
		return this.getUserType()==1?"个人":"企业";
	}
	public String getAcctName() {
		return acctName;
	}
	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}
	
	
	
	
	
	
	
	
}
