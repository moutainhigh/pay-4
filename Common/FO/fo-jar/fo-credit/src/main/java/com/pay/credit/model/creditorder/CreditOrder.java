/**
 *
 */
package com.pay.credit.model.creditorder;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import com.pay.inf.model.Model;

/**
 * @author zhixiang.deng
 *
 * @date 2015年8月4日
 */
public class CreditOrder implements Model {
	/** 融资订单流水号 */
	private String creditOrderId;
	/** 商户号 
	 * */
	private String merchantCode;
	/** 商户名称 */
	private String merchantName;
	/** 状态 */
	private String status;
	/** 授信产品 1：订单授信，2：供应链授信  */
	private String creditProduct;
	/** 1：推广，2：物流，3：采购，4：企业自用 */
	private String purpose;
	/** 融资方式*/
	private String interestType;
	/** 融资期限 */
	private Long cycle;
	/** 剩余期数 */
	private BigDecimal remainingCycle;
	/** 融资金额  */
	private BigDecimal orderAmount;
	private double dOrderAmount;
	
	public double getdOrderAmount() {
		return dOrderAmount;
	}
	public void setdOrderAmount(final double dOrderAmount) {
		this.dOrderAmount = dOrderAmount;
	}
	/** 还款方式 */
	private String repayWay;
	/** 利率 */
	private BigDecimal interestRate;
	/** 出款方式 */
	private String withdrawWay;
	/**逾期宽限天数 */
	private BigDecimal graceDays;
	/** 罚息比例 */
	private BigDecimal defaultInterestRate;
	/** 手续费 */
	private BigDecimal fee;
	/** 币种 */
	private String currencyCode;
	/**商户授信清算币种**/
	private String creditCurrency;
	/** 应还总额 */
	private BigDecimal supposedTotalAmount;
	/** 已还本金*/
	private BigDecimal repayAmount;
	/** 当期应还本金 */
	private BigDecimal supposedRepayPrincipal;
	/** 当期利息 */
	private BigDecimal supposedRepayInterest;
	/** 当前罚息 */
	private BigDecimal supposedDefaultInterest;
	/** 待还本息总额 */
	private BigDecimal supposedRepayTotal;
	/** 备注*/
	private String memo;
	/** 公式备注 */
	private String formulaComment;
	/** 申请时间 */
	private Timestamp applyTime;
	/** 审核时间 */
	private Timestamp approveTime;
	/** 合约还款日期 */
	private Date repayDate;
	/** 放款日期 */
	private Date loanDate;
	/** 更新时间 */
	private Timestamp updateTime;
	/** 账号 */
	private String accountNo;
	/** 户名 */
	private String accountName;
	/** 开户行 */
	private String accountBank;
	/** 风险资金 */
	private BigDecimal riskAmount;
	/** 风险利率 */
	private BigDecimal riskRate;
	/** 合约开始日期 */
	private Date agreementStartDate;
	/** 合约结束日期 */
	private Date agreementEndDate;
	/** 融资期限单位 */
	private String cycleType;
	/** 信用评级 */
	private String creditRating;
	/**申请笔数**/
	private Integer applyCount;
	/**申请的单笔订单金额**/
	private BigDecimal singleOrderAmount;
	
	/***通过的笔数**/
	private Integer passCount;
	/**通过笔数的金额***/
	private String passOrderAmount;
	
	public String getPassOrderAmount() {
		return passOrderAmount;
	}
	public void setPassOrderAmount(String passOrderAmount) {
		this.passOrderAmount = passOrderAmount;
	}
	public Integer getPassCount() {
		return passCount;
	}
	public void setPassCount(Integer passCount) {
		this.passCount = passCount;
	}
	private Integer gmtCount;
	
	/** 授信申请金额 */
	private BigDecimal applyOrderAmount ;
	/** 入账金额 */
	private BigDecimal amountCredited ;
	
