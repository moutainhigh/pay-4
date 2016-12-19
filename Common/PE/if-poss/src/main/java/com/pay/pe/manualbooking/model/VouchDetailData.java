package com.pay.pe.manualbooking.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.ConstructorArgumentValues.ValueHolder;

/**
 * 手工记帐申请明细域模型
 */
//implements Model 
public class VouchDetailData {
	/**
	 * 账号
	 */
//	private String accountNo;
	private String accountCode ;
	
	/**
	 * 借贷标识， 1借2贷
	 */
	private Integer crdr;
	
	/**
	 * 金额
	 */
	private Double amount;
	/**
	 * 摘要
	 */
	
	private String text;
	
	

	/**
	 * 账号名称
	 */
	private String accountName;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 物理主键
	 */
	private Long vouchDetailId;
	
	/**
	 * 相关的手工记账申请
	 */
//	private ValueHolderInterface vouchData;
	private VouchData vouchData;
	/**
	 * 物理主键
	 */
	private Long vouchDataId;
	
	private static List<String> pk = new ArrayList<String>(1);
	static {
		pk.add("vouchDetailId");
	}
	
	public VouchDetailData() {
		super();
		vouchData = new VouchData();
	}

	public Object getPrimaryKey() {
		return new Object[] { vouchDetailId };
	}

	public List getPrimaryKeyFields() {
		return pk;
	}

	public void setPrimaryKey(Object key) {
		if (null != key) {
			Object[] obj = (Object[]) key;
			setVouchDetailId((Long) obj[0]);
		}
	}

//	public String getAccountNo() {
//		return accountNo;
//	}
//
//	public void setAccountNo(String accountNo) {
//		this.accountNo = accountNo;
//	}
	

	public Integer getCrdr() {
		return crdr;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public void setCrdr(Integer crdr) {
		this.crdr = crdr;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getVouchDetailId() {
		return vouchDetailId;
	}

	public void setVouchDetailId(Long vouchDetailId) {
		this.vouchDetailId = vouchDetailId;
	}

//	protected ValueHolderInterface getVouchDataHolder() {
//		return vouchData;
//	}
//
//	protected void setVouchDataHolder(ValueHolderInterface vouchData) {
//		this.vouchData = vouchData;
//	}
	
	public VouchData getVouchData() {
//		return (VouchData) vouchData.getValue();
		return this.vouchData ;
	}
	
	public void setVouchData(VouchData vouchData) {
//		this.vouchData.setValue(vouchData);
		this.vouchData = vouchData ;
	}
	
	

	public Long getVouchDataId() {
		return vouchDataId;
	}

	public void setVouchDataId(Long vouchDataId) {
		this.vouchDataId = vouchDataId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((vouchDetailId == null) ? 0 : vouchDetailId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final VouchDetailData other = (VouchDetailData) obj;
		if (vouchDetailId == null) {
			if (other.vouchDetailId != null)
				return false;
		} else if (!vouchDetailId.equals(other.vouchDetailId))
			return false;
		return true;
	}
}
