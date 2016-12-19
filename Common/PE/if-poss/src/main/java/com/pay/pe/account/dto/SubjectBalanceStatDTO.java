package com.pay.pe.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;


public class SubjectBalanceStatDTO implements Serializable {


	private BigDecimal beginningDrBalanceStat; //期初借方Dr
	private BigDecimal beginningCrBalanceStat; //期初贷方Cr
	private BigDecimal beginningBalanceStat; 

	private BigDecimal endingDrBalanceStat; //期末借方Dr
	private BigDecimal endingCrBalanceStat; //期末贷方Cr
	private BigDecimal endingBalanceStat; //

	private BigDecimal amountStat; //本期借方金额
	private BigDecimal drAmountStat; //本期借方金额
	private BigDecimal crAmountStat; //本期贷方金额
	
	private BigDecimal CrSubDrStat ;
//	return crAmount.divide(BigDecimal.valueOf(1000), 2, RoundingMode.HALF_UP);

	public BigDecimal getEndingDrBalanceStat() {
		return endingDrBalanceStat;
	}

	public void setEndingDrBalanceStat(BigDecimal endingDrBalanceStat) {
		this.endingDrBalanceStat = endingDrBalanceStat;
	}

	public BigDecimal getCrSubDrStat() {
		return CrSubDrStat;
	}

	public void setCrSubDrStat(BigDecimal crSubDrStat) {
		CrSubDrStat = crSubDrStat;
	}
	
	

}
