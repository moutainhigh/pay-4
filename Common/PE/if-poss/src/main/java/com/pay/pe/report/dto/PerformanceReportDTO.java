package com.pay.pe.report.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class PerformanceReportDTO  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String memberCode;
	private String memberName;
	private BigDecimal b2bAmt;
	private BigDecimal b2bFee;
	private BigDecimal b2cAmt;
	private BigDecimal b2cFee;
	private BigDecimal p2bAmt;
	private BigDecimal p2bFee;
	private BigDecimal p2aAmt;
	private BigDecimal p2aFee;
	private BigDecimal cecAmt;
	private BigDecimal cecFee;
	private BigDecimal allAmt;
	private BigDecimal allFee;
	
	private BigDecimal accPayAmt;
	private BigDecimal accPayFee;
	private BigDecimal lccAmt;
	private BigDecimal lccFee;
	private BigDecimal rcAmt;
	private BigDecimal rcFee;
	
	private BigDecimal cqpAmt;
	private BigDecimal cqpFee;
	private BigDecimal dqpAmt;
	private BigDecimal dqpFee;
	
	public BigDecimal getB2bAmt() {
		return b2bAmt;
	}
	public void setB2bAmt(BigDecimal b2bAmt) {
		this.b2bAmt = b2bAmt;
	}
	public BigDecimal getB2bFee() {
		return b2bFee;
	}
	public void setB2bFee(BigDecimal b2bFee) {
		this.b2bFee = b2bFee;
	}
	public BigDecimal getB2cAmt() {
		return b2cAmt;
	}
	public void setB2cAmt(BigDecimal b2cAmt) {
		this.b2cAmt = b2cAmt;
	}
	public BigDecimal getB2cFee() {
		return b2cFee;
	}
	public void setB2cFee(BigDecimal b2cFee) {
		this.b2cFee = b2cFee;
	}
	public BigDecimal getP2bAmt() {
		return p2bAmt;
	}
	public void setP2bAmt(BigDecimal p2bAmt) {
		this.p2bAmt = p2bAmt;
	}
	public BigDecimal getP2bFee() {
		return p2bFee;
	}
	public void setP2bFee(BigDecimal p2bFee) {
		this.p2bFee = p2bFee;
	}
	public BigDecimal getP2aAmt() {
		return p2aAmt;
	}
	public void setP2aAmt(BigDecimal p2aAmt) {
		this.p2aAmt = p2aAmt;
	}
	public BigDecimal getP2aFee() {
		return p2aFee;
	}
	public void setP2aFee(BigDecimal p2aFee) {
		this.p2aFee = p2aFee;
	}
	public BigDecimal getAllAmt() {
		return allAmt;
	}
	public void setAllAmt(BigDecimal allAmt) {
		this.allAmt = allAmt;
	}
	public BigDecimal getAllFee() {
		return allFee;
	}
	public void setAllFee(BigDecimal allFee) {
		this.allFee = allFee;
	}
	public String getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public BigDecimal getAccPayAmt() {
		return accPayAmt;
	}
	public void setAccPayAmt(BigDecimal accPayAmt) {
		this.accPayAmt = accPayAmt;
	}
	public BigDecimal getAccPayFee() {
		return accPayFee;
	}
	public void setAccPayFee(BigDecimal accPayFee) {
		this.accPayFee = accPayFee;
	}
	public BigDecimal getLccAmt() {
		return lccAmt;
	}
	public void setLccAmt(BigDecimal lccAmt) {
		this.lccAmt = lccAmt;
	}
	public BigDecimal getLccFee() {
		return lccFee;
	}
	public void setLccFee(BigDecimal lccFee) {
		this.lccFee = lccFee;
	}
	public BigDecimal getCecAmt() {
		return cecAmt;
	}
	public void setCecAmt(BigDecimal cecAmt) {
		this.cecAmt = cecAmt;
	}
	public BigDecimal getCecFee() {
		return cecFee;
	}
	public void setCecFee(BigDecimal cecFee) {
		this.cecFee = cecFee;
	}
	public BigDecimal getRcAmt() {
		return rcAmt;
	}
	public void setRcAmt(BigDecimal rcAmt) {
		this.rcAmt = rcAmt;
	}
	public BigDecimal getRcFee() {
		return rcFee;
	}
	public void setRcFee(BigDecimal rcFee) {
		this.rcFee = rcFee;
	}
	public BigDecimal getCqpAmt() {
		return cqpAmt;
	}
	public void setCqpAmt(BigDecimal cqpAmt) {
		this.cqpAmt = cqpAmt;
	}
	public BigDecimal getCqpFee() {
		return cqpFee;
	}
	public void setCqpFee(BigDecimal cqpFee) {
		this.cqpFee = cqpFee;
	}
	public BigDecimal getDqpAmt() {
		return dqpAmt;
	}
	public void setDqpAmt(BigDecimal dqpAmt) {
		this.dqpAmt = dqpAmt;
	}
	public BigDecimal getDqpFee() {
		return dqpFee;
	}
	public void setDqpFee(BigDecimal dqpFee) {
		this.dqpFee = dqpFee;
	}
}
