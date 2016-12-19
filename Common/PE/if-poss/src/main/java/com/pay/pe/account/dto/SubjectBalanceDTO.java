package com.pay.pe.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;


public class SubjectBalanceDTO implements Serializable {
	private String acctCode = null;
	private String acctName = null;
	private Integer balanceBy = null;
	
	//下列参数与Excel下载相关
	private String beginDate = null;
	private String endDate = null;


	private BigDecimal beginningDrBalance; //期初借方Dr余额
	private BigDecimal beginningCrBalance; //期初贷方Cr余额
	private BigDecimal beginningBalance; 

	private BigDecimal endingDrBalance; //期末借方Dr余额
	private BigDecimal endingCrBalance; //期末贷方Cr余额
	
	private BigDecimal endingBalance; //

	private BigDecimal amount; //本期借方金额
	private BigDecimal drAmount; //本期借方金额
	private BigDecimal crAmount; //本期贷方金额	
	
	//科目级别
	private String subjectLevel;

	
	public String getSubjectLevel() {
		return subjectLevel;
	}

	public void setSubjectLevel(String subjectLevel) {
		this.subjectLevel = subjectLevel;
	}

	public BigDecimal getCrAmount() {
		if (crAmount != null) {
			return crAmount.divide(BigDecimal.valueOf(1000), 2, RoundingMode.HALF_UP);
		}
		return crAmount;
	}

	
	public BigDecimal getDrAmount() {
		if (drAmount != null) {
			return drAmount.divide(BigDecimal.valueOf(1000), 2, RoundingMode.HALF_UP);
		}
		return drAmount;
	}

	

	public String getAcctCode() {
		return acctCode;
	}

	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public BigDecimal getBeginningDrBalance() {
		if (beginningDrBalance != null) {
			return beginningDrBalance.divide(BigDecimal.valueOf(1000), 2, RoundingMode.HALF_UP);
		}
//		return beginningDrBalance;
//		if(this.balanceBy!=null && this.balanceBy==1 && this.beginningBalance!=null){
//			return beginningBalance.divide(BigDecimal.valueOf(1000), 2, RoundingMode.HALF_UP);
//		}
		return BigDecimal.valueOf(0);
	}

	public BigDecimal getBeginningDr() {
		return beginningDrBalance;
	}
	
	public void setBeginningDrBalance(BigDecimal beginningDrBalance) {
		this.beginningDrBalance = beginningDrBalance;
	}
//	setScale(3, RoundingMode.HALF_UP)
	public BigDecimal getBeginningCrBalance() {
		if (beginningCrBalance != null) {
			return beginningCrBalance.divide(BigDecimal.valueOf(1000), 2, RoundingMode.HALF_UP);
		}
//		return beginningCrBalance;
//		if(this.balanceBy!=null && this.balanceBy==2 && this.beginningBalance!=null){
//			return beginningBalance.divide(BigDecimal.valueOf(1000), 2, RoundingMode.HALF_UP);
//		}
		return BigDecimal.valueOf(0);
	}
	
	public BigDecimal getBeginningCr() {
		return beginningCrBalance;
	}

	public void setBeginningCrBalance(BigDecimal beginningCrBalance) {
		this.beginningCrBalance = beginningCrBalance;
	}

	public BigDecimal getEndingDrBalance() {
		if (endingDrBalance != null) {
			return endingDrBalance.divide(BigDecimal.valueOf(1000), 2, RoundingMode.HALF_UP);
		}
//		return endingDrBalance;
//		if(this.balanceBy!=null && this.balanceBy==1 && this.endingBalance!=null){
//			return endingBalance.divide(BigDecimal.valueOf(1000), 2, RoundingMode.HALF_UP);
//		}
		return BigDecimal.valueOf(0);
	}
	
	public BigDecimal getEndingDr() {
		return endingDrBalance;
	}

	public void setEndingDrBalance(BigDecimal endingDrBalance) {
		this.endingDrBalance = endingDrBalance;
	}

	public BigDecimal getEndingCrBalance() {
		if (endingCrBalance != null) {
			return endingCrBalance.divide(BigDecimal.valueOf(1000), 2, RoundingMode.HALF_UP);
		}
//		return endingCrBalance;
//		if(this.balanceBy!=null && this.balanceBy==2 && this.endingBalance!=null){
//			return endingBalance.divide(BigDecimal.valueOf(1000), 2, RoundingMode.HALF_UP);
//		}
		return BigDecimal.valueOf(0);
	}
	
	public BigDecimal getEndingCr() {
		return endingCrBalance;
	}

	public void setEndingCrBalance(BigDecimal endingCrBalance) {
		this.endingCrBalance = endingCrBalance;
	}

	public void setCrAmount(BigDecimal crAmount) {
		this.crAmount = crAmount;
	}

	public void setDrAmount(BigDecimal drAmount) {
		this.drAmount = drAmount;
	}
	
	
	
	public String toString(){
		StringBuffer buffer  = new StringBuffer();
		buffer.append(" acctCode="+acctCode);
		buffer.append(" acctName="+acctName);
		buffer.append(" beginningDrBalance="+beginningDrBalance);
		buffer.append(" beginningCrBalance="+beginningCrBalance);
		buffer.append(" endingDrBalance="+endingDrBalance);
		buffer.append(" endingCrBalance="+endingCrBalance);
		buffer.append(" drAmount="+drAmount);
		buffer.append(" crAmount="+crAmount);
		return buffer.toString() ;
	}


	public String getBeginDate() {
		return beginDate;
	}


	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public BigDecimal getBeginningBalance() {
		if (beginningBalance != null) {
			return beginningBalance.divide(BigDecimal.valueOf(1000), 2, RoundingMode.HALF_UP);
		}
		return beginningBalance;
	}


	public void setBeginningBalance(BigDecimal beginningBalance) {
		this.beginningBalance = beginningBalance;
	}


	public BigDecimal getEndingBalance() {
		if (endingBalance != null) {
			return endingBalance.divide(BigDecimal.valueOf(1000), 2, RoundingMode.HALF_UP);
		}
		return beginningBalance;
	}


	public void setEndingBalance(BigDecimal endingBalance) {
		this.endingBalance = endingBalance;
	}


	public BigDecimal getAmount() {
		if (amount != null) {
			return amount.divide(BigDecimal.valueOf(1000), 2, RoundingMode.HALF_UP);
		}
		return amount;
	}


	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


	public Integer getBalanceBy() {
		return balanceBy;
	}

	public void setBalanceBy(Integer balanceBy) {
		this.balanceBy = balanceBy;
	}

	public BigDecimal getCrSubDr() {
		if (crAmount != null && drAmount!=null) {
			BigDecimal crsubDr =crAmount.subtract(drAmount); 
			return crsubDr.divide(BigDecimal.valueOf(1000), 2, RoundingMode.HALF_UP) ;
		}
		return null;
	}
	

}
