/**
 * 
 */
package com.pay.acc.balancelog.dto;

import java.math.BigDecimal;

/**
 * 用于冻结金额的参数
 * @author ddr
 *
 */
public class FrozenAmountDto {

	private Long memberCode;//必须
	private String  acctCode;//	VARCHAR2(32)	N			账户号
	private BigDecimal frozenAmount;	//	NUMBER(19)	Y			发生金额（厘）
	private String orderSeqNo;  //用户订单流水号
	
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
	public BigDecimal getFrozenAmount() {
		return frozenAmount;
	}
	public void setFrozenAmount(BigDecimal frozenAmount) {
		this.frozenAmount = frozenAmount;
	}
	public String getOrderSeqNo() {
		return orderSeqNo;
	}
	public void setOrderSeqNo(String orderSeqNo) {
		this.orderSeqNo = orderSeqNo;
	}
	
	public String toString(){
		return "memberCode@"+memberCode+",frozenAmount@"+frozenAmount;
	}
	
	
	
	
	
}