	public Integer getGmtCount() {
		return gmtCount;
	}
	public void setGmtCount(final Integer gmtCount) {
		this.gmtCount = gmtCount;
	}
	public BigDecimal getSingleOrderAmount() {
		return singleOrderAmount;
	}
	public void setSingleOrderAmount(final BigDecimal singleOrderAmount) {
		this.singleOrderAmount = singleOrderAmount;
	}
	public Integer getApplyCount() {
		return applyCount;
	}
	public void setApplyCount(final Integer applyCount) {
		this.applyCount = applyCount;
	}
	public String getCreditOrderId() {
		return creditOrderId;
	}
	public void setCreditOrderId(final String creditOrderId) {
		this.creditOrderId = creditOrderId;
	}
	public String getMerchantCode() {
		return merchantCode;
	}
	public void setMerchantCode(final String merchantCode) {
		this.merchantCode = merchantCode;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(final String merchantName) {
		this.merchantName = merchantName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(final String status) {
		this.status = status;
	}
	public String getCreditProduct() {
		return creditProduct;
	}
	public void setCreditProduct(final String creditProduct) {
		this.creditProduct = creditProduct;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(final String purpose) {
		this.purpose = purpose;
	}
	public String getInterestType() {
		return interestType;
	}
	public void setInterestType(final String interestType) {
		this.interestType = interestType;
	}
	
	public Long getCycle() {
		return cycle;
	}
	public void setCycle(final Long cycle) {
		this.cycle = cycle;
	}
	public BigDecimal getRemainingCycle() {
		return remainingCycle;
	}
	public void setRemainingCycle(final BigDecimal remainingCycle) {
		this.remainingCycle = remainingCycle;
	}
	public BigDecimal getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(final BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getRepayWay() {
		return repayWay;
	}
	public void setRepayWay(final String repayWay) {
		this.repayWay = repayWay;
	}
	public BigDecimal getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(final BigDecimal interestRate) {
		this.interestRate = interestRate;
	}
	public String getWithdrawWay() {
		return withdrawWay;
	}
	public void setWithdrawWay(final String withdrawWay) {
		this.withdrawWay = withdrawWay;
	}
	public BigDecimal getGraceDays() {
		return graceDays;
	}
	public void setGraceDays(final BigDecimal graceDays) {
		this.graceDays = graceDays;
	}
	public BigDecimal getDefaultInterestRate() {
		return defaultInterestRate;
	}
	public void setDefaultInterestRate(final BigDecimal defaultInterestRate) {
		this.defaultInterestRate = defaultInterestRate;
	}
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(final BigDecimal fee) {
		this.fee = fee;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(final String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public BigDecimal getSupposedTotalAmount() {
		return supposedTotalAmount;
	}
	public void setSupposedTotalAmount(final BigDecimal supposedTotalAmount) {
		this.supposedTotalAmount = supposedTotalAmount;
	}
	public BigDecimal getRepayAmount() {
		return repayAmount;
	}
	public void setRepayAmount(final BigDecimal repayAmount) {
		this.repayAmount = repayAmount;
	}
	public BigDecimal getSupposedRepayPrincipal() {
		return supposedRepayPrincipal;
	}
	public void setSupposedRepayPrincipal(final BigDecimal supposedRepayPrincipal) {
		this.supposedRepayPrincipal = supposedRepayPrincipal;
	}
	public BigDecimal getSupposedRepayInterest() {
		return supposedRepayInterest;
	}
	public void setSupposedRepayInterest(final BigDecimal supposedRepayInterest) {
		this.supposedRepayInterest = supposedRepayInterest;
	}
	public BigDecimal getSupposedDefaultInterest() {
		return supposedDefaultInterest;
	}
	public void setSupposedDefaultInterest(final BigDecimal supposedDefaultInterest) {
		this.supposedDefaultInterest = supposedDefaultInterest;
	}
	public BigDecimal getSupposedRepayTotal() {
		return supposedRepayTotal;
	}
	public void setSupposedRepayTotal(final BigDecimal supposedRepayTotal) {
		this.supposedRepayTotal = supposedRepayTotal;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(final String memo) {
		this.memo = memo;
	}
	public String getFormulaComment() {
		return formulaComment;
	}
	public void setFormulaComment(final String formulaComment) {
		this.formulaComment = formulaComment;
	}
	public Timestamp getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(final Timestamp applyTime) {
		this.applyTime = applyTime;
	}
	public Timestamp getApproveTime() {
		return approveTime;
	}
	public void setApproveTime(final Timestamp approveTime) {
		this.approveTime = approveTime;
	}
	public Date getRepayDate() {
		return repayDate;
	}
	public void setRepayDate(final Date repayDate) {
		this.repayDate = repayDate;
	}
	public Date getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(final Date loanDate) {
		this.loanDate = loanDate;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(final Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(final String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(final String accountName) {
		this.accountName = accountName;
	}
	public String getAccountBank() {
		return accountBank;
	}
	public void setAccountBank(final String accountBank) {
		this.accountBank = accountBank;
	}
	public BigDecimal getRiskAmount() {
		return riskAmount;
	}
	public void setRiskAmount(final BigDecimal riskAmount) {
		this.riskAmount = riskAmount;
	}
	public BigDecimal getRiskRate() {
		return riskRate;
	}
	public void setRiskRate(final BigDecimal riskRate) {
		this.riskRate = riskRate;
	}
	public Date getAgreementStartDate() {
		return agreementStartDate;
	}
	public void setAgreementStartDate(final Date agreementStartDate) {
		this.agreementStartDate = agreementStartDate;
	}
	public Date getAgreementEndDate() {
		return agreementEndDate;
	}
	public void setAgreementEndDate(final Date agreementEndDate) {
		this.agreementEndDate = agreementEndDate;
	}
	public String getCycleType() {
		return cycleType;
	}
	public void setCycleType(final String cycleType) {
		this.cycleType = cycleType;
	}
	public String getCreditRating() {
		return creditRating;
	}
	public void setCreditRating(final String creditRating) {
		this.creditRating = creditRating;
	}
	public String getCreditCurrency() {
		return creditCurrency;
	}
	public void setCreditCurrency(final String creditCurrency) {
		this.creditCurrency = creditCurrency;
	}
	public BigDecimal getApplyOrderAmount() {
		return applyOrderAmount;
	}
	public void setApplyOrderAmount(final BigDecimal applyOrderAmount) {
		this.applyOrderAmount = applyOrderAmount;
	}
	public BigDecimal getAmountCredited() {
		return amountCredited;
	}
	public void setAmountCredited(final BigDecimal amountCredited) {
		this.amountCredited = amountCredited;
	}
	
}
