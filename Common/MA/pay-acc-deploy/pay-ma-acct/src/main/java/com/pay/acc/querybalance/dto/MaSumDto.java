package com.pay.acc.querybalance.dto;

import java.math.BigDecimal;

/**
 * @author 
 * @version 
 * @data 2010-7-28 上午10:56:18
 * 历史记录总和统计DTO
 */
public class MaSumDto {
	/**
	 * 收入总额 
	 */
	private BigDecimal incomeSumNo;
	/**
	 * 支出总额
	 */
	private BigDecimal expensesSumNo;
	/**
	 * 余额
	 */
	private BigDecimal balanceSumNo;
	/**
	 * 收入比数
	 */
	private Integer incomeSumNoCount;
	/**
	 *支出比数 
	 */
	private Integer expensesSumNoCount;
	/**
	 * 总条数
	 */
	private Integer count;
	
	public BigDecimal getIncomeSumNo() {
		return incomeSumNo;
	}
	public void setIncomeSumNo(BigDecimal incomeSumNo) {
		this.incomeSumNo = incomeSumNo;
	}
	public BigDecimal getExpensesSumNo() {
		return expensesSumNo;
	}
	public void setExpensesSumNo(BigDecimal expensesSumNo) {
		this.expensesSumNo = expensesSumNo;
	}
	public BigDecimal getBalanceSumNo() {
		return balanceSumNo;
	}
	public void setBalanceSumNo(BigDecimal balanceSumNo) {
		this.balanceSumNo = balanceSumNo;
	}
	public Integer getIncomeSumNoCount() {
		return incomeSumNoCount;
	}
	public void setIncomeSumNoCount(Integer incomeSumNoCount) {
		this.incomeSumNoCount = incomeSumNoCount;
	}
	public Integer getExpensesSumNoCount() {
		return expensesSumNoCount;
	}
	public void setExpensesSumNoCount(Integer expensesSumNoCount) {
		this.expensesSumNoCount = expensesSumNoCount;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	} 
	
}
