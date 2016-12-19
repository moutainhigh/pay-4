package com.pay.pe.manualbooking.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;

/**
 * 手工记账明细数据传输对象ϸ
 */
public class VouchDataDetailDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5097486303129470667L;

	/**
	 * 明细物理主键
	 */
	private Long vouchDetailId;
	
	/**
	 * 帐户号
	 */
//	private String accountNo;
	private String accountCode;
	
	
	/**
	 * 借贷标记：1借2贷
	 */
	private Integer crdr;
	
	/**
	 * 金额数目
	 */
	private String amount;
	
	/**
	 * 帐户名称
	 */
	private String accountName;
	
	/**
	 * 备注
	 */
	private String remark;

	public VouchDataDetailDto() {
		
	}
	
	public VouchDataDetailDto(String accountCode, Integer crdr, String amount,
			String accountName, String remark) {
		super();
//		this.accountNo = accountNo;
		this.accountCode = accountCode ;
		this.crdr = crdr;
		this.setAmount(amount);
		this.accountName = accountName;
		this.remark = remark;
	}

//	public String getAccountNo() {
//		return accountNo;
//	}

	public Integer getCrdr() {
		return crdr;
	}

	public String getAmount() {
		return amount;
	}

	public String getAccountName() {
		return accountName;
	}

	public String getRemark() {
		return remark;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Long getVouchDetailId() {
		return vouchDetailId;
	}

	public void setVouchDetailId(Long vouchDetailId) {
		this.vouchDetailId = vouchDetailId;
	}

//	public void setAccountNo(String accountNo) {
//		this.accountNo = accountNo;
//	}

	public void setCrdr(Integer crdr) {
		this.crdr = crdr;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getAmountStr() {
		if (StringUtils.isEmpty(amount)) {
			return "";
		}
		amount = amount.trim();
		BigDecimal b = new BigDecimal(amount);
		b = b.setScale(2, BigDecimal.ROUND_HALF_DOWN);
		DecimalFormat df = new DecimalFormat("#,##0.00");
		String s = df.format(b);
		return s;
	}
	
	
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append(" vouchDetailId= "+vouchDetailId);
		buffer.append(" accountCode= "+accountCode);
		buffer.append(" crdr= "+crdr);
		buffer.append(" amount= "+amount);
		buffer.append(" accountName= "+accountName );
		
		return buffer.toString() ;
	}
}
