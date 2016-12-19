package com.pay.credit.model.finace;


import java.sql.Timestamp;
import java.util.Date;

import com.pay.inf.model.Model;

public class FinaceProtocol implements Model{
	/**融资流水号***/
	private String creditOrderId;
	/***合约还款日期***/
	private Date repayDate;  
	/**融资方式**/
	private String interestType;
	/**融资状态***/
	private String status;
	/**利率***/
	private String interest;
	/**放款金额**/
	private String gmtAmount;
	/**利息***/
	private String longAmount;
	/**出款方式***/
	private String withdrawWay;	
	/**还款方式**/
	private String  repayWay;
	/**申请日期***/
	private Timestamp applyTime;
	/**单笔订单金额***/
	private String singleOrderAmount;
	/**出款日期**/
	private Date gmtLoan;
	/*****
	 * 申请日期时间范围查询
	 */
	private Timestamp startDate;
	
	private Timestamp endDate;

	public String getSingleOrderAmount() {
		return singleOrderAmount;
	}
	public void setSingleOrderAmount(String singleOrderAmount) {
		this.singleOrderAmount = singleOrderAmount;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public String getCreditOrderId() {
		return creditOrderId;
	}
	public void setCreditOrderId(String creditOrderId) {
		this.creditOrderId = creditOrderId;
	}
	public Date getRepayDate() {
		return repayDate;
	}
	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}
	public String getInterestType() {
		return interestType;
	}
	public void setInterestType(String interestType) {
		this.interestType = interestType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public String getGmtAmount() {
		return gmtAmount;
	}
	public void setGmtAmount(String gmtAmount) {
		this.gmtAmount = gmtAmount;
	}
	public String getLongAmount() {
		return longAmount;
	}
	public void setLongAmount(String longAmount) {
		this.longAmount = longAmount;
	}
	public String getWithdrawWay() {
		return withdrawWay;
	}
	public void setWithdrawWay(String withdrawWay) {
		this.withdrawWay = withdrawWay;
	}
	public String getRepayWay() {
		return repayWay;
	}
	public void setRepayWay(String repayWay) {
		this.repayWay = repayWay;
	}
	public Timestamp getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Timestamp applyTime) {
		this.applyTime = applyTime;
	}
	public Date getGmtLoan() {
		return gmtLoan;
	}
	public void setGmtLoan(Date gmtLoan) {
		this.gmtLoan = gmtLoan;
	} 
	
}
