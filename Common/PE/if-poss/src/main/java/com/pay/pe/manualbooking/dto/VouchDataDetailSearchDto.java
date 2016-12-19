package com.pay.pe.manualbooking.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * 手工记账明细查询数据传输对象
 */
public class VouchDataDetailSearchDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8685467687523679381L;

	/**
	 * 账号
	 */
	private String accountCode;
	
	/**
	 * 记帐日期
	 */
	private Date applyDate;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 借款金额
	 */
	private String crAmount;
	
	/**
	 * 贷款金额
	 */
	private String drAmount;
	
	/**
	 * 凭证号
	 */
	private String vouchCode;
	
	/**
	 * 申请人
	 */
	private String operator;
	
	public VouchDataDetailSearchDto() {
		
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCrAmount() {
		return crAmount;
	}

	public void setCrAmount(String crAmount) {
		this.crAmount = crAmount;
	}

	public String getDrAmount() {
		return drAmount;
	}
	
	public String getCrAmountStr() {
		if (StringUtils.isEmpty(crAmount)) {
			return "";
		}
		crAmount = crAmount.trim();
		BigDecimal b = new BigDecimal(crAmount);
		
		b = b.setScale(2, BigDecimal.ROUND_HALF_DOWN);
		
		DecimalFormat df = new DecimalFormat("###,###.00");
		String s = df.format(b);
		
		return s;
	}
	
	public String getDrAmountStr() {
		if (StringUtils.isEmpty(drAmount)) {
			return "";
		}
		drAmount = drAmount.trim();
		BigDecimal b = new BigDecimal(drAmount);
		
		b = b.setScale(2, BigDecimal.ROUND_HALF_DOWN);
		
		DecimalFormat df = new DecimalFormat("###,###.00");
		String s = df.format(b);
		
		return s;
	}
	

	public void setDrAmount(String drAmount) {
		this.drAmount = drAmount;
	}

	public String getVouchCode() {
		return vouchCode;
	}

	public void setVouchCode(String vouchCode) {
		this.vouchCode = vouchCode;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	
}
