/**
 *  File: MassPaytobankOrderTotalInfo.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-19      bill_peng     Changes
 *  
 *
 */
package com.pay.fundout.withdraw.model.masspaytobank;

import java.io.Serializable;

/**
 * @author bill_peng
 *
 */
public class MassPaytobankOrderTotalInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2654760407305699175L;
	private Integer succNum;
	private Integer faildNum;
	private Long succAmount;
	private Long faildAmount;
	public Integer getSuccNum() {
		return succNum;
	}
	public void setSuccNum(Integer succNum) {
		this.succNum = succNum;
	}
	public Integer getFaildNum() {
		return faildNum;
	}
	public void setFaildNum(Integer faildNum) {
		this.faildNum = faildNum;
	}
	public Long getSuccAmount() {
		return succAmount;
	}
	public void setSuccAmount(Long succAmount) {
		this.succAmount = succAmount;
	}
	public Long getFaildAmount() {
		return faildAmount;
	}
	public void setFaildAmount(Long faildAmount) {
		this.faildAmount = faildAmount;
	}
	
	

}
