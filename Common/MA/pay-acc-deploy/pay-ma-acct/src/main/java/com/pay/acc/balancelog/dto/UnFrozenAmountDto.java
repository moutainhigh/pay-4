/**
 * 
 */
package com.pay.acc.balancelog.dto;

import java.math.BigDecimal;

/**
 * 用于解冻冻结金额的参数
 * @author ddr
 *
 */
public class UnFrozenAmountDto {

	private Long memberCode;//必须
	private String  acctCode;//	必须
	private BigDecimal unFrozenAmount;	//	NUMBER(19)	Y			发生金额（厘）
	private String orderSeqNo;//客户订单流水号
	
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public String getAcctCode() {
		return acctCode;
	}
	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}
	public BigDecimal getUnFrozenAmount() {
		return unFrozenAmount;
	}
	public void setUnFrozenAmount(BigDecimal unFrozenAmount) {
		this.unFrozenAmount = unFrozenAmount;
	}
	public String getOrderSeqNo() {
		return orderSeqNo;
	}
	public void setOrderSeqNo(String orderSeqNo) {
		this.orderSeqNo = orderSeqNo;
	}
	
	public String toString(){
		return "memberCode@"+memberCode+",unFrozenAmount@"+unFrozenAmount;
	}

	
	
	
	
	
	
}
