package com.pay.credit.model.creditorder;

import java.sql.Timestamp;
import java.util.Date;

import com.pay.inf.model.Model;
/***
 * 
 * 订单授信明细查询
 * @author ddl
 *
 */
	
public class CreditOrderDetailed implements Model{
	/**授信明细ID**/
	private String creditByOrderDetailId;
	/** 商户号 */
	private String merchantCode;
	/** 商户名称 */
	private String merchantName;
	/** 融资订单流水号 */
	private String creditOrderId;
	/**网关订单号**/
	private String tradeOrderNo;
	/**商户订单号**/
	private String orderId;
	/**授信币种**/
	private String currencyCode;
	/**融资状态**/
	private String status;
	/**订单风险状态**/
	private String riskStatus;
	/**融资金额**/
	private String orderAmount;
	/**放款金额**/
	private String gmtAmount;
	/**利率**/
	private String interest;
	/**利息**/
	private  String loanAmount;
	/**清算日期**/
	private Date settlementData;
	/**放款日期**/
	private Date gmtLoan;
	/**清算日期String**/
	private String ssettlementData;
	/**放款日期String**/
	private String sgmtLoan;
	/**审核时间**/
	private Timestamp approveTime;
	/**更新时间***/
	private Timestamp updateTime;
	/**打分**/
	private String score;
	/**打分详情**/
	private String riskAssessmentDetail;
	/**交易币种**/
	private String tradeCurrencyCode;
	/**单笔订单金额***/
	private String singleOrderAmount;
	
	public String getSingleOrderAmount() {
		return singleOrderAmount;
	}
	public void setSingleOrderAmount(String singleOrderAmount) {
		this.singleOrderAmount = singleOrderAmount;
	}
	public String getTradeCurrencyCode() {
		return tradeCurrencyCode;
	}
	public void setTradeCurrencyCode(String tradeCurrencyCode) {
		this.tradeCurrencyCode = tradeCurrencyCode;
	}
	public Timestamp getApproveTime() {
		return approveTime;
	}
	public void setApproveTime(Timestamp approveTime) {
		this.approveTime = approveTime;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public String getRiskAssessmentDetail() {
		return riskAssessmentDetail;
	}
	public void setRiskAssessmentDetail(String riskAssessmentDetail) {
		this.riskAssessmentDetail = riskAssessmentDetail;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getSsettlementData() {
		return ssettlementData;
	}
	public void setSsettlementData(String ssettlementData) {
		this.ssettlementData = ssettlementData;
	}
	public String getSgmtLoan() {
		return sgmtLoan;
	}
	public void setSgmtLoan(String sgmtLoan) {
		this.sgmtLoan = sgmtLoan;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getGmtAmount() {
		return gmtAmount;
	}
	public void setGmtAmount(String gmtAmount) {
		this.gmtAmount = gmtAmount;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public Date getSettlementData() {
		return settlementData;
	}
	public void setSettlementData(Date settlementData) {
		this.settlementData = settlementData;
	}
	public Date getGmtLoan() {
		return gmtLoan;
	}
	public void setGmtLoan(Date gmtLoan) {
		this.gmtLoan = gmtLoan;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getCreditOrderId() {
		return creditOrderId;
	}
	public void setCreditOrderId(String creditOrderId) {
		this.creditOrderId = creditOrderId;
	}
	public String getTradeOrderNo() {
		return tradeOrderNo;
	}
	public void setTradeOrderNo(String tradeOrderNo) {
		this.tradeOrderNo = tradeOrderNo;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRiskStatus() {
		return riskStatus;
	}
	public void setRiskStatus(String riskStatus) {
		this.riskStatus = riskStatus;
	}
	public String getCreditByOrderDetailId() {
		return creditByOrderDetailId;
	}
	public void setCreditByOrderDetailId(String creditByOrderDetailId) {
		this.creditByOrderDetailId = creditByOrderDetailId;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
}
