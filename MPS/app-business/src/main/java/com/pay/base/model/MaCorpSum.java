package com.pay.base.model;
/**
 * @author jerry_jin
 * @version 
 * @data 2010-10-2
 * 历史记录总和统计
 */
public class MaCorpSum {
	/**
	 * 收入总额 
	 */
	private double incomeSumNo;
	/**
	 * 支出总额
	 */
	private double expensesSumNo;
	/**
	 * 余额
	 */
	private double balanceSumNo;
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
	
	public double getIncomeSumNo() {
		return incomeSumNo;
	}
	public void setIncomeSumNo(double incomeSumNo) {
		this.incomeSumNo = incomeSumNo;
	}
	public double getExpensesSumNo() {
		return expensesSumNo;
	}
	public void setExpensesSumNo(double expensesSumNo) {
		this.expensesSumNo = expensesSumNo;
	}
	public double getBalanceSumNo() {
		return balanceSumNo;
	}
	public void setBalanceSumNo(double balanceSumNo) {
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
