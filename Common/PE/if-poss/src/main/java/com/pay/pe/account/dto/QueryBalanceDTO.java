package com.pay.pe.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;


public class QueryBalanceDTO implements Serializable {

	private BigDecimal beginningBalance; //期初

	private BigDecimal endingBalance; //期末

	private BigDecimal crAmount; //当期贷方金额

	private BigDecimal drAmount; //当期借方金额

	private BigDecimal marginAmount; //差额

	//下列参数与Excel下载相关
	private String acctCode = null;
	private String beginDate = null;
	private String endDate = null;

	/**
	 * @return the beginningBalance
	 */
	public BigDecimal getBeginningBalance() {
		if (beginningBalance != null) {
			beginningBalance = beginningBalance.setScale(3, RoundingMode.HALF_UP);
		}
		return beginningBalance;
	}

	/**
	 * @return the endingBalance
	 */
	public BigDecimal getEndingBalance() {
		if (endingBalance != null) {
			endingBalance = endingBalance.setScale(3, RoundingMode.HALF_UP);
		}
		return endingBalance;
	}

	/**
	 * @return the crAmount
	 */
	public BigDecimal getCrAmount() {
		if (crAmount != null) {
			crAmount = crAmount.setScale(3, RoundingMode.HALF_UP);
		}
		return crAmount;
	}

	/**
	 * @return the drAmount
	 */
	public BigDecimal getDrAmount() {
		if (drAmount != null) {
			drAmount = drAmount.setScale(3, RoundingMode.HALF_UP);
		}
		return drAmount;
	}

	/**
	 * @return the marginAmount
	 */
	public BigDecimal getMarginAmount() {
		if (marginAmount != null) {
			marginAmount = marginAmount.setScale(3, RoundingMode.HALF_UP);
		}
		return marginAmount;
	}

	/**
	 * @return the acctCode
	 */
	public String getAcctCode() {
		return acctCode;
	}

	/**
	 * @return the beginDate
	 */
	public String getBeginDate() {
		return beginDate;
	}

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param beginningBalance the beginningBalance to set
	 */
	public void setBeginningBalance(BigDecimal beginningBalance) {
		this.beginningBalance = beginningBalance;
	}

	/**
	 * @param endingBalance the endingBalance to set
	 */
	public void setEndingBalance(BigDecimal endingBalance) {
		this.endingBalance = endingBalance;
	}

	/**
	 * @param crAmount the crAmount to set
	 */
	public void setCrAmount(BigDecimal crAmount) {
		this.crAmount = crAmount;
	}

	/**
	 * @param drAmount the drAmount to set
	 */
	public void setDrAmount(BigDecimal drAmount) {
		this.drAmount = drAmount;
	}

	/**
	 * @param marginAmount the marginAmount to set
	 */
	public void setMarginAmount(BigDecimal marginAmount) {
		this.marginAmount = marginAmount;
	}

	/**
	 * @param acctCode the acctCode to set
	 */
	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}

	/**
	 * @param beginDate the beginDate to set
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
